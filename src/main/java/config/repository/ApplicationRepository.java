package config.repository;

import config.dtos.ApplicationDto;

import java.util.Collection;

public interface ApplicationRepository {
    ApplicationDto get(String name);
    Collection<ApplicationDto> getAll();
}
