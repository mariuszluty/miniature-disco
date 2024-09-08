package org.example;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {
    public static void main(String[] args) throws IOException {
        FolderCabinet folderCabinet = new FolderCabinet("C:\\Users\\mariu\\Code\\java\\HorusZadanie");
        System.out.println("folderCabinet.count() = " + folderCabinet.count());
        System.out.println("folderCabinet.findFoldersBySize(\"SMALL\") = " + folderCabinet.findFoldersBySize("SMALL"));
        System.out.println("folderCabinet.findFoldersBySize(\"MEDIUM\") = " + folderCabinet.findFoldersBySize("MEDIUM"));
        System.out.println("folderCabinet.findFoldersBySize(\"LARGE\") = " + folderCabinet.findFoldersBySize("LARGE"));
        System.out.println("folderCabinet.findFolderByName(\"example\") = " + folderCabinet.findFolderByName("example"));
        System.out.println("folderCabinet.findFolderByName(\"cokolwiek\") = " + folderCabinet.findFolderByName("cokolwiek"));
    }
}

interface Cabinet {
    // zwraca dowolny element o podanej nazwie
    Optional<Folder>
    findFolderByName(String name);

    // zwraca wszystkie foldery podanego rozmiaru SMALL/MEDIUM/LARGE
    List<Folder> findFoldersBySize(String size);

    //zwraca liczbę wszystkich obiektów tworzących strukturę
    int count();
}

interface Folder {
    String getName();
    String getSize();
}

interface MultiFolder extends Folder {
    List<Folder> getFolders();
}
