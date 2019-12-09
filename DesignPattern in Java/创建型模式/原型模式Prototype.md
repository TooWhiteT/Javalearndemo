### **1.原型模式定义**

#### **原型模式定义**

定义：用原型实例指定创建对象的种类，并通过拷贝这些原型创建新的对象。

#### **原型模式UML图**

[![V1YQ2t.png](https://s2.ax1x.com/2019/06/01/V1YQ2t.png)](https://s2.ax1x.com/2019/06/01/V1YQ2t.png)

在原型模式中有如下角色：

- Client：客户端角色。

- Prototype：抽象原型角色，抽象类或者接口，用来声明clone方法。

- ConcretePrototype：具体的原型类，是客户端角色使用的对象，即被复制的对象。

  ​	需要注意的是，Prototype通常是不用自己定义的，因为拷贝这个操作十分常用，Java中就提供了Cloneable接口来支持拷贝操作，它就是原型模式中的Prototype。当然，原型模式也未必非得去实现Cloneable接口，也有其他的实现方式。

### **2.原型模式简单实现**

​		原型模式的核心是clone方法，通过该方法进行拷贝，这里举一个名片拷贝的例子。
现在已经流行电子名片了，只要扫一下就可以将名片拷贝到自己的名片库中， 我们先实现名片类。

#### **具体的原型类**

```java
public class BusinessCard implements Cloneable {
    private String name;
    private String company;
    public BusinessCard(){
        System.out.println("执行构造函数BusinessCard");
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    @Override
    public BusinessCard clone() {
        BusinessCard businessCard = null;
        try {
            businessCard = (BusinessCard) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return businessCard;
    }
    public void show() {
        System.out.println("name:" + name);
        System.out.println("company:" + company);
    }
}
```

​		BusinessCard类实现了Cloneable接口，它是一个标识接口，表示这个对象是可拷贝的，只要重写clone方法就可以实现拷贝。如果实现了Cloneable接口却没有重写clone方法就会报错。需要注意的是，clone方法不是在Cloneable接口中定义的（Cloneable接口中没有定义任何方法），而是在Object中定义的。

#### **客户端调用**

```java
public class Client {
    public static void main(String[] args) {
        BusinessCard businessCard = new BusinessCard();
        businessCard.setName("钱三");
        businessCard.setCompany("阿里");
        //拷贝名片
        BusinessCard cloneCard1 = businessCard.clone();
        cloneCard1.setName("赵四");
        cloneCard1.setCompany("百度");

        BusinessCard cloneCard2 = businessCard.clone();
        cloneCard2.setName("孙五");
        cloneCard2.setCompany("腾讯");

        businessCard.show();
        cloneCard1.show();
        cloneCard2.show();
    }
}
```

​		除了第一个名片，其他两个名片都是通过clone方法得到的，需要注意的是，clone方法并不会执行cloneCard1和cloneCard2的构造函数，运行结果为：
执行构造函数BusinessCard
name:钱三
company:阿里
name:赵四
company:百度
name:孙五
company:腾讯

### **3.浅拷贝和深拷贝**

​	原型模式涉及到浅拷贝和深拷贝的知识点，为了更好的理解它们，还需要举一些例子。

#### **实现浅拷贝**

​	上述的例子中，BusinessCard的字段都是String类型的，如果字段是引用的类型的，会出现什么情况呢？如下所示。

```java
public class DeepBusinessCard implements Cloneable {
    private String name;
    private Company company = new Company();

    public void setName(String name) {
        this.name = name;
    }

    public void setCompany(String name, String address) {
        this.company.setName(name);
        this.company.setAddress(address);
    }

    @Override
    public DeepBusinessCard clone() {
        DeepBusinessCard businessCard = null;
        try {
            businessCard = (DeepBusinessCard) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return businessCard;
    }

    public void show() {
        System.out.println("name:" + name);
        System.out.println("company:" + company.getName() + "-address-" + company.getAddress());
    }

}
```

我们定义了DeepBusinessCard 类，它的字段company是引用类型的，Company类如下所示。

```java
public class Company {
    private String name;
    private String address;

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
```

在客户端使用DeepBusinessCard：

```java
public class Client {
    public static void main(String[] args) {
       DeepBusinessCard businessCard=new DeepBusinessCard();
        businessCard.setName("钱三");
        businessCard.setCompany("阿里","北京望京");

        DeepBusinessCard cloneCard1=businessCard.clone();
        cloneCard1.setName("赵四");
        cloneCard1.setCompany("百度","北京西二旗");

        DeepBusinessCard cloneCard2=businessCard.clone();
        cloneCard2.setName("孙五");
        cloneCard2.setCompany("腾讯","北京中关村");

        businessCard.show();
        cloneCard1.show();
        cloneCard2.show();
    }
}
```

运行结果为：
name:钱三
company:腾讯-address-北京中关村
name:赵四
company:腾讯-address-北京中关村
name:孙五
company:腾讯-address-北京中关村

​	从结果可以看出company字段为最后设置的”腾讯”、”北京中关村”。这是因为Object类提供的clone方法，不会拷贝对象中的内部数组和引用对象，导致它们仍旧指向原来对象的内部元素地址，这种拷贝叫做浅拷贝。
​	company字段是引用类型，businessCard被拷贝后，company字段仍旧指向原来的businessCard对象的company字段的地址。这样我们每次设置company字段，都会覆盖上一次设置的值，最终留下的就是最后一次设置的值：”腾讯”、”北京中关村”。
引用关系如下图所示。
[![V1YM8I.png](https://s2.ax1x.com/2019/06/01/V1YM8I.png)](https://s2.ax1x.com/2019/06/01/V1YM8I.png)
这样的引用关系显然不符合需求，有多个对象可以修改company，我们应该将引用关系改为如下形式：

[![V1YKPA.png](https://s2.ax1x.com/2019/06/01/V1YKPA.png)](https://s2.ax1x.com/2019/06/01/V1YKPA.png)
	拷贝businessCard对象的同时，也将它内部的引用对象company进行拷贝，使得每个拷贝的对象之间无任何关联，都指向了自身对应的company，这种拷贝就是深拷贝。

#### **实现深拷贝**

首先需要修改Company类，如下所示。

```java
public class Company implements Cloneable{
    private String name;
    private String address;
    ...
    public Company clone(){
        Company company=null;
        try {
            company= (Company) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return company;
    }
}
```

​	为了实现Company类能被拷贝，Company类也需要实现Cloneable接口并且覆写clone方法。接着修改DeepBusinessCard的clone方法：

```java
public class DeepBusinessCard implements Cloneable {
    private String name;
    private Company company = new Company();
    ...
    @Override
    public DeepBusinessCard clone() {
        DeepBusinessCard businessCard = null;
        try {
            businessCard = (DeepBusinessCard) super.clone();
            businessCard.company = this.company.clone();//1

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return businessCard;
    }
  ...
}
```

在注释1处增加了对company字段的拷贝处理。最后在客户端调用，输出的结果为：
name:钱三
company:阿里-address-北京望京
name:赵四
company:百度-address-北京西二旗
name:孙五
company:腾讯-address-北京中关村

### **4.原型模式的使用场景**

- 如果类的初始化需要耗费较多的资源，那么可以通过原型拷贝避免这些消耗。
- 通过new产生一个对象需要非常繁琐的数据准备或访问权限，则可以使用原型模式。
- 一个对象需要提供给其他对象访问，而且各个调用者可能都需要修改其值时，可以拷贝多个对象供调用者使用，即保护性拷贝。

### **5.原型模式的优缺点**

#### **优点**

原型模式是在内存中二进制流的拷贝，要比new一个对象的性能要好，特别是需要产生大量对象时。

#### **缺点**

直接在内存中拷贝，构造函数是不会执行的，这样就减少了约束，这既是优点也是缺点，需要在实际应用中去考量。