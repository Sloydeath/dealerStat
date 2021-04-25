package com.leverx.controller;

import com.leverx.exception.GameObjectNotFoundException;
import com.leverx.exception.UserNotFoundException;
import com.leverx.model.Comment;
import com.leverx.model.GameObject;
import com.leverx.model.User;
import com.leverx.service.intefaces.GameObjectService;
import com.leverx.service.intefaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class GameObjectController {

    private final GameObjectService gameObjectService;
    private final UserService userService;

    @Autowired
    public GameObjectController(GameObjectService gameObjectService, UserService userService) {
        this.gameObjectService = gameObjectService;
        this.userService = userService;
    }

    @GetMapping("/objects")
    public ResponseEntity<List<GameObject>> getAllObjects() {
        List<GameObject> gameObjects = gameObjectService.findAll();
        return gameObjects != null && !gameObjects.isEmpty()
                ? new ResponseEntity<>(gameObjects, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/traders/objects/{id}")
    public ResponseEntity<?> updateObjectById(@RequestBody GameObject newGameObject, @PathVariable Long id) {
        GameObject gameObject = gameObjectService.findGameObjectById(id);
        if (gameObject != null) {
            gameObject.setText(newGameObject.getText());
            gameObject.setTitle(newGameObject.getTitle());
            gameObject.setUpdatedAt(LocalDateTime.now());
            boolean updated = gameObjectService.update(gameObject);
            return updated
                    ? new ResponseEntity<>(HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        else {
            throw new GameObjectNotFoundException(id);
        }
    }

    @PostMapping("/traders/{id}/objects")
    public ResponseEntity<?> saveNewGameObject(@RequestBody GameObject newGameObject, @PathVariable Long id) {
        User user = userService.findUserById(id);
        GameObject gameObject = new GameObject();
        if (user != null) {
            gameObject.setTitle(newGameObject.getTitle());
            gameObject.setText(newGameObject.getText());
            gameObject.setCreatedAt(LocalDateTime.now());
            gameObject.setUser(user);
            gameObjectService.save(gameObject);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else {
            throw new UserNotFoundException(id);
        }
    }

    @DeleteMapping("/traders/objects/{id}")
    public ResponseEntity<?> deleteGameObject(@PathVariable Long id) {
        boolean deleted = gameObjectService.deleteGameObjectById(id);
        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping("/objects/users/{id}")
    public ResponseEntity<List<GameObject>> getAllObjectsByTraderId(@PathVariable Long id) {
        List<GameObject> gameObjects = gameObjectService.findAllByTraderId(id);
        return gameObjects != null && !gameObjects.isEmpty()
                ? new ResponseEntity<>(gameObjects, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
