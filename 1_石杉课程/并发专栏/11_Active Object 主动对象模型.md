# Active Object - 主动对象模型

> 主动对象模型通过对任务提交和任务执行的分离，简化异步任务调用方。

### 六大组件

![image-20210720160153284](https://i.loli.net/2021/07/20/3VDErvsfLgA9e5m.png)

1. `Proxy`

   > 调用方调用的对象，servant 的代理对象，提供了异步任务方法入口。

2. `Future`

   > 异步任务方法返回的调用方的结果。

3. `MethodRequest`

   > Proxy 只是提供了方法入口，Proxy 会封装请求上下文的信息，传递给 servant 或放入到 ActivationQueue 中。

4. `servant`

   > 实现 Proxy 异步任务业务逻辑的对象，接受 MethodRequest，并设置结果到Future。

5. `ActivationQueue`

   > 任务缓冲区，当任务请求（MethodRequest）过多，任务会添加到缓冲区，等待工作线程空闲

6. `scheduler`

   > 调度器，获取任务请求（MethodRequest），分配到工作线程。

