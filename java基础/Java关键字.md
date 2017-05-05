# Java关键字

## 1. synchronized

synchronized用来修饰一个方法或者一个代码块，能够保证同一时刻最多只有一个线程执行改段代码

### 1.1 对应规则

#### 1.1.1 当两个并发线程访问同一个对象中的同一个synchronized(this)同步代码块。一个时间段内只能有一个线程能够执行该代码块，另外一个线程必须等待当前线程执行完这个同步代码块才能执行该代码块

```
public class Thread1 implements Runnable {  
     public void run() {  
          synchronized(this) {  
               for (int i = 0; i < 5; i++) {  
                    System.out.println(Thread.currentThread().getName() + " synchronized loop " + i);  
               }  
          }  
     }  
     public static void main(String[] args) {  
          Thread1 t1 = new Thread1();  
          Thread ta = new Thread(t1, "A");  
          Thread tb = new Thread(t1, "B");  
          ta.start();  
          tb.start();  
     } 
}
```
结果

```
A synchronized loop 0 
A synchronized loop 1  
A synchronized loop 2  
A synchronized loop 3  
A synchronized loop 4  
B synchronized loop 0  
B synchronized loop 1  
B synchronized loop 2  
B synchronized loop 3  
B synchronized loop 4
```

#### 1.1.2 当一个线程访问一个对象中的一个synchronized(this)同步代码块，另外一个线程仍然可以访问该对象中的非synchronized(this)同步代码块

```
public class Thread2 {  
     public void m4t1() {  
          synchronized(this) {  
               int i = 5;  
               while( i-- > 0) {  
                    System.out.println(Thread.currentThread().getName() + " : " + i);  
                    try {  
                         Thread.sleep(500);  
                    } catch (InterruptedException ie) {  
                    }  
               }  
          }  
     }  
     public void m4t2() {  
          int i = 5;  
          while( i-- > 0) {  
               System.out.println(Thread.currentThread().getName() + " : " + i);  
               try {  
                    Thread.sleep(500);  
               } catch (InterruptedException ie) {  
               }  
          }  
     }  
     public static void main(String[] args) {  
          final Thread2 myt2 = new Thread2();  
          Thread t1 = new Thread(  new Runnable() {  public void run() {  myt2.m4t1();  }  }, "t1"  );  
          Thread t2 = new Thread(  new Runnable() {  public void run() { myt2.m4t2();   }  }, "t2"  );  
          t1.start();  
          t2.start();  
     } 
}
```

结果： 

```
t1 : 4  
t2 : 4  
t1 : 3  
t2 : 3  
t1 : 2  
t2 : 2  
t1 : 1  
t2 : 1  
t1 : 0  
t2 : 0
```

#### 1.1.3 当一个线程访问一个对象中的一个synchronized(this)同步代码块，另外一个线程不可以可以访问该对象中的synchronized(this)同步代码块

```
public class Thread2 {  
     public void m4t1() {  
          synchronized(this) {  
               int i = 5;  
               while( i-- > 0) {  
                    System.out.println(Thread.currentThread().getName() + " : " + i);  
                    try {  
                         Thread.sleep(500);  
                    } catch (InterruptedException ie) {  
                    }  
               }  
          }  
     }  
     public void m4t2() {  
          synchronized(this) {  
               int i = 5;  
               while( i-- > 0) {  
                    System.out.println(Thread.currentThread().getName() + " : " + i);  
                    try {  
                         Thread.sleep(500);  
                    } catch (InterruptedException ie) {  
                    }  
               }  
          }
     }
     public static void main(String[] args) {  
          final Thread2 myt2 = new Thread2();  
          Thread t1 = new Thread(  new Runnable() {  public void run() {  myt2.m4t1();  }  }, "t1"  );  
          Thread t2 = new Thread(  new Runnable() {  public void run() { myt2.m4t2();   }  }, "t2"  );  
          t1.start();  
          t2.start();  
     } 
}
```

结果：

```
t1 : 4  
t1 : 3  
t1 : 2  
t1 : 1  
t1 : 0  
t2 : 4  
t2 : 3  
t2 : 2  
t2 : 1  
t2 : 0
```
### 1.2 synchronized方法 和 synchronized块

#### 1.2.1 synchronized方法

```
public synchronized void accessVal(int newVal);
```

#### 1.2.2 synchronized块

```
synchronized(syncObject) {  
//允许访问控制的代码  
} 
```

## 2. abstract

## 2.1 abstract使用注意点

* abstract 可以修饰类或方法
* abstract 类不可以直接实例化
* abstract 方法不在声明它的类中实现，但必须在某个子类中重写此抽象方法。
* abstract 关键字不能应用于 static、private 或 final 方法，因为这些方法不能被重写，因此，不能在子类中实现
* abstract类中不一定包含抽象方法，但是包含抽象方法的类一定要声明为抽象类
* 子类使用extends继承抽象父类

```
public  abstract class MyClass {
public void log(){
    System.out.print("hehe");
}
public abstract void  goHome();
public abstract void  backHome();
}
```

## 3. interface

接口是若干常量和抽象方法的集合，是一种特殊的抽象类

### 3.1 interface使用注意点

* 所有的方法必须都是抽象的(这些方法默认public abstract，同理常量默认为public static final)，不能有方法体，是一种特殊的抽象类


```
public interface SataHdd{
    //连接线的数量
    public static final int CONNECT_LINE=4;
    //写数据
    public void writeData(String data);
    //读数据
    public String readData();
}
```

## 4. interface和abstract的区别

|| abstract | interface |
|:--|:--|:--|
|实例化|不能|不能|
|类的继承和实现|一个类只能继承一个父类|一个类可以实现多个接口|
|是否需要实现抽象方法|是|是|
|实现|使用extends|使用implements |


## 5. final


## 6. static

