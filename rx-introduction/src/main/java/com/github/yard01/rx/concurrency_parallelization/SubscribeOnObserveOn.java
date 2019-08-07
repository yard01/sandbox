package com.github.yard01.rx.concurrency_parallelization;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class SubscribeOnObserveOn {
	private final static long start = System.currentTimeMillis();
	
	public static void log(String s) {
		System.out.println(System.currentTimeMillis() - start +"\t |"
				+ Thread.currentThread().getName() 
				+ Thread.currentThread().getId() 
				+ "\t |" + s);
	};
	
	private static class MyThreadFactory implements ThreadFactory {
		String pattern;
		
		public MyThreadFactory(String pattern) {
			this.pattern = pattern;
		}
		
		@Override
		public Thread newThread(Runnable r) {
			// TODO Auto-generated method stub
			return new Thread(r, pattern);
		}
		
	}
	
	public static Observable<String> simple() {
		return Observable.create(subscriber -> {
			log("Подписан");
			subscriber.onNext("A");
			subscriber.onNext("B");
			subscriber.onComplete();
		});
	}
	
	//private static ThreadFactory threadFactory(String pattern) {
	//	ThreadFactoryBuilder t;
	//	return new ThreadFactoryBuilder() 
	//}
	private static Observable<UUID> store(String s) {
		return Observable.create(subscriber -> {
				log("Сохраняется" + s);
				//--
				//some long work
				//--
				subscriber.onNext(UUID.randomUUID());
				subscriber.onComplete();
			});
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExecutorService poolA = Executors.newFixedThreadPool(10, new MyThreadFactory("Sched-A-"));
		ExecutorService poolB = Executors.newFixedThreadPool(10, new MyThreadFactory("Sched-B-"));
		ExecutorService poolC = Executors.newFixedThreadPool(10, new MyThreadFactory("Sched-C-"));
		
		log("Начало");
		
		final Observable<String> obs = simple();
		//log("Создан");
		//log("Преобразован");		
		//obs.subscribe(s -> log("Получено " + s), //Consumer
		//		      Throwable::printStackTrace, //Consumer 
		//		      ()-> log("Завершен") //Action
		//		      );
		//log("Выход");
		
		//obs = simple();
		log("Создан");
		log("Преобразован");
		//obs.subscribeOn(scheduler)
		Scheduler schedulerA = Schedulers.from(poolA);
		Scheduler schedulerB = Schedulers.from(poolB);
		Scheduler schedulerC = Schedulers.from(poolC);
		
//		obs
//		//.subscribeOn(schedulerA)
//		//.subscribeOn(schedulerB)
//		.doOnNext(s -> log("Найдено 1: " + s))
//		.observeOn(schedulerC)
//		.doOnNext(s -> log("Найдено 2: " + s))
//		
//		.subscribe(s -> log("Получено " + s), //Consumer
//				      Throwable::printStackTrace, //Consumer 
//				      ()-> log("Завершен") //Action
//				      );
		obs
		.subscribeOn(schedulerA)
		.flatMap(record -> store(record)
				          .subscribeOn(schedulerB))
		//.map(record -> store(record)) //.subscribeOn(schedulerB))		
		.observeOn(schedulerC)
		.subscribe(s -> log("Получено " + s), //Consumer
				      Throwable::printStackTrace, //Consumer 
				      ()-> log("Завершен") //Action
				      );

		log("Выход");

	}

}
