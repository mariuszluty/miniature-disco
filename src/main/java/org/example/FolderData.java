package org.example;

import java.nio.file.Path;

public class FolderData implements Folder{
    static final int BYTE_TO_MEGABYTE = 1024 * 1024;
    static final int SMALL_THRESHOLD = 1;
    static final int MEDIUM_THRESHOLD = 50;
    static final String SMALL_LABEL = "SMALL";
    static final String MEDIUM_LABEL = "MEDIUM";
    static final String LARGE_LABEL = "LARGE";

    String name;
    String size;

    public FolderData(String name, long size) {
        this.name = name;
        size = size / BYTE_TO_MEGABYTE; // in MB
        this.size = CastSizeToString(size);
    }

    private String CastSizeToString(long size){
        if (size < SMALL_THRESHOLD) {
            return SMALL_LABEL;
        } else if (size < MEDIUM_THRESHOLD) {
            return MEDIUM_LABEL;
        } else {
            return LARGE_LABEL;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSize() {
        return size;
    }

    @Override
    public String toString() {
        return name;
    }
}
