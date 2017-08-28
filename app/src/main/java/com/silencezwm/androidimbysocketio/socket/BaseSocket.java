package com.silencezwm.androidimbysocketio.socket;

import android.text.TextUtils;

import com.silencezwm.androidimbysocketio.listener.IEmitterListener;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.engineio.client.transports.WebSocket;


/**
 * @author silencezwm on 2017/8/25 上午11:02
 * @email silencezwm@gmail.com
 * @description Socket基类
 */
public class BaseSocket {

    Socket mSocket = null;

    // socket是否初始化
    private boolean isSocketInit = false;

    private EmitterEvent mEmitterEvent;

    IEmitterListener mIEmitterListener;

    BaseSocket(Builder builder) {
        if (isSocketInit) return;

        IO.Options options = new IO.Options();
        options.timeout = builder.timeout;
        options.reconnection = builder.reconnection;
        options.reconnectionAttempts = builder.reconnectionAttempts;
        options.reconnectionDelay = builder.reconnectionDelay;
        options.reconnectionDelayMax = builder.reconnectionDelayMax;
        options.forceNew = builder.forceNew;
        options.transports = builder.transports;
        try {
            mSocket = IO.socket(builder.socketHost, options);
            mIEmitterListener = builder.emitterListener;
            initEmitterEvent(mIEmitterListener);
            isSocketInit = true;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            isSocketInit = false;
        }
    }

    private void initEmitterEvent(IEmitterListener emitterListener) {
        if (mEmitterEvent == null) {
            mEmitterEvent = new EmitterEvent();
        }
        mEmitterEvent.onEmitterEvent(mSocket, emitterListener);
    }

    public boolean isConnected() {
        return mSocket.connected();
    }

    public void connect() {
        if (socketIsNotNullAndInit()) {
            mSocket.connect();
        }
    }

    public void disConnnect() {
        if (socketIsNotNullAndInit()) {
            mSocket.disconnect();
            offEmitterListener();
        }
    }

    private void offEmitterListener() {
        mEmitterEvent.offEmitterEvent(mSocket);
    }

    public void close() {
        if (socketIsNotNullAndInit()) {
            mSocket.close();
            isSocketInit = false;
        }
    }

    private boolean socketIsNotNullAndInit() {
        return mSocket != null && isSocketInit;
    }


    public static class Builder {

        private String[] transports = new String[]{WebSocket.NAME};

        // 连接超时时间
        private int timeout = -1;

        // 是否自动重连
        private boolean reconnection = true;

        // 重连尝试次数
        private int reconnectionAttempts = 100;

        // 重连间隔
        private int reconnectionDelay = 3000;

        // 最大连接等待时间
        private int reconnectionDelayMax = 3000;

        private boolean forceNew = false;

        private IEmitterListener emitterListener;

        // Socket服务器地址
        private String socketHost = null;

        public Builder(String socketHost) {
            if (TextUtils.isEmpty(socketHost)) {
                throw new NullPointerException("socketHost not allow is null");
            }
            this.socketHost = socketHost;

        }

        public Builder timeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        public Builder reconnection(boolean reconnection) {
            this.reconnection = reconnection;
            return this;
        }

        public Builder reconnectionAttempts(int reconnectionAttempts) {
            this.reconnectionAttempts = reconnectionAttempts;
            return this;
        }

        public Builder reconnectionDelay(int reconnectionDelay) {
            this.reconnectionDelay = reconnectionDelay;
            return this;
        }

        public Builder reconnectionDelayMax(int reconnectionDelayMax) {
            this.reconnectionDelayMax = reconnectionDelayMax;
            return this;
        }

        public Builder forceNew(boolean forceNew) {
            this.forceNew = forceNew;
            return this;
        }

        public Builder setEmitterListener(IEmitterListener emitterListener) {
            this.emitterListener = emitterListener;
            return this;
        }

        public BaseSocket build() {
            return new BaseSocket(this);
        }

    }


}
