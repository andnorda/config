package config.resources;

import config.dtos.VersionDto;
import config.repository.VersionRepository;

import java.util.Collection;

public class VersionResource {
    private final VersionRepository repo;

    public VersionResource(VersionRepository repo) {
        this.repo = repo;
    }

    public VersionDto get(String appName, String versionName) {
        return repo.get(appName, versionName);
    }

    public Collection<VersionDto> getAll(String appName) {
        return repo.getAll(appName);
    }
}
