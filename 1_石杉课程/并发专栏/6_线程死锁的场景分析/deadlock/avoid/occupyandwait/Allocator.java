package deadlock.avoid.occupyandwait;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Murphy
 * @Version 1.0
 * @Date 2021/6/9 17:02
 * @Desc
 * @Since 1.0
 */
public class Allocator {

    private static Allocator INSTANCE = new Allocator();

    public static Allocator getInstance(){
        return INSTANCE;
    }

    private List<Object> als = new ArrayList<>();

    // 一次性申请所有资源
    boolean apply(Object from, Object to) {
        synchronized (this) {
            if (als.contains(from) || als.contains(to)) {
                return false;
            } else {
                als.add(from);
                als.add(to);
            }
            return true;
        }
    }

    // 释放资源
    void clean(Object from, Object to){
        synchronized (this) {
            als.remove(from);
            als.remove(to);
        }
    }
}
