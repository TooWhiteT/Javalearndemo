package Singlecase;

public class SingleCase03 {
    //要解决双重检查锁模式带来空指针异常的问题，只需要使用volatile关键字，volatile关键字严格遵循happens-before原则，即在读操作前，写操作必须全部完成
    private static volatile SingleCase03 instance;
    //添加volatile关键字之后的双重检查锁模式是一种比较好的单例实现模式，能够保证在多线程的情况下线程安全也不会有性能问题。
    //private static SingleCase03 instance;

    private SingleCase03() {
    }
    /**
     * DCL
     * 双重检查锁模式是一种非常好的单例实现模式，解决了单例、性能、线程安全问题，上面的双重检测锁模式看上去完美无缺，
     * 其实是存在问题，在多线程的情况下，可能会出现 空指针 问题，出现问题的原因是JVM在实例化对象的时候会进行优化和指令重排序操作。
     *
     */
    public static SingleCase03 getInstance() {
        if (instance == null) {//第一次判断，如果这里为空，不进入抢锁阶段，直接返回实例
            synchronized (SingleCase03.class) {
                if (instance == null) {// 抢到锁之后再次判断是否为空
                    instance = new SingleCase03();
                }
            }
        }
        return instance;
    }
}
