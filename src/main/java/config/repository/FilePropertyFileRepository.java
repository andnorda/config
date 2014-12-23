package config.repository;

import config.dtos.PropertyFileDto;
import config.repository.FileRepository;
import config.repository.PropertyFileRepository;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FilePropertyFileRepository implements PropertyFileRepository {
    private final FileRepository fileRepository;

    public FilePropertyFileRepository(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
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

    @Override
    public PropertyFileDto get(String... path) {
        File file = fileRepository.getFile(path);
        return new PropertyFileDto(file.getName(), getProperties(file));
    }

    @Override
    public void update(PropertyFileDto propertyFileDto, String... path) {
        String fileName = path[path.length - 1];
        File file = fileRepository.getDir(Arrays.copyOf(path, path.length - 1));
        fileRepository.getFile(path);

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

    private Map<String, String> getProperties(File file) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey().toString(),
                        entry -> entry.getValue().toString()));
    }
}
