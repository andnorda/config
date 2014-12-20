package config;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FakeApplicationRepository implements ApplicationRepository {
    private Map<String, ApplicationDto> apps = new HashMap<String, ApplicationDto>();

    public void add(String name, ApplicationDto applicationDto) {
        apps.put(name, applicationDto);
    }

    @Override
    public ApplicationDto get(String name) {
        return apps.get(name);
    }

    @Override
    public Collection<ApplicationDto> getAll() {
        return apps.values();
    }
}
