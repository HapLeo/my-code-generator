package top.hapleow.core.config;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.ResourceLoader;
import org.beetl.core.resource.ClasspathResourceLoader;

/**
 * @author wuyulin
 * @description
 * @date 2022/12/2
 */
public class GroupTemplateConfig {


    public static GroupTemplate groupTemplate() {

        return groupTemplate(BeetlConfig.configuration());
    }


    private static GroupTemplate groupTemplate(Configuration cfg) {

        if (cfg == null) {
            throw new RuntimeException("configuration cannot be null ! ");
        }

        //初始化代码
        ResourceLoader resourceLoader = new ClasspathResourceLoader();
        return new GroupTemplate(resourceLoader, cfg);
    }
}
