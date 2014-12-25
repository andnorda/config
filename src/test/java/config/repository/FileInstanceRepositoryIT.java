package config.repository;

import config.dtos.InstanceDto;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FileInstanceRepositoryIT {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private InstanceRepository repo;

    @Before
    public void setUp() throws Exception {
        repo = new FileInstanceRepository(new FileRepository(folder.newFolder("repo")));

        folder.newFolder("repo", "app", "version", "instance");
        folder.newFile("repo/app/version/instance/file1");
        folder.newFile("repo/app/version/instance/file2");

    }

    @Test
    public void returnInstance() throws Exception {
        // When
        InstanceDto instance = repo.get("app", "version", "instance");

        // Then
        assertThat(instance.getName(), is("instance"));
        assertThat(instance.getPropertyFiles(), hasItems("file1", "file2"));
    }

    @Test
    public void returnsAllInstances() throws Exception {
        // When
        Collection<InstanceDto> instances = repo.getAll("app", "version");

        // Then
        assertThat(instances.size(), is(1));

        InstanceDto instance = instances.iterator().next();
        assertThat(instance.getName(), is("instance"));
        assertThat(instance.getPropertyFiles(), hasItems("file1", "file2"));
    }
}
