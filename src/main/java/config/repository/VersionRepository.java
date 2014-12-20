package config.repository;

import config.dtos.VersionDto;

import java.util.Collection;

public interface VersionRepository {
    VersionDto get(String appName, String versionName);
    Collection<VersionDto> getAll(String appName);
}
