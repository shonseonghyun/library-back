package com.example.library.domain.user.entity;

import com.example.library.domain.review.entity.ReviewEntity;
import com.example.library.domain.user.entity.converter.SocialLoginTypeConverter;
import com.example.library.domain.user.entity.converter.UserGradeConverter;
import com.example.library.domain.user.enums.SocialLoginType;
import com.example.library.domain.user.enums.UserGrade;
import com.example.library.global.entityListener.Entity.ModifiedEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName ="createOfficialUser",builderClassName = "createOfficialUser")
@Table(name = "user")
@DynamicInsert
public class UserEntity extends ModifiedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String userPwd;

    @Column(nullable = false)
    private String userName;

    private String tel;

    @Column(nullable = false, name = "email")
    private String userEmail;

    @Convert(converter = SocialLoginTypeConverter.class)
    private SocialLoginType provider;

    @Column(name = "provider_id")
    private String providerId;

    private String gender;

    @Column(nullable = false)
    private Integer useFlg;

    @Column(name = "user_grade")
    @Convert(converter = UserGradeConverter.class)
    private UserGrade userGrade;

    @OneToMany(
            cascade = {CascadeType.MERGE //유저엔티티를 통한 리뷰엔티티리스트 내 추가할 때 필요
                    ,CascadeType.PERSIST //reviewNo를 알고 있는 UserEntitiy가 저장되는 시점에 리뷰에 대한 정보도 함꼐 저장
            } //PERSIST 대신 MERGE나 ALL 가능
            ,orphanRemoval = true

    )
    @JoinColumn(name = "user_no",updatable = false,nullable = false)
    private List<ReviewEntity> review = new ArrayList<>();

    @Builder(builderMethodName = "createOAuth2User", builderClassName = "createOAuth2User")
    public UserEntity (String userId, String userPwd, String userEmail, String userName, String providerId, SocialLoginType provider, Integer useFlg) {
        this.userId = userId;
        this.userPwd = userPwd;
        this.userEmail = userEmail;
        this.userName = userName;
        this.providerId = providerId;
        this.provider = provider;
        this.userGrade = UserGrade.OFFICIALMEMBER;
        this.useFlg = useFlg;
    }
}