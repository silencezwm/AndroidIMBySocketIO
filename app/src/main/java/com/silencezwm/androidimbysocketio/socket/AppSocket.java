package com.silencezwm.androidimbysocketio.socket;

import com.silencezwm.androidimbysocketio.listener.IConstants;

/**
 * @author silencezwm on 2017/8/25 上午11:12
 * @email silencezwm@gmail.com
 * @description AppSocket
 */
public class AppSocket extends BaseSocket {

    private static volatile AppSocket INSTANCE = null;

    public static AppSocket getInstance() {
        if (INSTANCE == null) {
            throw new NullPointerException("must first call the build() method");
        }
        return INSTANCE;
    }

    public static AppSocket init(Builder builder) {
        return new AppSocket(builder);
    }

    private AppSocket(Builder builder) {
        super(builder);
        INSTANCE = this;
    }

    /**
     * 增加用户
     *
     * @param username
     */
    public void addUser(String username) {
        mSocket.emit(IConstants.ADD_USER, username);
    }

    /**
     * 发送消息
     *
     * @param content
     */
    public void sendMessage(String content) {
        mSocket.emit(IConstants.NEW_MESSAGE, content);
    }

    /**
     * 输入中
     */
    public void typing() {
        mSocket.emit(IConstants.TYPING);
    }

    /**
     * 停止输入
     */
    public void stopTyping() {
        mSocket.emit(IConstants.STOP_TYPING);
    }

}
