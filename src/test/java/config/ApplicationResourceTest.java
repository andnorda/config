package config;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ApplicationResourceTest {
    private FakeApplicationRepository repo;
    private ApplicationResource resource;

    @Before
    public void setUp() throws Exception {
        repo = new FakeApplicationRepository();
        resource = new ApplicationResource(repo);
    }

    @Test
    public void getOne() throws Exception {
        // Given
        ApplicationDto applicationDto = new ApplicationDto("app name");
        repo.add("app name", applicationDto);

        // Then
        assertThat(resource.get("app name"), is(applicationDto));
    }

    @Test
    public void getAll() throws Exception {
        // Given
        ApplicationDto app1 = new ApplicationDto("app1");
        ApplicationDto app2 = new ApplicationDto("app2");
        repo.add("app1", app1);
        repo.add("app2", app2);

        // Then
        assertThat(resource.getAll(), hasItems(app1, app2));
    }
}
