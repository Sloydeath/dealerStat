package com.leverx.service.intefaces;

import com.leverx.model.GameObject;

import java.util.List;

public interface GameObjectService {
    void save(GameObject gameObject);
    boolean update(GameObject gameObject);
    List<GameObject> findAll();
    GameObject findGameObjectById(Long id);
    List<GameObject> findAllByTraderId(Long id);
    boolean deleteGameObjectById(Long id);
}
