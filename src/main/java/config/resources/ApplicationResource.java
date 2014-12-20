package config.resources;

import config.dtos.ApplicationDto;
import config.repository.ApplicationRepository;

import java.util.Collection;

public class ApplicationResource {
    private ApplicationRepository applicationRepository;

    public ApplicationResource(ApplicationRepository repository) {
        this.applicationRepository = repository;
    }

    public ApplicationDto get(String name) {
        return applicationRepository.get(name);
    }

    public Collection<ApplicationDto> getAll() {
        return applicationRepository.getAll();
    }
}
