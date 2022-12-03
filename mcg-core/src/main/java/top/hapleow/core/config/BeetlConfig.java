package top.hapleow.core.config;

import org.beetl.core.Configuration;

import java.io.IOException;

/**
 * @author wuyulin
 * @description
 * @date 2022/12/2
 */
public class BeetlConfig {


    public static Configuration configuration() {

        Configuration cfg = null;
        try {
            cfg = new Configuration();
            cfg.setHtmlTagSupport(false);
            cfg.setPlaceholderStart("@{");
            cfg.setPlaceholderEnd("}");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return cfg;
    }
}
