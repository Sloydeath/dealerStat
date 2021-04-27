package com.leverx.redis.queue;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RedisMessageListener implements MessageListener {
    public static List<String> messageList = new ArrayList<String>();

    public void onMessage(final Message message, final byte[] paramArrayOfByte) {
        messageList.add(message.toString());
        System.out.println("Received by RedisMessageListener: " + new String(message.getBody()));
    }
}
