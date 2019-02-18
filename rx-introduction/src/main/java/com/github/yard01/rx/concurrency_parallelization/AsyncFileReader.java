package com.github.yard01.rx.concurrency_parallelization;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class AsyncFileReader {
	
	public static void readObservable() {

		BufferedReader br = new BufferedReader(
									new InputStreamReader(AsyncFileReader.class.getClassLoader().getResourceAsStream("pushkin.txt")
											)
									);			
		Observable.fromIterable(br.lines()::iterator)
					 .observeOn(Schedulers.io()) // читаем строки в потоке
		             .subscribe(AsyncFileReader::viewData  // и отдаем их на "Устройство Вывода viewData"      			
		            		 );					
	}

	public static void readFlowable() {		

		BufferedReader br = new BufferedReader(
									new InputStreamReader(AsyncFileReader.class.getClassLoader().getResourceAsStream("pushkin.txt")
											)
									);			
		Flowable.fromIterable(br.lines()::iterator)
					 .observeOn(Schedulers.io()) // читаем строки в потоке
		             .subscribe(AsyncFileReader::viewData  // и отдаем их на "Устройство Вывода viewData"      			
		            		 );					
	}
	
	public static void viewData(String data) {
		//"Устройство Вывода" выводит каждую строку в отдельном потоке 
		Observable.just(data).subscribeOn(Schedulers.computation()) //помещаем в очередь 		
		.subscribe(s-> {//System.out::println
							sleep(500); 
							System.out.println(Thread.currentThread().getName() +" -> " +s);
						}
				   );
	}
	
	
	
	public static void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		//readObservable();	
		readFlowable();			
		sleep(60000);
		
	}
}
