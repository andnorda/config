package config.resources;

import config.fakes.FakeVersionRepository;
import config.dtos.VersionDto;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;

public class VersionResourceTest {

    private FakeVersionRepository repo;
    private VersionResource resource;

    @Before
    public void setUp() throws Exception {
        repo = new FakeVersionRepository();
        resource = new VersionResource(repo);
    }

    @Test
    public void getOne() throws Exception {
        // Given
        VersionDto versionDto = new VersionDto("version name");
        repo.add("app name", "version name", versionDto);

        // Then
        assertThat(resource.get("app name", "version name"), is(versionDto));
    }

    @Test
    public void getAll() throws Exception {
        // Given
        VersionDto version1 = new VersionDto("version1");
        VersionDto version2 = new VersionDto("version2");
        repo.add("app", "version1", version1);
        repo.add("app", "version2", version2);

        // Then
        assertThat(resource.getAll("app"), hasItems(version1, version2));
    }

}
