package config.repository.impl;

import config.dtos.PropertyFileDto;
import config.repository.PropertyFileRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class FilePropertyFileRepository extends FileRepository implements PropertyFileRepository {
    private final File baseDir;

    public FilePropertyFileRepository(File baseDir) {
        super(baseDir);
        this.baseDir = baseDir;
    }

    @Override
    public PropertyFileDto get(String... path) {
        List<String> dirs = Arrays.asList(path);
        String fileName = dirs.get(dirs.size() - 1);
        dirs = dirs.subList(0, dirs.size() - 1);
        File file = baseDir;
        for (String dir : dirs) {
            file = getSubDirWithName(file, dir);
        }
        return getPropertyFileWithName(file, fileName);
    }

    @Override
    public Collection<PropertyFileDto> getAll(String... path) {
        List<String> dirs = Arrays.asList(path);
        File file = baseDir;
        for (String dir : dirs) {
            file = getSubDirWithName(file, dir);
        }
        return getAllPropertyFiles(file);
    }

    @Override
    public void update(String appName, String fileName, PropertyFileDto propertyFileDto) {
        File app = getAppDir(appName);
        getPropertyFileWithName(app, fileName);
        writeNewPropertiesToFile(app, fileName, propertyFileDto);
    }

    @Override
    public void update(String appName, String versionName, String fileName, PropertyFileDto propertyFileDto) {
        File app = getAppDir(appName);
        File version = getSubDirWithName(app, versionName);
        getPropertyFileWithName(version, fileName);
        writeNewPropertiesToFile(version, fileName, propertyFileDto);
    }

    @Override
    public void update(String appName, String versionName, String instanceName, String fileName, PropertyFileDto propertyFileDto) {
        File app = getAppDir(appName);
        File version = getSubDirWithName(app, versionName);
        File instance = getSubDirWithName(version, instanceName);
        getPropertyFileWithName(instance, fileName);
        writeNewPropertiesToFile(instance, fileName, propertyFileDto);
    }

    private void writeNewPropertiesToFile(File parent, String fileName, PropertyFileDto propertyFileDto) {
        PrintWriter writer;
        try {
            writer = new PrintWriter(parent.getPath() + "/" + fileName, "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        for (Map.Entry entry : propertyFileDto.getProperties().entrySet()) {
            writer.println(entry.getKey() + "=" + entry.getValue());
        }
        writer.close();
    }
}
