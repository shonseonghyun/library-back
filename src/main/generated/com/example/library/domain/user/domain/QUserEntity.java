package com.example.library.domain.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserEntity is a Querydsl query type for UserEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserEntity extends EntityPathBase<UserEntity> {

    private static final long serialVersionUID = 1230583547L;

    public static final QUserEntity userEntity = new QUserEntity("userEntity");

    public final com.example.library.global.entityListener.Entity.QModifiedEntity _super = new com.example.library.global.entityListener.Entity.QModifiedEntity(this);

    //inherited
    public final StringPath createdDt = _super.createdDt;

    //inherited
    public final StringPath createdTm = _super.createdTm;

    public final StringPath gender = createString("gender");

    //inherited
    public final StringPath modifiedDt = _super.modifiedDt;

    //inherited
    public final StringPath modifiedTm = _super.modifiedTm;

    public final EnumPath<com.example.library.domain.user.enums.SocialLoginType> provider = createEnum("provider", com.example.library.domain.user.enums.SocialLoginType.class);

    public final StringPath providerId = createString("providerId");

    public final StringPath refreshToken = createString("refreshToken");

    public final StringPath tel = createString("tel");

    public final NumberPath<Integer> useFlg = createNumber("useFlg", Integer.class);

    public final StringPath userEmail = createString("userEmail");

    public final EnumPath<com.example.library.domain.user.enums.UserGrade> userGrade = createEnum("userGrade", com.example.library.domain.user.enums.UserGrade.class);

    public final StringPath userId = createString("userId");

    public final StringPath userName = createString("userName");

    public final NumberPath<Long> userNo = createNumber("userNo", Long.class);

    public final StringPath userPwd = createString("userPwd");

    public QUserEntity(String variable) {
        super(UserEntity.class, forVariable(variable));
    }

    public QUserEntity(Path<? extends UserEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserEntity(PathMetadata metadata) {
        super(UserEntity.class, metadata);
    }

}

