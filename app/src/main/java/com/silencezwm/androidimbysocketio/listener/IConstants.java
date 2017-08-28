package com.silencezwm.androidimbysocketio.listener;

/**
 * @author silencezwm on 2017/8/25 上午11:03
 * @email silencezwm@gmail.com
 * @description 常量
 */
public interface IConstants {

    // 该地址为Socket.IO官方测试地址，实际项目中请更换
    String CHAT_SERVER_URL = "https://socketio-chat.now.sh/";


    // 登录
    String LOGIN = "login";
    // 新消息
    String NEW_MESSAGE = "new message";
    // 用户加入
    String USER_JOINED = "user joined";
    // 用户退出
    String USER_LEFT = "user left";
    // 输入中
    String TYPING = "typing";
    // 停止输入
    String STOP_TYPING = "stop typing";


    // 增加用户
    String ADD_USER = "add user";


    String USERNAME = "username";
    String NUMUSERS = "numUsers";



}
