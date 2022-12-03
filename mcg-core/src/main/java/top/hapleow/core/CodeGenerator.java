package top.hapleow.core;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
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


    /**
     * 执行生成方法
     *
     * @param rootPath
     * @param modelName
     * @param templateName
     * @param tableContent
     * @param tags
     */
    public void execute(String rootPath, String modelName, String templateName, final Map<String, Object> tableContent, String... tags) {

        Map<String,Object> content = new HashMap<>(tableContent);

        String templatePath = "/template/" + templateName;

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

        // 如果没有指定tags，则分析模板默认的tags
        if (tags == null || tags.length == 0) {
            tags = getTemplateTags(templateName);
        }
        // 自动探测需要生成到的目录
        File file = filePathAutoDetective.detectPath(rootPath, tags);
        if (file == null)
            throw new RuntimeException("未找到合适的文件夹，请指定更多tags或更精确的rootPath以便更精确的匹配目标文件夹！");
        File serviceFile = new File(file.getAbsolutePath() + File.separator + fileName);

        String pkg = filePathAutoDetective.calClassPackage(serviceFile);
        content.put("packageName", pkg);

        // 重新生成模板内容
        String newContent = render(content, templatePath);

        // 将内容写入类文件中
        writeAndFlush(newContent, serviceFile);
    }

    /**
     * 获取模板默认的tags
     *
     * @param templateName
     * @return
     */
    private String[] getTemplateTags(String templateName) {

        if (templateName == null) return null;

        // model模板单独处理
        if (templateName.startsWith("model.")) {
            return new String[]{"model", "java"};
        }
        return StrUtil.toUnderlineCase(templateName).replace(".btl", "").replace("model", "").replace("imodel", "").replace("_", ".").split("\\.");
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
        return file.getName().split(".btl")[0].replace("model", modelName);
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
