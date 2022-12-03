package top.hapleow.core.util;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Data
public class JavaTypeConverter {

    private static Map<String, String> types = new HashMap<>();

    static {
        types.put("char", "String");
        types.put("varchar", "String");
        types.put("nvarchar", "String");

        types.put("decimal", "Double");
        types.put("double", "Double");

        types.put("tinyint", "Integer");
        types.put("int", "Integer");
        types.put("long", "Long");
        types.put("bigint", "Long");

        types.put("date", "LocalDate");
        types.put("datetime", "LocalDateTime");

        types.put("boolean", "Boolean");

    }


    /**
     * 将数据库的类型转换成Java类型
     *
     * @param type
     * @return
     */
    public static String convert(String type) {

        type = type.toLowerCase().split(" ")[0];
        type = type.split("\\(")[0];

        for (String key : types.keySet()) {
            if (Objects.equals(key, type)) {
                return types.get(key);
            }
            if (key.contains(type)) {
                return types.get(key);
            }
        }
        return "String";
    }
}
