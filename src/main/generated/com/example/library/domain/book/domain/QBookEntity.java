package com.example.library.domain.book.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBookEntity is a Querydsl query type for BookEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBookEntity extends EntityPathBase<BookEntity> {

    private static final long serialVersionUID = -1314047177L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBookEntity bookEntity = new QBookEntity("bookEntity");

    public final com.example.library.global.entityListener.Entity.QModifiedEntity _super = new com.example.library.global.entityListener.Entity.QModifiedEntity(this);

    public final StringPath bookAuthor = createString("bookAuthor");

    public final NumberPath<Long> bookCode = createNumber("bookCode", Long.class);

    public final StringPath bookContent = createString("bookContent");

    public final QBookImageEntity bookImage;

    public final StringPath bookLocation = createString("bookLocation");

    public final StringPath bookName = createString("bookName");

    public final StringPath bookPublisher = createString("bookPublisher");

    public final EnumPath<com.example.library.domain.book.enums.BookState> bookState = createEnum("bookState", com.example.library.domain.book.enums.BookState.class);

    //inherited
    public final StringPath createdDt = _super.createdDt;

    //inherited
    public final StringPath createdTm = _super.createdTm;

    public final StringPath isbn = createString("isbn");

    //inherited
    public final StringPath modifiedDt = _super.modifiedDt;

    //inherited
    public final StringPath modifiedTm = _super.modifiedTm;

    public final StringPath pubDt = createString("pubDt");

    public QBookEntity(String variable) {
        this(BookEntity.class, forVariable(variable), INITS);
    }

    public QBookEntity(Path<? extends BookEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBookEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBookEntity(PathMetadata metadata, PathInits inits) {
        this(BookEntity.class, metadata, inits);
    }

    public QBookEntity(Class<? extends BookEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bookImage = inits.isInitialized("bookImage") ? new QBookImageEntity(forProperty("bookImage"), inits.get("bookImage")) : null;
    }

}

