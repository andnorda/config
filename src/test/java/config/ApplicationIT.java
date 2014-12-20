package config;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ApplicationIT {

    @Before
    public void setUp() throws Exception {
        new File("repo").mkdir();
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.deleteDirectory(new File("repo"));
    }

    @Test
    public void test() throws Exception {
        new File("repo/app1").mkdir();

        ApplicationResource applicationResource = new ApplicationResource(new FileRepository(new File("repo")));
        assertThat(applicationResource.getAll().size(), is(1));
    }
}
