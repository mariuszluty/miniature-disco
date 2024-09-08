package org.example;

import java.util.ArrayList;
import java.util.List;

public class MultiFolderData extends FolderData implements MultiFolder{
    List<Folder> children;

    public MultiFolderData(String name, long length) {
        super(name, length);
        this.children = new ArrayList<>();
    }

    @Override
    public List<Folder> getFolders() {
        return children;
    }
}
