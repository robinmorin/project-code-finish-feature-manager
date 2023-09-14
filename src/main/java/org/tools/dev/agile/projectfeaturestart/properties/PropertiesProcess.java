package org.tools.dev.agile.projectfeaturestart.properties;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropertiesProcess {
    private String profile;
    private String extFile;
    private String branchName;
    private String patternFileEnv;
    private String prefixFileToGet;
    private String gitDirectory;

    @Override
    public String toString() {
            return "Variaveis para processamento. PropertiesProcess [" +
                "profile='" + profile + '\'' +
                ", extFile='" + extFile + '\'' +
                ", branchName='" + branchName + '\'' +
                ", patternFileEnv='" + patternFileEnv + '\'' +
                ", prefixFileToGet='" + prefixFileToGet + '\'' +
                ", gitDirectory=" + gitDirectory +']';
    }
}
