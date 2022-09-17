### 一、编程规约

#### (一) 命名风格

1. 【强制】代码中的命名均不能以下划线或美元符号开始，也不能以下划线或美元符号结束。

   反例：_name/__name/$name/name_/name$/name__

2. 【强制】代码中的命名严禁使用拼音与英文混合的方式，更不允许直接使用中文的方式。 

   说明：正确的英文拼写和语法可以让阅读者易于理解，避免歧义。注意，即使纯拼音命名方式也要避免采用。

3. 【强制】类名使用UpperCamelCase风格，但以下情形例外：DO /BO / DTO / VO/ AO / PO / UID等。 

   正例：MarcoPolo / UserDO /XmlService /TcpUdpDeal /TaPromotion 

   反例：macroPolo / UserDo /XMLService /TCPUDPDeal /TAPromotion 

4. 【强制】方法名、参数名、成员变量、局部变量都统一使用lowerCamelCase风格，必须遵从驼峰形式。 

   正例： localValue / getHttpMessage() / inputUserId 

5. 【强制】常量命名全部大写，单词间用下划线隔开，力求语义表达完整清楚，不要嫌名字长。

   正例：MAX_STOCK_COUNT

   反例：MAX_COUNT

6. 【强制】抽象类命名使用Abstract或Base开头；异常类命名使用Exception结尾；测试类命名以它要测试的类的名称开始，以Test结尾。 

7. 【强制】类型与中括号紧挨相连来表示数组。

   正例：定义整形数组int[] arrayDemo;

   反例：在main参数中，使用String args[]来定义。

8. 【强制】POJO类中布尔类型的变量，都不要加is前缀，否则部分框架解析会引起序列化错误。 

   反例：定义为基本数据类型Boolean isDeleted的属性，它的方法也是isDeleted()，RPC框架在反向解析的时候，“误以为”对应的属性名称是deleted，导致属性获取不到，进而抛出异常。 

9. 【强制】包名统一使用小写，点分隔符之间有且仅有一个自然语义的英语单词。包名统一使用单数形式，但是类名如果有复数含义，类名可以使用复数形式。

   正例：应用工具类包名为com.alibaba.ai.util、类名为MessageUtils（此规则参考spring的框架结构） 

10. 【强制】杜绝完全不规范的缩写，避免望文不知义。

    反例：AbstractClass“缩写”命名成AbsClass；condition“缩写”命名成 condi，此类随意缩写严重降低了代码的可阅读性。

11. 【推薦】为了达到代码自解释的目标，任何自定义编程元素在命名时，使用尽量完整的单词组合来表达其意。

    正例：在JDK中，表达原子更新的类名为：AtomicReferenceFieldUpdater。

    反例：变量int a的随意命名方式。

12. 【推薦】如果模块、接口、类、方法使用了设计模式，在命名时需体现出具体模式。 

    说明：将设计模式体现在名字中，有利于阅读者快速理解架构设计理念。

    正例：

    public class OrderFactory; 

    public class LoginProxy;

    public class ResourceObserver; 

13. 【推薦】接口类中的方法和属性不要加任何修饰符号（public也不要加），保持代码的简洁性，并加上有效的Javadoc注释。尽量不要在接口里定义变量，如果一定要定义变量，肯定是与接口方法相关，并且是整个应用的基础常量。

    正例：接口方法签名void commit(); 接口基础常量String COMPANY = "alibaba"; 

    反例：接口方法定义public abstract void f(); 

    说明：JDK8中接口允许有默认实现，那么这个default方法，是对所有实现类都有价值的默认实现。 

14. 接口和实现类的命名有两套规则： 

    1）【强制】对于Service和DAO类，基于SOA的理念，暴露出来的服务一定是接口，内部的实现类用Impl的后缀与接口区别。 

    正例：CacheServiceImpl实现CacheService接口。 

    2） 【推薦】 如果是形容能力的接口名称，取对应的形容词为接口名（通常是–able的形式）。 

    正例：AbstractTranslator实现 Translatable接口。 

15. 【參考】枚举类名建议带上Enum后缀，枚举成员名称需要全大写，单词间用下划线隔开。

    说明：枚举其实就是特殊的类，域成员均为常量，且构造方法被默认强制是私有。 

    正例：枚举名字为ProcessStatusEnum的成员名称：SUCCESS / UNKNOWN_REASON。 

16.【參考】各层命名规约： 

​	A) Service/DAO层方法命名规约 

​		1） 获取单个对象的方法用get做前缀。 

​		2） 获取多个对象的方法用list做前缀，复数形式结尾如：listObjects。 

​		3） 获取统计值的方法用count做前缀。 

​		4） 插入的方法用save/insert做前缀。 

​		5） 删除的方法用remove/delete做前缀。 

​		6） 修改的方法用update做前缀。 

​	B) 领域模型命名规约 

​		1） 数据对象：xxxDO，xxx即为数据表名。 

​		2） 数据传输对象：xxxDTO，xxx为业务领域相关的名称。 

​		3） 展示对象：xxxVO，xxx一般为网页名称。 

​		4） POJO是DO/DTO/BO/VO的统称，禁止命名成xxxPOJO。

 

#### (二) 常量定义

1. 【强制】不允许任何魔法值（即未经预先定义的常量）直接出现在代码中。

   反例：

   String key = "Id#taobao_" + tradeId; 

   cache.put(key, value); 

2. 【强制】在long或者Long赋值时，数值后使用大写的L，不能是小写的l，小写容易跟数字1混淆，造成误解。

   说明：Long a = 2l; 写的是数字的21，还是Long型的2? 

3. 【强制】不要使用一个常量类维护所有常量，要按常量功能进行归类，分开维护。 

   说明：大而全的常量类，杂乱无章，使用查找功能才能定位到修改的常量，不利于理解和维护。

   正例：缓存相关常量放在类CacheConsts下；系统配置相关常量放在类ConfigConsts下。 

4. 【推薦】常量的复用层次有五层：跨应用共享常量、应用内共享常量、子工程内共享常量、包内共享常量、类内共享常量。 

   1） 跨应用共享常量：放置在二方库中，通常是client.jar中的constant目录下。 

   2） 应用内共享常量：放置在一方库中，通常是子模块中的constant目录下。 

   反例：易懂变量也要统一定义成应用内共享常量，两位攻城师在两个类中分别定义了表示“是”的变量： 

   类A中：public static final String YES = "yes"; 

   类B中：public static final String YES = "y"; 

   A.YES.equals(B.YES)，预期是true，但实际返回为false，导致线上问题。 

   3） 子工程内部共享常量：即在当前子工程的constant目录下。 

   4） 包内共享常量：即在当前包下单独的constant目录下。 

   5） 类内共享常量：直接在类内部private static final定义。 

