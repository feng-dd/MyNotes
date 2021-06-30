## Promise 模式

> Promise 是一种异步的编程模式；
>
> 执行任务A时，会返回一个Promise对象，同时执行任务B，若任务B需要任务A执行的结果，可以从Promise对象中获取，而无需从等待任务A执行完毕再去执行任务B。

##### 烧水泡茶案例

###### 同步执行

![image-20210630140947681](https://i.loli.net/2021/06/30/6Vv1ELCUZtY8Fbg.png)

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
> 1. Promisor：异步执行的角色，内部包含一个异步执行器 Executor，去异步处理任务，返回 Promise 对象；
> 2. Executor：异步执行器，处理异步任务；
> 3. Promise：包含了异步执行任务返回结果的对象；
> 4. result：异步任务执行的结果，从 Promise 对象中获取。

![image-20210630141023747](https://i.loli.net/2021/06/30/8Lxfyj2XsieChOr.png)

![image-20210628113121088](https://i.loli.net/2021/06/28/xmWuXI7DlATwGhj.png)

### 云盘上传数据案例

![image-20210630140731695](https://i.loli.net/2021/06/30/nE1VcsmjJYp7t3q.png)

