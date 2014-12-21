package config.repository;

import config.dtos.VersionDto;
import config.exceptions.NotFound;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;

public class FileVersionRepository implements VersionRepository {
    private final File baseDir;

    public FileVersionRepository(File baseDir) {
        this.baseDir = baseDir;
    }

    @Override
    public VersionDto get(String appName, String versionName) {
        return null;
    }

    @Override
    public Collection<VersionDto> getAll(String appName) {
        File[] files = baseDir.listFiles(file -> file.isDirectory() && file.getName().equals(appName));
        if (files == null) {
            throw new NotFound();
        }
        return new HashSet<>();
    }
}
