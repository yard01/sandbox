package com.github.yard01.rx.concurrency_parallelization;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class AsyncFileReader2 {
	public static void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void readFlowable() {		

		BufferedReader br = new BufferedReader(
									new InputStreamReader(AsyncFileReader.class.getClassLoader().getResourceAsStream("pushkin.txt")
											)
									);			
		Flowable.fromIterable(br.lines()::iterator)					
					 .observeOn(Schedulers.io()) // читаем строки в потоке
					 .doOnNext(s -> {;}) // читаем строку
		             .subscribe(AsyncFileReader::viewData  // и отдаем их на "Устройство Вывода viewData"      			
		            		 );					
	}
	
	public static void main(String[] args) {
		//readObservable();	
		//readFlowable();			
		sleep(60000);
		
	}
}