5. 【推薦】如果变量值仅在一个固定范围内变化用enum类型来定义。 

   说明：如果存在名称之外的延伸属性应使用enum类型，下面正例中的数字就是延伸信息，表示一年中的第几个季节。

   正例： 

```java
public enum SeasonEnum { 
    SPRING(1), SUMMER(2), AUTUMN(3), WINTER(4); 
    private int seq; 
    SeasonEnum(int seq){ 
        this.seq = seq; 
    } 
}
```



#### (三) 代码格式

1. 【强制】大括号的使用约定。如果是大括号内为空，则简洁地写成{}即可，不需要换行；如果是非空代码块则： 

   1） 左大括号前不换行。 

   2） 左大括号后换行。 

   3） 右大括号前换行。 

   4） 右大括号后还有else等代码则不换行；表示终止的右大括号后必须换行。 

2. 【强制】左小括号和字符之间不出现空格；同样，右小括号和字符之间也不出现空格；而左大括号前需要空格。详见第5条下方正例提示。 

   反例：if (空格a == b空格)

3. 【强制】if/for/while/switch/do等保留字与括号之间都必须加空格。 

4. 【强制】任何二目、三目运算符的左右两边都需要加一个空格。

   说明：运算符包括赋值运算符=、逻辑运算符&&、加减乘除符号等。 

5. 【强制】采用4个空格缩进，禁止使用tab字符。 

   说明：如果使用tab缩进，必须设置1个tab为4个空格。IDEA设置tab为4个空格时，请勿勾选Use tab character；而在eclipse中，必须勾选insert spaces for tabs。

   正例： （涉及1-5点）  

```java
public static void main(String[] args) { 
    // 缩进4个空格 
    String say = "hello"; 
    // 运算符的左右必须有一个空格 
    int flag = 0; 
    // 关键词if与括号之间必须有一个空格，括号内的f与左括号，0与右括号不需要空格 
    if (flag == 0) { 
        System.out.println(say);
    }
    // 左大括号前加空格且不换行；左大括号后换行 
    if (flag == 1) { 
        System.out.println("world");
    // 右大括号前换行，右大括号后有else，不用换行 
    } else { 
        System.out.println("ok");
    // 在右大括号后直接结束，则必须换行 
    } 
}
```

​    6. 【强制】注释的双斜线与注释内容之间有且仅有一个空格。

​	正例：

​	// 这是示例注释，请注意在双斜线之后有一个空格 

​	String ygb = new String(); 

7. 【强制】单行字符数限制不超过120个，超出需要换行，换行时遵循如下原则：

   1）第二行相对第一行缩进4个空格，从第三行开始，不再继续缩进，参考示例。

   2） 运算符与下文一起换行。 

   3） 方法调用的点符号与下文一起换行。 

   4） 方法调用中的多个参数需要换行时，在逗号后进行。 

   5） 在括号前不要换行，见反例。 

   正例： 

```java
StringBuffer sb = new StringBuffer(); 
// 超过120个字符的情况下，换行缩进4个空格，点号和方法名称一起换行 
sb.append("zi").append("xin")... 
.append("huang")... 
.append("huang")... 
.append("huang");
```

   反例：

```java
StringBuffer sb = new StringBuffer(); 
// 超过120个字符的情况下，不要在括号前换行 
sb.append("zi").append("xin")...append
("huang"); 
// 参数很多的方法调用可能超过120个字符，不要在逗号前换行 
method(args1, args2, args3, ... , argsX);
```

​     8.【强制】方法参数在定义和传入时，多个参数逗号后边必须加空格。 

​		正例：下例中实参的args1，后边必须要有一个空格。

​		method(args1, args2, args3); 

9. 【强制】IDE的text file encoding设置为UTF-8; IDE中文件的换行符使用Unix格式，不要使用Windows格式。 

10. 【推薦】单个方法的总行数不超过80行。

    说明：包括方法签名、结束右大括号、方法内代码、注释、空行、回车及任何不可见字符的总行数不超过80行.

    正例：代码逻辑分清红花和绿叶，个性和共性，绿叶逻辑单独出来成为额外方法，使主干代码更加清晰；共性逻辑抽取成为共性方法，便于复用和维护。

11. 【推薦】没有必要增加若干空格来使某一行的字符与上一行对应位置的字符对齐。 

    正例： 

```java
int one = 1; 
long two = 2L; 
float three = 3F; 
StringBuffer sb = new StringBuffer();
```

​	说明：增加sb这个变量，如果需要对齐，则给a、b、c都要增加几个空格，在变量比较多的情况下，是非常累赘的事情。

12. 【推薦】不同逻辑、不同语义、不同业务的代码之间插入一个空行分隔开来以提升可读性。 

    说明：任何情形，没有必要插入多个空行进行隔开。

 

#### (四) OOP规约

1. 【强制】避免通过一个类的对象引用访问此类的静态变量或静态方法，无谓增加编译器解析成本，直接用类名来访问即可。

2. 【强制】所有的覆写方法，必须加@Override注解。 

   说明：getObject()与get0bject()的问题。一个是字母的O，一个是数字的0，加@Override可以准确判断是否覆盖成功。另外，如果在抽象类中对方法签名进行修改，其实现类会马上编译报错。

3. 【强制】相同参数类型，相同业务含义，才可以使用Java的可变参数，避免使用Object。 

   说明：可变参数必须放置在参数列表的最后。（提倡同学们尽量不用可变参数编程） 

   正例：public List<User> listUsers(String type, Long... ids) {...} 

4. 【强制】外部正在调用或者二方库依赖的接口，不允许修改方法签名，避免对接口调用方产生影响。接口过时必须加@Deprecated注解，并清晰地说明采用的新接口或者新服务是什么。

5. 【强制】不能使用过时的类或方法。 

   说明：java.net.URLDecoder中的方法decode(String encodeStr) 这个方法已经过时，应该使用双参数decode(String source, String encode)。接口提供方既然明确是过时接口，那么有义务同时提供新的接口；作为调用方来说，有义务去考证过时方法的新实现是什么。 

6. 【强制】Object的equals方法容易抛空指针异常，应使用常量或确定有值的对象来调用equals。 

   正例："test".equals(object); 

   反例：object.equals("test"); 

   说明：推荐使用java.util.Objects#equals（JDK7引入的工具类） 

7. 【强制】所有的相同类型的包装类对象之间值的比较，全部使用equals方法比较。 

   说明：对于Integer var = ? 在-128至127范围内的赋值，Integer对象是在IntegerCache.cache产生，会复用已有对象，这个区间内的Integer值可以直接使用==进行判断，但是这个区间之外的所有数据，都会在堆上产生，并不会复用已有对象，这是一个大坑，推荐使用equals方法进行判断。 

