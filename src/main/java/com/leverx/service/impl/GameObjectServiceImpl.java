package com.leverx.service.impl;

import com.leverx.model.GameObject;
import com.leverx.repository.GameObjectRepository;
import com.leverx.service.GameObjectService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * This is a service class of Game Objects
 *
 * @author Andrew Panas
 */

@Service
@Transactional
public class GameObjectServiceImpl implements GameObjectService {

    private static final Logger log = Logger.getLogger(GameObjectServiceImpl.class);
    private final GameObjectRepository gameObjectRepository;

    @Autowired
    public GameObjectServiceImpl(GameObjectRepository gameObjectRepository) {
        this.gameObjectRepository = gameObjectRepository;
    }

    @Override
    public void save(GameObject gameObject) {
        gameObjectRepository.save(gameObject);
    }

    @Override
    public boolean update(GameObject gameObject) {
        if (gameObjectRepository.findById(gameObject.getId()).isPresent()) {
            gameObjectRepository.save(gameObject);
            return true;
        }
        else {
            log.info("In method updateGameObject: No such Game Object in database");
            return false;
        }
    }

    @Override
    public List<GameObject> findAll() {
        return gameObjectRepository.findAll();
    }

    @Override
    public GameObject findGameObjectById(Long id) {
        return gameObjectRepository.findById(id).orElse(new GameObject());
    }

    @Override
    public List<GameObject> findAllByTraderId(Long id) {
        return gameObjectRepository.findAllByTraderId(id);
    }

    @Override
    public boolean deleteGameObjectById(Long id) {
        if (gameObjectRepository.findById(id).isPresent()) {
            gameObjectRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
