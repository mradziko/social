package com.airnauts.kaktus.utils;

import de.greenrobot.event.EventBus;

public class EventBusManager {
    public static EventBus DEFAULT_CHANNEL;
    public static EventBus REFRESH_CHANNEL;

    public synchronized static void initialize() {
        DEFAULT_CHANNEL = EventBus.getDefault();
        REFRESH_CHANNEL = EventBus.builder().build();
    }
}
