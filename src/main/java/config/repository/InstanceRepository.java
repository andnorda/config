package config.repository;

import config.dtos.InstanceDto;

import java.util.Collection;

public interface InstanceRepository {
    InstanceDto get(String appName, String versionName, String instanceName);
    Collection<InstanceDto> getAll(String appName, String versionName);
}
