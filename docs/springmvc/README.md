# spring mvc介绍

什么是spring mvc？

SpringMVC 也叫 Spring web mvc。是 Spring 框架的一部分，是在 Spring3.0 后发布的。

我们也可以将其想象成是spring，只是这个是用于web开发，在使用的时候，也会使用到spring的很多的功能



SpringMVC就是一个Spring。 Spring是容器，ioc能够管理对象，使用`<bean>`, @Component, @Repository, @Service, @Controller
SpringMVC能够创建对象， 放入到容器中（SpringMVC容器）， springmvc容器中放的是控制器对象，我们要做的是 使用@Contorller创建控制器对象， 把对象放入到springmvc容器中， 把创建的对象作为控制器使用
这个控制器对象能接收用户的请求，显示处理结果，就当做是一个servlet使用。

使用@Controller注解创建的是一个普通类的对象， 不是Servlet。 springmvc赋予了控制器对象一些额外的功能，这些功能包括，处理用户发送的请求等等

web开发底层是servlet， springmvc中有一个对象是Servlet ： DispatherServlet(中央调度器)
DispatherServlet: 负责接收用户的所有请求，用户把请求给了DispatherServlet， 之后DispatherServlet把请求转发给我们的Controller对象， 最后是Controller对象处理请求。

`DispatherServlet`这个对象其实就是一个servlet对象，用于接收用户的请求，然后将这些请求发送给`@Controller`创建的对象进行使用

## 优点

**1.**基于 **MVC** **架构**

基于 MVC 架构，功能分工明确。解耦合，

**2.**容易理解，上手快；使用简单。

就可以开发一个注解的 SpringMVC 项目，SpringMVC 也是轻量级的，jar 很小。不依赖的

特定的接口和类。

**3.** **作 为** **Spring** **框 架 一 部 分 ， 能 够 使 用** **Spring** **的** **IoC** **和** **Aop** **。 方 便 整 合**

**Strtus,MyBatis,Hiberate,JPA** **等其他框架。**

**4.SpringMVC** **强化注解的使用，在控制器，**Service**，**Dao**, **都可以使用注解。方便灵活。**

使用@Controller 创建处理器对象,@Service 创建业务对象，@Autowired 或者@Resource

在控制器类中注入 Service, Service 类中注入 Dao。







# 第一个实例程序

1. 新建maven项目

2. 导入依赖，servlet和spring-webmvc

    ```xml
    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.11</version>
            <scope>test</scope>
        </dependency>
    
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.3.6</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>4.0.1</version>
            <scope>provided</scope>
        </dependency>
    
        <dependency>
            <groupId>javax.servlet.jsp</groupId>
            <artifactId>javax.servlet.jsp-api</artifactId>
            <version>2.3.1</version>
            <scope>compile</scope>
        </dependency>
    
        <dependency>
            <groupId>com.thetransactioncompany</groupId>
            <artifactId>cors-filter</artifactId>
            <version>2.10</version>
        </dependency>
    
    </dependencies>
    ```

3. 在web.xml中注册springmvc框架的核心对象DispatcherServlet

    - DispatcherServlet叫做中央调度器， 是一个servlet， 它的父类是继承HttpServlet
    - DispatcherServlet页叫做前端控制器（front controller）
    - DispatcherServlet负责接收用户提交的请求， 调用其它的控制器对象，并把请求的处理结果显示给用户

4. 创建一个发起请求的页面 index.jsp

5. 创建控制器(处理器)类

    - 在类的上面加入@Controller注解，创建对象，并放入到springmvc容器中
    - 在类中的方法上面加入@RequestMapping注解。

6. 创建一个作为结果的jsp，显示请求的处理结果。

7. 创建springmvc的配置文件（spring的配置文件一样）

    - 声明组件扫描器， 指定@Contorller注解所在的包名
    - 声明视图解析器。帮助处理视图的。



## 配置`web.xml`文件

- 注册核心对象DispatcherServlet

```
<servlet>
    <servlet-name>myWeb</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>

    <init-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:springmvc.xml</param-value>
    </init-param>

    <load-on-startup>1</load-on-startup>
</servlet>

<servlet-mapping>
    <servlet-name>myWeb</servlet-name>
    <url-pattern>*.do</url-pattern>
</servlet-mapping>
```

如果没有这个`<init-param>`标签声明springmvc配置文件的位置的话（配置文件就是一个spring配置文件），那么就会报错

