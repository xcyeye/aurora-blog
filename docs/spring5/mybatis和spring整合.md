# mybatis和spring整合开发

将 MyBatis 与 Spring 进行整合，主要解决的问题就是将 SqlSessionFactory 对象交由 Spring

来管理。所以，该整合，只需要将 SqlSessionFactory 的对象生成器 SqlSessionFactoryBean 注

册在 Spring 容器中，再将其注入给 Dao 的实现类即可完成整合。

实现 Spring 与 MyBatis 的整合常用的方式：扫描的 Mapper 动态代理

Spring 像插线板一样，mybatis 框架是插头，可以容易的组合到一起。插线板 spring 插 

上 mybatis，两个框架就是一个整体。


## maven依赖

```xml
<dependencies>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.11</version>
        <scope>test</scope>
    </dependency>

    <!--spring依赖-->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>5.2.5.RELEASE</version>
    </dependency>

    <!--spring事务依赖-->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-tx</artifactId>
        <version>5.2.5.RELEASE</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-jdbc</artifactId>
        <version>5.2.5.RELEASE</version>
    </dependency>

    <!--mybatis和spring集成的依赖-->
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>3.5.1</version>
    </dependency>

    <!--阿里公司的数据库连接池-->
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis-spring</artifactId>
        <version>1.3.1</version>
    </dependency>

    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>5.1.9</version>
    </dependency>
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>druid</artifactId>
        <version>1.1.12</version>
    </dependency>
</dependencies>
```



因为是spring和mybatis的整合，所以我们使用ioc容器进行对象的管理

```xml
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis-spring</artifactId>
    <version>1.3.1</version>
</dependency>
```

这个依赖是mybatis提供的，就是用于spring创建sqlsession等对象使用

```
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-tx</artifactId>
    <version>5.2.5.RELEASE</version>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>5.2.5.RELEASE</version>
</dependency>
```

这两个依赖，是用于执行事务的时候，使用的依赖

因为mybatis的`.xml`文件，不是在resource包中，所以还需要一个包含插件，用于能够解析到xml文件

```xml
<resources>
    <resource>
        <directory>src/main/java</directory><!--所在的目录-->
        <includes><!--包括目录下的.properties,.xml 文件都会扫描到-->
            <include>**/*.properties</include>
            <include>**/*.xml</include>
        </includes>
        <filtering>false</filtering>
    </resource>
</resources>
```



## 新建配置文件

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <settings>
        <!--设置mybatis输出日志-->
        <setting name="logImpl" value="STDOUT_LOGGING"/>
    </settings>

    <mappers>
        <package name="vin.cco.dao"/>
    </mappers>
</configuration>
```

因为使用到spring和mybatis的整合，所以我们创建配置文件的时候，就不用像mybatis的主配置文件那样复杂

只需要在配置文件中指明dao的`xml`文件位置就可以了

`<package name="vin.cco.dao"/>`这种写法，就相当于是包含了dao包下的所有的`.xml`配置文件



# druid的spring配置文档

官方配置文档

```xml
 <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close"> 
     <property name="url" value="${jdbc_url}" />
     <property name="username" value="${jdbc_user}" />
     <property name="password" value="${jdbc_password}" />

     <property name="filters" value="stat" />

     <property name="maxActive" value="20" />
     <property name="initialSize" value="1" />
     <property name="maxWait" value="6000" />
     <property name="minIdle" value="1" />

     <property name="timeBetweenEvictionRunsMillis" value="60000" />
     <property name="minEvictableIdleTimeMillis" value="300000" />

     <property name="testWhileIdle" value="true" />
     <property name="testOnBorrow" value="false" />
     <property name="testOnReturn" value="false" />

     <property name="poolPreparedStatements" value="true" />
     <property name="maxOpenPreparedStatements" value="20" />

     <property name="asyncInit" value="true" />
 </bean>
```

`init-method="init" destroy-method="close"`这个是spring自动会调用的，也就是当spring容器创建这个对象的时候，spring就会自动调用`init`方法执行初始化，当关闭这个对象的时候，spring就会调用`close`方法关闭，其实也就是使用连接和使用完连接的时候，因为只有使用druid的时候，就是使用mysql连接，也就是创建对象

`<property name="url" value="${jdbc_url}" />`这种写法，就相当于是初始化这个对象中的url的属性值，其中value的值，就是这个对象对应属性的值

> - 在上面的配置中，通常你需要配置url、username、password，maxActive这三项。
> - Druid会自动跟url识别驱动类名，如果连接的数据库非常见数据库，配置属性driverClassName
> - asyncInit是1.1.4中新增加的配置，如果有initialSize数量较多时，打开会加快应用启动时间



所以我们在使用的时候，就可以直接复制三项就可以了，也就是

```xml
 <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close"> 
     <property name="url" value="${jdbc_url}" />
     <property name="username" value="${jdbc_user}" />
     <property name="password" value="${jdbc_password}" />
