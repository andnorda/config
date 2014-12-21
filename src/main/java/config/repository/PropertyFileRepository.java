package config.repository;

import config.dtos.PropertyFileDto;

import java.util.Collection;

public interface PropertyFileRepository {
    PropertyFileDto getApplicationPropertyFile(String appName, String fileName);
    Collection<PropertyFileDto> getAllApplicationPropertyFiles(String appName);

    PropertyFileDto getVersionPropertyFile(String appName, String versionName, String fileName);
    Collection<PropertyFileDto> getAllVersionPropertyFiles(String appName, String versionName);

    PropertyFileDto getInstancePropertyFiles(String appName, String versionName, String instanceName, String fileName);
    Collection<PropertyFileDto> getAllInstancePropertyFiles(String appName, String versionName, String instanceName);
}
