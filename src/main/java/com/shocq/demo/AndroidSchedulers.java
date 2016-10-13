package com.shocq.demo;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by chtiboss on 06/10/2016.
 */
public class AndroidSchedulers {
    public static Scheduler mainThread() {
        return Schedulers.immediate();
    }
}
