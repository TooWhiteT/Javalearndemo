### **1.外观模式简介**

#### **外观模式介绍**

当我们开发Android的时候，无论是做SDK还是封装API，我们大多都会用到外观模式，它通过一个外观类使得整个系统的结构只有一个统一的高层接口，这样能降低用户的使用成本。

#### **外观模式定义**

为系统中的一组接口提供一个一致的界面，此模式定义了一个高层接口，这个接口使得子系统更加容易使用。

#### **外观模式结构图**

[![img](https://s2.ax1x.com/2019/05/31/Vlh7kj.png)](https://s2.ax1x.com/2019/05/31/Vlh7kj.png)

- Facade：外观类，知道哪些子系统类负责处理请求，将客户端的请求代理给适当的子系统对象。
- Subsystem：子系统类，实现子系统的功能，处理外观类指派的任务，注意子系统类不含有外观类的引用。

### **2.外观模式的简单实现**

在上一篇[设计模式之装饰模式](http://blog.csdn.net/itachi85/article/details/51282647)我们举了武侠的例子，这一篇我们还举武侠的例子，首先我们把武侠张无忌当作一个系统，他作为一个武侠，他本身分为三个系统分别是招式、内功和经脉。

#### **子系统类（Subsystem）**

我们知道张无忌的三个系统分别是招式、内功和经脉。那我们来创建它们：

```java
/**
 * 子系统招式
 */
public class ZhaoShi {
    public void TaiJiQuan(){
        System.out.print("使用着招式太极拳");
    }
    public void QiShangQuan(){
        System.out.print("使用招式七伤拳");
    }
    public void ShengHuo(){
        System.out.print("使用招式圣火令");
    }
}
```



```java
/**
 * 子系统内功
 */
public class NeiGong {
    public void JiuYang(){
        System.out.print("使用九阳神功");
    }
    public void QianKun(){
        System.out.print("使用乾坤大挪移");
    }
}
/**
 * 子系统经脉
 */
public class JingMai {
    public void jingmai(){
        System.out.print("开启经脉");
    }
}
```

张无忌有很多的武学和内功，怎么将他们搭配，并对外界隐藏呢，我们接下来看看外观类：

#### **外观类（Facade）**

这里的外观类就是张无忌，他负责将自己的招式、内功和经脉通过不同的情况合理的运用：

```java
/**
 * 外观类张无忌
 */
public class ZhangWuJi {
    private JingMai jingMai;
    private ZhaoShi zhaoShi;
    private NeiGong neiGong;

    public ZhangWuJi(){
        jingMai=new JingMai();
        zhaoShi=new ZhaoShi();
        neiGong=new NeiGong();
    }
    /**
     * 使用乾坤大挪移
     */
    public void Qiankun(){
        jingMai.jingmai();//开启经脉
        neiGong.QianKun();//使用内功乾坤大挪移

    }
    /**
     * 使用七伤拳
     */
    public void QiShang(){
        jingMai.jingmai(); //开启经脉
        neiGong.JiuYang();//使用内功九阳神功
        zhaoShi.QiShangQuan();//使用招式七伤拳
    }
}
```

初始化外观类的同时将各个子系统类创建好。很明显张无忌很好的将自身的各个系统搭配好，如果使用七伤拳的话就需要开启经脉、使用内功九阳神功接下来使用招式七伤拳，如果不开经脉或者使用九阳神功的话那么七伤拳的威力会大打折扣。

#### **客户端调用**

```java
public class Test {
    public static void main(String[] args){
        ZhangWuJi zhangWuJi=new ZhangWuJi();
        //张无忌使用乾坤大挪移
        zhangWuJi.Qiankun();
        //张无忌使用七伤拳
        zhangWuJi.QiShang();
    }
}
```

当张无忌使用乾坤大挪移或者七伤拳的时候，比武的对手显然不知道张无忌本身运用了什么，同时张无忌也不需要去重新计划使用七伤拳的时候需要怎么做，他已经早就计划好了。如果每次使用七伤拳或者乾坤大挪移时都要计划怎么做很显然会增加成本并贻误战机。另外张无忌也可以改变自己的内功、招式和经脉，这些都是对比武的对手有所隐藏的。
外观模式本身就是将子系统的逻辑和交互隐藏起来，为用户提供一个高层次的接口，使得系统更加易用，同时也隐藏了具体的实现，这样即使具体的子系统发生了变化，用户也不会感知到。

### **3.外观模式使用场景**

- 构建一个有层次结构的子系统时，使用外观模式定义子系统中每层的入口点，如果子系统之间是相互依赖的，则可以让他们通过外观接口进行通信，减少子系统之间的依赖关系。
- 子系统往往会因为不断的重构演化而变得越来越复杂，大多数的模式使用时也会产生很多很小的类，这给外部调用他们的用户程序带来了使用的困难，我们可以使用外观类提供一个简单的接口，对外隐藏子系统的具体实现并隔离变化。
- 当维护一个遗留的大型系统时，可能这个系统已经非常难以维护和拓展，但因为它含有重要的功能，新的需求必须依赖于它，则可以使用外观类，来为设计粗糙或者复杂的遗留代码提供一个简单的接口，让新系统和外观类交互，而外观类负责与遗留的代码进行交互。