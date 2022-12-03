package top.hapleow.core.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuyulin
 * @description
 * @date 2022/12/1
 */
public class FileUtil {


    public static List<File> listFile(String rootPath) {

        File rootFile = new File(rootPath);
        List<File> fileList = new ArrayList<>();
        fileList.add(rootFile);
        listFile(rootFile, fileList);
        return fileList;
    }


    private static void listFile(File rootPath, List<File> fileList) {

        if (rootPath.isDirectory()) {
            File[] files = rootPath.listFiles();
            if (files == null || files.length == 0) {
                return;
            }
            for (File file : files) {
                fileList.add(file);
                listFile(file.getAbsoluteFile(), fileList);
            }
        }
    }


    /**
     * 获取根目录下的目录列表
     * 不包含文件
     *
     * @param rootPath
     * @return
     */
    public static List<File> listDirectory(String rootPath) {
        List<File> fileList = listFile(rootPath);
        return fileList.stream().filter(File::isDirectory).collect(Collectors.toList());
    }

    /**
     * 获取根目录下的文件列表
     * 不包含目录
     *
     * @param rootPath
     * @return
     */
    public static List<File> listRealFile(String rootPath) {
        List<File> fileList = listFile(rootPath);
        return fileList.stream().filter(File::isFile).collect(Collectors.toList());
    }

}
