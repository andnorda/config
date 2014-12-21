package config.repository;

import config.dtos.VersionDto;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class FileVersionRepository extends FileRepository implements VersionRepository {

    public FileVersionRepository(File baseDir) {
        super(baseDir);
    }

    @Override
    public VersionDto get(String appName, String versionName) {
        File app = getAppDir(appName);
        File version = getSubDirWithName(app, versionName);
        return new VersionDto(version.getName(), listSubDirNames(version));
    }

    @Override
    public Collection<VersionDto> getAll(String appName) {
        File app = getAppDir(appName);
        return listSubDirs(app).stream()
                .map(file -> new VersionDto(file.getName(), listSubDirNames(file)))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
