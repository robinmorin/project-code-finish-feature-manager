package org.tools.dev.agile.projectfeaturestart;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tools.dev.agile.projectfeaturestart.enums.ArgsEnum;
import org.tools.dev.agile.projectfeaturestart.properties.PropertiesProcess;
import org.tools.dev.agile.projectfeaturestart.service.ProcessService;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@SpringBootApplication
public class ExtractEnvVarsFromYamlApplication implements CommandLineRunner {

    private final ProcessService processService;

    public static void main(String[] args) {
        SpringApplication.run(ExtractEnvVarsFromYamlApplication.class, args);
    }

    public void run(String... args) throws Exception {
        if(args == null || args.length == 0){
            System.out.println("Insuficientes argumentos pra a executar a extração.");
            System.out.println("Pode enviar --help para mais informações.");
        } else if (Arrays.stream(args).anyMatch(s -> s.contains("--help"))){
            System.out.println("Documentação dos argumentos pra a executar a extração.\r\n\n");
            showDocArgs();
            showDocArqsRequired();
        }

        var propertiesProcess = processArgs(args);

        List<String> results = processService.execute(propertiesProcess);

        System.out.println("Resultados:");
        results.stream().map("\t"::concat).forEach(System.out::println);

    }

    private PropertiesProcess processArgs(String...args) {
        var argsList = List.of(args);
        validateRequiredArgs(argsList);
        var propProcess = new PropertiesProcess();
        propProcess.setProfile(assignValue(argsList, ArgsEnum.PROFILE));
        propProcess.setExtFile(assignValue(argsList, ArgsEnum.EXT_FILE));
        propProcess.setBranchName("refs/heads/" + assignValue(argsList, ArgsEnum.BRANCH_NAME));
        propProcess.setGitDirectory(assignValue(argsList, ArgsEnum.GIT_DIRECTORY));
        propProcess.setPatternFileEnv(assignValue(argsList, ArgsEnum.PATTERN_FILE));
        propProcess.setPatternFileEnv(assignValue(argsList, ArgsEnum.PREFIX_FILE_YAML));
        return propProcess;
    }

    private void validateRequiredArgs(List<String> args) {
        if(!ArgsEnum.getRequiredArgs().stream()
                                        .allMatch(argsItem ->
                                                Stream.of(args).flatMap(Collection::stream)
                                                      .anyMatch(argsIn -> argsIn.contains(argsItem)))){
            showDocArqsRequired();
            System.exit(1);
        }
    }

    private static void showDocArqsRequired() {
        System.out.println("Os seguintes argumentos são obrigatorios:");
        ArgsEnum.getRequiredArgs().stream().map("\t"::concat).forEach(System.out::println);
    }

    private static void showDocArgs() {
        Arrays.stream(ArgsEnum.values()).forEach(System.out::println);
    }

    private String assignValue(List<String> argsList, ArgsEnum argsEnum) {
        return argsList.stream()
                .filter(s -> s.contains(argsEnum.getArgument()))
                .findAny()
                .map(arg-> arg.split("=")[1])
                .orElse(argsEnum.getDefaultValue());
    }
}