</bean>
```

需要使用到`<property>`标签，因为我们使用的是`set`注入的方式，也就是`<property name="url" value="${jdbc_url}" />`对应对象中的`setUrl()`方法



### spring配置

```xml
<bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource"
      init-method="init" destroy-method="close">
    <property name="url" value="jdbc:mysql://localhost:3306/mybatis" />
    <property name="username" value="root" />
    <property name="password" value="123456" />
    <property name="maxActive" value="100" />
</bean>
```

当我们在配置的时候，idea都会进行提示

![image-20210522103958880](http://ooszy.cco.vin/img/blog-note/image-20210522103958880.png?x-oss-process=style/pictureProcess1)



当点击`name="url"`就可以进入到源码中

在源码中，其对应的方法为

```java
public void setUrl(String jdbcUrl) {
    if (!StringUtils.equals(this.jdbcUrl, jdbcUrl)) {
        if (this.inited) {
            throw new UnsupportedOperationException();
        } else {
            if (jdbcUrl != null) {
                jdbcUrl = jdbcUrl.trim();
            }

            this.jdbcUrl = jdbcUrl;
        }
    }
}
```

这个就是使用spring配置druid的过程，也就是使用配置文件管理druid对象的过程



## 创建SqlSessionFactory对象

因为使用SQLSession对象的话，需要使用`SqlSessionFactory`对象进行创建

原生方法创建这个对象是

```java
InputStream in = Resources.getResourceAsStream("mybatis.xml");
SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
SqlSessionFactory factory  = builder.build(in);
```

但是我们是使用spring和mybatis进行整合，所以此对象的创建，交给ioc进行管理

```
创建SqlSessionFactory对象
<bean id="sqlSession" class="org.mybatis.spring.SqlSessionFactoryBean">
    <property name="dataSource" ref="myDataSource" />
    <property name="configLocation" value="classpath:mybatis.xml" />
</bean>
```

因为创建这个`SqlSessionFactory`对象需要使用到数据库的一些链接信息，而且我们没有在mybatis的主配置文件中写出，因为我们将这个过程交给spring来做

`<property name="dataSource" ref="myDataSource" />`就可以使用`ref`使用我们已经配置的连接池的信息

`<property name="configLocation" value="classpath:mybatis.xml" />`主配置文件的位置，这个`configLocation`属性，需要传入一个`Resource`对象，所以我们需要使用到`classpath:`后面接的就是mybatis主配置文件的位置

```java
public void setConfigLocation(Resource configLocation) {
    this.configLocation = configLocation;
}
```



现在这个SqlSessionFactory对象就已经创建成功了，spring和mybatis的整合文件模板，基本上都是这个固定形式，只是配置文件的位置的不同，以后可以直接使用



### 测试

```java
@Test
public void test1() throws Exception {
    String path = "applicationContext.xml";
    ApplicationContext context = new ClassPathXmlApplicationContext(path);
    for (String name : context.getBeanDefinitionNames()) {
        System.out.println(name + " ---> "+ context.getBean(name).getClass());
    }
}
```

```java
myDataSource ---> class com.alibaba.druid.pool.DruidDataSource
sqlSession ---> class org.apache.ibatis.session.defaults.DefaultSqlSessionFactory
```

可以看到，成功创建了这两个对象，SqlSessionFactory这个对象是我们获取SQLSession对象需要使用到的，mybatis的创建就是使用这个对象进行获取

这个配置就是spring和mybatis的一个整合





## 创建dao对象

如果要执行sql语句的话，那么就需要使用到一个SQLSession对象，通过这个对象的`getMappper()`方法可以创建一个dao对象

原生方法创建SQLSession对象

```java
InputStream in = Resources.getResourceAsStream("mybatis.xml");
SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
SqlSessionFactory factory  = builder.build(in);
SqlSession sqlSession =  factory.openSession();
```

- 使用spring的方式

    ```xml
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory" />
        <property name="basePackage" value="vin.cco.dao" />
    </bean>
    ```

创建这个对象的作用:

> MapperScannerConfigurer:在内部调用getMapper()生成每个dao接口的代理对象。

创建这个对象的过程中，我们需要将创建的`SqlSessionFactory`对象传入`<property name="sqlSessionFactoryBeanName" value="sqlSessionFactory" />`，在这个对象创建的内部，就已经创建了这个SqlSession对象，需要看源码



这个`MapperScannerConfigurer`对象的作用，就是`在内部调用getMapper()生成每个dao接口的代理对象`，并且`<property name="basePackage" value="vin.cco.dao" />`标签就是指明这些dao类的位置，然后这个`MapperScannerConfigurer`对象就会把这个包中的所有的`接口`都执行一遍，也就是`getMapper(***.class)`创建这个接口的对象，然后将这些创建的对象存在在容器中



所以测试

```java
@Test
public void test1() throws Exception {
    String path = "applicationContext.xml";
    ApplicationContext context = new ClassPathXmlApplicationContext(path);
    System.out.println("--------------\n\n");
    for (String name : context.getBeanDefinitionNames()) {
        System.out.println(name + " ---> "+ context.getBean(name).getClass());
    }
}
```

现在dao包中，就只有一个接口

![image-20210522125843934](http://ooszy.cco.vin/img/blog-note/image-20210522125843934.png?x-oss-process=style/pictureProcess1)

运行结果

```java
myDataSource ---> class com.alibaba.druid.pool.DruidDataSource
sqlSessionFactory ---> class org.apache.ibatis.session.defaults.DefaultSqlSessionFactory
org.mybatis.spring.mapper.MapperScannerConfigurer#0 ---> class org.mybatis.spring.mapper.MapperScannerConfigurer
studentDao ---> class com.sun.proxy.$Proxy9
    
