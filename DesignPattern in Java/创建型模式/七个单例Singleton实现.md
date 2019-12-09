# 单例模式

**定义：保证一个类仅有一个实例，并提供一个访问它的全局访问点。**

**单例模式结构图：**
[![img](https://s2.ax1x.com/2019/05/31/VlyMh8.png)](https://s2.ax1x.com/2019/05/31/VlyMh8.png)

单例模式有多种写法各有利弊，现在我们来看看各种模式写法。

### **1. 饿汉模式**

```java
public class Singleton {  
     private static Singleton instance = new Singleton();  
     private Singleton (){
     }
     public static Singleton getInstance() {  
     return instance;  
     }  
 }
```

这种方式在类加载时就完成了初始化，所以类加载较慢，但获取对象的速度快。 这种方式基于类加载机制避免了多线程的同步问题，但是也不能确定有其他的方式（或者其他的静态方法）导致类装载，这时候初始化instance显然没有达到懒加载的效果。

### **2. 懒汉模式（线程不安全）**

```java
public class Singleton {  
      private static Singleton instance;  
      private Singleton (){
      }   
      public static Singleton getInstance() {  
      if (instance == null) {  
          instance = new Singleton();  
      }  
      return instance;  
      }  
 }
```

懒汉模式申明了一个静态对象，在用户第一次调用时初始化，虽然节约了资源，但第一次加载时需要实例化，反映稍慢一些，而且在多线程不能正常工作。

### **3. 懒汉模式（线程安全）**

```java
public class Singleton {  
      private static Singleton instance;  
      private Singleton (){
      }
      public static synchronized Singleton getInstance() {  
      if (instance == null) {  
          instance = new Singleton();  
      }  
      return instance;  
      }  
 }
```

这种写法能够在多线程中很好的工作，但是每次调用getInstance方法时都需要进行同步，造成不必要的同步开销，而且大部分时候我们是用不到同步的，所以不建议用这种模式。

### **4. 双重检查模式 （DCL）**

```java
public class Singleton {  
      private volatile static Singleton singleton;  
      private Singleton (){
      }   
      public static Singleton getInstance() {  
      if (instance== null) {  
          synchronized (Singleton.class) {  
          if (instance== null) {  
              instance= new Singleton();  
          }  
         }  
     }  
     return singleton;  
     }  
 }
```

这种写法在getSingleton方法中对singleton进行了两次判空，第一次是为了不必要的同步，第二次是在singleton等于null的情况下才创建实例。在这里用到了volatile关键字，不了解volatile关键字的可以查看[Java多线程（三）volatile域](http://blog.csdn.net/itachi85/article/details/50274169)这篇文章，在这篇文章我也提到了双重检查模式是正确使用volatile关键字的场景之一。
在这里使用volatile会或多或少的影响性能，但考虑到程序的正确性，牺牲这点性能还是值得的。 DCL优点是资源利用率高，第一次执行getInstance时单例对象才被实例化，效率高。缺点是第一次加载时反应稍慢一些，在高并发环境下也有一定的缺陷，虽然发生的概率很小。DCL虽然在一定程度解决了资源的消耗和多余的同步，线程安全等问题，但是他还是在某些情况会出现失效的问题，也就是DCL失效，在《java并发编程实践》一书建议用**静态内部类单例模式**来替代DCL。

### **5. 静态内部类单例模式**

```java
public class Singleton { 
    private Singleton(){
    }
      public static Singleton getInstance(){  
        return SingletonHolder.sInstance;  
    }  
    private static class SingletonHolder {  
        private static final Singleton sInstance = new Singleton();  
    }  
}
```

​		第一次加载Singleton类时并不会初始化sInstance，只有第一次调用getInstance方法时虚拟机加载SingletonHolder 并初始化sInstance ，这样不仅能确保线程安全也能保证Singleton类的唯一性，所以推荐使用静态内部类单例模式。

### **6. 枚举单例**

```java
public enum Singleton {  
     INSTANCE;  
     public void doSomeThing() {  
     }  
 }
//或者
public class SingletonObject7 {


    private SingletonObject7(){

    }

    /**
     * 枚举类型是线程安全的，并且只会装载一次
     */
    private enum Singleton{
        INSTANCE;

        private final SingletonObject7 instance;

        Singleton(){
            instance = new SingletonObject7();
        }

        private SingletonObject7 getInstance(){
            return instance;
        }
    }

    public static SingletonObject7 getInstance(){

        return Singleton.INSTANCE.getInstance();
    }
}
```

默认枚举实例的创建是线程安全的，并且在任何情况下都是单例，上述讲的几种单例模式实现中，有一种情况下他们会重新创建对象，那就是反序列化，将一个单例实例对象写到磁盘再读回来，从而获得了一个实例。反序列化操作提供了readResolve方法，这个方法可以让开发人员控制对象的反序列化。在上述的几个方法示例中如果要杜绝单例对象被反序列化是重新生成对象，就必须加入如下方法：

```java
private Object readResolve() throws ObjectStreamException{
		return singleton;
}
```

枚举单例的优点就是简单，但是大部分应用开发很少用枚举，可读性并不是很高，不建议用。

### **7. 使用容器实现单例模式**

```java
public class SingletonManager { 
　　private static Map<String, Object> objMap = new HashMap<String,Object>();
　　private Singleton() { 
　　}
　　public static void registerService(String key, Objectinstance) {
　　　　if (!objMap.containsKey(key) ) {
　　　　　　objMap.put(key, instance) ;
　　　　}
　　}
　　public static ObjectgetService(String key) {
　　　　return objMap.get(key) ;
　　}
}
```

用SingletonManager 将多种的单例类统一管理，在使用时根据key获取对象对应类型的对象。这种方式使得我们可以管理多种类型的单例，并且在使用时可以通过统一的接口进行获取操作，降低了用户的使用成本，也对用户隐藏了具体实现，降低了耦合度。

**总结**

到这里七中写法都介绍完了，至于选择用哪种形式的单例模式，取决于你的项目本身，是否是有复杂的并发环境，还是需要控制单例对象的资源消耗。