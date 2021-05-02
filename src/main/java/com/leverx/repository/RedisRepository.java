package com.leverx.repository;

/**
 * Repository layer
 *
 * @author Andrew Panas
 */

public interface RedisRepository {
    void setHashcode(final String email, String hashcode);
    Object getHashcode(final String email);
    void deleteHashcode(final String email);
    boolean isExists(final String email);
}
