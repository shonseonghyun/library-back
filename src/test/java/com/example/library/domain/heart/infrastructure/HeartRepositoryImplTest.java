package com.example.library.domain.heart.infrastructure;

import com.example.library.config.QuerydslConfig;
import com.example.library.domain.heart.domain.Heart;
import com.example.library.domain.heart.domain.HeartRepository;
import com.example.library.domain.heart.domain.dto.HeartResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Repository;

import java.util.List;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class)) //위에서 말했듯이
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QuerydslConfig.class)
class HeartRepositoryImplTest {

    @Autowired
    HeartRepository heartRepository;

    @Test
    void 저장테스트(){
        Heart heart= Heart.builder()
                        .userNo(1L)
                                .build();
        Heart saved= heartRepository.save(heart);
//        List<HeartResponseDto.Response> responseList = heartRepository.findHeartsByUserNo(1L);
        Assertions.assertEquals(saved.getHeartNo(),heart.getHeartNo());
//        Assertions.assertEquals(responseList.size(),1);

    }


}