//下面的是spring的对象
org.springframework.context.annotation.internalConfigurationAnnotationProcessor ---> class org.springframework.context.annotation.ConfigurationClassPostProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor ---> class org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor
org.springframework.context.event.internalEventListenerProcessor ---> class org.springframework.context.event.EventListenerMethodProcessor
org.springframework.context.event.internalEventListenerFactory ---> class org.springframework.context.event.DefaultEventListenerFactory
```



`studentDao ---> class com.sun.proxy.$Proxy9`这个对象就是`dao`包中的`StudentDao`接口的对象，对象的名字不是我们自己定义的，这个是spring自己定义的名字，并且对象的名字就是接口名首字母小写得到的，这是一个代理对象`class com.sun.proxy.$Proxy9`，并且使用的是jdk的动态代理



假如我们又新建了一个接口

![image-20210522130250936](http://ooszy.cco.vin/img/blog-note/image-20210522130250936.png?x-oss-process=style/pictureProcess1)

那么现在执行测试，不用改动spring的配置

```java
myDataSource ---> class com.alibaba.druid.pool.DruidDataSource
sqlSessionFactory ---> class org.apache.ibatis.session.defaults.DefaultSqlSessionFactory
org.mybatis.spring.mapper.MapperScannerConfigurer#0 ---> class org.mybatis.spring.mapper.MapperScannerConfigurer
orderDao ---> class com.sun.proxy.$Proxy9
studentDao ---> class com.sun.proxy.$Proxy10
```

自动创建这个dao对象

### 大致过程

所以，创建dao对象的大致过程就是

1. 创建数据源
2. 创建Factory
3. 获取dao



### 测试sql插入

```java
@Test
public void test2() throws Exception {
    String path = "applicationContext.xml";
    ApplicationContext context = new ClassPathXmlApplicationContext(path);
    StudentDao dao = (StudentDao) context.getBean("studentDao");
    Student student = new Student(1,"chuchen","2202@qq.com",21);
    int i = dao.insertStudentDao(student);
    System.out.println(i);
}
```

成功插入

需要注意的就是`StudentDao dao = (StudentDao) context.getBean("studentDao")`参数中的bean名字就是接口首字母小写的名字



## 通过service执行dao方法

上面过程中，是直接使用dao中的方法执行的，但是我们一般都是通过service进行dao的调用

那么使用这种方式的时候，就需要创建service对象，这个也是在spring中进行管理的

```xml
<bean id="myService" class="vin.cco.service.impl.StudentServiceImpl">
    <property name="dao" ref="studentDao" />
</bean>
```

因为在执行spring配置文件中的时候，就已经创建了StudentDao对象，所以在这里，我们可以直接引用，通过`<property name="dao" ref="studentDao" />`的ref

![image-20210522133313990](http://ooszy.cco.vin/img/blog-note/image-20210522133313990.png?x-oss-process=style/pictureProcess1)



```java
@Test
public void test3() throws Exception {
    String path = "applicationContext.xml";
    ApplicationContext context = new ClassPathXmlApplicationContext(path);
    StudentService service = (StudentService) context.getBean("myService");
    List<Student> students = service.selectAllSer();
    for (Student student : students) {
        System.out.println(student);
    }
}
```



```java
Student{id=1, name='chuchen', email='2202@qq.com', age=21}
```



## 集合properties配置文件一起使用

因为在配置数据库连接池的时候，数据库的一些信息都是写在spring配置文件中的，这样会是项目变得难维护，所以可以将他们分开，也就是数据库的信息，单独写在一个配置文件中

```properties
jdbc.url = jdbc:mysql://localhost:3306/mybatis
jdbc.username = root
jdbc.pwd = 123456
jdbc.maxActive = 20
```

```
<!--引入properties文件-->
<context:property-placeholder location="jdbc.properties" />

<bean id="myDataSource" class="com.alibaba.druid.pool.DruidDataSource"
      init-method="init" destroy-method="close">
    <property name="url" value="${jdbc.url}" />
    <property name="username" value="${jdbc.username}" />
    <property name="password" value="${jdbc.pwd}" />
    <property name="maxActive" value="${jdbc.maxActive}" />
