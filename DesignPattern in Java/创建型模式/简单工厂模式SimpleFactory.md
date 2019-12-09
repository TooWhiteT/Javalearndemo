### **1.简单工厂模式简介**

#### **定义**

简单工厂模式属于创建型模式又叫做静态工厂方法模式，是由一个工厂对象决定创建出哪一种产品类的实例。

#### **简单工厂模式结构图**

[![img](https://s2.ax1x.com/2019/05/31/Vlcbff.png)](https://s2.ax1x.com/2019/05/31/Vlcbff.png)

- Factory：工厂类，简单工厂模式的核心，它负责实现创建所有实例的内部逻辑。工厂类的创建产品类的方法可以被外界直接调用，创建所需的产品对象。
- IProduct：抽象产品类，简单工厂模式所创建的所有对象的父类，它负责描述所有实例所共有的公共接口。
- Product：具体产品类，是简单工厂模式的创建目标。

### **2.简单工厂模式简单实现**

这里我们用生产电脑来举例，假设有一个电脑的代工生产商，它目前已经可以代工生产联想电脑了，随着业务的拓展，这个代工生产商还要生产惠普和华硕的电脑，这样我们就需要用一个单独的类来专门生产电脑，这就用到了简单工厂模式。下面我们来实现简单工厂模式：

#### **创建抽象产品类**

我们创建一个电脑的抽象产品类，他有一个抽象方法用于启动电脑：

```java
public abstract class Computer {
    /**
     * 产品的抽象方法，由具体的产品类去实现
     */
    public abstract void start();
}
```

#### **创建具体产品类**

接着我们创建各个品牌的电脑，他们都继承了他们的父类Computer ，并实现了父类的start方法：

**联想电脑：**

```java
public class LenovoComputer extends Computer{
    @Override
    public void start() {
        System.out.println("联想电脑启动");
    }
```

**惠普电脑：**

```
public class HpComputer extends Computer{
    @Override
    public void start() {
        System.out.println("惠普电脑启动");
    }
}
```

**华硕电脑：**

```java
public class AsusComputer extends Computer {
    @Override
    public void start() {
        System.out.println("华硕电脑启动");
    }
}
```

#### **创建工厂类**

接下来创建一个工厂类，它提供了一个静态方法createComputer用来生产电脑。你只需要传入你想生产的电脑的品牌，它就会实例化相应品牌的电脑对象：

```java
public class ComputerFactory {
    public static Computer createComputer(String type){
        Computer mComputer=null;
        switch (type) {
            case "lenovo":
                mComputer=new LenovoComputer();
               break;
            case "hp":
                mComputer=new HpComputer();
                break;
            case "asus":
                mComputer=new AsusComputer();
                break;

        }
        return mComputer;
    }
}
```

#### **客户端调用工厂类**

客户端调用工厂类，传入“hp”生产出惠普电脑并调用该电脑对象的start方法：

```java
public class CreatComputer {
    public static void main(String[]args){
      ComputerFactory.createComputer("hp").start();
    }
}
```

### **3.使用简单工厂模式的场景和优缺点**

#### **使用场景**

- 工厂类负责创建的对象比较少。
- 客户只知道传入工厂类的参数，对于如何创建对象（逻辑）不关心。

#### **简单工厂模式优缺点**

##### **优点：**

使用户根据参数获得对应的类实例，避免了直接实例化类，降低了耦合性。

##### **缺点：**

可实例化的类型在编译期间已经被确定，如果增加新类型，则需要修改工厂，违背了开放封闭原则(ASD) 。 简单工厂需要知道所有要生成的类型，当子类过多或者子类层次过多时不适合使用。