package config.resources;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import config.dtos.ApplicationDto;
import config.dtos.PropertyFileDto;
import config.fakes.FakeApplicationRepository;
import config.fakes.FakePropertyFileRepository;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsCollectionContaining;
import org.junit.Before;
import org.junit.Ignore;
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
        resource = new ApplicationResource(applicationRepository, propertyFileRepository);
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
    public void returnsApplicationPropertyFile() throws Exception {
        // Given
        PropertyFileDto propertyFileDto = new PropertyFileDto("file", ImmutableMap.of("key", "value"));
        propertyFileRepository.addApplicationPropertyFile("app", "file", propertyFileDto);

        // Then
        assertThat(resource.getApplicationPropertyFile("app", "file"), Is.is(propertyFileDto));
    }

    @Test
    public void returnsAllApplicationPropertyFiles() throws Exception {
        // Given
        PropertyFileDto propertyFile1 = new PropertyFileDto("file1", ImmutableMap.of("key", "value"));
        PropertyFileDto propertyFile2 = new PropertyFileDto("file2", ImmutableMap.of("key", "value"));
        propertyFileRepository.addApplicationPropertyFile("app", "file1", propertyFile1);
        propertyFileRepository.addApplicationPropertyFile("app", "file2", propertyFile2);

        // Then
        assertThat(resource.getAllApplicationPropertyFiles("app"), IsCollectionContaining.hasItems(propertyFile1, propertyFile2));
    }

    @Test
    @Ignore
    public void changesPropertyValue() throws Exception {
        // Given
        PropertyFileDto propertyFileDto = new PropertyFileDto("file", ImmutableMap.of("key", "old"));
        propertyFileRepository.addApplicationPropertyFile("app", "file", propertyFileDto);

        // When
        resource.changeProperty("app", "file", "key", "new");

        // Then
        assertThat(resource.getApplicationPropertyFile("app", "file").getProperties().values(), hasItems("new"));
    }
}
