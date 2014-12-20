package config;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class FileRepository implements ApplicationRepository {
    private File baseDir;

    public FileRepository(File baseDir) {
        this.baseDir = baseDir;
    }

    @Override
    public ApplicationDto get(String name) {
        return null;
    }

    @Override
    public Collection<ApplicationDto> getAll() {
        List<File> files = Arrays.asList(baseDir.listFiles());
        return files.stream()
                .map(file -> new ApplicationDto())
                .collect(Collectors.toCollection(HashSet::new));
    }
}
