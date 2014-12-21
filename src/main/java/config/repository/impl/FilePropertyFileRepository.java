package config.repository.impl;

import config.dtos.PropertyFileDto;
import config.repository.PropertyFileRepository;

import java.io.File;
import java.util.Collection;

public class FilePropertyFileRepository extends FileRepository implements PropertyFileRepository {
    public FilePropertyFileRepository(File baseDir) {
        super(baseDir);
    }

    @Override
    public PropertyFileDto get(String appName, String fileName) {
        File app = getAppDir(appName);
        return getPropertyFileWithName(app, fileName);
    }

    @Override
    public void update(String appName, String fileName, PropertyFileDto propertyFileDto) {

    }

    @Override
    public Collection<PropertyFileDto> getAll(String appName) {
        File app = getAppDir(appName);
        return getAllPropertyFiles(app);
    }

    @Override
    public PropertyFileDto get(String appName, String versionName, String fileName) {
        File app = getAppDir(appName);
        File version = getSubDirWithName(app, versionName);
        return getPropertyFileWithName(version, fileName);
    }

    @Override
    public Collection<PropertyFileDto> getAll(String appName, String versionName) {
        File app = getAppDir(appName);
        File version = getSubDirWithName(app, versionName);
        return getAllPropertyFiles(version);
    }

    @Override
    public PropertyFileDto get(String appName, String versionName, String instanceName, String fileName) {
        File app = getAppDir(appName);
        File version = getSubDirWithName(app, versionName);
        File instance = getSubDirWithName(version, instanceName);
        return getPropertyFileWithName(instance, fileName);
    }

    @Override
    public Collection<PropertyFileDto> getAll(String appName, String versionName, String instanceName) {
        File app = getAppDir(appName);
        File version = getSubDirWithName(app, versionName);
        File instance = getSubDirWithName(version, instanceName);
        return getAllPropertyFiles(instance);
    }
}
