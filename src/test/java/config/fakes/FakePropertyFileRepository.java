package config.fakes;

import config.dtos.PropertyFileDto;
import config.repository.PropertyFileRepository;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class FakePropertyFileRepository implements PropertyFileRepository {

    private final Map<String, PropertyFileDto> files = new HashMap<>();

    public void add(PropertyFileDto propertyFileDto, String... path) {
        files.put(StringUtils.join(path, "") + propertyFileDto.getName(), propertyFileDto);
    }

    @Override
    public PropertyFileDto get(String appName, String fileName) {
        return new PropertyFileDto(fileName, new HashMap<>(files.get(appName + fileName).getProperties()));
    }

    @Override
    public void update(String appName, String fileName, PropertyFileDto propertyFileDto) {
        files.put(appName + fileName, propertyFileDto);
    }

    @Override
    public Collection<PropertyFileDto> getAll(String appName) {
        return files.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(appName))
                .map(Map.Entry::getValue)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public PropertyFileDto get(String appName, String versionName, String fileName) {
        return new PropertyFileDto(fileName, new HashMap<>(files.get(appName + versionName + fileName).getProperties()));
    }

    @Override
    public void update(String appName, String versionName, String fileName, PropertyFileDto propertyFileDto) {
        files.put(appName + versionName + fileName, propertyFileDto);
    }

    @Override
    public Collection<PropertyFileDto> getAll(String appName, String versionName) {
        return files.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(appName + versionName))
                .map(Map.Entry::getValue)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public PropertyFileDto get(String appName, String versionName, String instanceName, String fileName) {
        return new PropertyFileDto(fileName, new HashMap<>(files.get(appName + versionName + instanceName + fileName).getProperties()));
    }

    @Override
    public void update(String appName, String versionName, String instanceName, String fileName, PropertyFileDto propertyFileDto) {
        files.put(appName + versionName + instanceName + fileName, propertyFileDto);
    }

    @Override
    public List<PropertyFileDto> getAll(String appName, String versionName, String instanceName) {
        return files.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(appName + versionName + instanceName))
                .map(Map.Entry::getValue)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
