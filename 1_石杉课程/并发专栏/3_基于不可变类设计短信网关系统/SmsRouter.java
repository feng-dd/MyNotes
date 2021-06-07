import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Murphy
 * @Version 1.0
 * @Date 2021/6/4 10:53
 * @Desc 短信服务商路由
 *          每次发短信时，需要读取类中的smsInfoRouterMap获取当前优先级较高的短信服务商路由，再去发送短信。
 *          而这个数据查多写少，数据又较为常用，所以系统启动就从数据库加载到内存，方便使用。
 * @Since 1.0
 */
public class SmsRouter {

    /**
     * 因为这个类型要在系统启动时，初始化所以内部自己实例化。
     * volatile保证多线程下的可见性
     */
    private static volatile SmsRouter instance = new SmsRouter();

    /**
     * 短信服务商的优先级映射
     */
    private final Map<Integer, SmsInfo> smsInfoRouterMap;

    /**
     * 初始短信网关路由信息
     */
    public SmsRouter(){
        // 对象初始化时会对final
        this.smsInfoRouterMap = this.LoadSmsInfoRouterMapFromDB();
    }

    public static SmsRouter getInstance(){
        return instance;
    }

    // 短信服务厂商路由变更后，整个替换
    public static void setInstance(SmsRouter newInstance){
        instance = newInstance;
    }

    /**
     * 获取短信服务商列表
     * @return
     */
    public Map<Integer, SmsInfo> getSmsInfoRouterMap() {
//        return smsInfoRouterMap;
        // 防御性复制，外面获取并改变返回的Map,对本身的Map也不会影响
        return Collections.unmodifiableMap(deepCopy(smsInfoRouterMap));
    }

    /**
     * 将DB中的短信服务商数据放入内存中
     * @return
     */
    private Map<Integer, SmsInfo> LoadSmsInfoRouterMapFromDB(){
        // 模拟从DB获取数据放入Map
        Map<Integer, SmsInfo> smsInfoMap = new HashMap<>();
        smsInfoMap.put(1, new SmsInfo("https://www.aliyun.com", 180L));
        smsInfoMap.put(2, new SmsInfo("https://www.aliyun.com", 181L));
        smsInfoMap.put(3, new SmsInfo("https://www.aliyun.com", 182L));
        return smsInfoMap;
    }

    private Map<Integer, SmsInfo> deepCopy(Map<Integer, SmsInfo> smsInfoRouterMap){
        Map<Integer, SmsInfo> result = new HashMap<>();
//        smsInfoRouterMap.entrySet()
//                .forEach(entry ->
//                        result.put(entry.getKey(), entry.getValue())
//                );
        smsInfoRouterMap.forEach((k,v) -> result.put(k, v));
        return result;
    }

    /**
     * 更新短信服务商路由
     * 多线程下会存在：线程A setUrl后，线程B读取smsInfoRouterMap.get(3)，此时数据是错误的。
     */
    public void changeRoute(){
//        Map<Integer, SmsInfo> jGSmsInfo = instance.getSmsInfoRouterMap();
//        SmsInfo smsInfo = smsInfoRouterMap.get(3);
//        smsInfo.setUrl("https://www.jiguang.com");
//        smsInfo.setMaxSizeInBytes(184L);

        // 1.更新数据库中的短信服务商列表
        updateSmsRouteInfoLists();
        // 2.更新内存中的短信服务商列表
        SmsRouter.setInstance(new SmsRouter());
    }

    private void updateSmsRouteInfoLists() {
        // todo ...
    }

}
