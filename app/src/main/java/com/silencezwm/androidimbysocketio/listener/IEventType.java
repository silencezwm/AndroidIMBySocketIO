package com.silencezwm.androidimbysocketio.listener;


/**
 * @author silencezwm on 2017/8/25 下午12:08
 * @email silencezwm@gmail.com
 * @description
 *
 */
public interface IEventType {

    String LOGIN = "login";
    String ADD_USER = "addUser";
    String NEW_MESSAGE = "newMessage";
    String USER_JOINED = "userJoined";
    String USER_LEFT = "userLeft";
    String TYPING = "typing";
    String STOP_TYPING = "stopTyping";

}
