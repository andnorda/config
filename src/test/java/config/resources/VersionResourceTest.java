package config.resources;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import config.dtos.PropertyFileDto;
import config.dtos.VersionDto;
import config.fakes.FakePropertyFileRepository;
import config.fakes.FakeVersionRepository;
import config.servies.PropertyFileServiceImpl;
import org.hamcrest.CoreMatchers;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;

public class VersionResourceTest {

    private FakeVersionRepository versionRepository;
    private FakePropertyFileRepository propertyFileRepository;
    private VersionResource resource;

    @Before
    public void setUp() throws Exception {
        versionRepository = new FakeVersionRepository();
        propertyFileRepository = new FakePropertyFileRepository();
        resource = new VersionResource(versionRepository, new PropertyFileServiceImpl(propertyFileRepository));
    }

    @Test
    public void getOne() throws Exception {
        // Given
        VersionDto versionDto = new VersionDto("version name", ImmutableList.of("instance"), ImmutableList.of("file"));
        versionRepository.add("app name", "version name", versionDto);

        // Then
        assertThat(resource.get("app name", "version name"), is(versionDto));
    }

    @Test
    public void getAll() throws Exception {
        // Given
        VersionDto version1 = new VersionDto("version1", ImmutableList.of("instance"), ImmutableList.of("file"));
        VersionDto version2 = new VersionDto("version2", ImmutableList.of("instance"), ImmutableList.of("file"));
        versionRepository.add("app", "version1", version1);
        versionRepository.add("app", "version2", version2);

        // Then
        assertThat(resource.getAll("app"), hasItems(version1, version2));
    }

    @Test
    public void returnsVersionPropertyFile() throws Exception {
        // Given
        PropertyFileDto propertyFileDto = new PropertyFileDto("file", ImmutableMap.of("key", "value"));
        propertyFileRepository.addVersionPropertyFile("app", "version", "file", propertyFileDto);

        // Then
        Assert.assertThat(resource.getFile("app", "version", "file"), Is.is(propertyFileDto));
    }

    @Test
    public void returnsAllVersionPropertyFiles() throws Exception {
        // Given
        PropertyFileDto propertyFile1 = new PropertyFileDto("file1", ImmutableMap.of("key", "value"));
        PropertyFileDto propertyFile2 = new PropertyFileDto("file2", ImmutableMap.of("key", "value"));
        propertyFileRepository.addVersionPropertyFile("app", "version", "file1", propertyFile1);
        propertyFileRepository.addVersionPropertyFile("app", "version", "file2", propertyFile2);

        // Then
        Assert.assertThat(resource.getAllFiles("app", "version"), hasItems(propertyFile1, propertyFile2));
    }

    @Test
    public void changesPropertyValue() throws Exception {
        // Given
        propertyFileRepository.addVersionPropertyFile("app", "version", "file", new PropertyFileDto("file", ImmutableMap.of("key", "old")));

        // When
        resource.changeProperty("app", "version", "file", "key", "new");

        // Then
        Assert.assertThat(resource.getFile("app", "version", "file"), is(new PropertyFileDto("file", ImmutableMap.of("key", "new"))));
    }
}
