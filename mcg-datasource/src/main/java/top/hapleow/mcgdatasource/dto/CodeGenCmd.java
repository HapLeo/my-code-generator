package top.hapleow.mcgdatasource.dto;

import lombok.Data;

/**
 * @author wuyulin
 * @description
 * @date 2022/12/2
 */
@Data
public class CodeGenCmd {

    private String rootPath;

    private String tableName;

    private String templatePath;

    private String[] tags;
}
