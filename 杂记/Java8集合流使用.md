# `Java 8`

```java
List<> list = medicalInstitutionPageResponse.getRecord().stream().map(InstitutionConvert.INSTANCE :: convert2).collect(Collectors.toList());

// list转map			collect(Collectors.groupingBy(WeChatDTO::getInstitutionId, Collectors.counting()))
Map<Integer, Long> map = weChatDTOList.stream().filter(e -> e.getInstitutionId() != null).collect(Collectors.groupingBy(WeChatDTO::getInstitutionId, Collectors.counting()));
```

## 集合流的使用

> 获取集合流的方式 
>
> ```java
> // 1.集合.stream()
> List<String> list = new ArrayList<>();
> list.add("java");
> list.add("c");
> // 传统方式,输出 java
> for(String s : list){
>     if(null != s && s.length() == 4){
>         System.out.println(s);
>     }
> }
> // 流式
> Stream<String> listStream = list.stream();
> listStream.filter(s -> null != s).filter(s-> s.length() == 4).forEach(System.out::println);
> 
> // 2.Map 不是 Conllection,需要间接获取
> Map<String,Integer> map = new HashMap<>();
> Stream<String> keysStream = map.keySet().stream();
> Stream<Integer> valuesStream = map.values().stream();
> Stream<Map.Entry<String,Integer>> entrysStream = map.entrySet().stream();
> 
> // 3.Array.stream(array) 或 Stream.of(array)
> String[] array = {"java","c"};
> Stream<String> arrayStream1 = Arrays.stream(array);
> Stream<String> arrayStream2 = Stream.of(array);
> ```
>
> 常用方法
>
> ```java
> List<String> list1 = new ArrayList<>();
> list1.add("莫问");
> list1.add("逝清雪");
> list1.add("王母娘娘");
> list1.add("玉皇大帝");
> list1.add("吉泽明步");
> 
> // filter() 过滤，结果：莫问
> list1.stream().filter(s-> null != s).filter(s -> s.length() == 2).forEach(System.out::println);
> 
> // count() 统计，结果：5
> System.out.println(list1.stream().count());
> 
> // limit() 截取前n个元素，结果：莫问 逝清雪
> list1.stream().limit(2).forEach(System.out::println);
> 
> // skip() 去除前n个元素，结果：王母娘娘 玉皇大帝 吉泽明步
> list1.stream.skip(2).forEach(System.out::println);
> 
> // concat() 合并，结果：莫问 逝清雪 王母娘娘 玉皇大帝 吉泽明步 苍井空 宫崎骏
> List<String> list2 = new ArrayList<>();
> list2.add("苍井空");
> list2.add("宫崎骏");
> Stream.concat(list1.stream(),list2.stream()).froEach(System.out::println);
> ```
>
> 流转换为集合
>
> ```java
> List<String> list = new ArrayList<>();
> Stream<String> listStream = list.stream();
> // 转List	stream.collect(Collectors.toList())
> List<String> stream2list = listStream.collect(Collectors.toList())
> 
> // 转Set		stream.collect(Collectors.toSet())
> Set<String> stream2Set = listStream.collect(Collectors.toSet())
> 
> // 转数组		stream.toArray()、或者stream.toArray(泛型)，推荐第二种，因为可以直接指定转换后数组的泛型
> String[] array = {"java","c"};
> Stream<String> arrayStream = Arrays.stream(array);
> String[] stream2array = arrayStream.toArray(String[]::new)
> ```
>
> 获取并发流
>
> ```java
> List<String> list = new ArrayList<>();
> // 间接获取
> Stream<String> stream1 = list.stream().parallel();
> // 直接获取
> Stream<String> stream2 = list.parallelStream()
> ```

## Predicate 函数

```java
// 过滤条件
Predicate<MedicalInstitutionDetailResponse> predicate = a -> Boolean.TRUE;
if (StringUtils.isNotEmpty(search)) {
    predicate.and(e -> (e.getMedicalInstitutionSimpleCode().contains(search) || e.getMedicalInstitutionName().contains(search)));
}
// predicate 中未添加过滤条件
```

> `test(value)`方法：返回`boolean`，判断`value`是否符合`test()`方法的逻辑。
>
> `and(predicate)`方法：返回`Predicate `，相当于短路与`(&&)`，一般配合`test()`使用。
>
> `negate()`方法：返回`Predicate `，相当于逻辑非`(!)`，一般配合`test()`使用。
>
> `or()`方法：返回`Predicate `，相当于短路或`(||)`，一般配合`test()`使用。

