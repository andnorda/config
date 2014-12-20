package config.fakes;

import config.dtos.InstanceDto;
import config.repository.InstanceRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FakeInstanceRepository implements InstanceRepository {
    private final Map<String, InstanceDto> instances = new HashMap<>();

    public void add(String appName, String versionName, String instanceName, InstanceDto instanceDto) {
        instances.put(appName + versionName + instanceName, instanceDto);
    }

    @Override
    public InstanceDto get(String appName, String versionName, String instanceName) {
        return instances.get(appName + versionName + instanceName);
    }

    @Override
    public Collection<InstanceDto> getAll(String appName, String versionName) {
        return instances.values();
    }
}
