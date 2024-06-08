package com.example.library.domain.book.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBookImageEntity is a Querydsl query type for BookImageEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBookImageEntity extends EntityPathBase<BookImageEntity> {

    private static final long serialVersionUID = -2135431606L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBookImageEntity bookImageEntity = new QBookImageEntity("bookImageEntity");

    public final com.example.library.global.entityListener.Entity.QBaseEntity _super = new com.example.library.global.entityListener.Entity.QBaseEntity(this);

    public final QBookEntity book;

    //inherited
    public final StringPath createdDt = _super.createdDt;

    //inherited
    public final StringPath createdTm = _super.createdTm;

    public final StringPath filePath = createString("filePath");

    public final NumberPath<Long> fileSize = createNumber("fileSize", Long.class);

    public final NumberPath<Long> imgNo = createNumber("imgNo", Long.class);

    public final StringPath newFileName = createString("newFileName");

    public final StringPath originalFileName = createString("originalFileName");

    public QBookImageEntity(String variable) {
        this(BookImageEntity.class, forVariable(variable), INITS);
    }

    public QBookImageEntity(Path<? extends BookImageEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBookImageEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBookImageEntity(PathMetadata metadata, PathInits inits) {
        this(BookImageEntity.class, metadata, inits);
    }

    public QBookImageEntity(Class<? extends BookImageEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.book = inits.isInitialized("book") ? new QBookEntity(forProperty("book"), inits.get("book")) : null;
    }

}

