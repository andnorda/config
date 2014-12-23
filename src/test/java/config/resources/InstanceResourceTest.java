package config.resources;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import config.dtos.InstanceDto;
import config.dtos.PropertyFileDto;
import config.fakes.FakeInstanceRepository;
import config.fakes.FakePropertyFileRepository;
import config.servies.PropertyFileServiceImpl;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;

public class InstanceResourceTest {

    private FakeInstanceRepository instanceRepository;
    private FakePropertyFileRepository propertyFileRepository;
    private InstanceResource resource;

    @Before
    public void setUp() throws Exception {
        instanceRepository = new FakeInstanceRepository();
        propertyFileRepository = new FakePropertyFileRepository();
        resource = new InstanceResource(instanceRepository, new PropertyFileServiceImpl(propertyFileRepository));
    }

    @Test
    public void getOne() throws Exception {
        // Given
        InstanceDto instanceDto = new InstanceDto("instance name", ImmutableList.of("file"));
        instanceRepository.add("app name", "version name", "instance name", instanceDto);

        // Then
        assertThat(resource.get("app name", "version name", "instance name"), is(instanceDto));
    }

    @Test
    public void getAll() throws Exception {
        // Given
        InstanceDto instance1 = new InstanceDto("instance1", ImmutableList.of("file"));
        InstanceDto instance2 = new InstanceDto("instance2", ImmutableList.of("file"));
        instanceRepository.add("app", "version", "instance1", instance1);
        instanceRepository.add("app", "version", "instance2", instance2);

        // Then
        assertThat(resource.getAll("app", "version"), hasItems(instance1, instance2));
    }

    @Test
    public void getFile() throws Exception {
        // Given
        PropertyFileDto propertyFileDto = new PropertyFileDto("file", ImmutableMap.of("key", "value"));
        propertyFileRepository.add(propertyFileDto, "app", "version", "instance");

        // Then
        Assert.assertThat(resource.getFile("app", "version", "instance", "file"), Is.is(propertyFileDto));
    }

    @Test
    public void getAllFiles() throws Exception {
        // Given
        PropertyFileDto propertyFile1 = new PropertyFileDto("file1", ImmutableMap.of("key", "value"));
        PropertyFileDto propertyFile2 = new PropertyFileDto("file2", ImmutableMap.of("key", "value"));
        propertyFileRepository.add(propertyFile1, "app", "version", "instance");
        propertyFileRepository.add(propertyFile2, "app", "version", "instance");

        // Then
        Assert.assertThat(resource.getAllFiles("app", "version", "instance"), hasItems(propertyFile1, propertyFile2));
    }

    @Test
    public void changesPropertyValue() throws Exception {
        // Given
        propertyFileRepository.add(new PropertyFileDto("file", ImmutableMap.of("key", "old")), "app", "version", "instance");

        // When
        resource.changeProperty("app", "version", "instance", "file", "key", "new");

        // Then
        Assert.assertThat(resource.getFile("app", "version", "instance", "file"), is(new PropertyFileDto("file", ImmutableMap.of("key", "new"))));
    }
}
