package config.repository;

import config.exceptions.NotFound;
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
            repo.getApplicationPropertyFile("app", "file");
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoFile() throws Exception {
            // Given
            new File("repo/app").mkdir();

            // When
            repo.getApplicationPropertyFile("app", "file");
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenDirFile() throws Exception {
            // Given
            new File("repo/app/file.properties").mkdirs();

            // When
            repo.getApplicationPropertyFile("app", "file");
        }

        @Test
        public void returnsPropertyFile() throws Exception {
            // Given
            new File("repo/app").mkdir();
            new File("repo/app/file.properties").createNewFile();
            new File("repo/app/file.properties").mkdir();

            // Then
            assertThat(repo.getApplicationPropertyFile("app", "file").getName(), is("file"));
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
            repo.getAllApplicationPropertyFiles("app");
        }

        @Test
        public void returnsEmptyCollection() throws Exception {
            // Given
            new File("repo/app").mkdir();

            // Then
            assertThat(repo.getAllApplicationPropertyFiles("app"), is(empty()));
        }

        @Test
        public void returnsCollectionWithOneFile() throws Exception {
            // Given
            new File("repo/app").mkdir();
            new File("repo/app/file.properties").createNewFile();
            new File("repo/app/file.nope").createNewFile();
            new File("repo/app/notAFile.properties").mkdir();

            // Then
            assertThat(repo.getAllApplicationPropertyFiles("app").size(), is(1));
            assertThat(repo.getAllApplicationPropertyFiles("app").iterator().next().getName(), is("file"));
        }
    }
}
