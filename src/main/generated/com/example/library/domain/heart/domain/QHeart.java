package com.example.library.domain.heart.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QHeart is a Querydsl query type for Heart
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHeart extends EntityPathBase<Heart> {

    private static final long serialVersionUID = -661964438L;

    public static final QHeart heart = new QHeart("heart");

    public final com.example.library.global.entityListener.Entity.QBaseEntity _super = new com.example.library.global.entityListener.Entity.QBaseEntity(this);

    public final NumberPath<Long> bookNo = createNumber("bookNo", Long.class);

    //inherited
    public final StringPath createdDt = _super.createdDt;

    //inherited
    public final StringPath createdTm = _super.createdTm;

    public final NumberPath<Long> heartNo = createNumber("heartNo", Long.class);

    public final NumberPath<Long> userNo = createNumber("userNo", Long.class);

    public QHeart(String variable) {
        super(Heart.class, forVariable(variable));
    }

    public QHeart(Path<? extends Heart> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHeart(PathMetadata metadata) {
        super(Heart.class, metadata);
    }

}

