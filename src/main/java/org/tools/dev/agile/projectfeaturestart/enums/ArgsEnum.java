package org.tools.dev.agile.projectfeaturestart.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.tools.dev.agile.projectfeaturestart.helper.BeanStaticHelper;
import org.tools.dev.agile.projectfeaturestart.properties.PropertiesDefault;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum ArgsEnum {

    PROFILE("--profile",
            "String usado nas construção do nome dos arquivos que serão gerados.\n\r" +
                      "Por padrão o 'pattern file name' dos arquivos gerados é o seguinte..." +
                      BeanStaticHelper.getBean(PropertiesDefault.class).getFile().getPattern(),
            true,
            "<profile>",
            "local"),

    EXT_FILE("--extFile",
            "Extensão dos arquivos que serão gerados pela extração das variaveis desde os charts",
            false,
            "<ext-file>",
            BeanStaticHelper.getBean(PropertiesDefault.class).getFile().getExt()),

    BRANCH_NAME("--branchName",
                "Nome da branch desde onde sera feita a extração das variaveis dos charts",
                  true,
                     "<branch>",
              ""),

    PATTERN_FILE("--patternFile",
                "Pattern ou estrutura pra construir os nomes dos arquivos. \n\r" +
                          "Por padrão o 'pattern file name' dos arquivos gerados é o seguinte..." +
                          BeanStaticHelper.getBean(PropertiesDefault.class).getFile().getPattern(),
                  false,
                          BeanStaticHelper.getBean(PropertiesDefault.class).getFile().getPattern()),

    GIT_DIRECTORY("--gitDirectory",
                 "Diretorio base onde existe o repositorio de arquivos charts",
                  false,
                          BeanStaticHelper.getBean(PropertiesDefault.class).getFile().getDirectory()),

    PREFIX_FILE_YAML("--prefixFileYaml",
                    "Nome prefix que possuem os arquivos charts que precisaram ser tratados",
                      false,
                              BeanStaticHelper.getBean(PropertiesDefault.class).getYamlFile().getPrefixFileToGet());

    public static final String LFCR = "\n \r";
    private final String argument;
    private final String description;
    private final boolean required;
    private final String tag;
    private final String defaultValue;

    public static ArgsEnum getArgumentByText(String argument){
        return Arrays.stream(ArgsEnum.values())
                .filter(argsEnum -> argsEnum.argument.equalsIgnoreCase(argument))
                .findAny().orElse(null);
    }

    public static List<String> getRequiredArgs(){
        return Arrays.stream(ArgsEnum.values())
                .filter(ArgsEnum::isRequired)
                .map(ArgsEnum::getArgument)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\t");
        sb.append(argument).append(LFCR);
        sb.append("Descrição : \t'").append(description).append(LFCR);
        sb.append("Valor Padrão : \t'").append(defaultValue).append(LFCR);
        return sb.toString();
    }
}
