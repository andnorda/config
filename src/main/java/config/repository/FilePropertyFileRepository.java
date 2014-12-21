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
        List<File> propertyFiles = Arrays.asList(app.listFiles(file -> file.getName().equals(fileName + ".properties") && file.isFile()));
        if (propertyFiles.isEmpty()) {
            throw new NotFound();
        }
        return new PropertyFileDto(propertyFiles.get(0).getName().replace(".properties", ""));
    }

    @Override
    public Collection<PropertyFileDto> getAllApplicationPropertyFiles(String appName) {
        File app = getAppDir(appName);
        List<File> propertyFiles = Arrays.asList(app.listFiles(file -> file.isFile() && file.getName().endsWith(".properties")));
        return propertyFiles.stream()
                .map(file -> new PropertyFileDto(file.getName().replace(".properties", "")))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public PropertyFileDto getVersionPropertyFile(String appName, String versionName, String fileName) {
        File app = getAppDir(appName);
        File version = getSubDirWithName(app, versionName);
        List<File> propertyFiles = Arrays.asList(version.listFiles(file -> file.getName().equals(fileName + ".properties") && file.isFile()));
        if (propertyFiles.isEmpty()) {
            throw new NotFound();
        }
        return new PropertyFileDto(propertyFiles.get(0).getName().replace(".properties", ""));
    }

    @Override
    public Collection<PropertyFileDto> getAllVersionPropertyFiles(String appName, String versionName) {
        return null;
    }

    @Override
    public PropertyFileDto getInstancePropertyFiles(String appName, String versionName, String instanceName, String fileName) {
        return null;
    }

    @Override
    public List<PropertyFileDto> getAllInstancePropertyFiles(String appName, String versionName, String instanceName) {
        return null;
    }
}