</bean>
```

现在如果需要切换数据库的连接信息，就可以直接在properties文件中进行更改就行



# 事务控制

spring给我们提供了事务控制的相关类，我们只需要使用就可以了

## 为何要使用spring的事务控制

因为如果我们使用的是jdbc，那么我们需要去熟悉这个类的使用过程，还有对于事务的使用过程，如果使用的是mybatis，那么就需要去学习其相关的事务控制类

这样会让我们学习的成本大大加大，spring已经给我们提供了事务控制的相关类，我们只需要去使用就可以了

比如我们使用的是mybatis，那么就只需要声明是mybatis就可以，如果使用的是hibernate，那么就只需要声明我们使用的连接数据库使用的是这个就行

声明之后，我们无论使用什么工具进行事务，都使用spring的事务管理器相关的方法就可以了



## spring事务管理

事务原本是数据库中的概念，在 Dao 层。但一般情况下，需要将事务提升到业务层，

即 Service 层。这样做是为了能够使用事务的特性来管理具体的业务。

在 Spring 中通常可以通过以下两种方式来实现对事务的管理：

> （1）使用 Spring 的事务注解管理事务
>
> （2）使用 AspectJ 的 AOP 配置管理事务



## 事务管理器

事务管理器是 PlatformTransactionManager 接口对象。其主要用于完成事务的提交、回

滚，及获取事务的状态信息。

![image-20210522163611212](http://ooszy.cco.vin/img/blog-note/image-20210522163611212.png?x-oss-process=style/pictureProcess1)



PlatformTransactionManager 接口有两个常用的实现类：

➢ DataSourceTransactionManager：使用 JDBC 或 MyBatis 进行数据库操作时使用。

➢ HibernateTransactionManager：使用 Hibernate 进行持久化数据时使用。



### 回滚

Spring 事务的默认回滚方式是：发生运行时异常和 error 时回滚，发生受查(编译)异常时

提交。不过，对于受查异常，程序员也可以手工设置其回滚方式。 



#### 回滚时机



- 当你的业务方法，执行成功，没有异常抛出，当方法执行完毕，spring在方法执行后提交事务。事务管理器commit
- 当你的业务方法抛出运行时异常或ERROR， spring执行回滚，调用事务管理器的rollback
    运行时异常的定义： RuntimeException  和他的子类都是运行时异常， 例如NullPointException , NumberFormatException
- 当你的业务方法抛出非运行时异常， 主要是受查异常时，提交事务，受查异常：在你写代码中，必须处理的异常。例如IOException, SQLException

为何发生受查异常还会提交事务



## 事务定义接口

事务定义接口 TransactionDefinition 中定义了事务描述相关的三类常量：事务隔离级别、

事务传播行为、事务默认超时时限，及对它们的操作



### 五个隔离级别

这些常量均是以 ISOLATION_开头。即形如 ISOLATION_XXX。 

➢ DEFAULT：采用 DB 默认的事务隔离级别。MySql 的默认为 REPEATABLE_READ； Oracle

默认为 READ_COMMITTED。 

➢ READ_UNCOMMITTED：读未提交。未解决任何并发问题。

➢ READ_COMMITTED：读已提交。解决脏读，存在不可重复读与幻读。

➢ REPEATABLE_READ：可重复读。解决脏读、不可重复读，存在幻读

➢ SERIALIZABLE：串行化。不存在并发问题



### 七个传播行为

所谓事务传播行为是指，处于不同事务中的方法在相互调用时，执行期间事务的维护情

况。如，A 事务中的方法 doSome()调用 B 事务中的方法 doOther()，在调用执行期间事务的

维护情况，就称为事务传播行为。事务传播行为是加在方法上的。

事务传播行为常量都是以 PROPAGATION_ 开头，形如 PROPAGATION_XXX。

> `**PROPAGATION_REQUIRED**`
>
> `**PROPAGATION_REQUIRES_NEW**`
>
> `**PROPAGATION_SUPPORTS**`
>
> PROPAGATION_MANDATORY 
>
> PROPAGATION_NESTED
>
> PROPAGATION_NEVER
>
> PROPAGATION_NOT_SUPPORTED

标红的三个传播行为是项目中经常需要使用到的





#### **PROPAGATION_REQUIRED**

指定的方法必须在事务内执行。若当前存在事务，就加入到当前事务中；若当前没有事

务，则创建一个新事务。这种传播行为是最常见的选择，也是 Spring 默认的事务传播行为。

如该传播行为加在 doOther()方法上。若 doSome()方法在调用 doOther()方法时就是在事

务内运行的，则 doOther()方法的执行也加入到该事务内执行。若 doSome()方法在调用

doOther()方法时没有在事务内执行，则 doOther()方法会创建一个事务，并在其中执行。



![image-20210522164141529](http://ooszy.cco.vin/img/blog-note/image-20210522164141529.png?x-oss-process=style/pictureProcess1)



#### **PROPAGATION_SUPPORTS**

指定的方法支持当前事务，但若当前没有事务，也可以以非事务方式执行。

![image-20210522164209329](http://ooszy.cco.vin/img/blog-note/image-20210522164209329.png?x-oss-process=style/pictureProcess1)



#### **PROPAGATION_REQUIRES_NEW**

总是新建一个事务，若当前存在事务，就将当前事务挂起，直到新事务执行完毕。

![image-20210522164236276](http://ooszy.cco.vin/img/blog-note/image-20210522164236276.png?x-oss-process=style/pictureProcess1)



### **定义了默认事务超时时限**

常量 TIMEOUT_DEFAULT 定义了事务底层默认的超时时限，sql 语句的执行时长。

注意，事务的超时时限起作用的条件比较多，且超时的时间计算点较复杂。所以，该

值一般就使用默认值即可。



# spring开启事务

```java
public class SaleBuySerImpl implements SaleBuySer {
    private SaleDao saleDao;
    private GoodsDao goodsDao;

