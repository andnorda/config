package config.repository;

import config.dtos.ApplicationDto;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class FileApplicationRepository implements ApplicationRepository {
    private File baseDir;

    public FileApplicationRepository(File baseDir) {
        this.baseDir = baseDir;
    }

    @Override
    public ApplicationDto get(String name) {
        return null;
    }

    @Override
    public Collection<ApplicationDto> getAll() {
        List<File> files = Arrays.asList(baseDir.listFiles());
        return files.stream()
                .filter(File::isDirectory)
                .map(file -> new ApplicationDto(file.getName()))
                .collect(Collectors.toCollection(HashSet::new));
    }
}
