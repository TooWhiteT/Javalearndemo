### **享元模式定义**

享元模式是结构型设计模式的一种，是池技术的重要实现方式，它可以减少应用程序创建的对象，降低程序内存的占用，提高程序的性能。

**定义：使用共享对象有效的支持大量细粒度的对象**

要求细粒度对象，那么不可避免地使得对象数量多且性质相近，这些对象分为两个部分：内部状态和外部状态。内部状态是对象可共享出来的信息，存储在享元对象内部并且不会随环境的改变而改变。而外部状态是对象依赖的一个标记是随环境改变而改变的并且不可共享的状态。
享元模式结构图如下图所示。
[![img](https://s2.ax1x.com/2019/05/31/V192ZQ.png)](https://s2.ax1x.com/2019/05/31/V192ZQ.png)

在享元模式中有如下角色：

- Flyweight：抽象享元角色，同时定义出对象的外部状态和内部状态的接口或者实现。
- ConcreteFlyweight：具体享元角色，实现抽象享元角色定义的业务。
- FlyweightFactory：享元工厂，负责管理对象池和创建享元对象。

### **享元模式简单实现**

某宝商城卖商品，如果每个用户下单都生成商品对象显然会耗费很多资源，如果赶上双11，那恐怖的订单量会生成很多商品对象，更何况商城卖的商品种类繁多，这样就极易会产生OOM。因此我们采用享元模式来对商品的创建进行优化。

#### **抽象享元角色**

抽象享元角色是一个商品接口，它定义了showGoodsPrice方法用来展示商品的价格：

```java
public interface IGoods {
    public void showGoodsPrice(String name);
}
```

#### **具体享元角色**

定义类Goods，它实现IGoods 接口，并实现了showGoodsPrice方法，如下所示。

```java
public class Goods implements IGoods{
    private String name;//名称
    private String version;//版本
    Goods(String name){
        this.name=name;
    }
    @Override
    public void showGoodsPrice(String version) {
        if(version.equals("32G")){
            System.out.println("价格为5199元");
        }else if(version.equals("128G")){
            System.out.println("价格为5999元");
        }
    }
}
```

其中name为内部状态，version为外部状态。showGoodsPrice方法根据version的不同会打印出不同的价格。

#### **享元工厂**

```java
public class GoodsFactory {
    private static Map<String,Goods> pool=new HashMap<String, Goods>();
    public static Goods getGoods(String name){
        if(pool.containsKey(name)){
            System.out.println("使用缓存,key为:"+name);
            return pool.get(name);
        }else{
            Goods goods=new Goods(name);
            pool.put(name,goods);
            System.out.println("创建商品,key为:"+name);
            return goods;
        }
    }
}
```

享元工厂GoodsFactory 用来创建Goods对象。通过Map容器来存储Goods对象，将内部状态name作为Map的key，以便标识Goods对象。如果Map容器中包含此key，则使用Map容器中存储的Goods对象，否则就新创建Goods对象，并放入Map容器中。

#### **客户端调用**

客户端中调用GoodsFactory的getGoods方法来创建Goods对象，并调用Goods 的showGoodsPrice方法来显示产品的价格，如下所示。

```java
public class Client {
    public static void main(String[]args) {
        Goods goods1=GoodsFactory.getGoods("iphone7");
        goods1.showGoodsPrice("32G");
        Goods goods2=GoodsFactory.getGoods("iphone7");
        goods2.showGoodsPrice("32G");
        Goods goods3=GoodsFactory.getGoods("iphone7");
        goods3.showGoodsPrice("128G");
    }
}
```

运行结果为：
创建商品,key为:iphone7
价格为5199元
使用缓存,key为:iphone7
价格为5199元
使用缓存,key为:iphone7
价格为5999元

从输出看出，只有第一次是创建Goods对象，后面因为key值相同，所以都是使用了对象池中的Goods对象。在这个例子中，name作为内部状态是不变的，并且作为Map的key值是可以共享的。而showGoodsPrice方法中需要传入的version值则是外部状态，他的值是变化的。

### **享元模式的使用场景**

- 系统中存在大量的相似对象。
- 需要缓冲池的场景。
- 细粒度的对象都具备较接近的外部状态，而且内部状态与环境无关，也就是说对象没有特定身份。