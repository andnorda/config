package config.integration;

import config.repository.FileVersionRepository;
import config.resources.VersionResource;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;

public class VersionIT {

    private VersionResource resource;

    @Before
    public void setUp() throws Exception {
        resource = new VersionResource(new FileVersionRepository(new File("repo")));
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.deleteDirectory(new File("repo"));
    }

    @Test
    public void returnsEmptyCollection() throws Exception {
        new File("repo/app").mkdir();
        assertThat(resource.getAll("app"), is(empty()));
    }
}
