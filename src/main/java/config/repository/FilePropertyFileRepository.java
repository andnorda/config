package config.repository;

import config.dtos.PropertyFileDto;
import config.exceptions.NotFound;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FilePropertyFileRepository extends FileRepository implements PropertyFileRepository {
    public FilePropertyFileRepository(File baseDir) {
        super(baseDir);
    }

    @Override
    public PropertyFileDto getApplicationPropertyFile(String appName, String fileName) {
        File app = getAppDir(appName);
        return getPropertyFileWithName(app, fileName);
    }

    @Override
    public Collection<PropertyFileDto> getAllApplicationPropertyFiles(String appName) {
        File app = getAppDir(appName);
        return getAllPropertyFiles(app);
    }

    @Override
    public PropertyFileDto getVersionPropertyFile(String appName, String versionName, String fileName) {
        File app = getAppDir(appName);
        File version = getSubDirWithName(app, versionName);
        return getPropertyFileWithName(version, fileName);
    }

    @Override
    public Collection<PropertyFileDto> getAllVersionPropertyFiles(String appName, String versionName) {
        File app = getAppDir(appName);
        File version = getSubDirWithName(app, versionName);
        return getAllPropertyFiles(version);
    }

    @Override
    public PropertyFileDto getInstancePropertyFiles(String appName, String versionName, String instanceName, String fileName) {
        File app = getAppDir(appName);
        File version = getSubDirWithName(app, versionName);
        File instance = getSubDirWithName(version, instanceName);
        return getPropertyFileWithName(instance, fileName);
    }

    @Override
    public Collection<PropertyFileDto> getAllInstancePropertyFiles(String appName, String versionName, String instanceName) {
        File app = getAppDir(appName);
        File version = getSubDirWithName(app, versionName);
        File instance = getSubDirWithName(version, instanceName);
        return getAllPropertyFiles(instance);
    }
}
