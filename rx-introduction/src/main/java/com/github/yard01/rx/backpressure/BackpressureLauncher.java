package com.github.yard01.rx.backpressure;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class BackpressureLauncher {
	public static void observable() {
		
		Observable.range(1, 999_999_999)
		.map(MyItem::new).observeOn(Schedulers.io()).subscribe(myItem -> {
			sleep(50);
			System.out.println("Received MyItem " + myItem.id);
		});
		sleep(Long.MAX_VALUE);
		
	}
	
	public static void flowable() {
		Flowable.range(1, 999_999_999)
		.map(MyItem::new).observeOn(Schedulers.io()).subscribe(myItem -> {
			sleep(50);
			System.out.println("Received MyItem " + myItem.id);
		});
		sleep(Long.MAX_VALUE);
	}
	
	public static void main(String[] args) {
		//observable();
		flowable();
	}

	static void sleep(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	static final class MyItem {
		final int id;

		MyItem(int id) {
			this.id = id;
			System.out.println("Constructing MyItem " + id);
		}
	}
}
