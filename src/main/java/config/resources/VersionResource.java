package config.resources;

import config.dtos.PropertyFileDto;
import config.dtos.VersionDto;
import config.repository.VersionRepository;
import config.servies.PropertyFileService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("applications/{application}/versions")
@Produces(MediaType.APPLICATION_JSON)
public class VersionResource {
    private final VersionRepository versionRepository;
    private final PropertyFileService propertyFileService;

    public VersionResource(VersionRepository versionRepository, PropertyFileService propertyFileService) {
        this.versionRepository = versionRepository;
        this.propertyFileService = propertyFileService;
    }

    @GET
    public Collection<VersionDto> getAll(
            @PathParam("application") String appName) {
        return versionRepository.getAll(appName);
    }

    @GET
    @Path("/{version}")
    public VersionDto get(
            @PathParam("application") String appName,
            @PathParam("version") String versionName) {
        return versionRepository.get(appName, versionName);
    }

    @GET
    @Path("/{version}/files")
    public Collection<PropertyFileDto> getAllFiles(
            @PathParam("application") String appName,
            @PathParam("version") String versionName) {
        return propertyFileService.getAll(appName, versionName);
    }

    @GET
    @Path("{version}/files/{file}")
    public PropertyFileDto getFile(
            @PathParam("application") String appName,
            @PathParam("version") String versionName,
            @PathParam("file") String fileName) {
        return propertyFileService.get(appName, versionName, fileName);
    }

    public void changeProperty(String appName, String versionName, String fileName, String propertyKey, String propertyValue) {
        propertyFileService.changeProperty(appName, versionName, fileName, propertyKey, propertyValue);
    }
}
