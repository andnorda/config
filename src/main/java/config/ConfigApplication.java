package config;

import config.repository.impl.FileApplicationRepository;
import config.repository.impl.FileInstanceRepository;
import config.repository.impl.FilePropertyFileRepository;
import config.repository.impl.FileVersionRepository;
import config.resources.ApplicationResource;
import config.resources.InstanceResource;
import config.resources.VersionResource;
import config.servies.PropertyFileService;
import config.servies.PropertyFileServiceImpl;
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
        PropertyFileService propertyFileService = new PropertyFileServiceImpl(propertyFileRepository);
        ApplicationResource applicationResource = new ApplicationResource(new FileApplicationRepository(baseDir), propertyFileService);
        VersionResource versionResource = new VersionResource(new FileVersionRepository(baseDir), propertyFileService);
        InstanceResource instanceResource = new InstanceResource(new FileInstanceRepository(baseDir), propertyFileService);

        environment.jersey().register(applicationResource);
        environment.jersey().register(versionResource);
        environment.jersey().register(instanceResource);
    }
}
