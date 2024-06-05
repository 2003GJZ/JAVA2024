package org.example;

public class MyccNUMA {

    public String source;  //包的源节点
    public String destination;  //回包节点
    public String nameLocal;  //请求的变量

    public String bodyLocal;  //回包内容
    public boolean LorR;   //来判断，包往哪边传    true 往左，false 往右
    public int frequency=0;  //包被转发的次数
    public Long num;  //包的编号
    public MyccNUMA(String source, String nameLocal, boolean lorR, Long num) {
        System.out.println("生成消息 MyccNUMA:"+nameLocal);
        this.source = source;
        this.nameLocal = nameLocal;
        LorR = lorR;
        this.num = num;
    }


}
