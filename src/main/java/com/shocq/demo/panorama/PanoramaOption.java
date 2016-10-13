package com.shocq.demo.panorama;

import org.kohsuke.args4j.Option;

/**
 * Created by shocq on 12/10/2016.
 */
public class PanoramaOption {
    @Option(name = "-h", aliases = {"--help"}, usage = "Print help")
    public boolean help;

    @Option(name = "-v", aliases = {"--version"}, usage = "Print version")
    public boolean version;

    @Option(name = "-d", aliases = {"--directoty"}, usage = "directory")
    public String directory;
}
