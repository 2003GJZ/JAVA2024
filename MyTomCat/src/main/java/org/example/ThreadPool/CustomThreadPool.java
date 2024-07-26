package org.example.ThreadPool;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import org.example.URL;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;



public class CustomThreadPool {
    private static final String CONFIG_FILE = URL.CONFIG_FILE;
    private static ThreadPoolExecutor threadPool;
    static {

        ThreadPoolConfig config = loadConfig();

        // 使用默认配置，如果配置文件不存在或解析失败
        int maxPoolSize = config != null ? config.max : 12;
        int corePoolSize = config != null ? config.core : 8;
        long keepAliveTime = config != null ? config.keepAlive : 60;
        int queueCapacity = config != null ? config.queue : 1000;
        TimeUnit timeUnit = config != null && "ms".equals(config.unit) ? TimeUnit.MILLISECONDS : TimeUnit.SECONDS;
        System.out.println("maxPoolSize: " + maxPoolSize + ", corePoolSize: " + corePoolSize + ",\nkeepAliveTime: " + keepAliveTime + ", queueCapacity: " + queueCapacity + ", timeUnit: " + timeUnit);
        threadPool = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveTime,
                timeUnit,
                new LinkedBlockingQueue<>(queueCapacity)
        );
    }
    public static void close(String end) {
        if(end.equals("end")){
            threadPool.shutdown();
        }

    }


    // 加载配置文件
private static ThreadPoolConfig loadConfig() {
        // 创建Gson对象
Gson gson = new Gson();
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            // 从JSON文件中读取数据并转换为ThreadPoolConfig对象
            return gson.fromJson(reader, ThreadPoolConfig.class);
        } catch (FileNotFoundException e) {
            // 配置文件未找到，使用默认设置
            System.out.println("Config file not found, using default settings.");
        } catch (JsonIOException | JsonSyntaxException | IOException e) {
            // 异常处理
            e.printStackTrace();
        }
        return null;
    }
    //提交任务

    public static void submit(Runnable task) {
        threadPool.submit(task);
    }

}
