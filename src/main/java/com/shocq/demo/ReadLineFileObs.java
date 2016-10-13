package com.shocq.demo;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

import java.io.*;

public class ReadLineFileObs {


    public static Observable<String> getLinesOfFileObs(String fileName) {

        Observable<String> obsLines = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                BufferedReader in;
                try {
                    in = new BufferedReader(new FileReader(fileName));
                    String line = in.readLine();
                    while (line != null && !subscriber.isUnsubscribed()) {
                        subscriber.onNext(line);
                        line = in.readLine();
                    }
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        });



        File file = new File(fileName);
        System.out.println(file.getAbsolutePath());
        return Observable
                .just(file)
                .flatMap(new Func1<File, Observable<String>>() {
                    @Override
                    public Observable<String> call(File file) {
                        return Observable.create(new Observable.OnSubscribe<String>() {
                            @Override
                            public void call(Subscriber<? super String> subscriber) {
                                BufferedReader in;
                                try {
                                    in = new BufferedReader(new FileReader(file));
                                    String line = in.readLine();
                                    while (line != null) {
                                        subscriber.onNext(line);
                                        line = in.readLine();
                                    }
                                    subscriber.onCompleted();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    subscriber.onError(e);
                                }
                            }
                        });
                    }
                });


    }
}
