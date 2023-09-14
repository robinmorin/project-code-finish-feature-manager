package org.tools.dev.agile.projectfeaturestart.properties;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties("app.file-manager.default")
public class PropertiesDefault{

    private YamlFile yamlFile;
    private File file;

    @Getter
    @Setter
    public static class YamlFile {
        private String prefixFileToGet;
    }

    @Getter
    @Setter
    public static class File {
        private String ext;
        private String pattern;
        private String directory;
    }

}
