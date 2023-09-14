package org.tools.dev.agile.projectfeaturestart.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class FileManagerYamlHelper {

    private static final List<String> EXCLUDE_ENV_VAR = List.of("JAVA_OPTS");

    public Map<String, String> loadEnvVariables(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        var dataNode = mapper.readTree(file).path("spec").path("data").path("configmap").path("data");
        if(!dataNode.isEmpty()) {
            var dataPropMap = mapper.readValue(dataNode.asText(), new TypeReference<Map<String, String>>() {});
            return dataPropMap.entrySet().stream()
                    .filter(entry -> !EXCLUDE_ENV_VAR.contains(entry.getKey()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        } else return null;
    }
}
