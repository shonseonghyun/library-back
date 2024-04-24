package com.example.library.global.entityListener.Entity;

import com.example.library.global.entityListener.ModifiedDateFormatListener;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(ModifiedDateFormatListener.class)
public class ModifiedEntity extends BaseEntity {

    @Column
    private String modifiedDt;

    @Column
    private String modifiedTm;
}