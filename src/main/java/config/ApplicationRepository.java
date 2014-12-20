package config;

import java.util.Collection;

public interface ApplicationRepository {
    ApplicationDto get(String name);
    Collection<ApplicationDto> getAll();
}
