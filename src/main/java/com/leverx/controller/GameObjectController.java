package com.leverx.controller;

import com.leverx.model.GameObject;
import com.leverx.service.GameObjectService;
import com.leverx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GameObjectController {

    private final GameObjectService gameObjectService;

    @Autowired
    public GameObjectController(GameObjectService gameObjectService) {
        this.gameObjectService = gameObjectService;
    }

    @GetMapping("/objects")
    public ResponseEntity<List<GameObject>> getAllObjects() {
        List<GameObject> gameObjects = gameObjectService.findAll();
        return gameObjects != null && !gameObjects.isEmpty()
                ? new ResponseEntity<>(gameObjects, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/objects/users/{id}")
    public ResponseEntity<List<GameObject>> getAllObjectsByTraderId(@PathVariable Long id) {
        List<GameObject> gameObjects = gameObjectService.findAllByTraderId(id);
        return gameObjects != null && !gameObjects.isEmpty()
                ? new ResponseEntity<>(gameObjects, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
