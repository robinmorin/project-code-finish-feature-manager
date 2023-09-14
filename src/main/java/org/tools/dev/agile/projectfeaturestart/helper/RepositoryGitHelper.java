package org.tools.dev.agile.projectfeaturestart.helper;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RepositoryGitHelper {

    private Repository repo;
    private final String directory;
    private Git gitManager;

    public RepositoryGitHelper(String directory) throws IOException {
        repo = new FileRepositoryBuilder()
                .setGitDir(new File(directory.concat("\\.git")))
                .build();
        this.directory = directory;
        gitManager = new Git(repo);
    }

    public BlockingQueue<File> getFileFromBranch(String branchName, String filenamePattern) throws GitAPIException, IOException {
        //Todo: tratamento de errores
        gitManager.checkout().setName(branchName).call();
        gitManager.pull();

        try (Stream<Path> listFiles = Files.list(Paths.get(directory.concat("\\charts")))) {

            return listFiles
                    .filter(file -> !Files.isDirectory(file))
                    .filter(file -> (!ObjectUtils.isEmpty(filenamePattern) && file.getFileName().startsWith(filenamePattern)))
                    .map(Path::toFile).distinct()
                    .collect(Collectors.toCollection(LinkedBlockingQueue::new));
        }
    }

    public boolean checkExistBranch(String branchName) throws GitAPIException {
        return gitManager.branchList().call().stream()
                .anyMatch(ref -> ref.getName().equalsIgnoreCase(branchName));
    }
}
