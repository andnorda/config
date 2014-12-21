package config.repository;

import config.dtos.ApplicationDto;
import config.exceptions.NotFound;
import config.repository.impl.FileApplicationRepository;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
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
            new File("repo/app/version1").mkdir();
            new File("repo/app/version2").mkdir();
            new File("repo/app/file1").createNewFile();
            new File("repo/app/file2").createNewFile();

            // Then
            assertThat(repo.getAll().size(), is(1));
            ApplicationDto application = repo.getAll().iterator().next();
            assertThat(application.getName(), is("app"));
            assertThat(application.getVersions(), hasItems("version1", "version2"));
            assertThat(application.getPropertyFiles(), hasItems("file1", "file2"));
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

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNonDirMatch() throws Exception {
            // Given
            new File("repo/app").createNewFile();

            // Then
            repo.get("app");
        }

        @Test
        public void returnApplication() throws Exception {
            // Given
            new File("repo/app").mkdir();
            new File("repo/app/version1").mkdir();
            new File("repo/app/version2").mkdir();
            new File("repo/app").createNewFile();
            new File("repo/app/file1").createNewFile();
            new File("repo/app/file2").createNewFile();

            // Then
            assertThat(repo.get("app").getName(), is("app"));
            assertThat(repo.get("app").getVersions(), hasItems("version1", "version2"));
            assertThat(repo.get("app").getPropertyFiles(), hasItems("file1", "file2"));
        }
    }
}
