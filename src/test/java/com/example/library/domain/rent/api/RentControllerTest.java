package com.example.library.domain.rent.api;

import com.example.library.domain.RestDocsSupport;
import com.example.library.domain.rent.application.RentService;
import com.example.library.domain.rent.domain.dto.RentStatusResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(RentController.class)
class RentControllerTest extends RestDocsSupport {

    @MockBean
    RentService rentService;

    @Test
    @WithMockUser(username = "테스트_최고관리자", authorities = {"0"}) //권한 부여
    void 유저_대여현황_가져오기() throws Exception {
        //given
        Long userNo=1L;
        RentStatusResponseDto.Response response1 = new RentStatusResponseDto.Response(1L,"test1","20240329","20240401",true);
        RentStatusResponseDto.Response response2 = new RentStatusResponseDto.Response(2L,"test1","20240329","20240401",true);
        List<RentStatusResponseDto.Response> list = Arrays.asList(response1,response2);

        //when
        when(rentService.getCurrentRentStatus(userNo)).thenReturn(list);

        //then
        mockMvc.perform(get("/rent/currentRentStatus/user/1")
                .with(csrf())
        )
        .andExpect(jsonPath("$.data[0].bookNo").value(1L))
        .andExpect(jsonPath("$.data[1].bookNo").value(2L))
        ;

    }
}