package config.repository;

import config.dtos.VersionDto;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;

public class FileVersionRepository implements VersionRepository {
    public FileVersionRepository(File baseDir) {
    }

    @Override
    public VersionDto get(String appName, String versionName) {
        return null;
    }

    @Override
    public Collection<VersionDto> getAll(String appName) {
        return new HashSet<>();
    }
}
