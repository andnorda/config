package config;

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