    /**
     *
     * @author chuchen
     * @date 2021/5/22 19:49
     * @param gid gid 购买商品的id
     * @param nums nums 购买商品的数量
     * @return int
     */
    @Override
    public int insertSaleDao (int gid, int nums) {
        Sale sale = new Sale(gid,nums);
        saleDao.insertSaleDao(sale);

        Goods goods = goodsDao.selectById(gid);
        System.out.println(goods);

        if (goods == null) {
            throw new MyException("编号为: "+gid+" 不存在商品");
        }else if (goods.getAmount() < nums){
            throw new MyException("编号为: "+gid+" 库存不足");
        }

        Goods buyGoods = new Goods();
        buyGoods.setId(gid);
        buyGoods.setAmount(nums);
        goodsDao.updateGoodsDao(buyGoods);

        return 0;
    }

    public void setSaleDao (SaleDao saleDao) {
        this.saleDao = saleDao;
    }

    public void setGoodsDao (GoodsDao goodsDao) {
        this.goodsDao = goodsDao;
    }
}
```

```java
@Test
public void test2() throws Exception {
    String path = "applicationContext.xml";
    ApplicationContext context = new ClassPathXmlApplicationContext(path);

    SaleBuySer ser = (SaleBuySer) context.getBean("myService");
    ser.insertSaleDao(2,3420);
}
```





## 声明事务管理器对象

```
<bean id="transactionManager"
      class="org.springframework.jdbc.datasource.DataSourceTransactionManager" >
    <property name="dataSource" ref="myDataSource" />
