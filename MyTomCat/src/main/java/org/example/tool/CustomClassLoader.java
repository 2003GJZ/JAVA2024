package org.example.tool;
//org.example.tool.hello
import org.example.compile.JavaCompilerAllFiles;

import java.io.*;
import java.lang.reflect.Method;

public class CustomClassLoader extends ClassLoader {

    private final String classPath; // 保存类文件目录路径

    public CustomClassLoader(String classPath) {
        this.classPath = classPath; // 初始化类加载器时设置类文件目录路径
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {

            // 编译Java文件
//            JavaCompilerAllFiles.compile();
            // 将类名转换为文件路径
            String fileName = name.replace('.', File.separatorChar)+".class" ;
            System.out.println(fileName);
            File classFile = new File(classPath, fileName); // 创建文件对象，表示类文件

            // 读取类文件的字节码
            FileInputStream inputStream = new FileInputStream(classFile);
            byte[] classData = new byte[(int) classFile.length()]; // 创建字节数组存储类数据
            inputStream.read(classData); // 读取类文件内容到字节数组
            inputStream.close(); // 关闭文件输入流

            // 使用字节码定义类
            return defineClass(name, classData, 0, classData.length);
        } catch (IOException e) {
            // 如果读取类文件失败，抛出ClassNotFoundException
            throw new ClassNotFoundException("Could not load class " + name, e);
        }
    }

    public static void do_quest(String classPath,String url, InputStream in, OutputStream out) {
        try {
            // 从 classPath 提取类文件目录和类名
            File classFile = new File(classPath);
            String fullClassPath = classFile.getParent(); // 提取目录路径
            String className = classFile.getName(); // 提取文件名

            // 去掉文件扩展名
            if (className.endsWith(".java")) {
                className = className.substring(0, className.length() - 5);
            } else if (className.endsWith(".class")) {
                className = className.substring(0, className.length() - 6);
            }

            // 将文件系统路径转换为包名结构
            String packagePath = fullClassPath.replace(File.separatorChar, '.');

            // 创建自定义类加载器实例，传入类文件的目录路径
            CustomClassLoader loader = new CustomClassLoader(fullClassPath);

            // 使用自定义类加载器加载指定的类
            Class<?> loadedClass = loader.loadClass(packagePath + "." + className);

            // 创建加载类的实例
            Object instance = loadedClass.getDeclaredConstructor().newInstance();

            // 获取类中名为"init"的方法，该方法接受两个参数：InputStream 和 OutputStream
            Method method = loadedClass.getMethod("init",String.class, InputStream.class, OutputStream.class);

            // 调用方法，并传递参数
            method.invoke(instance,url, in, out);
        } catch (Exception e) {
            // 捕获并打印所有异常
            e.printStackTrace();
        }
    }



}
