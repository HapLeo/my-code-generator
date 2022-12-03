package top.hapleow.core;

import lombok.Data;

/**
 * @author wuyulin
 * @description
 * @date 2022/12/2
 */
@Data
public class GenParams {

    /**
     * 项目根目录
     */
    private String rootPath;


    /**
     * 模型名称
     */
    private String modelName;


    /**
     * 模板名称
     */
    private String templatePath;


}
