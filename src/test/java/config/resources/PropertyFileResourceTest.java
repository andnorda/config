package config.resources;

import config.dtos.PropertyFileDto;
import config.fakes.FakePropertyFileRepository;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

public class PropertyFileResourceTest {

    private PropertyFileResource resource;
    private FakePropertyFileRepository repo;

    @Before
    public void setUp() throws Exception {
        repo = new FakePropertyFileRepository();
        resource = new PropertyFileResource(repo);
    }

    @Test
    public void returnsApplicationPropertyFile() throws Exception {
        // Given
        PropertyFileDto propertyFileDto = new PropertyFileDto("file");
        repo.addApplicationPropertyFile("app", "file", propertyFileDto);

        // Then
        assertThat(resource.getApplicationPropertyFile("app", "file"), is(propertyFileDto));
    }

    @Test
    public void returnsAllApplicationPropertyFiles() throws Exception {
        // Given
        PropertyFileDto propertyFile1 = new PropertyFileDto("file1");
        PropertyFileDto propertyFile2 = new PropertyFileDto("file2");
        repo.addApplicationPropertyFile("app", "file1", propertyFile1);
        repo.addApplicationPropertyFile("app", "file2", propertyFile2);

        // Then
        assertThat(resource.getAllApplicationPropertyFiles("app"), hasItems(propertyFile1, propertyFile2));
    }

    @Test
    public void returnsVersionPropertyFile() throws Exception {
        // Given
        PropertyFileDto propertyFileDto = new PropertyFileDto("file");
        repo.addVersionPropertyFile("app", "version", "file", propertyFileDto);

        // Then
        assertThat(resource.getVersionPropertyFile("app", "version", "file"), is(propertyFileDto));
    }

    @Test
    public void returnsAllVersionPropertyFiles() throws Exception {
        // Given
        PropertyFileDto propertyFile1 = new PropertyFileDto("file1");
        PropertyFileDto propertyFile2 = new PropertyFileDto("file2");
        repo.addVersionPropertyFile("app", "version", "file1", propertyFile1);
        repo.addVersionPropertyFile("app", "version", "file2", propertyFile2);

        // Then
        assertThat(resource.getAllVersionPropertyFiles("app", "version"), hasItems(propertyFile1, propertyFile2));
    }

    @Test
    public void returnsInstancePropertyFile() throws Exception {
        // Given
        PropertyFileDto propertyFileDto = new PropertyFileDto("file");
        repo.addInstancePropertyFile("app", "version", "instance", "file", propertyFileDto);

        // Then
        assertThat(resource.getInstancePropertyFile("app", "version", "instance", "file"), is(propertyFileDto));
    }

    @Test
    public void returnsAllInstancePropertyFiles() throws Exception {
        // Given
        PropertyFileDto propertyFile1 = new PropertyFileDto("file1");
        PropertyFileDto propertyFile2 = new PropertyFileDto("file2");
        repo.addInstancePropertyFile("app", "version", "instance", "file1", propertyFile1);
        repo.addInstancePropertyFile("app", "version", "instance", "file2", propertyFile2);

        // Then
        assertThat(resource.getAllInstancePropertyFiles("app", "version", "instance"), hasItems(propertyFile1, propertyFile2));
    }
}
