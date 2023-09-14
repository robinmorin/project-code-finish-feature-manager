package org.tools.dev.agile.projectfeaturestart.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.tools.dev.agile.projectfeaturestart.helper.FileManagerEnvHelper;
import org.tools.dev.agile.projectfeaturestart.helper.FileManagerYamlHelper;
import org.tools.dev.agile.projectfeaturestart.helper.RepositoryGitHelper;
import org.tools.dev.agile.projectfeaturestart.properties.PropertiesProcess;
import org.tools.dev.agile.projectfeaturestart.service.ProcessService;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.INTERFACES)
@Slf4j
public class ProcessServiceimpl implements ProcessService {

    private FileManagerYamlHelper yamlHelper;

    public List<String> execute(PropertiesProcess properties) throws IOException, GitAPIException, InterruptedException {
        log.info("{}",properties);
        log.info("Iniciando processo de extração de Environment Variables desde o repositorio que contem os arquivos charts");
        RepositoryGitHelper gitHelper = new RepositoryGitHelper(properties.getGitDirectory());
        gitHelper.checkExistBranch(properties.getBranchName());
        var lstFile = gitHelper.getFileFromBranch(properties.getBranchName(), properties.getPrefixFileToGet());
        while (lstFile.size() > 0){
            var mapVars = yamlHelper.loadEnvVariables(lstFile.poll());
            String fileName = resolvePatternFileName(properties.getPatternFileEnv(),properties);
            FileManagerEnvHelper envHelper = new FileManagerEnvHelper(fileName);
            envHelper.fillFileContent(mapVars);
        }
        var mapas2 = yamlHelper.loadEnvVariables(lstFile.take());
        var mapas3 = yamlHelper.loadEnvVariables(lstFile.take());
        return null;
    }

    private String resolvePatternFileName(String patternFileEnv, PropertiesProcess properties) {
        return null;
    }

    private boolean isAllUpperCaseValidChars(String value){
        return value.chars().filter(ch -> ch != '-' && ch != '_').allMatch(Character::isLowerCase);
    }

}
