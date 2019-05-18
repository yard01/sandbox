package com.github.yard01.sandbox.crib.service_crib.simpleservice;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import android.util.Log;

import com.github.yard01.sandbox.crib.service_crib.simpleservice.SimpleServiceTabContentCreator;

import java.util.concurrent.TimeUnit;

public class SimpleServiceCrib extends Service {
    public static final String LOG_TAG = "service_crib";
    public static int stopId = -1;

    public static int interval = 1;

    private boolean isStopped = false;

    public SimpleServiceCrib() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return new SimpleBinder(this);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate");
    }

    //private void
    void sendPendingIntent(PendingIntent pendingIntent, int requestCode) {
        if (pendingIntent == null) return;
        try {
            pendingIntent.send(requestCode);
            //pendingIntent.
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    void someTask(final Intent parentIntent) {
        // По умолчанию сервис работает в основном потоке,
        // поэтому фоновые задачи надо выполнять в отдельном потоке
        //Intent intent = new Intent(Activity. .BROADCAST_ACTION);
        final  Service service = this;
        new Thread( new Runnable() {
            public void run() {
                Intent intent = new Intent(SimpleServiceTabContentCreator.BROADCAST_ACTION); //обратная связь через броадкаст
                //if (pendingIntent();)

                PendingIntent pendingIntent = (parentIntent != null) ? (PendingIntent) parentIntent.getParcelableExtra(SimpleServiceTabContentCreator.PENDING_INTENT_KEY) : null;//parentIntent.getParcelableExtra(SimpleServiceTabContentCreator.PENDING_INTENT_KEY);

                intent.putExtra(SimpleServiceTabContentCreator.DATA_KEY, "Start Service Task");
                service.sendBroadcast(intent);
                if (pendingIntent != null)
                    sendPendingIntent(pendingIntent, 111); //отправка в Activity через PendingIntent

                for ( int i = 1; i <= 60; i++) {
                    if (isStopped) break;
                    Log.d(LOG_TAG, "hash: " + this.hashCode() + ", i = " + i);
                    try {
                        TimeUnit.SECONDS.sleep(interval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (stopId > 0) {
                        //stopSelf(stopId);
                        intent.putExtra(SimpleServiceTabContentCreator.DATA_KEY, "Attempt to stop the Service Task for id = " + stopId);
                        service.sendBroadcast(intent); //отправка через Broadcast

                        boolean stopResult = stopSelfResult(stopId); // аналогичен stopSelf(stopId);
                        intent.putExtra(SimpleServiceTabContentCreator.DATA_KEY, "the service stop result " + stopResult);
                        service.sendBroadcast(intent); //отправка через Broadcast

                        Log.d(LOG_TAG, "stop result: "  + stopResult);
                        stopId = -1;
                    }
                }
                intent.putExtra(SimpleServiceTabContentCreator.DATA_KEY, "Attempt to stop the Service by use stopSelf ");
                service.sendBroadcast(intent); //отправка через Broadcast
                stopSelf(); //Остановка сервиса
                if (pendingIntent != null)
                    sendPendingIntent(pendingIntent, 222); //отправка в Activity через PendingIntent

            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //данные в сервис передаются через intent, устанавливая intent.setExtra()
        //и могут быть прочитаны
        // , например, с помощью intent.getIntExtra(Ключ, Значение)

        Log.d(LOG_TAG, "onStartCommand, startId: " + startId + ", state stopped is " + isStopped +", flags = " + flags);
        someTask(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(LOG_TAG, "onDestroy");
        isStopped = true;
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(LOG_TAG, "onConfigurationMemory");
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        Log.d(LOG_TAG, "onLowMemory");
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        Log.d(LOG_TAG, "onTrimMemory");
        super.onTrimMemory(level);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(LOG_TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(LOG_TAG, "onRebind");
        super.onRebind(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d(LOG_TAG, "onTaskRemoved");
        super.onTaskRemoved(rootIntent);
    }

    public void incInterval() {
        interval++;
    }

    public void decInterval() {
        if (interval > 1) interval--;
    }

}
