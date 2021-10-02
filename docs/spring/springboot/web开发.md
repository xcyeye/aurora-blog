---

categories: [web,内容协商,视图,spring静态资源]
---


# springboot web开发


https://docs.spring.io/spring-boot/docs/current/reference/html/features.html



## 静态资源

springboot对静态资源有一定的要求

By default, Spring Boot serves static content from a directory called `/static` (or `/public` or `/resources` or `/META-INF/resources`) in the classpath or from the root of the `ServletContext`

也就是说，在类路径中的static,public,resources,/META-INF/resources都是静态资源文件，此静态资源文件被映射到`/**`路径中，我们直接通过`地址/静态资源文件`就可以访问到



![image-20210702171541727](http://ooszy.cco.vin/img/blog-note/image-20210702171541727.png?x-oss-process=style/pictureProcess1)

红色部分的文件夹都是静态资源文件夹，每个里面都有一张图片，我们直接通过`http://localhost:8080/44.jpg`便可以访问到这些图片，这个就是静态资源文件夹，test,haha文件夹中的资源，不能通过`http://localhost:8080/静态资源文件`进行访问



## 修改路径

我们可以通过`http://localhost:8080/44.jpg`访问静态资源，但是在后期的操作中，我们还需要配置过滤器等等，这样的话，不方便，所以我们可以规定静态资源的访问都是以这种方式进行`http://localhost:8080/resources/静态资源文件`

> 修改
>
> `spring.mvc.static-path-pattern=/resources/**`
>
> 这样修改之后就可以了



## 指定静态资源文件夹

同时我们也可以自己指定静态资源文件夹的位置，同样通过修改便可以

> ```yaml
> web:
>     resources:
>       static-locations: "classpath:/newres/"
> ```
>
> `newres`便是新的静态资源文件的位置，那么上面的那些static，public等中的文件，都不能通过直接访问到，只有在newres中的静态资源文件才可以被直接访问到



## 相同访问路径问题

如果静态资源图片`3.jpg`，恰巧有一个Controller，此访问路径也是`3.jpg`，那么我们通过浏览器访问到的是Controller，如果在Controller中，没有这个`3.jpg`的访问路径，那么就会取静态资源文件中查找，是否有这个文件，如果没有，则返回404



# 欢迎页面

在静态文件夹中，新建一个index.html，那么直接访问主机的时候，这个就充当了欢迎页面，也就是主页

Spring Boot supports both static and templated welcome pages. It first looks for an `index.html` file in the configured static content locations. If one is not found, it then looks for an `index` template. If either is found, it is automatically used as the welcome page of the application.

如果没有这个`index.html`的话，那么就会去找，时候有一个资源访问时index，如果有，则作为欢迎页面，同时我们也可以设置网站图标

在静态资源文件夹中，放入一个favicon.ico，那么在访问资源的时候，springboot会自动将网站图标设置为这个，但是这个可能是spring已经移除了，设置了不行，文档中也被移除了



> 设置欢迎页，不能设置静态资源访问路径，否则的话，会失效
>
> `spring.mvc.static-path-pattern=/resources/**`





# 源码分析

## WebMvcAutoConfiguration

```java
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = Type.SERVLET)
@ConditionalOnClass({ Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class })
@ConditionalOnMissingBean(WebMvcConfigurationSupport.class)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 10)
@AutoConfigureAfter({ DispatcherServletAutoConfiguration.class, TaskExecutionAutoConfiguration.class,
		ValidationAutoConfiguration.class })
public class WebMvcAutoConfiguration {}
```



## 单独一个构造方法

```java
这个构造方法是在WebMvcAutoConfigurationAdapter中的
public WebMvcAutoConfigurationAdapter(
    org.springframework.boot.autoconfigure.web.ResourceProperties resourceProperties,
    WebProperties webProperties, WebMvcProperties mvcProperties, ListableBeanFactory beanFactory,
    ObjectProvider<HttpMessageConverters> messageConvertersProvider,
    ObjectProvider<ResourceHandlerRegistrationCustomizer> resourceHandlerRegistrationCustomizerProvider,
    ObjectProvider<DispatcherServletPath> dispatcherServletPath,
    ObjectProvider<ServletRegistrationBean<?>> servletRegistrations) {
    this.resourceProperties = resourceProperties.hasBeenCustomized() ? resourceProperties
        : webProperties.getResources();
    this.mvcProperties = mvcProperties;
    this.beanFactory = beanFactory;
    this.messageConvertersProvider = messageConvertersProvider;
    this.resourceHandlerRegistrationCustomizer = resourceHandlerRegistrationCustomizerProvider.getIfAvailable();
    this.dispatcherServletPath = dispatcherServletPath;
    this.servletRegistrations = servletRegistrations;
    this.mvcProperties.checkConfiguration();
}
```

> `如果配置文件中，值存在一个构造方法，并且这个构造方法是有参的，那么在参数对象都会从容器中取，进行赋值操作`
>
> ResourceProperties resourceProperties是绑定资源配置的，对应配置文件中的`spring.resources`值
>
> WebMvcProperties mvcProperties 获取和spring.mvc绑定的所有的值的对象 ListableBeanFactory beanFactory Spring的beanFactory 
>
> HttpMessageConverters 找到所有的HttpMessageConverters ResourceHandlerRegistrationCustomizer 找到 资源处理器的自定义器。========= DispatcherServletPath   //ServletRegistrationBean   给应用注册Servlet、Filter....



## 资源配置的默认规则

```java
@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    if (!this.resourceProperties.isAddMappings()) {
        logger.debug("Default resource handling disabled");
        return;
    }
    addResourceHandler(registry, "/webjars/**", "classpath:/META-INF/resources/webjars/");
    addResourceHandler(registry, this.mvcProperties.getStaticPathPattern(), (registration) -> {
        registration.addResourceLocations(this.resourceProperties.getStaticLocations());
        if (this.servletContext != null) {
            ServletContextResource resource = new ServletContextResource(this.servletContext, SERVLET_LOCATION);
            registration.addResourceLocations(resource);
        }
    });
}
```

配置文件`spring.resources.add-mappings: false`，那么resourceProperties.isAddMappings()的值为false，也就是进入

```java
if (!this.resourceProperties.isAddMappings()) {
    logger.debug("Default resource handling disabled");
    return;
}
```

那么后面的资源配置都不会进行配置，并且我们访问静态资源，在static，public等目录中的，都不会被访问到，因为静态资源处理的功能已经被关闭了



```java
@ConfigurationProperties(prefix = "spring.resources", ignoreUnknownFields = false)
public class ResourceProperties {

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/META-INF/resources/","classpath:/resources/", "classpath:/static/", "classpath:/public/" };
    private String[] staticLocations = CLASSPATH_RESOURCE_LOCATIONS;
```

在这个方法中，可以看到spring配置的静态资源的路径





## rest风格



我们也可以使用这种形式

```java
@RestController
public class UserController {

    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public String doGet(){
        return "get-user";
    }

    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public String doPost(){
        return "post-user";
    }

    //@DeleteMapping("/user")
    @RequestMapping(value = "/user",method = RequestMethod.DELETE)
    public String doDelete(){
        return "delete-user";
    }

    //@PutMapping("/user")
    @RequestMapping(value = "/user",method = RequestMethod.PUT)
    public String doPut(){
        return "put-user";
    }
}
```



```java
@DeleteMapping("/user")
//@RequestMapping(value = "/user",method = RequestMethod.DELETE)
public String doDelete(){
    return "delete-user";
}

@PutMapping("/user")
//@RequestMapping(value = "/user",method = RequestMethod.PUT)
public String doPut(){
    return "put-user";
}
```



```java
@Bean
@ConditionalOnMissingBean(HiddenHttpMethodFilter.class)
@ConditionalOnProperty(prefix = "spring.mvc.hiddenmethod.filter", name = "enabled")
public OrderedHiddenHttpMethodFilter hiddenHttpMethodFilter() {
    return new OrderedHiddenHttpMethodFilter();
}
```

如果不是使用客户端(postman等等直接发送delete，put)的话，那么想要通过表单的形式使用rest风格，就必须开启

```yaml
spring: 
    mvc:
        hiddenmethod:
          filter:
            enabled: true
```



`@ConditionalOnProperty(prefix = "spring.mvc.hiddenmethod.filter", name = "enabled")`这个注解

```java
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    HttpServletRequest requestToUse = request;
    if ("POST".equals(request.getMethod()) && request.getAttribute("javax.servlet.error.exception") == null) {
        String paramValue = request.getParameter(this.methodParam);
        if (StringUtils.hasLength(paramValue)) {
            String method = paramValue.toUpperCase(Locale.ENGLISH);
            if (ALLOWED_METHODS.contains(method)) {
                requestToUse = new HiddenHttpMethodFilter.HttpMethodRequestWrapper(request, method);
            }
        }
    }

    filterChain.doFilter((ServletRequest)requestToUse, response);
}
```

而且使用表单的方式发送delete，put请求，必须发送post请求，并且在表单中，需要有一个字段，

![image-20210702205434319](http://ooszy.cco.vin/img/blog-note/image-20210702205434319.png?x-oss-process=style/pictureProcess1)

这个`_method`的值就是delete或者是put或者是PATCH，因为springboot支持三种，可以在下面查看

```java
static {
    ALLOWED_METHODS = Collections.unmodifiableList(Arrays.asList(HttpMethod.PUT.name(), HttpMethod.DELETE.name(), HttpMethod.PATCH.name()));
}
```



`if ("POST".equals(request.getMethod())`判断是否是post请求，如果不是，直接跳过，直接跳过的话，用于直接使用客户端（postman等）直接发送get或者是delete，put等请求情况，所以如果使用表单的方式发送delete，put，那么就必须是post请求



```java
private String methodParam = "_method";
String paramValue = request.getParameter(this.methodParam);
String method = paramValue.toUpperCase(Locale.ENGLISH);
```



获取`_method`字段的值，并且无论这个参数的值是多大写还是小写，都会将其转换为大写

`ALLOWED_METHODS.contains(method)`判断这个_method值是否在定义中

```java
private static final List<String> ALLOWED_METHODS;
static {
    ALLOWED_METHODS = Collections.unmodifiableList(Arrays.asList(HttpMethod.PUT.name(), HttpMethod.DELETE.name(), HttpMethod.PATCH.name()));
}
```



这个过程就是如果使用表单的形式，发送delete，put请求的配置，而且我们也可以更改这个_method这个参数的名称



通过下面的方式

```java
@Bean
public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
    HiddenHttpMethodFilter methodFilter = new HiddenHttpMethodFilter();
    methodFilter.setMethodParam("_m");

    return methodFilter;
}
```

![image-20210702211055723](http://ooszy.cco.vin/img/blog-note/image-20210702211055723.png?x-oss-process=style/pictureProcess1)

因为这个是，容器中没有HiddenHttpMethodFilter对象才会创建一个默认的HiddenHttpMethodFilter，默认的使用的是`_method`，在此对象中，提供了一个方法可以修改

```java
public void setMethodParam(String methodParam) {
    Assert.hasText(methodParam, "'methodParam' must not be empty");
    this.methodParam = methodParam;
}
```





## 请求映射原理

![image-20210702221711109](http://ooszy.cco.vin/img/blog-note/image-20210702221711109.png?x-oss-process=style/pictureProcess1)

 

FrameworkServlet类中的方法分析

```java
protected final void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    this.processRequest(request, response);
}
protected final void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    this.processRequest(request, response);
}
protected final void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    this.processRequest(request, response);
}

protected final void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    this.processRequest(request, response);
}

protected final void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    .........
        
    try {
            this.doService(request, response);
        } catch (IOException | ServletException var16) {
            failureCause = var16;
            throw var16;
.......
    }
    
protected abstract void doService(HttpServletRequest var1, HttpServletResponse var2) throws Exception;
```

从这个类中的doget(),dopost()等请求的方法可以看出，所有的请求都会调用processRequest()方法，并且最终都会调用`this.doService(request, response)`

因为这个是一个抽象类，doService()没有什么方法体，我们在其子类中进行查看



DispatcherServlet类

```java
protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
    方法体........
    try {
        this.doDispatch(request, response);
    } finally {
        if (!WebAsyncUtils.getAsyncManager(request).isConcurrentHandlingStarted() && attributesSnapshot != null) {
            this.restoreAttributesAfterInclude(request, attributesSnapshot);
        }

        if (this.parseRequestPath) {
            ServletRequestPathUtils.setParsedRequestPath(previousRequestPath, request);
        }

    }
```

从这里可以看出，所有的请求最终，都会调用一个`this.doDispatch(request, response)`方法，这个方法才是真正有用的



```java
protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
 
    ............;
    
    try {
        processedRequest = this.checkMultipart(request);//检查是否是文件上传请求
        multipartRequestParsed = processedRequest != request;
        mappedHandler = this.getHandler(processedRequest);//找到当前请求是由哪个Handler进行处理的，也就是哪个Controller
    }
    
    .............;
   
}
```

进入this.checkMultipart(request)方法

```java
protected HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
    if (this.handlerMappings != null) {
        Iterator var2 = this.handlerMappings.iterator();

        while(var2.hasNext()) {
            HandlerMapping mapping = (HandlerMapping)var2.next();
            HandlerExecutionChain handler = mapping.getHandler(request);
            if (handler != null) {
                return handler;
            }
        }
    }
    return null;
}
```

`this.handlerMappings`能够获取到4个Mapping

![image-20210702225939199](http://ooszy.cco.vin/img/blog-note/image-20210702225939199.png?x-oss-process=style/pictureProcess1)

![image-20210702230121453](http://ooszy.cco.vin/img/blog-note/image-20210702230121453.png?x-oss-process=style/pictureProcess1)

每一个mapping中，都有一个路径匹配规则，但是这4个的路径匹配规则都是一样的



第一个mapping

![image-20210702230244371](http://ooszy.cco.vin/img/blog-note/image-20210702230244371.png?x-oss-process=style/pictureProcess1)

这里的`registry`中，就可以看到此Mapping对象可以处理当前项目中所有资源地址中的哪些地址，因为这个`RequestMappingHandlerMapping`的处理请求就是容器中使用`@RequestMapping()`设置的路径，还有error,管方的，当前浏览器访问的资源路径为`http://localhost:8080/user`，get请求，可以猜测到，此请求就是由这个Mapping进行处理的



![image-20210702230746211](http://ooszy.cco.vin/img/blog-note/image-20210702230746211.png?x-oss-process=style/pictureProcess1)

由这个可以看到，当前浏览器发送的请求方式，在RequestMappingHandlerMapping对象中，可以匹配的资源路径有哪些，当前发送的是get请求，上面列出的全部都是get请求方式的资源路径



下面这个是一个迭代器，将这4个Mapping一个一个的取出，和当前浏览器访问的资源名称，请求方式进行匹配

```java
while(var2.hasNext()) {
    HandlerMapping mapping = (HandlerMapping)var2.next();
    HandlerExecutionChain handler = mapping.getHandler(request);
    if (handler != null) {
        return handler;
    }
}
```

这里第一个就满足了，我们发送一个欢迎页进行测试，也就是访问`localhost:8080/`

因为这个的访问路径就是`/`，这个index.html的页面，最后会交给`WelcomePageHandlerMapping`进行处理



> 理解这个之后，在后期我们可能会自定义自己的请求映射等等



# 参数解析handlerAdapters处理器源码分析

执行完`doDispatch(HttpServletRequest request, HttpServletResponse response)`方法之后，就会得到处理此请求的映射处理器对象`mappedHandler = this.getHandler(processedRequest)`，`HandlerAdapter ha = this.getHandlerAdapter(mappedHandler.getHandler())`这个方法中，执行的就是解析这个请求，此方法如下

```java
protected HandlerAdapter getHandlerAdapter(Object handler) throws ServletException {
    if (this.handlerAdapters != null) {
        Iterator var2 = this.handlerAdapters.iterator();

        while(var2.hasNext()) {
            HandlerAdapter adapter = (HandlerAdapter)var2.next();
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
    }

    throw new ServletException("No adapter for handler [" + handler + "]: The DispatcherServlet configuration needs to include a HandlerAdapter that supports this handler");
}
```

## 1.1请求映射处理器适配器对象

this.handlerAdapters此对象中，保存了4个handlerAdapters对象

RequestMappingHandlerAdapter `此处理器会处理，使用@RequestMapping`注解标注的方法解析

org.springframework.web.servlet.function.support.HandlerFunctionAdapter 支持函数式编程的

org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter

org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter

后面的几个不常用

![image-20210703144541026](http://ooszy.cco.vin/img/blog-note/image-20210703144541026.png?x-oss-process=style/pictureProcess1)



其中每一个请求映射处理器适配器中，都存在参数解析器，就比如下图中的这个，可以看到，可以解析下面这些注解标注

![image-20210703145243370](http://ooszy.cco.vin/img/blog-note/image-20210703145243370.png?x-oss-process=style/pictureProcess1)

![image-20210703145259556](http://ooszy.cco.vin/img/blog-note/image-20210703145259556.png?x-oss-process=style/pictureProcess1)

并且一般都还有一个解析map集合的解析器

![image-20210703145047849](http://ooszy.cco.vin/img/blog-note/image-20210703145047849.png?x-oss-process=style/pictureProcess1)



初始化参数解析器

![image-20210703145449586](http://ooszy.cco.vin/img/blog-note/image-20210703145449586.png?x-oss-process=style/pictureProcess1)



还有返回值处理器

![image-20210703145753253](http://ooszy.cco.vin/img/blog-note/image-20210703145753253.png?x-oss-process=style/pictureProcess1)



并且还有返回值建议

![image-20210703145853613](http://ooszy.cco.vin/img/blog-note/image-20210703145853613.png?x-oss-process=style/pictureProcess1)

这里建议都是json，因为我们使用了`@ResponseBody`，springboot默认使用的是阿里巴巴的jackson作为json的返回值

![image-20210703150121872](http://ooszy.cco.vin/img/blog-note/image-20210703150121872.png?x-oss-process=style/pictureProcess1)

从这里可以看到，可以处理的请求方式有哪些



## 1.2

if (adapter.supports(handler))这里会有一个判断，使用迭代的方式从4个请求映射处理器适配器中，一个一个的取出，判断取出的适配器能不能处理当前请求的请求映射器

所以HandlerAdapter ha = this.getHandlerAdapter(mappedHandler.getHandler())此执行的目的就是为当前的请求获取一个适合的适配器



## 1.3Actually invoke the handler

```java
// Actually invoke the handler.
mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
```

此方法是真正执行的方法，也就是程序执行当前请求对应的@RequestMapping标注的方法，此方法在`RequestMappingHandlerAdapter`类中

Invoke the RequestMapping handler method preparing a ModelAndView if view resolution is required

最终会执行到此方法`ModelAndView invokeHandlerMethod(HttpServletRequest request,
			HttpServletResponse response, HandlerMethod handlerMethod)`

从这个handlerMethod对象中，我们可以看到此执行的方法的参数类型，方法的返回值等等

![image-20210703152148811](http://ooszy.cco.vin/img/blog-note/image-20210703152148811.png?x-oss-process=style/pictureProcess1)



## 执行方法

invokeAndHandle(ServletWebRequest webRequest, ModelAndViewContainer mavContainer,
			Object... providedArgs)此方法属于`ServletInvocableHandlerMethod`

Object returnValue = invokeForRequest(webRequest, mavContainer, providedArgs)这个方法会执行这个请求，也就是执行方法，这个请求资源对应的方法



## 获取参数值

```java
public Object invokeForRequest(NativeWebRequest request, @Nullable ModelAndViewContainer mavContainer,
                               Object... providedArgs) throws Exception {

    Object[] args = getMethodArgumentValues(request, mavContainer, providedArgs);
    if (logger.isTraceEnabled()) {
        logger.trace("Arguments: " + Arrays.toString(args));
    }
    return doInvoke(args);
}
```

Object[] args = getMethodArgumentValues(request, mavContainer, providedArgs)能够得到此方法参数中的值



![image-20210703160921850](http://ooszy.cco.vin/img/blog-note/image-20210703160921850.png?x-oss-process=style/pictureProcess1)

进入此方法中MethodParameter[] parameters = getMethodParameters()，此方法能够得到@RequestMapping标注的方法的参数

注解信息，从这里可以看到此方法参数的注解有哪些

![image-20210703161334183](http://ooszy.cco.vin/img/blog-note/image-20210703161334183.png?x-oss-process=style/pictureProcess1)



### 解析注解参数

HandlerMethodArgumentResolverComposite类中的

```java
private HandlerMethodArgumentResolver getArgumentResolver(MethodParameter parameter) {
    HandlerMethodArgumentResolver result = this.argumentResolverCache.get(parameter);
    if (result == null) {
        for (HandlerMethodArgumentResolver resolver : this.argumentResolvers) {
            if (resolver.supportsParameter(parameter)) {
                result = resolver;
                this.argumentResolverCache.put(parameter, result);
                break;
            }
        }
    }
    return result;
}
```



![image-20210703161742742](http://ooszy.cco.vin/img/blog-note/image-20210703161742742.png?x-oss-process=style/pictureProcess1)

此方法能够从，27个参数解析器中，查看是否能够解析我们方法中的参数注解，如果能够解析，那么就会this.argumentResolverCache.put(parameter, result)将此解析器放入数组中，这就是为什么spring应用初次运行的时候，会比较慢，但是启动之后，就会越来越快，因为之后的请求，都不会进入for循环中进行判断，标注在方法参数中的注解，在不在规定的这27个中



args[i] = this.resolvers.resolveArgument(parameter, mavContainer, request, this.dataBinderFactory)此方法执行结束后，会为方法参数中的每一个参数匹配对应的值

```java
public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
                              NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {

    HandlerMethodArgumentResolver resolver = getArgumentResolver(parameter);
    if (resolver == null) {
        throw new IllegalArgumentException("Unsupported parameter type [" +
                                           parameter.getParameterType().getName() + "]. supportsParameter should be called first.");
    }
    return resolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
}
```

HandlerMethodArgumentResolver resolver = getArgumentResolver(parameter)此方法能够得到参数id



HandlerMethodArgumentResolver resolver = getArgumentResolver(parameter)

![image-20210703164107114](http://ooszy.cco.vin/img/blog-note/image-20210703164107114.png?x-oss-process=style/pictureProcess1)

参数的运行结果，大概就是这个样子



## 视图Model，Map原理

```java
@GetMapping("/paramss")
public String testParam(
    Map<String,Object> map,
    Model model
){
    map.put("map","vmap");
    model.addAttribute("mv","vmc");
    return "forward:/success";
}
```

传入model和map集合，查看执行原理分析





## 处理自定义类型对象

```java
@GetMapping("/saveUser")
public Person personTest(Person person) {
    return person;
}
```

当执行到getArgumentResolver(MethodParameter parameter)此方法的时候，最终是`ServletModelAttributeMethodProcessor`进行自定义对象的处理

判断的依据是判断此对象是否是简单类型，简单类型就是除了Class,String，枚举等等的类

最终会调用InvocableHandlerMethod类中的method.invoke(getBean(), args)此方法去调用controller中的方法

最重要的方法为

```java
public final Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
                                    NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {

    Assert.state(mavContainer != null, "ModelAttributeMethodProcessor requires ModelAndViewContainer");
    Assert.state(binderFactory != null, "ModelAttributeMethodProcessor requires WebDataBinderFactory");

    String name = ModelFactory.getNameForParameter(parameter);
    ModelAttribute ann = parameter.getParameterAnnotation(ModelAttribute.class);
    if (ann != null) {
        mavContainer.setBinding(name, ann.binding());
    }

    Object attribute = null;
    BindingResult bindingResult = null;

    if (mavContainer.containsAttribute(name)) {
        attribute = mavContainer.getModel().get(name);
    }
    else {
        // Create attribute instance
        try {
            attribute = createAttribute(name, parameter, binderFactory, webRequest);
        }
        catch (BindException ex) {
            if (isBindExceptionRequired(parameter)) {
                // No BindingResult parameter -> fail with BindException
                throw ex;
            }
            // Otherwise, expose null/empty value and associated BindingResult
            if (parameter.getParameterType() == Optional.class) {
                attribute = Optional.empty();
            }
            else {
                attribute = ex.getTarget();
            }
            bindingResult = ex.getBindingResult();
        }
    }

    if (bindingResult == null) {
        // Bean property binding and validation;
        // skipped in case of binding failure on construction.
        WebDataBinder binder = binderFactory.createBinder(webRequest, attribute, name);
        if (binder.getTarget() != null) {
            if (!mavContainer.isBindingDisabled(name)) {
                bindRequestParameters(binder, webRequest);
            }
            validateIfApplicable(binder, parameter);
            if (binder.getBindingResult().hasErrors() && isBindExceptionRequired(binder, parameter)) {
                throw new BindException(binder.getBindingResult());
            }
        }
        // Value type adaptation, also covering java.util.Optional
        if (!parameter.getParameterType().isInstance(attribute)) {
            attribute = binder.convertIfNecessary(binder.getTarget(), parameter.getParameterType(), parameter);
        }
        bindingResult = binder.getBindingResult();
    }

    // Add resolved attribute and BindingResult at the end of the model
    Map<String, Object> bindingResultModel = bindingResult.getModel();
    mavContainer.removeAttributes(bindingResultModel);
    mavContainer.addAllAttributes(bindingResultModel);

    return attribute;
}
```

WebDataBinder binder = binderFactory.createBinder(webRequest, attribute, name)

WebDataBinder web数据绑定器

在这个web数据绑定器中，有很多的方法可以将一个String类型的数据，转换成int类型

![image-20210703205945952](http://ooszy.cco.vin/img/blog-note/image-20210703205945952.png?x-oss-process=style/pictureProcess1)

这个datebinder中含有很多的**converter**，能够将请求的数据转换成我们需要的数据类型，并且这个**converter**我们也可以自己添加，添加到这个datebinder中就可以

未来我们可以给WebDataBinder里面放自己的Converter；

**private static final class** StringToNumber<T **extends** Number> **implements** Converter<String, T>



## 自定义类型转换器Converter

如果我们传递的参数是正规参数，就比如我们自己定义了一个规则，表单value值为 chuchen,13，前面是名字，后面是年龄，但是这样的话，官方converter不知道，这样就需要我们自己定义规则



```java
public class Person {
    private String name;
    private int age;

    private Pet pet;

    public Pet getPet () {
        return pet;
    }
}
```

如果浏览器的访问路径为http://localhost:8080/saveUser?name=chuchen&age=21&pet=xiaoba,34

那么就会报一个错误

```java
Field error in object 'person' on field 'pet': rejected value [xiaoba,34]; codes [typeMismatch.person.pet,typeMismatch.pet,typeMismatch.vin.cco.chuchen.domain.Pet,typeMismatch]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [person.pet,pet]; arguments []; default message [pet]]; default message [Failed to convert property value of type 'java.lang.String' to required type 'vin.cco.chuchen.domain.Pet' for property 'pet'; nested exception is java.lang.IllegalStateException: Cannot convert value of type 'java.lang.String' to required type 'vin.cco.chuchen.domain.Pet' for property 'pet': no matching editors or conversion strategy found]]
```

这个就是类型不匹配造成的，就需要我们自己自定义converter

自定义我们在WebMvcConfigurer类中进行定义，此类中，存在addFormatters(FormatterRegistry registry)方法，此方法可以Add Converters and Formatters in addition to the ones registered by default.增加converter或者是formatters，只需要重写这个方法就可以了



自定义规则

```java
@Bean
public WebMvcConfigurer webMvcConfigurer() {
    return new WebMvcConfigurer() {
        @Override
        public void addFormatters (FormatterRegistry registry) {
            registry.addConverter(new Converter<String, Pet>() {
                /**
                     *
                     * @author chuchen
                     * @date 2021/7/3 21:53
                     * @param source 就是浏览器访问之后的数据源，也就是这里的pet=xiaoba,34中的xiaoba,34
                     * @return Pet
                     */
                @Override
                public Pet convert (String source) {
                    //书写convert规则
                    if (!StringUtils.isEmpty(source)) {
                        Pet pet = new Pet();
                        //根据,进行分则，这个就是规则
                        String[] split = source.split(",");
                        pet.setDogName(split[0]);
                        pet.setDogAge(Integer.parseInt(split[1]));
                        return pet;
                    }
                    return null;
                }
            });
        }
    };
}
```

最终使用的就是我们的这个规则

最终我们会在这个web数据绑定器中，看到我们自定义的这个converter

![image-20210703222538730](http://ooszy.cco.vin/img/blog-note/image-20210703222538730.png?x-oss-process=style/pictureProcess1)



# 响应数据与内容协商

数据的返回值有很多中，有modelandview还有json等等

如果我们标注了@ResponseBody注解，那么默认使用jackson进行数据的处理



HandlerMethodReturnValueHandlerComposite类中的方法handleReturnValue()能够处理返回值，这个方法是核心方法



```java
private HandlerMethodReturnValueHandler selectHandler(@Nullable Object value, MethodParameter returnType) {
    boolean isAsyncValue = isAsyncReturnValue(value, returnType);
    for (HandlerMethodReturnValueHandler handler : this.returnValueHandlers) {
        if (isAsyncValue && !(handler instanceof AsyncHandlerMethodReturnValueHandler)) {
            continue;
        }
        if (handler.supportsReturnType(returnType)) {
            return handler;
        }
    }
    return null;
}
```

在此方法中，一共有15中返回值处理器，还会使用处理方法参数的方法，使用for循环从这15中返回值处理器中，一一的遍历出来，进行判断，期间中调用下类中的方法进行判断是否支持

![image-20210704084308400](http://ooszy.cco.vin/img/blog-note/image-20210704084308400.png?x-oss-process=style/pictureProcess1)

boolean isAsyncValue = isAsyncReturnValue(value, returnType)此段代码是判断当前是不是异步的



```java
使用for循环的方式一一判断当前返回值类型Person需要由哪种返回值处理器进行处理
for (HandlerMethodReturnValueHandler handler : this.returnValueHandlers) {
    if (isAsyncValue && !(handler instanceof AsyncHandlerMethodReturnValueHandler)) {
        continue;
    }
    if (handler.supportsReturnType(returnType)) {
        return handler;
    }
}
```

这15种返回值处理器有

![image-20210704085342197](http://ooszy.cco.vin/img/blog-note/image-20210704085342197.png?x-oss-process=style/pictureProcess1)

springmvc底层支持的所有返回数据类型有

```java
ModelAndView
Model
View
ResponseEntity 
ResponseBodyEmitter
StreamingResponseBody
HttpEntity
HttpHeaders
Callable
DeferredResult
ListenableFuture
CompletionStage
WebAsyncTask
有 @ModelAttribute 且为对象类型的
@ResponseBody 注解 ---> RequestResponseBodyMethodProcessor；
```



最终，我们方法的返回值类型是Person，并且有@ResponseBody注解，最终是由RequestResponseBodyMethodProcessor返回值处理器进行处理的，如果这里，我们没有标@ResponseBosy，那么可能返回值处理器就是由下面中的某个进行处理（最有可能，看情况）

![image-20210704085947672](http://ooszy.cco.vin/img/blog-note/image-20210704085947672.png?x-oss-process=style/pictureProcess1)

到这里，就确定了处理返回值类型的返回值处理器



## 返回值解析原理

当确定此返回值使用哪个解析器进行解析之后，就会使用`MessageConverters`进行返回值的解析，这个就和参数解析converter一样，springmvc底层也是内置了多种converter

解析返回值的核心方法在`writeWithMessageConverters()`，此方法在AbstractMessageConverterMethodProcessor类中

```java
protected <T> void writeWithMessageConverters(@Nullable T value, MethodParameter returnType,
                                              ServletServerHttpRequest inputMessage, ServletServerHttpResponse outputMessage)
    throws IOException, HttpMediaTypeNotAcceptableException, HttpMessageNotWritableException {

    Object body;
    Class<?> valueType;
    Type targetType;

    if (value instanceof CharSequence) {
        body = value.toString();
        valueType = String.class;
        targetType = String.class;
    }
    else {
        body = value;
        valueType = getReturnValueType(body, returnType);
        targetType = GenericTypeResolver.resolveType(getGenericType(returnType), returnType.getContainingClass());
    }

    if (isResourceType(value, returnType)) {
        outputMessage.getHeaders().set(HttpHeaders.ACCEPT_RANGES, "bytes");
        if (value != null && inputMessage.getHeaders().getFirst(HttpHeaders.RANGE) != null &&
            outputMessage.getServletResponse().getStatus() == 200) {
            Resource resource = (Resource) value;
            try {
                List<HttpRange> httpRanges = inputMessage.getHeaders().getRange();
                outputMessage.getServletResponse().setStatus(HttpStatus.PARTIAL_CONTENT.value());
                body = HttpRange.toResourceRegions(httpRanges, resource);
                valueType = body.getClass();
                targetType = RESOURCE_REGION_LIST_TYPE;
            }
            catch (IllegalArgumentException ex) {
                outputMessage.getHeaders().set(HttpHeaders.CONTENT_RANGE, "bytes */" + resource.contentLength());
                outputMessage.getServletResponse().setStatus(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE.value());
            }
        }
    }

    MediaType selectedMediaType = null;
    MediaType contentType = outputMessage.getHeaders().getContentType();
    boolean isContentTypePreset = contentType != null && contentType.isConcrete();
    if (isContentTypePreset) {
        if (logger.isDebugEnabled()) {
            logger.debug("Found 'Content-Type:" + contentType + "' in response");
        }
        selectedMediaType = contentType;
    }
    else {
        HttpServletRequest request = inputMessage.getServletRequest();
        List<MediaType> acceptableTypes;
        try {
            acceptableTypes = getAcceptableMediaTypes(request);
        }
        catch (HttpMediaTypeNotAcceptableException ex) {
            int series = outputMessage.getServletResponse().getStatus() / 100;
            if (body == null || series == 4 || series == 5) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Ignoring error response content (if any). " + ex);
                }
                return;
            }
            throw ex;
        }
        List<MediaType> producibleTypes = getProducibleMediaTypes(request, valueType, targetType);

        if (body != null && producibleTypes.isEmpty()) {
            throw new HttpMessageNotWritableException(
                "No converter found for return value of type: " + valueType);
        }
        List<MediaType> mediaTypesToUse = new ArrayList<>();
        for (MediaType requestedType : acceptableTypes) {
            for (MediaType producibleType : producibleTypes) {
                if (requestedType.isCompatibleWith(producibleType)) {
                    mediaTypesToUse.add(getMostSpecificMediaType(requestedType, producibleType));
                }
            }
        }
        if (mediaTypesToUse.isEmpty()) {
            if (body != null) {
                throw new HttpMediaTypeNotAcceptableException(producibleTypes);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("No match for " + acceptableTypes + ", supported: " + producibleTypes);
            }
            return;
        }

        MediaType.sortBySpecificityAndQuality(mediaTypesToUse);

        for (MediaType mediaType : mediaTypesToUse) {
            if (mediaType.isConcrete()) {
                selectedMediaType = mediaType;
                break;
            }
            else if (mediaType.isPresentIn(ALL_APPLICATION_MEDIA_TYPES)) {
                selectedMediaType = MediaType.APPLICATION_OCTET_STREAM;
                break;
            }
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Using '" + selectedMediaType + "', given " +
                         acceptableTypes + " and supported " + producibleTypes);
        }
    }

    if (selectedMediaType != null) {
        selectedMediaType = selectedMediaType.removeQualityValue();
        for (HttpMessageConverter<?> converter : this.messageConverters) {
            GenericHttpMessageConverter genericConverter = (converter instanceof GenericHttpMessageConverter ?
                                                            (GenericHttpMessageConverter<?>) converter : null);
            if (genericConverter != null ?
                ((GenericHttpMessageConverter) converter).canWrite(targetType, valueType, selectedMediaType) :
                converter.canWrite(valueType, selectedMediaType)) {
                body = getAdvice().beforeBodyWrite(body, returnType, selectedMediaType,
                                                   (Class<? extends HttpMessageConverter<?>>) converter.getClass(),
                                                   inputMessage, outputMessage);
                if (body != null) {
                    Object theBody = body;
                    LogFormatUtils.traceDebug(logger, traceOn ->
                                              "Writing [" + LogFormatUtils.formatValue(theBody, !traceOn) + "]");
                    addContentDispositionHeader(inputMessage, outputMessage);
                    if (genericConverter != null) {
                        genericConverter.write(body, targetType, selectedMediaType, outputMessage);
                    }
                    else {
                        ((HttpMessageConverter) converter).write(body, selectedMediaType, outputMessage);
                    }
                }
                else {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Nothing to write: null body");
                    }
                }
                return;
            }
        }
    }

    if (body != null) {
        Set<MediaType> producibleMediaTypes =
            (Set<MediaType>) inputMessage.getServletRequest()
            .getAttribute(HandlerMapping.PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE);

        if (isContentTypePreset || !CollectionUtils.isEmpty(producibleMediaTypes)) {
            throw new HttpMessageNotWritableException(
                "No converter for [" + valueType + "] with preset Content-Type '" + contentType + "'");
        }
        throw new HttpMediaTypeNotAcceptableException(getSupportedMediaTypes(body.getClass()));
    }
}
```

- 其中，isResourceType(@Nullable Object value, MethodParameter returnType)方法判断此returnType的class是否是InputStreamResource.class，如果是的话，就会调用相关的方法进行处理

- MediaType selectedMediaType 这个是一个类型，存在请求头中的类型

    ![image-20210704102231645](http://ooszy.cco.vin/img/blog-note/image-20210704102231645.png?x-oss-process=style/pictureProcess1)

    在这个中，q=0.9,q=0.8是一个权重，值越高，越先使用对应的类型接收，`*/*`表示能够接收所有的类型

- MediaType contentType = outputMessage.getHeaders().getContentType()如果程序已经做了一部分响应，那么会从响应头中，拿类型

- 如果没有，那么会从请求头中拿取类型

    ```java
    HttpServletRequest request = inputMessage.getServletRequest();
    List<MediaType> acceptableTypes;
    try {
        acceptableTypes = getAcceptableMediaTypes(request);
    }
    ```

    acceptableTypes中，存放了浏览器请求头中的类型，也就是下面这些

    ![image-20210704102720673](http://ooszy.cco.vin/img/blog-note/image-20210704102720673.png?x-oss-process=style/pictureProcess1)

    此过程也就是servlet中的一样

### messageConverters

在springmvc的底层，一共有一下几种消息转换器

![image-20210704103013128](http://ooszy.cco.vin/img/blog-note/image-20210704103013128.png?x-oss-process=style/pictureProcess1)

这些消息转换器支持的类型有

> 0 ByteArrayHttpMessageConverter--->byte
>
> 1 StringHttpMessageConverter--->String
>
> 2 --->String
>
> 3 --->Resource
>
> 4 SourceHttpMessageConverter能够支持5中，可以支持下面这些
>
> ![image-20210704103904665](http://ooszy.cco.vin/img/blog-note/image-20210704103904665.png?x-oss-process=style/pictureProcess1)
>
> 6 --->MultiValueMap
>
> Jaxb2RootElementHttpMessageConverter --->@XmlRootElement
>
> - MappingJackson2HttpMessageConverter --->能够处理任何类型
>
>     因为打死你个执行这个消息转换器的时候
>
>     ```java
>     AbstractHttpMessageConverter
>     protected boolean canWrite(@Nullable MediaType mediaType) {
>         if (mediaType == null || MediaType.ALL.equalsTypeAndSubtype(mediaType)) {
>             return true;
>         }
>         for (MediaType supportedMediaType : getSupportedMediaTypes()) {
>             if (supportedMediaType.isCompatibleWith(mediaType)) {
>                 return true;
>             }
>         }
>         return false;
>     }
>     ```
>
>     这里返回的永远都为true，也就是说，如果我们标有@ResponseBody注解，无论我们的返回值是什么，如果不是这些消息转换器进行处理
>
>     ![image-20210704104954977](http://ooszy.cco.vin/img/blog-note/image-20210704104954977.png?x-oss-process=style/pictureProcess1)
>
>     那么最终都会由MappingJackson2HttpMessageConverter转换器进行消息的处理
>
>     
>
> 



消息转换器执行核心方法

```java
AbstractMessageConverterMethodProcessor类中
protected List<MediaType> getProducibleMediaTypes(
    HttpServletRequest request, Class<?> valueClass, @Nullable Type targetType) {

    Set<MediaType> mediaTypes =
        (Set<MediaType>) request.getAttribute(HandlerMapping.PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE);
    if (!CollectionUtils.isEmpty(mediaTypes)) {
        return new ArrayList<>(mediaTypes);
    }
    List<MediaType> result = new ArrayList<>();
    for (HttpMessageConverter<?> converter : this.messageConverters) {
        if (converter instanceof GenericHttpMessageConverter && targetType != null) {
            if (((GenericHttpMessageConverter<?>) converter).canWrite(targetType, valueClass, null)) {
                result.addAll(converter.getSupportedMediaTypes(valueClass));
            }
        }
        else if (converter.canWrite(valueClass, null)) {
            result.addAll(converter.getSupportedMediaTypes(valueClass));
        }
    }
    return (result.isEmpty() ? Collections.singletonList(MediaType.ALL) : result);
}
```

这里还是使用for循环的方式，遍历每一个消息转换器，然后在调用converter.canWrite(valueClass, null)方法进行判断，此消息转换器是否能够将此方法返回类型，转换成此消息转换器能够转换的类型

当确定消息转换器的类型之后，就会计算出返回值的类型

![image-20210704105350101](http://ooszy.cco.vin/img/blog-note/image-20210704105350101.png?x-oss-process=style/pictureProcess1)



所以最终的响应头的类型就有

AbstractMessageConverterMethodProcessor类中的`List<MediaType> producibleTypes = getProducibleMediaTypes(request, valueType, targetType)`

![image-20210704105556942](http://ooszy.cco.vin/img/blog-note/image-20210704105556942.png?x-oss-process=style/pictureProcess1)



```java
List<MediaType> mediaTypesToUse = new ArrayList<>();
for (MediaType requestedType : acceptableTypes) {
    for (MediaType producibleType : producibleTypes) {
        if (requestedType.isCompatibleWith(producibleType)) {
            mediaTypesToUse.add(getMostSpecificMediaType(requestedType, producibleType));
        }
    }
}
```

这里一共会进行6*8此循环

因为浏览器的请求头中的类型，只有下面这几种（确定名字的）
![image-20210704110518962](http://ooszy.cco.vin/img/blog-note/image-20210704110518962.png?x-oss-process=style/pictureProcess1)

但是我们消息转换器转成的，一个也没有包含在其中

![image-20210704110606017](http://ooszy.cco.vin/img/blog-note/image-20210704110606017.png?x-oss-process=style/pictureProcess1)

那么现在就只有请求头中的`*/*`能够匹配

期间会将Person转成json对象



# 内容协商

内容协商功能也就是请求方法是同一个，但是spring可以根据客户端发送请求的不同（请求头的adccept接收类型），返回不同的数据类型，就比如，如果是`*/*`，那么默认是返回json，如果是application/xml，那么返回的xml数据格式

但是如果想要返回xml文本数据的话，需要下面依赖的支持

```xml
<dependency>
    <groupId>com.fasterxml.jackson.dataformat</groupId>
    <artifactId>jackson-dataformat-xml</artifactId>
</dependency>
```





- 原理分析

如果我们使用postman发送请求，并且确定了请求头的类型

![image-20210704221925660](http://ooszy.cco.vin/img/blog-note/image-20210704221925660.png?x-oss-process=style/pictureProcess1)

acceptableTypes = getAcceptableMediaTypes(request)那么程序在拿到这个请求头的时候，就只会拿到一个类型

![image-20210704222016461](http://ooszy.cco.vin/img/blog-note/image-20210704222016461.png?x-oss-process=style/pictureProcess1)



最终找出的能够返回的类型有

![image-20210704223046940](http://ooszy.cco.vin/img/blog-note/image-20210704223046940.png?x-oss-process=style/pictureProcess1)

但是这里面，有两个对象是重复的

```java
for (MediaType mediaType : mediaTypesToUse) {
    if (mediaType.isConcrete()) {
        selectedMediaType = mediaType;
        break;
    }
    else if (mediaType.isPresentIn(ALL_APPLICATION_MEDIA_TYPES)) {
        selectedMediaType = MediaType.APPLICATION_OCTET_STREAM;
        break;
    }
}
```

这段代码，能够从这4个类型中，选择一个最合适的返回类型

![image-20210704223438919](http://ooszy.cco.vin/img/blog-note/image-20210704223438919.png?x-oss-process=style/pictureProcess1)

这段代码，还是一样的思路，从能够将@ResponseBody的消息转换器中，选择一个合适的，能够使用这个消息转换器将Person对象转换成xml

最终`MappingJackson2XmlHttpMessageConverter`此消息转换器能够进行转换

内容协商功能我们也可以自定义，可以用我们自己的协议，





## 基于请求参数的内容协商

如果直接使用浏览器发送请求的话，浏览器不能自己设置请求头，但是浏览器默认优先xml的形式，但是springboot提供了一种基于请求参数的内容协商模式，在url地址后面，使用format参数确定接收的媒体类型，值可以是json or xml，

> http://localhost:8080/test/response?format=xml 返回xml
>
> http://localhost:8080/test/response?format=json 返回json

开启

```yaml
spring:  
  mvc:
    contentnegotiation:
      favor-parameter: true
```



官方描述

![image-20210705163927661](http://ooszy.cco.vin/img/blog-note/image-20210705163927661.png?x-oss-process=style/pictureProcess1)





## 自定义内容协商

我们可以加入我们自己的媒体类型，就比如application/qsyyke，程序能够处理我们的这种媒体类型，这个时候，就需要我们自己自定义内容协商模式了



> 步骤
>
> 创建类，实现`HttpMessageConverter<T>`接口
>
> 在public WebMvcConfigurer webMvcConfigurer() {}中，将我们自定义的MessageConverter加入中
>
> 重写方法的时候，需要选择这个
>
> ![image-20210705183820808](http://ooszy.cco.vin/img/blog-note/image-20210705183820808.png?x-oss-process=style/pictureProcess1)
>
> configureMessageConverters()此方法会将我们加入的converter直接覆原来的，第二个是在原来的基础上加入
>
> 设置我们自定义的媒体类型

但是springboot内部是继承AbstractHttpMessageConverter抽象类，就比如StringHttpMessageConverter等等，此抽象类最终是实现了`HttpMessageConverter<T>`接口



```java
public class MyConverter implements HttpMessageConverter<Person> {
    @Override
    public boolean canRead (Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite (Class<?> clazz, MediaType mediaType) {
        //canWrite()是否能够读取
        return clazz.isAssignableFrom(Person.class);
    }

    @Override
    public List<MediaType> getSupportedMediaTypes () {
        //我们的converter能够处理什么媒体类型
        return MediaType.parseMediaTypes("application/qsyyke");
    }

    @Override
    public Person read (Class<? extends Person> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    /**
     * 此方法是处理的核心，也就是如果请求的媒体类型是application/qsyyke<p>
     * 我们的converter是如何将Person对象进行输出的
     * 需要将最终的数据，以输出流的形式进行输出
     */
    @Override
    public void write (Person person, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        String msg = "the del data of self converter is: " +
            person.getName() + "年龄: " + person.getAge() + person.getPet();
        outputMessage.getBody().write(msg.getBytes());
    }
}
```



```java
@Bean
public WebMvcConfigurer webMvcConfigurer() {
    return new WebMvcConfigurer() {
        @Override
        public void extendMessageConverters (List<HttpMessageConverter<?>> converters) {
            converters.add(new MyConverter());
        }
    };
}
```

测试

![image-20210705184444526](http://ooszy.cco.vin/img/blog-note/image-20210705184444526.png?x-oss-process=style/pictureProcess1)



现在在所有的converter中，就有了我们自己的converter

![image-20210705184651744](http://ooszy.cco.vin/img/blog-note/image-20210705184651744.png?x-oss-process=style/pictureProcess1)



## 内容协商管理器

内容协商管理器就是能够根据请求参数，返回策略，默认是使用基于请求头的策略，ContentNegotiationManager就是内容协商管理器

```java
此方法就是返回最佳策略
public List<MediaType> resolveMediaTypes(NativeWebRequest request) throws HttpMediaTypeNotAcceptableException {
    for (ContentNegotiationStrategy strategy : this.strategies) {
        List<MediaType> mediaTypes = strategy.resolveMediaTypes(request);
        if (mediaTypes.equals(MEDIA_TYPE_ALL_LIST)) {
            continue;
        }
        return mediaTypes;
    }
    return MEDIA_TYPE_ALL_LIST;
}
```

内容协商管理器中，默认是有两种（开启基于请求参数的情况下）

![image-20210705214237280](http://ooszy.cco.vin/img/blog-note/image-20210705214237280.png?x-oss-process=style/pictureProcess1)

一种是基于请求参数ParameterContentNegotiationStrategy，另一种是基于请求头HeaderContentNegotiationStrategy

默认情况下，基于请求参数时，有两种策略，json和xml，如果我们想要基于请求参数的情况下，将我们自己的媒体类型加入进去，那么就需要改变此内容协商管理器

在此方法中，会根据是基于请求参数还是基于请求头返回请求的媒体类型，通过这两种模式在内容协商管理器中的顺序可以看出，最先是先匹配参数，然后在请求头



## 基于请求参数加入自定义类型

http://localhost:8080/test/response?format=xml通过方式返回的是xml，但是我们也可以加入我们自己的媒体类型`application/qsyyke`，使用qsyyke表示，也就是最终，http://localhost:8080/test/response?format=qsyyke能够返回`application/qsyyke`

![image-20210705215342848](http://ooszy.cco.vin/img/blog-note/image-20210705215342848.png?x-oss-process=style/pictureProcess1)

在这个内容协商管理器中，基于请求参数时，可以有三个选择，json,xml.qsyyke，那么我们传入http://localhost:8080/test/response?format=qsyyke时，程序就会知道给我们返回什么



在WebMvcConfigurer中，有一个方法configureContentNegotiation(ContentNegotiationConfigurer configurer)可以配置内容协商管理器，但是这个方法会覆盖原来默认的，也就是如果我们只加入基于参数的时候，基于请求头的媒体类型，就不能使用，



```java
@Bean
public WebMvcConfigurer webMvcConfigurer() {
    return new WebMvcConfigurer() {
        //配置基于参数的内容协商管理器


        @Override
        public void configureContentNegotiation (ContentNegotiationConfigurer configurer) {
            Map<String, MediaType> mediaTypes = new HashMap<>();
            mediaTypes.put("json",MediaType.APPLICATION_JSON);
            mediaTypes.put("xml",MediaType.APPLICATION_XML);
            mediaTypes.put("qsyyke",MediaType.parseMediaType("application/qsyyke"));

            ParameterContentNegotiationStrategy parameter =
                new ParameterContentNegotiationStrategy(mediaTypes);
            configurer.strategies(Arrays.asList(parameter));
        }
    };
}
```

此ContentNegotiationConfigurer对象还可以更改基于请求参数的名字，默认是format

![image-20210705220414166](http://ooszy.cco.vin/img/blog-note/image-20210705220414166.png?x-oss-process=style/pictureProcess1)



测试

最终，基于请求参数的内容协商管理器中，我们自己的媒体类型就被加入进去了

![image-20210705221720822](http://ooszy.cco.vin/img/blog-note/image-20210705221720822.png?x-oss-process=style/pictureProcess1)

但是现在，基于请求头的内容协商就没在了，也就是我们通过浏览器或者postman发送application/json,application/xml,application/qsyyke都不会有基于请求参数的内容协商，那么最后找到的就是`*/*`，也就是无论你accept是什么，全部都是返回json，测试成功



问题

存在的问题就是我们不能使用基于请求头的方式，因为尽管我们application/json，最终返回的都是`*/*`（没有发送基于参数）

![image-20210705222318388](http://ooszy.cco.vin/img/blog-note/image-20210705222318388.png?x-oss-process=style/pictureProcess1)



那么造成这种问题的原因就是因为我们的内容协商管理器中，



![image-20210705223221583](http://ooszy.cco.vin/img/blog-note/image-20210705223221583.png?x-oss-process=style/pictureProcess1)此参数传递的是一个数组，但是我们在自定义的时候，就只传入基于参数的，基于请求头的方式没有传入，所以解决的办法就是就将基于请求头的内容协商管理器加入

片段

```java
mediaTypes.put("qsyyke",MediaType.parseMediaType("application/qsyyke"));

HeaderContentNegotiationStrategy header =
    new HeaderContentNegotiationStrategy();
ParameterContentNegotiationStrategy parameter =
    new ParameterContentNegotiationStrategy(mediaTypes);
configurer.strategies(Arrays.asList(parameter,header));
```

![image-20210705223519645](http://ooszy.cco.vin/img/blog-note/image-20210705223519645.png?x-oss-process=style/pictureProcess1)

现在就完成了，基于请求头，和参数都存在，我们也还可以自定义我们自己的基于什么的内容协商管理器，





# 视图解析源码分析

```java
public String doLogin(Model model, User user, HttpSession session) {
    if (StringUtils.hasLength(user.getUsername())
        && "123456".equals(user.getPassword())) {
        //验证成功
        session.setAttribute("loginInfo",user);
        session.setAttribute("msg","登录成功");
        return "redirect:main";
    }else {
        session.setAttribute("msg","账号或密码错误");
        return "login";
    }
}
```

当执行我们的方法的时候，如果我们这里有我们自己的对象作为参数对象，那么会把此参数对象放入这个model中，尽管我们没有放入

![image-20210706162705557](http://ooszy.cco.vin/img/blog-note/image-20210706162705557.png?x-oss-process=style/pictureProcess1)

执行完这个目标方法之后，就会得到一个返回值

![image-20210706162939366](http://ooszy.cco.vin/img/blog-note/image-20210706162939366.png?x-oss-process=style/pictureProcess1)

得到返回结果之后，会根据此结果，从15个返回值处理器中，得到一个处理此返回值的处理器

![image-20210706163257116](http://ooszy.cco.vin/img/blog-note/image-20210706163257116.png?x-oss-process=style/pictureProcess1)

最终这个返回值`redirect:main`是由ViewNameMethodReturnValueHandler进行处理的

handleReturnValue()处理返回值的方法中，框架会将方法参数中的model对象保存至该对象中

![image-20210706164255199](http://ooszy.cco.vin/img/blog-note/image-20210706164255199.png?x-oss-process=style/pictureProcess1)

```java
public void handleReturnValue(@Nullable Object returnValue, MethodParameter returnType,
                              ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {

    if (returnValue instanceof CharSequence) {
        String viewName = returnValue.toString();
        mavContainer.setViewName(viewName);
        if (isRedirectViewName(viewName)) {
            mavContainer.setRedirectModelScenario(true);
        }
    }
    else if (returnValue != null) {
        // should not happen
        throw new UnsupportedOperationException("Unexpected return type: " +
                                                returnType.getParameterType().getName() + " in method: " + returnType.getMethod());
    }
}
```

`if (isRedirectViewName(viewName))`会根据方法的返回值判断是不是重定向，判断方法为，所有以redirect:开头的返回值都是重定向

```java
protected boolean isRedirectViewName(String viewName) {
    return (PatternMatchUtils.simpleMatch(this.redirectPatterns, viewName) || viewName.startsWith("redirect:"));
}
```



任何目标方法执行完毕，都会返回一个ModelAndView对象

```java
ModelMap model = mavContainer.getModel();
ModelAndView mav = new ModelAndView(mavContainer.getViewName(), model, mavContainer.getStatus());
```



`DispatcherServlet`中的processDispatchResult(processedRequest, response, mappedHandler, mv, dispatchException);决定了如何响应数据，也就是如何渲染页面

render(mv, request, response)此方法就是渲染页面

![image-20210706170249213](http://ooszy.cco.vin/img/blog-note/image-20210706170249213.png?x-oss-process=style/pictureProcess1)

![image-20210706170302289](http://ooszy.cco.vin/img/blog-note/image-20210706170302289.png?x-oss-process=style/pictureProcess1)



# 拦截器源码分析

1. 根据当前的请求，得到一个请求处理器，在此请求处理器中，存在3个拦截器，一个是我们的，另外两个是程序本身的，另外两个所有的请求都会经过那两个拦截器

    ![image-20210706190306100](http://ooszy.cco.vin/img/blog-note/image-20210706190306100.png?x-oss-process=style/pictureProcess1)

2. 得到请求适配器之后，会执行拦截器的preHandle，会根据拦截器中，该predhandle方法的放行结果，进行判断，此判断方法为

    ```java
    boolean applyPreHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        for (int i = 0; i < this.interceptorList.size(); i++) {
            HandlerInterceptor interceptor = this.interceptorList.get(i);
            if (!interceptor.preHandle(request, response, this.handler)) {
                triggerAfterCompletion(request, response, null);
                return false;
            }
            this.interceptorIndex = i;
        }
        return true;
    }
    ```

    

    ![image-20210706190634886](http://ooszy.cco.vin/img/blog-note/image-20210706190634886.png?x-oss-process=style/pictureProcess1)

    当前拿到的是我们自己的拦截器，就会进入preHandle()方法执行，因为不满足条件，返回false，但是如果拦截器不满足条件并且在此处进行了重定向操作，那么就会直接跳出此循环，会发生此异常

    ![image-20210706191418285](http://ooszy.cco.vin/img/blog-note/image-20210706191418285.png?x-oss-process=style/pictureProcess1)

    但是如果没有进行重定向操作，就不会发生异常，返回执行结果

    如果拦截器返回false，那么就会执行，当前拦截器之后的另外拦截器的afterCompletion()方法

    ```java
    boolean applyPreHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        for (int i = 0; i < this.interceptorList.size(); i++) {
            HandlerInterceptor interceptor = this.interceptorList.get(i);
            if (!interceptor.preHandle(request, response, this.handler)) {
                triggerAfterCompletion(request, response, null);
                return false;
            }
            this.interceptorIndex = i;
        }
        return true;
    }
    ```

    如果此拦截器返回false，那么就会直接return，目标方法不会被执行

    ![image-20210706192713292](http://ooszy.cco.vin/img/blog-note/image-20210706192713292.png?x-oss-process=style/pictureProcess1)

    所以，`preHandle()方法是在目标方法执行之前就执行了`



`拦截器整体执行顺序为`

> 1. 发送请求，获取请求处理器，执行preHandle()方法
>     - preHandle()返回true，执行目标方法，目标方法执行，渲染视图完成之后，执行postHandle()方法
>     - 返回false，如果此preHandle()期间发生异常，直接退出，执行当前拦截器之后的拦截器的afterCompletion()方法
> 2. 执行postHandle()方法
> 3. 执行postHandle()方法

![image-20210706195152823](http://ooszy.cco.vin/img/blog-note/image-20210706195152823.png?x-oss-process=style/pictureProcess1)

![image-20210706195158621](http://ooszy.cco.vin/img/blog-note/image-20210706195158621.png?x-oss-process=style/pictureProcess1)

![image-20210706195204164](http://ooszy.cco.vin/img/blog-note/image-20210706195204164.png?x-oss-process=style/pictureProcess1)

![image-20210706193231742](http://ooszy.cco.vin/img/blog-note/image-20210706193231742.png?x-oss-process=style/pictureProcess1)



`无论怎样，afterCompletion()方法永远都会执行`





# 文件上传源码分析

1. 发送请求的时候，就会进行是否是文件上传检查doDispatch()

    ```java
    processedRequest = checkMultipart(request);
    
    可以看出，判断是否是文件上传项，是通过ContentType值进行判断的，所以文件上传必须要multipart/form-data
    public boolean isMultipart(HttpServletRequest request) {
        return StringUtils.startsWithIgnoreCase(request.getContentType(), "multipart/");
    }
    ```

    文件上传的将解析器为StandardServletMultipartResolver，只有一个解析器

    此方法checkMultipart(request)检查是之后，会返回一个StandardMultipartHttpServletRequest对象



checkMultipart(HttpServletRequest request)

![image-20210707135106835](http://ooszy.cco.vin/img/blog-note/image-20210707135106835.png?x-oss-process=style/pictureProcess1)





# 异常处理

springboot已经对发生的异常进行了处理

By default, Spring Boot provides an `/error` mapping that handles all errors in a sensible way, and it is registered as a “global” error page in the servlet container. For machine clients, it produces a JSON response with details of the error, the HTTP status, and the exception message. For browser clients, there is a “whitelabel” error view that renders the same data in HTML format (to customize it, add a `View` that resolves to `error`).

There are a number of `server.error` properties that can be set if you want to customize the default error handling behavior. See the [“Server Properties”](http://ooszy.cco.vin/img/blog-note/https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#application-properties.server) section of the Appendix.

To replace the default behavior completely, you can implement `ErrorController` and register a bean definition of that type or add a bean of type `ErrorAttributes` to use the existing mechanism but replace the contents.



If you want to display a custom HTML error page for a given status code, you can add a file to an `/error` directory. Error pages can either be static HTML (that is, added under any of the static resource directories) or be built by using templates. The name of the file should be the exact status code or a series mask.

For example, to map `404` to a static HTML file, your directory structure would be as follows:

![image-20210707140634707](http://ooszy.cco.vin/img/blog-note/image-20210707140634707.png?x-oss-process=style/pictureProcess1)



从描述中，可以看出，springboot已经对所有的异常进行了映射，也就是说，如果发生异常的时候，就会跳转到一个`/error`映射路径，在次映射路径中，可以看到错误信息

此错误信息对于浏览器，会返回一个白页，对于客户端，像postman，会返回一个接送

我们也可以自定义此error行为，可以实现ErrorController，

从上图可以看出，我们也可以替换springboot的404，500等页面

方法

> 在static或者是templates下，建立一个文件夹error，此文件下，放404，500等页面，因为400，404等页面的内容一样，我们对404.html命名的时候，可以4xx.html，这样只要响应状态码以4开头的，都用这个页面显示，500也是一样



客户端返回错误信息

![image-20210707141733715](http://ooszy.cco.vin/img/blog-note/image-20210707141733715.png?x-oss-process=style/pictureProcess1)

浏览器返回错误信息

![image-20210707141754064](http://ooszy.cco.vin/img/blog-note/image-20210707141754064.png?x-oss-process=style/pictureProcess1)

替换之后

![image-20210707141958989](http://ooszy.cco.vin/img/blog-note/image-20210707141958989.png?x-oss-process=style/pictureProcess1)

![image-20210707142009583](http://ooszy.cco.vin/img/blog-note/image-20210707142009583.png?x-oss-process=style/pictureProcess1)

`替换错误页面，只能替换浏览器访问的页面，对于客户端，返回的数据，还是json`





## 源码分析

ErrorMvcAutoConfiguration配置类分析

1. 在容器中，放入了一个DefaultErrorAttributes组件，在没有这个组件的情况下

    ```java
    DefaultErrorAttributes implements ErrorAttributes, HandlerExceptionResolver, Ordered{}
    ```

    在此类中保存了返回错误json数据时，可以返回哪些数据

    ```java
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = getErrorAttributes(webRequest, options.isIncluded(Include.STACK_TRACE));
        if (!options.isIncluded(Include.EXCEPTION)) {
            errorAttributes.remove("exception");
        }
        if (!options.isIncluded(Include.STACK_TRACE)) {
            errorAttributes.remove("trace");
        }
        if (!options.isIncluded(Include.MESSAGE) && errorAttributes.get("message") != null) {
            errorAttributes.remove("message");
        }
        if (!options.isIncluded(Include.BINDING_ERRORS)) {
            errorAttributes.remove("errors");
        }
        return errorAttributes;
    }
    ```

    

2. 放入BasicErrorController，没有的情况下

    ```java
    @RequestMapping("${server.error.path:${error.path:/error}}")
    public class BasicErrorController extends AbstractErrorController {}
    ```

    server.error.path是配置修改，error.path:/error是默认，也就是此controller，默认是处理/error的请求，@RequestMapping写在类上，表示此controller中的所有的请求都是/error/...

    在此controller中，一共有两个请求，一个是返回页面的，一个是返回json的

    

    ```java
    //返回页面
    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {}
    
    //返回json
    @RequestMapping
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {}
    
    public static final String TEXT_HTML_VALUE = "text/html";
    ```





3. 在容器中，放入一个默认视图解析器DefaultErrorViewResolver，也是没有的情况下，此默认视图解析器，能够解析以下异常

    ![image-20210707154310465](http://ooszy.cco.vin/img/blog-note/image-20210707154310465.png?x-oss-process=style/pictureProcess1)

    此类中的，有两个方法

    ```java
    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
        ModelAndView modelAndView = resolve(String.valueOf(status.value()), model);
        if (modelAndView == null && SERIES_VIEWS.containsKey(status.series())) {
            modelAndView = resolve(SERIES_VIEWS.get(status.series()), model);
        }
        return modelAndView;
    }
    
    private ModelAndView resolve(String viewName, Map<String, Object> model) {
        String errorViewName = "error/" + viewName;
        TemplateAvailabilityProvider provider = this.templateAvailabilityProviders.getProvider(errorViewName,
                                                                                               this.applicationContext);
        if (provider != null) {
            return new ModelAndView(errorViewName, model);
        }
        return resolveResource(errorViewName, model);
    }
    ```

    视图解析方法resolveErrorView()调用ModelAndView resolve()得到一个视图，并且String errorViewName = "error/" + viewName，也就是说，如果我们发生404，那么这个视图名就是`error/404`，那么就会在文件中，找`error/404.html`，这个也就是解释了，我们放置404，500页面的时候，需要在静态文件夹下，建立error文件夹

4. 在容器中，放入一个视图(容器中，没有此bean名为error时)，此视图的名字为error

```java
@Bean(name = "error")
@ConditionalOnMissingBean(name = "error")
public View defaultErrorView() {
    return this.defaultErrorView;
}
```

此视图返回一个默认的视图，视图是和视图解析器一起的，所以在此配置类中，又放入了一个视图解析器，此视图，是根据视图名进行解析

```java
public BeanNameViewResolver beanNameViewResolver() {}
```

执行的顺序可以理解为

> 当浏览器发送错误请求(404,500)等，会请求/error，此请求会找视图(因为此请求返回的是一个视图return (modelAndView != null) ? modelAndView : new ModelAndView("error", model))，容器中，有一个beanname的视图解析器beanNameViewResolver，此视图解析器，会解析视图error，并且此视图error在容器中存在，此视图返回一个默认的视图(return this.defaultErrorView;)，此默认视图的渲染逻辑为(render)
>
> ![image-20210707153316466](http://ooszy.cco.vin/img/blog-note/image-20210707153316466.png?x-oss-process=style/pictureProcess1)
>
> 这个就是我们看到的白页



`如果我们想要自定义错误页，因为错误页最终是去找一个名为error的视图，所以我们可以自己创建一个View,beanname=error的视图`



> `如果我们想要自定义返回json的数据，那么我们就要自定义DefaultErrorAttributes`
>
> 如果我们想要自定义浏览器的视图页面，那么就要自定义一个名为error的视图View



## 开始分析

1. `mv = ha.handle(processedRequest, response, mappedHandler.getHandler())`此是执行目标方法，执行目标方法期间，如果有任何的异常，都会被catch

2. 之后，执行渲染页面processDispatchResult()

3. 因为执行目标方法需要返回一个视图，但是发生异常，此视图就为空，在渲染中，如果有异常，就会执行mv = processHandlerException(request, response, handler, exception)再次获取一个视图，这是一个核心方法

    - 在processDispatchResult()方法中，因为发生了异常，所以会调用processHandlerException()，在此方法中，会使用for循环方式，遍历出所有的异常处理器，看哪个异常能够处理

        ![image-20210707163223651](http://ooszy.cco.vin/img/blog-note/image-20210707163223651.png?x-oss-process=style/pictureProcess1)

    - 但是没有任何一个异常能够处理，最后会抛出一个异常，因为在运行期间，只要抛出异常，就会被doDispatch()方法中的catch捕获到，这个请求就执行结束了，`但是结束了这个请求之后，会立马有一个/error请求被重新发起`

        ![image-20210707163503328](http://ooszy.cco.vin/img/blog-note/image-20210707163503328.png?x-oss-process=style/pictureProcess1)



### /error请求

因为上一个请求中，发生了异常，没有哪个异常处理器能够处理，所以就将这个异常转发到`/error`进行处理

因为在Sproingboot的内部，有一个`BasicErrorController`，此controller专门处理/error的请求，所以这个/error请求会交给BasicErrorController进行处理，执行其中的目标方法(会根据浏览器还是客户端进行判断)

#### 浏览器发送



1. 如果是浏览器发送的请求，执行errorHtml() --->  resolveErrorView()处理错误视图，因为所有的错误视图，都有一个默认的异常处理器进行处理DefaultErrorViewResolver

2. 执行resolve()  --->执行resolveResource(errorViewName, model)得到一个视图

3. 

DefaultErrorViewResolver中的方法

```java
String viewName = /error/状态码.html
private ModelAndView resolveResource(String viewName, Map<String, Object> model) {
    for (String location : this.resources.getStaticLocations()) {
        try {
            Resource resource = this.applicationContext.getResource(location);
            resource = resource.createRelative(viewName + ".html");
            if (resource.exists()) {
                return new ModelAndView(new HtmlResourceView(resource), model);
            }
        }
        catch (Exception ex) {
        }
    }
    return null;
}
```

此方法会从当前项目的所有静态目录中，for拿到每一个静态文件夹，

![image-20210707170108665](http://ooszy.cco.vin/img/blog-note/image-20210707170108665.png?x-oss-process=style/pictureProcess1)

然后在当前拿到的静态文件夹上，判断此文件是否存在(`静态目录/error/状态码.html`)

因为我们将错误也放在templates下，此templates不是静态目录，所以最终会返回null，所以此方法得到的视图是一个null

4. 再次调用resolve()方法，但是这个时候，会返回一个新的ModelAndView，return new ModelAndView(errorViewName, model)，errorViewName=/error/5xx,

    > 无论在return (modelAndView != null) ? modelAndView : new ModelAndView("error", model)之前，是否有一个视图，最终都会返回一个视图



#### 客户端发送

客户端发送的话，那么就会执行BasicErrorController中的public ResponseEntity<Map<String, Object>> error(HttpServletRequest request)方法

最终会执行到

```java
protected Map<String, Object> getErrorAttributes(HttpServletRequest request, ErrorAttributeOptions options) {
    WebRequest webRequest = new ServletWebRequest(request);
    return this.errorAttributes.getErrorAttributes(webRequest, options);
}
```

此方法返回的就是一个map集合，其中保存了异常信息

![image-20210707172857235](http://ooszy.cco.vin/img/blog-note/image-20210707172857235.png?x-oss-process=style/pictureProcess1)

这个就是我们看到的json

 

## 1.几种处理异常的方法

### 使用@ExceptionHandler注解

```java
@Slf4j
@ControllerAdvice
public class MyException {

    @ExceptionHandler({ArithmeticException.class,NullPointerException.class})
    public String handleMathException(Exception e) {
        log.info("异常{}",e.getMessage());
        return "/";
    }
}
```

@ControllerAdvice会将该bean加入到容器中

如果运行期间，发生了ArithmeticException.class,NullPointerException.class，那么就会使用该方法进行处理，并且返回的是一个视图



#### 源码分析

因为defaultErrorAttributes不能处理该异常，但是另一个就能处理，因为我们使用了@ExceptionHandler注解，在类HandlerExceptionResolverComposite中的public ModelAndView resolveException()方法

![image-20210707183932640](http://ooszy.cco.vin/img/blog-note/image-20210707183932640.png?x-oss-process=style/pictureProcess1)



因为ExceptionHandlerExceptionResolver能够处理@ExceptionHandler注解标注的异常，所以这个就会执行上面的异常



### 2. @ResponseStatus

```java
@ResponseStatus(value = HttpStatus.FORBIDDEN,reason = "用户数量太多")
public class ToManyPeople extends RuntimeException {

    public ToManyPeople (String message) {
        super(message);
    }
}
```

使用：

在需要使用此异常的地方，使用throw抛出此异常就可以

```java
@GetMapping("/pricing_table")
public String pricing_table() {
    throw new ToManyPeople("太多人异常");
}
```

@ResponseStatus(value = HttpStatus.FORBIDDEN,reason = "用户数量太多")中的value就是响应状态码

reason就是message信息

原理

![image-20210707194634910](http://ooszy.cco.vin/img/blog-note/image-20210707194634910.png?x-oss-process=style/pictureProcess1)

因为存在此异常处理器，此异常处理器就是处理，标有@ResponseStatus()的异常，ResponseStatusExceptionResolver ，把responsestatus注解的信息底层调用response.sendError(statusCode, resolvedReason)；tomcat发送的/error

![image-20210707195033845](http://ooszy.cco.vin/img/blog-note/image-20210707195033845.png?x-oss-process=style/pictureProcess1)



tomcat发送的/error最终会交给spring进行处理



### 3.实现HandlerExceptionResolver接口

我们也可以实现此接口，从而自定义异常处理

```java
@Component
public class MyCustomerHandlerExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException (HttpServletRequest request,
                                          HttpServletResponse response,
                                          Object handler,
                                          Exception ex) {
        try {
            response.sendError(511,"我喜欢的异常");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView();
    }
}
```

因为我们的异常已经加入到容器中，所以发生异常时，对异常的处理，就可以看到我们自己的异常处理

![image-20210707200153847](http://ooszy.cco.vin/img/blog-note/image-20210707200153847.png?x-oss-process=style/pictureProcess1)

从这上面可以看出，我们异常是第三个，所以想要最先使用我们的异常进行处理，就需要`@Order()`注解

```java
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Component
public class MyCustomerHandlerExceptionResolver implements HandlerExceptionResolver {}
```

这样在词处理的时候，就会优先使用我们的异常处理器



# 原生组件注入

如果想要在spring应用中，使用tomcat原生的servlet，那么就有两种方式可以做到



## 方式一

```java
@WebServlet("/my")
public class MyServlet extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("myservlet");
    }
}

@WebFilter(urlPatterns = {"/css/*","/my"})
public class MyFilter implements Filter {
    @Override
    public void doFilter (ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("执行原生过滤器");
    }

    @Override
    public void init (FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy () {

    }
}

@WebListener
public class MyServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized (ServletContextEvent sce) {
        System.out.println("监听器工作");
    }

    @Override
    public void contextDestroyed (ServletContextEvent sce) {

    }
}
```

如果要在spring应用中，使用原生的servlet，过滤器，监听器的时候，需要在这些类上面加上@WebServlet("/my")，@WebListener，@WebFilter(urlPatterns = {"/css/*","/my"})注解，并且要在主类上，使用@ServletComponentScan(basePackages = "vin.cco.qsyyke")注解告诉spring，扫描哪些包中的，原生组件



> `如果spring中，配置了拦截器的话，但是使用原生servleturl不会被拦截`



## 方式二

也可以使用这种方式，使用注册的方式

If convention-based mapping is not flexible enough, you can use the `ServletRegistrationBean`, `FilterRegistrationBean`, and `ServletListenerRegistrationBean` classes for complete control.



```java
@Configuration
public class MyRegistrationConfig {

    @Bean
    public ServletRegistrationBean registrationServlet() {


        ServletRegistrationBean registrationBean =
                new ServletRegistrationBean(new MyServlet(),"/re","/re1");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean filter() {
        FilterRegistrationBean filterRegistrationBean =
                new FilterRegistrationBean(new MyFilter());
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/re","/re1"));

        return filterRegistrationBean;
    }

    @Bean
    public ServletListenerRegistrationBean li() {
        ServletListenerRegistrationBean bean =
        new ServletListenerRegistrationBean(new MyServletContextListener());
        return bean;
    }
}
```

这种方式是通过向组件中，注册原生的各个Bean



# springboot定制化组件的几种方式

我们能后定制化组件，如果我们没有定制化的，那么就会使用自动配置

官方描述

If you want to keep those Spring Boot MVC customizations and make more [MVC customizations](http://ooszy.cco.vin/img/blog-note/https://docs.spring.io/spring-framework/docs/5.3.8/reference/html/web.html#mvc) (interceptors, formatters, view controllers, and other features), you can add your own `@Configuration` class of type `WebMvcConfigurer` but **without** `@EnableWebMvc`.

If you want to provide custom instances of `RequestMappingHandlerMapping`, `RequestMappingHandlerAdapter`, or `ExceptionHandlerExceptionResolver`, and still keep the Spring Boot MVC customizations, you can declare a bean of type `WebMvcRegistrations` and use it to provide custom instances of those components.

If you want to take complete control of Spring MVC, you can add your own `@Configuration` annotated with `@EnableWebMvc`, or alternatively add your own `@Configuration`-annotated `DelegatingWebMvcConfiguration` as described in the Javadoc of `@EnableWebMvc`.



## 1.@Configuration+WebMvcConfigurer

如果想要定制springmvc的组件，比如拦截器，视图，或者是其他的web特征，那么就编写一个自定义配置类，但是在这个配置类上，不能有`@EnableWebMvc`，因为此注解是全面接管的意思，添加的话，自动配置的所有功能都不能使用

```java
//添加拦截器功能就是使用这种方式实现的
@Configuration
public class LoginConf implements WebMvcConfigurer {

    @Override
    public void addInterceptors (InterceptorRegistry registry) {
        //"*/css/**","*/js/**","*/images/**","*/fonts/**"
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login","/","/css/**","/js/**","/images/**","/fonts/**");

    }
}
```

如果存在`@EnableWebMvc`，那么我们就要定义springmvc底层的行为

### 测试

当使用`@EnableWebMvc`，那么我们访问静态资源静不能访问到，因为已经全面接管了mvc

```java
@EnableWebMvc
@Configuration
public class LoginConf implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers (ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/aa/**")
                .addResourceLocations("classpath:/static/");
    }
}
```

这个就是定制静态资源的访问路径，所有以`/aa/**`开头的请求，都会在`/static/`下去找

http://localhost:8080/aa/css/bootstrap-fileupload.min.css

但是如果我们没有加上@EnableWebMvc，并且又重写了addResourceHandlers()，那么可以直接访问静态资源，也可以使用我们上面的`/aa/...`的方式，这个和在配置类中配置静态资源的路径不同，配置类中是全面替换，这个是加上

## 2.修改请求映射器，适配器，解析器...

如果想要修改`RequestMappingHandlerMapping`, `RequestMappingHandlerAdapter`, or `ExceptionHandlerExceptionResolver`,但是仍然需要springmvc的一些功能，那么就可以创建一个bean，此bean返回 `WebMvcRegistrations` 就可以

```java
@Bean
public WebMvcRegistrations webMvcRegistrations() {

}
```

在这里可以修改以下功能，但是不推荐修改![image-20210708080730178](http://ooszy.cco.vin/img/blog-note/image-20210708080730178.png?x-oss-process=style/pictureProcess1)



## 3.完全控制`@Configuration` + `@EnableWebMvc`

如果想要完全控制spring mvc，那么就需要编写自定义配置类，加上`@EnableWebMvc`注解，所以如果存在`@EnableWebMvc`，那么我么就要在此

测试，

## 4.修改配置文件

修改配置文件是最推荐使用的方法，直接在配置文件中，修改所需的功能，不影响其他功能的使用



## 5.实现xxxCustomizer

如果想要自定义嵌入servlet容器，那么可以修改配置文件，或者是实现 WebServerFactoryCustomizer接口

Common servlet container settings can be configured by using Spring `Environment` properties. Usually, you would define the properties in your `application.properties` or `application.yaml` file.

Common server settings include:

- Network settings: Listen port for incoming HTTP requests (`server.port`), interface address to bind to `server.address`, and so on.
- Session settings: Whether the session is persistent (`server.servlet.session.persistent`), session timeout (`server.servlet.session.timeout`), location of session data (`server.servlet.session.store-dir`), and session-cookie configuration (`server.servlet.session.cookie.*`).
- Error management: Location of the error page (`server.error.path`) and so on.
- [SSL](http://ooszy.cco.vin/img/blog-note/https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto.webserver.configure-ssl)
- [HTTP compression](http://ooszy.cco.vin/img/blog-note/https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto.webserver.enable-response-compression)

Spring Boot tries as much as possible to expose common settings, but this is not always possible. For those cases, dedicated namespaces offer server-specific customizations (see `server.tomcat` and `server.undertow`). For instance, [access logs](http://ooszy.cco.vin/img/blog-note/https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto.webserver.configure-access-logs) can be configured with specific features of the embedded servlet container.



If you need to programmatically configure your embedded servlet container, you can register a Spring bean that implements the `WebServerFactoryCustomizer` interface. `WebServerFactoryCustomizer` provides access to the `ConfigurableServletWebServerFactory`, which includes numerous customization setter methods. The following example shows programmatically setting the port:



```java
@Component
public class MyWebServerFactoryCustomizer implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    @Override
    public void customize(ConfigurableServletWebServerFactory server) {
        server.setPort(9000);
    }
}
```

所以如果想要定制哪方面的功能，我们也可以实现`xxxCustomizer`接口



## 定制化原理

使用`@EnableWebMvc`，那么就会导入`@Import(DelegatingWebMvcConfiguration.class)`，此DelegatingWebMvcConfiguration的作用就只是保证了springmvc的基本使用，（这个springmvc基本的使用，是在WebMvcConfigurationSupport中定义的），DelegatingWebMvcConfiguration继承了WebMvcConfigurationSupport，也就继承了springmvc的基本使用保证，DelegatingWebMvcConfiguration会把容器中，所有的WebMvcConfigurer拿来使用，无论有多少个，所有定制功能的集合都是这些WebMvcConfigurer整合起来的，但是对于静态资源的配置，是在WebMvcAutoConfiguration中定义的，但是此自动配置要生效的前提是@ConditionalOnMissingBean({WebMvcConfigurationSupport.class})，我们有`@EnableWebMvc`，就一定会有WebMvcConfigurationSupport.class，所以自动配置就不会生效，也就是`@EnableWebMvc`导致了WebMvcAutoConfiguration没有生效
