package com.github.yard01.sandbox.crib.service_crib.simpleservice;

import android.app.Service;
import android.os.Binder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.io.FileDescriptor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SimpleBinder extends Binder {

    SimpleServiceCrib serviceCrib;

    public SimpleBinder(SimpleServiceCrib service) {
        this.serviceCrib = service;
    }


    public SimpleServiceCrib getService() {
        return serviceCrib;
    }
}
