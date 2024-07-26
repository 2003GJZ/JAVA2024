package org.example.tool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {

    // 读取文件内容到StringBuffer，每次读取1024字节
    public static StringBuffer readFromFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        StringBuffer buffer = new StringBuffer();

        byte[] bytes = Files.readAllBytes(path);
        int offset = 0;
        while (offset < bytes.length) {
            int length = Math.min(1024, bytes.length - offset);
            buffer.append(new String(bytes, offset, length));
            offset += length;
        }

        return buffer;
    }

    // 将StringBuffer中的内容写入文件
    public static void writeToFile(String filePath, StringBuffer buffer) throws IOException {
        Path path = Paths.get(filePath);
        Files.write(path, buffer.toString().getBytes());
    }
}
