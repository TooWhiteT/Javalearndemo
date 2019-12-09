### **1.代理模式简介**

#### **代理模式介绍**

**代理模式也叫委托模式**，是结构型设计模式的一种。在现实生活中我们用到类似代理模式的场景有很多，比如代购、代理上网、打官司等。

#### **定义**

为其他对象提供一种代理以控制这个对象的访问。

#### **代理模式结构图**

[![img](https://s2.ax1x.com/2019/05/31/VlgRNq.png)](https://s2.ax1x.com/2019/05/31/VlgRNq.png)

- Subject：抽象主题类，声明真实主题与代理的共同接口方法。
- RealSubject：真实主题类，定义了代理所表示的真实对象，客户端通过代理类间接的调用真实主题类的方法。
- ProxySubject：代理类，持有对真实主题类的引用，在其所实现的接口方法中调用真实主题类中相应的接口方法执行。
- Client：客户端类。

### **2.代理模式的简单实现**

假设我要买一个BV的包（博主我很喜欢一直买不起），国内的太贵了，我找了个代购来帮我海外购买，这样能省好多钱，哈哈。

#### **抽象主题类（Subject）**

抽象主题类具有真实主题类和代理的共同接口方法，我想要代购，那共同的方法就是购买：

```java
public interface IShop {
    //购买
    void buy();
}
```

#### **真实主题类（RealSubject）**

这个购买者LiuWangShu也就是我，实现了IShop接口提供的 buy()方法：

```java
public class LiuWangShu implements IShop {
    @Override
    public void buy() {
        System.out.println("购买");
    }
}
```

#### **代理类（ProxySubject）**

我找的代理类同样也要实现IShop接口，并且要持有被代理者，在buy()方法中调用了被代理者的buy()方法：

```java
public class Purchasing implements IShop {
    private IShop mShop;
    public Purchasing(IShop shop){
        mShop=shop;
    }

    @Override
    public void buy() {
        mShop.buy();
    }
}
```

#### **客户端类（Client）**

```java
public class Client {
    public static void main(String[] args){
        //创建LiuWangShu
        IShop liuwangshu=new LiuWangShu();
        //创建代购者并将LiuWangShu作为构造函数传
        IShop purchasing=new Purchasing(liuwangshu);
        purchasing.buy();
    }
}
```

看完客户端类的代码，其实也是很好理解，就是代理类包含了真实主题类（被代理者），最终调用的都是真实主题类（被代理者）实现的方法，在上面的例子就是LiuWangShu类的buy()方法，所以运行的结果就是“购买”。

### **3.动态代理的简单实现**

从编码的角度来说，代理模式分为静态代理和动态代理，上面的例子是静态代理，在代码运行前就已经存在了代理类的class编译文件，而动态代理则是在代码运行时通过反射来动态的生成代理类的对象，并确定到底来代理谁。也就是我们在编码阶段不需要知道代理谁，代理谁我们将会在代码运行时决定。Java提供了动态的代理接口InvocationHandler，实现该接口需要重写invoke()方法。下面我们在上面静态代理的例子上做修改：

创建动态代理类：

```java
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
public class DynamicPurchasing implements InvocationHandler{
    private Object obj;
    public DynamicPurchasing(Object obj){
        this.obj=obj;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result=method.invoke(obj, args);
        return result;
    }
}
```

在动态代理类中我们声明一个Object的引用，该引用指向被代理类，我们调用被代理类的具体方法在invoke()方法中执行。接下来我们修改客户端类代码：

```java
import java.lang.reflect.Proxy;
public class Client {
    public static void main(String[] args){
        //创建LiuWangShu
        IShop liuwangshu=new LiuWangShu();
        //创建动态代理
        DynamicPurchasing  mDynamicPurchasing=new DynamicPurchasing(liuwangshu);
        //创建LiuWangShu的ClassLoader
        ClassLoader loader=liuwangshu.getClass().getClassLoader();
        //动态创建代理类
        IShop purchasing= (IShop) Proxy.newProxyInstance(loader,new Class[]{IShop.class},mDynamicPurchasing);
        purchasing.buy();
    }
}
```

### **4.代理模式的应用**

#### **代理模式类型**

代理模式的类型主要有以下几点：

1. 远程代理：为一个对象在不同的地址空间提供局部代表，这样系统可以将Server部分的事项隐藏。
2. 虚拟代理：使用一个代理对象表示一个十分耗资源的对象并在真正需要时才创建。
3. 安全代理：用来控制真实对象访问时的权限。
4. 智能指引：当调用真实的对象时，代理处理另外一些事，比如计算真实对象的引用计数，当该对象没有引用时，可以自动释放它；或者访问一个实际对象时，检查是否已经能够锁定它，以确保其他对象不能改变它。

#### **代理模式使用场景**

无法或者不想直接访问某个对象时可以通过一个代理对象来间接的访问。