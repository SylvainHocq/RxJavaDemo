package com.shocq.demo.panorama;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import rx.Observable;
import rx.functions.Func2;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class PanoObs {

    public static Observable<Stack<File>> createObs(String adsFileName, long gap) {
        Func2<ArrayList<File>, ArrayList<File>, ArrayList<File>> init = null;
        File root = new File(adsFileName);
        return DirecyoryObs
                .createListDirectoryObs(root)
                .filter(o -> o.isFile())
                .map(f -> {
                    Stack<File> list = new Stack<>();
                    list.push(f);
                    return list;
                })
                .scan((filesStack1, filesStack2) -> {
                    Stack<File> ret = new Stack<>();
                    File file1 = filesStack1.lastElement();
                    File file2 = filesStack2.get(0);
                    long diff = getDiff(file1, file2);
                    if (diff < gap) {
                        ret = filesStack1;
                        ret.push(file2);
                    } else {
                        ret = filesStack2;
                    }
                    return ret;
                })
                .filter(files -> files.size() > 1)
//                .doOnNext(files -> {
//                    String name = files.get(0).getName();
//                    name = name.substring(0, name.indexOf("."));
//                    String name1 = files.lastElement().getName();
//                    name1 = name1.substring(0, name1.indexOf("."));
//                    String directoryName = name +"_"+name1;
//                    File directory = new File(files.get(0).getParentFile(), directoryName);
//                    System.out.println(directory);
//                })

                ;

    }

    private static long getDiff(File file1, File file2) {
        Date date1 = getCreationDate(file1);
        Date date2 = getCreationDate(file2);
        return date2.getTime() - date1.getTime();
    }

    static SimpleDateFormat parser=new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
    private static Date getCreationDate(File file1) {


        Metadata metadata = null;
        try {
            metadata = ImageMetadataReader.readMetadata(file1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String dateFormat = Observable.from(metadata.getDirectories())
                .flatMap(directory -> Observable.from(directory.getTags()))
                .filter(tag -> tag.getTagName().equals("Date/Time Original"))
                .map(Tag::getDescription)
                .single().toBlocking().single();

        try {
            Date date = parser.parse(dateFormat);
            return date;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
}
