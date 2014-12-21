package config.repository;

import com.google.common.collect.ImmutableMap;
import config.dtos.PropertyFileDto;
import config.exceptions.NotFound;
import config.repository.impl.FilePropertyFileRepository;
import org.apache.commons.io.FileUtils;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.PrintWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;

@RunWith(Enclosed.class)
public class FileVersionPropertyFileRepositoryIT {

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
            repo.get("app", "version", "file");
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoVersion() throws Exception {
            // Given
            new File("repo/app").mkdir();

            // When
            repo.get("app", "version", "file");
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoFile() throws Exception {
            // Given
            new File("repo/app/version").mkdirs();

            // When
            repo.get("app", "version", "file");
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenDirFile() throws Exception {
            // Given
            new File("repo/app/version/file").mkdirs();

            // When
            repo.get("app", "version", "file");
        }

        @Test
        public void returnsPropertyFile() throws Exception {
            // Given
            new File("repo/app/version").mkdirs();
            new File("repo/app/version/file").createNewFile();
            new File("repo/app/version/file").mkdir();

            // Then
            assertThat(repo.get("app", "version", "file").getName(), is("file"));
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
        public void returnsEmptyCollection() throws Exception {
            // Given
            new File("repo/app/version").mkdirs();

            // Then
            assertThat(repo.getAll("app", "version"), is(empty()));
        }

        @Test
        public void returnsCollectionWithOneFile() throws Exception {
            // Given
            new File("repo/app/version").mkdirs();
            new File("repo/app/version/file").createNewFile();
            new File("repo/app/version/notAFile").mkdir();

            // Then
            assertThat(repo.getAll("app", "version").size(), is(1));
            assertThat(repo.getAll("app", "version").iterator().next().getName(), is("file"));
        }
    }

    public static class Update {
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
            repo.update("app", "version", "file", new PropertyFileDto("file", ImmutableMap.of("key", "new")));
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoVersion() throws Exception {
            // Given
            new File("repo/app").mkdir();

            // When
            repo.update("app", "version", "file", new PropertyFileDto("file", ImmutableMap.of("key", "new")));
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoFile() throws Exception {
            // Given
            new File("repo/app/version").mkdirs();

            // When
            repo.update("app", "version", "file", new PropertyFileDto("file", ImmutableMap.of("key", "new")));
        }

        @Test
        public void updatesProperty() throws Exception {
            // Given
            new File("repo/app/version").mkdirs();
            PrintWriter writer = new PrintWriter("repo/app/version/file", "UTF-8");
            writer.println("key=old");
            writer.close();

            // When
            repo.update("app", "version", "file", new PropertyFileDto("file", ImmutableMap.of("key", "new")));

            // Then
            assertThat(repo.get("app", "version", "file"), CoreMatchers.is(new PropertyFileDto("file", ImmutableMap.of("key", "new"))));
        }
    }
}
