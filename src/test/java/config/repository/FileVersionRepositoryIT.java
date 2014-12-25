package config.repository;

import config.dtos.VersionDto;
import org.hamcrest.core.IsCollectionContaining;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FileVersionRepositoryIT {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private VersionRepository repo;

    @Before
    public void setUp() throws Exception {
        repo = new FileVersionRepository(new FileRepository(folder.newFolder("repo")));

        folder.newFolder("repo", "app", "version");
        folder.newFolder("repo", "app", "version", "instance1");
        folder.newFolder("repo", "app", "version", "instance2");
        folder.newFile("repo/app/version/file1");
        folder.newFile("repo/app/version/file2");
    }

    @Test
    public void returnVersion() throws Exception {
        // When
        VersionDto version = repo.get("app", "version");

        // Then
        assertThat(version.getName(), is("version"));
        assertThat(version.getInstances(), hasItems("instance1", "instance2"));
        assertThat(version.getPropertyFiles(), IsCollectionContaining.hasItems("file1", "file2"));
    }

    @Test
    public void returnsAllVersions() throws Exception {
        // When
        Collection<VersionDto> versions = repo.getAll("app");

        // Then
        assertThat(versions.size(), is(1));

        VersionDto version = versions.iterator().next();
        assertThat(version.getName(), is("version"));
        assertThat(version.getInstances(), hasItems("instance1", "instance2"));
        assertThat(version.getPropertyFiles(), hasItems("file1", "file2"));
    }
}
