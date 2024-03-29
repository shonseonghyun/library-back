package com.example.library.domain.rent.application;

import com.example.library.domain.rent.domain.RentRepository;
import com.example.library.domain.rent.domain.dto.RentStatusResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class RentServiceImplTest {

    @InjectMocks
    private RentServiceImpl rentService;

    @Mock
    private RentRepository rentRepository;

    @Test
    void 유저_대여현황_가져오기(){
        RentStatusResponseDto.Response response1 = new RentStatusResponseDto.Response(1L,"test1","20240329","20240401",true);
        RentStatusResponseDto.Response response2 = new RentStatusResponseDto.Response(1L,"test1","20240329","20240401",true);
        List<RentStatusResponseDto.Response> list = Arrays.asList(response1,response2);

        //given
        when(rentRepository.findUserRentStatus(1L)).thenReturn(list);

        List<RentStatusResponseDto.Response> selectedList = rentService.getCurrentRentStatus(1L);
        System.out.println(list.get(0));
        System.out.println(list.get(1));
        Assertions.assertEquals(selectedList.size(),2);

    }
}