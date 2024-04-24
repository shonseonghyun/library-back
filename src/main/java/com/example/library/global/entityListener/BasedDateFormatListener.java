package com.example.library.global.entityListener;

import com.example.library.global.entityListener.Entity.BaseEntity;
import com.example.library.global.utils.DateUtil;
import jakarta.persistence.PrePersist;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BasedDateFormatListener {

    @PrePersist
    public void prePersist(Object obj){
        BaseEntity baseEntity = (BaseEntity) obj;
        baseEntity.setCreatedDt(DateUtil.getDate());
        baseEntity.setCreatedTm(DateUtil.getTime());
    }
}
