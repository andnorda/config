package config.resources;

import config.dtos.PropertyFileDto;
import config.repository.PropertyFileRepository;

import java.util.Collection;

public class PropertyFileResource {
    private PropertyFileRepository repository;

    public PropertyFileResource(PropertyFileRepository repository) {
        this.repository = repository;
    }

    public PropertyFileDto getApplicationPropertyFile(String appName, String fileName) {
        return repository.getApplicationPropertyFile(appName, fileName);
    }

    public Collection<PropertyFileDto> getAllApplicationPropertyFiles(String appName) {
        return repository.getAllApplicationPropertyFiles(appName);
    }

    public PropertyFileDto getVersionPropertyFile(String appName, String versionName, String fileName) {
        return repository.getVersionPropertyFile(appName, versionName, fileName);
    }

    public Collection<PropertyFileDto> getAllVersionPropertyFiles(String appName, String versionName) {
        return repository.getAllVersionPropertyFiles(appName, versionName);
    }

    public PropertyFileDto getInstancePropertyFile(String appName, String versionName, String instanceName, String fileName) {
        return repository.getInstancePropertyFiles(appName, versionName, instanceName, fileName);
    }

    public Collection<PropertyFileDto> getAllInstancePropertyFiles(String appName, String versionName, String instanceName) {
        return repository.getAllInstancePropertyFiles(appName, versionName, instanceName);
    }
}
