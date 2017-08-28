package com.silencezwm.androidimbysocketio.chatroom;

import android.util.Log;

import com.silencezwm.androidimbysocketio.bean.ObserverModel;
import com.silencezwm.androidimbysocketio.listener.IChatRoom;
import com.silencezwm.androidimbysocketio.listener.IConstants;
import com.silencezwm.androidimbysocketio.listener.IEventType;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;

import io.socket.client.Manager;
import io.socket.client.Socket;

/**
 * @author silencezwm on 2017/8/25 上午11:18
 * @email silencezwm@gmail.com
 * @description 基类聊天室
 */
public class BaseChatRoom extends Observable implements IChatRoom {

    private String TAG = BaseChatRoom.class.getSimpleName();

    BaseChatRoom() {

    }

    private void login(int numUsers) {
        setChanged();
        ObserverModel model = new ObserverModel();
        model.setEventType(IEventType.LOGIN);
        ObserverModel.Login login = new ObserverModel.Login();
        login.setNumUsers(numUsers);
        model.setLogin(login);
        notifyObservers(model);
    }

    private void newMessage(String username, String messsage) {
        setChanged();
        ObserverModel model = new ObserverModel();
        model.setEventType(IEventType.NEW_MESSAGE);
        ObserverModel.NewMessage newMessage = new ObserverModel.NewMessage();
        newMessage.setUsername(username);
        newMessage.setMessage(messsage);
        model.setNewMessage(newMessage);
        notifyObservers(model);
    }

    private void userJoined(String username, int numUsers) {
        setChanged();
        ObserverModel model = new ObserverModel();
        model.setEventType(IEventType.USER_JOINED);
        ObserverModel.UserJoined userJoined = new ObserverModel.UserJoined();
        userJoined.setUsername(username);
        userJoined.setNumUsers(numUsers);
        model.setUserJoined(userJoined);
        notifyObservers(model);
    }

    private void userLeft(String username, int numUsers) {
        setChanged();
        ObserverModel model = new ObserverModel();
        model.setEventType(IEventType.USER_LEFT);
        ObserverModel.UserLeft userLeft = new ObserverModel.UserLeft();
        userLeft.setUsername(username);
        userLeft.setNumUsers(numUsers);
        model.setUserLeft(userLeft);
        notifyObservers(model);
    }

    private void typing(String username) {
        setChanged();
        ObserverModel model = new ObserverModel();
        model.setEventType(IEventType.TYPING);
        ObserverModel.Typing typing = new ObserverModel.Typing();
        typing.setUsername(username);
        model.setTyping(typing);
        notifyObservers(model);
    }

    private void stopTyping(String username) {
        setChanged();
        ObserverModel model = new ObserverModel();
        model.setEventType(IEventType.STOP_TYPING);
        ObserverModel.StopTyping stopTyping = new ObserverModel.StopTyping();
        stopTyping.setUsername(username);
        model.setStopTyping(stopTyping);
        notifyObservers(model);
    }

    @Override
    public void emitterListenerResut(String key, Object... args) {
        switch (key) {
            case Manager.EVENT_TRANSPORT:

                break;

            case Socket.EVENT_CONNECT_ERROR:
                Log.e(TAG, "EVENT_CONNECT_ERROR");
                break;

            case Socket.EVENT_CONNECT_TIMEOUT:
                Log.e(TAG, "EVENT_CONNECT_TIMEOUT");
                break;

            // Socket连接成功
            case Socket.EVENT_CONNECT:

                break;

            // Socket断开连接
            case Socket.EVENT_DISCONNECT:
                break;

            // Socket连接错误
            case Socket.EVENT_ERROR:
                Log.e(TAG, "EVENT_ERROR");
                break;

            // Socket重新连接
            case Socket.EVENT_RECONNECT:
                Log.d(TAG, "EVENT_RECONNECT");
                break;

            case Socket.EVENT_RECONNECT_ATTEMPT:
                Log.e(TAG, "EVENT_RECONNECT_ATTEMPT");
                break;

            case Socket.EVENT_RECONNECT_ERROR:
                Log.e(TAG, "EVENT_RECONNECT_ERROR");
                break;

            case Socket.EVENT_RECONNECT_FAILED:
                Log.e(TAG, "EVENT_RECONNECT_FAILED");
                break;

            case Socket.EVENT_RECONNECTING:
                Log.e(TAG, "EVENT_RECONNECTING");
                break;

            case IConstants.LOGIN:
                JSONObject data = (JSONObject) args[0];

                int numUsers;
                try {
                    numUsers = data.getInt("numUsers");
                } catch (JSONException e) {
                    return;
                }

                login(numUsers);
                break;

            case IConstants.NEW_MESSAGE:
                JSONObject newMessage = (JSONObject) args[0];
                String newMsgUsername;
                String newMessageMsg;
                try {
                    newMsgUsername = newMessage.getString("username");
                    newMessageMsg = newMessage.getString("message");
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    return;
                }

                newMessage(newMsgUsername, newMessageMsg);
                break;

            case IConstants.USER_JOINED:
                JSONObject userJoined = (JSONObject) args[0];
                String userJoinedUsername;
                int userJoinedNumUsers;
                try {
                    userJoinedUsername = userJoined.getString("username");
                    userJoinedNumUsers = userJoined.getInt("numUsers");
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    return;
                }

                userJoined(userJoinedUsername, userJoinedNumUsers);
                break;

            case IConstants.USER_LEFT:
                JSONObject userLeft = (JSONObject) args[0];
                String userLeftUsername;
                int userLeftNumUsers;
                try {
                    userLeftUsername = userLeft.getString("username");
                    userLeftNumUsers = userLeft.getInt("numUsers");
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    return;
                }

                userLeft(userLeftUsername, userLeftNumUsers);
                break;

            case IConstants.TYPING:
                JSONObject typing = (JSONObject) args[0];
                String typingUsername;
                try {
                    typingUsername = typing.getString("username");
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    return;
                }

                typing(typingUsername);
                break;

            case IConstants.STOP_TYPING:
                JSONObject stopTyping = (JSONObject) args[0];
                String stopTypingUsername;
                try {
                    stopTypingUsername = stopTyping.getString("username");
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    return;
                }

                stopTyping(stopTypingUsername);
                break;

        }
    }

    @Override
    public void requestSocketResult(String key, Object... args) {

        switch (key) {

        }
    }


}
