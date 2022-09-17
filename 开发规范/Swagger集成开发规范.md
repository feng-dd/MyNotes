以yundt-nuskin-identity为例

### springfox-swagger2依赖

**使用yundt-nuskin-modules 2.6.1 swagger版本**

```xml
<springfox-swagger2.version>2.6.1</springfox-swagger2.version>

<dependency>

  <groupId>io.springfox</groupId>

  <artifactId>springfox-swagger2</artifactId>

  <version>${springfox-swagger2.version}</version>

</dependency>
```



### SwaggerConfig配置

RequestHandlerSelectors.basePackage配置生成api文档包，添加了请求头auth

```java
@Configuration
@EnableSwagger2
public class SwaggerConfig {


    @Bean
    public Docket createRestApi() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> parameters = new ArrayList<>();
        ticketPar.name("auth").description("user auth")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        parameters.add(ticketPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .globalOperationParameters(parameters)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dtyunxi.yundt.identity.rest"))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("nuskin-hk-app-identity构建api")
                .description("简单优雅的restfun风格，https://nutowntest.nuskin.com.hk")
                .termsOfServiceUrl("https://nutowntest.nuskin.com.hk")
                .version("1.0")
                .build();
    }
}
```



### 修改WebMvcConfigurerAdapter配置

增加swagger静态资源配置（目的是查看swagger-ui页面，不需要可取消配置）

```java
@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
```

### 增加工程登录不拦截接口

```java
        setUri.add("/**/api-docs");
        setUri.add("/**/swagger-resources");
        setUri.add("/**/swagger-resources/**");
```



### swagger注解添加

常用注解： 

\- @Api()用于类； 

表示标识这个类是swagger的资源 

\- @ApiOperation()用于方法； 

表示一个http请求的操作 

\- @ApiParam()用于方法，参数，字段说明； 

表示对参数的添加元数据（说明或是否必填等） 

\- @ApiModel()用于类 

表示对类进行说明，用于参数用实体类接收 

\- @ApiModelProperty()用于方法，字段 

表示对model属性的说明或者数据操作更改 

\- @ApiIgnore()用于类，方法，方法参数 

表示这个方法或者类被忽略 

\- @ApiImplicitParam() 用于方法 

表示单独的请求参数 

\- @ApiImplicitParams() 用于方法，包含多个 @ApiImplicitParam



### 集成启动后结果查看

示例：

swagger-ui页面：https://3000101.nuskin.com.tw/nuskin-hk-app-identity/api/swagger-ui.html

swagger-api json数据：https://3000101.nuskin.com.tw/nuskin-hk-app-identity/api/v2/api-docs