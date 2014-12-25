package config.repository;

import config.dtos.PropertyFileDto;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FilePropertyFileRepository implements PropertyFileRepository {
    private final FileRepository fileRepository;

    public FilePropertyFileRepository(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public PropertyFileDto get(String... path) {
        return toPropertyFile().apply(fileRepository.getFile(path));
    }

    @Override
    public Collection<PropertyFileDto> getAll(String... path) {
        Collection<File> files = fileRepository.listFiles(path);
        return files.stream()
                .map(toPropertyFile())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private Function<File, PropertyFileDto> toPropertyFile() {
        return file -> new PropertyFileDto(file.getName(), getProperties(file));
    }

    private Map<String, String> getProperties(File file) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().toString(),
                        entry -> entry.getValue().toString()));
    }

    @Override
    public void update(PropertyFileDto propertyFileDto, String... path) {
        File file = fileRepository.getFile(path);
        writePropertiesToFile(propertyFileDto, file);
    }

    private void writePropertiesToFile(PropertyFileDto propertyFileDto, File file) {
        PrintWriter writer;
        try {
            writer = new PrintWriter(file.getPath(), "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        propertyFileDto.getProperties().entrySet().forEach(entry -> writer.println(entry.getKey() + "=" + entry.getValue()));
        writer.close();
    }
}
