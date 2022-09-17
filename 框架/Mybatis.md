# `JDBC`

```java
// 注册 JDBC 驱动
Class. forName ( "com.mysql.jdbc.Driver");
// 打开连接
conn = DriverManager. getConnection ( DB_URL , USER , PASSWORD );
// 执行查询
stmt = conn.createStatement();
String sql= T "SELECT , bid, , name, d author_id M FROM blog";
ResultSet rs = stmt.executeQuery(sql);
// 获取结果集并处理
while(rs.next()){
    int bid = rs.getInt( "bid");
    String name = rs.getString( "name");
    String authorId = rs.getString( "author_id");
}
```

1. 在 `maven` 中引入 `MySQL` 驱动的依赖（`JDBC` 的包在 `java.sql`中），注册驱动。
2. 通过`DriverManager`获取一个`Connection`，参数里面填数据库地址，用户名和密码。
3. 通过 Connection 创建一个 Statement 对象。
4. 通过 Statement 的 execute()方法执行 `SQL`。当然 Statement 上面定义了非常多的方法。execute()方法返回一个`ResultSet`对象，我们把它叫做结果集。
5. 通过`ResultSet`获取数据。转换成一个`POJO`对象。
6. 要关闭数据库相关的资源，包括`ResultSet、Statement、Connection`，它们的关闭顺序和打开的顺序正好是相反的。

> 代码冗余：在每一个对数据库进行操作的的方法中，都需要类似这样一段代码；
>
> 安全问题：需要自己去管理数据库的连接资源，如果忘记写`close()`了，就可能会造成数据库服务连接耗尽。
>
> 耦合：处理业务逻辑和处理数据的代码耦合。
>
> 结果集处理：要把 `ResultSet` 转换成 `POJO`的时候，必须根据字段属性的类型一个个地去处理。

`Spring JDBC`

>  对`JDBC`封装，提供了模板方法`JdbcTemplate`，简化数据库操作。

**相较于`JDBC`的优化**

1. 不需要关心资源管理的问题。
2. 减少对结果集的处理的重复代码。为实体创建一个映射类，实现`RowMapper`接口，重写`mapRow()`，对结果集进行处理。

```java
public class EmployeeRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet resultSet, t int i) s throws SQLException {
        Employee employee = w new Employee();
        employee.setEmpId(resultSet.getInt( "emp_id"));
        employee.setEmpName(resultSet.getString( "emp_name"));
        employee.setEmail(resultSet.getString( "emial"));
    	return employee;
    }
}
```

3. 在 `DAO` 层调用的时候传入自定义的 `RowMapper` 类，最终返回我们需要的类型。结果集和实体类类型的映射也是自动完成的。

   ```JAVA
   public List<Employee> query(String sql){
       // 传入一个数据源进行初始化，对数据库资源进行管理，不用手动创建和关闭。
   	new JdbcTemplate( new DruidDataSource());
       return jdbcTemplate.query(sql,new EmployeeRowMapper());
   }
   ```

**存在的问题**

	1. `sql`写死在代码中。
	2. 参数只能按固定位置的顺序传入（数组），它是通过占位符去替换的，不能自动映射。
	3. 没有查询缓存。

## `ORM`

> `Object Relational Mapping `对象与关系的映射，用来解决程序对象和关系型数据库的相互映射的问题。
>
> `O`：程序里面的对象
>
> `R`：映射
>
> `M`：关系型数据库

