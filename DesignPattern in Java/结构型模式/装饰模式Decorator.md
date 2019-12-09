### **1.装饰模式简介**

#### **装饰模式介绍**

装饰模式是结构型设计模式之一，不必改变类文件和使用继承的情况下，动态地扩展一个对象的功能，是继承的替代方案之一。它是通过创建一个包装对象，也就是装饰来包裹真实的对象。

#### **定义**

动态地给一个对象添加一些额外的职责，就增加功能来说，装饰模式比生成子类更为灵活。

#### **装饰模式结构图**

[![img](https://s2.ax1x.com/2019/05/31/Vl2XZj.png)](https://s2.ax1x.com/2019/05/31/Vl2XZj.png)

- Component：抽象组件，给对象动态的添加职责。
- ConcreteComponent：组件具体实现类。
- Decorator：抽象装饰者，继承Component，从外类来拓展Component类的功能，但对于Component来说无需知道Decorator的存在。
- ConcreteDecorator：装饰者具体实现类。

### **2.装饰模式的简单实现**

装饰模式在现实生活中有很多例子，比如给一个人穿上各种衣服，给一幅画涂色上框等等，这次我要举得例子有些不同，举一个武侠修炼武功的例子：杨过本身就会全真剑法，有两位武学前辈洪七公和欧阳锋分别传授杨过打狗棒法和蛤蟆功，这样杨过除了会全真剑法还会打狗棒法和蛤蟆功。

#### **抽象组件（Component）**

作为武侠肯定要会使用武功的，我们先定义一个武侠的抽象类，里面有使用武功的抽象方法：

```java
public abstract class Swordsman {
    /**
     * Swordsman武侠有使用武功的抽象方法
     */
    public abstract void attackMagic();
}
```

#### **组件具体实现类（ConcreteComponent）**

被装饰的具体对象，在这里就是被教授武学的具体的武侠，他就是杨过，杨过作为武侠当然也会武学，虽然不怎么厉害：

```java
public class YangGuo extends Swordsman{
    @Override
    public void attackMagic() {
        //杨过初始的武学是全真剑法
        System.out.println("杨过使用全真剑法");
    }
}
```

#### **抽象装饰者（Decorator）**

抽象装饰者保持了一个对抽象组件的引用，方便调用被装饰对象中的方法。在这个例子中就是武学前辈要持有武侠的引用，方便教授他武学并“融会贯通”：

```java
public abstract class Master extends Swordsman{
    private Swordsman mSwordsman;

    public Master(Swordsman mSwordsman){
        this.mSwordsman=mSwordsman;
    }
    @Override
    public void attackMagic() {
        mSwordsman.attackMagic();
    }
}
```

#### **装饰者具体实现类（ConcreteDecorator）**

这个例子中用两个装饰者具体实现类，分别是洪七公和欧阳锋，他们负责来传授杨过新的武功：

```java
public class HongQiGong extends Master {
    public HongQiGong(Swordsman mSwordsman) {
        super(mSwordsman);
    }
    public void teachAttackMagic(){
        System.out.println("洪七公教授打狗棒法");
        System.out.println("杨过使用打狗棒法");
    }
    @Override
    public void attackMagic() {
        super.attackMagic();
        teachAttackMagic();
    }
}
public class OuYangFeng extends Master {
    public OuYangFeng(Swordsman mSwordsman) {
        super(mSwordsman);
    }
    public void teachAttackMagic(){
        System.out.println("欧阳锋教授蛤蟆功");
        System.out.println("杨过使用蛤蟆功");
    }
    @Override
    public void attackMagic() {
        super.attackMagic();
        teachAttackMagic();
    }
}
```

#### **客户端调用**

经过洪七公和欧阳锋的教导，杨过除了初始武学全真剑法又学会了打狗棒法和蛤蟆功：

```java
public class Client {
    public static void main(String[] args){
        //创建杨过
        YangGuo mYangGuo=new YangGuo();
        //洪七公教授杨过打狗棒法，杨过会了打狗棒法
        HongQiGong mHongQiGong=new HongQiGong(mYangGuo);
        mHongQiGong.attackMagic();

        //欧阳锋教授杨过蛤蟆功，杨过学会了蛤蟆功
        OuYangFeng mOuYangFeng=new OuYangFeng(mYangGuo);
        mOuYangFeng.attackMagic();
    }
}
```

### **3.装饰模式的优缺点和使用场景**

#### **优点**

- 通过组合而非继承的方式，动态的来扩展一个对象的功能，在运行时选择不同的装饰器，从而实现不同的行为。
- 有效避免了使用继承的方式扩展对象功能而带来的灵活性差，子类无限制扩张的问题。
- 具体组件类与具体装饰类可以独立变化，用户可以根据需要增加新的具体组件类和具体装饰类，在使用时再对其进行组合，原有代码无须改变，符合“开闭原则”。

#### **缺点**

- 装饰链不能过长，否则会影响效率。
- 因为所有对象都是继承于Component,所以如果Component内部结构发生改变，则不可避免地影响所有子类(装饰者和被装饰者)，如果基类改变，势必影响对象的内部。
- 比继承更加灵活机动的特性，也同时意味着装饰模式比继承更加易于出错，排错也很困难，对于多次装饰的对象，调试时寻找错误可能需要逐级排查，较为烦琐，所以只在必要的时候使用装饰者模式。

#### **使用场景**

- 在不影响其他对象的情况下，以动态、透明的方式给单个对象添加职责。
- 需要动态地给一个对象增加功能，这些功能可以动态的撤销。
- 当不能采用继承的方式对系统进行扩充或者采用继承不利于系统扩展和维护时。

### **4.装饰模式和代理模式**

代理模式和装饰模式有点像，都是持有了被代理或者被装饰对象的引用。它们两个最大的不同就是装饰模式对引用的对象增加了功能，而代理模式只是对引用对象进行了控制却没有对引用对象本身增加功能。