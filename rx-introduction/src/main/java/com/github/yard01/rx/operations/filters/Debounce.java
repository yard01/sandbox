package com.github.yard01.rx.operations.filters;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class Debounce {
	//Если в течение ТАЙМАУТА приходит новый элемент, то текущий элемент удаляется
	//счетчик времени сбрасывается всякий раз при приходе нового элемента
	
	public static void main(String[] args) {
		Observable<String> source = Observable.create(emitter -> {
		    emitter.onNext("A"); // 

		    Thread.sleep(1_500); 
		    emitter.onNext("B"); //Таймаут превышен, A остается

		    Thread.sleep(500); 
		    emitter.onNext("C"); // Таймаут не превышен, B удаляется

		    Thread.sleep(250);
		    emitter.onNext("D"); // Таймаут не превышен, С удаляется

		    Thread.sleep(2_000); //Таймаут превышен, D остается
		    emitter.onNext("E"); 
		    
		    emitter.onComplete(); //завершили поток, нового ничего не пришло, E остается
		});

		source.subscribeOn(Schedulers.io())
		        .debounce(1, TimeUnit.SECONDS) //Таймаут - 1с
		        .blockingSubscribe(
		                item -> System.out.println("onNext: " + item),
		                Throwable::printStackTrace,
		                () -> System.out.println("onComplete"));
		
	}
}
