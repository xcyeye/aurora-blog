# SSM整合开发

SSM 编程，即 SpringMVC + Spring + MyBatis 整合，是当前最为流行的 JavaEE 开发技术架构。其实 SSM 整合的实质，仅仅就是将 MyBatis整合入 Spring。因为 SpringMVC原本就是 Spring的一部分，不用专门整合。

SSM 整合的实现方式可分为两种：基于 XML 配置方式，基于注解方式。



# 基本步骤

1. 新建数据库
2. 创建maven项目
3. 添加依赖，spring，springmvc，mybatis，jsckson，mysql，servlet api，servlet jsp等等
4. 编写`web.xml`文件
    - 创建`DispatcherServlet`对象，用于创建springmvc容器
    - 创建spring监听器，`ContextLoaderListener`用于创建spring容器，管理对象
    - 设置字符集过滤器，因为post中文会出现乱码问题
5. 创建包，Controller包，service，dao，实体类包名创建好
6. 编写配置文件
    - spring配置
    - mybatis配置
    - springmvc配置
    - 数据库信息配置
7. 之后就是写代码



## 依赖

```
<dependencies>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.11</version>
        <scope>test</scope>
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
    <!--springmvc-->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>5.3.6</version>
    </dependency>
    <!--事务的-->
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
    <!--aspectj 依赖-->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-aspects</artifactId>
        <version>5.3.6</version>
    </dependency>
    <!--jackson-->
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
    <!--mybatis 和 spring 整合的-->
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis-spring</artifactId>
        <version>1.3.1</version>
    </dependency>
    <!--mybatis-->
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>3.5.1</version>
    </dependency>
    <!--mysql 驱动-->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>5.1.9</version>
    </dependency>
    <!--druid-->
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>druid</artifactId>
        <version>1.1.12</version>
    </dependency>
</dependencies>

<build>
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
    <plugins>
        <plugin>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.1</version>
            <configuration>
                <source>1.8</source>
                <target>1.8</target>
            </configuration>
        </plugin>
    </plugins>
</build>
```



## 配置文件

### 数据库信息

```properties
jdbc.url = jdbc:mysql://localhost:3306/mybatis
jdbc.username = root
jdbc.pwd = 123456
jdbc.maxActive = 20
```



### spring配置

1. 组件扫描器，因为要使用到注解，进行对象的创建，就必须要有组件扫描器，这里扫描的包就只需要是service就可以了，因为dao是由`MapperScannerConfigurer`对象自动扫描创建，视图层是由springmvc配置文件，进行扫描，所以就剩下service
2. 引入属性配置文件，因为和mybatis是整合的，所以在spring配置文件中，需要用到连接池等，就需要引入数据库连接信息配置文件
3. 配置druid连接池信息
4. 注册SQLSessionFactoryBean
5. 声明对象`MapperScannerConfigurer`，会自动扫描Mapper接口，自动代理对象，此对象可以自动创建dao对象，并且此dao对象的名字就是dao类的首字母小写作为名字

```
<!--引入数据库连接信息-->
<context:property-placeholder location="classpath:conf/jdbc.properties" />

<!--配置druid连接池信息-->
<bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource"
      init-method="init" destroy-method="close">
    <property name="url" value="${jdbc.url}" />
    <property name="username" value="${jdbc.username}" />
    <property name="password" value="${jdbc.pwd}" />
    <property name="maxActive" value="${jdbc.maxActive}" />
</bean>

<!--SqlSessionFactoryBean创建SqlSessionFactory-->
<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
    <property name="dataSource" ref="dataSource" />
    <property name="configLocation"  value="classpath:conf/mybatis.xml" />
</bean>

<!--声明mybatis的扫描器，创建dao对象-->
<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
    <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory" />
    <property name="basePackage" value="vin.cco.dao" />
</bean>

<!--创建对象，此对象会自动扫描Dao对象，自动创建dao类对象-->
<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
    <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory" />
    <property name="basePackage" value="vin.cco.dao" />
</bean>

<context:component-scan base-package="vin.cco.service" />
```



## springmvc配置

1. 组件扫描器，因为在springmvc中，会使用到注解，所以就必须有组件扫描器
2. 视图解析器，可以不用写很多访问路径的目录信息`WEB-INF/jsp`
3. 注册注解驱动，解决静态资源冲突问题，注册注解驱动之后，spring会使用`component-scan`组件扫描器，将我们使用的注解标记到工厂中，用来处理我们的请求，还有能够帮助我们处理返回值作用，比如，返回一个对象，可以将此对象转换为json格式，返回，就必须要用到这个注解驱动的作用

