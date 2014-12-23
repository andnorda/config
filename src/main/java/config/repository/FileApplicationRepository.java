package config.repository;

import config.dtos.ApplicationDto;
import config.repository.ApplicationRepository;
import config.repository.FileRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FileApplicationRepository implements ApplicationRepository {
    private final FileRepository fileRepository;

    public FileApplicationRepository(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public ApplicationDto get(String appName) {
        File file = fileRepository.getDir(appName);
        return toApplication().apply(file);
    }

    @Override
    public Collection<ApplicationDto> getAll() {
        Collection<File> files = fileRepository.listDirs();
        return files.stream()
                .map(toApplication())
                .collect(Collectors.toList());
    }

    private Function<File, ApplicationDto> toApplication() {
        return file -> new ApplicationDto(file.getName(), fileRepository.listSubDirNames(file), fileRepository.listFileNames(file));
    }
}
