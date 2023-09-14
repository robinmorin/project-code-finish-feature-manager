package org.tools.dev.agile.projectfeaturestart.helper;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FileManagerEnvHelper {

    private String fileName;

    public FileManagerEnvHelper(String filename) {
        this.fileName = filename;
    }


    public void fillFileContent(Map<String,String> contentMap){
        try (FileWriter writer = new FileWriter(fileName,false)){
            contentMap.forEach((key, value) -> {
                try {
                    writer.write(key.concat("=").concat(value));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
