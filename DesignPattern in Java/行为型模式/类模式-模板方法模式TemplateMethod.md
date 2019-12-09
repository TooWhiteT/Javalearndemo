### **1.模版方法模式简介**

#### **模版方法模式介绍**

在软件开发中，有时会遇到类似的情况，某个方法的实现需要多个步骤，其中有些步骤是固定的，而有些步骤并不固定，存在可变性。为了提高代码的复用性和系统的灵活性，可以使用模板方法模式来应对这类情况。

#### **模版方法模式定义**

定义一个操作中的算法框架，而将一些步骤延迟到子类中，使得子类可以不改变一个算法的结构即可重定义算法的某些特定步骤。

#### **模版方法模式结构图**

[![img](https://s2.ax1x.com/2019/05/31/VlhXcV.png)](https://s2.ax1x.com/2019/05/31/VlhXcV.png)

- AbstractClass：抽象类，定义了一套算法。
- ConcreteClass：具体实现类。

### **2.模版方法模式的简单实现**

#### **创建抽象类，定义算法框架**

一个武侠要战斗的时候，也有一套固定的通用模式，那就是运行内功、开通经脉、准备武器和使用招式，我们把这些用代码表示就是：

```java
public abstract class AbstractSwordsman {
  //该方法为final，防止算法框架被覆写
  public final void fighting(){
      //运行内功，抽象方法
      neigong();
      //调整经脉,具体方法
      meridian();
      //如果有武器则准备武器
      if(hasWeapons()) {
          weapons();
      }
      //使用招式
      moves();
      //钩子方法
      hook();
  }
    //空实现方法
    protected void hook(){}
    protected abstract void neigong();
    protected abstract void weapons();
    protected abstract void moves();
    protected void meridian(){
        System.out.println("开通正经与奇经");
    }

    /**
     * 是否有武器，默认是有武器的，钩子方法
     * @return
     */
    protected boolean hasWeapons(){
         return true;
    }
}
```

需要注意的是这个抽象类包含了三种类型的方法，分别是抽象方法、具体方法和钩子方法。抽象方法是交由子类去实现，具体方法则在父类实现了子类公共的方法实现，在上面的例子就是武侠开通经脉的方式都一样，所以就在具体方法中实现。钩子方法则分为两类，第一类是15行，它有一个空实现的方法，子类可以视情况来决定是否要覆盖它；第二类则是第9行，这类钩子方法的返回类型通常是bool类型的，一般用于对某个条件进行判断，如果条件满足则执行某一步骤，否则将不执行。

#### **定义具体实现类**

本文就拿张无忌、张三丰来作为例子：

```java
public class ZhangWuJi extends AbstractSwordsman {

    @Override
    protected void neigong() {
        System.out.println("运行九阳神功");
    }

    @Override
    protected void weapons() {
    }

    @Override
    protected void moves() {
        System.out.println("使用招式乾坤大挪移");
    }

    @Override
    protected boolean hasWeapons() {
        return false;
    }
}
```

张无忌没有武器所以hasWeapons方法返回false，这样也不会走weapons方法了。

```java
public class ZhangSanFeng extends AbstractSwordsman {

    @Override
    protected void neigong() {
        System.out.println("运行纯阳无极功");
    }

    @Override
    protected void weapons() {
        System.out.println("使用真武剑");
    }

    @Override
    protected void moves() {
        System.out.println("使用招式神门十三剑");
    }

    @Override
    protected void hook() {
        System.out.println("突然肚子不舒服，老夫先去趟厕所");
    }
}
```

最后张三丰突然肚子不舒服所以就实现了钩子方法hook。

#### **客户端调用**

```java
public class Client {
    public static void main(String[] args) {
        ZhangWuJi zhangWuJi=new ZhangWuJi();
        zhangWuJi.fighting();
        ZhangSanFeng zhangSanFeng=new ZhangSanFeng();
        zhangSanFeng.fighting();
    }
}
```

运行结果：
运行九阳神功
开通正经与奇经
使用招式乾坤大挪移
运行纯阳无极功
开通正经与奇经
使用真武剑
使用招式神门十三剑
突然肚子不舒服，老夫先去趟厕所

### **4.模版方法模式的优缺点和使用场景**

#### **优点**

- 模板方法模式通过把不变的行为搬移到超类，去除了子类中的重复代码。
- 子类实现算法的某些细节，有助于算法的扩展。

#### **缺点**

- 每个不同的实现都需要定义一个子类，这会导致类的个数的增加，设计更加抽象。

#### **使用场景**

- 各子类中公共的行为应被提取出来并集中到一个公共父类中以避免代码重复。
- 面对重要复杂的算法，可以把核心算法设计为模版方法，周边相关细节功能则有各个子类实现。
- 需要通过子类来决定父类算法中某个步骤是否执行，实现子类对父类的反向控制。