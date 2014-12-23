package config.repository;

import config.dtos.PropertyFileDto;

import java.util.Collection;

public interface PropertyFileRepository {
    Collection<PropertyFileDto> getAll(String... path);
    PropertyFileDto get(String... path);
    void update(String appName, String fileName, PropertyFileDto propertyFileDto);

    void update(String appName, String versionName, String fileName, PropertyFileDto propertyFileDto);

    void update(String appName, String versionName, String instanceName, String fileName, PropertyFileDto propertyFileDto);
}
