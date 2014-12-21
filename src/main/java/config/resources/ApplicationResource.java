package config.resources;

import config.dtos.ApplicationDto;
import config.dtos.PropertyFileDto;
import config.repository.ApplicationRepository;
import config.repository.PropertyFileRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("applications")
@Produces(MediaType.APPLICATION_JSON)
public class ApplicationResource {
    private final ApplicationRepository applicationRepository;
    private final PropertyFileRepository propertyFileRepository;

    public ApplicationResource(ApplicationRepository applicationRepository, PropertyFileRepository propertyFileRepository1) {
        this.applicationRepository = applicationRepository;
        propertyFileRepository = propertyFileRepository1;
    }

    @GET
    public Collection<ApplicationDto> getAll() {
        return applicationRepository.getAll();
    }

    @GET
    @Path("/{application}")
    public ApplicationDto get(
            @PathParam("application") String name) {
        return applicationRepository.get(name);
    }

    @GET
    @Path("/{application}/files")
    public Collection<PropertyFileDto> getAllApplicationPropertyFiles(
            @PathParam("application") String appName) {
        return propertyFileRepository.getAllApplicationPropertyFiles(appName);
    }

    @GET
    @Path("/{application}/files/{file}")
    public PropertyFileDto getApplicationPropertyFile(
            @PathParam("application") String appName,
            @PathParam("file") String fileName) {
        return propertyFileRepository.getApplicationPropertyFile(appName, fileName);
    }

    public void changeProperty(String appName, String fileName, String propertyName, String propertyValue) {
    }
}
