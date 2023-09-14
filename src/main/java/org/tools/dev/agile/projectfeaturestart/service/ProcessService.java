package org.tools.dev.agile.projectfeaturestart.service;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.tools.dev.agile.projectfeaturestart.properties.PropertiesProcess;

import java.io.IOException;
import java.util.List;

public interface ProcessService {

    List<String> execute(PropertiesProcess properties) throws IOException, GitAPIException, InterruptedException;

}
