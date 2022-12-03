package top.hapleow.core;

import cn.hutool.core.io.resource.ClassPathResource;
import org.beetl.core.Template;
import top.hapleow.core.config.GroupTemplateConfig;
import top.hapleow.core.detective.FilePathAutoDetective;
import top.hapleow.core.detective.ImportListAutoDetective;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author wuyulin
 * @description
 * @date 2022/12/1
 */
public class CodeGenerator {


    private FilePathAutoDetective filePathAutoDetective = new FilePathAutoDetective();
    private ImportListAutoDetective importListAutoDetective = new ImportListAutoDetective();

    private Map<String, Object> content = new HashMap<>();

    public Map<String, Object> getContent() {
        return content;
    }

    /**
     * 执行生成方法
     *
     * @param rootPath
     * @param modelName
     * @param templatePath
     * @param tags
     */
    public void execute(String rootPath, String modelName, String templatePath, String... tags) {

        // 从模板拼接文件名
        String fileName = getFileName(modelName, templatePath);

        // 设置模板变量
        content.put("modelName", modelName);

        // 执行模板引擎，获取生成内容
        String str = render(content, templatePath);
        Set<String> clazzSet = importListAutoDetective.findClassNames(str);

        // 获取需要导入的类全限定名并绑定到模板引擎的变量中
        Set<String> imports = importListAutoDetective.getImportClasses(clazzSet, rootPath, fileName);
        content.put("imports", imports);

        // 自动探测需要生成到的目录
        File file = filePathAutoDetective.detectPath(rootPath, tags);
        File serviceFile = new File(file.getAbsolutePath() + File.separator + fileName);

        String pkg = filePathAutoDetective.calClassPackage(serviceFile);
        content.put("packageName", pkg);

        // 重新生成模板内容
        String newContent = render(content, templatePath);

        // 将内容写入类文件中
        writeAndFlush(newContent, serviceFile);
    }

    /**
     * 从模板名称获取文件名称
     *
     * @param modelName
     * @param templatePath
     * @return
     */
    private String getFileName(String modelName, String templatePath) {

        File file = new ClassPathResource(templatePath).getFile();
        if (file == null) {
            throw new RuntimeException("模板文件" + templatePath + "不存在！");
        }
        return file.getName().replace("model", modelName).replace(".btl", "");
    }


    /**
     * 写文件
     *
     * @param str
     * @param serviceFile
     */
    public void writeAndFlush(String str, File serviceFile) {
        try {
            serviceFile.createNewFile();
            try (FileOutputStream fileOutputStream = new FileOutputStream(serviceFile)) {
                fileOutputStream.write(str.getBytes(StandardCharsets.UTF_8));
            } finally {
                // todo
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行渲染
     *
     * @return
     * @throws IOException
     */
    public String render(Map<String, Object> content, String fileClassPath) {

        //初始化代码
        Template t = GroupTemplateConfig.groupTemplate().getTemplate(fileClassPath);
        t.binding("content", content);
        return t.render();
    }


}
