package com.leverx.service.intefaces;

import java.util.List;

import com.leverx.model.Comment;

public interface CommentService {
    void save(Comment comment);
    boolean deleteById(Long id);
    boolean update(Comment comment);
    List<Comment> findAllNotApproved();
    List<Comment> findAllByTraderIdAndApproved(Long id);
    Comment findById(Long id);
}
