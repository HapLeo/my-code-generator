package top.hapleow.mcgdatasource.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import top.hapleow.core.CodeGenerator;
import top.hapleow.mcgdatasource.dto.CodeGenCmd;
import top.hapleow.mcgdatasource.model.TableColumn;
import top.hapleow.mcgdatasource.model.TableInfo;
import top.hapleow.mcgdatasource.service.ICodeGenService;

import java.util.List;
import java.util.Map;

/**
 * @author wuyulin
 * @description
 * @date 2022/12/2
 */
@Service
public class CodeGenServiceImpl implements ICodeGenService {
    public static final String LIST_TABLES_SQL = "show table status WHERE 1=1";
    public static final String LIST_FIELDS_SQL = "show full fields from %s";
    public static final String TABLE_DETAIL_SQL = "select table_name as name,TABLE_COMMENT as comment from information_schema.`TABLES` where table_name = ? ";

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<Map<String, String>> listTables() {
        return null;
    }

    @Override
    public TableInfo listColumns(String tableName) {
        Map<String, Object> tableBaseInfo = jdbcTemplate.queryForMap(TABLE_DETAIL_SQL, tableName);
        TableInfo tableInfo = new TableInfo();
        tableInfo.setName(String.valueOf(tableBaseInfo.get("name")));
        tableInfo.setComment(String.valueOf(tableBaseInfo.get("comment")));

        List<Map<String, Object>> maps = jdbcTemplate.queryForList(String.format(LIST_FIELDS_SQL, tableName));
        CopyOptions copyOptions = CopyOptions.create();
        copyOptions.ignoreCase();
        List<TableColumn> tableColumns = BeanUtil.copyToList(maps, TableColumn.class, copyOptions);
        tableInfo.setFields(tableColumns);
        tableInfo.repair();
        return tableInfo;
    }

    @Override
    public void codGen(CodeGenCmd codeGenCmd) {

        TableInfo table = listColumns(codeGenCmd.getTableName());
        CodeGenerator codeGenerator = new CodeGenerator();
        Map<String, Object> content = codeGenerator.getContent();
        content.put("table", table);
        codeGenerator.execute(codeGenCmd.getRootPath(), StrUtil.upperFirst(StrUtil.toCamelCase(codeGenCmd.getTableName())), codeGenCmd.getTemplatePath(), codeGenCmd.getTags());
        System.out.println();

    }
}
