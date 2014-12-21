package config.repository;

import config.dtos.InstanceDto;
import config.exceptions.NotFound;
import config.repository.impl.FileInstanceRepository;
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
public class FileInstanceRepositoryIT {

    public static class GetAll {

        private FileInstanceRepository repo;
        private File baseDir;

        @Before
        public void setUp() throws Exception {
            baseDir = new File("repo");
            baseDir.mkdir();
            repo = new FileInstanceRepository(baseDir);
        }

        @After
        public void tearDown() throws Exception {
            FileUtils.deleteDirectory(baseDir);
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoApp() throws Exception {
            repo.getAll("app", "version");
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoVersion() throws Exception {
            // Given
            new File("repo/app").mkdir();

            // Then
            repo.getAll("app", "version");
        }

        @Test
        public void returnsEmptyCollection_GivenNoInstances() throws Exception {
            // Given
            new File("repo/app/version").mkdirs();

            // Then
            assertThat(repo.getAll("app", "version"), is(empty()));
        }

        @Test
        public void returnsCollectionWithOneInstance() throws Exception {
            // Given
            new File("repo/app/version/instance").mkdirs();
            new File("repo/app/version/nonDir").createNewFile();
            new File("repo/app/version/instance/file1.properties").createNewFile();
            new File("repo/app/version/instance/file2.properties").createNewFile();

            // Then
            assertThat(repo.getAll("app", "version").size(), is(1));
            InstanceDto instance = repo.getAll("app", "version").iterator().next();
            assertThat(instance.getName(), is("instance"));
            assertThat(instance.getPropertyFiles(), hasItems("file1", "file2"));
        }
    }

    public static class GetOne {

        private FileInstanceRepository repo;
        private File baseDir;

        @Before
        public void setUp() throws Exception {
            baseDir = new File("repo");
            baseDir.mkdir();
            repo = new FileInstanceRepository(baseDir);
        }

        @After
        public void tearDown() throws Exception {
            FileUtils.deleteDirectory(baseDir);
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoApp() throws Exception {
            repo.get("app", "version", "instance");
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoVersion() throws Exception {
            // Given
            new File("repo/app").mkdir();

            // When
            repo.get("app", "version", "instance");
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoInstance() throws Exception {
            // Given
            new File("repo/app/version").mkdirs();

            // When
            repo.get("app", "version", "instance");
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNonDirInstance() throws Exception {
            // Given
            new File("repo/app/version").mkdirs();
            new File("repo/app/version/instance").createNewFile();

            // When
            repo.get("app", "version", "instance");
        }

        @Test
        public void returnsInstance() throws Exception {
            // Given
            new File("repo/app/version/instance").mkdirs();
            new File("repo/app/version/instance").createNewFile();
            new File("repo/app/version/instance/file1.properties").createNewFile();
            new File("repo/app/version/instance/file2.properties").createNewFile();

            // Then
            assertThat(repo.get("app", "version", "instance").getName(), is("instance"));
            assertThat(repo.get("app", "version", "instance").getPropertyFiles(), hasItems("file1", "file2"));
        }
    }
}
