package com.github.yard01.rx.introduction;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.reactivex.Observable;

public class RxDemonstrator {
    String tmpStr = "";

	public void demonstrate() {
		System.out.println("######EXAMPLE 1##################################");
		String msg = "Hello";
	    Observable<String> obs = Observable.just(msg); // provides datea
	    obs.subscribe(s -> tmpStr = s); // Callable as subscriber
	    System.out.println(tmpStr + " = " + msg +" : " + tmpStr.equals(msg));
		System.out.println("#################################################");
		
		System.out.println();		

		System.out.println("######EXAMPLE 2##################################");
		EasyObservable observable = new EasyObservable();
		EasyObserver srv = new EasyObserver();			
		observable.just("Hello World!").subscribe(srv);
		
		List<String> collected = Stream.of("a", "b", "c").collect(Collectors.toList());		
		observable.fromIterable(collected)
		.subscribe(srv); 
		System.out.println("#################################################");
		
		System.out.println();
		
		System.out.println("######EXAMPLE 3##################################");		
		Observable<String> source = Observable.create(emitter -> {
			emitter.onNext("Alpha");
			emitter.onNext("Beta");
			emitter.onNext("Gamma");
			emitter.onNext("Delta");
			emitter.onNext("Epsilon");
			emitter.onComplete();
		});	
		source.subscribe(s -> System.out.println("RECEIVED: " + s)); //subscribe (Observer<? super String>)
		System.out.println("#################################################");

		
		System.out.println("######EXAMPLE 4##################################");		
		Observable<String> source4 = Observable.create(emitter -> {
			emitter.onNext("Alpha");
			emitter.onNext("Beta");
			emitter.onNext("Gamma");
			emitter.onNext("Delta");
			emitter.onNext("Epsilon");
			emitter.onComplete();
		});	
		Observable<Integer> lengths = source4.map(String::length);				
		Observable<Integer> filtered = lengths.filter(i -> i >= 5);
		filtered.subscribe(s -> System.out.println("RECEIVED: " + s));
		System.out.println("#################################################");

		
	}
}
