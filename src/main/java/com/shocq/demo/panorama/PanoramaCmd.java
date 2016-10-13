package com.shocq.demo.panorama;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import rx.Observer;
import rx.schedulers.Schedulers;

import java.io.File;
import java.util.Stack;

public class PanoramaCmd {

    public static void main(String args[]) {
        PanoramaOption options = new PanoramaOption();
        CmdLineParser optionsParser = new CmdLineParser(options);
        try {
            optionsParser.parseArgument(args);
        } catch (CmdLineException e) {
            e.printStackTrace();
        }
        if(options.help || options.directory==null) {
            optionsParser.printUsage(System.out);
            System.exit(0);
        }
        PanoObs
                .createObs(options.directory, options.gap)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.immediate())
                .subscribe(new Observer<Stack<File>>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("DONE");
                        System.exit(0);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Stack<File> files) {
                        System.out.println("regroupement "+files);
                    }
                });

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
}
