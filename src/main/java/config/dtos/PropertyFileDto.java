package config.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class PropertyFileDto {
    private final String name;
    private final Map<String, String> properties;
}
