package com.silencezwm.androidimbysocketio;

import android.app.Application;

import com.silencezwm.androidimbysocketio.chatroom.MainChatRoom;

/**
 * @author silencezwm on 2017/8/25 上午11:16
 * @email silencezwm@gmail.com
 * @description App
 */
public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        MainChatRoom.init();
    }

}
