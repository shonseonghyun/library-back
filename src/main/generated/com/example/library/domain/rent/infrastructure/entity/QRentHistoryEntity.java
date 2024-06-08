package com.example.library.domain.rent.infrastructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRentHistoryEntity is a Querydsl query type for RentHistoryEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRentHistoryEntity extends EntityPathBase<RentHistoryEntity> {

    private static final long serialVersionUID = 1522378863L;

    public static final QRentHistoryEntity rentHistoryEntity = new QRentHistoryEntity("rentHistoryEntity");

    public final NumberPath<Long> bookNo = createNumber("bookNo", Long.class);

    public final BooleanPath extensionFlg = createBoolean("extensionFlg");

    public final StringPath haveToReturnDt = createString("haveToReturnDt");

    public final NumberPath<Long> historyNo = createNumber("historyNo", Long.class);

    public final NumberPath<Long> managerNo = createNumber("managerNo", Long.class);

    public final StringPath rentDt = createString("rentDt");

    public final EnumPath<com.example.library.domain.rent.enums.RentState> rentState = createEnum("rentState", com.example.library.domain.rent.enums.RentState.class);

    public final StringPath returnDt = createString("returnDt");

    public final NumberPath<Long> userNo = createNumber("userNo", Long.class);

    public QRentHistoryEntity(String variable) {
        super(RentHistoryEntity.class, forVariable(variable));
    }

    public QRentHistoryEntity(Path<? extends RentHistoryEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRentHistoryEntity(PathMetadata metadata) {
        super(RentHistoryEntity.class, metadata);
    }

}

