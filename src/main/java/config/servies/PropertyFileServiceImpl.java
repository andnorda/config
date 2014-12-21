package config.servies;

import config.dtos.PropertyFileDto;
import config.exceptions.NotFound;
import config.repository.PropertyFileRepository;

import java.util.Collection;
import java.util.Map;

public class PropertyFileServiceImpl implements PropertyFileService {
    private PropertyFileRepository repo;

    public PropertyFileServiceImpl(PropertyFileRepository repo) {
        this.repo = repo;
    }

    @Override
    public Collection<PropertyFileDto> getAll(String appName) {
        return repo.getAll(appName);
    }

    @Override
    public PropertyFileDto get(String appName, String fileName) {
        return repo.get(appName, fileName);
    }

    @Override
    public void changeProperty(String appName, String fileName, String propertyKey, String propertyValue) {
        PropertyFileDto propertyFileDto = repo.get(appName, fileName);
        Map<String, String> properties = updateProperty(propertyKey, propertyValue, propertyFileDto);
        repo.update(appName, fileName, new PropertyFileDto(propertyFileDto.getName(), properties));
    }

    @Override
    public Collection<PropertyFileDto> getAll(String appName, String versionName) {
        return repo.getAll(appName, versionName);
    }

    @Override
    public PropertyFileDto get(String appName, String versionName, String fileName) {
        return repo.get(appName, versionName, fileName);
    }

    @Override
    public void changeProperty(String appName, String versionName, String fileName, String propertyKey, String propertyValue) {
        PropertyFileDto propertyFileDto = repo.get(appName, versionName, fileName);
        Map<String, String> properties = updateProperty(propertyKey, propertyValue, propertyFileDto);
        repo.update(appName, versionName, fileName, new PropertyFileDto(propertyFileDto.getName(), properties));
    }

    @Override
    public Collection<PropertyFileDto> getAll(String appName, String versionName, String instanceName) {
        return repo.getAll(appName, versionName, instanceName);
    }

    @Override
    public PropertyFileDto get(String appName, String versionName, String instanceName, String fileName) {
        return repo.get(appName, versionName, instanceName, fileName);
    }

    @Override
    public void changeProperty(String appName, String versionName, String instanceName, String fileName, String propertyKey, String propertyValue) {
        PropertyFileDto propertyFileDto = repo.get(appName, versionName, instanceName, fileName);
        Map<String, String> properties = updateProperty(propertyKey, propertyValue, propertyFileDto);
        repo.update(appName, versionName, instanceName, fileName, new PropertyFileDto(propertyFileDto.getName(), properties));
    }

    private Map<String, String> updateProperty(String propertyKey, String propertyValue, PropertyFileDto propertyFileDto) {
        Map<String, String> properties = propertyFileDto.getProperties();
        if (!properties.containsKey(propertyKey)) {
            throw new NotFound();
        }
        properties.put(propertyKey, propertyValue);
        return properties;
    }
}
