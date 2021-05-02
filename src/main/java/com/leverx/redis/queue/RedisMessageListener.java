package com.leverx.redis.queue;

import org.apache.log4j.Logger;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrew Panas
 */

@Service
public class RedisMessageListener implements MessageListener {
    private static final Logger log = Logger.getLogger(RedisMessageListener.class);
    public static List<String> messageList = new ArrayList<>();

    public void onMessage(final Message message, final byte[] paramArrayOfByte) {
        messageList.add(message.toString());
        log.info("Received by RedisMessageListener: " + new String(message.getBody()));
    }
}
