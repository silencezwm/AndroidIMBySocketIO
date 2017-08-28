package com.silencezwm.androidimbysocketio.chatroom;


import com.silencezwm.androidimbysocketio.listener.IConstants;
import com.silencezwm.androidimbysocketio.socket.AppSocket;


/**
 * @author silencezwm on 2017/8/25 上午11:27
 * @email silencezwm@gmail.com
 * @description 主聊天室
 */
public class MainChatRoom extends BaseChatRoom {

    private static volatile MainChatRoom INSTANCE = null;

    public static MainChatRoom getInstance() {
        if (INSTANCE == null) {
            throw new NullPointerException("must first call the init() method");
        }
        return INSTANCE;
    }

    public static void init() {
        new MainChatRoom();
    }

    private MainChatRoom() {
        super();
        INSTANCE = this;
        initAppSocket();
    }

    /**
     * 初始化Socket
     */
    public void initAppSocket() {
        AppSocket.Builder builder = new AppSocket.Builder(IConstants.CHAT_SERVER_URL)
                .setEmitterListener(this);
        AppSocket.init(builder).connect();
    }

}
