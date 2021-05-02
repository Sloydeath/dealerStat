package com.leverx.controller;

import com.leverx.error.exception.CommentNotFoundException;
import com.leverx.model.Comment;
import com.leverx.model.User;
import com.leverx.service.CommentService;
import com.leverx.service.GameObjectService;
import com.leverx.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller for admin's URL.
 * Check src/main/resources/postman/Dealer_stat.postman_collection.json for more detail URL mapping.
 *
 * @author Andrew Panas
 */

@RestController
@RequestMapping("/admins")
public class AdminController {

    private static final Logger log = Logger.getLogger(AdminController.class);
    private final CommentService commentService;
    private final UserService userService;
    private final GameObjectService gameObjectService;

    @Autowired
    public AdminController(CommentService commentService, UserService userService, GameObjectService gameObjectService) {
        this.commentService = commentService;
        this.userService = userService;
        this.gameObjectService = gameObjectService;
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getAllNotActiveUsers() {
        List<User> users = userService.getAllNotActive();
        return users != null && !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Comment>> getAllNotApprovedComments() {
        List<Comment> comments = commentService.findAllNotApproved();
        return comments != null && !comments.isEmpty()
                ? new ResponseEntity<>(comments, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/comments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateComment(@RequestBody Comment newComment, @PathVariable Long id) {
        Comment comment = commentService.findById(id);
        if (comment != null && comment.getId() != null) {
            comment.setApproved(newComment.isApproved());
            comment.setMessage(newComment.getMessage());
            comment.setCreatedAt(LocalDateTime.now());
            boolean updated = commentService.update(comment);
            return updated
                    ? new ResponseEntity<>(HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        else {
            log.debug("In method POST/updateComment: Comment not found");
            throw new CommentNotFoundException("Comment not found");
        }
    }

    @DeleteMapping(value = "/comments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        boolean deleted = commentService.deleteById(id);
        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUserById(id);
        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/objects/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteGameObject(@PathVariable Long id) {
        boolean deleted = gameObjectService.deleteGameObjectById(id);
        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
