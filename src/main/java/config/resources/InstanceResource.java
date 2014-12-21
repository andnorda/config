package config.resources;

import config.dtos.InstanceDto;
import config.dtos.PropertyFileDto;
import config.repository.InstanceRepository;
import config.repository.PropertyFileRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("applications/{application}/versions/{version}/instances")
@Produces(MediaType.APPLICATION_JSON)
public class InstanceResource {
    private final InstanceRepository instanceRepository;
    private final PropertyFileRepository propertyFileRepository;

    public InstanceResource(InstanceRepository instanceRepository, PropertyFileRepository propertyFileRepository) {
        this.instanceRepository = instanceRepository;
        this.propertyFileRepository = propertyFileRepository;
    }

    @GET
    public Collection<InstanceDto> getAll(
            @PathParam("application") String appName,
            @PathParam("version") String versionName) {
        return instanceRepository.getAll(appName, versionName);
    }

    @GET
    @Path("/{instance}")
    public InstanceDto get(
            @PathParam("application") String appName,
            @PathParam("version") String versionName,
            @PathParam("instance") String instanceName) {
        return instanceRepository.get(appName, versionName, instanceName);
    }

    @GET
    @Path("/{instance}/files")
    public Collection<PropertyFileDto> getAllInstancePropertyFiles(
            @PathParam("application") String appName,
            @PathParam("version") String versionName,
            @PathParam("instance") String instanceName) {
        return propertyFileRepository.getAllInstancePropertyFiles(appName, versionName, instanceName);
    }

    @GET
    @Path("/{instance}/files/{file}")
    public PropertyFileDto getInstancePropertyFile(
            @PathParam("application") String appName,
            @PathParam("version") String versionName,
            @PathParam("instance") String instanceName,
            @PathParam("file") String fileName) {
        return propertyFileRepository.getInstancePropertyFiles(appName, versionName, instanceName, fileName);
    }
}
