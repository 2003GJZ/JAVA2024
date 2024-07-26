package org.example.pagemethod;


import org.example.URL;
import org.example.tool.FileUtil;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Handler {
        String Content_Type="text/html";
    default  void init(String url,InputStream in , OutputStream out){
        String filePath;
        try {
            StringBuffer buffer = FileUtil.readFromFile(URL.Disposition_json);
            JSONObject jsonObject = new JSONObject(buffer.toString());
            // 获取 "filter" 对象
            JSONObject filterObject = jsonObject.getJSONObject("reveal");
            // 获取 "url" 对应的值
            filePath = filterObject.getString(url);
            // 指定 login.html 文件路径
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        handler(in,out,filePath);
    }
         abstract void handler (InputStream in , OutputStream out, String filePath);
}