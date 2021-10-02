---
categories: [servlet,服务器,web服务器]
---

# 嵌入式servlet服务器

## 原理描述

官方描述

Under the hood, Spring Boot uses a different type of `ApplicationContext` for embedded servlet container support. The `ServletWebServerApplicationContext` is a special type of `WebApplicationContext` that bootstraps itself by searching for a single `ServletWebServerFactory` bean. Usually a `TomcatServletWebServerFactory`, `JettyServletWebServerFactory`, or `UndertowServletWebServerFactory` has been auto-configured.



1. spring boot启动，发现是一个web项目（因为导入web的start），导入tomcat

2. servlet的web服务器ServletWebServerFactoryAutoConfiguration在启动的时候，会导入ServletWebServerFactoryConfiguration，此ServletWebServerFactoryConfiguration中，有多个bean，都是不同服务器的WebServerFactory

```java
@Bean
TomcatServletWebServerFactory tomcatServletWebServerFactory(){}

@Bean
JettyServletWebServerFactory JettyServletWebServerFactory()

@Bean
UndertowServletWebServerFactory undertowServletWebServerFactory()
```

3. ServletWebServerApplicationContext启动的时候，会寻找**ServletWebServerFactory**

    在ServletWebServerFactoryConfiguration中内置了三个ServletWebServerFactory，`TomcatServletWebServerFactory`, `JettyServletWebServerFactory`, or `UndertowServletWebServerFactory`

4. 因为springboot在启动的时候，会自动导入xxAutoConfiguration中的配置，导入ServletWebServerFactoryAutoConfiguration中的配置，还会导入ServletWebServerFactoryConfiguration配置，ServletWebServerFactoryConfiguration会动态的判断导入的是哪个WebServletFactory，因为导入的是web-start，所以默认是tomcat--->TomcatServletWebServerFactory

5. TomcatServletWebServerFactory会创建tomcat服务器，并会启动

    ServletWebServerApplicationContext容器在启动的时候，会有一个

    ```java
    public final void refresh() throws BeansException, IllegalStateException {
        try {
            super.refresh();
        }
        catch (RuntimeException ex) {
            ////////////////////重要
            WebServer webServer = this.webServer;
            if (webServer != null) {
                webServer.stop();
            }
            throw ex;
        }
    }
    
    //如果发生异常的话，就会停止，否则会createWebServer();
    @Override
    protected void onRefresh() {
        super.onRefresh();
        try {
            createWebServer();
        }
        catch (Throwable ex) {
            throw new ApplicationContextException("Unable to start web server", ex);
        }
    }
    ```

    下面方法是寻找webServletFactory，在一个应用中，只能有一个web服务器

    ```java
    protected ServletWebServerFactory getWebServerFactory() {
        // Use bean names so that we don't consider the hierarchy
        String[] beanNames = getBeanFactory().getBeanNamesForType(ServletWebServerFactory.class);
        if (beanNames.length == 0) {
            throw new ApplicationContextException("Unable to start ServletWebServerApplicationContext due to missing "
                                                  + "ServletWebServerFactory bean.");
        }
        if (beanNames.length > 1) {
            throw new ApplicationContextException("Unable to start ServletWebServerApplicationContext due to multiple "
                                                  + "ServletWebServerFactory beans : " + StringUtils.arrayToCommaDelimitedString(beanNames));
        }
        return getBeanFactory().getBean(beanNames[0], ServletWebServerFactory.class);
    }
    ```

    在WebServer中，可以看到webServer的实现，对于tomcat

    ```java
    public WebServer getWebServer(ServletContextInitializer... initializers) {
        if (this.disableMBeanRegistry) {
            Registry.disableRegistry();
        }
        Tomcat tomcat = new Tomcat();
        ............
        for (Connector additionalConnector : this.additionalTomcatConnectors) {
            tomcat.getService().addConnector(additionalConnector);
        }
        prepareContext(tomcat.getHost(), initializers);
        return getTomcatWebServer(tomcat);
    }
    ```

    在这里直接new了一个tomcat，Tomcat tomcat = new Tomcat()，此方法调用之后，会返回一个TomcatWebServer，在这个类中，存在一个start()方法，这个就是启动tomcat

    



## 选择servlet服务器

因为默认使用的是tomcat服务器，但是我们可以选择其他的

![image-20210707220632323](http://ooszy.cco.vin/img/blog-note/image-20210707220632323.png?x-oss-process=style/pictureProcess1)

因为默认的是tomcat，我们如果想要使用其他的服务器的话，就需要将tomcat进行移除，因为只能有一个服务器



```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </exclusion>
    </exclusions>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-undertow</artifactId>
</dependency>
```





## 定制化servlet容器

官方文档https://docs.spring.io/spring-boot/docs/current/reference/html/features.html##features.developing-web-applications.embedded-container.customizing

- 实现 ` WebServerFactoryCu**stomizer<ConfigurableServletWebServerFactory> `

- 把配置文件的值和`**ServletWebServerFactory 进行绑定**`

- 修改配置文件 **server.xxx**
- 直接自定义 **ConfigurableServletWebServerFactory** 

**xxxxx****Customizer****：定制化器，可以改变xxxx的默认规则**

```java
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomizationBean implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    @Override
    public void customize(ConfigurableServletWebServerFactory server) {
        server.setPort(9000);
    }
}
```