8.  关于基本数据类型与包装数据类型的使用标准如下：

   1） 【强制】所有的POJO类属性必须使用包装数据类型。

   2） 【强制】RPC方法的返回值和参数必须使用包装数据类型。 

   3） 【推薦】所有的局部变量使用基本数据类型。

   说明：POJO类属性没有初值是提醒使用者在需要使用时，必须自己显式地进行赋值，任何NPE问题，或者入库检查，都由使用者来保证。 

   正例：数据库的查询结果可能是null，因为自动拆箱，用基本数据类型接收有NPE风险。 

   反例：比如显示成交总额涨跌情况，即正负x%，x为基本数据类型，调用的RPC服务，调用不成功时，返回的是默认值，页面显示为0%，这是不合理的，应该显示成中划线。所以包装数据类型的null值，能够表示额外的信息，如：远程调用失败，异常退出。

9. 【强制】定义DO/DTO/VO等POJO类时，不要设定任何属性默认值。 

   反例：POJO类的gmtCreate默认值为new Date()，但是这个属性在数据提取时并没有置入具体值，在更新其它字段时又附带更新了此字段，导致创建时间被修改成当前时间。

10. 【强制】序列化类新增属性时，请不要修改serialVersionUID字段，避免反序列失败；如果完全不兼容升级，避免反序列化混乱，那么请修改serialVersionUID值。 

    说明：注意serialVersionUID不一致会抛出序列化运行时异常。

11. 【强制】构造方法里面禁止加入任何业务逻辑，如果有初始化逻辑，请放在init方法中。 

12. 【强制】POJO类必须写toString方法。使用IDE中的工具：source> generate toString时，如果继承了另一个POJO类，注意在前面加一下super.toString。 

​	说明：在方法执行抛出异常时，可以直接调用POJO的toString()方法打印其属性值，便于排查问题。

13. 【强制】禁止在POJO类中，同时存在对应属性xxx的isXxx()和getXxx()方法。

    说明：框架在调用属性xxx的提取方法时，并不能确定哪个方法一定是被优先调用到。

14. 【推薦】使用索引访问用String的split方法得到的数组时，需做最后一个分隔符后有无内容的检查，否则会有抛IndexOutOfBoundsException的风险。 

    说明：

```java
String str = "a,b,c,,"; 
String[] ary = str.split(","); 
// 预期大于3，结果是3 
System.out.println(ary.length);
```

15. 【推薦】当一个类有多个构造方法，或者多个同名方法，这些方法应该按顺序放置在一起，便于阅读，此条规则优先于第16条规则。

16. 【推薦】类内方法定义的顺序依次是：公有方法或保护方法> 私有方法 > getter/setter方法。 

    说明：公有方法是类的调用者和维护者最关心的方法，首屏展示最好；保护方法虽然只是子类关心，也可能是“模板设计模式”下的核心方法；而私有方法外部一般不需要特别关心，是一个黑盒实现；因为承载的信息价值较低，所有Service和DAO的getter/setter方法放在类体最后。 

17. 【推薦】setter方法中，参数名称与类成员变量名称一致，this.成员名 =参数名。在getter/setter方法中，不要增加业务逻辑，增加排查问题的难度。

    反例： 

```java
public Integer getData() { 
    if (condition) { 
        return this.data + 100; 
    } else { 
        return this.data - 100; 
    } 
}
```

18. 【推薦】循环体内，字符串的连接方式，使用StringBuilder的append方法进行扩展。 

    说明：下例中，反编译出的字节码文件显示每次循环都会new出一个StringBuilder对象，然后进行append操作，最后通过toString方法返回String对象，造成内存资源浪费。

    反例：

```java
String str = "start"; 
for (int i = 0; i < 100; i++) { 
    str = str + "hello"; 
}
```

19. 【推薦】final可以声明类、成员变量、方法、以及本地变量，下列情况使用final关键字： 

    1） 不允许被继承的类，如：String类。 

    2） 不允许修改引用的域对象。 

    3） 不允许被重写的方法，如：POJO类的setter方法。

    4） 不允许运行过程中重新赋值的局部变量。 

    5） 避免上下文重复使用一个变量，使用final描述可以强制重新定义一个变量，方便更好地进行重构。

20. 【推薦】慎用Object的clone方法来拷贝对象。 

    说明：对象的clone方法默认是浅拷贝，若想实现深拷贝需要重写clone方法实现域对象的深度遍历式拷贝。

21.【推薦】类成员与方法访问控制从严： 

​	1） 如果不允许外部直接通过new来创建对象，那么构造方法必须是private。 

​	2） 工具类不允许有public或default构造方法。 

​	3） 类非static成员变量并且与子类共享，必须是protected。 

​	4） 类非static成员变量并且仅在本类使用，必须是private。 

​	5） 类static成员变量如果仅在本类使用，必须是private。 

​	6） 若是static成员变量，考虑是否为final。 

​	7） 类成员方法只供类内部调用，必须是private。 

​	8） 类成员方法只对继承类公开，那么限制为protected。 

​	说明：任何类、方法、参数、变量，严控访问范围。过于宽泛的访问范围，不利于模块解耦。思考：如果是一个private的方法，想删除就删除，可是一个public的service成员方法或成员变量，删除一下，不得手心冒点汗吗？变量像自己的小孩，尽量在自己的视线内，变量作用域太大，无限制的到处跑，那么你会担心的。



#### (五) 集合处理

1. 【强制】关于hashCode和equals的处理，遵循如下规则：

   1） 只要重写equals，就必须重写hashCode。 

   2） 因为Set存储的是不重复的对象，依据hashCode和equals进行判断，所以Set存储的对象必须重写这两个方法。 

   3） 如果自定义对象作为Map的键，那么必须重写hashCode和equals。 

   说明：String重写了hashCode和equals方法，所以我们可以非常愉快地使用String对象作为key来使用。 

2. 【强制】ArrayList的subList结果不可强转成ArrayList，否则会抛出ClassCastException异常，即java.util.RandomAccessSubList cannot be cast to java.util.ArrayList。 

   说明：subList 返回的是 ArrayList 的内部类 SubList，并不是 ArrayList而是ArrayList 的一个视图，对于SubList子列表的所有操作最终会反映到原列表上。

3. 【强制】在subList场景中，高度注意对原集合元素的增加或删除，均会导致子列表的遍历、增加、删除产生ConcurrentModificationException 异常。 

4. 【强制】使用集合转数组的方法，必须使用集合的toArray(T[] array)，传入的是类型完全一样的数组，大小就是list.size()。 

   说明：使用toArray带参方法，入参分配的数组空间不够大时，toArray方法内部将重新分配内存空间，并返回新数组地址；如果数组元素个数大于实际所需，下标为[ list.size() ]的数组元素将被置为null，其它数组元素保持原值，因此最好将方法入参数组大小定义与集合元素个数一致。

   正例：

```java
List<String>
list = new ArrayList<String>(2); 
list.add("guan");
list.add("bao");
String[]
array = new String[list.size()]; 
array = list.toArray(array);
```

