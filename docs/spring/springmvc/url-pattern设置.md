在没有特殊要求的情况下，SpringMVC 的中央调度器 DispatcherServlet 的`<url-pattern/>`

常使用后辍匹配方式，如写为*.do 或者 *.action, *.mvc 等。也就是通过映射进行资源的访问



`web.xml`文件中的这个映射配置，可以写成`/`

```xml
<servlet-mapping>
    <servlet-name>myWeb</servlet-name>
    <url-pattern>/</url-pattern>
</servlet-mapping>
```

但是如果这样设置之后，那么所有的资源，包括静态资源(js，HTML，css)等，都将使用`DispatcherServlet`，但是这个也会有一个问题，因为`DispatcherServlet`对象对于静态资源是处理不了的，所以访问这些静态资源的时候，就会出现404，但是对于不是静态资源的访问（some.do等），可以访问

> 当我们没有进行设置的时候，访问静态资源确实可以访问，这是因为对于静态资源，是交给tomcat进行处理的，所以就会访问到



# tomcat的web.xml

在tomcat的`conf/web.xml`文件中，有下面这个配置

```
<servlet>
    <servlet-name>default</servlet-name>
    <servlet-class>org.apache.catalina.servlets.DefaultServlet</servlet-class>
    <init-param>
        <param-name>debug</param-name>
        <param-value>0</param-value>
    </init-param>
    <init-param>
        <param-name>listings</param-name>
        <param-value>false</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
</servlet>

<servlet-mapping>
    <servlet-name>default</servlet-name>
    <url-pattern>/</url-pattern>
</servlet-mapping>
```

这里有一个默认的`DefaultServlet`对象，在tomcat启动的时候，对于静态资源的请求就是这个对象操作的

上面的注释

```
The default servlet for all web applications, that serves static  
resources.  It processes all requests that are not mapped to other
servlets with servlet mappings (defined either here or in your own
web.xml file).  This servlet supports the following initialization
parameters (default values are in square brackets):               
```

这个默认的servlet是应用于所有的静态资源，它接受除所有没有添加映射的请求，也就是说，我们在`webapps`中，没有添加映射的请求，都将使用默认的tomcat的servlet

在`webapps/web.xml`文件中，有下面这个配置

```xml
<servlet-mapping>
    <servlet-name>myWeb</servlet-name>
    <url-pattern>*.do</url-pattern>
</servlet-mapping>
```

那么所有的`*.do`的请求不会使用默认的servlet，将使用`<servlet-name></servlet-name>`对应的servlet


# 修改配置文件

在`webapps/web.xml`文件中，为

```xml
<servlet>
    <servlet-name>myWeb</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>

    <init-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:springmvc.xml</param-value>
    </init-param>

    <load-on-startup>1</load-on-startup>
</servlet>
```

添加一个映射`<servlet-mapping>`，name值为`/`

```xml
<servlet-mapping>
    <servlet-name>myWeb</servlet-name>
    <url-pattern>/</url-pattern>
</servlet-mapping>
```



那么现在启动程序，就可以访问，包括jsp文件，但是这个就会出现一个问题，因为`org.springframework.web.servlet.DispatcherServlet`对象是不具有处理静态资源文件的功能，所以如果访问静态资源（HTML，css，js，img）等等就会出现404的情况，但是对于其他的动态资源，可以访问。

将`<url-pattern>/</url-pattern>`设置为`/`，我们就可以不用对`requestMapping()`中的扩展名有什么限制，也不用添加一个资源路劲之后，就需要在`web.xml`文件中，新添加一个地址映射`<servlet-mapping>`



## 解决静态资源问题

解决静态资源访问不到，有两种方式

### 使用`<mvc:default-servlet-handler/>`

但是这个问题，是可以解决的，我们只需要对发起请求的资源做一个筛选，将是静态资源的文件，交给tomcat进行处理，就可以了



步骤

> 1. 在springmvc配置文件中，添加标签，没有注解驱动`<mvc:annotation-driven />`前提下
>
>     ```xml
>     <mvc:default-servlet-handler />
>     ```
>
>     加入这个标签后，框架会创健控制器对象DefaultServletHttpRequestHandler（类似我们自己创建的MyController，DefaultServletHttpRequestHandler这个对象可以把接收的请求转发给 tomcat的default这个servlet。
>
>     但是也会遇到一个新的问题，因为其是将所有的请求都交给默认的servlet进行处理，那么对于动态的资源，就会出现404
>
>     解决这个动态资源404，就需要在springmvc配置文件中，加入注解驱动`<mvc:annotation-driven />`，就可以解决这个问题


### 第二种方式 使用`<mvc:resources/>`

在 Spring3.0 版本后，Spring 定义了专门用于处理静态资源访问请求的处理器ResourceHttpRequestHandler。并且添加了`<mvc:resources/>`标签，专门用于解决静态资源无法访问问题。需要在 springmvc 配置文件中添加如下形式的配置



在springmvc配置文件中，添加标签`<mvc:resources mapping="img/**" location="./img/" />`

> `location` 表示静态资源所在目录。当然，目录不要使用/WEB-INF/及其子目录。
>
> `mapping` 表 示 对 该 资 源 的 请 求 （ **以** **/img/** **开 始 的 请 求 ， 如** **/img/beauty.jpg ,** 
>
> **/img/car.png** **等**）。注意，后面是两个星号***\***。



原理

> mvc:resources 加入后框架会创建 ResourceHttpRequestHandler这个处理器对象。
>
> ​    让这个对象处理静态资源的访问，不依赖tomcat服务器。
>
> ​    mapping:访问静态资源的uri地址， 使用通配符 **
>
> ​    location：静态资源在你的项目中的目录位置。



- 优化，因为如果这样设置的话，那么我们在使用js等等的时候，都需要记性添加这个标签，这个就可以浪费时间，所以我们可以创建一个文件夹`static`，将所有的静态资源都放入到这个文件夹中，然后`<mvc:resources mapping="static/**" location="/static/" />`就可以了

`但是使用这个方法，还是需要加上注解驱动才可以访问动态资源`



# 绝对路径和相对路径的区别和影响

如果网址的根目录为`localhost/cco`，那么有一个a标签`<a href="img/a.jpg">`，那么当我们点击这个标签的时候，其访问路径变为`localhost/cco/img/a.jog`，资源存在

但是如果这个a标签的地址为`<a href="/img/a.jpg">`，那么点击此标签，其访问地址，最终会变为`localhost/img/a.jpg`，`/代表的是相对位置`，所以就会出现问题，解决这个问题的方式有两种，一种是使用jsp的el表达式，但是这个不推荐使用，还有一种方式就是在HTMl中，添加一个标签，`base`，base标签， 是html语言中的标签。 表示当前页面中访问地址的基地址。你的页面中所有 没有“/”开头的地址，都是以base标签中的地址为参考地址使用base中的地址 + user/some.do 组成访问地址