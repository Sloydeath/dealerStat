package com.leverx.controller;

import com.leverx.error.exception.GameObjectNotFoundException;
import com.leverx.error.exception.UserNotFoundException;
import com.leverx.model.GameObject;
import com.leverx.model.User;
import com.leverx.service.GameObjectService;
import com.leverx.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/traders")
public class TraderController {

    private static final Logger log = Logger.getLogger(TraderController.class);
    private final UserService userService;
    private final GameObjectService gameObjectService;

    @Autowired
    public TraderController(UserService userService, GameObjectService gameObjectService) {
        this.userService = userService;
        this.gameObjectService = gameObjectService;
    }

    @PutMapping("/objects/{id}")
    public ResponseEntity<?> updateObjectById(@RequestBody GameObject newGameObject, @PathVariable Long id, Principal principal) {
        User authUser = userService.findUserByEmail(principal.getName());
        if (gameObjectService.findAll().stream().anyMatch(go -> go.getUser().equals(authUser) && go.getId().equals(id))) {
            GameObject gameObject = gameObjectService.findGameObjectById(id);
            if (gameObject != null && gameObject.getId() != null) {
                gameObject.setText(newGameObject.getText());
                gameObject.setTitle(newGameObject.getTitle());
                gameObject.setUpdatedAt(LocalDateTime.now());
                boolean updated = gameObjectService.update(gameObject);
                return updated
                        ? new ResponseEntity<>(HttpStatus.OK)
                        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                log.debug("In method PUT/updateObjectById: Game Object not found");
                throw new GameObjectNotFoundException("Game Object not found");
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/objects")
    public ResponseEntity<?> saveNewGameObject(@RequestBody GameObject newGameObject, Principal principal) {
        User user = userService.findUserByEmail(principal.getName());
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
            log.debug("In method POST/saveNewGameObject: User is not found");
            throw new UserNotFoundException("User is not found");
        }
    }

    @DeleteMapping("/objects/{id}")
    public ResponseEntity<?> deleteGameObject(@PathVariable Long id, Principal principal) {
        User authUser = userService.findUserByEmail(principal.getName());
        if (gameObjectService.findAll().stream().anyMatch(go -> go.getUser().equals(authUser) && go.getId().equals(id))) {
            boolean deleted = gameObjectService.deleteGameObjectById(id);
            return deleted
                    ? new ResponseEntity<>(HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/objects/my")
    public ResponseEntity<List<GameObject>> getAllGameObjectsOfAuthTrader(Principal principal) {
        User user = userService.findUserByEmail(principal.getName());
        List<GameObject> gameObjects = gameObjectService.findAllByTraderId(user.getId());
        return gameObjects != null && !gameObjects.isEmpty()
                ? new ResponseEntity<>(gameObjects, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
