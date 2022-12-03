package top.hapleow.core.Templates;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuyulin
 * @description
 * @date 2022/12/3
 */
public class ListTemplates {


    public static List<String> listTemplates() {

        URL url = ListTemplates.class.getResource("/template");
        if (url == null) return new ArrayList<>();
        File file = new File(url.getFile());
        File[] files = file.listFiles();
        if (files == null) return new ArrayList<>();
        return Arrays.stream(files).map(File::getName).collect(Collectors.toList());
    }
}
