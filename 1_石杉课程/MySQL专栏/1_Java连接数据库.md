# Java连接数据库

## 一、MySQL驱动

一般Java程序访问MySQL，需要依赖MySQL的驱动。

```xml
<dependency>
    <groupId>MySQL</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.46</version>
</dependency>
```

### 1、单线程场景

> Java程序通过**MySQL驱动**与MySQL数据库建立网络连接用来执行SQL语句。

![image-20210601211856836](https://i.loli.net/2021/06/01/XEfayZASjug2onl.png)

### 二、并发场景

当Java程序是多线程时，会通过**数据库连接（常见的有DBCP,C3P0,Druid）**池访问MySQL数据库。

![image-20210601212320173](https://i.loli.net/2021/06/01/CAh3eqzY7OT89lp.png)

#### 演变过程

>  假设Java程序部署在Tomcat服务器上，多个用户发送请求，Tomcat并发处理。

1. 多个请求竞争一个数据库连接，效率极低。
2. 每个请求创建一个数据库连接，使用完销毁；在高并发情况下，频繁创建和销毁数据库连接，耗用大量时间，效率极低。
3. 使用数据库连接池，池内保持一定数量的数据库连接，每个请求使用池内不同的连接，使用完不销毁，而是放回池中，等待其他请求线程使用。

## 二、MySQL架构——连接池

> 一个系统可以连接多个数据库，同样的，多个系统也可以连接同一个数据库；MySQL架构中的第一个环节，就是**连接池**：
>
> 1. 维护了与系统间的多个数据库连接；
> 2. 账号密码验证；
> 3. 库表权限验证。

<img src="https://i.loli.net/2021/06/01/aTschNyuM574LRz.png" alt="image-20210601213318140" style="zoom:150%;" />

## 三、Java与MySQL建立连接的方式

### 1、JDBC

```java
Connection conn = null;
Statement stmt = null;
try {
    // 1.注册 JDBC 驱动
    Class. forName ( "com.mysql.jdbc.Driver");
    // 2.获取连接
    conn = DriverManager. getConnection ( DB_URL , USER , PASSWORD );
    // 3.执行查询
    stmt = conn.createStatement();
    String sql= T "SELECT bid,name,author_id FROM blog";
    ResultSet rs = stmt.executeQuery(sql);
    // 4.处理结果集
    while(rs.next()){
        int bid = rs.getInt( "bid");
        String name = rs.getString( "name");
        String authorId = rs.getString( "author_id");
    }
} catch (SQLException se) {
    // 处理 JDBC 错误
    se.printStackTrace();
} catch(Exception e) {
   // 处理 Class.forName 错误
   e.printStackTrace();
} finally {
   // 5.完成后关闭
    if(rs!=null) rs.close();
    if(stmt!=null) stmt.close();
    if(conn!=null) conn.close();
}
```

1. PrepareStatement：代表执行预编译的SQL语句。使用setter()传入查询变量，自动对特殊字符进行转义，避免SQL注入攻击，可以执行多次。

2. Statement：执行静态sql语句。
3. execute()：结果为true(查询到结果)通过getResultSet()获取结果集，为flase(插入/修改)getUpdateCount()获取更新条数。
4. executeQuery()：查询语句(DDL)，返回ResultSet。
5. executeUpdate()：插入/删除/修改(DML)，返回值int型。当放入DDL语句时，返回值0，放入DML时返回值是更新的条数。

### 2、连接池

> 手写简易数据库连接池

```java
public class MyDataSource {
    private static MyDataSource instance = new MyDataSource();
    private static LinkedList<Connection> pool;
    /** 驱动 */
    private Connection conn = null;
    /** 最大连接数 */
    public static final int MAX = 10;
    /** 最小连接数 */
    public static final int MIN = 5;
    /** 驱动 */
    public static final String DRIVERNAME = "com.mysql.jdbc.Driver";
    /** 数据库地址 */
    public static String URL = "jdbc:mysql://localhost::3306/test_db";
    /** 账户 */
    public static String UID = "root";
    /** 密码 */
    public static String PWD = "root";

    private MyDataSource() {
        try {
            Class.forName(DRIVERNAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
        pool = new LinkedList<>();
        // 初始化连接池
        for (int i = 0; i <= MIN; i++) {
            try {
                conn = DriverManager.getConnection(URL, UID, PWD);
                pool.add(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取单例对象
     */
    public static MyDataSource getInstance() {
        return instance;
    }

    /**
     * 获取连接
     */
    public synchronized Connection getConnection() {
        if (pool.size() > 0)
            // 链表删除第一个节点,并返回该节点中的连接对象;
            return pool.removeFirst();
        else
            return null;
    }

    /**
     * 回收连接
     */
    public synchronized void releaseConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                if (pool.size() < MAX)
                    pool.addFirst(conn);
                else
                    conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

// 测试
public static void main(String[] args){
    MyDataSource instance = MyDataSource.getInstance();
    Connection conn = instance.getConnection();
    try {
        Statement statement = conn.createStatement();
        ResultSet result =state.executeQuery("select * from sys_user");  
	while(result.next()){  
	   System.out.println(result.getString("name"));  
	}   
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
	if (result != null) result.close();  
	if (state != null) state.close();  
        if (conn != null) instance.releaseConnection(conn);
    }

}
```

### 3、多线程并发操作数据库

> 多个线程共享Connection对象，是不安全的，因为可以利用Java中的ThreadLocal为每个线程保存一个Connection对象

```java
public class ConnnectionManager {
 
    private static final ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>();
     
    private static final String BETADBURL = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf8&autoReconnect=true&user=root&password=root";
    
    public static Connection getConnectionFromThreadLocal() {
        Connection conn = connectionHolder.get();
        try {
            if (conn == null || conn.isClosed()) {
                Connection con = ConnnectionManager.getConnection();
                connectionHolder.set(con);
                System.out.println("[Thread]" + Thread.currentThread().getName());
                return con;
            }
            return conn;
        } catch (Exception e) {
            System.out.println("[ThreadLocal Get Connection Error]" + e.getMessage());
        }
        return null;
    }
     
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection(BETADBURL);
        } catch (Exception e) {
            System.out.println("[Get Connection Error]" + e.getMessage());
        }
        return conn;
    }
}

// 数据修改线程
public class DataUpdater implements Runnable {
 
    private PreparedStatement pst;
    private List<UserProfileItem> userProfiles;
    private final String SQL = "insert into userprofile (`uid` ,`profile` , `logday`) VALUES (?, ? ,?) ON DUPLICATE KEY UPDATE `profile`= ? ";
     
    public DataUpdater(List<UserProfileItem> userProfiles) {
        this.userProfiles = userProfiles;
    }
     
    public void run() {
        try {
            pst = ConnnectionManager.getConnectionFromThreadLocal().prepareStatement(SQL);
            for (UserProfileItem userProfile : userProfiles) {
                if(userProfile.getUid() != null && !userProfile.getUid().isEmpty() &&
                        userProfile.getProfile() != null && !userProfile.getProfile().isEmpty()) {
                    pst.setString(1, userProfile.getUid());
                    pst.setString(2, userProfile.getProfile());
                    pst.setInt(3, userProfile.getLogday());
                    pst.setString(4, userProfile.getProfile());
                    pst.addBatch();
                }
            }
            pst.executeBatch();
        } catch (Exception e) {
            System.err.println("[SQL ERROR MESSAGE]" + e.getMessage());
        } finally {
             close(pst);
        }
    }
 
    public void close(PreparedStatement pst) {
        if (pst != null) {
            try {
                pst.close();
            } catch (SQLException e) {
                System.err.println("[Close Statement Error]" + e.getMessage());
            }
        }
    }
}

// 
public class DataUpdaterMain {
     
    private LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
    private QunarThreadPoolExecutor qunarThreadPoolExecutor = new QunarThreadPoolExecutor(5, 8, 5, TimeUnit.MINUTES, queue);
     
    public void shutThreadPool(ThreadPoolExecutor executor) {
        if (executor != null) {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(20 , TimeUnit.MINUTES)) {
                    executor.shutdownNow();
                }
            } catch (Exception e) {
                System.err.println("[ThreadPool Close Error]" + e.getMessage());
            }
        }
    }
     
    public void close(Reader reader) {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                System.err.println("[Close Io Error]" + e.getMessage());
            }
        }
    }
     
    public void closeConnection(Connection conn , Statement st) {
        try {
            if (conn != null) {
                conn.close();
            }
            if (st != null) {
                conn.close();
            }
        } catch (Exception e) {
            System.err.println("[Close MySQL Error]" + e.getMessage());
        }
    }
     
    public boolean update(String file ,int logday) {
        long start = System.currentTimeMillis();
        BufferedReader br = null;
        int num = 0;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line = null;
            List<UserProfileItem> userProfiles = new LinkedList<UserProfileItem>();
            while ((line = br.readLine()) != null) {
                ++num;
                String []items = line.split("\t");
                if (items.length == 2) {
                    String uid = items[0];
                    String profile = items[1];
                    userProfiles.add(new UserProfileItem(uid, profile, logday));
                    if (userProfiles.size() >= 100) {
                        qunarThreadPoolExecutor.execute(new DataUpdater(userProfiles));
                        userProfiles = new LinkedList<UserProfileItem>();
                    }
                } else {
                    System.err.println("[Data Error]" + line);
                }
            }
            qunarThreadPoolExecutor.execute(new DataUpdater(userProfiles));;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("[Read File Error]" + e.getMessage());
            return false;
        }  finally {
            System.err.println("[Update] take time " + (System.currentTimeMillis() - start) + ".ms");
            System.err.println("[Update] update item " + num);
            shutThreadPool(qunarThreadPoolExecutor);;
            close(br);
        }
        return true;
    }
     
    public static void main(String []args) {
        String file = "D:\\workspaces\\promotionwordData.log";
        int logday = Integer.parseInt("20150606");
        DataUpdaterMain dataUpdaterMain = new DataUpdaterMain();
        dataUpdaterMain.update(file, logday);
    }
}
```

