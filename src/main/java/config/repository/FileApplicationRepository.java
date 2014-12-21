package config.repository;

import config.dtos.ApplicationDto;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FileApplicationRepository extends FileRepository implements ApplicationRepository {

    public FileApplicationRepository(File baseDir) {
        super(baseDir);
    }

    @Override
    public ApplicationDto get(String appName) {
        return new ApplicationDto(getAppDir(appName).getName());
    }

    @Override
    public Collection<ApplicationDto> getAll() {
        List<File> files = allAppDirs();
        return files.stream()
                .map(file -> new ApplicationDto(file.getName()))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
