package config.fakes;

import config.dtos.PropertyFileDto;
import config.repository.PropertyFileRepository;

import java.util.*;
import java.util.stream.Collectors;

public class FakePropertyFileRepository implements PropertyFileRepository {
    private final Map<String, PropertyFileDto> applicationPropertyFiles = new HashMap<>();

    public void add(String appName, String fileName, PropertyFileDto propertyFileDto) {
        applicationPropertyFiles.put(appName + fileName, propertyFileDto);
    }

    @Override
    public PropertyFileDto get(String appName, String fileName) {
        return new PropertyFileDto(fileName, new HashMap<>(applicationPropertyFiles.get(appName + fileName).getProperties()));
    }

    @Override
    public void update(String appName, String fileName, PropertyFileDto propertyFileDto) {
        applicationPropertyFiles.put(appName + fileName, propertyFileDto);
    }

    @Override
    public Collection<PropertyFileDto> getAll(String appName) {
        return applicationPropertyFiles.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(appName))
                .map(Map.Entry::getValue)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private final Map<String, PropertyFileDto> versionPropertyFiles = new HashMap<>();

    public void add(String appName, String versionName, String fileName, PropertyFileDto propertyFileDto) {
        versionPropertyFiles.put(appName + versionName + fileName, propertyFileDto);
    }

    @Override
    public PropertyFileDto get(String appName, String versionName, String fileName) {
        return new PropertyFileDto(fileName, new HashMap<>(versionPropertyFiles.get(appName + versionName + fileName).getProperties()));
    }

    @Override
    public void update(String appName, String versionName, String fileName, PropertyFileDto propertyFileDto) {
        versionPropertyFiles.put(appName + versionName + fileName, propertyFileDto);
    }

    @Override
    public Collection<PropertyFileDto> getAll(String appName, String versionName) {
        return versionPropertyFiles.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(appName + versionName))
                .map(Map.Entry::getValue)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private final Map<String, PropertyFileDto> instancePropertyFiles = new HashMap<>();

    public void add(String appName, String versionName, String instanceName, String fileName, PropertyFileDto propertyFileDto) {
        instancePropertyFiles.put(appName + versionName + instanceName + fileName, propertyFileDto);
    }

    @Override
    public PropertyFileDto get(String appName, String versionName, String instanceName, String fileName) {
        return new PropertyFileDto(fileName, new HashMap<>(instancePropertyFiles.get(appName + versionName + instanceName + fileName).getProperties()));
    }

    @Override
    public void update(String appName, String versionName, String instanceName, String fileName, PropertyFileDto propertyFileDto) {
        instancePropertyFiles.put(appName + versionName + instanceName + fileName, propertyFileDto);
    }

    @Override
    public List<PropertyFileDto> getAll(String appName, String versionName, String instanceName) {
        return instancePropertyFiles.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(appName + versionName + instanceName))
                .map(Map.Entry::getValue)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