```java
public class PredicateTest {
   	/**
     * - 1.判断传入的字符串的长度是否大于5
     * @param judgeString 待判断字符串
     * @return
     */
    public boolean stringLength(String str) {
        return str.length() > 5 ? true:false;
    }

    /**
     *  - 2.判断传入的参数是否是奇数
     * @param num        待判断的数字
     * @return               1 代表偶数， 0代表奇数
     */
    public int numbersOdds(int num) {
        return num % 2 == 0 ? 1 : 0;
    }

    /**
     * - 3.判断数字是否大于10
     * @param num       待判断的数字
     * @return               1. 代表大于10 ， 0 代表小于10
     */
    public int specialNumbers(int num) {
        return num > 10 ? 1 : 0;
    }

    public static void main(String[] args) {

        PredicateTest predicateTest = new PredicateTest();
        // 单条件
        // 传统判断方式
        System.out.println(predicateTest.stringLength("hello"));
        System.out.println(predicateTest.numbersOdds(4));
        System.out.println(predicateTest.specialNumbers(-1));

		// Java 8 方式
		System.out.println(predicateTest.judgment("hello",val -> String.valuOf(val).length() > 5));
		System.out.println(predicateTest.judgment(4,val -> val % 2 == 0);
		System.out.println(predicateTest.judgment(-1,val -> val > 10);
                           
         // 多条件
         System.out.println(predicateAND.testAndMethod("zhangsan", str1 -> str1.equals("zhangsan"), str2 -> str2.length() > 5));
    }
    
    public boolean judgment(int value,Predicate<Integer> predicate) {
        return predicate.test(value);
    }
                           
    /**
     * @param stringOne         待判断的字符串
     * @param predicateOne      断定表达式1
     * @param predicateTwo      断定表达式2
     * @return                    是否满足两个条件
     */
    public boolean testAndMethod(String str, Predicate<String> predicateOne,Predicate<String> predicateTwo) {
        return predicateOne.and(predicateTwo).test(str);
    }
}
```

# 对象流转

`request、response`	前后端交互的对象
`DTO` 		`Manager`接收的对象
`BO` 		`Manager`返回的对象

`Service`的增删改方法需要添加日志

```java
String operateMemo = OperateLogTool.add().add(insertDO).build(String.format("创建科室[%d]", insertDO.getDepartmentId()), null);
// IPlatformOperateLogService 日志服务
platformOperateLogService.addOperationLog(OperateLogConstants.BRANCH_MANAGE,OperateLogConstants.CREATE_DEPARTMENT,insertDO.getDepartmentId(), operateMemo,createDTO.getOperationStaffId());
```

```java
// 获取当前用户ID
SystemContextHolder.getAccountContext().getUid().intValue()
```

# 数据库配置

`dao`层的配置文件中配置，如：`medcloud-institution\institution-service\institution-dao\src\main\resources\dao-dev.yaml`

# 项目启动问题

启动项目前，先从远程仓库对项目进行更新，在对`Maven`依赖更新，再启动项目。

当项目启动时报无法连接`Redis、MongoDB`时，检查`VPN`是否未链接。

# 接口开发

`xx-api`：`com.xx.xx.api.dubbo.service.a`

`interface`：`IAService`

```java
package com.xx.xx.api.dubbo.service.a;
import ...

/**
 * @author xxxxxx
 * @version 1.0
 * @date 2020/7/2 3:07 下午
 * @desc XX
 * @see
 * @since 1.0
 */
public interface IAService {
    /**
     * XXXXX
     * @param param
     * @return
     */
    Boolean a(String param);
}
```

`xx-service`：`com.xx.xx.api`

`class`：`AServiceImpl`

```java
package com.xx.xx.api;
import ...

/**
 * @author xxxxxx
 * @version 1.0
 * @date 2020/7/2 3:07 下午
 * @desc XX
 * @see
 * @since 1.0
 */
@Service
public class AServiceImpl implements IAService {

    @Autowired
    private BService bService;

    /**
     * XXXXX
     * @param param
     * @return
     */
    @Override
    public Boolean a(String param) {
        return bService.b(param);
    }
}
```

`xx-service`：`com.xx.xx.service.xx`

`class`：`BService`

```java
package com.xx.xx.service.xx;
import ...

/**
 * xxservice
 * Author: xxxxx
 * Date:  2019/1/14
 */
@Service
public class BService{
    private static final Logger LOGGER = LoggerFactory.getLogger(BService.class);
    
    /**
     * 通知短信发送
     *
     * @param mobile  手机号
     * @param content 发送内容
     * @param params  参数
     * @return
     */
    public boolean sendSms(String param) {
        ...
        return Boolean.TRUE;
    }
}
```

在当前服务配置服务提供者 `dubbo provider 配置`：`xx-rest > resource > dubbo-provider.xml`

```xml
<dubbo:service interface="com.xx.xx.api.dubbo.service.a.IAService" class="com.xx.xx.api.AServiceImpl"/>
```

在消费端配置服务消费者`dubbo consumer`配置：`xx-rpc > resource > dubbo-consumer.xml`

```xml
<dubbo:reference id="aService" interface="com.xx.xx.api.dubbo.service.xx.IAService"/>
```

> `PS`：短信接口可以参照`SCRM`的`ISendShortMessageService`