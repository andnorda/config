package config.repository;

import com.google.common.collect.ImmutableMap;
import config.dtos.PropertyFileDto;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FilePropertyFileRepositoryIT {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private PropertyFileRepository repo;

    @Before
    public void setUp() throws Exception {
        repo = new FilePropertyFileRepository(new FileRepository(folder.newFolder("repo")));

        folder.newFolder("repo", "app", "version", "instance");
        folder.newFile("repo/app/file");
        folder.newFile("repo/app/version/file");
        folder.newFile("repo/app/version/instance/file");
    }

    @Test
    public void returnsPropertyFile() throws Exception {
        // When
        PropertyFileDto file = new PropertyFileDto("file", ImmutableMap.of());

        // Then
        assertThat(repo.get("app", "file"), is(file));
        assertThat(repo.get("app", "version", "file"), is(file));
        assertThat(repo.get("app", "version", "instance", "file"), is(file));
    }

    @Test
    public void returnsPropertyFiles() throws Exception {
        // When
        PropertyFileDto file = new PropertyFileDto("file", ImmutableMap.of());

        // Then
        assertThat(repo.getAll("app"), hasItems(file));
        assertThat(repo.getAll("app", "version"), hasItems(file));
        assertThat(repo.getAll("app", "version", "instance"), hasItems(file));
    }

    @Test
    public void updatesProperty() throws Exception {
        // When
        PropertyFileDto files = new PropertyFileDto("file", ImmutableMap.of("key", "new"));
        repo.update(files, "app", "version", "instance", "file");

        // Then
        assertThat(repo.get("app", "version", "instance", "file"), is(files));
    }
}
