package com.github.yard01.rx.basic;

import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CompletableDemo {
	public static float errorGenerator = 1;
	public static Consumer<String> out = s -> {
		System.out.println(Thread.currentThread().getName() +"\t" + s);
	};
	
	//private static 
	
	public static void demoFromObservable() throws InterruptedException {		
		Observable obs = Observable.create(emitter -> {
			// этот блок выполнится при подписке в Completable,  
			// но доступа к испускаемым объектам у Completable нет
			emitter.onNext("A");
			emitter.onNext("B");
			out.accept("observable: waiting...");
			Thread.sleep(8000);
			errorGenerator = 1/0; //стек вызовов + Error
			emitter.onComplete();
			//errorGenerator = 1/0; // стек вызовов + Complete
		})
		//		
		.subscribeOn(Schedulers.io()) //
		;
		//Thread.sleep(3000); // 
		//if (true) return;
		Action a = () -> out.accept("observable subscribe: Action");
		Consumer<? super Throwable> c = throwable -> out.accept("observable subscribe: Error " + throwable.getMessage()); 
		
		Completable.complete()
		//.observeOn(Schedulers.io()) //
		.fromObservable(obs)
		.doOnComplete(()-> {out.accept("observable: COMPLETE");})		
		.doOnError( throwable -> {out.accept("observable: ERROR " + throwable.getMessage());})		
		.subscribe(a, c);//.subscribe(); //перехватит ошибку, так же как и Callable
		
	}
	
	private static <V> V callableMethod() {
		errorGenerator = 1/0;
		return null;
	}
	
	public static void demoFromCallable() {
		Action a = () -> out.accept("callable subscribe: Action");
		Consumer<? super Throwable> c = throwable -> out.accept("callable subscribe: Error"); 
				
		Callable callable = CompletableDemo::callableMethod;		
		Completable.complete()		
		.fromCallable(callable)		
		.doOnComplete(()-> {out.accept("callable: COMPLETE");})		
		.doOnError(throwable -> {out.accept("callable: ERROR");})
		.subscribe(a, c); //.subscribe();
		
	}
	
	public static void demoFromRunnable() {
		Action a = () -> out.accept("runnable subscribe: Action");
		Consumer<? super Throwable> c = throwable -> out.accept("runnable subscribe: Error"); 
		
		//Runnable r1 = () -> {
		//	System.out.println("Something Runnable 1");
		//	errorGenerator = 1/0;
		//};
		
		Runnable r = () -> {try {
			out.accept("Something Runnable");
			errorGenerator = 1/0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}};
		
		Completable.complete().fromRunnable(r)
		.doOnComplete(()-> {out.accept("runnable: COMPLETE");})		
		.doOnError(throwable -> {out.accept("runnable: ERROR");})
		.subscribe(a, c);
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		demoFromObservable();
		demoFromCallable();
		demoFromRunnable();
		Thread.sleep(12000);
	}
}
