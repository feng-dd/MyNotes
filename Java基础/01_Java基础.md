## Java基础

### 1、面向对象的特性

封装、继承、多态、抽象

- **抽象：**将一类对象的共同特征总结出来构造类的过程；包括数据抽象和行为抽象；只关注对象的属性和行为，不关注行为的细节。

- **封装：**把数据和操作数据的方法绑定，对数据的访问只能通过已定义的接口；在类中编写方法就是对实现细节的封装，编写类就是对数据和数据操作的封装；隐藏一切可隐藏的东西，只向外界提供最简单的编程接口。
- **继承：**从已有类继承信息创建新类的过程；提供继承信息的是父类(基类)，得到继承信息的是子类(派生类)。
- **多态：**允许不同子类型的对象对同一消息作出不同的响应；同样的对象调用同样的方法做了不同的事情；编译时多态，重载(前绑定，`overload`)；运行时多态，重写(后绑定，`override`)

### 2、访问修饰符

|  修饰符   | 当前类 | 同包 | 子类 | 其他 |
| :-------: | :----: | :--: | :--: | :--: |
|  public   |   √    |  √   |  √   |  √   |
| protected |   √    |  √   |  √   |  ×   |
|  default  |   √    |  √   |  ×   |  ×   |
|  private  |   √    |  ×   |  ×   |  ×   |

### 3、数据类型

