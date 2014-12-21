package config.repository;

import config.exceptions.NotFound;
import config.repository.impl.FilePropertyFileRepository;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;

@RunWith(Enclosed.class)
public class FileInstancePropertyFileRepositoryIT {

    public static class GetOne {
        private FilePropertyFileRepository repo;
        private File baseDir;

        @Before
        public void setUp() throws Exception {
            baseDir = new File("repo");
            baseDir.mkdir();
            repo = new FilePropertyFileRepository(baseDir);
        }

        @After
        public void tearDown() throws Exception {
            FileUtils.deleteDirectory(baseDir);
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoApp() throws Exception {
            repo.get("app", "version", "instance", "file");
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoVersion() throws Exception {
            // Given
            new File("repo/app").mkdir();

            // When
            repo.get("app", "version", "instance", "file");
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoInstance() throws Exception {
            // Given
            new File("repo/app/version").mkdirs();

            // When
            repo.get("app", "version", "instance", "file");
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoFile() throws Exception {
            // Given
            new File("repo/app/version/instance").mkdirs();

            // When
            repo.get("app", "version", "instance", "file");
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenDirFile() throws Exception {
            // Given
            new File("repo/app/version/instance/file.properties").mkdirs();

            // When
            repo.get("app", "version", "instance", "file");
        }

        @Test
        public void returnsPropertyFile() throws Exception {
            // Given
            new File("repo/app/version/instance").mkdirs();
            new File("repo/app/version/instance/file.properties").createNewFile();
            new File("repo/app/version/instance/file.properties").mkdir();

            // Then
            assertThat(repo.get("app", "version", "instance", "file").getName(), is("file"));
        }
    }

    public static class GetAll {
        private FilePropertyFileRepository repo;
        private File baseDir;

        @Before
        public void setUp() throws Exception {
            baseDir = new File("repo");
            baseDir.mkdir();
            repo = new FilePropertyFileRepository(baseDir);
        }

        @After
        public void tearDown() throws Exception {
            FileUtils.deleteDirectory(baseDir);
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoApp() throws Exception {
            repo.getAll("app", "version", "instance");
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoVersion() throws Exception {
            // Given
            new File("repo/app").mkdir();

            // Then
            repo.getAll("app", "version", "instance");
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoInstance() throws Exception {
            // Given
            new File("repo/app/version").mkdirs();

            // Then
            repo.getAll("app", "version", "instance");
        }

        @Test
        public void returnsEmptyCollection() throws Exception {
            // Given
            new File("repo/app/version/instance").mkdirs();

            // Then
            assertThat(repo.getAll("app", "version", "instance"), is(empty()));
        }

        @Test
        public void returnsCollectionWithOneFile() throws Exception {
            // Given
            new File("repo/app/version/instance").mkdirs();
            new File("repo/app/version/instance/file.properties").createNewFile();
            new File("repo/app/version/instance/file.nope").createNewFile();
            new File("repo/app/version/instance/notAFile.properties").mkdir();

            // Then
            assertThat(repo.getAll("app", "version", "instance").size(), is(1));
            assertThat(repo.getAll("app", "version", "instance").iterator().next().getName(), is("file"));
        }
    }
}
