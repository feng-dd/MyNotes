import java.util.Map;

/**
 * @Author Murphy
 * @Version 1.0
 * @Date 2021/6/4 15:44
 * @Desc 发送短信的场景
 * @Since 1.0
 */
public class SendShortMsgDemo {

    public static void main(String[] args) {
        SmsRouter smsRouter = SmsRouter.getInstance();
        Map<Integer, SmsInfo> smsInfoRouterMap = smsRouter.getSmsInfoRouterMap();
        smsInfoRouterMap.forEach((k,v) -> System.out.print("k:"+ k + ",value:" + v));
        SmsRouter newSmsRouter = SmsRouter.getInstance();
        SmsRouter.setInstance(newSmsRouter);
    }
}