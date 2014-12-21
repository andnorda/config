package config.repository;

import config.exceptions.NotFound;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class FileRepository {

    private File baseDir;

    public FileRepository(File baseDir) {
        this.baseDir = baseDir;
    }

    protected List<File> allAppDirs() {
        return listSubDirs(baseDir);
    }

    protected File getAppDir(String appName) {
        return getSubDirWithName(baseDir, appName);
    }

    protected File getSubDirWithName(File parent, String dirName) {
        File[] files = parent.listFiles(file -> file.isDirectory() && file.getName().equals(dirName));
        if (files.length == 0) {
            throw new NotFound();
        }
        return files[0];
    }

    protected List<File> listSubDirs(File parent) {
        File[] listFiles = parent.listFiles(File::isDirectory);
        return Arrays.asList(listFiles);
    }
}
