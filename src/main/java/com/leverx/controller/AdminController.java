package com.leverx.controller;

import com.leverx.error.exception.CommentNotFoundException;
import com.leverx.model.Comment;
import com.leverx.service.CommentService;
import com.leverx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private final CommentService commentService;
    private final UserService userService;

    @Autowired
    public AdminController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @GetMapping("/comments")
    public ResponseEntity<List<Comment>> getAllNotApprovedComments() {
        List<Comment> comments = commentService.findAllNotApproved();
        return comments != null && !comments.isEmpty()
                ? new ResponseEntity<>(comments, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/comments/{id}")
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

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        boolean deleted = commentService.deleteById(id);
        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUserById(id);
        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
