package deadlock.avoid.occupyandwait;

/**
 * @Author Murphy
 * @Version 1.0
 * @Date 2021/6/9 16:31
 * @Desc 死锁
 * @Since 1.0
 */
public class AvoidDeadLock {

    public static void main(String[] args) {
        Account a = new Account();
        Account b = new Account();
        a.transfer(b,100);
        b.transfer(a,200);
    }

}

