package config.repository;

import config.dtos.ApplicationDto;
import config.exceptions.NotFound;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FileApplicationRepository implements ApplicationRepository {
    private File baseDir;

    public FileApplicationRepository(File baseDir) {
        this.baseDir = baseDir;
    }

    @Override
    public ApplicationDto get(String appName) {
        File[] listFiles = baseDir.listFiles(file -> file.getName().equals(appName) && file.isDirectory());
        List<File> files = Arrays.asList(listFiles);
        if (files.isEmpty()) {
            throw new NotFound();
        }
        return new ApplicationDto(files.get(0).getName());
    }

    @Override
    public Collection<ApplicationDto> getAll() {
        File[] listFiles = baseDir.listFiles(File::isDirectory);
        List<File> files = Arrays.asList(listFiles);
        return files.stream()
                .map(file -> new ApplicationDto(file.getName()))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
