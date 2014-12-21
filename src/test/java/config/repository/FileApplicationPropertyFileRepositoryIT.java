package config.repository;

import com.google.common.collect.ImmutableMap;
import config.dtos.PropertyFileDto;
import config.exceptions.NotFound;
import config.repository.impl.FilePropertyFileRepository;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.PrintWriter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItems;

@RunWith(Enclosed.class)
public class FileApplicationPropertyFileRepositoryIT {

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

        @Test(expected = NotFound.class)
        public void throwsNotFound_GivenNoApp() throws Exception {
            repo.get("app", "file");
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoFile() throws Exception {
            // Given
            new File("repo/app").mkdir();

            // When
            repo.get("app", "file");
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenDirFile() throws Exception {
            // Given
            new File("repo/app/file.properties").mkdirs();

            // When
            repo.get("app", "file");
        }

        @Test
        public void returnsPropertyFile() throws Exception {
            // Given
            new File("repo/app").mkdir();
            PrintWriter writer = new PrintWriter("repo/app/file.properties", "UTF-8");
            writer.println("key=value");
            writer.close();
            new File("repo/app/file.properties").mkdir();

            // Then
            assertThat(repo.get("app", "file").getName(), is("file"));
            assertThat(repo.get("app", "file").getProperties().keySet(), hasItems("key"));
            assertThat(repo.get("app", "file").getProperties().values(), hasItems("value"));
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
        public void returnsCollectionWithOneFile() throws Exception {
            // Given
            new File("repo/app").mkdir();
            PrintWriter writer = new PrintWriter("repo/app/file.properties", "UTF-8");
            writer.println("key=value");
            writer.close();
            new File("repo/app/file.nope").createNewFile();
            new File("repo/app/notAFile.properties").mkdir();

            // Then
            assertThat(repo.getAll("app").size(), is(1));
            PropertyFileDto propertyFileDto = repo.getAll("app").iterator().next();
            assertThat(propertyFileDto.getName(), is("file"));
            assertThat(propertyFileDto.getProperties().keySet(), hasItems("key"));
            assertThat(propertyFileDto.getProperties().values(), hasItems("value"));
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
            repo.update("app", "file", new PropertyFileDto("file", ImmutableMap.of("key", "new")));
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoFile() throws Exception {
            // Given
            new File("repo/app").mkdir();

            // When
            repo.update("app", "file", new PropertyFileDto("file", ImmutableMap.of("key", "new")));
        }

        @Test
        public void updatesProperty() throws Exception {
            // Given
            new File("repo/app").mkdir();
            PrintWriter writer = new PrintWriter("repo/app/file.properties", "UTF-8");
            writer.println("key=old");
            writer.close();

            // When
            repo.update("app", "file", new PropertyFileDto("file", ImmutableMap.of("key", "new")));

            // Then
            assertThat(repo.get("app", "file"), is(new PropertyFileDto("file", ImmutableMap.of("key", "new"))));
        }
    }
}
