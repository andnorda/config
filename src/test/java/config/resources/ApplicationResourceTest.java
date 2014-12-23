package config.resources;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import config.dtos.ApplicationDto;
import config.dtos.PropertyFileDto;
import config.fakes.FakeApplicationRepository;
import config.fakes.FakePropertyFileRepository;
import config.servies.PropertyFileServiceImpl;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ApplicationResourceTest {

    private FakeApplicationRepository applicationRepository;
    private FakePropertyFileRepository propertyFileRepository;
    private ApplicationResource resource;

    @Before
    public void setUp() throws Exception {
        applicationRepository = new FakeApplicationRepository();
        propertyFileRepository = new FakePropertyFileRepository();
        resource = new ApplicationResource(applicationRepository, new PropertyFileServiceImpl(propertyFileRepository));
    }

    @Test
    public void getOne() throws Exception {
        // Given
        ApplicationDto applicationDto = new ApplicationDto("app name", ImmutableList.of("version"), ImmutableList.of("file"));
        applicationRepository.add("app name", applicationDto);

        // Then
        assertThat(resource.get("app name"), is(applicationDto));
    }

    @Test
    public void getAll() throws Exception {
        // Given
        ApplicationDto app1 = new ApplicationDto("app1", ImmutableList.of("version"), ImmutableList.of("file"));
        ApplicationDto app2 = new ApplicationDto("app2", ImmutableList.of("version"), ImmutableList.of("file"));
        applicationRepository.add("app1", app1);
        applicationRepository.add("app2", app2);

        // Then
        assertThat(resource.getAll(), hasItems(app1, app2));
    }

    @Test
    public void getFile() throws Exception {
        // Given
        PropertyFileDto propertyFileDto = new PropertyFileDto("file", ImmutableMap.of("key", "value"));
        propertyFileRepository.add(propertyFileDto, "app");

        // Then
        assertThat(resource.getFile("app", "file"), is(propertyFileDto));
    }

    @Test
    public void getAllFiles() throws Exception {
        // Given
        PropertyFileDto propertyFile1 = new PropertyFileDto("file1", ImmutableMap.of("key", "value"));
        PropertyFileDto propertyFile2 = new PropertyFileDto("file2", ImmutableMap.of("key", "value"));
        propertyFileRepository.add(propertyFile1, "app");
        propertyFileRepository.add(propertyFile2, "app");

        // Then
        assertThat(resource.getAllFiles("app"), hasItems(propertyFile1, propertyFile2));
    }

    @Test
    public void changesPropertyValue() throws Exception {
        // Given
        propertyFileRepository.add(new PropertyFileDto("file", ImmutableMap.of("key", "old")), "app");

        // When
        resource.changeProperty("app", "file", "key", "new");

        // Then
        assertThat(resource.getFile("app", "file"), is(new PropertyFileDto("file", ImmutableMap.of("key", "new"))));
    }
}