​		反例：直接使用toArray无参方法存在问题，此方法返回值只能是Object[]类，若强转其它类型数组将出现ClassCastException错误。

5. 【强制】使用工具类Arrays.asList()把数组转换成集合时，不能使用其修改集合相关的方法，它的add/remove/clear方法会抛出UnsupportedOperationException异常。 

   说明：asList的返回对象是一个Arrays内部类，并没有实现集合的修改方法。Arrays.asList体现的是适配器模式，只是转换接口，后台的数据仍是数组。 String[] str = new String[] { "you", "wu" }; List list = Arrays.asList(str); 

   第一种情况：list.add("yangguanbao");运行时异常。 

   第二种情况：str[0] = "gujin"; 那么list.get(0)也会随之修改。 

6. 【强制】泛型通配符<? extends T>来接收返回的数据，此写法的泛型集合不能使用add方法，而<? super T>不能使用get方法，作为接口调用赋值时易出错。 

   说明：扩展说一下PECS(Producer Extends Consumer Super)原则：

   第一、频繁往外读取内容的，适合用<? extends T>。

   第二、经常往里插入的，适合用<? super T>。 

7. 【强制】不要在foreach循环里进行元素的remove/add操作。remove元素请使用Iterator方式，如果并发操作，需要对Iterator对象加锁。 

   正例：

```java
List<String>
list = new ArrayList<>(); 
list.add("1");
list.add("2");
Iterator<String>
iterator = list.iterator(); 
while (iterator.hasNext()) { 
    String item = iterator.next(); 
    if (删除元素的条件) { 
        iterator.remove();
    }
}
```

​	 反例：

```java
for (String item : list) { 
    if ("1".equals(item)) { 
        list.remove(item);
    }
}
```

​	说明：以上代码的执行结果肯定会出乎大家的意料，那么试一下把“1”换成“2”，会是同样的结果吗？

8. 【强制】 在JDK7版本及以上，Comparator实现类要满足如下三个条件，不然Arrays.sort，Collections.sort会报IllegalArgumentException异常。 

   说明：三个条件如下

   1） x，y的比较结果和y，x的比较结果相反。 

   2） x>y，y>z，则x>z。 

   3） x=y，则x，z比较结果和y，z比较结果相同。 

   反例：下例中没有处理相等的情况，实际使用中可能会出现异常：

```java
new Comparator<Student>() { 
    @Override 
    public
    int compare(Student o1, Student o2) { 
        return o1.getId() > o2.getId() ? 1 : -1; 
    }
};
```

9. 【推薦】集合泛型定义时，在JDK7及以上，使用diamond语法或全省略。 

   说明：菱形泛型，即diamond，直接使用<>来指代前边已经指定的类型。

   正例： 

   // <> diamond方式 

   HashMap<String, String> userCache = new HashMap<>(16); 

   //全省略方式 

   ArrayList<User> users = new ArrayList(10); 

10. 【推薦】集合初始化时，指定集合初始值大小。 

    说明：HashMap使用HashMap(int initialCapacity) 初始化。 

    正例：initialCapacity =(需要存储的元素个数 / 负载因子) + 1。注意负载因子（即loader factor）默认为0.75，如果暂时无法确定初始值大小，请设置为16（即默认值）。 

    反例：HashMap需要放置1024个元素，由于没有设置容量初始大小，随着元素不断增加，容量7次被迫扩大，resize需要重建hash表，严重影响性能。

11. 【推薦】使用entrySet遍历Map类集合KV，而不是keySet方式进行遍历。 

    说明：keySet其实是遍历了2次，一次是转为Iterator对象，另一次是从hashMap中取出key所对应的value。而entrySet只是遍历了一次就把key和value都放到了entry中，效率更高。如果是JDK8，使用Map.foreach方法。 

    正例：values()返回的是V值集合，是一个list集合对象；keySet()返回的是K值集合，是一个Set集合对象；entrySet()返回的是K-V值组合集合。 

12. 【推薦】高度注意Map类集合K/V能不能存储null值的情况，如下表格：

| 集合类            | Key          | Value        | Super       | 说明                   |
| ----------------- | ------------ | ------------ | ----------- | ---------------------- |
| Hashtable         | 不允许为null | 不允许为null | Dictionary  | 线程安全               |
| ConcurrentHashMap | 不允许为null | 不允许为null | AbstractMap | 锁分段技术（JDK8:CAS） |
| TreeMap           | 不允许为null | 允许为null   | AbstractMap | 线程不安全             |
| HashMap           | 允许为null   | 允许为null   | AbstractMap | 线程不安全             |

​	反例： 由于HashMap的干扰，很多人认为ConcurrentHashMap是可以置入null值，而事实上，存储null值时会抛出NPE异常。 

13. 【參考】合理利用好集合的有序性(sort)和稳定性(order)，避免集合的无序性(unsort)和不稳定性(unorder)带来的负面影响。 

    说明：有序性是指遍历的结果是按某种比较规则依次排列的。稳定性指集合每次遍历的元素次序是一定的。如：ArrayList是order/unsort；HashMap是unorder/unsort；TreeSet是order/sort。 

14. 【參考】利用Set元素唯一的特性，可以快速对一个集合进行去重操作，避免使用List的contains方法进行遍历、对比、去重操作。

 

#### (六) 并发处理

1. 【强制】获取单例对象需要保证线程安全，其中的方法也要保证线程安全。

   说明：资源驱动类、工具类、单例工厂类都需要注意。 

2. 【强制】创建线程或线程池时请指定有意义的线程名称，方便出错时回溯。 

   正例： 

```java
public class TimerTaskThread extends Thread { 
    public TimerTaskThread() { 
        super.setName("TimerTaskThread");
        ...
    }
}
```

3. 【强制】线程资源必须通过线程池提供，不允许在应用中自行显式创建线程。 

   说明：使用线程池的好处是减少在创建和销毁线程上所消耗的时间以及系统资源的开销，解决资源不足的问题。如果不使用线程池，有可能造成系统创建大量同类线程而导致消耗完内存或者“过度切换”的问题。 

4. 【强制】线程池不允许使用Executors去创建，而是通过ThreadPoolExecutor的方式，这样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险。

   说明：Executors返回的线程池对象的弊端如下:

   1）FixedThreadPool和SingleThreadPool:允许的请求队列长度为Integer.MAX_VALUE，可能会堆积大量的请求，从而导致OOM。

   2）CachedThreadPool和ScheduledThreadPool:允许的创建线程数量为Integer.MAX_VALUE，可能会创建大量的线程，从而导致OOM。

5. 【强制】SimpleDateFormat 是线程不安全的类，一般不要定义为static变量，如果定义为static，必须加锁，或者使用DateUtils工具类。 

   正例：注意线程安全，使用DateUtils。亦推荐如下处理： 