```
<!--声明组件扫描器-->
<context:component-scan base-package="vin.cco.controller" />

<!--视图解析器-->
<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" >
    <property name="prefix" value="/WEB-INF/jsp/" />
    <property name="suffix" value=".jsp" />
</bean>

<!--注解驱动-->
<mvc:annotation-driven />
```



### web.xml

在此文件中，需要配置

1. 注册中央调度器`DispatcherServlet`
2. 创建spring监听器，用于在服务器启动的时候，完成spring容器对象的初始化操作
3. 设置字符集过滤器，用于针对post请求，出现的中文乱码问题

```
<!--注册中央调度器-->
<servlet>
    <servlet-name>myweb</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <init-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:conf/dispatcherServlet.xml</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
    <servlet-name>myweb</servlet-name>
    <url-pattern>/</url-pattern>
</servlet-mapping>

<!--注册spring监听器，在服务器启动的时候，完成对象的初始化操作-->
<context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath:conf/applicationContext.xml</param-value>
</context-param>
<listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>

<!--注册字符集过滤器-->
<filter>
    <filter-name>characterEncodingFilter</filter-name>
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
    <filter-name>characterEncodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```





## mybatis配置

1. 配置别名，使用`<typeAliases>`，配置别名之后，就不用再DaoMapper映射文件中，写类的权限定名称了，`<package name="vin.cco.dao" />`这样配置，那么这个dao包下的类名就是别名
2. 指定sql语句的映射文件位置，`<package name="vin.cco.dao" />`一定要注意，映射文件的名字一定要和接口的名一样，包括名字的大小写

```
<!--别名-->
<typeAliases>
    <package name="vin.cco.domain"/>
</typeAliases>

<!--设置sql语句映射文件文职-->
<mappers>
    <package name="vin.cco.dao" />
</mappers>
```





# 代码

## dao

```java
public interface StudentDao {
    int insertStuDao(Student student);

    List<Student> selectStuDao();
}
```

sql映射文件

```xml
<mapper namespace="vin.cco.dao.StudentDao">
    <select id="selectStuDao" resultType="Student" >
        select name,id,age from student;
    </select>
    <insert id="insertStuDao" >
        insert into student (name,age) value (#{name},#{age})
    </insert>
</mapper>
```

> `select name,id,age from student`这里最好不要使用`select * from student;`

## service

```java
public interface StudentSer {
    int insertStuDao(Student student);

    List<Student> selectStuDao();
}

//impl
@Service
public class StudentSerImpl implements StudentSer {

    //使用自动注入的方法，也就是按类型注入
    @Autowired
    private StudentDao studentDao;

    @Override
    public int insertStuDao (Student student) {
        return studentDao.insertStuDao(student);
    }

    @Override
    public List<Student> selectStuDao () {
        return studentDao.selectStuDao();
    }
}
```



## controller

```java
@RequestMapping("/student")
@Controller
public class MyController {
    @Autowired
    private StudentSer ser;

    @RequestMapping("insertStu")
    public ModelAndView insertStu(Student student) {
        System.out.println("执行...");
        ModelAndView mv = new ModelAndView();
        int num = 0;
        try {
            num = ser.insertStuDao(student);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String tips = "学生 {"+ student.getName() +"} 插入成功";
        if (num == 0) {
            tips = "学生 {"+ student.getName() +"} 插入失败";
        }
        mv.addObject("tips",tips);
        mv.setViewName("show");
        return mv;
    }

    @RequestMapping("s")
    @ResponseBody
    public List<Student> selectAll() {
        System.out.println("执行...");
        List<Student> students = ser.selectStuDao();
        return students;
    }
}
```



测试通过



## 总结及注意

对于静态文件，设置mapper为`/`，但是在springmvc配置文件中，使用`<mvc:resources mapping="/image/**" location="/image/" />`解决静态文件问题，访问不到资源，重新启动idea便可以解决问题

对于查询操作，最好不要使用`select *`，不推荐这样使用，因为如果表的结构发生改变，可以会引起不必要的问题

方法返回的类型是`List<Student>`，在写sql映射文件的时候，`resultType="Student"`不需要考虑如何将Student封装成`List<Student>`，我们只需要写集合中的被封装的那个对象就可以了，其余的操作，框架会帮助我们做

对于sql映射文件，不要忘记`<mapper namespace="vin.cco.dao.StudentDao">`namespace值，不然就会出现异常信息，此值需要填写dao接口的类名全限定名称就可







