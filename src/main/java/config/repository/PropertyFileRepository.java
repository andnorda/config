package config.repository;

import config.dtos.PropertyFileDto;

import java.util.Collection;

public interface PropertyFileRepository {
    Collection<PropertyFileDto> getAll(String... path);
    PropertyFileDto get(String... path);
    void update(PropertyFileDto propertyFileDto, String... path);
}
