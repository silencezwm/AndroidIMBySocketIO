package com.silencezwm.androidimbysocketio.bean;


/**
 * @author silencezwm on 2017/8/25 下午12:06
 * @email silencezwm@gmail.com
 * @description 观察者实体类
 */
public class ObserverModel {

    private String mEventType;

    private Login mLogin;

    private NewMessage mNewMessage;

    private UserJoined mUserJoined;

    private UserLeft mUserLeft;

    private Typing mTyping;

    private StopTyping mStopTyping;

    public String getEventType() {
        return mEventType;
    }

    public void setEventType(String eventType) {
        mEventType = eventType;
    }

    public Login getLogin() {
        return mLogin;
    }

    public void setLogin(Login login) {
        mLogin = login;
    }


    public NewMessage getNewMessage() {
        return mNewMessage;
    }

    public void setNewMessage(NewMessage newMessage) {
        mNewMessage = newMessage;
    }

    public UserJoined getUserJoined() {
        return mUserJoined;
    }

    public void setUserJoined(UserJoined userJoined) {
        mUserJoined = userJoined;
    }

    public UserLeft getUserLeft() {
        return mUserLeft;
    }

    public void setUserLeft(UserLeft userLeft) {
        mUserLeft = userLeft;
    }

    public Typing getTyping() {
        return mTyping;
    }

    public void setTyping(Typing typing) {
        mTyping = typing;
    }

    public StopTyping getStopTyping() {
        return mStopTyping;
    }

    public void setStopTyping(StopTyping stopTyping) {
        mStopTyping = stopTyping;
    }

    public static class Login {
        private int numUsers;

        public int getNumUsers() {
            return numUsers;
        }

        public void setNumUsers(int numUsers) {
            this.numUsers = numUsers;
        }
    }

    public static class NewMessage{
        private String username;
        private String message;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class UserJoined{
        private String username;
        private int numUsers;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getNumUsers() {
            return numUsers;
        }

        public void setNumUsers(int numUsers) {
            this.numUsers = numUsers;
        }
    }

    public static class UserLeft{
        private String username;
        private int numUsers;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getNumUsers() {
            return numUsers;
        }

        public void setNumUsers(int numUsers) {
            this.numUsers = numUsers;
        }
    }

    public static class Typing{
        private String username;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    public static class StopTyping{
        private String username;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

}
