package config.repository;

import config.dtos.PropertyFileDto;

import java.util.Collection;

public interface PropertyFileRepository {
    Collection<PropertyFileDto> getAll(String appName);
    PropertyFileDto get(String appName, String fileName);
    void update(String appName, String fileName, PropertyFileDto propertyFileDto);

    Collection<PropertyFileDto> getAll(String appName, String versionName);
    PropertyFileDto get(String appName, String versionName, String fileName);
    void update(String appName, String versionName, String fileName, PropertyFileDto propertyFileDto);

    Collection<PropertyFileDto> getAll(String appName, String versionName, String instanceName);
    PropertyFileDto get(String appName, String versionName, String instanceName, String fileName);
    void update(String appName, String versionName, String instanceName, String fileName, PropertyFileDto propertyFileDto);
}
