package config.repository;

import config.exceptions.NotFound;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;

public class FileVersionRepositoryIT {

    private FileVersionRepository repo;

    @Before
    public void setUp() throws Exception {
        repo = new FileVersionRepository(new File("repo"));
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.deleteDirectory(new File("repo"));
    }

    @Test (expected = NotFound.class)
    public void throwsNotFound_GivenNoApp() throws Exception {
        repo.getAll("app");
    }

    @Test
    public void returnsEmptyCollection() throws Exception {
        // Given
        new File("repo/app").mkdir();

        // Then
        assertThat(repo.getAll("app"), is(empty()));
    }
}
