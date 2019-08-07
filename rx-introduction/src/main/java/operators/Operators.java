package operators;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observables.GroupedObservable;

public class Operators {
	private static void flatMap() {
		Observable<String> source =
				Observable.just("Alpha", "Beta", "Gamma", "Delta","Epsilon");
				source
				//.single("").toObservable()
				.flatMap(s -> Observable.fromArray(s.split("")))							
				.subscribe(System.out::println);
				
	}
	
	public static void groupBy() {
		Observable<String> source =
				Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon");
		
		Observable<GroupedObservable<Integer,String>> byLengths = source.groupBy(s -> s.length()); // функция, возвращающая критерий группировки
		
		byLengths.flatMapSingle(grp -> grp.toList()) //
		.subscribe(System.out::println);		
	}
	
	public static void flatMapSingle() {
		Single s;
		Completable c;
	}
	
	public static void main(String[] args) {
		flatMap(); //раздробит строки на символы 
		//groupBy(); // сгруппирует элементы в списки по длине строки
		//Observable.fromArray(s.split(""))
	}
}
