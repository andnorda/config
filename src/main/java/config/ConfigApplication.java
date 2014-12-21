package config;

import config.repository.FileApplicationRepository;
import config.repository.FileInstanceRepository;
import config.repository.FilePropertyFileRepository;
import config.repository.FileVersionRepository;
import config.resources.ApplicationResource;
import config.resources.InstanceResource;
import config.resources.VersionResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.io.File;

public class ConfigApplication extends Application<ConfigConfiguration> {
    public static void main(String[] args) throws Exception {
        new ConfigApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<ConfigConfiguration> configConfigurationBootstrap) {

    }

    @Override
    public void run(ConfigConfiguration configConfiguration, Environment environment) throws Exception {
        File baseDir = new File("repo");
        FilePropertyFileRepository propertyFileRepository = new FilePropertyFileRepository(baseDir);
        ApplicationResource applicationResource = new ApplicationResource(new FileApplicationRepository(baseDir), propertyFileRepository);
        VersionResource versionResource = new VersionResource(new FileVersionRepository(baseDir), propertyFileRepository);
        InstanceResource instanceResource = new InstanceResource(new FileInstanceRepository(baseDir), propertyFileRepository);

        environment.jersey().register(applicationResource);
        environment.jersey().register(versionResource);
        environment.jersey().register(instanceResource);
    }
}
