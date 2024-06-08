package com.example.library.domain.review.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReviewEntity is a Querydsl query type for ReviewEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewEntity extends EntityPathBase<ReviewEntity> {

    private static final long serialVersionUID = -391649515L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReviewEntity reviewEntity = new QReviewEntity("reviewEntity");

    public final com.example.library.global.entityListener.Entity.QModifiedEntity _super = new com.example.library.global.entityListener.Entity.QModifiedEntity(this);

    public final com.example.library.domain.book.domain.QBookEntity book;

    //inherited
    public final StringPath createdDt = _super.createdDt;

    //inherited
    public final StringPath createdTm = _super.createdTm;

    //inherited
    public final StringPath modifiedDt = _super.modifiedDt;

    //inherited
    public final StringPath modifiedTm = _super.modifiedTm;

    public final StringPath reviewContent = createString("reviewContent");

    public final NumberPath<Long> reviewNo = createNumber("reviewNo", Long.class);

    public final com.example.library.domain.user.domain.QUserEntity user;

    public QReviewEntity(String variable) {
        this(ReviewEntity.class, forVariable(variable), INITS);
    }

    public QReviewEntity(Path<? extends ReviewEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReviewEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReviewEntity(PathMetadata metadata, PathInits inits) {
        this(ReviewEntity.class, metadata, inits);
    }

    public QReviewEntity(Class<? extends ReviewEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.book = inits.isInitialized("book") ? new com.example.library.domain.book.domain.QBookEntity(forProperty("book"), inits.get("book")) : null;
        this.user = inits.isInitialized("user") ? new com.example.library.domain.user.domain.QUserEntity(forProperty("user")) : null;
    }

}

