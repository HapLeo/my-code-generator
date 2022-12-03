package top.hapleow.core.detective;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Properties;

public class ImportListAutoDetectiveTest {

    private ImportListAutoDetective importListAutoDetective;

    @Before
    public void before() {
        importListAutoDetective = new ImportListAutoDetective();
    }

    @Test
    public void readPathFilesImportList() {
        Map<String, String> importList = importListAutoDetective.readPathFilesImportList("D:\\20210406\\workspace\\aiwoplatformat");
        for (Map.Entry<String, String> entry : importList.entrySet()) {
            if (entry.getValue().contains("com.jbs")) continue;
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
    }

    @Test
    public void getImportTemplateItems(){
        Properties importTemplateItems = ImportListAutoDetective.getImportTemplateItems();

    }

}