## Promise 模式

> Promise 是一种异步的编程模式；
>
> 执行任务A时，会返回一个Promise对象，同时执行任务B，若任务B需要任务A执行的结果，可以从Promise对象中获取，而无需从等待任务A执行完毕再去执行任务B。

##### 烧水泡茶案例

###### 同步执行

![image-20210628104058272](https://i.loli.net/2021/06/28/OxHjJFkXft4Uh1e.png)

烧水到水开：5min

洗茶杯：1min

备茶叶：1min

泡茶：1min

`总耗时：8min`

###### 异步执行

![image-20210628105145136](https://i.loli.net/2021/06/28/jiYVCRKmrIAWM4q.png)

烧水到水开（并行）：5min

洗茶杯（并行）：1min

备茶叶（并行）：1min

泡茶：1min

`总耗时：6min`



**异步执行的方式相较于同步执行的方式要更加高效**

### 模式构成

> 四个角色：
>
> 1. Promisor：create()代表开启整个任务的执行，创建异步执行器Executor，创建并返回结果对象Promise；
> 2. Executor：Promisor.create()中创建了异步执行器，去异步执行任务B，可理解为执行洗茶杯、备茶叶、泡茶这条任务的异步执行器；
> 3. Promise： Promisor.create()返回Promise对象，相当于水开后的蜂鸣声；
> 4. result：promise.get() 获取到执行的结果，可理解为水开了这个结果。

![image-20210628111357668](https://i.loli.net/2021/06/28/ECYc2hkn1HloM9a.png)

![image-20210628113121088](https://i.loli.net/2021/06/28/xmWuXI7DlATwGhj.png)