```java
private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() { 
    @Override 
    protected DateFormat initialValue() { 
        return new SimpleDateFormat("yyyy-MM-dd"); 
    } 
};
```

​	说明：如果是JDK8的应用，可以使用Instant代替Date，LocalDateTime代替Calendar，DateTimeFormatter代替SimpleDateFormat，官方给出的解释：			simple beautiful strong immutable thread-safe。

6. 【强制】高并发时，同步调用应该去考量锁的性能损耗。能用无锁数据结构，就不要用锁；能锁区块，就不要锁整个方法体；能用对象锁，就不要用类锁。 

   说明：尽可能使加锁的代码块工作量尽可能的小，避免在锁代码块中调用RPC方法。 

7. 【强制】对多个资源、数据库表、对象同时加锁时，需要保持一致的加锁顺序，否则可能会造成死锁。

   说明：线程一需要对表A、B、C依次全部加锁后才可以进行更新操作，那么线程二的加锁顺序也必须是A、B、C，否则可能出现死锁。 

8. 【强制】并发修改同一记录时，避免更新丢失，需要加锁。要么在应用层加锁，要么在缓存加锁，要么在数据库层使用乐观锁，使用version作为更新依据。 

   说明：如果每次访问冲突概率小于20%，推荐使用乐观锁，否则使用悲观锁。乐观锁的重试次数不得小于3次。 

9. 【强制】多线程并行处理定时任务时，Timer运行多个TimeTask时，只要其中之一没有捕获抛出的异常，其它任务便会自动终止运行，使用ScheduledExecutorService则没有这个问题。 

10. 【强制】使用CountDownLatch进行异步转同步操作，每个线程退出前必须调用countDown方法，线程执行代码注意catch异常，确保countDown方法被执行到，避免主线程无法执行至await方法，直到超时才返回结果。 

    说明：注意，子线程抛出异常堆栈，不能在主线程try-catch到。 

11. 【强制】避免Random实例被多线程使用，虽然共享该实例是线程安全的，但会因竞争同一seed 导致的性能下降。

    说明：Random实例包括java.util.Random 的实例或者 Math.random()的方式。 

    正例：在JDK7之后，可以直接使用API ThreadLocalRandom，而在 JDK7之前，需要编码保证每个线程持有一个实例。 

12. 【推薦】在并发场景下，通过双重检查锁（double-checked locking）实现延迟初始化的优化问题隐患(可参考 The "Double-Checked Locking is Broken" Declaration)，推荐解决方案中较为简单一种（适用于JDK5及以上版本），将目标属性声明为 volatile型。 

    反例： 

```java
class LazyInitDemo { 
    private Helper helper = null; 
    public Helper getHelper() { 
        if (helper == null) synchronized(this) { 
            if (helper == null) 
                helper = new Helper(); 
        }
        return helper; 
    }
    // other methods and fields... 
}
```

13. 【參考】volatile解决多线程内存不可见问题。对于一写多读，是可以解决变量同步问题，但是如果多写，同样无法解决线程安全问题。如果是count++操作，使用如下类实现：AtomicInteger count = new AtomicInteger(); count.addAndGet(1); 如果是JDK8，推荐使用LongAdder对象，比AtomicLong性能更好（减少乐观锁的重试次数）。 

14. 【參考】 HashMap在容量不够进行resize时由于高并发可能出现死链，导致CPU飙升，在开发过程中可以使用其它数据结构或加锁来规避此风险。

15. 【參考】ThreadLocal无法解决共享对象的更新问题，ThreadLocal对象建议使用static修饰。这个变量是针对一个线程内所有操作共享的，所以设置为静态变量，所有此类实例共享此静态变量 ，也就是说在类第一次被使用时装载，只分配一块存储空间，所有此类的对象(只要是这个线程内定义的)都可以操控这个变量。 

 

#### (七) 控制语句

1. 【强制】在一个switch块内，每个case要么通过break/return等来终止，要么注释说明程序将继续执行到哪一个case为止；在一个switch块内，都必须包含一个default语句并且放在最后，即使空代码。

2. 【强制】在if/else/for/while/do语句中必须使用大括号。即使只有一行代码，避免采用单行的编码方式：if (condition) statements; 

3. 【强制】在高并发场景中，避免使用”等于”判断作为中断或退出的条件。

   说明：如果并发控制没有处理好，容易产生等值判断被“击穿”的情况，使用大于或小于的区间判断条件来代替。

   反例：判断剩余奖品数量等于0时，终止发放奖品，但因为并发处理错误导致奖品数量瞬间变成了负数，这样的话，活动无法终止。

4. 【推薦】表达异常的分支时，少用if-else方式，这种方式可以改写成： 

   if (condition) { 

   ... 

   return obj; 

   } 

   // 接着写else的业务逻辑代码;

   说明：如果非得使用if()...else if()...else...方式表达逻辑，避免后续代码维护困难，请勿超过3层。 

   正例：超过3层的 if-else 的逻辑判断代码可以使用卫语句、策略模式、状态模式等来实现，其中卫语句示例如下：

```java
public void today() { 
    if (isBusy()) { 
        System.out.println(“change time.”); 
        return; 
    } 
    if (isFree()) { 
        System.out.println(“go to travel.”); 
        return; 
    } 
    System.out.println(“stay at home to learn Alibaba Java Coding Guidelines.”);
    return; 
}
```

5. 【推薦】除常用方法（如getXxx/isXxx）等外，不要在条件判断中执行其它复杂的语句，将复杂逻辑判断的结果赋值给一个有意义的布尔变量名，以提高可读性。

   说明：很多if语句内的逻辑相当复杂，阅读者需要分析条件表达式的最终结果，才能明确什么样的条件执行什么样的语句，那么，如果阅读者分析逻辑表达式错误呢？

   正例：

```java
// 伪代码如下 
final boolean existed = (file.open(fileName, "w") != null) && (...) || (...); 
if (existed) { 
... 
}
```

​	反例：

```java
if ((file.open(fileName, "w") != null) && (...) || (...)) { 
    ... 
}
```

6. 【推薦】循环体中的语句要考量性能，以下操作尽量移至循环体外处理，如定义对象、变量、获取数据库连接，进行不必要的try-catch操作（这个try-catch是否可以移至循环体外）。 

7. 【推薦】避免采用取反逻辑运算符。

   说明：取反逻辑不利于快速理解，并且取反逻辑写法必然存在对应的正向逻辑写法。

   正例：使用if (x < 628)来表达x 小于628。

   反例：使用if (!(x >= 628))来表达x 小于628。

8. 【推薦】接口入参保护，这种场景常见的是用作批量操作的接口。

9. 【參考】下列情形，需要进行参数校验： 

   1） 调用频次低的方法。 

   2） 执行时间开销很大的方法。此情形中，参数校验时间几乎可以忽略不计，但如果因为参数错误导致中间执行回退，或者错误，那得不偿失。

   3） 需要极高稳定性和可用性的方法。 

   4） 对外提供的开放接口，不管是RPC/API/HTTP接口。

   5） 敏感权限入口。 

