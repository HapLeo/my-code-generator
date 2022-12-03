package top.hapleow.mcgdatasource.service;

import top.hapleow.mcgdatasource.dto.CodeGenCmd;
import top.hapleow.mcgdatasource.model.TableInfo;

import java.util.List;
import java.util.Map;

/**
 * @author wuyulin
 * @description
 * @date 2022/12/2
 */
public interface ICodeGenService {

    List<Map<String, String>> listTables();

    TableInfo listColumns(String tableName);

    void codGen(CodeGenCmd codeGenCmd);
}
