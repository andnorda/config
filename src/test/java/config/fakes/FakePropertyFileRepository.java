package config.fakes;

import config.dtos.PropertyFileDto;
import config.repository.PropertyFileRepository;

import java.util.*;
import java.util.stream.Collectors;

public class FakePropertyFileRepository implements PropertyFileRepository {
    private final Map<String, PropertyFileDto> applicationPropertyFiles = new HashMap<>();

    public void addApplicationPropertyFile(String appName, String fileName, PropertyFileDto propertyFileDto) {
        applicationPropertyFiles.put(appName + fileName, propertyFileDto);
    }

    @Override
    public PropertyFileDto getApplicationPropertyFile(String appName, String fileName) {
        return applicationPropertyFiles.get(appName + fileName);
    }

    @Override
    public Collection<PropertyFileDto> getAllApplicationPropertyFiles(String appName) {
        return applicationPropertyFiles.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(appName))
                .map(Map.Entry::getValue)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private final Map<String, PropertyFileDto> versionPropertyFiles = new HashMap<>();

    public void addVersionPropertyFile(String appName, String versionName, String fileName, PropertyFileDto propertyFileDto) {
        versionPropertyFiles.put(appName + versionName + fileName, propertyFileDto);
    }

    @Override
    public PropertyFileDto getVersionPropertyFile(String appName, String versionName, String fileName) {
        return versionPropertyFiles.get(appName + versionName + fileName);
    }

    @Override
    public Collection<PropertyFileDto> getAllVersionPropertyFiles(String appName, String versionName) {
        return versionPropertyFiles.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(appName + versionName))
                .map(Map.Entry::getValue)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private final Map<String, PropertyFileDto> instancePropertyFiles = new HashMap<>();

    public void addInstancePropertyFile(String appName, String versionName, String instanceName, String fileName, PropertyFileDto propertyFileDto) {
        instancePropertyFiles.put(appName + versionName + instanceName + fileName, propertyFileDto);
    }

    @Override
    public PropertyFileDto getInstancePropertyFiles(String appName, String versionName, String instanceName, String fileName) {
        return instancePropertyFiles.get(appName + versionName + instanceName + fileName);
    }

    @Override
    public List<PropertyFileDto> getAllInstancePropertyFiles(String appName, String versionName, String instanceName) {
        return instancePropertyFiles.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(appName + versionName + instanceName))
                .map(Map.Entry::getValue)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
