package com.youn.intly.comment.domain.repository;

import com.youn.intly.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c where c.id = :id")
    Optional<Comment> findCommentById(@Param("id") Long id);
}