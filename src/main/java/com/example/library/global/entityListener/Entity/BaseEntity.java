package com.example.library.global.entityListener.Entity;

import com.example.library.global.entityListener.BasedDateFormatListener;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(BasedDateFormatListener.class)
public class BaseEntity {
    @Column(updatable = false)
    private String createdDt;

    @Column(updatable = false)
    private String createdTm;
}
