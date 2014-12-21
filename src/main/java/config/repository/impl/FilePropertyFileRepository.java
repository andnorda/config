package config.repository.impl;

import config.dtos.PropertyFileDto;
import config.repository.PropertyFileRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Map;

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
        File app = getAppDir(appName);
        getPropertyFileWithName(app, fileName);
        writeNewPropertiesToFile(app, fileName, propertyFileDto);
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
    public void update(String appName, String versionName, String fileName, PropertyFileDto propertyFileDto) {
        File app = getAppDir(appName);
        File version = getSubDirWithName(app, versionName);
        getPropertyFileWithName(version, fileName);
        writeNewPropertiesToFile(version, fileName, propertyFileDto);
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
    public void update(String appName, String versionName, String instanceName, String fileName, PropertyFileDto propertyFileDto) {
        File app = getAppDir(appName);
        File version = getSubDirWithName(app, versionName);
        File instance = getSubDirWithName(version, instanceName);
        getPropertyFileWithName(instance, fileName);
        writeNewPropertiesToFile(instance, fileName, propertyFileDto);
    }

    @Override
    public Collection<PropertyFileDto> getAll(String appName, String versionName, String instanceName) {
        File app = getAppDir(appName);
        File version = getSubDirWithName(app, versionName);
        File instance = getSubDirWithName(version, instanceName);
        return getAllPropertyFiles(instance);
    }

    private void writeNewPropertiesToFile(File parent, String fileName, PropertyFileDto propertyFileDto) {
        PrintWriter writer;
        try {
            writer = new PrintWriter(parent.getPath() + "/" + fileName + ".properties", "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        for (Map.Entry entry : propertyFileDto.getProperties().entrySet()) {
            writer.println(entry.getKey() + "=" + entry.getValue());
        }
        writer.close();
    }
}