</bean>
<tx:annotation-driven transaction-manager="transactionManager" />
```

`<bean id="transactionManager">`声明一个事务管理器，这里需要使用到数据库的一些链接信息，因为需要声明使用的是哪种数据库，这里直接引用链接池的信息

`<tx:annotation-driven transaction-manager="transactionManager" />`开启事务注解驱动，只有声明这个之后，我们才可以成功使用注解事务，`transaction-manager`值就是事务管理器的id，声明这个很容器出错，因为`tx:annotation-driven`存在好几个相同的标签，使用的时候，一定需要直接，约束文件后面有一个tx

![image-20210522211356816](http://ooszy.cco.vin/img/blog-note/image-20210522211356816.png?x-oss-process=style/pictureProcess1)

使用最后一个就没有错



## 使用注解事务

通过@Transactional 注解方式，可将事务织入到相应 public 方法中，实现事务管理。

@Transactional 的所有可选属性如下所示：

➢ propagation：用于设置事务传播属性。该属性类型为 Propagation 枚举，默认值为Propagation.REQUIRED。 

➢ isolation：用于设置事务的隔离级别。该属性类型为 Isolation 枚举，默认值为

Isolation.DEFAULT。 

➢ readOnly：用于设置该方法对数据库的操作是否是只读的。该属性为 boolean，默认值

为 false。 

➢ timeout：用于设置本操作与数据库连接的超时时限。单位为秒，类型为 int，默认值为

-1，即没有时限。

➢ rollbackFor：指定需要回滚的异常类。类型为 Class[]，默认值为空数组。当然，若只有

一个异常类时，可以不使用数组。

➢ rollbackForClassName：指定需要回滚的异常类类名。类型为 String[]，默认值为空数组。

当然，若只有一个异常类时，可以不使用数组。

➢ noRollbackFor：指定不需要回滚的异常类。类型为 Class[]，默认值为空数组。当然，若

只有一个异常类时，可以不使用数组。

➢ noRollbackForClassName：指定不需要回滚的异常类类名。类型为 String[]，默认值为空

数组。当然，若只有一个异常类时，可以不使用数组。



> 需要注意的是，@Transactional 若用在方法上，只能用于 public 方法上。对于其他非 public
>
> 方法，如果加上了注解@Transactional，虽然 Spring 不会报错，但不会将指定事务织入到该
>
> 方法中。因为 Spring 会忽略掉所有非 public 方法上的@Transaction 注解。
>
> 若@Transaction 注解在类上，则表示该类上所有的方法均将在执行时织入事务



**实现注解的事务步骤：**

**复制** **trans_sale** **项目，新项目** **trans_sale_annotation**



使用

```java
@Transactional(
    propagation = Propagation.REQUIRED,
    isolation = Isolation.DEFAULT,
    rollbackFor = {NullPointerException.class,MyException.class, NotFoundException.class}
)
@Override
public int insertSaleDao (int gid, int nums) {
    Sale sale = new Sale(gid,nums);
    saleDao.insertSaleDao(sale);
}
```

这个就是事务注解

```java
@Transactional(
    propagation = Propagation.REQUIRED,
    isolation = Isolation.DEFAULT,
    rollbackFor = {NullPointerException.class,MyException.class, NotFoundException.class}
)
```

`propagation = Propagation.REQUIRED`声明传播行为

`isolation = Isolation.DEFAULT`声明事务类型

`rollbackFor = {NullPointerException.class,MyException.class, NotFoundException.class}`声明哪些异常触发回滚，可以看上面

因为上面的这个注解使用的就是默认值，所以我们可以直接写这个注解就可以了



```java
@Transactional(
    propagation = Propagation.REQUIRED,
    isolation = Isolation.DEFAULT,
    rollbackFor = {NullPointerException.class,MyException.class, NotFoundException.class}
)
@Override
public int insertSaleDao (int gid, int nums) {
    Sale sale = new Sale(gid,nums);
    saleDao.insertSaleDao(sale);

    Goods goods = goodsDao.selectById(gid);
    System.out.println(goods);

    if (goods == null) {
        throw new MyException("编号为: "+gid+" 不存在商品");
    }else if (goods.getAmount() < nums){
        throw new MyException("编号为: "+gid+" 库存不足");
    }

    Goods buyGoods = new Goods();
    buyGoods.setId(gid);
    buyGoods.setAmount(nums);
    goodsDao.updateGoodsDao(buyGoods);

    return 0;
}
```





![image-20210522211932994](http://ooszy.cco.vin/img/blog-note/image-20210522211932994.png?x-oss-process=style/pictureProcess1)

如果这里发生了异常，那么尽管已经插入了数据，那么也会触发回滚操作



## 执行的原理

其原理就使用到了切面通知中的环绕通知，其代码可以理解成下面这种

```java
@Around()
public Object buy() {

    try {
        //需要执行的代码
        comit();
    }catch (Exception e) {
        //异常
        roolback();
    }

    return new Object();
}
```

只是这个过程是spring在内部就帮我们实现了



### 是如何根据rollbackFor异常判断的？

首先，如果发生了异常，那么就会检查这个异常是否在`rollbackFor`数组中（不管这个异常是什么异常，受检异常还是运行时异常），如果这个异常在这个数组中，那么就会执行回滚

如果这个发生的异常，没有在这个数组中，那么会检查这个异常的类型，如果这个异常是一个运行时异常，那么就执行回滚，如果不是，那么就忽略







# 使用aspectj开启事务

对于大型的项目，我们不可能为每一个方法都添加一个注解，这样会变得特别笨拙，而且还会使修改非常麻烦，因为如果我们想要修改的话，就必须找到需要修改的注解所在的方法和类，这并不是一件容易的事情，所以，将他们分离开来，修改的时候，将是一件容易的事情，我们只需要在配置文件中，找到那个方法就可以了



## 步骤

1. 要使用的是aspectj框架，需要加入依赖

    ```xml
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-aspects</artifactId>
        <version>5.2.5.RELEASE</version>
    </dependency>
    ```

2. 声明事务管理器对象

    ```xml
    <bean id="transactionManager"
    class="org.springframework.jdbc.datasource.DataSourceTransactionManager" >
    <property name="dataSource" ref="myDataSource" />
    </bean>
    ```






声明方法需要的事务类型（配置方法的事务属性【隔离级别，传播行为，超时】）

这个是使用`<tx:advice>`标签

1. `<tx:advice>`

	- id:自定义名称，表示`<tx:advice>` 和 `</tx:advice>`之间的配置内容的
	- transaction-manager:事务管理器对象的id

2. `<tx:attributes>`

    tx:attributes：配置事务属性

在这个`tx:attributes`中，可以指明哪些方法使用事务，` <tx:method name="insertSaleDao">`配置方法

3. ` <tx:method name="insertSaleDao">`
    - ``name`就是方法名，不用加括号
    - `isolation`隔离级别
    - `rollback-for`哪些异常回滚，如果是运行时异常，一定回滚，多个使用逗号分隔开
    - `propagation`传播行为

方法名，可以使用通配符，但是如果想要使用通配符的话，就必须命名规范，有一定的格式

`<tx:method name="add*" propagation="REQUIRES_NEW" />`表示所有的以`add`开头的方法

`<tx:method name="*" propagation="SUPPORTS" read-only="true" />`所有方法（配置的包中，类中）



级别：

- ` <tx:method name="insertSaleDao">`级别最高，指定确切方法名
- `<tx:method name="add*" />`这个通配符的等级比上一个低
- ` <tx:method name="*" />`最低

```xml
<tx:advice id="myAdvice" transaction-manager="transactionManager" >
    <tx:attributes>
        <tx:method name="insertSaleDao" isolation="DEFAULT"
                   rollback-for="java.lang.NullPointerException,vin.cco.excep.MyException"
                   propagation="REQUIRED"/>
        <tx:method name="add*" propagation="REQUIRES_NEW" />
        <!--指定修改方法-->
        <tx:method name="modify*" />
        <!--删除方法-->
        <tx:method name="remove*" />
        <!--查询方法，query，search，find-->
        <tx:method name="*" propagation="SUPPORTS" read-only="true" />
    </tx:attributes>
</tx:advice>
```


​    

