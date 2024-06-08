package com.example.library.global.mail.domain.mail.domain.mailForm;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMailEntity is a Querydsl query type for MailEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMailEntity extends EntityPathBase<MailEntity> {

    private static final long serialVersionUID = -792908928L;

    public static final QMailEntity mailEntity = new QMailEntity("mailEntity");

    public final StringPath mailContent = createString("mailContent");

    public final NumberPath<Long> mailNo = createNumber("mailNo", Long.class);

    public final StringPath mailType = createString("mailType");

    public QMailEntity(String variable) {
        super(MailEntity.class, forVariable(variable));
    }

    public QMailEntity(Path<? extends MailEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMailEntity(PathMetadata metadata) {
        super(MailEntity.class, metadata);
    }

}

