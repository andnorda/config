package config.resources;

import config.dtos.ApplicationDto;
import config.dtos.PropertyFileDto;
import config.repository.ApplicationRepository;
import config.servies.PropertyFileService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("applications")
@Produces(MediaType.APPLICATION_JSON)
public class ApplicationResource {
    private final ApplicationRepository applicationRepository;
    private final PropertyFileService propertyFileService;

    public ApplicationResource(ApplicationRepository applicationRepository, PropertyFileService propertyFileService) {
        this.applicationRepository = applicationRepository;
        this.propertyFileService = propertyFileService;
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
    public Collection<PropertyFileDto> getAllFiles(
            @PathParam("application") String appName) {
        return propertyFileService.getAll(appName);
    }

    @GET
    @Path("/{application}/files/{file}")
    public PropertyFileDto getFile(
            @PathParam("application") String appName,
            @PathParam("file") String fileName) {
        return propertyFileService.get(appName, fileName);
    }

    @POST
    @Path("/{application}/files/{file}/properties/{property}")
    public void changeProperty(
            @PathParam("application") String appName,
            @PathParam("file") String fileName,
            @PathParam("property") String propertyName,
            @QueryParam("value") String propertyValue) {
        propertyFileService.changeProperty(appName, fileName, propertyName, propertyValue);
    }
}
