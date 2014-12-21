package config.repository.impl;

import config.dtos.InstanceDto;
import config.repository.InstanceRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class FileInstanceRepository extends FileRepository implements InstanceRepository {
    public FileInstanceRepository(File baseDir) {
        super(baseDir);
    }

    @Override
    public InstanceDto get(String appName, String versionName, String instanceName) {
        File app = getAppDir(appName);
        File version = getSubDirWithName(app, versionName);
        File instance = getSubDirWithName(version, instanceName);
        return new InstanceDto(instance.getName(), listPropertyFileNames(instance));
    }

    @Override
    public Collection<InstanceDto> getAll(String appName, String versionName) {
        File app = getAppDir(appName);
        File version = getSubDirWithName(app, versionName);
        return listSubDirs(version).stream()
                .map(file -> new InstanceDto(file.getName(), listPropertyFileNames(file)))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}