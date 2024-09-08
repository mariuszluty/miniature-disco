package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class FolderCabinet implements Cabinet {
    private List<Folder> folders;

    public void getFolders(Path path, Folder parent, List<Folder> result) throws IOException {
        try (Stream<Path> stream = Files.list(path)) {
            List<Path> folderList = stream
                    .filter(Files::isDirectory).toList();

            boolean isMultiFolder = !folderList.isEmpty();

            String name = path.getFileName().toString();

            long size = Files.walk(path)
                    .filter(p -> p.toFile().isFile())
                    .mapToLong(p -> p.toFile().length())
                    .sum();

            Folder folder = isMultiFolder ? new MultiFolderData(name, size) : new FolderData(name, size);

            result.add(folder);

            if (parent instanceof MultiFolder) {
                ((MultiFolder) parent).getFolders().add(folder);
            } else {
                folders.add(folder);
            }

            for(Path p : folderList){
                getFolders(p, folder, result);
            }
        }
    }

    public FolderCabinet(String path) throws IOException {
        Path rootDir = Path.of(path);
        folders = new ArrayList<>();
        List<Folder> result = new ArrayList<>();
        getFolders(rootDir, null, result);
    }

    @Override
    public Optional<Folder> findFolderByName(String name) {
        return findFolderByName(name, folders);
    }

    private Optional<Folder> findFolderByName(String name, List<Folder> folders) {
        for (Folder folder : folders) {
            if (folder.getName().equals(name)) {
                return Optional.of(folder);
            }
            if (folder instanceof MultiFolder) {
                Optional<Folder> found = findFolderByName(name, ((MultiFolder) folder).getFolders());
                if (found.isPresent()) {
                    return found;
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {
        List<Folder> result = new ArrayList<>();
        findFoldersBySize(size, folders, result);
        return result;
    }

    private void findFoldersBySize(String size, List<Folder> folders, List<Folder> result) {
        for (Folder folder : folders) {
            if (folder.getSize().equals(size)) {
                result.add(folder);
            }
            if (folder instanceof MultiFolder) {
                findFoldersBySize(size, ((MultiFolder) folder).getFolders(), result);
            }
        }
    }

    @Override
    public int count() {
        return countFolders(folders);
    }

    private int countFolders(List<Folder> folders) {
        int count = 0;
        for (Folder folder : folders) {
            count++;
            if (folder instanceof MultiFolder) {
                count += countFolders(((MultiFolder) folder).getFolders());
            }
        }
        return count;
    }
}