4. 配置aop：指定哪些哪类要创建代理。

    因为上一步，就指定了哪些方法添加事务，但是还不知道是哪些包，哪些类中的方法，这一步就是为这些方法绑定类

    ```xml
    <aop:config>
        <aop:pointcut id="myPoint" expression="execution(* *..service..*.*(..))"/>
        <aop:advisor advice-ref="myAdvice" pointcut-ref="myPoint" />
    </aop:config>
    ```

    使用`<aop:config>`标签，配置切入点表达式：指定哪些包中类，要使用事务

    - id:切入点表达式的名称，唯一值
    - expression：切入点表达式，指定哪些类要使用事务，aspectj会创建代理对象

    ```java
    com.bjpowernode.service
    com.crm.service
    com.service
    ```

    `<aop:advisor advice-ref="myAdvice" pointcut-ref="myPoint" />`用于配置增强器：关联adivce和pointcut

    - advice-ref:通知，上面tx:advice哪里的配置
    - pointcut-ref：切入点表达式的id

    如果有多个的话，就使用多个标签

    ```
    <aop:config>
        <aop:pointcut id="myPoint" expression="execution(* *..service..*.*(..))"/>
        <aop:advisor advice-ref="myAdvice" pointcut-ref="myPoint" />
    </aop:config>
    
    <aop:config>
        <aop:pointcut id="myPoint1" expression="execution(* *..service1..*.*(..))"/>
        <aop:advisor advice-ref="myAdvice1" pointcut-ref="myPoint1" />
    </aop:config>
    ```

    

5. 完整代码

    ```
    <!--声明事务管理器对象-->
    <bean id="transactionManager"
          class="org.springframework.jdbc.datasource.DataSourceTransactionManager" >
        <property name="dataSource" ref="myDataSource" />
    </bean>
    <!--<tx:annotation-driven transaction-manager="transactionManager" />-->
    
    <tx:advice id="myAdvice" transaction-manager="transactionManager" >
        <tx:attributes>
            <tx:method name="insertSaleDao" isolation="DEFAULT"
                       rollback-for="java.lang.NullPointerException,vin.cco.excep.MyException"
                       propagation="REQUIRED"/>
            <tx:method name="add*" propagation="REQUIRES_NEW" />
            <!--指定修改方法-->
            <tx:method name="modify*" />
            <!--删除方法-->
            <tx:method name="remove*" />
            <!--查询方法，query，search，find-->
            <tx:method name="*" propagation="SUPPORTS" read-only="true" />
    
        </tx:attributes>
    </tx:advice>
    
    <aop:config>
        <aop:pointcut id="myPoint" expression="execution(* *..service..*.*(..))"/>
        <aop:advisor advice-ref="myAdvice" pointcut-ref="myPoint" />
    </aop:config>
    ```

    



# spring配置web

因为web项目是运行在tomcat上的，所以我们使用spring创建对象的时候，是通过

```java
String path = "applicationContext.xml";
ApplicationContext context = new ClassPathXmlApplicationContext(path);

Student student = (Student) context.getBean("student");
```



但是如果将这个过程放在doPost()或者doGet()，那么用户每一次请求，都将执行spring配置文件，就会将所有的对象都创建一遍，时间，资源浪费了，所以，这个过程不能放在这两个方法中

