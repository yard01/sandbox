package com.github.yard01.rx.operations.filters;

import io.reactivex.Observable;

public class Distinct {
	public static void main(String[] args) {
		Observable.just(1, 1, 2, 1, 2, 3, 3, 4)
        .distinctUntilChanged()
        .subscribe(System.out::println);
	}
}
