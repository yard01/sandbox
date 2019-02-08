package com.github.yard01.rx.concurrency_parallelization;

import org.junit.Assert;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class SchedulersExample {

	static String tmp = "*";
	
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		e.printStackTrace();
		}
	}
	
	private static void schedulerWorker() {
		tmp = "*";
		Scheduler scheduler = Schedulers.single(); 
		Scheduler.Worker worker = scheduler.createWorker();
		worker.schedule(() -> tmp += "action");		
		System.out.println(tmp);  
		sleep(1000);
		System.out.println(tmp);  
	}

	private static void cancelingAction() {
		tmp = "-";
		Scheduler scheduler = Schedulers.newThread();
		Scheduler.Worker worker = scheduler.createWorker();
		worker.schedule(() -> {
		    tmp += "First_Action";
		    //worker.unsubscribe();
		});
		worker.schedule(() -> tmp += "Second_Action");
		
	}
	
	private static void schedulersIO() {
		//Schedulers.io() - Допускает повторное использование завершенных потоков
		Observable.just("io")
		  .subscribeOn(Schedulers.io())
		  .subscribe(i -> {
			  	sleep(1000);
			  	System.out.println("the first thread: " + Thread.currentThread().getName());
			  });
		//задержка, превый поток (время работы - 1 с.) ////////////////////
		sleep(2000); //успевает завершиться до запуска второго
		///////////////////////////////////////////////////////////////////
		
		// если мы запускаем новый поток, дождавшись завершения первого, то  
		// запустится повторно первый
		// если мы не дождались завершения первого потока, то запустится новый поток
		Observable.just("io")
		  .subscribeOn(Schedulers.io())
		  .subscribe(i -> {
			  	System.out.println("the second thread: " + Thread.currentThread().getName());
			  });

	}
	
	private static void schedulersNewThread() {
		//Новый поток создается всякий раз при вызове observeOn
		//
		Observable.just("Hello")
		  .observeOn(Schedulers.newThread())
		  .doOnNext(s ->
		    System.out.println( Thread.currentThread().getName())
		  )
		  .observeOn(Schedulers.newThread())
		  .subscribe(s ->
		    System.out.println( Thread.currentThread().getName())
		  );
		
		//Новый поток создается всякий раз 		
		Observable.just("Hello")
		.subscribeOn(Schedulers.newThread())
		  .subscribe(i -> {
			  	sleep(500);
			  	System.out.println(Thread.currentThread().getName());
			  });
		Observable.just("Hello")
		.subscribeOn(Schedulers.newThread())
		  .subscribe(i -> {
			  	sleep(500);
			  	System.out.println(Thread.currentThread().getName());
			  });

		Observable.just("Hello")
		.subscribeOn(Schedulers.newThread())
		  .subscribe(i -> {
			  	sleep(500);
			  	System.out.println(Thread.currentThread().getName());
			  });

		sleep(5000);
		Observable.just("Hello")
		.subscribeOn(Schedulers.newThread())
		  .subscribe(i -> {
			  	sleep(1000);
			  	System.out.println(Thread.currentThread().getName());
			  });
		sleep(10000);		
	  	System.out.println("Done");
		//результат
	  	//RxNewThreadScheduler-1
	  	//RxNewThreadScheduler-2
	  	//RxNewThreadScheduler-4
	  	//RxNewThreadScheduler-3
	  	//RxNewThreadScheduler-5
	  	//RxNewThreadScheduler-6
	  	//Done

	}
	
	private static void schedulersComputation() {
		//Schedulers.computation() - анализирует число параллельно работающих потоков		
		//  и доступных процессоров Runtime.getRuntime().availableProcessors(),
		// сначала каждый новый поток помещается в очередь и запускается только в том случае, 
		// если для него свободен процессор, потоки, которым не хватило процессора,
		// будут ждать завершения ВСЕХ работающих в данный момент потоков,
		// и только после их завершения запустится следующая порция ожидающих потоков
		// Причем, потоки используются ПОВТОРНО
		System.out.println("Available processors:" + Runtime.getRuntime().availableProcessors());
		Observable.just("computation1") //помещает поток в неограниченную очередь,
		  .subscribeOn(Schedulers.computation()) //запускает при освобождении процессора
		  .subscribe(i -> {
			  	sleep(8000);
			  	System.out.println(i + ". the first thread: " + Thread.currentThread().getName());
			  });
		Observable.just("computation2")
		  .subscribeOn(Schedulers.computation())
		  .subscribe(i -> {
			  	sleep(8000);
			  	System.out.println(i + ". the second thread: " + Thread.currentThread().getName());
			  });
		
		Observable.just("computation3")
		  .subscribeOn(Schedulers.computation())
		  .subscribe(i -> {
			  	sleep(2000);
			  	System.out.println(i + ". the third thread: " + Thread.currentThread().getName());
			  });


		Observable.just("computation4")
		  .subscribeOn(Schedulers.computation())
		  .subscribe(i -> {
			  	sleep(2000);
			  	System.out.println(i + ". the fouth thread: " + Thread.currentThread().getName());
			  });


		Observable.just("computation5")
		  .subscribeOn(Schedulers.computation())
		  .subscribe(i -> {
			  	sleep(2000);
			  	System.out.println(i + ". the fifth thread: " + Thread.currentThread().getName());
			  });

		Observable.just("computation6")
		  .subscribeOn(Schedulers.computation())
		  .subscribe(i -> {
			  	sleep(2000);
			  	System.out.println(i + ". the sixth thread: " + Thread.currentThread().getName());
			  });
		
		sleep(8500);
		System.out.println("the first THREAD GROUP is done");		
		sleep(5000);
		System.out.println("DONE");
		//результат
		//Available processors:4
		//computation4. the fouth thread: RxComputationThreadPool-4
		//computation3. the third thread: RxComputationThreadPool-3
		//computation1. the first thread: RxComputationThreadPool-1
		//computation2. the second thread: RxComputationThreadPool-2
		//the first THREAD GROUP is done
		//computation5. the fifth thread: RxComputationThreadPool-1
		//computation6. the sixth thread: RxComputationThreadPool-2
		//DONE
		
		
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//schedulerWorker();
		//schedulersComputation();
		schedulersNewThread();
	}

}
