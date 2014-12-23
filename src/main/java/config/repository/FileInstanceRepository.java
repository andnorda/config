package config.repository;

import config.dtos.InstanceDto;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FileInstanceRepository implements InstanceRepository {
    private final FileRepository fileRepository;

    public FileInstanceRepository(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public InstanceDto get(String appName, String versionName, String instanceName) {
        File file = fileRepository.getDir(appName, versionName, instanceName);
        return toInstance().apply(file);
    }

    @Override
    public Collection<InstanceDto> getAll(String appName, String versionName) {
        Collection<File> files = fileRepository.listDirs(appName, versionName);
        return files.stream()
                .map(toInstance())
                .collect(Collectors.toList());
    }

    private Function<File, InstanceDto> toInstance() {
        return file -> new InstanceDto(file.getName(), fileRepository.listFileNames(file));
    }
}
