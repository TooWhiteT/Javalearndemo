package Singlecase;

public class SingleCase02 {
    private static SingleCase02 instance;

    private SingleCase02() {
    }
    /** 懒汉单例
     * 实现了懒加载，节约了内存空间
     *
     * 在不加锁的情况下，线程不安全，可能出现多份实例
     * 在加锁的情况下，会使程序串行化，使系统有严重的性能问题
     */
    //不加锁 线程不安全
    public static SingleCase02 getInstance() {
        if (instance == null) {
            instance = new SingleCase02();
        }
        return instance;
    }
    //加锁 线程安全
//    public synchronized static SingleCase02 getInstance() {
//        if (instance == null) {
//            instance = new SingleCase02();
//        }
//        return instance;
//    }
}