10. 【參考】下列情形，不需要进行参数校验： 

    1） 极有可能被循环调用的方法。但在方法说明里必须注明外部参数检查要求。

    2） 底层调用频度比较高的方法。毕竟是像纯净水过滤的最后一道，参数错误不太可能到底层才会暴露问题。一般DAO层与Service层都在同一个应用中，部署在同一台服务器中，所以DAO的参数校验，可以省略。 

    3） 被声明成private只会被自己代码所调用的方法，如果能够确定调用方法的代码传入参数已经做过检查或者肯定不会有问题，此时可以不校验参数。

 

#### (八) 注释规约

1. 【强制】类、类属性、类方法的注释必须使用Javadoc规范，使用/**内容*/格式，不得使用//xxx方式。 

   说明：在IDE编辑窗口中，Javadoc方式会提示相关注释，生成Javadoc可以正确输出相应注释；在IDE中，工程调用方法时，不进入方法即可悬浮提示方法、参数、返回值的意义，提高阅读效率。

2. 【强制】所有的抽象方法（包括接口中的方法）必须要用Javadoc注释、除了返回值、参数、异常说明外，还必须指出该方法做什么事情，实现什么功能。

   说明：对子类的实现要求，或者调用注意事项，请一并说明。 

3. 【强制】所有的类都必须添加创建者和创建日期。 

4. 【强制】方法内部单行注释，在被注释语句上方另起一行，使用//注释。方法内部多行注释使用/* */注释，注意与代码对齐。

5. 【强制】所有的枚举类型字段必须要有注释，说明每个数据项的用途。

6. 【推薦】与其“半吊子”英文来注释，不如用中文注释把问题说清楚。专有名词与关键字保持英文原文即可.

   反例：“TCP连接超时”解释成“传输控制协议连接超时”，理解反而费脑筋。 

7. 【推薦】代码修改的同时，注释也要进行相应的修改，尤其是参数、返回值、异常、核心逻辑等的修改。

   说明：代码与注释更新不同步，就像路网与导航软件更新不同步一样，如果导航软件严重滞后，就失去了导航的意义。 

8. 【參考】谨慎注释掉代码。在上方详细说明，而不是简单地注释掉。如果无用，则删除。

   说明：代码被注释掉有两种可能性：1）后续会恢复此段代码逻辑。2）永久不用。前者如果没有备注信息，难以知晓注释动机。后者建议直接删掉（代码仓库保存了历史代码）。 

9. 【參考】对于注释的要求：第一、能够准确反应设计思想和代码逻辑；第二、能够描述业务含义，使别的程序员能够迅速了解到代码背后的信息。完全没有注释的大段代码对于阅读者形同天书，注释是给自己看的，即使隔很长时间，也能清晰理解当时的思路；注释也是给继任者看的，使其能够快速接替自己的工作。 

10. 【參考】好的命名、代码结构是自解释的，注释力求精简准确、表达到位。避免出现注释的一个极端：过多过滥的注释，代码的逻辑一旦修改，修改注释是相当大的负担。

    反例： 

    // put elephant into fridge 

    put(elephant, fridge); 

    方法名put，加上两个有意义的变量名elephant和fridge，已经说明了这是在干什么，语义清晰的代码不需要额外的注释。

11. 【參考】特殊注释标记，请注明标记人与标记时间。注意及时处理这些标记，通过标记扫描，经常清理此类标记。线上故障有时候就是来源于这些标记处的代码。

    1） 待办事宜（TODO）:（ 标记人，标记时间，[预计处理时间]） 表示需要实现，但目前还未实现的功能。这实际上是一个Javadoc的标签，目前的Javadoc还没有实现，但已经被广泛使用。只能应用于类，接口和方法（因为它是一个Javadoc标签）。 

    2） 错误，不能工作（FIXME）:（标记人，标记时间，[预计处理时间]） 在注释中用FIXME标记某代码是错误的，而且不能工作，需要及时纠正的情况。



#### (九) 其它

1. 【强制】在使用正则表达式时，利用好其预编译功能，可以有效加快正则匹配速度。

   说明：不要在方法体内定义：Pattern pattern = Pattern.compile(“规则”); 

2. 【强制】velocity调用POJO类的属性时，建议直接使用属性名取值即可，模板引擎会自动按规范调用POJO的getXxx()，如果是boolean基本数据类型变量（boolean命名不需要加is前缀），会自动调用isXxx()方法。 

   说明：注意如果是Boolean包装类对象，优先调用getXxx()的方法。 

3. 【强制】后台输送给页面的变量必须加$!{var}——中间的感叹号。 

   说明：如果var等于null或者不存在，那么${var}会直接显示在页面上。

4. 【强制】注意 Math.random() 这个方法返回是double类型，注意取值的范围0≤x<1（能够取到零值，注意除零异常），如果想获取整数类型的随机数，不要将x放大10的若干倍然后取整，直接使用Random对象的nextInt或者nextLong方法。 

5. 【强制】获取当前毫秒数System.currentTimeMillis(); 而不是new Date().getTime(); 

   说明：如果想获取更加精确的纳秒级时间值，使用System.nanoTime()的方式。在JDK8中，针对统计时间等场景，推荐使用Instant类。 

6. 【推薦】不要在视图模板中加入任何复杂的逻辑。 

   说明：根据MVC理论，视图的职责是展示，不要抢模型和控制器的活。 

7. 【推薦】任何数据结构的构造或初始化，都应指定大小，避免数据结构无限增长吃光内存。 

