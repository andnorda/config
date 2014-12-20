package config.resources;

import config.dtos.InstanceDto;
import config.repository.InstanceRepository;

import java.util.Collection;

public class InstanceResource {
    private final InstanceRepository repo;

    public InstanceResource(InstanceRepository repo) {
        this.repo = repo;
    }

    public InstanceDto get(String appName, String versionName, String instanceName) {
        return repo.get(appName, versionName, instanceName);
    }

    public Collection<InstanceDto> getAll(String appName, String versionName) {
        return repo.getAll(appName, versionName);
    }
}
