package com.my.base.helper;

import org.greenrobot.eventbus.EventBus;

/**
 * @author caishuzhan
 */
public class EventBusHepler {

    public static void register(Object src) {
        if (!EventBus.getDefault().isRegistered(src)) {
            EventBus.getDefault().register(src);
        }
    }

    public static void unRegister(Object src) {
        if (EventBus.getDefault().isRegistered(src)) {
            EventBus.getDefault().unregister(src);
        }
    }

    public static void post(Object event) {
        if (event == null) return;
        EventBus.getDefault().post(event);
    }
}