![image-20210523125424627](http://ooszy.cco.vin/img/blog-note/image-20210523125424627.png?x-oss-process=style/pictureProcess1)

每次执行配置文件，其对象都是不同的

此时，可以考虑，将 Spring 容器的创建放在 Servlet 进行初始化时进行，即执行 init()方

法时执行。并且，Servlet 还是单例多线程的，即一个业务只有一个 Servlet 实例，所有执行

该业务的用户执行的都是这一个 Servlet 实例。这样，Spring 容器就具有了唯一性了。

但是，Servlet 是一个业务一个 Servlet 实例，即 LoginServlet 只有一个，但还会有

StudentServlet、TeacherServlet 等。每个业务都会有一个 Servlet，都会执行自己的 init()方法，

也就都会创建一个 Spring 容器了。这样一来，Spring 容器就又不唯一了。

## **使用** **Spring** **的监听器** ContextLoaderListener

举例：springweb-2 项目（在 spring-web 项目基础上修改）

对于 Web 应用来说，ServletContext 对象是唯一的，一个 Web 应用，只有一个

ServletContext 对象，该对象是在 Web 应用装载时初始化的。若将 Spring 容器的创建时机，

放在 ServletContext 初始化时，就可以保证 Spring 容器的创建只会执行一次，也就保证了

Spring 容器在整个应用中的唯一性。

当 Spring 容器创建好后，在整个应用的生命周期过程中，Spring 容器应该是随时可以被访问的。即，Spring 容器应具有全局性。而放入 ServletContext 对象的属性，就具有应用的

全局性。所以，将创建好的 Spring 容器，以属性的形式放入到 ServletContext 的空间中，就

保证了 Spring 容器的全局性。

上述的这些工作，已经被封装在了如下的 Spring 的 Jar 包的相关 API 中：

spring-web-5.2.5.RELEASE



## 步骤

1. 添加依赖

    ```xml
    <dependency> 
        <groupId>org.springframework</groupId> 
        <artifactId>spring-web</artifactId> 
        <version>5.2.5.RELEASE</version>
    </dependency>
    ```

2. **注册监听器** **ContextLoaderListener**

    若要在 ServletContext 初 始 化 时 创 建 Spring 容 器 ， 就 需 要 使 用 监 听 器 接 口

    ServletContextListener 对 ServletContext 进行监听。在 web.xml 中注册该监听器。

    ![image-20210523125642737](http://ooszy.cco.vin/img/blog-note/image-20210523125642737.png?x-oss-process=style/pictureProcess1)

    这个过程在`web.xml`文件中

    `执行这个过程很容易出错，因为默认web.xml`文件中，spring配置文件的路径在`WEB-INFO`下，也就是和`web.xml`文件在同一个目录下，并且spring的配置文件的名称也是固定的

    ![image-20210523130810316](http://ooszy.cco.vin/img/blog-note/image-20210523130810316.png?x-oss-process=style/pictureProcess1)

    如果spring配置文件的名字不是这个，或者是路径不是这个，就会报这个错误

    但是这个是可以进行修改的，也是在`web.xml`文件中

    ```xml
    <context-param>
        <!-- contextConfigLocation:表示配置文件的路径  -->
        <param-name>contextConfigLocation</param-name>
        <!--自定义配置文件的路径-->
        <param-value>classpath:spring.xml</param-value>
    </context-param>
    ```

    

    Spring 为该监听器接口定义了一个实现类 ContextLoaderListener，完成了两个很重要的

    工作：创建容器对象，并将容器对象放入到了 ServletContext 的空间中。

    打开 ContextLoaderListener 的源码。看到一共四个方法，两个是构造方法，一个初始化

    方法，一个销毁方法。

    ![image-20210523125730396](http://ooszy.cco.vin/img/blog-note/image-20210523125730396.png?x-oss-process=style/pictureProcess1)

    所以，在这四个方法中较重要的方法应该就是 contextInitialized()，context 初始化方法。

    ![image-20210523125749197](http://ooszy.cco.vin/img/blog-note/image-20210523125749197.png?x-oss-process=style/pictureProcess1)

    跟踪 initWebApplicationContext()方法，可以看到，在其中创建了容器对象。

    ![image-20210523125811479](http://ooszy.cco.vin/img/blog-note/image-20210523125811479.png?x-oss-process=style/pictureProcess1)

    并且，将创建好的容器对象放入到了 ServletContext 的空间中，key 为一个常量：

    WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE。

    ![image-20210523125829646](http://ooszy.cco.vin/img/blog-note/image-20210523125829646.png?x-oss-process=style/pictureProcess1)

    3. **指定** **Spring** **配置文件的位置**`<context-param>`

        ContextLoaderListener 在对 Spring 容器进行创建时，需要加载 Spring 配置文件。其默认

        的 Spring 配置文件位置与名称为：WEB-INF/applicationContext.xml。但，一般会将该配置文

        件放置于项目的 classpath 下，即 src 下，所以需要在 web.xml 中对 Spring 配置文件的位置及名称进行指定。

        ![image-20210523125959643](http://ooszy.cco.vin/img/blog-note/image-20210523125959643.png?x-oss-process=style/pictureProcess1)

        从监听器 ContextLoaderListener 的父类 ContextLoader 的源码中可以看到其要读取的配

        置文件位置参数名称 contextConfigLocation。

        ![image-20210523130016955](http://ooszy.cco.vin/img/blog-note/image-20210523130016955.png?x-oss-process=style/pictureProcess1)

        

        4. **获取** **Spring** **容器对象**

            在 Servlet 中获取容器对象的常用方式有两种：

            - **直接从** **ServletContext** **中获取** 

                从对监听器 ContextLoaderListener 的源码分析可知，容器对象在 ServletContext 的中存放的 key 为WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE。所以，可以直接通过 ServletContext 的 getAttribute()方法，按照指定的 key 将容器对象获取到。

                ![image-20210523130106196](http://ooszy.cco.vin/img/blog-note/image-20210523130106196.png?x-oss-process=style/pictureProcess1)

            - **通过** **WebApplicationContextUtils** **获取**

                工具类 WebApplicationContextUtils 有一个方法专门用于从 ServletContext 中获取 Spring容器对象：getRequiredWebApplicationContext(ServletContext sc)

                **调用** **Spring** **提供的方法获取容器对象**

                ![image-20210523130212960](http://ooszy.cco.vin/img/blog-note/image-20210523130212960.png?x-oss-process=style/pictureProcess1)

                查其源码，看其调用关系，就可看到其是从 ServletContext 中读取的属性值，即 Spring容器。

                ![image-20210523130237453](http://ooszy.cco.vin/img/blog-note/image-20210523130237453.png?x-oss-process=style/pictureProcess1)

                以上两种方式，无论使用哪种获取容器对象，刷新 success 页面后，可看到代码中使用的 Spring 容器均为同一个对象。

                ![image-20210523130301079](http://ooszy.cco.vin/img/blog-note/image-20210523130301079.png?x-oss-process=style/pictureProcess1)

                

                

