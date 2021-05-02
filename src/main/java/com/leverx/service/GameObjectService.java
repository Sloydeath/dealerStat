package com.leverx.service;

import com.leverx.model.GameObject;

import java.util.List;

/**
 * Interface of game object service
 *
 * @author Andrew Panas
 */

public interface GameObjectService {
    void save(GameObject gameObject);
    boolean update(GameObject gameObject);
    List<GameObject> findAll();
    GameObject findGameObjectById(Long id);
    List<GameObject> findAllByTraderId(Long id);
    boolean deleteGameObjectById(Long id);
}
