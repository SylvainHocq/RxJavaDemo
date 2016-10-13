package com.shocq.demo.panorama;

import org.kohsuke.args4j.CmdLineParser;
import rx.Observable;

import java.io.File;

/**
 * Created by chtiboss on 08/10/2016.
 */
public class PanoramaSearch {


    public static void main(String[] args) {
        PanoramaOption option = parseArguments(args);
        createObs(option.directory);
    }

    private static void createObs(String directory) {


        Observable.just(1, 2, 3, 4, 5)
                .scan((sum, item) -> sum + item);


        Observable
                .just(new File(directory))
                .flatMap(file -> {
                    File[] array = file.listFiles();
                    return Observable.from(array != null ? array : new File[0]);
                })
                .toSortedList((file, file2) -> (int)(file.lastModified()-file2.lastModified()))
//                .map(files2 -> {
//                    Object files21 = null;
//                    return new FilePair(files2, null);
//                })
                .flatMap(files -> Observable.from(files.toArray(new File[files.size()])))
                .map(file -> new FileWrapper(file, null))
                .scan((fileWrapper, fileWrapper2) -> new FileWrapper(fileWrapper, fileWrapper2))
//                .scan(new Func2<FileWrapper, FileWrapper, FileWrapper>() {
//                    @Override
//                    public FileWrapper call(FileWrapper file, FileWrapper file2) {
//                        return new FileWrapper();
//                    }
//                })
                ;

    }

    private static PanoramaOption parseArguments(String[] args) {
        PanoramaOption options = new PanoramaOption();
        CmdLineParser optionsParser = new CmdLineParser(options);
        try {
            optionsParser.parseArgument(args);

        } catch (Exception e) {

        }
        return options;
    }

    private static class FileWrapper extends File {
        private FileWrapper filesRoot;
        private FileWrapper filesChild;

        public FileWrapper(File files, FileWrapper files2) {
            super(files.getAbsolutePath());
        }

        public FileWrapper(FileWrapper files, FileWrapper files2) {
            super(files.getAbsolutePath());
            this.filesRoot = files;
            this.filesChild = files2;
        }


    }
}
