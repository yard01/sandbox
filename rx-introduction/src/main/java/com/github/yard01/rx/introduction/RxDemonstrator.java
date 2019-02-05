package com.github.yard01.rx.introduction;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.reactivex.Observable;

public class RxDemonstrator {
    String tmpStr = "";

	public void demonstrate() {
		
	    //Observable<String> obs = Observable.just("Hello"); // provides datea
	    //obs.subscribe(s -> tmpStr = s); // Callable as subscriber
	    //System.out.println(tmpStr.equals("Hello"));
		
		
		EasyObservable observable = new EasyObservable();
		EasyObserver srv = new EasyObserver();			
		observable.just("Hello World!").subscribe(srv);
		
		List<String> collected = Stream.of("a", "b", "c").collect(Collectors.toList());		
		observable.fromIterable(collected).subscribe(srv); 
		
	}
}
