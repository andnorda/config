package config.repository;

import com.google.common.collect.ImmutableMap;
import config.dtos.PropertyFileDto;
import config.exceptions.NotFound;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;

@RunWith(Enclosed.class)
public class FilePropertyFileRepositoryIT {

    public static class GetOne {
        @Rule
        public TemporaryFolder folder = new TemporaryFolder();

        private PropertyFileRepository repo;

        @Before
        public void setUp() throws Exception {
            repo = new FilePropertyFileRepository(new FileRepository(folder.newFolder("repo")));
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoApp() throws Exception {
            repo.get("app", "file");
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoVersion() throws Exception {
            // Given
            folder.newFolder("repo", "app");

            // When
            repo.get("app", "version", "file");
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoInstance() throws Exception {
            // Given
            folder.newFolder("repo", "app", "version");

            // When
            repo.get("app", "version", "instance", "file");
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoFile() throws Exception {
            // Given
            folder.newFolder("repo", "app", "version", "instance");

            // When
            repo.get("app", "version", "instance", "file");
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenDirFile() throws Exception {
            // Given
            folder.newFolder("repo", "app", "version", "instance", "file");

            // When
            repo.get("app", "version", "instance", "file");
        }

        @Test
        public void returnsPropertyFile() throws Exception {
            // Given
            folder.newFolder("repo", "app", "version", "instance");
            folder.newFile("repo/app/file");
            folder.newFile("repo/app/version/file");
            folder.newFile("repo/app/version/instance/file");

            // Then
            PropertyFileDto file = new PropertyFileDto("file", ImmutableMap.of());
            assertThat(repo.get("app", "file"), is(file));
            assertThat(repo.get("app", "version", "file"), is(file));
            assertThat(repo.get("app", "version", "instance", "file"), is(file));
        }
    }

    public static class GetAll {
        @Rule
        public TemporaryFolder folder = new TemporaryFolder();

        private PropertyFileRepository repo;

        @Before
        public void setUp() throws Exception {
            repo = new FilePropertyFileRepository(new FileRepository(folder.newFolder("repo")));
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoApp() throws Exception {
            repo.getAll("app");
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoVersion() throws Exception {
            // Given
            folder.newFolder("repo", "app");

            // Then
            repo.getAll("app", "version");
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoInstance() throws Exception {
            // Given
            folder.newFolder("repo", "app", "version");

            // Then
            repo.getAll("app", "version", "instance");
        }

        @Test
        public void returnsEmptyCollection() throws Exception {
            // Given
            folder.newFolder("repo", "app", "version", "instance");

            // Then
            assertThat(repo.getAll("app"), is(empty()));
            assertThat(repo.getAll("app", "version"), is(empty()));
            assertThat(repo.getAll("app", "version", "instance"), is(empty()));
        }

        @Test
        public void returnsCollectionWithOneFile() throws Exception {
            // Given
            folder.newFolder("repo", "app", "version", "instance");
            folder.newFile("repo/app/file");
            folder.newFile("repo/app/version/file");
            folder.newFile("repo/app/version/instance/file");

            // Then
            PropertyFileDto file = new PropertyFileDto("file", ImmutableMap.of());
            assertThat(repo.getAll("app"), hasItems(file));
            assertThat(repo.getAll("app", "version"), hasItems(file));
            assertThat(repo.getAll("app", "version", "instance"), hasItems(file));
        }
    }

    public static class Update {
        @Rule
        public TemporaryFolder folder = new TemporaryFolder();

        private FilePropertyFileRepository repo;
        private PropertyFileDto file = new PropertyFileDto("file", ImmutableMap.of("key", "new"));

        @Before
        public void setUp() throws Exception {
            repo = new FilePropertyFileRepository(new FileRepository(folder.newFolder("repo")));
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoApp() throws Exception {
            repo.update(file, "app", "file");
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoVersion() throws Exception {
            // Given
            folder.newFolder("repo", "app");

            // When
            repo.update(file, "app", "version", "file");
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoInstance() throws Exception {
            // Given
            folder.newFolder("repo", "app", "version");

            // When
            repo.update(file, "app", "version", "instance", "file");
        }

        @Test (expected = NotFound.class)
        public void throwsNotFound_GivenNoFile() throws Exception {
            // Given
            folder.newFolder("repo", "app", "version", "instance");

            // When
            repo.update(file, "app", "version", "instance", "file");
        }

        @Test
        public void updatesProperty() throws Exception {
            // Given
            folder.newFolder("repo", "app", "version", "instance");
            folder.newFile("repo/app/version/instance/file");

            // When
            repo.update(file, "app", "version", "instance", "file");

            // Then
            assertThat(repo.get("app", "version", "instance", "file"), is(file));
        }
    }
}
