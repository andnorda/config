package config.servies;

import config.dtos.PropertyFileDto;

import java.util.Collection;

public interface PropertyFileService {
    Collection<PropertyFileDto> getAll(String appName);
    PropertyFileDto get(String appName, String fileName);
    void changeProperty(String appName, String fileName, String propertyName, String propertyValue);

    Collection<PropertyFileDto> getAll(String appName, String versionName);
    PropertyFileDto get(String appName, String versionName, String fileName);
    void changeProperty(String appName, String versionName, String fileName, String propertyKey, String propertyValue);

    Collection<PropertyFileDto> getAll(String appName, String versionName, String instanceName);
    PropertyFileDto get(String appName, String versionName, String instanceName, String fileName);
    void changeProperty(String appName, String versionName, String instanceName, String fileName, String propertyKey, String propertyValue);
}
