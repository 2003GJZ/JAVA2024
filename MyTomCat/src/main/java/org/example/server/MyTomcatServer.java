package org.example.server;

import org.example.URL;
import org.example.tool.CustomClassLoader;
import org.example.tool.FileUtil;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;

public class MyTomcatServer extends Thread {
    private Socket clientSocket;
    String httpVersion;

    public MyTomcatServer(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (InputStream in = clientSocket.getInputStream();
             OutputStream out = clientSocket.getOutputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            // 读取请求行
            String requestLine = reader.readLine();
            if (requestLine != null) {
                System.out.println("Request Line: " + requestLine);

                // 解析请求行，提取URI
                String[] requestParts = requestLine.split(" ");
                if (requestParts.length >= 2) {
                    String method = requestParts[0];
                    String uri = requestParts[1];
                    String httpVersion = requestParts[2];

                    System.out.println("Method: " + method);
                    System.out.println("URI: " + uri);
                    System.out.println("HTTP Version: " + httpVersion);
                    if (method.equals("GET")|| method.equals("POST")) {
                        StringBuffer buffer = FileUtil.readFromFile(URL.Disposition_json);
                        JSONObject jsonObject = new JSONObject(buffer.toString());
                        // 获取 "filter" 对象
                        JSONObject filterObject = jsonObject.getJSONObject("path");
                        // 获取 "url" 对应的值
                        String valueForSlash;
                        try {
                            valueForSlash = filterObject.getString(uri);
                        }catch (Exception e){
                            valueForSlash = null;
                        }
                        if(valueForSlash==null||valueForSlash.equals("")){
                            CustomClassLoader.do_quest(filterObject.getString("/not"),"/not", in, out);
                        }else {
                            CustomClassLoader.do_quest(valueForSlash,uri, in, out);
                        }
                    }else {
                        // 只支持GET方法，其他方法返回405方法不允许
                        String methodNotAllowedResponse = "HTTP/1.1 405 Method Not Allowed\r\n" +
                                "Content-Type: text/html\r\n" +
                                "\r\n" +
                                "<html>" +
                                "<head><title>405 Method Not Allowed</title></head>" +
                                "<body><h1>405 - Method Not Allowed</h1></body>" +
                                "</html>";
                        out.write(methodNotAllowedResponse.getBytes());
                    }



                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
