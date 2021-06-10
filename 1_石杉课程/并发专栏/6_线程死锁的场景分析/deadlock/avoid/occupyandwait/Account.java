package deadlock.avoid.occupyandwait;

/**
 * @Author Murphy
 * @Version 1.0
 * @Date 2021/6/9 17:01
 * @Desc 账户
 * @Since 1.0
 */
public class Account {

    private Allocator actr = Allocator.getInstance();

    private int balance;

    // 转账
    void transfer(Account target, int amt){
        while(!actr.apply(this, target));
        // 一次获取到所有资源成功，在并发很高的情况下，性能很差，可能一个线程需要N次才能申请到
        try{
            synchronized(this){
                System.out.println(this.toString()+" lock obj1");
                synchronized(target){
                    System.out.println(this.toString()+" lock obj2");
                    if (this.balance > amt){
                        this.balance -= amt;
                        target.balance += amt;
                    }
                }
            }
        } finally {
            //执行完后，再释放持有的资源
            actr.clean(this, target);
        }
    }
}
