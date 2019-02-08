package com.github.yard01.rx.concurrency_parallelization;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class SimpleEasyThread {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Observable
		.interval(1, TimeUnit.SECONDS) //каждую секунду эмитирует Observable<Long> в поток, пока работает программа
		.map(i -> i + " Mississippi") // выводит строку №Элемента Missisippi 
		.subscribe(System.out::println);
		
		sleep(5000); // задержка программы
	}

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