![ORM](https://i.loli.net/2021/04/08/IieSomJP9WFH6VG.png)

### `Hibernate`

1. 为实体类建立一些`hbm`的`xml`映射文件。

   ```xml
   <hibernate-mapping>
       <class name="cn.gupaoedu.vo.User" table="user">
       	<id name="id">
       		<generator class="native"/>
       	</id>
           <property name="password"/>
           <property name="cellphone"/>
           <property name="username"/>
       </class>
   </hibernate-mapping>
   ```

2. 通过`Hibernate`提供`（session）`的增删改查的方法来操作对象。

   ```java
   //创建对象
   User user = new User();
   user.setPassword("123456");
   user.setCellphone("18166669999");
   user.setUsername("qingshan");
   //获取加载配置管理类
   Configuration configuration = new Configuration();
   //不给参数就默认加载 hibernate.cfg.xml 文件，
   configuration.configure();
   //创建 Session 工厂对象
   SessionFactory factory = configuration.buildSessionFactory();
   //得到 Session 对象
   Session session = factory.openSession();
   //使用 Hibernate 操作数据库，都要开启事务,得到事务对象
   Transaction transaction = session.getTransaction();
   //开启事务
   transaction.begin();
   //把对象添加到数据库中
   session.save(user);
   //提交事务
   transaction.commit();
   //关闭 Session
   session.close();
   ```

   **相较于`JDBC`的优化**

   ​	操作数据库的数据，就像是操作对象。`Hibernate`的框架会自动生成`SQL`语句（可以屏蔽数据库的差异），自动进行映射。

   **在业务复杂的项目中存在的问题**

   	1. 使用get()、save() 、update()对象的这种方式，实际操作的是所有字段，没有办法指定部分字段，换句话说就是不够灵活。
   	2. 自动生成`SQL`的方式，优化困难。
   	3. 不支持动态`SQL`（比如分表中的表名变化，以及条件、参数）。

## `mybatis`

> 相对于`Hibernate`的全自动化来说，属于半自动化`ORM`框架，`SQL`和代码分离。
>
> 封装程度没有`Hibernate`高；不会自动生成全部的`SQL`语句；主要解决的是`SQL`和对象的映射问题。

```JAVA
public void testMapper() throws IOException {
    String resource = "mybatis-config.xml";
    // 读取 mybatis 全局配置文件
    InputStream inputStream = Resources. getResourceAsStream (resource);
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    // 工厂类根据全局配置文件创建 SqlSession 会话，类似于 JDBC 的 statement
    SqlSession session = sqlSessionFactory.openSession();
    try {
        BlogMapper mapper = session.getMapper(BlogMapper. class);
        Blog blog = mapper.selectBlogById(1);
        System. out .println(blog);
    } finally {
        session.close();
    }
}
```

#### 核心对象

	> `SqlSessionFactoryBuiler、SqlSessionFactory、SqlSession、Mapper`如果不用容器，什么时候创建和销毁这些对象?

**`SqlSessionFactoryBuiler`：**用来构建`SqlSessionFactory` ，而`SqlSessionFactory`只需要一个，所以只要构建了一个`SqlSessionFactory`，就没有存在的意义了。所以它的生命周期只存在于**方法的局部**。

**`SqlSessionFactory`：**是用来创建`SqlSession`，每次访问数据库都需要创建一个会话。因为我们一直有创建会话的需要，所以`SqlSessionFactory`应该存在于应用的整个生命周期中（**应用作用域**）。创建 `SqlSession`只需要一个实例，否则会产生混乱，浪费资源。所以我们要采用单例模式创建`SqlSessionFactory`。

**`SqlSession`：**`SqlSession`是一个会话，因为它不是线程安全的，不能在线程间共享。所以我们在请求开始的时候创建一个 `SqlSession`对象，在请求结束或者说方法执行完毕的时候要及时关闭它（**一次请求或者操作中**）。

**`Mapper`：**发送`SQL`来操作数据库的数据，应该在一个 `SqlSession`事务方法之内。`Mapper`（实际上是一个代理对象）是从`SqlSession`中获取的。

| 对象                        | 生命周期                     |
| --------------------------- | ---------------------------- |
| `SqlSessionFactoryBuiler`   | 方法局部（method）           |
| `SqlSessionFactory（单例）` | 应用级别（application）      |
| `SqlSession`                | 请求和操作（request/method） |
| `Mapper`                    | 方法（method）               |

#### 核心配置文件

> `mabatis-config.xml`

一级标签

```xml
<configuration>
    <properties resource="org/mybatis/example/config.properties">
          <property name="username" value="dev_user"/>
          <property name="password" value="F2Fa3!33TYyg"/>
    </properties>
    <!-- 设置好的属性可以在整个配置文件中用来替换需要动态配置的属性值 
        driver、url 从config.properties获取的 -->
    <dataSource type="POOLED">
          <property name="driver" value="${driver}"/>
          <property name="url" value="${url}"/>
          <property name="username" value="${username}"/>
          <property name="password" value="${password}"/>
    </dataSource>
    
    <typeAliases>
        <typeAlias alias="blog" type="com.gupaoedu.domain.Blog" />
    </typeAliases>
    <!-- 创建自定义的 -->
    <typeHandlers>
        <typeHandler handler="com.gupaoedu.type.MyTypeHandler"></typeHandler>
    </typeHandlers>
    
    <!-- 对象工厂 -->
	<objectFactory type="com.gupaoedu.objectfactory.GPObjectFactory">
        <property name="gupao" value="666"/>
    </objectFactory>
    
    <settings>
        <!-- 打印查询语句 -->
        <setting name="logImpl" value="STDOUT_LOGGING" />
        <!-- 控制全局缓存（二级缓存）-->
        <setting name="cacheEnabled" value="true"/>
        <!-- 延迟加载的全局开关。当开启时，所有关联对象都会延迟加载。默认 false  -->
        <setting name="lazyLoadingEnabled" value="true"/>
        <!-- 当开启时，任何方法的调用都会加载该对象的所有属性。默认 false，可通过select标签的fetchType来		      覆盖 -->
        <setting name="aggressiveLazyLoading" value="false"/>
        <!-- Mybatis 创建具有延迟加载能力的对象所用到的代理工具，默认JAVASSIST -->
        <!-- <setting name="proxyFactory" value="CGLIB" /> -->
        <!-- STATEMENT级别的缓存，使一级缓存，只针对当前执行的这一statement有效 -->
        <!-- <setting name="localCacheScope" value="STATEMENT"/> -->
        <setting name="localCacheScope" value="SESSION"/>
    </settings>
    
    <environments default ="development">
        <environment id ="development">
            <transactionManager type ="JDBC"/>
            <dataSource type ="POOLED">
                <property name" ="driver" value ="com.mysql.jdbc.Driver"/>
                <property name ="url" value ="jdbc:mysql://127.0.0.1:3306/gp-mybatis?useUnicode=true"/>
                <property name" ="username" value ="root"/>
                <property name" ="password" value ="123456"/>
            </dataSource>
    	</environment>
	</environments>
                                                           
    <mappers>
        <mapper resource="BlogMapper.xml"/>
    </mappers>
</configuration>
```

	1. `configuration`：根标签，对应`Mybatis`的配置类`Configuration`，贯穿`Mybatis`的整个执行流程。类中的属性，对应其它子标签。

 2. `properties`：可以引入`properties`文件，通过`${}`引用参数。

	3. `typeAliases`：类型别名。

	4. `typeHandlers`：类型转换。自定义类型转换规则，需要继承`BaseTypeHandler<T>`，并实现四个抽象方法。`Mybatis`以同样的方式定义了许多`TypeHandler`（`Type`包下）；然后注册到`TypeHandlerRegistry`中，也就是写在这个标签下；最后在需要使用该转换的`Mapper`映射文件的字段中指定`typeHandlers`。

    ![Mybatis_TypeHandler](https://i.loli.net/2021/04/08/pHFK15k8v3cltLO.png)

    | 从`Java`类型到`JDBC`类型            | 从`JDBC`类型到`Java`类型                                     |
    | :---------------------------------- | :----------------------------------------------------------- |
    | `setNonNullParameter`：设置非空参数 | `getNullableResult`：获取空结果集（根据列名），一般都是调用这个`getNullableResult`：获取空结果集（根据下标值）<br/>`getNullableResult`：存储过程用的 |

    ```xml-dtd
    <!-- JAVA -> JDBC -->
    <insert id" ="insertBlog" parameterType ="com.gupaoedu.domain.Blog">
        insert into blog (bid, name, author_id)
        values (#{bid,jdbcType=INTEGER},
        #{name,jdbcType=VARCHAR,typeHandler=com.gupaoedu.type.MyTypeHandler},
        #{authorId,jdbcType=INTEGER})
    </insert>
    <!-- JDBC -> JAVA  -->
    <result column" ="name" property" ="name" jdbcType ="VARCHAR" 		 typeHandler="com.gupaoedu.type.MyTypeHandler"/>
    ```

	5. `objectFactory`：通过结果集创建实体类对象，`Mybatis`中有默认的实现类 `DefaultObjectFactory`，创建对象的方法最终都调用了`instantiateClass()`，通过反射来实现；可以通过继承`DefaultObjectFactory`修改`objectFactory`初始化对象时的行为，例如将某属性+1；在这个标签上注册，创建对象时就会自动调用。

    | 方法                                                         | 作用                           |
    | ------------------------------------------------------------ | ------------------------------ |
    | `void setProperties(Properties properties)`                  | 设置参数时调用                 |
    | `<T> T create(Class<T> type)`                                | 创建对象（调用无参构造函数）   |
    | `<T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs)` | 创建对象（调用带参数构造函数） |
    | `<T> n boolean isCollection(Class<T> type)`                  | 判断是否集合                   |

	6. `environment`：一个标签表示一个数据源；这里面有两个关键的标签，一个是事务管理器`transactionManager`，一个是数据源`dataSource`。如果是`Spring + MyBatis`，则没有必要配置，`applicationContext.xml`里面配置数据源会覆盖 `MyBatis`的配置。

	7. `Mapper`：配置映射器，也就是`Mapper.xml`的路径，让`MyBatis`在启动的时候去扫描这些映射器，创建映射关系；四种指定 Mapper 文件的方式。

    1、使用相对于类路径的资源引用（`resource`）
    2、使用完全限定资源定位符（绝对路径）（`URL`）
    3、使用映射器接口实现类的完全限定类名
    4、将包内的映射器接口实现全部注册为映射器（最常用）

#### 缓存

一级缓存

> 默认开启；
>
> 同一个`SqlSession`，在参数和`SQL`完全相同的情况下，调用同一个`Mapper`方法，只执行一次；
>
> 因为`SqlSession`在第一次查询结束后，会将查询结果缓存，再次查询时，没有声明需要刷新且缓存未超时，`SqlSession`将从缓存中读取，而不会请求数据库。
>
> 缓存失效：
>
> 1. `MyBatis`在开启一个数据库会话时，会创建一个新的`SqlSession`对象，`SqlSession`对象中会有一个新的`Executor`对象，`Executor`对象中持有一个新的`PerpetualCache`对象；当会话结束时，`SqlSession`对象及其内部的`Executor`对象还有`PerpetualCache`对象也一并释放掉。
> 2. 如果`SqlSession`调用了`close()`方法，会释放掉一级缓存`PerpetualCache`对象，一级缓存将不可用。
> 3. 如果`SqlSession`调用了`clearCache()`，会清空`PerpetualCache`对象中的数据，但是该对象仍可使用。
> 4. `SqlSession`中执行了任何一个`update`操作`(update()、delete()、insert())` ，都会清空`PerpetualCache`对象的数据，但是该对象可以继续使用。

### 二级缓存

> `mapper`级别的缓存，多个`SqlSession`去操作同一个`Mapper`（不同`mapper`，相同`namespace`也是算是同一个`Mapper`）的`sql`语句，多个`SqlSession`可以共用二级缓存，二级缓存是跨`SqlSession`的。
>
> 映射语句文件中的所有`select`语句将会被缓存；
>
> 缓存失效：映射语句文件中的所有`insert、update`和`delete`语句都会刷新缓存。