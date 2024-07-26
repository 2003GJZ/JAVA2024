package org.example.compile;


import org.example.URL;
import org.example.tool.FileUtil;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class JavaCompilerAllFiles {
    public static void compile1() {
        try {
            StringBuffer buffer = FileUtil.readFromFile(URL.Disposition_json);
            JSONObject jsonObject = new JSONObject(buffer.toString());
            JSONObject pathObject = jsonObject.getJSONObject("path");

            for (String key : pathObject.keySet()) {
                String value = pathObject.getString(key);
                System.out.println("编译文件：" + value + ".java");

                // 注意这里的类路径，添加你的Tomcat jar包路径
                ProcessBuilder processBuilder = new ProcessBuilder(
                        "javac",
                        "-cp",
                        URL.currentDirectory,
                        value + ".java"
                );

                processBuilder.redirectErrorStream(true);
                Process process = processBuilder.start();

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                }

                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    System.out.println("编译成功：" + value);
                } else {
                    System.out.println("编译失败：" + value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
