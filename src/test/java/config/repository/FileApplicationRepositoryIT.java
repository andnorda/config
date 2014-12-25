package config.repository;

import config.dtos.ApplicationDto;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

public class FileApplicationRepositoryIT {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private ApplicationRepository repo;

    @Before
    public void setUp() throws Exception {
        repo = new FileApplicationRepository(new FileRepository(folder.newFolder("repo")));

        folder.newFolder("repo", "app");
        folder.newFolder("repo", "app", "version1");
        folder.newFolder("repo", "app", "version2");
        folder.newFile("repo/app/file1");
        folder.newFile("repo/app/file2");
    }

    @Test
    public void returnApplication() throws Exception {
        // When
        ApplicationDto application = repo.get("app");

        // Then
        assertThat(application.getName(), is("app"));
        assertThat(application.getVersions(), hasItems("version1", "version2"));
        assertThat(application.getPropertyFiles(), hasItems("file1", "file2"));
    }

    @Test
    public void returnsAllApplications() throws Exception {
        // When
        Collection<ApplicationDto> applications = repo.getAll();

        // Then
        assertThat(applications.size(), is(1));

        ApplicationDto application = applications.iterator().next();
        assertThat(application.getName(), is("app"));
        assertThat(application.getVersions(), hasItems("version1", "version2"));
        assertThat(application.getPropertyFiles(), hasItems("file1", "file2"));
    }
}
