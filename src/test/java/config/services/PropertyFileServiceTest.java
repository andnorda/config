package config.services;

import com.google.common.collect.ImmutableMap;
import config.dtos.PropertyFileDto;
import config.exceptions.NotFound;
import config.fakes.FakePropertyFileRepository;
import config.servies.PropertyFileService;
import config.servies.PropertyFileServiceImpl;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PropertyFileServiceTest {

    private PropertyFileService service;
    private FakePropertyFileRepository repo;

    @Before
    public void setUp() throws Exception {
        repo = new FakePropertyFileRepository();
        service = new PropertyFileServiceImpl(repo);
    }

    @Test
    public void updatesAppProperty() throws Exception {
        // Given
        repo.addApplicationPropertyFile("app", "file", new PropertyFileDto("file", ImmutableMap.of("key", "old")));

        // When
        service.changeProperty("app", "file", "key", "new");

        // Then
        assertThat(service.get("app", "file"), is(new PropertyFileDto("file", ImmutableMap.of("key", "new"))));
    }

    @Test (expected = NotFound.class)
    public void throwsNotFound_GivenKeyMissInApp() throws Exception {
        // Given
        repo.addApplicationPropertyFile("app", "file", new PropertyFileDto("file", ImmutableMap.of()));

        // When
        service.changeProperty("app", "file", "key", "new");
    }
}
