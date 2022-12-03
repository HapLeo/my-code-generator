package top.hapleow.core;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class CodeGeneratorTest {

    public static CodeGenerator codeGenerator;


    @BeforeClass
    public static void beforeClass() throws Exception {
        codeGenerator = new CodeGenerator();
    }

    @Test
    public void hello() {
        Map<String, Object> content = new HashMap<>();
        content.put("name", "beetle");
        String str = codeGenerator.render(content, "template/hello.txt");
        System.out.println(str);

    }

    @Test
    public void service() {
        Map<String, Object> content = new HashMap<>();
        content.put("modelName", "ErpGoodsUnit");
        String str = codeGenerator.render(content, "template/ImodelService.java.btl");
        System.out.println(str);

    }

    @Test
    public void execute() {

        String rootPath = "D:\\20210406\\ideaSpace\\aiwo-plat-etl";
        String modelName = "ErpGoodsUnit";
        String templatePath = "template/ImodelService.java.btl";
        String[] tags = {"java", "service"};
        codeGenerator.execute(rootPath, modelName, templatePath, tags);
    }

    @Test
    public void execute2() {

        String rootPath = "D:\\20210406\\ideaSpace\\my-code-generator";
        String modelName = "ErpGoodsUnit";
        String templatePath = "template/ImodelService.java.btl";
        String[] tags = {"java", "service"};
        codeGenerator.execute(rootPath, modelName, templatePath, tags);
    }

}