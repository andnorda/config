package config.repository;

import config.exceptions.NotFound;
import config.resources.ApplicationResource;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;

public class FileApplicationResporitoryIT {

    private FileApplicationRepository repo;

    @Before
    public void setUp() throws Exception {
        File baseDir = new File("repo");
        baseDir.mkdir();
        repo = new FileApplicationRepository(baseDir);
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.deleteDirectory(new File("repo"));
    }

    @Test
    public void returnsEmptyCollection() throws Exception {
        assertThat(repo.getAll(), is(empty()));
    }

    @Test
    public void returnsCollectionWithOneApplication() throws Exception {
        // Given
        new File("repo/app1").mkdir();

        // Then
        assertThat(repo.getAll().size(), is(1));
        assertThat(repo.getAll().iterator().next().getName(), is("app1"));
    }

    @Test
    public void filtersOutNonDirs() throws Exception {
        // Given
        new File("repo/app1").mkdir();
        new File("repo/notADir").createNewFile();

        // Then
        assertThat(repo.getAll().size(), is(1));
    }

    @Test (expected = NotFound.class)
    public void throwsNotFound_GivenNoMatches() throws Exception {
        repo.get("app1");
    }

    @Test
    public void returnApplication() throws Exception {
        // Given
        new File("repo/app1").mkdir();
        new File("repo/app1").createNewFile();

        // Then
        assertThat(repo.get("app1").getName(), is("app1"));
    }

    @Test (expected = NotFound.class)
    public void throwsNotFound_GivenNonDirMatch() throws Exception {
        // Given
        new File("repo/app1").createNewFile();

        // Then
        repo.get("app1");
    }
}
