package config.repository;

import config.exceptions.NotFound;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;

import java.io.File;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Enclosed.class)
public class FileRepositoryIT {
    public static class GetDir {
        @Rule
        public TemporaryFolder folder = new TemporaryFolder();

        private FileRepository repo;

        @Before
        public void setUp() throws Exception {
            repo = new FileRepository(folder.newFolder("repo"));
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoDir() throws Exception {
            repo.getDir("app");
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoSubDir() throws Exception {
            // Given
            folder.newFolder("repo", "app");

            // When
            repo.getDir("app", "version");
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNonDir() throws Exception {
            // Given
            folder.newFile("repo/app");

            // When
            repo.getDir("app");
        }

        @Test
        public void returnsDir() throws Exception {
            // Given
            folder.newFolder("repo", "app", "version", "instance");

            // Then
            assertTrue(repo.getDir("app", "version", "instance").isDirectory());
            assertThat(repo.getDir("app", "version", "instance").getName(), is("instance"));
        }
    }

    public static class GetFile {
        @Rule
        public TemporaryFolder folder = new TemporaryFolder();

        private FileRepository repo;

        @Before
        public void setUp() throws Exception {
            repo = new FileRepository(folder.newFolder("repo"));
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoFile() throws Exception {
            repo.getFile("app", "file");
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNonFile() throws Exception {
            // Given
            folder.newFolder("repo", "app", "file");

            // When
            repo.getFile("app", "file");
        }

        @Test
        public void returnsFile() throws Exception {
            // Given
            folder.newFolder("repo", "app", "version", "instance");
            folder.newFile("repo/app/version/instance/file");

            // Then
            assertTrue(repo.getFile("app", "version", "instance", "file").isFile());
            assertThat(repo.getFile("app", "version", "instance", "file").getName(), is("file"));
        }
    }

    public static class ListDirs {
        @Rule
        public TemporaryFolder folder = new TemporaryFolder();

        private FileRepository repo;

        @Before
        public void setUp() throws Exception {
            repo = new FileRepository(folder.newFolder("repo"));
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoDir() throws Exception {
            repo.listDirs("app");
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoSubDir() throws Exception {
            // Given
            folder.newFolder("repo", "app");

            // When
            repo.listDirs("app", "version");
        }

        @Test
        public void returnsDirs() throws Exception {
            // Given
            folder.newFolder("repo", "app");

            // Then
            assertThat(repo.listDirs().size(), is(1));
            assertTrue(repo.listDirs().iterator().next().isDirectory());
            assertThat(repo.listDirs().iterator().next().getName(), is("app"));
        }
    }

    public static class ListFiles {
        @Rule
        public TemporaryFolder folder = new TemporaryFolder();

        private FileRepository repo;

        @Before
        public void setUp() throws Exception {
            repo = new FileRepository(folder.newFolder("repo"));
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoDir() throws Exception {
            repo.listFiles("app");
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoSubDir() throws Exception {
            // Given
            folder.newFolder("repo", "app");

            // When
            repo.listFiles("app", "version");
        }

        @Test
        public void returnsFiles() throws Exception {
            // Given
            folder.newFolder("repo", "app", "version");
            folder.newFile("repo/app/version/file");

            // Then
            assertThat(repo.listFiles("app", "version").size(), is(1));
            assertTrue(repo.listFiles("app", "version").iterator().next().isFile());
            assertThat(repo.listFiles("app", "version").iterator().next().getName(), is("file"));
        }
    }

    public static class ListSubDirNames {
        @Rule
        public TemporaryFolder folder = new TemporaryFolder();

        private FileRepository repo;

        @Before
        public void setUp() throws Exception {
            repo = new FileRepository(folder.newFolder("repo"));
        }

        @Test
        public void returnsSubDirNames() throws Exception {
            // Given
            File file = folder.newFolder("repo", "app");
            folder.newFolder("repo", "app", "version");
            folder.newFile("repo/app/file");

            // Then
            assertThat(repo.listSubDirNames(file).size(), is(1));
            assertThat(repo.listSubDirNames(file), hasItems("version"));
        }
    }

    public static class ListFileNames {
        @Rule
        public TemporaryFolder folder = new TemporaryFolder();

        private FileRepository repo;

        @Before
        public void setUp() throws Exception {
            repo = new FileRepository(folder.newFolder("repo"));
        }

        @Test
        public void returnsFileNames() throws Exception {
            // Given
            File file = folder.newFolder("repo", "app");
            folder.newFolder("repo", "app", "version");
            folder.newFile("repo/app/file");

            // Then
            assertThat(repo.listFileNames(file).size(), is(1));
            assertThat(repo.listFileNames(file), hasItems("file"));
        }
    }
}
