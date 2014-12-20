package config.resources;

import config.dtos.InstanceDto;
import config.dtos.VersionDto;
import config.fakes.FakeInstanceRepository;
import config.fakes.FakeVersionRepository;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;

public class InstanceResourceTest {

    private FakeInstanceRepository repo;
    private InstanceResource resource;

    @Before
    public void setUp() throws Exception {
        repo = new FakeInstanceRepository();
        resource = new InstanceResource(repo);
    }

    @Test
    public void getOne() throws Exception {
        // Given
        InstanceDto instanceDto = new InstanceDto("instance name");
        repo.add("app name", "version name", "instance name", instanceDto);

        // Then
        assertThat(resource.get("app name", "version name", "instance name"), is(instanceDto));
    }

    @Test
    public void getAll() throws Exception {
        // Given
        InstanceDto instance1 = new InstanceDto("instance1");
        InstanceDto instance2 = new InstanceDto("instance2");
        repo.add("app", "version", "instance1", instance1);
        repo.add("app", "version", "instance2", instance2);

        // Then
        assertThat(resource.getAll("app", "version"), hasItems(instance1, instance2));
    }
}
