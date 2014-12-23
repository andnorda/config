package config;

import config.repository.*;
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
        FileRepository fileRepository = new FileRepository(new File("repo"));
        FilePropertyFileRepository propertyFileRepository = new FilePropertyFileRepository(fileRepository);
        FileApplicationRepository applicationRepository = new FileApplicationRepository(fileRepository);
        FileVersionRepository versionRepository = new FileVersionRepository(fileRepository);
        FileInstanceRepository instanceRepository = new FileInstanceRepository(fileRepository);

        PropertyFileService propertyFileService = new PropertyFileServiceImpl(propertyFileRepository);

        ApplicationResource applicationResource = new ApplicationResource(applicationRepository, propertyFileService);
        VersionResource versionResource = new VersionResource(versionRepository, propertyFileService);
        InstanceResource instanceResource = new InstanceResource(instanceRepository, propertyFileService);

        environment.jersey().register(applicationResource);
        environment.jersey().register(versionResource);
        environment.jersey().register(instanceResource);
    }
}
