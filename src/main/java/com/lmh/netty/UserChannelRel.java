package com.lmh.netty;

import io.netty.channel.Channel;

import java.util.HashMap;

/**
 * @ClassName: UserChannelRel
 * @Description: 用户 id 和 channel 的关联关系处理
 * @author: ALin
 * @date: 2020/4/1 上午10:54
 */
public class UserChannelRel {
    private static HashMap<String, Channel> manager = new HashMap<>();

    public static void put(String senderId, Channel channel) {
        manager.put(senderId, channel);
    }

    public static Channel get(String senderId) {
        return manager.get(senderId);
    }

    public static void output() {
        for (HashMap.Entry<String, Channel> entry : manager.entrySet()) {
            System.out.println("UserId: " + entry.getKey()
                    + ", ChannelId: " + entry.getValue().id().asLongText());
        }
    }
}