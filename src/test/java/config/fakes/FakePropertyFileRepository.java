package config.fakes;

import config.dtos.PropertyFileDto;
import config.repository.PropertyFileRepository;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class FakePropertyFileRepository implements PropertyFileRepository {

    @Override
    public Collection<PropertyFileDto> getAll(String... path) {
        return files.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(StringUtils.join(path, "")))
                .map(Map.Entry::getValue)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private final Map<String, PropertyFileDto> files = new HashMap<>();

    public void add(PropertyFileDto propertyFileDto, String... path) {
        files.put(StringUtils.join(path, "") + propertyFileDto.getName(), propertyFileDto);
    }

    @Override
    public PropertyFileDto get(String... path) {
        PropertyFileDto propertyFileDto = files.get(StringUtils.join(path, ""));
        return new PropertyFileDto(propertyFileDto.getName(), new HashMap<>(propertyFileDto.getProperties()));
    }

    @Override
    public void update(String appName, String fileName, PropertyFileDto propertyFileDto) {
        files.put(appName + fileName, propertyFileDto);
    }

    @Override
    public void update(String appName, String versionName, String fileName, PropertyFileDto propertyFileDto) {
        files.put(appName + versionName + fileName, propertyFileDto);
    }

    @Override
    public void update(String appName, String versionName, String instanceName, String fileName, PropertyFileDto propertyFileDto) {
        files.put(appName + versionName + instanceName + fileName, propertyFileDto);
    }
}
