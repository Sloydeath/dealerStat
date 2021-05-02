package com.leverx.redis.queue;

/**
 * @author Andrew Panas
 */

public interface MessagePublisher {
    void publish(final String message);
}
