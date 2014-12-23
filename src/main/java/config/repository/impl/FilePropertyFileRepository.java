package config.repository.impl;

import config.dtos.PropertyFileDto;
import config.repository.PropertyFileRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public class FilePropertyFileRepository extends FileRepository implements PropertyFileRepository {
    public FilePropertyFileRepository(File baseDir) {
        super(baseDir);
    }

    @Override
    public Collection<PropertyFileDto> getAll(String... path) {
        File file = getDir(path);
        return getAllPropertyFiles(file);
    }

    @Override
    public PropertyFileDto get(String... path) {
        String fileName = path[path.length - 1];
        File file = getDir(Arrays.copyOf(path, path.length - 1));
        return getPropertyFileWithName(file, fileName);
    }

    @Override
    public void update(PropertyFileDto propertyFileDto, String... path) {
        String fileName = path[path.length - 1];
        File file = getDir(Arrays.copyOf(path, path.length - 1));
        getPropertyFileWithName(file, fileName);

        PrintWriter writer;
        try {
            writer = new PrintWriter(file.getPath() + "/" + fileName, "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        for (Map.Entry entry : propertyFileDto.getProperties().entrySet()) {
            writer.println(entry.getKey() + "=" + entry.getValue());
        }
        writer.close();
    }

}
