package com.github.yard01.rx.basic;

import io.reactivex.Maybe;

public class MaybeDemo {
	//Maybe - похож на Single, за исключением того, что позволяет обойтись и без эмиссии объектов
	public static void main(String[] args) {
		// есть эмиссия
		Maybe<Integer> presentSource = Maybe.just(100);	
		presentSource.subscribe(s -> 
								System.out.println("Process 1 received: " + s),
								Throwable::printStackTrace,
								() -> System.out.println("Process 1 done!"));
		
		//нет эмиссии
		Maybe<Integer> emptySource = Maybe.empty();
		emptySource.subscribe(s -> System.out.println("Process 2 received: " + s),
							Throwable::printStackTrace,
							() -> System.out.println("Process 2 done!"));
		}
}
