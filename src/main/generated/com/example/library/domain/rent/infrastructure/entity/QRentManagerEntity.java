package com.example.library.domain.rent.infrastructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRentManagerEntity is a Querydsl query type for RentManagerEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRentManagerEntity extends EntityPathBase<RentManagerEntity> {

    private static final long serialVersionUID = 1626758344L;

    public static final QRentManagerEntity rentManagerEntity = new QRentManagerEntity("rentManagerEntity");

    public final NumberPath<Integer> currentRentNumber = createNumber("currentRentNumber", Integer.class);

    public final NumberPath<Long> managerNo = createNumber("managerNo", Long.class);

    public final BooleanPath overdueFlg = createBoolean("overdueFlg");

    public final NumberPath<Long> userNo = createNumber("userNo", Long.class);

    public QRentManagerEntity(String variable) {
        super(RentManagerEntity.class, forVariable(variable));
    }

    public QRentManagerEntity(Path<? extends RentManagerEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRentManagerEntity(PathMetadata metadata) {
        super(RentManagerEntity.class, metadata);
    }

}

