package com.silencezwm.androidimbysocketio.listener;

/**
 * @author silencezwm on 2017/8/25 上午11:07
 * @email silencezwm@gmail.com
 * @description 发射器接口
 */
public interface IEmitterListener {

    /**
     * 监听结果
     *
     * @param key
     * @param args
     */
    void emitterListenerResut(String key, Object... args);

    /**
     * 请求结果
     *
     * @param key
     * @param args
     */
    void requestSocketResult(String key, Object... args);

}
