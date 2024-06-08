package com.example.library.global.entityListener.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QModifiedEntity is a Querydsl query type for ModifiedEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QModifiedEntity extends EntityPathBase<ModifiedEntity> {

    private static final long serialVersionUID = 1731087815L;

    public static final QModifiedEntity modifiedEntity = new QModifiedEntity("modifiedEntity");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final StringPath createdDt = _super.createdDt;

    //inherited
    public final StringPath createdTm = _super.createdTm;

    public final StringPath modifiedDt = createString("modifiedDt");

    public final StringPath modifiedTm = createString("modifiedTm");

    public QModifiedEntity(String variable) {
        super(ModifiedEntity.class, forVariable(variable));
    }

    public QModifiedEntity(Path<? extends ModifiedEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QModifiedEntity(PathMetadata metadata) {
        super(ModifiedEntity.class, metadata);
    }

}

