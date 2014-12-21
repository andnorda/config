package config.resources;

import config.dtos.PropertyFileDto;
import config.dtos.VersionDto;
import config.repository.PropertyFileRepository;
import config.repository.VersionRepository;

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
    private final PropertyFileRepository propertyFileRepository;

    public VersionResource(VersionRepository versionRepository, PropertyFileRepository propertyFileRepository) {
        this.versionRepository = versionRepository;
        this.propertyFileRepository = propertyFileRepository;
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
    public Collection<PropertyFileDto> getAllVersionPropertyFiles(
            @PathParam("application") String appName,
            @PathParam("version") String versionName) {
        return propertyFileRepository.getAllVersionPropertyFiles(appName, versionName);
    }

    @GET
    @Path("{version}/files/{file}")
    public PropertyFileDto getVersionPropertyFile(
            @PathParam("application") String appName,
            @PathParam("version") String versionName,
            @PathParam("file") String fileName) {
        return propertyFileRepository.getVersionPropertyFile(appName, versionName, fileName);
    }
}
