package config.repository.impl;

import config.dtos.PropertyFileDto;
import config.repository.PropertyFileRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class FilePropertyFileRepository extends FileRepository implements PropertyFileRepository {
    private final File baseDir;

    public FilePropertyFileRepository(File baseDir) {
        super(baseDir);
        this.baseDir = baseDir;
    }

    @Override
    public Collection<PropertyFileDto> getAll(String... path) {
        List<String> dirs = Arrays.asList(path);
        File file = baseDir;
        for (String dir : dirs) {
            file = getSubDirWithName(file, dir);
        }
        return getAllPropertyFiles(file);
    }

    @Override
    public PropertyFileDto get(String... path) {
        List<String> dirs = Arrays.asList(path);
        String fileName = dirs.get(dirs.size() - 1);
        dirs = dirs.subList(0, dirs.size() - 1);
        File file = baseDir;
        for (String dir : dirs) {
            file = getSubDirWithName(file, dir);
        }
        return getPropertyFileWithName(file, fileName);
    }

    @Override
    public void update(PropertyFileDto propertyFileDto, String... path) {
        List<String> dirs = Arrays.asList(path);
        String fileName = dirs.get(dirs.size() - 1);
        dirs = dirs.subList(0, dirs.size() - 1);
        File file = baseDir;
        for (String dir : dirs) {
            file = getSubDirWithName(file, dir);
        }
        getPropertyFileWithName(file, fileName);
        writeNewPropertiesToFile(propertyFileDto, file, fileName);
    }

    private void writeNewPropertiesToFile(PropertyFileDto propertyFileDto, File parent, String fileName) {
        PrintWriter writer;
        try {
            writer = new PrintWriter(parent.getPath() + "/" + fileName, "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        for (Map.Entry entry : propertyFileDto.getProperties().entrySet()) {
            writer.println(entry.getKey() + "=" + entry.getValue());
        }
        writer.close();
    }
}
