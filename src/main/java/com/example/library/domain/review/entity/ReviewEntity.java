package com.example.library.domain.review.entity;

import com.example.library.domain.book.domain.BookEntity;
import com.example.library.domain.user.entity.UserEntity;
import com.example.library.global.entityListener.Entity.ModifiedEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "review")
public class ReviewEntity extends ModifiedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewNo;

    @ManyToOne(fetch = FetchType.LAZY
//            ,optional = false //userEntity가 없는 상황 가능
    )
    @JoinColumn(name="userNo")
    @NotFound(
            action = NotFoundAction.IGNORE)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookNo")
    private BookEntity book;


    @Column(nullable = false)
    private String reviewContent;
}
