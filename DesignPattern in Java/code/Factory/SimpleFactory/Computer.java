package Factory.SimpleFactory;
//抽象产品类
public abstract class Computer {

    //所有产品的共有功能  电脑 可以开机，关机
    public abstract void start();//开机

    public abstract void stop();//关机
}
