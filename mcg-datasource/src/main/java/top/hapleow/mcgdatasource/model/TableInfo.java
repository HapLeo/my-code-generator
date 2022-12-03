package top.hapleow.mcgdatasource.model;

import lombok.Data;

import java.util.List;

/**
 * @author wuyulin
 * @description
 * @date 2022/12/2
 */
@Data
public class TableInfo {

    String name;

    String comment;

    List<TableColumn> fields;

    public void repair() {
        if (fields != null && fields.size() > 0) {
            fields.forEach(TableColumn::repair);
        }
    }
}
