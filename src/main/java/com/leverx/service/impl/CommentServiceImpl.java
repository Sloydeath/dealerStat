package com.leverx.service.impl;

import com.leverx.model.Comment;
import com.leverx.repository.CommentRepository;
import com.leverx.service.CommentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private static final Logger log = Logger.getLogger(CommentServiceImpl.class);
    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public boolean deleteById(Long id) {
        if (commentRepository.findById(id).isPresent()) {
            commentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Comment comment) {
        if (commentRepository.findById(comment.getId()).isPresent()) {
            commentRepository.save(comment);
            return true;
        }
        else {
            log.info("--- No such comment in database ---");
            return false;
        }
    }

    @Override
    public List<Comment> findAllNotApproved() {
        return commentRepository.findAllNotApproved();
    }

    @Override
    public List<Comment> findAllApprovedByTraderId(Long id) {
        return commentRepository.findByTraderIdAndApproved(id);
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id).orElse(new Comment());
    }
}