![基本数据类型](https://i.loli.net/2021/04/08/5dDt3kOoFEC7wSb.png)

|  类型   | 占用空间(1个字节=8位) |                           取值范围                           | 默认值         |  包装类   |
| :-----: | :-------------------: | :----------------------------------------------------------: | -------------- | :-------: |
|  byte   |        1个字节        |                         -2^7 ~ 2^7-1                         | 0              |   Byte    |
|  short  |        2个字节        |                        -2^15 ~ 2^15-1                        | 0              |   Short   |
|   int   |        4个字节        |                        -2^31 ~ 2^31-1                        | 0              |  Integer  |
|  long   |        8个字节        |                        -2^63 ~ 2^63-1                        | 0L             |   Long    |
|  float  |        4个字节        | 1.4E-45 ~ 3.4E+38,-3.4E+38 ~ -1.4E-45（1.4E-45 = 1.4*10^-45） | 0.0F           |   Float   |
| double  |        8个字节        |          4.9E-324 ~ 1.7E+308,-1.7E+308 ~ -4.9E-324           | 0.0            |  Double   |
|  char   |        2个字节        |                         unicode字符                          | '/uoooo'(null) | Character |
| boolean |                       |                         true\|false                          | false          |  Boolean  |

```java
Integer a = new Integer(3);
Integer b = 3;//自动装箱
int c = 3;
System.out.println(a==b);//false,不同对象
System.out.println(a==c);//true,a自动拆箱为int类型

Integer f1=100,f2=100,f3=150,f4=150;
System.out.println(f1==f2);//true,字面量-128~127 之间，不会new新的对象，直接引用常量池中的Integer对象
System.out.println(f3==f4);//false
```

### 4、自动型转换

![基本数据类型自动转型](https://i.loli.net/2021/04/08/apTlF6c48KRnfLQ.png)

> 两个char型运算时，自动转换为int型；当char与别的类型运算时，也会先自动转换为int型的，再做其它类型的自动转换
>
> long类型为何会自动转换为float类型？long占八个字节，float只占四个字节；float的取值范围是-2^128到2^128，远远大于long
>
> 整型在内存中是直接换算成二进制存储的
>
> float类型，4个字节，32位，第1位是符号位（数符），即S；接下来的8位是指数域（阶码），即E；最后的23位，是小数域（尾数），即M；float的取值范围是-2^128到2^128

```java
short s1 = 1; 
s1 = s1 + 1; // 编译不通过,必须强转,s1 = (short)(s1 + 1)
s1 += 1;//编译通过,隐式强转
```

### 5、向上向下转型

> **向上转型(隐式)**：`Cat cat= new cat(); Animal animal = cat;`，`animal`可以调用`cat`的私有方法。
>
> **向下转型：**`Animal animal = new cat()`，`animal`不可以调用`cat`的私有方法。
>
> ``` java
> if (animal instanceof Cat) {//必须检查对象是否属于特定类型，否则报 ClassCastException
>      ((Cat) animal).meow();
> }
> ```

### 6、&和&&

> `&`：按位与；`&&`：短路与。都是两侧为`true`时返回`true`，但是`&&`左侧为`false`时，会短路直接返回`false`。
>
> ```java
> username != null &&!username.equals(""); //验证用户登录时判定用户名不为空,左右两侧判断不能换，当左侧不成立时，右侧如果进行判断，会报空指针异常。
> ```

### 7、栈（stack）、堆(heap)和静态存储区

`String str = new String(“hello”);` 

**栈空间：**基本数据类型的变量，对象的引用，函数调用。`str`

**堆空间：**通过new关键字和构造器创建的对象。`new`创建出来的字符串对象

**静态存储区：**程序中的字面量，如直接书写的100、“hello”和常量。`"hello"`

### 8、`swtich(exp)`中可以放哪几种类型的变量。

`byte、short、char、int、enum(JDK1.5+)、String(JDK1.7+)`

### 9、2乘以8最有效率的方法

2 << 3（左移3位相当于乘以2的3次方，右移3位相当于除以2的3次方）。

### 10、`==`和`equals()`

> `==`：当比较的是基本数据类型时，判断值是否相等；当比较两个对象的引用时，判断的是引用地址是否相同(两个引用指向同一个对象)。
>
> `equals()`：判断两个对象是否相等。
>
> 如果`==`返回`true`，那么`equals()`一定返回`true`。

### 11、重写`Object的equals()、hashCode()`

`hashcode`是根据对象的内存地址经哈希算法得来的。

满足等价关系：

- **自反性(reflective)：**对于任意非`null`的引用值`x`，`x.equals(x)`一定返回`true`。
- **对称性(symmetric)：**对于任意非`null`的引用值`x,y`，当且仅当`x.equals(y)`返回`true`，那么`y.equals(x)`也返回`true`。
- **传递性(transitive)：**对于任意非`null`的引用值`x,y,z`，当且仅当`x.equals(y)`返回`true`，且`y.equals(z)`返回`true`，那么`x.equals(z)`也返回`true`。
- **一致性：**对于任意非`null`的引用值`x,y`，当x，y没有被修改时，多次调用`x.equals(y)`返回值一致。
- 对于任意非`null`的引用值`x`，`x.equals(null)`一定返回`false`。

在使用`set`集合存入自定义类的对象时一定要重写`equals()`和`hashCode()`。

`HashSet`的底层是通过`HashMap`实现的，`set`容器内的元素是否相等是通过对象的`hashCode`判断的，所以重写`equals()`一定要重写`hashCode()`。

> `equals()`为`true`的两个对象，`hashCode()`一定相等；
>
> `hashCode()`相等的两个对象，`equals()`不一定返回`true`。

```java
public class Employee{
    String name;
    int age;
    Date date;
    //getter.../setter...
}
 
public static void main(String[] args) {
    // TODO Auto-generated method stub
    Employee x = new Employee();
    x.setName("Jane");
    x.setAge(35);
        
    Employee y = new Employee();
    y.setName("Jane");
    y.setAge(35);
        
    System.out.println(x.equals(x));//true
    System.out.println(x.equals(y));//false
    
    Set set = new HashSet();
    set.add(x);
    set.add(y);
    System.out.println("size:" + set.size());//2，此时set集合中的元素重复了
}

// 重写equals()和hashCode()。
public class Employee{
    String name;
    int age;
    Date date;
    //getter.../setter...
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Employee)) //instanceof 已经处理了obj = null的情况
            return false;
        Employee other = (Employee) obj;
	    if(this == other) // 地址相等
            return true;
        // 如果两个员工姓名、工资、入职时间都相等，我们认为两个对象相等
        if (other.name.equals(this.name) && other.age.equals(this.age) && other.date.equals(this.date)) {
			return true;
		} else {
			return false;
		}
        // JDK8中提供了Objects工具类，可以帮助我们简化这部分代码
        // 如果两者相等，返回true（含两者皆空的情形），否则比较两者值是否相等
        return Objects.equals(this.name, other.name)
                && Objects.equals(this.salary, other.salary)
             	&& Objects.equals(this.date, other.date);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;// 选择31是因为可以用移位和减法运算来代替乘法，从而得到更好的性能,
        				    // 31 * i == (i<<5) - i VM自动完成这个优化
        int result = 1;
        result = prime * result + age;
        result = prime * result + ((name == null)?0:name.hashCode());
        return result;
    }
}
```

### 12、方法参数中值传递和引用传递

> **值传递：**传递的是值的拷贝
>
> **引用传递：**传递的是对象本身
>
> Java中都是值传递，基本数据类型传的是值的拷贝；引用类型参数传递是值的引用的拷贝，方法内改变引用拷贝指向的对象属性值时，因为指的引用和引用拷贝指向的是同一对象，所以改变了对象的属性值。

### 13、`String、StringBuffer、StringBuilder`区别

> 都可以存储和操作字符串
>
> `String`是`final`类，只读，不可变，不能被继承。
>
> `StringBuffer、StringBuilder`可以对字符串直接进行修改。
>
> `StringBuffer`相较于`StringBuilder`是线程安全的，但效率略低。

如果连接后得到的字符串在静态存储区中是早已存在的，那么用`+`做字符串连接是优于`StringBuffer / StringBuilder`的`append`方法的

![String面试题](https://i.loli.net/2021/04/08/jm6kQxhtZTILsCa.png)

`intern() `方法返回字符串对象的规范化表示形式，对于任意两个字符串`s`和 `t`，当且仅当`s.equals(t)`为`true`时，`s.intern() == t.intern()`才为`true`。

### 14、重载和重写

> 都是实现多态的方式。
>
> **重载(Override)：**发生在一个类中，同名方法有不同的参数列表(参数的个数或类型不同，或两者都不同)。
>
> **重写(Overload)：**发生在继承关系中，子类重写父类的方法(方法、返回值类型、参数列表都相同)，必须符合里式替换原则(保证子类比父类更好访问，更容易捕获异常)。
>
> 
>
> 华为面试题：能根据返回类型来区分重载吗？不能，因为当重载的方法只有返回值类型不同时，Java编译器无法分辨出你想要调用的是哪个方法。

### 15、`JVM`加载`Class`文件的原理机制

> `JVM`中类的加载由类加载器(`ClassLoader`)和子类加载器实现的。
>
> 类加载器负责运行时查找和装载`Class`文件中的类。

由于`Java`的跨平台性，`Java`源程序不是可执行文件，而是一个或多个`Class`文件。

当`Java`程序需要使用某个类时，`JVM`需要保证该类已被加载、连接(验证、准备、解析)、初始化。

![JVM加载Class文件](https://i.loli.net/2021/04/08/IkPc4fSuHDnErTU.png)

- **类的加载：**读取`Class`文件中的数据到内存中，通常是创建一个字节数组读取`Class`文件，然后产生与加载类对应的`Class`对象；此时`Class`对象还不完整，类不可用。

- **连接-验证：**过滤不符合`JVM`编译规则的文件。

- **连接-准备：**为静态变量分配内存，并赋默认的初始值。

- **连接-解析：**将符号引用替换为直接引用。
- **初始化：**`JVM`对类初始化；1.如果存在直接父类且父类没有被初始化，先初始化父类；2.如果类存在初始化语句，依次执行。

### 16、类加载机制

> 类加载器：
>
> 引导类加载器(`Bootstrap ClassLoader`)：加载被虚拟机识别的类库，如所有`java.`开头的类；无法被`Java`程序直接引用
>
> 扩展类加载器(`Extension ClassLoader`)：加载`javax.`开头的类；可以直接使用
>
> 应用程序类加载器(`Application ClassLoader`)：负责加载用户路径(`ClassPath`)下所指定的类；可以直接使用；若程序中没有自定义类加载器，默认。
>
> 面试题：类为什么需要先通过父类加载器加载，只有父类加载器无法加载时，才由子类加载器去加载？(**类加载器代理模式**)
>
> 前提，同一个`Java`类，由不同的类加载器加载，通过加载器得到的`Class`对象是不同的；所以`Java`中的核心类的加载工作必须由引导类加载器统一完成，保证兼容性。

**全盘负责：**当一个类加载器负责加载某个`Class`时，该`Class`所依赖和引用的其他`Class`也由它加载，除非显式使用另外的类加载器加载。

**父类委托：**先让父类加载器尝试加载，若无法加载，尝试从自己的类路径中加载该类。

**缓存机制：**保证所有被加载过的`Class`都存入缓存中；当程序要使用某`Class`时，从缓存中查找，若不存在，系统会读取该类对应的二进制数据，将其转换成`Class`对象，存入缓存；修改过`Class`后，必须重启`JVM`，程序的修改才会生效。

![双亲委派模型](https://i.loli.net/2021/04/08/bsroe9UmKXPAYRq.png)

**类加载有三种方式**

1. 命令行启动时，由`JVM`初始化加载

2. `Class.forName()`动态加载，默认执行初始化块（`static`块），可以指定不执行

3. `ClassLoader.loadClass()`动态加载，不会执行初始化块（`static`块），`newInstance`时会初始化

```java
public class loaderTest { 
        public static void main(String[] args) throws ClassNotFoundException { 
                ClassLoader loader = HelloWorld.class.getClassLoader(); 
                System.out.println(loader); 
                //使用ClassLoader.loadClass()来加载类，不会执行初始化块 
                loader.loadClass("Test2"); 
                //使用Class.forName()来加载类，默认会执行初始化块 
                Class.forName("Test2"); 
                //使用Class.forName()来加载类，并指定ClassLoader，初始化时不执行静态块 
                Class.forName("Test2", false, loader); 
        } 
}
```

### 17、抽象类和接口

> **同：**抽象类和接口都不能实例化，但可以定义为引用；某个类继承了抽象类或实现了接口，就必须对其中的抽象方法进行实现，否则该类仍是抽象类。
>
> **异：**抽象类中可以有构造器和具体方法；接口中的成员必须是`public`(`JDK1.8+`可以有`default`方法)。抽象类中可以任意定义成员变量，接口中只能定义常亮；抽象类可以没有抽象方法。

### 18、静态内部类和内部类

静态内部类可以不依靠外部类实例被实例化，内部类则需要外部类实例化后才能实例化。

### 19、`static`修饰符

**`static`变量：**类变量，不属于类的对象，在内存中只有一个拷贝；通过对象访问静态变量；可以让类的多个对象共享内存，常用于上下文类和工具类。

**`static`方法：**类方法，可以直接通过类名调用；静态方法只能访问静态成员；非静态方法可以调用静态方法。

**`static`**不能修饰`abstract`(抽象)方法，因为静态方法不能被重写。

### 20、创建对象时构造器的调用顺序是

先初始化静态成员，然后调用父类构造器，再初始化非静态成员，最后调用自身构造器

![子类和父类的构造顺序](https://i.loli.net/2021/04/08/VQbxdtzMcSeprGY.png)

**答案：**`1a2b2b`。

存在继承的情况下，初始化顺序为：

1. 父类（静态变量、静态语句块）
2. 子类（静态变量、静态语句块）
3. 父类（实例变量、普通语句块）
4. 父类（构造函数）
5. 子类（实例变量、普通语句块）
6. 子类（构造函数）

### 21、`Java`实现克隆

> **浅克隆：**实现`Cloneable`接口，重写`Object`的`clone()`方法。
>
> **深克隆：**1.实现`Cloneable`接口，重写`Object`的`clone()`方法，2.实现`Serializable`接口，通过序列化和反序列化实现深克隆。

### 22、`Error` 和`Exception`

![异常体系](https://i.loli.net/2021/04/08/Vs6SeX43xl2UYNZ.png)

**`Error`：**系统级的错误和程序不必处理的异常，是恢复不是不可能但很困难的情况下的一种严重问题；比如内存溢出，不可能指望程序能处理这样的情况；

**`Exception`：**表示需要捕捉或者需要程序进行处理的异常，是一种设计或实现问题；也就是说，它表示如果程序运行正常，从不会发生的情况。

> `Exception`主要分为运行时异常(`RuntimeException`)和检查时异常(`CheckedException`)
>
> - **受检异常 ：**需要用`try...catch...`语句捕获并进行处理，并且可以从异常中恢复。
>
> - **非受检异常 ：**是程序运行时错误，例如除 0 会引发`Arithmetic Exception`，此时程序崩溃并且无法恢复。

> `JVM`处理异常流程
>
> > 一般来说，发生异常时，堆上会生成一个异常对象「包含当前栈帧的快照」；
> > 然后停止当前的执行流程，将异常对象从当前`Context`抛出；
> > 然后交由异常处理机制处理异常，使程序继续执行。

> 异常的处理方式 
>
> > 遇到问题不进行具体处理，而是继续抛给调用者(`throw,throws`)

> > `try…catch…`捕获异常，针对处理

**自定义异常**

在`Java`中你可以自定义异常。编写自己的异常类时需要记住下面的几点：

- 所有异常都必须是`Throwable`的子类。
- 如果希望写一个检查性异常类，则需要继承`Exception`类。
- 如果你想写一个运行时异常类，那么需要继承`RuntimeException`类。

### 23、`throws、throw、try、catch、finally`

> **`try、catch、finally`：**用try来执行一段程序，如果出现异常，系统会抛出（throw）一个异常，这时候你可以通过它的类型来捕捉（catch）它，或最后（finally）由缺省处理器来处理；try用来指定一块预防所有“异常”的程序；catch 子句紧跟在try块后面，用来指定你想要捕捉的“异常”的类型；finally 为确保一段代码不管发生什么“异常”都被执行一段代码；
>
> 每当遇到一个try 语句，“异常”的框架就放到栈上面，直到所有的try语句都完成。如果下一级的try语句没有对某种“异常”进行处理，栈就会展开，直到遇到有处理这种“异常”的try 语句。
>
> **`throw`：**用来明确地抛出一个“异常”；
>
> **`throws`：**用来标明一个成员函数可能抛出的各种“异常”。

### 24、常见异常

**运行时异常：**

![运行时异常](https://i.loli.net/2021/04/08/94ZBCTSRr1JsDwL.png)

**检查性异常：**

![检查性异常](https://i.loli.net/2021/04/08/Xj3wfYazHbZtvq2.png)

### 25、`final, finally, finalize`的区别?

**`final`：**修饰符（关键字）：

- 如果一个类被声明为`final`，意味着它不能再派生出新的子类，即**不能被继承，因此它和`abstract`是反义词。
- 将变量声明为`final`，可以保证它们在使用中不被改变，被声明为`final`的变量必须在声明时给定初值，而在以后的引用中**只能读取不可修改**。
- 被声明为`final`的方法也同样只能使用，**不能在子类中被重写**。

**`finally`：**通常放在`try…catch`的后面构造总是执行代码块，这就意味着程序无论正常执行还是发生异常，这里的代码只要`JVM`不关闭都能执行，可以将释放外部资源的代码写在`finally`块中。

如果存在`finally`代码块，`try`中的`return`语句不会立马返回调用者，而是记录下返回值待`finally`代码块执行完毕之后再向调用者返回其值，如果`finally`代码块中也有`return`语句，那么走`finally`代码块的`return`语句。

**`finalize`：**`Object`类中定义的方法，`Java`中允许使用`finalize()`方法在垃圾收集器将对象从内存中清除出去之前做必要的清理工作。这个方法是由垃圾收集器在销毁对象时调用的，通过重写`finalize()`方法可以整理系统资源或者执行其他清理工作。

### 26、集合框架

跳转到[集合容器](/Users/murphy/IdeaProjects/MyNotes/Java基础/集合容器.md)

### 27、内存泄漏和内存溢出

> **内存泄漏：**理论上`Java`不会产生内存泄漏，因为有垃圾回收机制(`GC`)；在实际开发中，可能会存在无用但可达的对象，这些对象不能`GC`回收就会发生内存泄漏。
>
> **内存溢出(`OutOfMemoryError`)：**程序在申请内存时，没有足够的内存空间供其使用。

![内存泄漏](https://i.loli.net/2021/04/08/VivbLshmKDc3qWg.png)

上面的代码实现了一个栈（先进后出（`FILO`））结构，乍看之下似乎没有什么明显的问题，它甚至可以通过你编写的各种单元测试。然而其中的pop方法却存在内存泄露的问题，当我们用pop方法弹出栈中的对象时，该对象不会被当作垃圾回收，即使使用栈的程序不再引用这些对象，因为栈内部维护着对这些对象的过期引用（`obsolete reference`）。在支持垃圾回收的语言中，内存泄露是很隐蔽的，这种内存泄露其实就是无意识的对象保持。如果一个对象引用被无意识的保留起来了，那么垃圾回收器不会处理这个对象，也不会处理该对象引用的其他对象，即使这样的对象只有少数几个，也可能会导致很多的对象被排除在垃圾回收之外，从而对性能造成重大影响，极端情况下会引发`Disk Paging`（物理内存与硬盘的虚拟内存交换数据），甚至造成`OutOfMemoryError`。

### 28、`BIO、NIO、AIO`

BIO：同步阻塞IO，传统IO；操作简单、并发处理能力低。

NIO：同步非阻塞IO，BIO的升级优化，客户端和服务器端通过 Channel(通道)通讯，实现了多路复用。

AIO：异步非阻塞IO，NIO的升级；通过事件和回调机制实现异步非阻塞。

### 29、IO多路复用

[参考文章](https://blog.csdn.net/ChineseSoftware/article/details/123812179)

IO：网络IO

多路：多个TCP连接（socket或channel）

复用：复用同一个或一组线程

一个或一组线程处理多个TCP连接，减少系统资源开销，无所创建和维护过多进程/线程。

三种FD（文件描述符）：writefds(写)、readfds(读)、和 exceptfds(异常)

#### select 机制

1️⃣基本原理：
客户端操作服务器时就会产生这三种文件描述符(简称fd)：writefds(写)、readfds(读)、和 exceptfds(异常)。select 会阻塞住监视 3 类文件描述符，等有数据、可读、可写、出异常或超时就会返回；返回后通过遍历 fdset 整个数组来找到就绪的描述符 fd，然后进行对应的 IO 操作。

2️⃣优点：
几乎在所有的平台上支持，跨平台支持性好

3️⃣缺点：

由于是采用轮询方式全盘扫描，会随着文件描述符 FD 数量增多而性能下降。
每次调用 select()，都需要把 fd 集合从用户态拷贝到内核态，并进行遍历(消息传递都是从内核到用户空间)。
单个进程打开的 FD 是有限制(通过FD_SETSIZE设置)的，默认是 1024 个，可修改宏定义，但是效率仍然慢。

#### poll 机制

1️⃣基本原理与 select 一致，也是轮询+遍历。唯一的区别就是 poll 没有最大文件描述符限制(使用链表的方式存储 fd)。

2️⃣poll 缺点

由于是采用轮询方式全盘扫描，会随着文件描述符 FD 数量增多而性能下降。
每次调用 select()，都需要把 fd 集合从用户态拷贝到内核态，并进行遍历(消息传递都是从内核到用户空间)。

#### epoll 机制

1️⃣基本原理：
没有 fd 个数限制，用户态拷贝到内核态只需要一次，使用时间通知机制来触发。通过 epoll_ctl 注册 fd，一旦 fd 就绪就会通过 callback 回调机制来激活对应 fd，进行相关的 io 操作。epoll 之所以高性能是得益于它的三个函数：

epoll_create() 系统启动时，在 Linux 内核里面申请一个B+树结构文件系统，返回 epoll 对象，也是一个 fd。
epoll_ctl() 每新建一个连接，都通过该函数操作 epoll 对象，在这个对象里面修改添加删除对应的链接 fd，绑定一个 callback 函数
epoll_wait() 轮训所有的 callback 集合，并完成对应的 IO 操作
2️⃣优点：
没 fd 这个限制，所支持的 FD 上限是操作系统的最大文件句柄数，1G 内存大概支持 10 万个句柄。效率提高，使用回调通知而不是轮询的方式，不会随着 FD 数目的增加效率下降。内核和用户空间 mmap 同一块内存实现(mmap 是一种内存映射文件的方法，即将一个文件或者其它对象映射到进程的地址空间)

3️⃣epoll缺点：
epoll 只能工作在linux下。

4️⃣epoll 应用：redis、nginx

#### 区别

![image-20220908155753058](/Users/murphy/Library/Application Support/typora-user-images/image-20220908155753058.png)

## `GC`

> **`GabageCollection`：**垃圾收集，`Java`提供的`GC`功能可以自动监测对象是否超过作用域从而达到自动回收内存的目的；要请求垃圾收集，可以调用`System.gc()、Runtime.getRuntime().gc()`。
>
> 在`JVM`中有一个垃圾回收线程(守护线程)，在虚拟机空闲或者当前堆内存不足时，才会触发执行，扫瞄那些没有被任何引用的对象，并将它们添加到要回收的集合中，进行回收。

### 1、常见`GC`

- **`Minor GC/Young GC：`**新生代`GC`，指发生在新生代的垃圾收集动作，因为`java`对象大多都具备朝生夕死的特性，所以`Minor GC`非常频繁，一般回收速度也比较快。
- **`Old GC/Major GC：`**收集整个`Old gen`的`GC`，只有`CMS`模式这么称呼。`MajorGC`的速度一般比`Minor GC`慢10倍以上。
- **`Full GC：`**当老年代不够分配内存时，收集整个堆，包括`Young gen、Old gen、Perm gen`（如果存在的话）等所有部分的模式。很慢。
- **`Mixed GC：`**收集整个`young gen`以及部分`old gen`的`GC`。只有`G1`模式这么称呼。

### 2、判断一个对象是否存活

1. **引用计数法：**为每一个对象设置一个引用计数器，每当一个地方引用了该对象，计数器`+1`，引用失效时，计数器`-1`，当对象的计数器为`0`时，将会被回收。

   **缺陷：**无法解决循环引用问题，当对象`A`引用对象`B`，对象`B`，又引用对象`A` ，那么此时 `A` 、`B`对象的引用计数器都不为零，也就造成无法完成垃圾回收，所以主流的虚拟机都没有采用这种算法。

   ```java
   public class ReferenceCountingGC {
   
     public Object instance;
   
     public ReferenceCountingGC(String name) {
     }
   
     public static void testGC(){
       ReferenceCountingGC a = new ReferenceCountingGC("objA");
       ReferenceCountingGC b = new ReferenceCountingGC("objB");
       a.instance = b;
       b.instance = a;
       a = null;
       b = null;
     }
   }
   ```

   **最后这2个对象已经不可能再被访问了，但由于他们相互引用着对方，导致它们的引用计数永远都不会为`0`，通过引用计数算法，也就永远无法通知`GC`收集器回收它们。**

2. **引用链法：**把内存中的每一个对象都看作一个节点，并且定义了一些对象作为根节点(`GC Roots`)，如果`A`对象中有`B`对象的引用，那么就认为`A`对象有一条指向`B`对象的链；`JVM`会起一个线程从所有的`GC Roots`开始往下遍历，当遍历完之后发现有一些对象不可到达，那么就认为这些对象已经没有用了，需要被回收。

   ![可达链算法](https://i.loli.net/2021/04/08/DQWNvcowGkl5a86.png)

   **`GC Roots`**

   - **虚拟机栈中引用的对象：**创建的对象会在堆上开辟内存空间，栈中有引用保存该内存地址，当对象生命周期结束，栈中的引用会出栈，即栈中有引用，则对象可达。
   - **方法区类静态属性引用的对象：**栈是线程私有的，静态变量引用的对象的静态引用放在线程共享的方法区中。
   - **方法区常量池引用的对象：**即`static final`修饰的常量，这种引用初始化后不会改变。
   - **本地方法栈`JNI`引用的对象：**本地方法栈保存了`native`方法中的对象引用。

   **引用**

   - **强引用：**`new`创建的对象；`Object obj = new Object()`，只要`obj`的生命周期没结束，或没有显示的将`obj`置为`null`，`JVM`就不会回收。

   - **软引用：**`SoftReference`类创建；被弱引用的对象，只有内存不够，才会被回收。

     ![软引用](https://i.loli.net/2021/04/08/d7VBfpUY4oET15N.png)

   - **弱引用：**`WeakReference`类创建；被弱引用的对象，下一次`GC`一定会被回收，只存活一个垃圾回收周期

     ![弱引用](https://i.loli.net/2021/04/08/cqUlKuOeijkoYZX.png)

   - **虚引用：**`PhantomReference`类创建；唯一目的是能在这个对象被回收时收到一个系统通知。

     ![虚引用](https://i.loli.net/2021/04/08/FGXiuBkzYEn1lC6.png)

     ![虚引用2](https://i.loli.net/2021/04/08/DvdKV9wxOFh2psS.png)

   当一个对象引用不可达，并不一定会被回收。

   当一个对象引用不可达时，对象不会被立马回收，而是进入"死缓"，需要被标记两次才会回收；1.当`GC Roots`不可达时，会被第一次标记，同时进行筛选("没有必要执行"：对象未重写`finalize()`或已被`JVM`调用过)，"有必要执行"的对象会被放入`F-Queue`队列，等待`JVM`自动创建低优先级的`Finalizer`线程去执行`finalize()`，在执行过程中`JVM`不会一直等待`finalize()`执行完毕；2.如果在执行`finalize()`时，对象重新与引用链上的任意对象建立关联(如`this`)，那么该对象就会逃脱垃圾回收系统；如果没有重新建立关联，就会被`JVM`第二次标记，该对象将被移除`F-Queue`队列，等待回收。

   `finalize()`只能运行一次。

### 3、`GC`算法和垃圾收集器器

- **标记-清除：**"标记"&"清除"俩个阶段，标记需要回收的对象，进行统一清除。**缺点：**效率低；会产生内存碎片，导致无法为大对象分配内存。
- **复制算法：**将内存划分为相等的两块，每次只使用其中一块，当这块用完，将存活的对象复制到另一块内存中，将这块内存一次清理。**优点：**效率高;不产生内存碎片；**缺点：**只使用了内存的一半。
- **标记-整理：**在**标记-清除**的基础上，标记完需要回收的对象后，先让存活的对象向一端移动，然后直接清理边界以外的对象。**优点：**避免了内存浪费以及产生内存碎片的问题。
- **分代收集(常用)：**基于对象的生命周期，将对象分别存放到新生代、老年代；新生代使用**复制算法**(因为对象存活率很低)，老年代使用**标记-清理**或**标记-整理**(对象存活率较高，没有额外空间进行分配担保)。

> 收集算法是内存回收的方法论，那么垃圾收集器就是内存回收的具体实现。
>
> 图片下是不同分代的收集器，如果相互间有连线，可以搭配使用；所处的区域表示他们属于新生代收集器还是老年代收集器。

![垃圾收集器](https://i.loli.net/2021/04/08/AIaqUPwloHyghXN.png)

- **`Serial`收集器：**串行收集器，**单线程**，工作时会暂停其他所有线程，等待它执行结束。会引起`STW(Stop The Word)`,由虚拟机在后台自动发起和自动完成的，在用户不可见的情况下把用户正常工作的线程全部停掉。`Serial与Serial Old`收集器运行过程：

  ![Serial/Serial Old 收集器](https://i.loli.net/2021/04/08/KuIPVpsWQFAieRC.png)

- **`ParNew`收集器：**`Serial`收集器的**多线程**版本。`ParNew与Serial Old`收集器运行过程：

  ![ParNew/Serial Old 收集器](https://i.loli.net/2021/04/08/1PHVypdRvW8hOBL.png)

- **`Paralle Scavenge`收集器：新生代**收集器，也使用**复制算法**，也是并行的**多线程**收集器；使得可以达到一个可控制的吞吐量(`CPU`用于运行用户代码的时间与`CPU`总消耗时间的比值，即吞吐量 = 运行代码时间 / (运行用户代码时间 + 垃圾收集时间))。通过下列参数精准控制：

  **`-XX:MaxGCPauseMillis`：**控制最大垃圾收集停顿时间；值是一个大于`0`的毫秒数，保证内存回收花费的时间不超过特定值；`GC`停顿时间缩短是以牺牲吞吐量和新生代空间来换取的：系统把新生代调小一些，收集`300M`新生代肯定比收集`500M`快，这也直接导致垃圾收集发生的更频繁，原来`10秒`收集一次，每次停顿`100毫秒`，现在变成`5秒`收集一次，每次停顿`70毫秒`，停顿时间的确下降，但吞吐量也在下降。

  **`-XX:GCTimeRatio`：**设置吞吐量大小；值是一个大于`0`且小于`100`的整数，垃圾收集时间占总时间的比率，相当于吞吐量的倒数；如果把测参数设置为19，那允许的最大`GC`时间就占总时间的`5%`（即`1/（1+19）`）

  **`-XX:UseAdaptiveSizePolicy`:**这是一个开关参数，当这个开关打开之后，就不需要手动指定新生代的大小（`-Xmn`）、`Eden`与`Survivor`区的比列（`-XX:SurvivorRatio`）、晋升老年代对象大小（`-XX:PertenureSizeThreshold`）等参数细节了，虚拟机会根据当前系统的运行情况收集性能监控信息，动态调整这些参数以提供最合适的停顿时间或者最大的吞吐量。

- **`Paralle Old`收集器：**是`Paralle Scavenge`收集器的老年代版本，使用**多线程**和**标记-整理**算法。`Paralle Scavenge与Paralle Old`收集器运行过程：

  ![Paralle Scavenge、Paralle Old收集器](https://i.loli.net/2021/04/08/9j2Mq4TxEnR16AI.png)

- **`CMS(Concurrent Mark Sweep)`收集器：**以获取最短回收停顿时间为目标的收集器，适合在注重用户体验的应用上使用；**老年代、多线程、标记-清除算法**。收集器运行过程：初始标记-并发标记-重复标记-并发清除。

- **`G1`收集器：**`JDK1.9`默认，**管理整个堆、多线程、多种`GC`算法**。收集器运行过程：初始标记-并发标记-最终标记-筛选回收。

### 4、`JVM`内存模型

![JVM内存结构(JDK1.8之前)](https://i.loli.net/2021/04/08/TGIa3CJHvSYefgN.png)

![JVM内存结构(JDK1.8之后)](https://i.loli.net/2021/04/08/vmiQ2MSjI9P3Kp8.png)

- **堆(线程共享）**

  用于存放对象实例和数组，垃圾回收器管理的主要区域；

  垃圾回收的主要算法是分代收集算法；以分代算法可以将堆分为新生代&老年代，新生代可进一步细分为`Eden`空间、`From survivor`空间、`To survivor`空间；细分的目的是根据对象的特征，更好的分配及回收内存空间；

  堆可以处于物理上不连续的内存空间，只要逻辑上是连续的即可，与磁盘一样；

  当前堆内存如果已经用完且无法动态扩展时，会触发`OutOfMemoryError`。

  可以通过`-Xms`和`-Xmx `这俩个虚拟机参数指定一个程序的堆内存大小，初始值和最大值`java -Xms1M -Xmx2M HackTheJava`。

- **方法区（线程共享）**

  用于存放已经被虚拟机加载的类信息(名称,修饰符等)、常量、静态变量、即时编译器编译后的代码等数据。和堆一样不需要物理上连续的内存，也可以动态扩展；运行时常量池是方法区的一部分，用于存放编译器生成的各种字面量和符号引用。

  该区域的内存可以选择不进行回收，该区域主要针对常量池的回收&类型的卸载。

  `JDK1.8`之前，虚拟机把方法区当做永久代进行 `GC` 回收，但逻辑上属于非堆内存中的一部分，实际不在堆上创建；`JDK1.8`开始，移除永久代，把方法区移出虚拟机内存，位于本地内存中

  所有线程共享同一个方法区，因此访问方法区数据的和动态链接的进程必须线程安全。如果两个线程试图访问一个还未加载的类的字段或方法，只能加载一次，而且两个线程必须等它加载完毕才能继续执行

  当方法区无法满足内存分配需求时，会触发`OutOfMemoryError`。

  

  用于存储class二进制文件，包含了虚拟机加载的类信息、常量(常量池)、静态变量(静态域)、即时编译后的代码等数据。

  **常量池：**

  常量池在编译期间就将一部分数据存放于该区域，包含以`final`修饰的基本数据类型的常量值、`String`字符串。

  **静态域：**

  存放类中以`static`声明的静态成员变量。

- **程序计数器（线程私有）**

  当前线程所执行的字节码的行号指示器。

  如果线程正在执行一个Java方法，计数器记录的是正在执行的虚拟机字节码指令的地址

  如果线程正在执行的是Native方法，这个计数器值则为空(Undefined)

  该区域不会有任何`OutOfMemoryError`的情况

- **虚拟机栈（线程私有）**

  描述的是Java方法执行的内存模型，栈是先进后出的数据结构，当前执行的方法在栈的顶部；每个方法在执行时，都会创建一个栈帧

  **栈帧：**存储局部变量表、操作数栈、常量池引用等信息，每个方法调用都意味着一个栈帧在虚拟机栈中入栈和出栈的过程。

  **局部变量表：**存储了基本数据类型、对象引用、返回地址

  可以通过`-Xss`这个虚拟机参数指定每个线程的`Java`虚拟机栈内存大小` java -Xss512M HackTheJava`

  当线程请求的栈深度大于虚拟机允许的最大深度时，触发`StackOverflowError`

  当虚拟机在动态扩展时无法申请到足够的内存时，触发`OutOfMemoryError`

- **本地方法栈（线程私有）**

  与虚拟机栈作用相似，区别在于虚拟机栈为Java方法服务，而本地方法栈为Native（本地）方法服务

- **直接内存**

  `JVM`内存以外的区域，当`JVM`内存区域+直接内存区域的总和大于物理内存或者达到操作系统限制，会导致动态扩展时触发`OutOfMemoryError`异常

  在使用大量`NIO`程序中有可能触发`OutOfMemoryError`异常

### 5、`Java`内存分配

#### 1 .对象优先在Eden分配

大多数情况下，对象会在新生代`Eden`区中分配。当`Eden`区没有足够空间进行分配时，虚拟机会发起一次 `Minor GC`。`Minor GC`相比`Major GC`更频繁，回收速度也更快。通过`Minor GC`之后，`Eden`区中绝大部分对象会被回收，而那些存活对象，将会送到`Survivor`的`From`区（**若`From`区空间不够，则直接进入`Old`区**） 。

#### 2 .Survivor区

`Survivor`区相当于是`Eden`区和`Old`区的一个缓冲，类似于我们交通灯中的黄灯。`Survivor`又分为2个区，一个是`From`区，一个是`To`区。每次执行`Minor GC`，会将`Eden`区中存活的对象放到`Survivor`的`From`区，而在`From`区中，仍存活的对象会根据他们的年龄值来决定去向。（`From Survivor`和`To Survivor`的逻辑关系会发生颠倒： `From`变`To` ， `To`变`From`，目的是保证有连续的空间存放对方，避免碎片化的发生，复制算法）

##### Survivor区存在的意义

如果没有`Survivor`区，`Eden`区每进行一次`Minor GC`，存活的对象就会被送到老年代，老年代很快就会被填满。而有很多对象虽然一次`Minor GC`没有消灭，但其实也并不会蹦跶多久，或许第二次，第三次就需要被清除。这时候移入老年区，很明显不是一个明智的决定。所以，`Survivor`的存在意义就是减少被送到老年代的对象，进而减少`Major GC`的发生。`Survivor`的预筛选保证，只有经历`16`次`Minor GC`还能在新生代中存活的对象，才会被送到老年代。

#### 3. 大对象直接进入老年代

所谓大对象是指，需要大量连续内存空间的`Java`对象，最典型的大对象就是那种很长的字符串以及数组。大对象对虚拟机的内存分配来说就是一个坏消息，经常出现大对象容易导致内存还有不少空间时就提前触发垃圾收集以获取足够的连续空间来 “安置” 它们。

虚拟机提供了一个`XX:PretenureSizeThreshold`参数，令大于这个设置值的对象直接在老年代分配，这样做的目的是避免在`Eden`区及两个`Survivor`区之间发生大量的内存复制（新生代采用的是复制算法）。

#### 4. 长期存活的对象将进入老年代

虚拟机给每个对象定义了一个对象年龄（`Age`）计数器，如果对象在Eden出生并经过第一次`Minor GC`后仍然存活，并且能被`Survivor`容纳的话，将被移动到`Survivor`空间中（正常情况下对象会不断的在`Survivor`的`From`与`To`区之间移动），并且对象年龄设为1。对象在`Survivor`区中每经历一次`Minor GC`，年龄就增加1岁，当它的年龄增加到一定程度（默认15岁），就将会晋升到老年代中。对象晋升	老年代的年龄阈值，可以通过参数 `XX:MaxPretenuringThreshold` 设置。

#### 5. 动态对象年龄判定

为了能更好地适应不同程度的内存状况，虚拟机并不是永远地要求对象的年龄必须达到 `MaxPretenuringThreshold`才能晋升老年代，如果`Survivor`空间中相同年龄所有对象大小的总和大于`Survivor`空间的一半，年龄大于或等于改年龄的对象就可以直接进入老年代，无需等到`MaxPretenuringThreshold`中要求的年龄。

### 5、`JVM`参数

- `-Xms / -Xmx` --- 堆的初始大小 / 堆的最大大小
- `-Xmn` --- 堆中年轻代的大小
- `-XX：-DisableExplicitGC` --- 让`System.gc()`不产生任何作用
- `-XX:+PrintGCDetail` --- 打印`GC`的细节
- `-XX:+PrintGCDateStamps` --- 打印`GC`操作的时间戳
- `-XX:MaxPretenuringThreshold` --- 对象晋升老年代的年龄阈值
- `-XX:PretenureSizeThreshold` --- 大于这个设置值的对象直接在老年代分配
- `-XX:SurvivorRatio` --- 配置`Eden`区域`Survivor`区的容量比值，默认是`8`，代表`Eden:Survivor1:Survivor2=​`:eight:::one:::one:。

12. 

13. 