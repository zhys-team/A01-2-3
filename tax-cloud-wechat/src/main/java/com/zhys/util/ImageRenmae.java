package com.zhys.util;
import java.io.File;

public class ImageRenmae {
    public static void main(String[] args) {
        String src = "D:\\test\\temp";
        String dest = "D:\\test\\dest";

//        int nameStartIndex = 1;
//        File file = new File(src);
//        String[] files = file.list();
//        for (String fileName : files) {
//            File destFile = new File(src + "\\" + fileName);
//            destFile.renameTo(new File(dest + "\\" + nameStartIndex));
//            nameStartIndex++;
//        }
        
        File file = new File(src);
        String[] files = file.list();
        for (String fileName : files) {
            File destFile = new File(src + "\\" + fileName);
            destFile.renameTo(new File(dest + "\\" + fileName.substring(0, fileName.indexOf("."))));
        }

    }
}
