package config.repository;

import config.exceptions.NotFound;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;

@RunWith(Enclosed.class)
public class FileApplicationRepositoryIT {

    public static class GetAll {

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
        public void returnsEmptyCollection_GivenNoApps() throws Exception {
            assertThat(repo.getAll(), is(empty()));
        }

        @Test
        public void returnsCollectionWithOneApplication_GivenOneDir() throws Exception {
            // Given
            new File("repo/app").mkdir();

            // Then
            assertThat(repo.getAll().size(), is(1));
            assertThat(repo.getAll().iterator().next().getName(), is("app"));
        }

        @Test
        public void filtersOutNonDirs() throws Exception {
            // Given
            new File("repo/app").mkdir();
            new File("repo/notADir").createNewFile();

            // Then
            assertThat(repo.getAll().size(), is(1));
        }
    }

    public static class GetOne {

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

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoMatches() throws Exception {
            repo.get("app");
        }

        @Test
        public void returnApplication() throws Exception {
            // Given
            new File("repo/app").mkdir();
            new File("repo/app").createNewFile();

            // Then
            assertThat(repo.get("app").getName(), is("app"));
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNonDirMatch() throws Exception {
            // Given
            new File("repo/app").createNewFile();

            // Then
            repo.get("app");
        }
    }
}