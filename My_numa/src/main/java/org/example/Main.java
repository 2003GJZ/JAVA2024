package org.example;
// 本项目是我简单实现了一个numa架构的java程序，其中包含一个Numa类，该类具有cpuname和L、R两个属性，L和R分别指向其左和右邻居节点。模拟了numa架构中的numa节点。
public class Main {
    public static void main(String[] args) {
     Numa x1=new Numa("x1","羊山公园");

     Numa x2=new Numa("x2","true");

     Numa x3=new Numa("x3","1368");

     Numa xxxx= new Numa("xxxxx","www.baidu.com");

     Numa ok= new Numa("ok","猴子偷桃");
     Numa_A yy= new Numa_A("yy","test","xxxxx","ok","x1","x2");
     Numa_A xx= new Numa_A("xx","test2","yy","ok","x1","x2");
     x1.L=x2;
     x2.R=x1;
     x2.L=x3;
     x3.R=x2;
     x3.L=yy;
     yy.R=x3;
     yy.L=xxxx;
     xxxx.R=yy;
     xxxx.L=ok;
     ok.R=xxxx;
     ok.L=xx;
     xx.R=ok;
     Numa ptr=x1;
        System.out.println("C P U:");
     while (ptr!=null){
         System.out.print(ptr.cpuname+"<--->");
         ptr.start();
         ptr=ptr.L;
     }
        System.out.println();
//        for (int i = 0; i < 2000; i++) {
//
//        }
//        x1.start();
//        x2.start();
//        x3.start();
//        xxxx.start();
//        ok.start();
//        yy.start();
//        xx.start();
//        while (true){
//
//        }
    }
}