package Singlecase;

public class SingleCase01 {
    //静态实例化对象
    private static final SingleCase01 instance = new SingleCase01();//饿汉
    //私有构造方法
    private SingleCase01 () {

    }
    //提供公共接口访问

    /** 饿汉单例模式
     * 由于使用了static关键字，保证了在引用这个变量时，关于这个变量的所以写入操作都完成，所以保证了JVM层面的线程安全
     *
     * 不能实现懒加载，造成空间浪费，如果一个类比较大，我们在初始化的时就加载了这个类，但是我们长时间没有使用这个类，这就导致了内存空间的浪费
     */
    public static SingleCase01 getInstance () {
        return instance;
    }

}
