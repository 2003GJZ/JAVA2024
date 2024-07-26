package org.example.pagemethod;


import java.io.InputStream;
import java.io.OutputStream;

import org.example.tool.FileUtil;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Login implements Handler {
    String Content_Type = "text/html\n";
    @Override
    public void handler(InputStream in, OutputStream out, String filePath) {

        StringBuffer content;

        // 读取文件内容
        try {
            content = FileUtil.readFromFile(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 构建 HTTP 响应
        String response = "HTTP/1.1 200 OK\n" +
                "Content-Type: text/html\n" +
                "Content-Length: " + content.length() + "\n" +
                "\n" +
                content.toString();

        // 输出响应内容
        try {
            out.write(response.getBytes(StandardCharsets.UTF_8));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
