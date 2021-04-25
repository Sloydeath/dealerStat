package com.leverx.repository;

import com.leverx.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.user.id = :id")
    List<Comment> findByTraderId(@Param("id") Long id);

    @Query("SELECT c FROM Comment c WHERE c.user.id = :id AND c.approved = true")
    List<Comment> findByTraderIdAndApprovedTrue(@Param("id") Long id);

    @Query("SELECT c FROM Comment c WHERE c.approved = false")
    List<Comment> findAllNotApproved();
}
