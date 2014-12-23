package config.repository;

import config.exceptions.NotFound;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class FileRepository {

    private File baseDir;

    public FileRepository(File baseDir) {
        this.baseDir = baseDir;
    }

    protected Collection<String> listSubDirNames(File parent) {
        File[] listFiles = parent.listFiles(File::isDirectory);
        return Arrays.asList(listFiles).stream()
                .map(File::getName)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    protected Collection<String> listFileNames(File parent) {
        return Arrays.asList(parent.listFiles(File::isFile)).stream()
                .map(File::getName)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    File getDir(String... path) {
        File file = baseDir;
        for (String dir : path) {
            File[] files = file.listFiles(file1 -> file1.isDirectory() && file1.getName().equals(dir));
            if (files.length == 0) {
                throw new NotFound();
            }
            file = files[0];
        }
        return file;
    }

    File getFile(String... path) {
        String fileName = path[path.length - 1];
        String[] dirs = Arrays.copyOf(path, path.length - 1);
        File[] files = getDir(dirs).listFiles(file -> file.getName().equals(fileName) && file.isFile());
        if (files.length == 0) {
            throw new NotFound();
        }
        return files[0];
    }

    Collection<File> listDirs(String... path) {
        return Arrays.asList(getDir(path).listFiles(File::isDirectory));
    }

    Collection<File> listFiles(String... path) {
        return Arrays.asList(getDir(path).listFiles(File::isFile));
    }
}