8. 【推薦】及时清理不再使用的代码段或配置信息。 

   说明：对于垃圾代码或过时配置，坚决清理干净，避免程序过度臃肿，代码冗余。 

   正例：对于暂时被注释掉，后续可能恢复使用的代码片断，在注释代码上方，统一规定使用三个斜杠(///)来说明注释掉代码的理由。



### 二、异常日志

#### (一) 异常处理

1. 【强制】Java 类库中定义的可以通过预检查方式规避的RuntimeException异常不应该通过catch 的方式来处理，比如：NullPointerException，IndexOutOfBoundsException等等。 

   说明：无法通过预检查的异常除外，比如，在解析字符串形式的数字时，不得不通过catch NumberFormatException来实现。 

   正例：if (obj != null) {...} 

   反例：try { obj.method(); } catch (NullPointerException e) {…} 

2. 【强制】异常不要用来做流程控制，条件控制。 

   说明：异常设计的初衷是解决程序运行中的各种意外情况，且异常的处理效率比条件判断方式要低很多。 

3. 【强制】catch时请分清稳定代码和非稳定代码，稳定代码指的是无论如何不会出错的代码。对于非稳定代码的catch尽可能进行区分异常类型，再做对应的异常处理。 

   说明：对大段代码进行try-catch，使程序无法根据不同的异常做出正确的应激反应，也不利于定位问题，这是一种不负责任的表现。 

   正例：用户注册的场景中，如果用户输入非法字符，或用户名称已存在，或用户输入密码过于简单，在程序上作出分门别类的判断，并提示给用户。 

4. 【强制】捕获异常是为了处理它，不要捕获了却什么都不处理而抛弃之，如果不想处理它，请将该异常抛给它的调用者。最外层的业务使用者，必须处理异常，将其转化为用户可以理解的内容。 

5. 【强制】有try块放到了事务代码中，catch异常后，如果需要回滚事务，一定要注意手动回滚事务。 

6. 【强制】finally块必须对资源对象、流对象进行关闭，有异常也要做try-catch。 

   说明：如果JDK7及以上，可以使用try-with-resources方式。 

7. 【强制】不要在finally块中使用return。

   说明：finally块中的return返回后方法结束执行，不会再执行try块中的return语句。

8. 【强制】捕获异常与抛异常，必须是完全匹配，或者捕获异常是抛异常的父类。

   说明：如果预期对方抛的是绣球，实际接到的是铅球，就会产生意外情况。 

9. 【推薦】方法的返回值可以为null，不强制返回空集合，或者空对象等，必须添加注释充分说明什么情况下会返回null值。 

   说明：本手册明确防止NPE是调用者的责任。即使被调用方法返回空集合或者空对象，对调用者来说，也并非高枕无忧，必须考虑到远程调用失败、序列化失败、运行时异常等场景返回null的情况。 

10. 【推薦】防止NPE，是程序员的基本修养，注意NPE产生的场景： 

    1）返回类型为基本数据类型，return包装数据类型的对象时，自动拆箱有可能产生NPE。 

    反例：public int f() { return Integer对象}， 如果为null，自动解箱抛NPE。 

    2） 数据库的查询结果可能为null。 

    3） 集合里的元素即使isNotEmpty，取出的数据元素也可能为null。 

    4） 远程调用返回对象时，一律要求进行空指针判断，防止NPE。 

    5） 对于Session中获取的数据，建议NPE检查，避免空指针。 

    6） 级联调用obj.getA().getB().getC()；一连串调用，易产生NPE。

    正例：使用JDK8的Optional类来防止NPE问题。 

11. 【推薦】定义时区分unchecked / checked 异常，避免直接抛出new RuntimeException()，更不允许抛出Exception或者Throwable，应使用有业务含义的自定义异常。推荐业界已定义过的自定义异常，如：DAOException / ServiceException等。 

12. 【參考】对于公司外的http/api开放接口必须使用“错误码”；而应用内部推荐异常抛出；跨应用间RPC调用优先考虑使用Result方式，封装isSuccess()方法、“错误码”、“错误简短信息”。 

    说明：关于RPC方法返回方式使用Result方式的理由： 

    1）使用抛异常返回方式，调用方如果没有捕获到就会产生运行时错误。

    2）如果不加栈信息，只是new自定义异常，加入自己的理解的error message，对于调用端解决问题的帮助不会太多。如果加了栈信息，在频繁调用出错的情况下，数据序列化和传输的性能损耗也是问题。

13. 【參考】避免出现重复的代码（Don’t Repeat Yourself），即DRY原则。 

    说明：随意复制和粘贴代码，必然会导致代码的重复，在以后需要修改时，需要修改所有的副本，容易遗漏。必要时抽取共性方法，或者抽象公共类，甚至是组件化。

    正例：一个类中有多个public方法，都需要进行数行相同的参数校验操作，这个时候请抽取：

    private boolean checkParam(DTO dto) {...} 

#### (二) 日志规约

1. 【强制】应用中不可直接使用日志系统（Log4j、Logback）中的API，而应依赖使用日志框架SLF4J中的API，使用门面模式的日志框架，有利于维护和各个类的日志处理方式统一。

```java
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 
private static final Logger logger = LoggerFactory.getLogger(Abc.class);
```

2. 【强制】日志文件至少保存15天，因为有些异常具备以“周”为频次发生的特点。 

3. 【强制】应用中的扩展日志（如打点、临时监控、访问日志等）命名方式

   appName_logType_logName.log。 

   logType:日志类型，如stats/monitor/access等；logName:日志描述。这种命名的好处：通过文件名就可知道日志文件属于什么应用，什么类型，什么目的，也有利于归类查找。

   正例：mppserver应用中单独监控时区转换异常，如：mppserver_monitor_timeZoneConvert.log 

   说明：推荐对日志进行分类，如将错误日志和业务日志分开存放，便于开发人员查看，也便于通过日志对系统进行及时监控。

4. 【强制】对trace/debug/info级别的日志输出，必须使用条件输出形式或者使用占位符的方式。 

   说明：logger.debug("Processing trade with id: " + id + " and symbol: " + symbol); 如果日志级别是warn，上述日志不会打印，但是会执行字符串拼接操作，如果symbol是对象，会执行toString()方法，浪费了系统资源，执行了上述操作，最终日志却没有打印。

   正例：（条件）建设采用如下方式 

```java
if (logger.isDebugEnabled()) { 
    logger.debug("Processing trade with id: " + id + " and symbol: " + symbol); 
}
```

​	正例：（占位符） 

​	logger.debug("Processing trade with id: {} and symbol : {} ", id, symbol); 

5. 【强制】避免重复打印日志，浪费磁盘空间，务必在log4j.xml中设置additivity=false。 

   正例：<logger name="com.taobao.dubbo.config" additivity="false"> 

6. 【强制】异常信息应该包括两类信息：案发现场信息和异常堆栈信息。如果不处理，那么通过关键字throws往上抛出。 

   正例：logger.error(各类参数或者对象toString() + "_" + e.getMessage(), e); 

7. 【推薦】谨慎地记录日志。生产环境禁止输出debug日志；有选择地输出info日志；如果使用warn来记录刚上线时的业务行为信息，一定要注意日志输出量的问题，避免把服务器磁盘撑爆，并记得及时删除这些观察日志。

   说明：大量地输出无效日志，不利于系统性能提升，也不利于快速定位错误点。记录日志时请思考：这些日志真的有人看吗？看到这条日志你能做什么？能不能给问题排查带来好处？

8. 【推薦】可以使用warn日志级别来记录用户输入参数错误的情况，避免用户投诉时，无所适从。如非必要，请不要在此场景打出error级别，避免频繁报警。

   说明：注意日志输出的级别，error级别只记录系统逻辑出错、异常或者重要的错误信息。

9. 【推薦】尽量用英文来描述日志错误信息，如果日志中的错误信息用英文描述不清楚的话使用中文描述即可，否则容易产生歧义。国际化团队或海外部署的服务器由于字符集问题，使用全英文来注释和描述日志错误信息。

 

### 三、单元测试

1. 【强制】好的单元测试必须遵守AIR原则。 

   说明：单元测试在线上运行时，感觉像空气（AIR）一样并不存在，但在测试质量的保障上，却是非常关键的。好的单元测试宏观上来说，具有自动化、独立性、可重复执行的特点。 

- - A：Automatic（自动化）
  - I：Independent（独立性）
  - R：Repeatable（可重复）

2. 【强制】单元测试应该是全自动执行的，并且非交互式的。测试用例通常是被定期执行的，执行过程必须完全自动化才有意义。输出结果需要人工检查的测试不是一个好的单元测试。单元测试中不准使用System.out来进行人肉验证，必须使用assert来验证。 

3. 【强制】保持单元测试的独立性。为了保证单元测试稳定可靠且便于维护，单元测试用例之间决不能互相调用，也不能依赖执行的先后次序。

   反例：method2需要依赖method1的执行，将执行结果作为method2的输入。 

4. 【强制】单元测试是可以重复执行的，不能受到外界环境的影响。 

   说明：单元测试通常会被放到持续集成中，每次有代码check in时单元测试都会被执行。如果单测对外部环境（网络、服务、中间件等）有依赖，容易导致持续集成机制的不可用。 

   正例：为了不受外界环境影响，要求设计代码时就把SUT的依赖改成注入，在测试时用spring这样的DI框架注入一个本地（内存）实现或者Mock实现。 

5. 【强制】对于单元测试，要保证测试粒度足够小，有助于精确定位问题。单测粒度至多是类级别，一般是方法级别。

   说明：只有测试粒度小才能在出错时尽快定位到出错位置。单测不负责检查跨类或者跨系统的交互逻辑，那是集成测试的领域。 

6. 【强制】核心业务、核心应用、核心模块的增量代码确保单元测试通过。 

   说明：新增代码及时补充单元测试，如果新增代码影响了原有单元测试，请及时修正。 

7. 【强制】单元测试代码必须写在如下工程目录：src/test/java，不允许写在业务代码目录下。 

   说明：源码构建时会跳过此目录，而单元测试框架默认是扫描此目录。 

8. 【推薦】单元测试的基本目标：语句覆盖率达到70%；核心模块的语句覆盖率和分支覆盖率都要达到100% 

   说明：在工程规约的应用分层中提到的DAO层，Manager层，可重用度高的Service，都应该进行单元测试。 

9. 【推薦】编写单元测试代码遵守BCDE原则，以保证被测试模块的交付质量。 

- - B：Border，边界值测试，包括循环边界、特殊取值、特殊时间点、数据顺序等。
  - C：Correct，正确的输入，并得到预期的结果。
  - D：Design，与设计文档相结合，来编写单元测试。
  - E：Error，强制错误信息输入（如：非法数据、异常流程、非业务允许输入等），并得到预期的结果。

10. 【推薦】对于数据库相关的查询，更新，删除等操作，不能假设数据库里的数据是存在的，或者直接操作数据库把数据插入进去，请使用程序插入或者导入数据的方式来准备数据。

    反例：删除某一行数据的单元测试，在数据库中，先直接手动增加一行作为删除目标，但是这一行新增数据并不符合业务插入规则，导致测试结果异常。 

11. 【推薦】和数据库相关的单元测试，可以设定自动回滚机制，不给数据库造成脏数据。或者对单元测试产生的数据有明确的前后缀标识。

    正例：在RDC内部单元测试中，使用RDC_UNIT_TEST_的前缀标识数据。 

12. 【推薦】对于不可测的代码建议做必要的重构，使代码变得可测，避免为了达到测试要求而书写不规范测试代码。

13. 【推薦】在设计评审阶段，开发人员需要和测试人员一起确定单元测试范围，单元测试最好覆盖所有测试用 例。

14. 【推薦】单元测试作为一种质量保障手段，不建议项目发布后补充单元测试用例，建议在项目提测前完成单元测试。

15. 【參考】为了更方便地进行单元测试，业务代码应避免以下情况： 

- - 构造方法中做的事情过多。
  - 存在过多的全局变量和静态方法。
  - 存在过多的外部依赖。
  - 存在过多的条件语句。说明：多层条件语句建议使用卫语句、策略模式、状态模式等方式重构。

16. 【參考】不要对单元测试存在如下误解： 

- - 那是测试同学干的事情。本文是开发手册，凡是本文内容都是与开发同学强相关的。
  - 单元测试代码是多余的。系统的整体功能与各单元部件的测试正常与否是强相关的。
  - 单元测试代码不需要维护。一年半载后，那么单元测试几乎处于废弃状态。
  - 单元测试与线上故障没有辩证关系。好的单元测试能够最大限度地规避线上障碍。



### 四、安全规约

1. 【强制】隶属于用户个人的页面或者功能必须进行权限控制校验。

   说明：防止没有做水平权限校验就可随意访问、修改、删除别人的数据，比如查看他人的私信内容、修改他人的订单。 

2. 【强制】用户敏感数据禁止直接展示，必须对展示数据进行脱敏。

   说明：中国大陆个人手机号码显示为:158****9119，隐藏中间4位，防止隐私泄露。 

3. 【强制】用户输入的SQL参数严格使用参数绑定或者METADATA字段值限定，防止SQL注入，禁止字符串拼接SQL访问数据库。 

4. 【强制】用户请求传入的任何参数必须做有效性验证。 

   说明：忽略参数校验可能导致： 

- - - page size过大导致内存溢出 
    - 恶意order by导致数据库慢查询 
    - 任意重定向 
    - SQL注入 
    - 反序列化注入 
    - 正则输入源串拒绝服务ReDoS 

  - 说明：Java代码用正则来验证客户端的输入，有些正则写法验证普通用户输入没有问题，但是如果攻击人员使用的是特殊构造的字符串来验证，有可能导致死循环的结果。

5. 【强制】禁止向HTML页面输出未经安全过滤或未正确转义的用户数据。 

6. 【强制】表单、AJAX提交必须执行CSRF安全验证。 

   说明：CSRF(Cross-site request forgery)跨站请求伪造是一类常见编程漏洞。对于存在CSRF漏洞的应用/网站，攻击者可以事先构造好URL，只要受害者用户一访问，后台便在用户不知情的情况下对数据库中用户参数进行相应修改。 

7. 【强制】在使用平台资源，譬如短信、邮件、电话、下单、支付，必须实现正确的防重放的机制，如数量限制、疲劳度控制、验证码校验，避免被滥刷而导致资损。

   说明：如注册时发送验证码到手机，如果没有限制次数和频率，那么可以利用此功能骚扰到其它用户，并造成短信平台资源浪费。 

8. 【推薦】发贴、评论、发送即时消息等用户生成内容的场景必须实现防刷、文本内容违禁词过滤等风控策略。 