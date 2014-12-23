package config.repository;

import config.dtos.VersionDto;

import java.io.File;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FileVersionRepository implements VersionRepository {
    private final FileRepository fileRepository;

    public FileVersionRepository(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public VersionDto get(String appName, String versionName) {
        File file = fileRepository.getDir(appName, versionName);
        return toVersion().apply(file);
    }

    @Override
    public Collection<VersionDto> getAll(String appName) {
        Collection<File> files = fileRepository.listDirs(appName);
        return files.stream()
                .map(toVersion())
                .collect(Collectors.toList());
    }

    private Function<File, VersionDto> toVersion() {
        return file -> new VersionDto(file.getName(), fileRepository.listSubDirNames(file), fileRepository.listFileNames(file));
    }
}
