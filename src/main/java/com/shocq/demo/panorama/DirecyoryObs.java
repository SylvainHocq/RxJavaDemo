package com.shocq.demo.panorama;

import rx.Observable;
import rx.Subscriber;

import java.io.File;

/**
 * Created by shocq on 12/10/2016.
 */
public class DirecyoryObs {

    public static Observable<? extends File> createListDirectoryObs(File root) {
        return Observable
                .just(root)
                .flatMap(DirecyoryObs::createFileObs)
        ;
    }

    private static Observable<? extends File> createFileObs(File folder) {
        return Observable.create(
                new Observable.OnSubscribe<File>() {
                    @Override
                    public void call(Subscriber<? super File> subscriber) {
                        File[] files = folder.listFiles();
                        for (File file : files) {
                            if (subscriber.isUnsubscribed()) {
                                break;
                            }
                            subscriber.onNext(file);
                        }
                        subscriber.onCompleted();
                    }
                }
        );
    }

}
