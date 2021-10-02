---

categories: [spring boot,spring,介绍,spring解析,spring分析]
---

# spring boot介绍

springboot已经集成了servlet，tomcat，还有spring，所以在使用springboot创建应用之后，我们不需要额外的为这个module设置tomcat服务器，更改端口号，也不需要像spring那样写很多的配置项



![image-20210702090435379](http://ooszy.cco.vin/img/blog-note/image-20210702090435379.png?x-oss-process=style/pictureProcess1)



## 使用

1. 添加maven依赖
2. 创建主程序
3. 编写Controller



maven依赖

除了添加springboot依赖外，我们还需要加入一个特殊的依赖，这个特殊的依赖，提供了特殊的启动类，是必须的

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.4.6</version>
</parent>
```

这个写在`<project>`标签中，就可以

还需要一个依赖，这个依赖中，就还有了很多需要使用的依赖，比如servlet，spring等等，不用我们再次添加spring等依赖，已经集成了，我们仅仅添加这个就可以

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```



最终`pom.xml`文件

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>vin.cco</groupId>
    <artifactId>001springboot</artifactId>
    <version>1.0-SNAPSHOT</version>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.4.6</version>
    </parent>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

    </dependencies>

</project>
```



编写主启动程序

```java
@SpringBootApplication
public class MainApplication {
    public static void main (String[] args) {
        SpringApplication.run(MainApplication.class,args);
    }
}
```

这个类就是启动spring应用的入口程序，也就是启动服务器的程序，需要加上`@SpringBootApplication`，注解，表明这是一个springboot的启动类程序

`SpringApplication.run(MainApplication.class,args)`调用此方法，启动程序

> 因为在启动的时候，此类会使用类加载器扫描所有包中的类，所以需要保证，此启动类文件，需要在所有包的上面，也就是目录
>
> ![image-20210608131048862](http://ooszy.cco.vin/img/blog-note/image-20210608131048862.png?x-oss-process=style/pictureProcess1)
>
> 否则的话，就会报错



Controller控制类

```java
@RestController
public class MyController {

    @RequestMapping("hello")
    public String doHello() {
        return "hello springboot";
    }
}
```

因为一般，我们返回值为String，都是写在浏览器上，并不是一个视图的名字，所以会直接在类上添加`@ResponseBody`注解，就表明，这个类中的所有方法，都是写在浏览器上

springboot又提供了一个注解`@RestController`，这个注解中，包括了`@Controller,@ResponseBody`注解，所以我们现在只需要写`@RestController`就可以了，不用再写两个



springboot的部署方法，可以打包成war的形式，然后发布，也可以打包成jar包形式，因为jar包中，就有了tomcat服务器类，还有servlet等等所需要的jar文件，可以直接通过cmd命令运行jar文件





# 依赖管理

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.4.6</version>
</parent>
```

这是springboot提供的一个依赖管理，只要我们的pom文件，继承这个父依赖，就可以使用这个pom文件中的特性，这些特性包括下面这些

比如默认规定了，编译项目使用的jdk版本和编码方式等

```xml
<properties>
    <java.version>1.8</java.version>
    <resource.delimiter>@</resource.delimiter>
    <maven.compiler.source>${java.version}</maven.compiler.source>
    <maven.compiler.target>${java.version}</maven.compiler.target>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
</properties>
```

在这个pom文件中，其还有一个父pom文件

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-dependencies</artifactId>
    <version>2.4.6</version>
</parent>
```

在这个`spring-boot-dependencies`依赖中，定义了很多需要使用到的依赖的版本号

![image-20210612194931430](http://ooszy.cco.vin/img/blog-note/image-20210612194931430.png?x-oss-process=style/pictureProcess1)

还有很多的依赖，都已经被引入进来了

![image-20210612195010965](http://ooszy.cco.vin/img/blog-note/image-20210612195010965.png?x-oss-process=style/pictureProcess1)

如果我们想要使用这些已经定义好的依赖的时候，就只需要在我们自己的pom文件中，直接使用就可以，版本号不用写，因为已经定义好了，但是可以使用我们自己的版本号

像这样就可以了，不用添加版本号

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
```



## 更改版本号

在当前项目中，定义一个属性，在这个标签中，进行版本号的修改，修改之后，maven就会使用就近原则，使用我们定义的版本号

```xml
<properties>
    <mysql.version>5.1.49</mysql.version>
</properties>
```

确保`<mysql.version>`标签和父依赖中，定义的一样

刷新pom文件，就已经被更改了

## 作用

> 1.引入父pom里面的依赖时无须指定版本；
> 2.java版本，项目编码格式，资源引用描述符已经设置好
> 3.插件管理
> a.封装了配置文件的过滤规则
> b.封装了打可执行jar、war的配置
> c.封装了插件的版本信息
> d.封装了日期格式
> e.引入了eclipse和IDEA相关依赖简化了配置，达到开箱即用等





# `Starters`场景启动器

什么是`Starters`？

Starters中集成了我们使用这个场景的很多的依赖，只要我们导入这个starters就可以直接使用这个场景进行开发

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

上面的这个就是开发web应用所需的场景依赖，在这个starters中，定义了很多需要的依赖

## 命名格式

这个starters我们可以自己定义，但是需要和官方的优点区别

- 官方命名格式

官方使用的命名格式为`spring-boot-starter-*`，其中的`*`表示所有的场景名，如`spring-boot-starter-web`用于web开发的场景，tomcat的场景`spring-boot-starter-tomcat`



- 自己的starters

`*-spring-boot-starter`，其中`*`表示我们的场景名



在spring中，定义了很多我们用于开发所需要的场景

![image-20210612202324133](http://ooszy.cco.vin/img/blog-note/image-20210612202324133.png?x-oss-process=style/pictureProcess1)

比如我们要开发一个aop，那么我们就使用提供的这个startters就可以了，这样不用再去导入那些繁琐的依赖



# 自动配置

- 自动配置tomcat

    这个tomcat已经被spring配置好了，因为我们在`spring-boot-starter-web`场景中，使用了tomcat场景`spring-boot-starter-tomcat`，所以就可以直接使用

    ![image-20210702090633221](http://ooszy.cco.vin/img/blog-note/image-20210702090633221.png?x-oss-process=style/pictureProcess1)

- 自动配好springmvc组件

    `spring-webmvc`，因为springboot也就是一个web应用，所以就需要使用springmvc，在场景中，已经引入，就不用我们自己再次引入

- 自动配置常见的设置

    因为在创建一个springweb项目的时候，我们需要在`web.xml`文件中，设置很多个东西，就比如引入`dispatcherServlet`，还有设置默认的字符集等等，但是在这里，这些都已经被设置好了，不用我们再次设置

    ```java
    ConfigurableApplicationContext run = SpringApplication.run(MainApplication.class, args);
    String[] names = run.getBeanDefinitionNames();
    for (String name : names) {
        System.out.println(name);
    }
    ```

    `SpringApplication.run(MainApplication.class, args)`返回一个ioc容器，我们可以通过返回对象，查看我们当前容器中，有哪些组件

    ![image-20210612204454794](http://ooszy.cco.vin/img/blog-note/image-20210612204454794.png?x-oss-process=style/pictureProcess1)

- 默认的包结构

    在创建springmvc项目中，我们会使用组件扫描器，扫描我们添加注解的类，从而能够使web资源被访问到，但是在这里，就已经设置了默认的包结构

    在主启动类所在的包中，及其主启动类所在包的所有子包中的类，都能被扫描到，这个就是默认的包结构

    如果我们将主启动类，移动到main包中，那么在web包中，定义一个Controller，那么访问的时候，就会出现404，因为这个web包，没有被扫描到

    ![image-20210612204802639](http://ooszy.cco.vin/img/blog-note/image-20210612204802639.png?x-oss-process=style/pictureProcess1)

    ![image-20210612205234615](http://ooszy.cco.vin/img/blog-note/image-20210612205234615.png?x-oss-process=style/pictureProcess1)

## 修改包结构
- 修改包结构

    在主启动类中的注解`@SpringBootApplication`里面有一个属性，可以添加需要扫描到的包，这样，如果添加的这个包，没有在默认包结构的范围内，那么也会被扫描到

    `@SpringBootApplication(scanBasePackages = "vin")`

    还可以通过注解`@ComponentScan`进行设置，但是会出现冲突的问题，所以使用注解，需要注意

`@SpringBootApplication`注解等于下面几个注解，所以可以使用下面三个注解，代替`@SpringBootApplication`注解，所以，如果存在`@SpringBootApplication`注解，又添加`@ComponentScan`时，就会出现问题

```java
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
```

还可以这样，更改默认的包结构

```java
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan("vin")
```


​    

- 各种配置都拥有默认值

    比如在配置文件中，存在一个，默认上传文件的最大长度，这个配置最终会映射到`MultipartAutoConfiguration`类中，所以，各种配置都拥有默认的值，这些最终都是映射到某个类中，可以在`application.properties`中，进行更改

- 按需加载所有配置项

    springboot启动时按需进行加载启动的，也就是，比如我们开发一个web应用，我们引入了web场景，那么就只会加载构建web应用所需要的依赖

    所有自动加载配置功能都是在`spring-boot-starter\<artifactId>spring-boot-autoconfigure</artifactId>`中，可以看到

    ![image-20210612211806255](http://ooszy.cco.vin/img/blog-note/image-20210612211806255.png?x-oss-process=style/pictureProcess1)

    在这给自动加载类中，我们可以看到所有常见的配置

    比如这个`aop`，需要引入`spring-boot-starter-aop`场景，其才会自动加载，否则不会

    因为没有引入aop场景，如果点进去，会出现爆红或者白色(`取决于你的idea主题样式`)

    ![image-20210612212059129](http://ooszy.cco.vin/img/blog-note/image-20210612212059129.png?x-oss-process=style/pictureProcess1)

    当引入`aop`场景时，会发现颜色会改变，因为这个aop引入之后，已经加载了

    ![image-20210612212251206](http://ooszy.cco.vin/img/blog-note/image-20210612212251206.png?x-oss-process=style/pictureProcess1)





# springboot注解

## 组件添加

在springmvc中，如果我们想要为某个类，注册对象的话，那么我们可以在`<bean>`标签中，进行对象的创建，但是在springboot中，可以使用注解进行对象的创建



使用步骤

1. 创建一个配置类`Config`
2. 在此类上，添加`@Configuration`注解，就表示这个类，是一个spring配置类，相当于spring的xml配置文件
3. 定义方法，方法上添加`@Bean`注解，方法名就是对象名，返回值类型，就是对象类型

```java
@Configuration
public class Config {

    @Bean
    public User user01() {
        return new User("chuchen",12);
    }

    @Bean
    public Pet pet01() {
        return new Pet("hachi");
    }
}
```

现在我们就创建了`User`和`Pet`两个对象

这两个对象在启动的时候，会被放入容器中，供我们使用，并且`Config`也是一个对象，使用了`@Configuration`注解，最终也会被放入容器中

`Config config = run.getBean("config", Config.class)`可以得到这个Config配置对象，通过打印可以查看这个对象的结果

```java
config: vin.cco.config.Config$$EnhancerBySpringCGLIB$$dfc1820a@7c9b78e3
```

可以看到这个对象是一个spring增强的代理对象

### `proxyBeanMethods`属性

这个属性有一个默认的值true，保证每个@Bean方法被调用多少次返回的组件都是单实例的，也就是如果这个值为true，那么无论是通过从容器中获取此对象，还是通过`run.getBean("config", Config.class).user01()`获取的对象，都是单实例的，也就是同一个对象，他们的哈希值是同一个

反之为false的时候，因为在容器中的对象永远都是同一个，所以通过获取的对象是同一个，因为这个对象在启动的时候，是被放入ServletContext中的

```java
User user01 = run.getBean("user01", User.class);
User user02 = run.getBean("user01", User.class);
```

但是通过`run.getBean("config", Config.class).user01()`获得的对象都是生成的，不是同一个



### Full和Lite

Full就是`@Configuration(proxyBeanMethods = true)`

> 如果为true的时候，假如通过`run.getBean("config", Config.class).user01()`获取此对象，会先在容器中寻找是否存在`User`对象，如果存在，那么就使用容器中的应用，如果在容器中，不存在此对象，那么就创建此对象，这种方式会是程序加载的时候，变缓慢，因为存在判断操作



Lite就是`@Configuration(proxyBeanMethods = false)`

> 如果为false的时候，尽管容器中存在此对象，也不会去容器中寻找，会创建新的对象，所以加载时候，就会比较快，比起上面那种情况，所以在构建应用的时候，推荐使用false情况



> 除了可以在方法上写`@Bean`注解之外，我们还可以写`@Bean、@Component、@Controller、@Service、@Repository`等注解，其作用就是以前那些
>
> `@ComponentScan`包扫描注解



## `@Import`

此注解的作用，就是将对象添加到容器中，可能某些对象，并没有使用注解添加到组件，可以使用此注解将其添加到容器中，此注解放在类上面，放在哪个类上都可以（包结构下），但是推荐将其放在配置类上

```java
public @interface Import {
    Class<?>[] value();
}
```

参数为Class数组，所以只需要将需要添加的类的Class传入就可以将其添加到容器中

```java
@Import({String.class, Animate.class})
```



容器中导入组件命名规则

```java
java.lang.String
vin.cco.domain.Animate
```



## @Conditional条件装配

在某些情况下，我们需要容器中，没有或者有或者其他的条件时，自动装配容器中的对象，那么就额可以使用这个注解进行装配



继承结构

![image-20210613103237642](http://ooszy.cco.vin/img/blog-note/image-20210613103237642.png?x-oss-process=style/pictureProcess1)



- 使用案例

比如，在容器中，没有含有`pet01`bean时，就创建`user01`bean，将其添加到容器中

```java
public class Config {

    @ConditionalOnMissingBean(name = "pet01")
    @Bean
    public User user01() {
        return new User("chuchen",12);
    }

    //@Bean
    public Pet pet01() {
        return new Pet("hachi");
    }
}

//方法可以判断容器中，是否含有某个beaN
boolean pet01 = run.containsBean("pet01");
boolean user01 = run.containsBean("user01");
```



这个注解，可以用在方法上面，用在方法上面，那么条件为真，那么这个方法就会执行，反之

如果将此注解放在类上，那么只有条件满足时，这个类中的配置才会生效



> 但是测试发现，如果用在类上，那么`@ConditionalOnMissingBean(name = "pet01")`这种情况是满足的，并且使用了`@Bean`，反而在pet01中，有`@Bean`是不满足的`@ConditionalOnBean(name = "pet01")`
>
> ```java
> @ConditionalOnMissingBean(name = "pet01")
> @Import({String.class, Animate.class})
> @Configuration(proxyBeanMethods = true)
> public class Config {
> 
>     @Bean
>     public User user01() {
>         return new User("chuchen",12);
>     }
> 
>     @Bean
>     public Pet pet01() {
>         System.out.println("pet01");
>         return new Pet("hachi");
>     }
> }
> ```



## @ImportResource

如果我们现在创建一个springxml的配置文件，并在其中，添加如下内容

```xml
<bean id="petxml" class="vin.cco.domain.Pet" >
    <property name="petName" value="petnamexml" />
</bean>
```

那么在容器中，不会存在此`petxml`对象，因为默认springboot是不认这个配置文件的，必须`在@Configuration`的配置类上面加上`@ImportResource`注解，将此springxml文件引入，才可以生效

```java
@ImportResource("classpath:springboot.xml")
```

> `必须要在配置类上面才会生效，如果在其他不适配置类上面添加@ImportResource注解，不会生效`
>
> 只有在容器中的组件，才能使用springboot提供的功能



## 配置绑定

如果我们将某些信息写在properties文件中，那么在使用里面信息的时候，正常方法就是获取这个文件的流，然后在从里面取出我们需要的信息，但是这种方法的话，对于springboot太繁琐了，springboot提供了注解，用来将`application.properties`文件中的某些信息进行绑定

实现配置文件对属性赋值操作

```java
public class Car {
    private String carName;
    private int sale;

    public Car () {
    }

    public Car (String carName, int sale) {
        this.carName = carName;
        this.sale = sale;
    }

    public String getCarName () {
        return carName;
    }
    public void setCarName (String carName) {
        this.carName = carName;
    }

    public int getSale () {
        return sale;
    }

    public void setSale (int sale) {
        this.sale = sale;
    }

    @Override
    public String toString () {
        return "Car{" + "carName='" + carName + '\'' + ", sale=" + sale + '}';
    }
}
```

```java
@Autowired
private Car car;

@RequestMapping("/car")
public Car returnCar() {
    return car;
}
```

```properties
mycar.carName = byd
mycar.sale = 10000
```



- 方法一

    在这个domain类上面添加两个注解

    ```java
    @Component
    @ConfigurationProperties(prefix = "mycar")
    ```

    `@ConfigurationProperties(prefix = "mycar")`作用就是将application.pro;perties配置文件中的`mycar.carName`等信息，和这个类中的属性进行绑定，`prefix`值为配置文件中的`mycar.carName`前缀，需要保证相同，并且需要保证，`mycar.carName`中的`carName`属性在Car类中，存在`setCarName()`方法，否则会报错

    还需要添加`@Component`这个注解，其作用就是将此组件car添加到容器中，因为只有在容器中的组件，才能使用springboot的功能

- 方法二

    1. 在Car类上，添加`@ConfigurationProperties(prefix = "mycar")`注解

    2. 在配置类上，添加`@EnableConfigurationProperties(Car.class)`注解，其作用就是

        > - 开启Car配置绑定功能
        > - 将这个Car组件，注册到容器中

    

# 源码分析

```java
@SpringBootApplication == {
    @SpringBootConfiguration
    @EnableAutoConfiguration
    @ComponentScan()
}
```

`@SpringBootApplication`注解等于后面三个，通过源码就可以看到，

- @SpringBootConfiguration

    此注解，等于

    ```java
    @Configuration
    @Indexed
    ```

    所以可以看到，此主启动程序就是一个配置类，因为有`@Configuration`注解

- @ComponentScan

    此注解，点击进去之后，可以看到，存在`@Repeatable(ComponentScans.class)`

- `@EnableAutoConfiguration`

    这个注解是最重要的，其有下面两个注解组成

    ```java
    @AutoConfigurationPackage
    @Import({AutoConfigurationImportSelector.class})
    ```

    `@AutoConfigurationPackage`注解又是由`@Import({Registrar.class})`，所以这个注解的作用就是将组件添加到容器中

    ```java
    static class Registrar implements ImportBeanDefinitionRegistrar, DeterminableImports {
        Registrar() {
        }
    
        public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
            AutoConfigurationPackages.register(registry, (String[])(new AutoConfigurationPackages.PackageImports(metadata)).getPackageNames().toArray(new String[0]));
        }
    
        public Set<Object> determineImports(AnnotationMetadata metadata) {
            return Collections.singleton(new AutoConfigurationPackages.PackageImports(metadata));
        }
    }
    ```

    dubug发现`AnnotationMetadata metadata`的值，就是主启动类的全限定名称，也就是这个标注是主类

    `BeanDefinitionRegistry registry`可以通过属性值，看到很多信息

    ![image-20210613171627834](http://ooszy.cco.vin/img/blog-note/image-20210613171627834.png?x-oss-process=style/pictureProcess1)

    这些信息中，除了spring默认的之外，都是我们将要添加到容器中的对象

    `new AutoConfigurationPackages.PackageImports(metadata).getPackageNames()`可以得到主类所在的包名，这个就是确定默认的包结构



## @EnableAutoConfiguration分析

这个组件能够批量导入组件

`AutoConfigurationImportSelector`类中的`protected List<String> getCandidateConfigurations(AnnotationMetadata metadata, AnnotationAttributes attributes) `返回的list集合中存放的就是需要加载的组件信息，`private static Map<String, List<String>> loadSpringFactories(ClassLoader classLoader)`此方法就是从配置文件中，加载需要的组件信息，并且是从所有包中的`Enumeration urls = classLoader.getResources("META-INF/spring.factories")`META-INF/spring.factories进行加载，在每一个jar中，都有

![image-20210613175220597](http://ooszy.cco.vin/img/blog-note/image-20210613175220597.png?x-oss-process=style/pictureProcess1)

文件，但是最重要的还是`spring-boot-autoconfigure-2.4.6.jar`中的配置，会加载这些

```java
# Auto Configure
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration,\
org.springframework.boot.autoconfigure.aop.AopAutoConfiguration,\
org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration,\
org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration,\
org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration,\
org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration,\
org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration,\
org.springframework.boot.autoconfigure.context.LifecycleAutoConfiguration,\
org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration,\
org.springframework.boot.autoconfigure.couchbase.CouchbaseAutoConfiguration,\
org.springframework.boot.autoconfigure.dao.PersistenceExceptionTranslationAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseReactiveDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseReactiveRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveElasticsearchRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveElasticsearchRestClientAutoConfiguration,\
org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.ldap.LdapRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.mongo.MongoReactiveRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.neo4j.Neo4jReactiveDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.neo4j.Neo4jReactiveRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.neo4j.Neo4jRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.solr.SolrRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.r2dbc.R2dbcDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.r2dbc.R2dbcRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration,\
org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration,\
org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration,\
org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration,\
org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientAutoConfiguration,\
org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration,\
org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration,\
org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration,\
org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration,\
org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration,\
org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration,\
org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration,\
org.springframework.boot.autoconfigure.hazelcast.HazelcastJpaDependencyAutoConfiguration,\
org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration,\
org.springframework.boot.autoconfigure.http.codec.CodecsAutoConfiguration,\
org.springframework.boot.autoconfigure.influx.InfluxDbAutoConfiguration,\
org.springframework.boot.autoconfigure.info.ProjectInfoAutoConfiguration,\
org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration,\
org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.JndiDataSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration,\
org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration,\
org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration,\
org.springframework.boot.autoconfigure.jms.JndiConnectionFactoryAutoConfiguration,\
org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration,\
org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration,\
org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration,\
org.springframework.boot.autoconfigure.jooq.JooqAutoConfiguration,\
org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration,\
org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration,\
org.springframework.boot.autoconfigure.availability.ApplicationAvailabilityAutoConfiguration,\
org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapAutoConfiguration,\
org.springframework.boot.autoconfigure.ldap.LdapAutoConfiguration,\
org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration,\
org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration,\
org.springframework.boot.autoconfigure.mail.MailSenderValidatorAutoConfiguration,\
org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration,\
org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration,\
org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration,\
org.springframework.boot.autoconfigure.mustache.MustacheAutoConfiguration,\
org.springframework.boot.autoconfigure.neo4j.Neo4jAutoConfiguration,\
org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,\
org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration,\
org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration,\
org.springframework.boot.autoconfigure.r2dbc.R2dbcTransactionManagerAutoConfiguration,\
org.springframework.boot.autoconfigure.rsocket.RSocketMessagingAutoConfiguration,\
org.springframework.boot.autoconfigure.rsocket.RSocketRequesterAutoConfiguration,\
org.springframework.boot.autoconfigure.rsocket.RSocketServerAutoConfiguration,\
org.springframework.boot.autoconfigure.rsocket.RSocketStrategiesAutoConfiguration,\
org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration,\
org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration,\
org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration,\
org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration,\
org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration,\
org.springframework.boot.autoconfigure.security.rsocket.RSocketSecurityAutoConfiguration,\
org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyAutoConfiguration,\
org.springframework.boot.autoconfigure.sendgrid.SendGridAutoConfiguration,\
org.springframework.boot.autoconfigure.session.SessionAutoConfiguration,\
org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration,\
org.springframework.boot.autoconfigure.security.oauth2.client.reactive.ReactiveOAuth2ClientAutoConfiguration,\
org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration,\
org.springframework.boot.autoconfigure.security.oauth2.resource.reactive.ReactiveOAuth2ResourceServerAutoConfiguration,\
org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration,\
org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration,\
org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration,\
org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration,\
org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration,\
org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration,\
org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration,\
org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration,\
org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.HttpHandlerAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.function.client.ClientHttpConnectorAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration,\
org.springframework.boot.autoconfigure.websocket.reactive.WebSocketReactiveAutoConfiguration,\
org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration,\
org.springframework.boot.autoconfigure.websocket.servlet.WebSocketMessagingAutoConfiguration,\
org.springframework.boot.autoconfigure.webservices.WebServicesAutoConfiguration,\
org.springframework.boot.autoconfigure.webservices.client.WebServiceTemplateAutoConfiguration
```

最终`List<String> configurations = SpringFactoriesLoader.loadFactoryNames(this.getSpringFactoriesLoaderFactoryClass(), this.getBeanClassLoader())`list的长度就是spring.factories中上面列出的长度

但是虽然springboot会一次性加载这么多的组件，但是最终，在容器中存在的，上面的有一些是没有的，容器中存在的某些组件，上面这个配置中也并没有，这个就是springboot的按需加载的应用





## 逐步分析



### 缓存

![image-20210702091925468](http://ooszy.cco.vin/img/blog-note/image-20210702091925468.png?x-oss-process=style/pictureProcess1)

在我们的自动配置包中，存在上面这个用于缓存功能的内，那么我们现在就来分析一下，他是否在项目中，有

@ConditionalOnBean         //	当给定的在bean存在时,则实例化当前Bean @ConditionalOnMissingBean  //	当给定的在bean不存在时,则实例化当前Bean @ConditionalOnClass        //	当给定的类名在类路径上存在，则实例化当前Bean @ConditionalOnMissingClass //	当给定的类名在类路径上不存在，则实例化当前Bean

- 看到此类的注解

    ```java
    @Configuration(
        proxyBeanMethods = false
    )
    @ConditionalOnClass({CacheManager.class})
    @ConditionalOnBean({CacheAspectSupport.class})
    @ConditionalOnMissingBean(
        value = {CacheManager.class},
        name = {"cacheResolver"}
    )
    @EnableConfigurationProperties({CacheProperties.class})
    @AutoConfigureAfter({CouchbaseDataAutoConfiguration.class, HazelcastAutoConfiguration.class, HibernateJpaAutoConfiguration.class, RedisAutoConfiguration.class})
    @Import({CacheAutoConfiguration.CacheConfigurationImportSelector.class, CacheAutoConfiguration.CacheManagerEntityManagerFactoryDependsOnPostProcessor.class})
    public class CacheAutoConfiguration {
    ```

    `@ConditionalOnClass({CacheManager.class})`判断此CacheManager.class是否存在，`@ConditionalOnBean({CacheAspectSupport.class})`判断此类型的bean是否存在，我们通过在主配置文件中，可以看到他们都不存在

    ```java
    System.out.println("----------------------");
    String[] beanNamesForType = run.getBeanNamesForType(CacheAspectSupport.class);
    System.out.println(beanNamesForType.length);
    
    String[] CacheManager = run.getBeanNamesForType(CacheManager.class);
    System.out.println(CacheManager.length);
    ```

    返回的长度都是0，那么条件不满足，项目便不会进行此配置类中的方法等操作了

    @Configuration(proxyBeanMethods = false)设置这是一个配置组件，并且此配置组件是一个lite，因为设置了false



### web

如果我们创建的是一个web应用，再来看看情况

```java
@AutoConfigureOrder(-2147483648)
@Configuration(
    proxyBeanMethods = false
)
@ConditionalOnWebApplication(
    type = Type.SERVLET
)
@ConditionalOnClass({DispatcherServlet.class})
@AutoConfigureAfter({ServletWebServerFactoryAutoConfiguration.class})
```



@ConditionalOnWebApplication(type = Type.SERVLET)这是一个web应用才满足条件，满足，并且type = Type.SERVLET指明了这个web的servlet必须是一个原生的，我们在容器中能够得到这个class，`DispatcherServlet和ServletWebServerFactoryAutoConfiguration`，所以会进行类中的配置



- 配置dispatcherServlet属性

```java
@Configuration(
    proxyBeanMethods = false
)
@Conditional({DispatcherServletAutoConfiguration.DefaultDispatcherServletCondition.class})
@ConditionalOnClass({ServletRegistration.class})
@EnableConfigurationProperties({WebMvcProperties.class})
protected static class DispatcherServletConfiguration {
    protected DispatcherServletConfiguration() {
    }

    @Bean(
        name = {"dispatcherServlet"}
    )
    public DispatcherServlet dispatcherServlet(WebMvcProperties webMvcProperties) {
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.setDispatchOptionsRequest(webMvcProperties.isDispatchOptionsRequest());
        dispatcherServlet.setDispatchTraceRequest(webMvcProperties.isDispatchTraceRequest());
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(webMvcProperties.isThrowExceptionIfNoHandlerFound());
        dispatcherServlet.setPublishEvents(webMvcProperties.isPublishRequestHandledEvents());
        dispatcherServlet.setEnableLoggingRequestDetails(webMvcProperties.isLogRequestDetails());
        return dispatcherServlet;
    }

    @Bean
    @ConditionalOnBean({MultipartResolver.class})
    @ConditionalOnMissingBean(
        name = {"multipartResolver"}
    )
    public MultipartResolver multipartResolver(MultipartResolver resolver) {
        return resolver;
    }
}
```



`@EnableConfigurationProperties({WebMvcProperties.class})`通过配置绑定，也就是将配置文件中的`spring.mvc`值都赋值给这个WebMvcProperties类中的属性，这个类中存在大量的set方法



```java
@Bean(
    name = {"dispatcherServlet"}
)
public DispatcherServlet dispatcherServlet(WebMvcProperties webMvcProperties) {
    DispatcherServlet dispatcherServlet = new DispatcherServlet();
    
此方法中，就是配置dispatcherservlet中的属性，这个也就是做了原来springmvc.xml文件中的工作，这个就是为什么我们什么都不同配置，直接使用springboot就可以了，这些配置也就是在这个方法中，进行配置的
```



```java
@Bean
@ConditionalOnBean({MultipartResolver.class})
@ConditionalOnMissingBean(
    name = {"multipartResolver"}
)
public MultipartResolver multipartResolver(MultipartResolver resolver) {
    return resolver;
}
这个是文件上传解析器的配置，如果容器中，存在MultipartResolver对象，不存在一个名为multipartResolver的bean，那么就会执行这个方法，在使用@Bean注解的方法中，如果参数是一个对象的话，那么在创建此bean的时候，就会从容器中，寻找此类对象，并将此类对象自动赋值给这个对象，此操作可以防止，我们使用其他方法配置文件上传解析器的时候，没有使用规范的bean名字multipartResolver
```



## 总结

多看几个源码的分析，我们便可以看到springboot的很多配置都已经帮我们配置好了，但是我们也可以使用我们自己定制的，使用我们自己的可以有两种方法

- 方法一

    因为springboot的配置，都会有一个`@ConditionalOnMissingBean()`判断注解，所以我们可以直接在我们的配置类中，创建一个新的@Bean，比如dispatcherServlet，那么这个bean的名字就叫dispatcherServlet，便可以替换原来的配置

- 方法二

    因为springboot的每一个默认配置，都是和一个配置文件进行绑定的，所以我们可以直接在配置文件中，直接修改就就可以了，这些配置文件的命名都是使用`***Properties`的方法，在这些配置文件中，便可以看到`@ConfigurationProperties()`在配置文件中的前缀，我们直接修改就可以了，也可以通过查看文档的方式，查看修改此配置的配置文件的前缀





# 最佳实践

我们可以很快速的开始一个一个springboot的应用，其步骤如下



- 引入一个我们需要的场景，此场景可以在https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.build-systems.starters中查看

- 查看自己需要的配置，或者修改，可以在自动配置类中，一个一个的分析，但是这样的话，太慢了，springboot就提供了一种方法，如果我们打开`debug=true`，那么在启动的时候，就会生成一个评估报告，此报告中，可以看到生效的和没有生效的有哪些，和没有生效的额原因是什么，都可以直接看到

    ```java
    ============================
    CONDITIONS EVALUATION REPORT
    ============================
    
    这个是生效的
    Positive matches:
    -----------------
    
       AopAutoConfiguration matched:
          - @ConditionalOnProperty (spring.aop.auto=true) matched (OnPropertyCondition)
    
       AopAutoConfiguration.AspectJAutoProxyingConfiguration matched:
          - @ConditionalOnClass found required class 'org.aspectj.weaver.Advice' (OnClassCondition)
              
              
    没有生效的
    Negative matches:
    -----------------
    
        ActiveMQAutoConfiguration:
    Did not match:
    - @ConditionalOnClass did not find required class 'javax.jms.ConnectionFactory' (OnClassCondition)
    
        AopAutoConfiguration.AspectJAutoProxyingConfiguration.JdkDynamicAutoProxyConfiguration:
    Did not match:
    - @ConditionalOnProperty (spring.aop.proxy-target-class=false) did not find property 'proxy-target-class' (OnPropertyCondition)
    ```

    

- 自定义加入或者替换组件

    @Bean、@Component。。。

- 自定义器  **XXXXXCustomizer**；

我们也可以更改启动的banner，也就是这个，可以是一张图片或者其他

![image-20210702110849613](http://ooszy.cco.vin/img/blog-note/image-20210702110849613.png?x-oss-process=style/pictureProcess1)

可以通过此配置项进行修改

![image-20210702110940676](http://ooszy.cco.vin/img/blog-note/image-20210702110940676.png?x-oss-process=style/pictureProcess1)



`spring.banner.image.location`便可以使用图片作为启动，默认值为`classpath:banner.gif`，我们将一张图片作为启动

测试使用下面这张图片作为启动banner

![image-20210702111415803](http://ooszy.cco.vin/img/blog-note/image-20210702111415803.png?x-oss-process=style/pictureProcess1)

spring.banner.image.location=classpath:33.jpg

这里需要进行激活一下，也就是先将这张图片改为`banner.jpg`，启动成功之后，在改为任意的名字

那么启动之后，就变为

![image-20210702111542415](http://ooszy.cco.vin/img/blog-note/image-20210702111542415.png?x-oss-process=style/pictureProcess1)

展示的并不是一张图片，而是文本



也可以直接使用文本，创建一个TXT，然后一样的操作



