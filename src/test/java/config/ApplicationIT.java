package config;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;

public class ApplicationIT {

    private ApplicationResource resource;

    @Before
    public void setUp() throws Exception {
        new File("repo").mkdir();
        resource = new ApplicationResource(new FileRepository(new File("repo")));
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.deleteDirectory(new File("repo"));
    }

    @Test
    public void returnsEmptyCollection() throws Exception {
        assertThat(resource.getAll(), is(empty()));
    }

    @Test
    public void returnsCollectionWithOneApplication() throws Exception {
        // Given
        new File("repo/app1").mkdir();

        // Then
        assertThat(resource.getAll().size(), is(1));
    }
}
