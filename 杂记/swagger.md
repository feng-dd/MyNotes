## 注解说明
- @Api
    > 用在请求的类上，表示对类的说明
    ```java
     @Api(tags = "说明该类的作用，可以在UI界面上看到的注解",
         value = "该参数没什么意义，在UI界面上也看到，所以不需要配置")
     @RestController
     @RequestMapping("example")
     public class ExampleController{
    
    }
    ```
- @ApiOperation
    > 用在请求的方法上，说明方法的用途、作用
    ```java
      @Api("示例Rest")
      @RestController
      @RequestMapping("example")
      public class ExampleController{
      
          @ApiOperation(value="说明方法的用途、作用",
                        notes="方法的备注说明",
                        response = "响应的数据")
          @GetMapping(value = "method")
          public void method(){}
      }
    ```    
 
- @ApiImplicitParams
    > 用在请求的方法上，表示一组参数说明
    ```java
      @Api("示例Rest")
      @RestController
      @RequestMapping("example")
      public class ExampleController{  
    
        @ApiOperation(value = "示例接口", response = ResultBody.class)
        @ApiImplicitParams({
           @ApiImplicitParam(paramType="参数放在哪个地方", 
                             name = "参数名", 
                             value = "参数的汉字说明、解释", 
                             required = '参数是否必须传', 
                             dataTypeClass = Long.class,
                             dataType = "参数类型，默认String，其它值dataType='Integer' || 与 dataTypeClass 二选一",
                             defaultValue = "参数的默认值"),
          @ApiImplicitParam(paramType="query", name = "id", value = "记录ID", required = true, dataTypeClass = Long.class),
          @ApiImplicitParam(paramType="header", name = "token", value = "token", required = true, dataTypeClass = String.class),
          @ApiImplicitParam(paramType="query", name = "note", value = "笔记", required = true, dataTypeClass = String.class),
        })            
        @GetMapping(value = "method")          
        public void method(@RequestParam("id") Long id,
                           @RequestHeader("token") String token,
                           @RequestParam("note") String note){
            
        }
      }
    ``` 
    > - @ApiImplicitParam(paramType = ??)取值如下
    >   - query  --> 请求参数的获取：@RequestParam `默认`
    >   - header --> 请求参数的获取：@RequestHeader
    >   - path（用于restful接口）--> 请求参数的获取：@PathVariable
    >   - body（不常用）
    >   - form（不常用）
     

- @ApiResponses
    > 用在请求的方法上，表示一组响应
    ```java
      @Api("示例Rest")
      @RestController
      @RequestMapping("example")
      public class ExampleController{
    
          @ApiOperation(value = "示例接口", response = ResultBody.class)
          @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "id", value = "记录ID", required = true, dataTypeClass = Long.class),
            @ApiImplicitParam(paramType="header", name = "token", value = "token", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(paramType="query", name = "note", value = "笔记", required = true, dataTypeClass = String.class),
          })    
          @ApiResponses({
              // 用在@ApiResponses中，一般用于表达一个错误的响应信息
            @ApiResponse(code ="数字，例如400",
                         message = "信息，例如'请求参数没填好'",
                         response = "抛出异常的类"),
           })
          @GetMapping(value = "method")
          public void method(@RequestParam("id") Long id,
                             @RequestHeader("token") String token,
                             @RequestParam("note") String note){}
      }
    ```    
- @ApiModel
    > - 用于响应类上，表示一个返回响应数据的信息
    > - 这种一般用在post创建的时候，使用@RequestBody这样的场景，请求参数无法使用@ApiImplicitParam注解进行描述的时候
    ```java
      @ApiModel
      public class Model{
    
          // 用在属性上，描述响应类的属性
          @ApiModelProperty()
          public String propertyName;
      }
    ``` 