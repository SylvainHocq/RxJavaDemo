package com.shocq.demo;

import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class RxSample {

    public static void main(String args[]) {
        exemple();

        waitFinish();
    }

    private static void waitFinish() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void exemple1() {

        Observable<String> observable = Observable.from(new String[]{"hello", "the", "world", "!"});

        observable.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
                System.exit(0);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError" + e);
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext " + s);
            }
        });

    }

    private static void exemple1Java8() {

        Observable<String> observable = Observable.from(new String[]{"hello", "the", "world", "!"});

        observable
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        s -> System.out.println(s),
                        t -> System.out.println(t),
                        () -> System.out.println("onComplete")
                );

    }


    private static void exemple() {

        Observable
                .from(new String[]{"hello", "the", "world", "!"})
                .map(String::length)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(System.out::println);

    }

}