```java
IOException parsing XML document from ServletContext resource [/WEB-INF/myWeb-servlet.xml]; nested exception is java.io.FileNotFoundException: Could not open ServletContext resource [/WEB-INF/myWeb-servlet.xml]
```

因为我们这个`<servlet-name>`的值为myWeb，所以在注册这个对象的时候，需要使用到springmvc配置文件，但是默认是在`/WEB-INF/myWeb-servlet.xml`中寻找，并且寻找的配置文件的名字就是`<servlet-name>`值加上`-servlet.xml`，所以我们需要使用`<init-param>`声明这个配置文件的位置，声明之后，注册这个对象，就会使用`<param-value>classpath:springmvc.xml</param-value>`这个文件

![image-20210523172520633](http://ooszy.cco.vin/img/blog-note/image-20210523172520633.png?x-oss-process=style/pictureProcess1)





他们的位置，不能颠倒，否则会报错

## `<load-on-startup>`

在`<servlet/>`中添加`<load-on-startup/>`的作用是，标记是否在Web服务器（这里是Tomcat）

启动时会创建这个 Servlet 实例，即是否在 Web 服务器启动时调用执行该 Servlet 的 init()方

法，而不是在真正访问时才创建。

它的值必须是一个整数。 

➢ 当值大于等于 0 时，表示容器在启动时就加载并初始化这个 servlet，数值越小，该 Servlet

的优先级就越高，其被创建的也就越早；

➢ 当值小于 0 或者没有指定时，则表示该 Servlet 在真正被使用时才会去创建。 

➢ 当值相同时，容器会自己选择创建顺序。 





## `<servlet-mapping>`

`<servlet-name>myWeb</servlet-name>`值就是上面的值

```
<servlet>
    <servlet-name>myWeb</servlet-name>
```



`<url-pattern>*.do</url-pattern>`用于配置哪些文件的请求，交给这个`DispatcherServlet`对象进行处理，也就是比如，用户方法`some.do`文件，那么就是这个对象进行处理







## 创建Controller类

```java
@Controller
public class MyController {

    @RequestMapping(value = "/some.do")
    public ModelAndView doSome() {
        System.out.println("请求了");
        ModelAndView mv = new ModelAndView();
        mv.addObject("name","chuchen");
        mv.addObject("age",21);

        mv.setViewName("/show.jsp");
        return mv;
    }
}
```



使用注解`@Controller`创建一个处理器对象，也叫做后端控制器（back controller），`能够接收请求的都是处理器对象，比如servlet`，这个处理器对象被放在springmvc容器中

这个对象用于处理用户提交的请求，springmvc中是使用方法来处理的。方法是自定义的， 可以有多种返回值， 多种参数，方法名称自定义



`@RequestMapping`注解是请求映射，是将一个请求路径和一个方法绑定在一起，这个方法就处理这个请求，比如`some.html`路径

参数

- value，数组，表示请求的uri地址的（some.do），value的值必须是唯一的， 不能重复。 在使用时，推荐地址以“/”，比如`value={"/some.do","/index.html"}`，用户访问这个资源，就会执行这个方法
- 位置，在方法上（常用），在类上

`使用RequestMapping修饰的方法叫做处理器方法或者控制器方法`

使用@RequestMapping修饰的方法可以处理请求的，类似Servlet中的doGet, doPost

- 返回值：ModelAndView 表示本次请求的处理结果

    - Model: 数据，请求处理完成后，要显示给用户的数据

    - View: 视图， 比如jsp等等。

`doSome()`方法就相当于servlet中的`doGet(),doPost`，只是这个方法，值接受`@RequestMapping(value = "/some.do")`value中的请求



```java
ModelAndView mv = new ModelAndView();
mv.addObject("name","chuchen");
mv.addObject("age",21);

mv.setViewName("/show.jsp");
return mv;
```

这个的作用就是将数据，封装在这个对象中，这个过程相当于`request.setAttribute()`

`mv.setViewName("/show.jsp");`就是指定我们返回的页面，相当于` request.getRequestDispather("").forward(...)`

`return mv`，当我们将数据放在这个`ModelAndView`对象中后，框架会在执行之后，去执行setAttribute(),forward(...)等代码，这个过程不需要我们做





## 组件扫描器

因为我们是使用注解，所以我们必须在springmvc配置文件中，声明组件扫描器

```xml
<context:component-scan base-package="vin.cco.web" />
```

这个扫描的是包名，不是类名

如果这里没有的话，那么请求时不会被绑定方法得到的

```java
org.springframework.web.servlet.DispatcherServlet.noHandlerFound No mapping for GET /some.do
```



![image-20210523184545674](http://ooszy.cco.vin/img/blog-note/image-20210523184545674.png?x-oss-process=style/pictureProcess1)

这里注意一下，需要加上`/`

如果不加，启动直接报错

![image-20210523185052886](http://ooszy.cco.vin/img/blog-note/image-20210523185052886.png?x-oss-process=style/pictureProcess1)

测试发现`/*.do`也是启动报错

`<url-pattern>*.do</url-pattern>`这样可以，也就是将所有以`.do`结尾的资源，都映射到这个对象上，但是不能这样`/*.do`，否则就会出现启动报错请求



# 处理过程



## 容器创建对象过程

tomcat启动的之后，会执行web.xml文件中的代码，轮到`<load-on-startup>1</load-on-startup>`执行的时候，就会创建`DispatcherServlet`对象，因为这个对象`HttpServletBean`有init方法，会执行这个方法

![image-20210524222651082](http://ooszy.cco.vin/img/blog-note/image-20210524222651082.png?x-oss-process=style/pictureProcess1)

在这个`HttpServletBean`对象的init方法中，会执行，这个过程就是创建配置文件中的对象

```java
WebApplicationContext wac = this.webApplicationContext;
String attrName = this.getServletContextAttributeName();
this.getServletContext().setAttribute(attrName, wac);
```

也就是创建配置文件中的对象，当这些对象创建成功之后，就会将容器中的对象，存放至`ServletContext`中，这个容器可以理解是一个map集合，通过调用`map.put()`方法将这些对象存入进去

对于创建`Controller`对象，也是创建成功之后，调用`put()`方法存在容器中



## 请求方法

![image-20210524223818271](http://ooszy.cco.vin/img/blog-note/image-20210524223818271.png?x-oss-process=style/pictureProcess1)





# 注意

我们可以在一个方法体中，设置多个路径

```java
mv.setViewName("/WEB-INF/view/other.jsp");
mv.setViewName("/WEB-INF/view/show.jsp");
```

但是这样的话，那么最终用户看到的数据，是最后一个`mv.setViewName`的页面



- 如果想要多个请求路径，对应这个方法，那么就使用数组的形式

    ```java
    @RequestMapping(value = {"/some.do","/first.do","cc.html"})
    ```

    但是并不意味着，这样写了之后，我们在浏览器中输入，`localhost/first.do`就能请问到这个资源，除了设置这个`@RequestMapping(value = {"/some.do","/first.do","cc.html"})`之外，还需要在`web.xml`文件中，添加映射，将`/some.do,/first.do,/cc.html`的映射到`DispatcherServlet`这个对象中，如果这一步不做的话，那么就会出现404

```
web.xml文件配置
<servlet>
    <servlet-name>myWeb</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>

    <init-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:springmvc.xml</param-value>
    </init-param>

    <load-on-startup>1</load-on-startup>
</servlet>

<servlet-mapping>
    <servlet-name>myWeb</servlet-name>
    <url-pattern>/some.do</url-pattern>
</servlet-mapping>

<servlet-mapping>
    <servlet-name>myWeb</servlet-name>
    <url-pattern>/first.do</url-pattern>
</servlet-mapping>

<servlet-mapping>
    <servlet-name>myWeb</servlet-name>
    <url-pattern>/cc.html</url-pattern>
</servlet-mapping>
```



以为有很多的页面，如果我们直接将这些页面放在`webapps`目录下，那么无论是谁，都可以访问，这个是不太允许的，解决这个的办法就是，将这些页面放在`webapps/WEB-INF/`目录下，在`WEB-IN`目录下的资源，直接在浏览器是不可以被请求到的，但是在代码中，请求转发可以请求到

![image-20210525111524630](http://ooszy.cco.vin/img/blog-note/image-20210525111524630.png?x-oss-process=style/pictureProcess1)

`mv.setViewName("/WEB-INF/view/show.jsp");`这样设置之后，就可以保证，这些资源只能通过某一个能请求到的资源进行访问



## 视图解析器

可能有很多的页面，都在`/WEB-INF/view/`这个路径下，但是每次这样写的时候，都会浪费时间，所以springmvc就解决了这个问题

在springmvc配置文件中，进行设置

```xml
<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" >
    <property name="prefix" value="/WEB-INF/view/" />
    <property name="suffix" value=".jsp" />
</bean>
```

- `<property name="prefix" value="/WEB-INF/view/" />`设置减省的路径名，一定要在前面和后面加上`/`
- `<property name="suffix" value=".jsp" />`设置后缀名

这样设置之后，我们就可以直接`mv.setViewName("other")`，其请求的路径为`/WEB-INF/view/other.jsp`



> `如果这样设置之后，并不是只有扩展名为jsp的路径，才会在返回的路径上，加上/WEB-INF/view/，而是所有的资源，就比如，设置的前提下，返回路径为index.html，那么最终的路径为/WEB-INF/view/index.html.jsp`





# `@RequestMapping`注解放在类上

这个`@RequestMapping`放在类上，是代表这个模块

```java
@RequestMapping("/user")
@Controller
public class MyController2 {
    @RequestMapping(value = {"/s.do"})
    public ModelAndView doSome() {
        System.out.println("请求了");
        ModelAndView mv = new ModelAndView();
        mv.addObject("name","chuchen");
        mv.addObject("age",21);
        mv.setViewName("other");
        //mv.setViewName("/WEB-INF/view/show.jsp");
        return mv;
    }
}
```

在项目中，比如user，login等模块，都是分割开的，请求路径也就是`/user/...`,`/login/...`的形式，这样的好处就是容易看

在类上面加上`@RequestMapping("/user")`的作用就是这样，也就是这里的模块的路径，如果将这个注解`@RequestMapping("/user")`放在类上，那么这个类中的所有方法的访问路径都必须加上`/user`才能访问到资源

如果这样设置，会怎么样？

`@RequestMapping("/user/d.do")`，那么是不是`localhost/user/d.do`也能访问到资源，并不是这样，如果这样设置，那么路径就必须加上`/user/d.do`才可以访问到



# 指明请求的方式

可以在`@RequestMapping(value = {"/get.do"},method = RequestMethod.GET)`注解中，执行此资源的请求方式

```java
//get请求
@RequestMapping(value = {"/get.do"},method = RequestMethod.GET)
//post请求
@RequestMapping(value = {"/post.do"},method = RequestMethod.POST)
```

如果不指定，那么无论get请求，还是post请求，都可以访问到资源

```java
@RequestMapping(value = {"/none.do"})//不指定请求方式，都可以请求到
```

但是如果设置的是一个post请求，或者是一个get请求，却使用一个相反的请求方式去请求，那么会报`405`

![image-20210525120623670](http://ooszy.cco.vin/img/blog-note/image-20210525120623670.png?x-oss-process=style/pictureProcess1)

# 获取请求参数信息

获取请求参数信息，有两个方法可以获取到

- 使用`HttpServletRequest request`对象
- 逐个赋值的方法



## HttpServletRequest request获取参数

```java
@RequestMapping(value = {"/c.c"})
public ModelAndView doSome(
    HttpServletRequest request,
    HttpServletResponse response,
    HttpSession session
) {
    ModelAndView mv = new ModelAndView();

    try {
        request.setCharacterEncoding("utf-8");
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    }
    String name = request.getParameter("name");
    String age = request.getParameter("age");
    mv.addObject("name",name);
    mv.addObject("age",age);
    mv.setViewName("parameter");

    return mv;
}
```

使用这种方法，获取参数和servlet中的一样，只需要在方法参数中，加上

```java
HttpServletRequest request,
HttpServletResponse response,
HttpSession session
```

在请求的时候，springmvc容器会自动对其进行赋值操作，只需要使用就可以



## 逐个赋值方式

逐个赋值方式需要在方法参数中，写上参数名，容器会自动获取方法参数对应的值，并将这个参数的值，赋值给方法形参值

参数为`name,age`

```java
@RequestMapping("/v.c")
public ModelAndView doAuto(String name,Integer age) {}
```

其底层操作为

```java
String strName = request.getParameter("name");
String strAge = request.getParameter("age");
```

 springmvc框架通过 DispatcherServlet 调用 MyController的doAuto()方法

调用方法时，按名称对应，把接收的参数赋值给形参doAuto(String name,Integer age)，框架会提供类型转换的功能，能把String转为 int ，long ， float， double等类型。



如果方法参数为`doAuto(String name,int age)`，在传递参数的时候，age参数为字符串，那么就会发生`400`错误，这个错误是spring中最常见的错误，客户端错误，比如这里的，不能将字符串`21sss`转换成int，就会发生错误

如果想要接收int类型的参数，那么最好使用`Integer`作为参数类型，这样转换失败的时候，这个参数值为null，在后期也比较好判断



##中文乱码问题

如果是get请求，那么参数如果是中文的话，不会出现乱码，如果是post请求，并且参数是中文的话，那么就会出现乱码请求，解决乱码，在servlet是在每一个方法中，都设置一下，但是在springmvc中，可以通过过滤器的方法进行设置，比较推荐这种方法

这个过滤器是框架就已经写好了的，我们直接使用就可以，但是需要在`web.xml`文件中，进行配置，并且需要这各过滤器添加一个映射

```
<filter>
    <filter-name>filterEncoding</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
        <param-name>encoding</param-name>
        <param-value>utf-8</param-value>
    </init-param>

    <init-param>
        <param-name>forceRequestEncoding</param-name>
        <param-value>true</param-value>
    </init-param>

    <init-param>
        <param-name>forceResponseEncoding</param-name>
        <param-value>true</param-value>
    </init-param>
</filter>

<filter-mapping>
    <filter-name>filterEncoding</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```



`forceRequestEncoding`是为请求设置编码方式，如果为true的话，那么映射所对应的所有请求的编码方式都会使用这个

```xml
<init-param>
    <param-name>forceRequestEncoding</param-name>
    <param-value>true</param-value>
</init-param>
```



`forceResponseEncoding`这个是设置响应的编码方式

```xml
<init-param>
    <param-name>forceResponseEncoding</param-name>
    <param-value>true</param-value>
</init-param>
```



为这个过滤器添加一个映射，`<url-pattern>/*</url-pattern>`所有的请求都会经过这个过滤器

```xml
<filter-mapping>
    <filter-name>filterEncoding</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```





# 获取参数方式

获取参数一共有三个方法

1. 使用`HttpServletRequest`
2. 通过处理器(方法)参数逐一匹配
3. 通过对象进行获取



推荐通过对象的方式进行获取



## 逐一匹配

```java
@RequestMapping(value = {"/ccc.c"})
public ModelAndView difPara1(String name, String age) {}
```

如果使用这种方式的话，就必须保证参数的字段于处理器中的形参名对应，否则的话，访问这个路径，就会出现null的情况



可以使用`@RequestParam()`注解解决，参数名和处理器形参名不一致的情况

请求地址为`/dif.c?sname=chuchen&sage=21`

```java
@RequestMapping(value = {"/dif.c"})
public ModelAndView difPara(
    @RequestParam(value = "sname",required = true) String name,
    @RequestParam(value = "sage") String age) {}
```

`@RequestParam(value = "sname",required = true) String name`请求地址中的`sname`参数的值会被赋值给`name`，使用这个注解，可以解决参数与处理器形参不一致的情况

这个注解中，还有其他的值，`boolean required() default true`的`required`是一个Boolean值，如果为true，表示，处理器形参名，必须在请求的时候，有值，否则的话，就会报错

```java
@RequestParam(value = "sname",required = true) String name,
```

如果请求的地址为`/dif.c`，那么就会报错

报错信息

```java
org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver.logException Resolved [org.springframework.web.bind.MissingServletRequestParameterException: Required request parameter 'sname' for method parameter type String is not present]
```



将这个`required = false`就可以解决这个问题

## 对象取值

还可以通过对象取值的方式获取参数信息，声明一个对象，`DispatcherServlet`对象在接收到用户请求的时候，会自动创建处理器形参对象(形参对象，可以有很多个)，会自动通过调用`set参数名()`进行赋值，如果这个参数在对象中没有`set参数名()`方法时，不会进行赋值操作，`必须要保证对象中存在set方法，并且调用的是，无参构造`

```java
public class Student {
    private String name;
    private int age;

    public Student () {
        System.out.println("执行无参构造");
    }

    public Student (String name, int age) {
        this.name = name;
        this.age = age;
        System.out.println("执行有参构造");
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        System.out.println("------------执行setName----------");
        this.name = name;
    }

    public int getAge () {
        return age;
    }

    public void setAge (int age) {
        System.out.println("-----------执行setAge--------------");
        this.age = age;
    }

    @Override
    public String toString () {
        return "Student{" + "name='" + name + '\'' + ", age=" + age + '}';
    }
}
```

```java
@RequestMapping(value = {"/stu.c"})
public ModelAndView stu(Student student) {
    System.out.println("name --->"+student.getName());
    System.out.println("age --->"+student.getAge());
    ModelAndView mv = new ModelAndView();
    mv.addObject("name",student.getName());
    mv.addObject("age",student.getAge());
    mv.addObject("student",student);
    mv.setViewName("parameter");

    System.out.println("------------执行结束-----------");

    return mv;
}
```

推荐使用这种方式进行参数的获取，在形参中，可以使用多个对象`public ModelAndView stu(Student student,School school,...) {}`，如果这里有多个对象的话，那么就会将参数中，对应的值，调用这些对象中，对应的`set`方法进行赋值



# 返回值类型

使用@Controller 注解的处理器的处理器方法，其返回值常用的有四种类型：

➢ 第一种：ModelAndView

➢ 第二种：String

➢ 第三种：无返回值 void

➢ 第四种：返回自定义类型对象



## **返回** **ModelAndView**

若处理器方法处理完后，需要跳转到其它资源，且又要在跳转的资源间传递数据，此时处理器方法返回 ModelAndView 比较好。当然，若要返回 ModelAndView，则处理器方法中需要定义 ModelAndView 对象。

在使用时，若该处理器方法只是进行跳转而不传递数据，或只是传递数据而并不向任何资源跳转（如对页面的 Ajax 异步响应），此时若返回 ModelAndView，则将总是有一部分多余：要么 Model 多余，要么 View 多余。即此时返回 ModelAndView 将不合适。

但是感觉这种方式用到的情况有点少，现在很多都使用前后端分离的方式，并不是通过jsp进行获取值，这种方式是通过请求转发实现的，感觉目前使用到的情况有点少



## **返回** **String**

处理器方法返回的字符串可以指定逻辑视图名，通过视图解析器解析可以将其转换为物理视图地址

- **返回内部资源逻辑视图名**

若要跳转的资源为内部资源，则视图解析器可以使用 InternalResourceViewResolver 内部

资源视图解析器。此时处理器方法返回的字符串就是要跳转页面的文件名去掉文件扩展名后

的部分。这个字符串与视图解析器中的 prefix、suffix 相结合，即可形成要访问的 URI。

`直接返回字符串，不加上扩展名，一定需要在springmvc配置文件中，添加视图解析器，否则不能使用`，并且能取出后缀名，也必须在此处定义`<property name="suffix" value=".jsp" />`

```java
<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" >
    <property name="prefix" value="/WEB-INF/view/" />
    <property name="suffix" value=".jsp" />
</bean>
```



```java
@RequestMapping("returnString.c")
public String returnString() {
    System.out.println("执行了");
    return "show";//加上视图解析器的前提下
}
```



同样的，也可以在这里写上物理路径，也就是`/WEB-INF/view/show.jsp`，但是如果使用这种情况，就不能加上视图解析器，否则的话，最终转发的路径为`/WEB-INF/view/show.jsp//WEB-INF/view/show.jsp`

返回String，适用于不返回数据，返回视图，但是在这个代码中，感觉也是不太常用，返回路径，以为是请求转发



## 返回**void**

对于处理器方法返回 void 的应用场景，AJAX 响应.

若处理器对请求处理后，无需跳转到其它任何资源，此时可以让处理器方法返回 void。

这种方式就是servlet的方式，将需要返回的数据，转换成json，或者就是原样，然后调用`response.getWrite().write()`方法



## **返回对象** **Object**

处理器方法也可以返回 Object 对象。这个 Object 可以是 Integer，String，自定义对象，Map，List 等。`但返回的对象不是作为逻辑视图出现的，而是作为直接在页面显示的数据出现的`

返回对象，需要使用@ResponseBody 注解，将转换后的 JSON 数据放入到响应体中。



### 依赖

由于返回 Object 数据，一般都是将数据转化为了 JSON 对象后传递给浏览器页面的。而这个由 Object 转换为 JSON，是由 Jackson 工具完成的。所以需要导入 Jackson 的相关 Jar 包。

```
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-core</artifactId>
    <version>2.12.1</version>
</dependency>

<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.12.1</version>
</dependency>
```





### **声明注解驱动**

将 Object 数据转化为 JSON 数据，需要由消息转换器 HttpMessageConverter 完成。而转换器的开启，需要由`<mvc:annotation-driven/>`来完成。

SpringMVC 使用消息转换器实现请求数据和对象，处理器方法返回对象和响应输出之间的自动转换当 Spring 容器进行初始化过程中，在`<mvc:annotation-driven/>`处创建注解驱动时，默认创建了七个 HttpMessageConverter 对象。也就是说，我们注册`<mvc:annotation-driven/>`，就是为了让容器为我们创建 HttpMessageConverter 对象，这个需要在springmvc配置文件中进行添加，但是只需要写出这个就可以，必须要选择这个约束文件`http://www.springframework.org/schema/mvc"`

```xml
<mvc:annotation-driven />
```

`HttpMessageConverter` 接口 : **`HttpMessageConverter<T>`**是 Spring3.0 新添加的一个接口，

**负责将请求信息转换为一个对象（类型为** **T****），将对象（类型为** **T****）输出为响应信息**

`HttpMessageConverter<T>`接口定义的方法：

`boolean canRead(Class<?> clazz,MediaType mediaType)`: 指定转换器可以读取的对象类型，即

转 换 器 是 否 可 将 请 求 信 息 转 换 为 clazz 类 型 的 对 象 ， 同 时 指 定 支 持 MIME 类 型

(text/html,applaiction/json 等) ，如果能够转换，那么就返回true，否则反之

`boolean canWrite(Class<?> clazz,MediaType mediaType)`:指定转换器是否可将 clazz 类型的对

象写到响应流中，响应流支持的媒体类型在 MediaType 中定义。

`LIst<MediaType> getSupportMediaTypes()`：该转换器支持的媒体类型。

`T read(Class<? extends T> clazz,HttpInputMessage inputMessage)`：将请求信息流转换为 T 类型的对象。

`void write(T t,MediaType contnetType,HttpOutputMessgae outputMessage)`:将 T 类型的对象写

到响应流中，同时指定相应的媒体类型为 contentType

加入注解驱动`<mvc:annotation-driven/>`后适配器类的 messageConverters 属性值

这个接口的实现类有

![image-20210525215929728](http://ooszy.cco.vin/img/blog-note/image-20210525215929728.png?x-oss-process=style/pictureProcess1)

这个的实现类，有很多，都是为了处理很多的mine类型的数据

支持的类型，可以在`MediaType`类中，进行查看



加入注解驱动之后，会创建7个对象，其下为

![image-20210525220335665](http://ooszy.cco.vin/img/blog-note/image-20210525220335665.png?x-oss-process=style/pictureProcess1)

![image-20210525220356513](http://ooszy.cco.vin/img/blog-note/image-20210525220356513.png?x-oss-process=style/pictureProcess1)





### 实现步骤

1. 加入依赖

2. 添加注册驱动`<mvc:annotation-driven>`，这一步，会使用这个jsonson工具类库，将需要转化为json的对象，将其转换为json字符串，添加注册驱动，相当于这个步骤

    ```java
    ObjectMapper om  = new ObjectMapper();
    String json = om.writeValueAsString(student);
    ```

3. 在处理器方法的上面加入@ResponseBody注解，这一步，相当于这个过程，只是这个过程是框架帮我们做了

    ```java
    response.setContentType("application/json;charset=utf-8");
    PrintWriter pw  = response.getWriter();
    pw.println(json);
    ```

    

- 如果不加上注册驱动`<mvc:annotation-driven>`，那么当我们处理请求，在`DispatcherServlet`对象的`doDispatch()`方法上，打断点，在这里进行打断点，就可以看到` MessageCoverters`对象创建了多少个对象

    ![image-20210525223433790](http://ooszy.cco.vin/img/blog-note/image-20210525223433790.png?x-oss-process=style/pictureProcess1)

    ![image-20210525223649934](http://ooszy.cco.vin/img/blog-note/image-20210525223649934.png?x-oss-process=style/pictureProcess1)

    下面这个，是添加上注册驱动之后，这个接口创建的对象，`MappingJackson2HttpMessageConverter`对象就是用来处理json对象的，如果没有添加驱动的话，那么这里的对象就只有4个

```
org.springframework.http.converter.ByteArrayHttpMessageConverter@1b0b99e6, org.springframework.http.converter.StringHttpMessageConverter@59549a0d, org.springframework.http.converter.ResourceHttpMessageConverter@32f9eb68, org.springframework.http.converter.ResourceRegionHttpMessageConverter@3087a311, org.springframework.http.converter.xml.SourceHttpMessageConverter@7784d84e, org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter@a7da041, org.springframework.http.converter.json.MappingJackson2HttpMessageConverter@77a2a8cd
```



### 测试

```java
@RequestMapping("ajax.c")
@ResponseBody
public Student returnAjax(String name,Integer age) {
    System.out.println("执行了");
    Student student = new Student(name,age);
    return student;
}
```

这个代码，返回的是一个json格式的数据

```
{"name":"chuchen","age":21}
```

不需要额外的操作，设置请求头类型，编码格式，不同手动将这个对象转换成json，再将其输出，这个过程，框架都已经帮我们做了

这个是框架自己设置的

![image-20210525224857528](http://ooszy.cco.vin/img/blog-note/image-20210525224857528.png?x-oss-process=style/pictureProcess1)



#### 执行过程

对`Student student = new Student(name,age)`，创建这个对象，并且对这个对象进行赋值操作，在返回数据的期间，会创建`messageConverters`接口的7个实现类，这个接口信息如下

```java
public interface HttpMessageConverter<T> {
    boolean canRead(Class<?> var1, @Nullable MediaType var2);

    boolean canWrite(Class<?> var1, @Nullable MediaType var2);

    List<MediaType> getSupportedMediaTypes();

    default List<MediaType> getSupportedMediaTypes(Class<?> clazz) {
        return !this.canRead(clazz, (MediaType)null) && !this.canWrite(clazz, (MediaType)null) ? Collections.emptyList() : this.getSupportedMediaTypes();
    }

    T read(Class<? extends T> var1, HttpInputMessage var2) throws IOException, HttpMessageNotReadableException;

    void write(T var1, @Nullable MediaType var2, HttpOutputMessage var3) throws IOException, HttpMessageNotWritableException;
}
```

他们的实现类，都继承了这些方法，这个时候，7个实现类，就会先后调用`boolean canRead(Class<?> var1, @Nullable MediaType var2)`方法，看是否，能够将这个对象转换成`var2`的类型，如果返回true，那么就会调用` void write(T var1, @Nullable MediaType var2, HttpOutputMessage var3)`方法，将这个对象转换成`var2`类型的数据格式

![image-20210525225926874](http://ooszy.cco.vin/img/blog-note/image-20210525225926874.png?x-oss-process=style/pictureProcess1)

之后，就轮到`@ResponseBody`注解的作用，将这个数据，写入到浏览器上

对象转换成json格式，对应的对象是`MappingJackson2HttpMessageConverter`







## 返回数组List类型

```java
@RequestMapping("/returnList.c")
@ResponseBody
public List<Student> returnList() {
    List<Student> list = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
        Student student = new Student("name"+i,20+i);
        list.add(student);
    }
    return list;
}
```

执行结果

```json
[
    {
        "name": "name0",
        "age": 20
    },
    {
        "name": "name1",
        "age": 21
    },
    {
        "name": "name2",
        "age": 22
    },
    {
        "name": "name3",
        "age": 23
    },
    {
        "name": "name4",
        "age": 24
    }
]
```

执行过程也是和上面一样，只是将List对象转换为json数组使用的对象是`ByteArrayHttpMessageConverter`



## 返回String数据，不是视图

同样也可以返回String，但是默认返回的是一个视图，也可以返回数据，但是这个需要进行设置，设置之后，返回的就是数据，并不是视图模式

如果String返回值想要返回的仅仅是数据的话，那么就只需要在处理器方法上使用注解`@ResponseBody`，因为这个注解的作用就是 相当于`response.getWrite().write()`方法，将数据写在浏览器上面，那么现在String返回的就不是视图，而是数据

```java
@RequestMapping("returnStringDate.c")
@ResponseBody
public String returnStringDate() {
    return "hello this is 初尘!";
}
```


### 问题

中文乱码问题，如果不设置请求头的话，那么就会出现中文乱码的问题

浏览器中的请求头信息，可以看到，编码并不是utf-8，所以中文就会出现乱码的问题

```
Content-Type: text/html;charset=ISO-8859-1
```

这样设置就可以解决问题

```java
@RequestMapping(value = "returnStringDate.c",produces = "text/text;charset=utf-8")
```

`produces = "text/text;charset=utf-8"`可以设置请求有的信息，指定请求头的信息，就可以解决问题

