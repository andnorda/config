package config.repository;

import config.dtos.PropertyFileDto;
import config.exceptions.NotFound;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

    protected ArrayList<String> listSubDirNames(File parent) {
        return listSubDirs(parent).stream()
                .map(File::getName)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    protected Collection<String> listPropertyFileNames(File parent) {
        return getAllPropertyFiles(parent).stream()
                .map(PropertyFileDto::getName)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    protected PropertyFileDto getPropertyFileWithName(File parent, String fileName) {
        List<File> propertyFiles = Arrays.asList(parent.listFiles(file -> file.getName().equals(fileName + ".properties") && file.isFile()));
        if (propertyFiles.isEmpty()) {
            throw new NotFound();
        }
        return new PropertyFileDto(propertyFiles.get(0).getName().replace(".properties", ""));
    }

    protected Collection<PropertyFileDto> getAllPropertyFiles(File parent) {
        List<File> propertyFiles = Arrays.asList(parent.listFiles(file -> file.isFile() && file.getName().endsWith(".properties")));
        return propertyFiles.stream()
                .map(file -> new PropertyFileDto(file.getName().replace(".properties", "")))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
