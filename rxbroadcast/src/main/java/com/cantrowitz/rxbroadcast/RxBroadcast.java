package com.cantrowitz.rxbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;

import rx.Observable;

/**
 * Created by adamcantrowitz on 9/1/15.
 */
public class RxBroadcast {

    private RxBroadcast() {
        throw new AssertionError("No instances");
    }

    /**
     * Create {@link Observable} that wraps {@link BroadcastReceiver} and emits received intents.
     *
     * @param context      the context the {@link BroadcastReceiver} will be created from
     * @param intentFilter the filter for the particular intent
     * @return {@link Observable} of {@link Intent} that matches the filter
     */
    public static Observable<Intent> fromBroadcast(Context context, IntentFilter intentFilter) {
        return Observable.create(new BroadcastProvider(new GlobalBroadcastProviderRegistration(intentFilter, context)));
    }

    /**
     * Create {@link Observable} that wraps {@link BroadcastReceiver} and emits received intents.
     *
     * @param context             the context the {@link BroadcastReceiver} will be created from
     * @param intentFilter        the filter for the particular intent
     * @param broadcastPermission String naming a permissions that a broadcaster must hold in order to send an Intent to you. If null, no permission is required.
     * @param handler             Handler identifying the thread that will receive the Intent. If null, the main thread of the process will be used.
     * @return {@link Observable} of {@link Intent} that matches the filter
     */
    public static Observable<Intent> fromBroadcast(Context context, IntentFilter intentFilter,
                                                   String broadcastPermission, Handler handler) {
        return Observable.create(new BroadcastProvider(new GlobalWPermissionsBroadcastProviderRegistration(intentFilter, context,
                broadcastPermission, handler)));
    }

    /**
     * Create {@link Observable} that wraps {@link BroadcastReceiver} and emits received intents.
     * <p/>
     * This uses a {@link LocalBroadcastManager}
     *
     * @param context      the context the {@link BroadcastReceiver} will be created from
     * @param intentFilter the filter for the particular intent
     * @return {@link Observable} of {@link Intent} that matches the filter
     */
    public static Observable<Intent> fromLocalBroadcast(Context context, IntentFilter intentFilter) {
        return Observable.create(new BroadcastProvider(new LocalBroadcastProviderRegistration(intentFilter,
                LocalBroadcastManager.getInstance(context))));
    }
}
