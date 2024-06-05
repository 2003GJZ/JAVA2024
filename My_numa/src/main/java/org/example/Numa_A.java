package org.example;

public class Numa_A extends Numa{
    String[] other_nameLocal;//要访问的变量名
    public Numa_A(String nameLocal, String bodyLocal,String... other_nameLocal) {
        super(nameLocal, bodyLocal);
        this.other_nameLocal=other_nameLocal;
    }
    @Override
    public void run() {
        for (String s : other_nameLocal) {
            scquire(s);
        }

        dorun();
    }

}
