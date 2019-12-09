### **前言**

虽说状态模式和策略模式的结构几乎是相同的，但是它们所解决的问题是不同的，读完这两篇文章你就会有了答案。

### **1.状态模式定义**

#### **状态模式定义**

**定义：当一个对象的内在状态改变时允许改变其行为，这个对象看起来像是改变了其类。**

#### **状态模式UML图**

[![V1YeVe.png](https://s2.ax1x.com/2019/06/01/V1YeVe.png)](https://s2.ax1x.com/2019/06/01/V1YeVe.png)

在享元模式中有如下角色：

- Context：环境角色，定义客户端需要的接口，并且负责具体状态的切换。
- State：抽象状态角色，可以是抽象类或者接口，负责对象状态定义，并封装了环境角色。
- ConcreteState：具体状态角色，实现抽象角色类，定义了本状态所要做的事情。

### **2.简单实现状态模式**

拿用mp3听歌来说，mp3有四种基本状态，分别是开机、关机、上一首歌和下一首歌。如果我们要写一个对mp3进行控制的类，你可能会这样写，如下所示。

```java
public class Mp3Controller {
    private static final int POWER_ON = 1;
    private static final int POWER_OFF = 2;
    private int state = POWER_OFF;
    public void powerOn() {
        if (state == POWER_OFF) {
            System.out.println("开机");
        }
        state = POWER_ON;
    }
    public void powerOff() {
        if (state == POWER_ON) {
            System.out.println("关机");
        }
        state = POWER_OFF;
    }
    public void preSong() {
        if (state == POWER_ON) {
            System.out.println("上一首歌");
        }
    }
    public void nextSong() {
        if (state == POWER_ON) {
            System.out.println("下一首歌");
        }
    }
}
```

​		在powerOn和powerOff方法中我们会将state置为相应的状态，在preSong和nextSong方法中，首先要判断当前mp3的state，如果是POWER_OFF，则不做任何处理，写到这里你可能会觉得实现很简单啊。那么我再添加些状态，比如待机状态、休眠状态、亮屏状态等等，顺便再添加些功能，比如调大音量、调小音量、降噪等。这样你实现起来，就会发现你会定义很多种状态，在功能中可能要用到多个条件语句进行判断，这会使得代码变得臃肿。
状态模式就是为了解决这一问题，将多个条件语句去掉，使得代码更加清晰，下面来进行实现。

#### **抽象状态角色**

```java
public interface Mp3State {
    //开机
    public void powerOn();
    //关机
    public void powerOff();
    //上一首歌曲
    public void preSong();
    //下一首歌曲
    public void nextSong();
}
```

接口Mp3State中定义了四种功能，接下来我们来实现Mp3State。

#### **具体状态角色**

我们先来实现开机状态，代码如下所示。

```java
public class PowerOnState implements Mp3State {
    @Override
    public void powerOn() {
        System.out.println("已开机");
    }
    @Override
    public void powerOff() {
        System.out.println("关机");
    }
    @Override
    public void preSong() {
        System.out.println("上一首歌");
    }
    @Override
    public void nextSong() {
        System.out.println("下一首歌");
    }
}
```

比较特殊的是powerOn方法中，打印了“已开机”，因为在PowerOnState 状态下进行开机操作是多此一举的。
接着实现关机状态：

```java
public class PowerOffState implements Mp3State {
    @Override
    public void powerOn() {
        System.out.println("开机");
    }
    @Override
    public void powerOff() {
    }
    @Override
    public void preSong() {
    }
    @Override
    public void nextSong() {
    }
}
```

在关机状态中只实现了powerOn方法，其他的方法都是空实现。

#### **环境角色**

```java
public class Context {
    private Mp3State mp3State;
    public void setMp3State(Mp3State mp3State){
        this.mp3State=mp3State;
    }
    public void powerOn(){
        mp3State.powerOn();
        setMp3State(new PowerOnState());
    }
    public void powerOff(){
        mp3State.powerOff();
        setMp3State(new PowerOffState());
    }
    public void preSong(){
        mp3State.preSong();
    }
    public void nextSong(){
        mp3State.nextSong();
    }
}
```

Context 中定义了setMp3State方法，用来设定状态，其中powerOn方法中会调用setMp3State方法将状态置为PowerOffState，同理powerOff中将状态置为PowerOffState。

#### **客户端调用**

```java
public class Client {
    public static void main(String[] args){
        Context context=new Context();
        context.setMp3State(new PowerOffState());
        context.preSong();
        context.powerOn();
        context.nextSong();
        context.powerOff();
    }
}
```

我们只需要先设定mp3的初始状态，就可以调用各种功能方法了，不需要再考虑功能和状态之间的关系。输出结果为：
开机
下一首歌
关机

虽然这个例子的代码很简单，这里还是给出UML图，如下所示。
[![V1YVbD.png](https://s2.ax1x.com/2019/06/01/V1YVbD.png)](https://s2.ax1x.com/2019/06/01/V1YVbD.png)

### **3.状态模式的使用场景和优缺点**

#### **优点**

- 避免了过多的条件语句，使得结构更清晰，提高代码的可维护性。
- 每个状态都是一个子类，方便增加和修改状态。
- 状态被放置到类的内部，外部调用不需要知道类的内部如何实现状态和行为的变换。

#### **缺点**

- 完全使用状态模式，可能会导致子类会过多。

#### **使用场景**

- 代码中包含大量与对象状态有关的条件语句。
- 对象的行为依赖着状态，并且行为随着状态的改变而改变。