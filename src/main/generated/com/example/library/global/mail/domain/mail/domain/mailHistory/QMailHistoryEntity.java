package com.example.library.global.mail.domain.mail.domain.mailHistory;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMailHistoryEntity is a Querydsl query type for MailHistoryEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMailHistoryEntity extends EntityPathBase<MailHistoryEntity> {

    private static final long serialVersionUID = -1527173470L;

    public static final QMailHistoryEntity mailHistoryEntity = new QMailHistoryEntity("mailHistoryEntity");

    public final com.example.library.global.entityListener.Entity.QBaseEntity _super = new com.example.library.global.entityListener.Entity.QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final StringPath createdDt = _super.createdDt;

    //inherited
    public final StringPath createdTm = _super.createdTm;

    public final StringPath email = createString("email");

    public final StringPath flg = createString("flg");

    public final NumberPath<Long> historyNo = createNumber("historyNo", Long.class);

    public final EnumPath<com.example.library.global.mail.domain.mail.enums.MailType> mailType = createEnum("mailType", com.example.library.global.mail.domain.mail.enums.MailType.class);

    public final NumberPath<Long> userNo = createNumber("userNo", Long.class);

    public QMailHistoryEntity(String variable) {
        super(MailHistoryEntity.class, forVariable(variable));
    }

    public QMailHistoryEntity(Path<? extends MailHistoryEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMailHistoryEntity(PathMetadata metadata) {
        super(MailHistoryEntity.class, metadata);
    }

}

