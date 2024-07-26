package org.example;

public class URL {
    public static String currentDirectory ;
    static {
        currentDirectory = System.getProperty("user.dir");
    }
    public static String CONFIG_FILE="src/main/java/org/example/disposition/struct.json";
    public static String Disposition_json="src/main/java/org/example/disposition/disposition.json";
//    public static String Disposition_json="src/main/java/org/example/disposition/disposition.json";
}