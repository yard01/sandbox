package com.github.yard01.rx.concurrency_parallelization;

import java.util.concurrent.ThreadLocalRandom;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class TwoThreads {

	public static void noParallel() { //выполнение в одном, главном, потоке
		Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon") //поток строк
				.map(s -> intenseCalculation((s))) // произвольная задержка от 0 до 3-х с
				.subscribe(System.out::println);
		
		Observable.range(1,6) // поток цифр от 1 до 6
				.map(s -> intenseCalculation((s))) // произвольная задержка от 0 до 3-х с				
				.subscribe(System.out::println);
	}
	
	public static void parallel() {
		Observable.just("Alpha", "Beta", "Gamma", "Delta",
				"Epsilon")
				.subscribeOn(Schedulers.computation())
				.map(s -> intenseCalculation((s)))
				.subscribe(System.out::println);
		
		Observable.range(1,6)
				.subscribeOn(Schedulers.computation())
				.map(s -> intenseCalculation((s)))
				.subscribe(System.out::println);
		
		sleep(20000); //время выполнения программы
						
	}
				
	public static <T> T intenseCalculation(T value) {
		//System.out.println("Thread name is: " + Thread.currentThread().getName());
		sleep(ThreadLocalRandom.current().nextInt(3000)); //ThreadLocalRandom изолированный поток для получения случайных чисел 
		return value;
	}
	
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		System.out.println("---I. NO PARALLEL---------");
		noParallel();
		System.out.println("-------------------------");

		System.out.println("------II. PARALLEL--------");
		parallel();
		System.out.println("-------------------------");

	}
}
