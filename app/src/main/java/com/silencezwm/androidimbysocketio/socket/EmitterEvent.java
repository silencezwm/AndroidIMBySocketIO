package com.silencezwm.androidimbysocketio.socket;


import com.silencezwm.androidimbysocketio.listener.IConstants;
import com.silencezwm.androidimbysocketio.listener.IEmitterListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.socket.client.Manager;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * @author silencezwm on 2017/8/25 上午10:56
 * @email silencezwm@gmail.com
 * @description Socket 发射器事件处理类
 */
public class EmitterEvent {

    private Map<String, Emitter.Listener> emitterEventMap = new HashMap<>();

    public EmitterEvent() {
        emitterEventMap.put(Manager.EVENT_TRANSPORT, null);
        emitterEventMap.put(Socket.EVENT_CONNECT_ERROR, null);
        emitterEventMap.put(Socket.EVENT_CONNECT_TIMEOUT, null);
        emitterEventMap.put(Socket.EVENT_CONNECT, null);
        emitterEventMap.put(Socket.EVENT_DISCONNECT, null);
        emitterEventMap.put(Socket.EVENT_ERROR, null);
        emitterEventMap.put(Socket.EVENT_RECONNECT, null);
        emitterEventMap.put(Socket.EVENT_RECONNECT_ATTEMPT, null);
        emitterEventMap.put(Socket.EVENT_RECONNECT_ERROR, null);
        emitterEventMap.put(Socket.EVENT_RECONNECT_FAILED, null);
        emitterEventMap.put(Socket.EVENT_RECONNECTING, null);

        emitterEventMap.put(IConstants.LOGIN, null);
        emitterEventMap.put(IConstants.NEW_MESSAGE, null);
        emitterEventMap.put(IConstants.USER_JOINED, null);
        emitterEventMap.put(IConstants.USER_LEFT, null);
        emitterEventMap.put(IConstants.TYPING, null);
        emitterEventMap.put(IConstants.STOP_TYPING, null);

    }

    public void onEmitterEvent(Socket socket, final IEmitterListener emitterListener) {
        Iterator iterator = emitterEventMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            final String event = (String) entry.getKey();
            Emitter.Listener listener;
            listener = new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    emitterListener.emitterListenerResut(event, args);
                }
            };
            emitterEventMap.put(event, listener);
            socket.on(event, listener);

        }
    }

    public void offEmitterEvent(Socket socket) {
        Iterator iterator = emitterEventMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String event = (String) entry.getKey();
            Emitter.Listener el = (Emitter.Listener) entry.getValue();
            socket.off(event, el);
        }
    }

}
