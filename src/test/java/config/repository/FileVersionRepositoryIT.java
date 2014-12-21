package config.repository;

import config.dtos.VersionDto;
import config.exceptions.NotFound;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.io.File;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;

@RunWith(Enclosed.class)
public class FileVersionRepositoryIT {

    public static class GetAll {

        private FileVersionRepository repo;

        @Before
        public void setUp() throws Exception {
            File baseDir = new File("repo");
            baseDir.mkdir();
            repo = new FileVersionRepository(baseDir);
        }

        @After
        public void tearDown() throws Exception {
            FileUtils.deleteDirectory(new File("repo"));
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoApp() throws Exception {
            repo.getAll("app");
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenFileApp() throws Exception {
            // Given
            new File("repo/app").createNewFile();

            // When
            repo.getAll("app");
        }

        @Test
        public void returnsEmptyCollection() throws Exception {
            // Given
            new File("repo/app").mkdir();

            // Then
            assertThat(repo.getAll("app"), is(empty()));
        }

        @Test
        public void returnsCollectionWithOneVersion() throws Exception {
            // Given
            new File("repo/app/version").mkdirs();
            new File("repo/app/version/instance1").mkdirs();
            new File("repo/app/version/instance2").mkdirs();
            new File("repo/app/version/file1.properties").createNewFile();
            new File("repo/app/version/file2.properties").createNewFile();

            // Then
            assertThat(repo.getAll("app").size(), is(1));
            VersionDto version = repo.getAll("app").iterator().next();
            assertThat(version.getName(), is("version"));
            assertThat(version.getInstances(), hasItems("instance1", "instance2"));
            assertThat(version.getPropertyFiles(), hasItems("file1", "file2"));
        }

        @Test
        public void filtersOutNonDirs() throws Exception {
            // Given
            new File("repo/app/version").mkdirs();
            new File("repo/app/file").createNewFile();

            // Then
            assertThat(repo.getAll("app").size(), is(1));
        }
    }

    public static class GetOne {

        private FileVersionRepository repo;

        @Before
        public void setUp() throws Exception {
            File baseDir = new File("repo");
            baseDir.mkdir();
            repo = new FileVersionRepository(baseDir);
        }

        @After
        public void tearDown() throws Exception {
            FileUtils.deleteDirectory(new File("repo"));
        }

        @Test (expected = NotFound.class)
        public void getOne_throwsNotFound_GivenNoApp() throws Exception {
            repo.get("app", "version");
        }

        @Test (expected = NotFound.class)
        public void getOne_throwsNotFound_GivenNoVersion() throws Exception {
            // Given
            new File("repo/app").mkdir();

            // When
            repo.get("app", "version");
        }

        @Test (expected = NotFound.class)
        public void getOne_throwsNotFound_GivenFileVersion() throws Exception {
            // Given
            new File("repo/app").mkdir();
            new File("repo/app/version").createNewFile();

            // When
            repo.get("app", "version");
        }

        @Test
        public void returnsVersion() throws Exception {
            // Given
            new File("repo/app/version").mkdirs();
            new File("repo/app/version/instance1").mkdirs();
            new File("repo/app/version/instance2").mkdirs();
            new File("repo/app/version").createNewFile();
            new File("repo/app/version/file1.properties").createNewFile();
            new File("repo/app/version/file2.properties").createNewFile();

            // Then
            assertThat(repo.get("app", "version").getName(), is("version"));
            assertThat(repo.get("app", "version").getInstances(), hasItems("instance1", "instance2"));
            assertThat(repo.get("app", "version").getPropertyFiles(), hasItems("file1", "file2"));
        }
    }
}
