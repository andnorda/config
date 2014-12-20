package config.fakes;

import config.dtos.VersionDto;
import config.repository.VersionRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FakeVersionRepository implements VersionRepository {
    private final Map<String, VersionDto> versions = new HashMap<>();

    public void add(String appName, String versionName, VersionDto versionDto) {
        versions.put(appName + versionName, versionDto);
    }

    @Override
    public VersionDto get(String appName, String versionName) {
        return versions.get(appName + versionName);
    }

    @Override
    public Collection<VersionDto> getAll(String appName) {
        return versions.values();
    }
}
