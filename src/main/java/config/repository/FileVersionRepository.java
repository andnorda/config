package config.repository;

import config.dtos.VersionDto;
import config.exceptions.NotFound;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FileVersionRepository implements VersionRepository {
    private final File baseDir;

    public FileVersionRepository(File baseDir) {
        this.baseDir = baseDir;
    }

    @Override
    public VersionDto get(String appName, String versionName) {
        File app = getAppDir(appName);
        List<File> versions = Arrays.asList(app.listFiles(dirWithName(versionName)));
        if (versions.isEmpty()) {
            throw new NotFound();
        }
        return versions.stream()
                .map(file -> new VersionDto(file.getName())).findFirst().get();
    }

    @Override
    public Collection<VersionDto> getAll(String appName) {
        File app = getAppDir(appName);
        return Arrays.asList(app.listFiles(File::isDirectory)).stream()
                .map(file -> new VersionDto(file.getName()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private File getAppDir(String appName) {
        File[] files = baseDir.listFiles(dirWithName(appName));
        if (files.length == 0) {
            throw new NotFound();
        }
        return files[0];
    }

    private FileFilter dirWithName(String name) {
        return file -> file.isDirectory() && file.getName().equals(name);
    }
}
