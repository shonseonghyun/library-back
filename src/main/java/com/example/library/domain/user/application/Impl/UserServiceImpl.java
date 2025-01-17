package com.example.library.domain.user.application.Impl;

import com.example.library.annotation.NeedNotify;
import com.example.library.annotation.Timer;
import com.example.library.domain.heart.application.event.CheckUserExistEvent;
import com.example.library.domain.user.application.dto.*;
import com.example.library.domain.user.domain.UserEntity;
import com.example.library.domain.user.application.event.UserDeletedEvent;
import com.example.library.domain.user.domain.repository.UserRepository;
import com.example.library.domain.user.enums.SocialLoginType;
import com.example.library.domain.user.enums.UserGrade;
import com.example.library.domain.user.infrastructure.UserOpenFeignClient;
import com.example.library.domain.user.application.UserService;
import com.example.library.domain.user.application.event.UserJoinedEvent;
import com.example.library.exception.ErrorCode;
import com.example.library.exception.exceptions.PasswordDifferentException;
import com.example.library.exception.exceptions.UserNotFoundException;
import com.example.library.global.event.Events;
import com.example.library.global.mail.domain.mail.application.event.SendedMailEvent;
import com.example.library.global.mail.domain.mail.enums.MailType;
import com.example.library.global.mail.domain.mail.application.dto.MailDto;
import com.example.library.global.response.ApiResponseDto;
import com.example.library.global.security.oauth2.principal.CustomOAuth2User;
import com.example.library.global.security.oauth2.userInfo.CustomOAuthAttributes;
import com.example.library.global.utils.JwtUtil;
import com.example.library.global.utils.MergeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final UserOpenFeignClient userOpenFeignClient;

    @Override
    @Timer
    @NeedNotify(type = MailType.MAIL_JOIN)
    @Transactional
    public void join(UserJoinReqDto userJoinReqDto) {
        UserEntity user = UserEntity.createOfficialUser()
                .userId(userJoinReqDto.getUserId())
                .userPwd(encoder.encode(userJoinReqDto.getUserPwd()))
                .userName(userJoinReqDto.getUserName())
                .tel(userJoinReqDto.getTel())
                .userEmail(userJoinReqDto.getEmail())
                .gender(userJoinReqDto.getGender())
                .useFlg(userJoinReqDto.getUseFlg())
                .userGrade(UserGrade.OFFICIALMEMBER)
                .build()
        ;

        UserEntity saved= userRepository.save(user);
        Events.raise(new UserJoinedEvent(saved.getUserNo()));
        Events.raise(new SendedMailEvent(new MailDto(user.getUserNo(), MailType.MAIL_JOIN)));
    }

    @Override
    @Timer
    @NeedNotify(type = MailType.MAIL_LOGIN)
    @Transactional
    public UserLoginResDto login(UserLoginReqDto userLoginReqDto) {
        String refreshToken = null;

        UserEntity selectedUser = getUserEntityByUserId(userLoginReqDto.getUserId());
        matchPassWord(userLoginReqDto.getUserPwd(), selectedUser.getUserPwd());

        String accessToken = JwtUtil.createAccessToken(selectedUser.getUserId());
        if(userLoginReqDto.isAutoLogin()){
            refreshToken = JwtUtil.createRefreshToken();
            doAutoLogin(selectedUser,refreshToken);
        }
        Events.raise(new SendedMailEvent(new MailDto(selectedUser.getUserNo(),MailType.MAIL_LOGIN)));
        return UserLoginResDto.from(selectedUser,accessToken,refreshToken);
    }

    @Override
    @Timer
    @Transactional
    public void doAutoLogin(UserEntity selectedUser,String refreshToken){
        log.info("자동 로그인 설정");
        selectedUser.loginSuccess(refreshToken);
        userRepository.save(selectedUser);
    }



    private boolean matchPassWord(String inputPwd,String dbPwd){
        if(!encoder.matches(inputPwd, dbPwd)) {
            throw new PasswordDifferentException(ErrorCode.PASSWORD_DIFFERNET);
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public UserSearchResDto getUserByUserNo(Long userNo) {
        UserEntity userEntity = getUserEntityByUserNo(userNo);
        return UserSearchResDto.from(userEntity);
    }

    @Override
    @Transactional
    public UserSearchResDto update(Long userNo, UserUpdateReqDto userUpdateReqDto) {
        UserEntity selectedUser = getUserEntityByUserNo(userNo);

        String encodingPwd =
                userUpdateReqDto.getUserPwd()==null || userUpdateReqDto.getUserPwd().replace(" ","").length()==0
                        ? null : encoder.encode(userUpdateReqDto.getUserPwd())
                ;

        String tel=
                userUpdateReqDto.getTel()==null || userUpdateReqDto.getTel().replace(" ","").length()==0
                        ? null : userUpdateReqDto.getTel()
                ;

        UserEntity targetUser= UserEntity.createOfficialUser()
                .userPwd(encodingPwd)
                .tel(tel)
                .gender(userUpdateReqDto.getGender())
                .build();

        MergeUtil.merge(selectedUser,targetUser);
        return UserSearchResDto.from(selectedUser);
    }

    @Override
    @NeedNotify(type = MailType.MAIL_DELETE)
    @Transactional
    public void delete(Long userNo) {
        UserEntity selectedUser = getUserEntityByUserNo(userNo);
        userRepository.deleteByUserNo(userNo);
        Events.raise(new UserDeletedEvent(userNo));
        Events.raise(new SendedMailEvent(new MailDto(userNo, selectedUser.getUserId(),selectedUser.getUserEmail(), MailType.MAIL_DELETE)));

    }

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        /*
         * DefaultOAut2UserService 객체 생성 후 해당 객체를 통하여 loadUser메소드를 통해 OAuth2User 반환
         * DefaultOAut2UserService.loadUser()메소드는 역할은 간략히 아래 순서로 흐른다
         * 1. 소셜로그인 API의 사용자 정보를 위한 통신
         * 2. 소셜로그인 API로부터 응답 받은 사용자 정보로 DefaultOAuth2User 객체 생성 후 반환
         * 따라서!! OAuth2User객체는 사용자 정보가 담긴 객체!
         */
        OAuth2UserService<OAuth2UserRequest,OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        //1. 당사에 가입된 유저 확인을 위한 데이터 추출
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        SocialLoginType socialLoginType = SocialLoginType.getSocialType(userRequest.getClientRegistration().getRegistrationId());
        Map<String,Object> attributes = oAuth2User.getAttributes();

        /* 플랫폼 종류에 따라 응답받은 유저 정보를 통해 CustomOAuthAttributes 객체 생성 */
        CustomOAuthAttributes customOAuthAttributes = CustomOAuthAttributes.of(socialLoginType,userNameAttributeName,attributes);


        //2. 당사 존재 여부 파악 (조회 조건: 소셜플랫폼 종류, email, socialId )
        Optional<UserEntity> saved = userRepository.findByProviderAndProviderIdAndUserEmail
                (
                    socialLoginType,
                    customOAuthAttributes.getOAuthUserInfo().getProviderId(),
                    customOAuthAttributes.getOAuthUserInfo().getEmail()
                );

        //3. 존재하지 않는 경우 자동 회원가입/ 존재하는 경우 패스
        if(saved.isEmpty()){
            UserEntity userEntityBySocialLogin = CustomOAuthAttributes.toEntity(socialLoginType, customOAuthAttributes.getOAuthUserInfo());
            UserEntity savedUserEntityBySocialLogin = userRepository.save(userEntityBySocialLogin);

            Events.raise(new UserJoinedEvent(savedUserEntityBySocialLogin.getUserNo()));
            Events.raise(new SendedMailEvent(new MailDto(savedUserEntityBySocialLogin.getUserNo(), MailType.MAIL_JOIN)));

            return new CustomOAuth2User(
                        Collections.singleton(new SimpleGrantedAuthority("ROLE")),
                        oAuth2User.getAttributes(),
                        userNameAttributeName,
                    savedUserEntityBySocialLogin
                    );
        }
        else{
            return new CustomOAuth2User(
                        Collections.singleton(new SimpleGrantedAuthority("ROLE")),
                        oAuth2User.getAttributes(),
                        userNameAttributeName,
                        saved.get()
                    );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserGrade getUserGrade(String userId) {
        UserEntity userEntity = getUserEntityByUserId(userId);
        return userEntity.getUserGrade();
    }

    public List<UserRentStatusResDto> getCurrentRentStatus(Long userNo){
        UserEntity selectedUser = getUserEntityByUserNo(userNo);
        ApiResponseDto<List<UserRentStatusResDto>> response = userOpenFeignClient.getCurrentRentStatus(userNo);
        return response.getData();
    }

    @Override
    public List<UserRentHistoryResDto> getRentHistory(Long userNo){
        UserEntity selectedUser = getUserEntityByUserNo(userNo);
        ApiResponseDto<List<UserRentHistoryResDto>> response = userOpenFeignClient.getRentHistory(userNo);
        return response.getData();
    }

    @Override
    public boolean checkExistUserId(String userId) {
        boolean existsFlg = true;
        try{
            UserEntity selecUserEntity =  userRepository.findByUserId(userId);
        }catch (UserNotFoundException e){
            existsFlg =false;
        }
        return existsFlg;
    }

    @Override
    public String reissueAccessTokenWithRefreshToken(String refreshToken) {
        UserEntity selectedUser = userRepository.findByRefreshToken(refreshToken);
        return JwtUtil.createAccessToken(selectedUser.getUserId());
    }

    /**
     * 유저번호로 유저엔티티 조회
     * @param userNo
     * @return throw UserNotFoundException
     */
    private UserEntity getUserEntityByUserNo(Long userNo) {
        return userRepository.findByUserNo(userNo);
    }

    private UserEntity getUserEntityByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    @EventListener
    public void checkUserExist(CheckUserExistEvent evt){
        getUserEntityByUserNo(evt.getUserNo());
    }
}
