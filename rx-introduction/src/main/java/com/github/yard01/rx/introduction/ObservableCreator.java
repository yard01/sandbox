package com.github.yard01.rx.introduction;



import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ObservableCreator {
	static interface Console<T> {
		public void out(T t);
	}

	static Console<String> console = System.out::println;
	static Consumer<Long> delay = Thread::sleep;
	
	
		
	public static void delayPrintln(long time, String text) throws Exception {
		delay.accept(time);
		console.out(text);
	}

	public static void secondPrintln(String text) throws Exception {
		delayPrintln(1000, text);
	}

	public static void secondPrintlnWithThreadName(String text) throws Exception {
		delayPrintln(1000, text + " # " + Thread.currentThread().getName());
	}

	
	public static void demo() throws Exception {
		
		
		Observable oos;
		//oos.
		
		console.out("----- START Observable.just Example -----");
		
		Observable.just("This is an JUST example as a sequence running")
		          .subscribe(ObservableCreator::secondPrintlnWithThreadName);
		
		Observable.just("This is an JUST example as a PARALLEL1 running")
				  .subscribeOn(Schedulers.io()) // parallelization
				  .subscribe(ObservableCreator::secondPrintlnWithThreadName);

		Observable.just("This is an JUST example as a PARALLEL2 running")
		.subscribeOn(Schedulers.io()) // parallelization
        .subscribe(ObservableCreator::secondPrintlnWithThreadName);
		
		console.out("----- STOP Observable.just Example -----");
		
		delay.accept(4000l);
		
		console.out("----- START Observable.fromCallable Example -----");
		
		//Observable.just(1,2,3,4,5,6,7,8,9,1/0)
		//.subscribe(i -> console.out("Received: " + i), 
		//		   e -> console.out("Error Captured: " + e));

		Observable.fromCallable(() -> 1 / 0 )
		.subscribe(i -> console.out("Received: " + i), 
				   e -> console.out("Error Captured: " + e));
		console.out("----- STOP Observable.fromCallable Example -----");
		
		//Observable.fromCallable(supplier)
	}
	
	public static void main(String[] args) throws Exception {
		delayPrintln(3000, "#### BEGIN ####");
		
		demo();
		
		delayPrintln(3000, "#### END ####");
	}
}
