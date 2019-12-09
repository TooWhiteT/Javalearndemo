### **1.建造者模式简介**

#### **定义**

建造者模式（builder），将一个复杂对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示。

#### **简介**

建造者模式（builder）是创建一个复杂对象的创建型模式，将构建复杂对象的过程和它的部件解耦，使得构建过程和部件的表示分离开来。
例如我们要DIY一个台式机电脑，我们找到DIY商家，我们可以要求这台电脑的cpu或者主板或者其他的部件都是什么牌子的什么配置的，这些部件是我们可以根据我们的需求来变化的，但是这些部件组装成电脑的过程是一样的，我们不需要知道这些部件是怎样组装成电脑的，我们只需要提供部件的牌子和配置就可以了。对于这种情况我们就可以采用建造者模式，将部件和组装过程分离，使得构建过程和部件都可以自由拓展，两者之间的耦合也降到最低。

#### **建造者模式结构图**

![img](https://upload-images.jianshu.io/upload_images/3897939-3d84c8a531e21505.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/923/format/webp)

- Dirextor: 指挥者类，用于统一组装流程
- Builder：抽象Builder类，规范产品的组建，一般是由子类实现。
- ConcreteBulider: 抽象Builder类的实现类，实现抽象Builder类定义的所有方法，并且返回一个组建好的对象
- Product: 产品类

### **2.建造者模式简单实现**

这里我们就用DIY组装电脑的例子来实现一下建造者模式。

#### **创建产品类**

我要组装一台电脑，电脑被抽象为Computer类，它有三个部件:CPU 、主板和内存。并在里面提供了三个方法分别用来设置CPU 、主板和内存：

```java
public class Computer {
    private String mCpu;
    private String mMainboard;
    private String mRam;

    public void setmCpu(String mCpu) {
        this.mCpu = mCpu;
    }

    public void setmMainboard(String mMainboard) {
        this.mMainboard = mMainboard;
    }

    public void setmRam(String mRam) {
        this.mRam = mRam;
    }
}
```

#### **创建Builder类规范产品的组建**

商家组装电脑有一套组装方法的模版，就是一个抽象的Builder类,里面提供了安装CPU、主板和内存的方法，以及组装成电脑的create方法：

```java
public abstract class Builder {
    public abstract void buildCpu(String cpu);
    public abstract void buildMainboard(String mainboard);
    public abstract void buildRam(String ram);
    public abstract Computer create();
}
```

商家实现了抽象的Builder类，MoonComputerBuilder类用于组装电脑：

```java
public class MoonComputerBuilder extends Builder {
    private Computer mComputer = new Computer();
    @Override
    public void buildCpu(String cpu) {
        mComputer.setmCpu(cpu);
    }

    @Override
    public void buildMainboard(String mainboard) {
        mComputer.setmMainboard(mainboard);
    }

    @Override
    public void buildRam(String ram) {
        mComputer.setmRam(ram);
    }

    @Override
    public Computer create() {
        return mComputer;
    }
}
```

#### **用Dirextor指挥者类来统一组装过程**

商家的指挥者类用来规范组装电脑的流程规范，先安装主板，再安装CPU，最后安装内存并组装成电脑：

```java
public class Direcror {
    Builder mBuild=null;
    public Direcror(Builder build){
        this.mBuild=build;
    }
    public Computer CreateComputer(String cpu,String mainboard,String ram){
        //规范建造流程
       this.mBuild.buildMainboard(mainboard);
       this.mBuild.buildCpu(cpu);
       this.mBuild.buildRam(ram);
       return mBuild.create();
    }
}
```



#### **客户端调用指挥者类**

最后商家用指挥者类组装电脑。我们只需要提供我们想要的CPU，主板和内存就可以了，至于商家怎样组装的电脑我们无需知道。

```java
public class CreatComputer {
    public static void main(String[]args){
        Builder mBuilder=new MoonComputerBuilder();
        Direcror mDirecror=new Direcror(mBuilder);
        //组装电脑
        mDirecror.CreateComputer("i7-6700","华擎玩家至尊","三星DDR4");
    }
}
```



### **3.使用建造者模式的场景和优缺点**

#### **使用场景**

- 当创建复杂对象的算法应该独立于该对象的组成部分以及它们的装配方式时。
- 相同的方法，不同的执行顺序，产生不同的事件结果时。
- 多个部件或零件,都可以装配到一个对象中，但是产生的运行结果又不相同时。
- 产品类非常复杂，或者产品类中的调用顺序不同产生了不同的效能。
- 创建一些复杂的对象时，这些对象的内部组成构件间的建造顺序是稳定的，但是对象的内部组成构件面临着复杂的变化。

#### **优缺点**

##### 优点：

- 使用建造者模式可以使客户端不必知道产品内部组成的细节。
- 具体的建造者类之间是相互独立的，容易扩展。
- 由于具体的建造者是独立的，因此可以对建造过程逐步细化，而不对其他的模块产生任何影响。

##### 缺点：

- 产生多余的Build对象以及Dirextor类。