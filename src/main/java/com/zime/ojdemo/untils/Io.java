package com.zime.ojdemo.untils;

import com.zime.ojdemo.modle.vo.result.fileResult;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

@Component
public class Io {
    public static void write(String zhi, String wz) throws IOException {
        File file = new File(wz);
        FileWriter fw = new FileWriter(file, false);
        fw.write(zhi);
        fw.close();
    }

    public static ArrayList<fileResult> readPro(File file) {
        if (Objects.isNull(file) || !file.exists() || file.isFile()) {
            return null;
        }

        File[] files = file.listFiles();
        ArrayList<fileResult> zhi = new ArrayList<>();
        for (File f : files) {
            fileResult fileResult = new fileResult();
            fileResult.setName(f.getName());
            fileResult.setTime(f.lastModified());
            fileResult.setSize(f.length());
            zhi.add(fileResult);
        }
        return zhi;
    }

    public static String read(File file) throws IOException {
        FileReader fr = new FileReader(file);
        // 一次性取多少个字节
        char[] chars = new char[1024];
        // 用来接收读取的字节数组
        StringBuilder sb = new StringBuilder();
        // 读取到的字节数组长度，为-1时表示没有数据
        int length;
        // 循环取数据
        while ((length = fr.read(chars)) != -1) {
            // 将读取的内容转换成字符串
            sb.append(chars, 0, length);
        }
        // 关闭流
        fr.close();

        return sb.toString();
    }

    public static Boolean deleteFile(File file) {
        //判断文件不为null或文件目录存在
        if (file == null || !file.exists()) {
            System.out.println("文件删除失败,请检查文件路径是否正确");
            return false;
        }
        //取得这个目录下的所有子文件对象
        File[] files = file.listFiles();
        //遍历该目录下的文件对象
        for (File f : files) {
            //打印文件名
            String name = file.getName();
            System.out.println(name);
            //判断子目录是否存在子目录,如果是文件则删除
            if (f.isDirectory()) {
                deleteFile(f);
            } else {
                f.delete();
            }
        }
        //删除空文件夹  for循环已经把上一层节点的目录清空。
        return file.delete();
    }
}
