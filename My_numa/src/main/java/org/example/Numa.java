package org.example;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

public class Numa extends Thread{
    HashMap <String, String> map = new HashMap<>();//可能一次拿多个 用来模拟 拿到的变量名和变量值  不是表示把别的cpu的变量值拿过来在自己这里存一份
    public String cpuname;//用线程名来表示cpu
    public ThreadLocal<String> bodyLocal;//内容
    public ThreadLocal<String> nameLocal;//变量名
    public Numa R;//右近点
    public Numa L;//左近点

    public Numa( String nameLocal,String bodyLocal) {
        this.cpuname = getName();
        this.bodyLocal = ThreadLocal.withInitial(() -> bodyLocal);
        this.nameLocal = ThreadLocal.withInitial(() -> nameLocal);
        System.out.println("cpu："+this.cpuname+"    name:"+nameLocal+"   body:"+bodyLocal);
    }
    public ArrayBlockingQueue<MyccNUMA> queue = new ArrayBlockingQueue<>(100);//队列 消息队列

   public void scquire(String other_nameLocal){
       Long num=System.nanoTime();//充当任务编号 用时间戳代替
    toL(new MyccNUMA(this.cpuname,other_nameLocal,true,num));
    toR(new MyccNUMA(this.cpuname,other_nameLocal,false,num));
   }
    public void addmessage(MyccNUMA message){
        queue.add(message);
    }
    public void toR(MyccNUMA message){//向右端发送消息
        if(message.nameLocal.equals(nameLocal.get())){
            message.destination=this.cpuname;
            message.bodyLocal=bodyLocal.get();//放入目标内容
            message.LorR=!message.LorR;
            System.out.println("<--找到目标--"+this.cpuname+"--开始回包-->");
            L.addmessage(message);
        }else{
            if(R!=null){
                System.out.println(this.cpuname+"：("+message.nameLocal+")转包---->");
                R.addmessage(message);
            }else {
                System.out.println(this.cpuname+"：("+message.nameLocal+")右端无请求内容---->");
                //下面操作为了避免两端都没有请求内容时，出现死循环，所以将请求频率设置为负数
                message.LorR=!message.LorR;
                message.frequency=-3*message.frequency;
                L.addmessage(message);
            }

        }

    }
    public void toL(MyccNUMA message){//向左端发送消息
        if(message.nameLocal.equals(nameLocal.get())){
            message.destination=this.cpuname;
            message.bodyLocal=this.bodyLocal.get();//放入目标内容
            message.LorR=!message.LorR;
            System.out.println("<--找到目标--  源："+message.source+"  到达："+this.cpuname+"  --开始回包-->");
            R.addmessage(message);
        }else{
            if(L!=null){
                System.out.println("<----转包("+message.nameLocal+")："+this.cpuname);
                L.addmessage(message);
            }else {
                System.out.println("<----左端无请求内容("+message.nameLocal+")："+this.cpuname);
                message.LorR=!message.LorR;
                //下面操作为了避免两端都没有请求内容时，出现死循环，所以将请求频率设置为负数
                message.frequency=-3*message.frequency;
                R.addmessage(message);
            }
        }
    }

    public void dorun(){
        while (true){
            if(queue.size()>0){
                MyccNUMA message = queue.poll();
                message.frequency++;
                if(!message.source.equals(this.cpuname)){
                    if(message.LorR){
                        toL(message);
                    }else{
                        toR(message);
                    }
                }else {
                    if(message.frequency>0){
                        System.out.print("                       接收到回包----来源：");
                        if(message.LorR){
                            System.out.print("右");
                        }else {
                            System.out.print("左");
                        }

                        if (message.frequency==2){
                            System.out.print("近");
                        }else {
                            for (int i = 0; i < message.frequency/2; i++) {
                                System.out.print("远");
                            }
                        }

                        System.out.println("节点："+message.destination);
                        System.out.println("                                                                 变量名："+message.nameLocal+"    内容："+message.bodyLocal);
                        map.put(message.nameLocal,message.bodyLocal);
                    }else {
                        if(message.LorR){
                            System.out.print("右");
                        }else {
                            System.out.print("左");
                        }
                        System.out.println("请求内容为空：任务号："+message.num);
                    }

                }

            }
        }
    }

    @Override
    public void run() {
        dorun();
    }
}
