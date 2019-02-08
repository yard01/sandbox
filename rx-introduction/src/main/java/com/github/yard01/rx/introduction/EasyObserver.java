package com.github.yard01.rx.introduction;

import io.reactivex.ObservableOperator;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class EasyObserver<T> implements Observer<T> {

	@Override
	public void onSubscribe(Disposable d) {
		// TODO Auto-generated method stub
		System.out.println("onSubscribe(): EasyObserver was subscribed");
		
	}

	@Override
	public void onNext(T t) {
		// TODO Auto-generated method stub
		System.out.println("onNext(): This is a received message: " + t.toString());
	}

	@Override
	public void onError(Throwable e) {
		// TODO Auto-generated method stub
		System.out.println("Error");
		
	}

	@Override
	public void onComplete() {
		// TODO Auto-generated method stub
		System.out.println("onComplete(): complete");

	}

}
