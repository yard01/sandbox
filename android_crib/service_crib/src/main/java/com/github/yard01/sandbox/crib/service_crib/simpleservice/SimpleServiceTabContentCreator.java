package com.github.yard01.sandbox.crib.service_crib.simpleservice;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.service_crib.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class SimpleServiceTabContentCreator {

    public static final String LOG_TAG = "service_call";

    public final static String BROADCAST_ACTION = "com.github.yard01.sandbox.crib.service_crib.simpleservice.backbroadcast";
    public final static String PENDING_ACTION = "com.github.yard01.sandbox.crib.service_crib.simpleservice.pending";
    public final static String DATA_KEY = "data_key";
    public final static String PENDING_INTENT_KEY = "pending_key";
    public final static int PENDING_REQUEST_CODE = 1;

    private static Context context;
    //private static IBinder currentBinder;
    private static SimpleServiceCrib serviceCrib;

    private static Messenger messengerService; // для связи через Binder, если сервис работает в другом процессе

    private static BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        // действия при получении сообщений
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(LOG_TAG, "received data: " + intent.getStringExtra(DATA_KEY));
        }
    };


    private static ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            //currentBinder = binder;
            serviceCrib = ((SimpleBinder)binder).getService();
            Log.d(LOG_TAG, "IBind onServiceConnected, service " + serviceCrib);

            //Messenger
            //bound = true ;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(LOG_TAG, "IBind onServiceDisconnected");
            //bound = false ;
        }
    };

    private static ServiceConnection serviceConnectionOtherProcess = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            //currentBinder = binder;
            //serviceCrib = ((SimpleBinder)binder).getService();
           // Log.d(LOG_TAG, "IBind onServiceConnected, service " + serviceCrib);

            //Messenger
            //bound = true ;
            messengerService = new Messenger(binder);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(LOG_TAG, "IBind onServiceDisconnected");
            //bound = false ;
        }
    };


    public static void destroyContent() {
        if (broadcastReceiver != null) {
            context.unregisterReceiver(broadcastReceiver);
            Log.d(LOG_TAG, "unregister " + broadcastReceiver);
            broadcastReceiver = null;
        }
    }

    public static void createContent(FragmentActivity activity) {
        //activity.cre
     //   ViewPager viewPager = (ViewPager) activity.findViewById(R.id.insidepager);
     //   viewPager.setAdapter(new ControlsPagerAdapter(activity.getSupportFragmentManager(), activity.getBaseContext()));
        //activity.getBaseContext().
    }


    public static void bindService() {
        //!!!Такой способ не работает!!!///////////////////
        //Intent i = new Intent(SimpleServiceCrib.class.getName()); //new Intent(context, SimpleServiceCrib.class);
        //i.setPackage("com.github.yard01.sandbox.crib.service_crib.simpleservice");// SimpleServiceCrib.class.getPackage().getName())
        ///////////////////////////////////////////////////
        //Надо делать, указывая context, так:
        Intent i = new Intent(context, SimpleServiceCrib.class);
        context.bindService(i, serviceConnection, Activity.BIND_AUTO_CREATE);
    }

    public static void bindServiceOtherProcess() {
        context.bindService(new Intent(context, SimpleServiceCribOtherProcess.class), serviceConnectionOtherProcess,
                Context.BIND_AUTO_CREATE);
    }

    public static void unbindService() {
        context.unbindService(serviceConnection);
    }

    public static void unbindServiceOtherProcess() {
        context.unbindService(serviceConnectionOtherProcess);
    }

    public static void createContent(FragmentManager fragmentManager, final View view) {
        //Способы взаимодействия с сервисом
        //1. Асинхронные
        //"Оформляем подписку" на сообщения от сервиса ///////////////////////////////
        //broadcastReceiver - наш обработчик сообщений ///////////////////////////////
        IntentFilter intFilt = new IntentFilter(BROADCAST_ACTION);
        context = view.getContext();
        context.registerReceiver(broadcastReceiver, intFilt); // регистрируем (включаем) BroadcastReceiver
        //////////////////////////////////////////////////////////////////////////////
        //cоздаем обратную связь между Activity и сервисом с помощью PendingIntent
        //в этом способе взаимодействия ответы от сервиса будут приходить в Activity с событием onActivityResult
        final PendingIntent pendingIntent = ((Activity)view.getContext()).createPendingResult(PENDING_REQUEST_CODE, new Intent(PENDING_ACTION), 0);
        //////////////////////////////////////////////////////////////////////////////

        //2. Синхронный - через IBinder
        //
        //context.bindService()
        //

        Button btnStart = view.findViewById(R.id.service_btn_start_simplesrv);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //запускаем сервис
                v.getContext().startService(new Intent(v.getContext(), SimpleServiceCrib.class)
                        .putExtra(PENDING_INTENT_KEY, pendingIntent)); //сообщаем PendingIntent, связанный с Activity, которая будет принимать сообщения от сервиса в onActivityResult

                v.getContext().startService(new Intent(v.getContext(), SimpleServiceCribOtherProcess.class)
                        .putExtra(PENDING_INTENT_KEY, pendingIntent)); //сообщаем PendingIntent, связанный с Activity, которая будет принимать сообщения от сервиса в onActivityResult
            }
        });

        Button btnStop = view.findViewById(R.id.service_btn_stop_simplesrv);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Остановка сервиса не произойдёт, пока с ним есть связь Bind
                //как только выполнится UnBind, произойдёт и остановка (если ранее уже была попытка остановить сервис)
                v.getContext().stopService(new Intent(v.getContext(), SimpleServiceCrib.class));
                v.getContext().stopService(new Intent(v.getContext(), SimpleServiceCribOtherProcess.class));

            }
        });

        Button btnStopById = view.findViewById(R.id.service_btn_stopid_simplesrv);
        final EditText et = view.findViewById(R.id.service_et_id_simplesrv);
        btnStopById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Остановка по Id запуска
                SimpleServiceCrib.stopId = Integer.valueOf(et.getText().toString());
            }
        });

        Button btnBindToService = view.findViewById(R.id.service_btn_bind_simplesrv);

        btnBindToService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService();
                unbindService();
                Toast.makeText(v.getContext(), "Connect is Successful", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnStartConnect = view.findViewById(R.id.service_btn_startconnect_simplesrv);
        btnStartConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService();
                bindServiceOtherProcess();

                //Toast.makeText(v.getContext(), "Connect has started: " + serviceCrib, Toast.LENGTH_SHORT).show();
            }
        });

        Button btnStopConnect = view.findViewById(R.id.service_btn_stopconnect_simplesrv);
        btnStopConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService();
                unbindServiceOtherProcess(); //
                Toast.makeText(v.getContext(), "Connect has stopped", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnIncInterval = view.findViewById(R.id.service_btn_incint_simplesrv);
        btnIncInterval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceCrib.incInterval();
            }
        });

        Button btnDecInterval = view.findViewById(R.id.service_btn_decint_simplesrv);
        btnDecInterval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceCrib.decInterval();
            }
        });

        Button btnSayHello = view.findViewById(R.id.service_btn_hello_simplesrv);
        btnSayHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sayHello();
            }
        });

    }

    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "MyService onBind");
        return new Binder();
    }


    public static void sayHello() {
        //if (!bound) return;
        // Create and send a message to the service, using a supported 'what' value
        Message msg = Message.obtain(null, SimpleServiceCribOtherProcess.MSG_SAY_HELLO, 0, 0);
        try {
            messengerService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    //public static void
}
