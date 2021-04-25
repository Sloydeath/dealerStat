package com.leverx.controller;

import com.leverx.exception.CommentNotFoundException;
import com.leverx.exception.UserNotFoundException;
import com.leverx.model.Comment;
import com.leverx.model.User;
import com.leverx.service.intefaces.CommentService;
import com.leverx.service.intefaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    @Autowired
    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @GetMapping("/admins/comments")
    public ResponseEntity<List<Comment>> getAllNotApprovedComments() {
        List<Comment> comments = commentService.findAllNotApproved();
        return comments != null && !comments.isEmpty()
                ? new ResponseEntity<>(comments, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/admins/comments/{id}")
    public ResponseEntity<?> updateComment(@RequestBody Comment newComment, @PathVariable Long id) {
        Comment comment = commentService.findById(id);
        if (comment != null) {
            comment.setApproved(newComment.isApproved());
            comment.setMessage(newComment.getMessage());
            comment.setCreatedAt(LocalDateTime.now());
            boolean updated = commentService.update(comment);
            return updated
                    ? new ResponseEntity<>(HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        else {
            throw new CommentNotFoundException(id);
        }
    }

    @DeleteMapping("/admins/comments/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        boolean deleted = commentService.deleteById(id);
        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping("/users/{id}/comments")
    public ResponseEntity<List<Comment>> getAllCommentsByTraderIdAndApproved(@PathVariable Long id) {
        List<Comment> comments = commentService.findAllByTraderIdAndApproved(id);
        return comments != null && !comments.isEmpty()
                ? new ResponseEntity<>(comments, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("users/{id}/comments")
    public ResponseEntity<?> saveComment(@RequestBody Comment newComment, @PathVariable Long id) {
        User user = userService.findUserById(id);
        Comment comment = new Comment();
        if (user != null) {
            comment.setApproved(false);
            comment.setMessage(newComment.getMessage());
            comment.setCreatedAt(LocalDateTime.now());
            comment.setUser(user);
            commentService.save(comment);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else {
            throw new UserNotFoundException(id);
        }
    }
}
