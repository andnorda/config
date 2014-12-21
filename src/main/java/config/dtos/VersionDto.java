package config.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;

@Getter
@AllArgsConstructor
public class VersionDto {
    private final String name;
    private final Collection<String> instances;
}
