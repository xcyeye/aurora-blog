# spring介绍



## 快速入门

构建一个简单的spring模块，一定需要以下几个jar包

![image-20210417193115919](http://ooszy.cco.vin/img/blog-note/image-20210417193115919.png?x-oss-process=style/pictureProcess1)

从这图中，也可以知道，在最底层的那几个，但是除了这四个以外，还需要使用一个日志输出的jar包



![image-20210417193214326](http://ooszy.cco.vin/img/blog-note/image-20210417193214326.png?x-oss-process=style/pictureProcess1)

1. 编写配置文件
2. 写一个测试类

- 配置文件

![image-20210417193338273](http://ooszy.cco.vin/img/blog-note/image-20210417193338273.png?x-oss-process=style/pictureProcess1)

可以直接点击这个springxml文件，直接创建spring的配置文件，可以减少加入约束的时间



```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="user" class="cn.vipblogs.chu.User"></bean>
</beans>
```

` <bean id="user" class="cn.vipblogs.chu.User"></bean>`在bean标签中需要id，class就是需要那个类的全限定名称





- 测试类

1. 加载配置文件
2. 使用配置文件的对象创建需要的对象

```java
@Test
public void test1() throws Exception {
    ApplicationContext context =
        new ClassPathXmlApplicationContext("bean1.xml");
    User user = context.getBean("user", User.class);
    user.add();

}
```

`ApplicationContext`除了`ClassPathXmlApplicationContext`实现类之外，我们还可以使用类`FileSystemXmlApplicationContext`加载spring的xml配置文件



`User user = context.getBean("user", User.class)`使用这个方法可以创建一个User对象，`getBean`的第一个参数就是`<bean id="user" class="cn.vipblogs.chu.User"></bean>`中的id号





# ioc

官方解释：

> **控制反转**（Inversion of Control，缩写为**IoC**），是面向对象编程中的一种设计原则，可以用来减低计算机代码之间的耦合度。其中最常见的方式叫做**依赖注入**（Dependency Injection，简称**DI**），还有一种方式叫“依赖查找”（Dependency Lookup）。通过控制反转，对象在被创建的时候，由一个调控系统内所有对象的外界实体将其所依赖的对象的引用传递给它。也可以说，依赖被注入到对象中



IOC也就是为了降低程序之间的耦合度，把对象的创建和对象之间的调用交给spring，入门的这个案例就是一个IOC例子

![image-20210417195918633](http://ooszy.cco.vin/img/blog-note/image-20210417195918633.png?x-oss-process=style/pictureProcess1)

这个User对象的创建并没有直接使用`User user = new User()`的方式，而是使用`User.class`进行创建，mybatis也是类似方式进行对象的创建



## IOC底层原理

IOC的底层原理就是`xml解析，工厂模式，反射`

### 工厂模式降低耦合度

工厂模式：其出现就是为了解决程序之间的耦合度问题



![image-20210417200354911](http://ooszy.cco.vin/img/blog-note/image-20210417200354911.png?x-oss-process=style/pictureProcess1)



如果使用原始方式`User user = new User()`创建对象的话，他们之间的耦合度太高，工厂模式就是，`UserFactory`工厂类进行获取User对象，然后在需要创建User对象的地方，使用这个工厂类进行获取User对象，这在很大一部分上能够进行解耦，但是也不能完全避免存在的耦合度





### 工厂模式+xml解析进一步降低耦合度



工厂模式能够降低程序之间的耦合度，但是还是存在一定的耦合度，使用xml解析，通过反射的方式进行对象的创建能够进一步降低程序之间的耦合度



![image-20210417201521361](http://ooszy.cco.vin/img/blog-note/image-20210417201521361.png?x-oss-process=style/pictureProcess1)

在工厂类中我们可以通过`Jsoup`对xml文件进行解析，从而得到User类的全限定名称，通过这个全限定名称，就可以通过`Class.forName()`方法获取字节码文件，进一步可以通过字节码文件创建User对象，就不用再使用`return new User()`这种存在耦合度的方式创建User对象，如果配置文件位置发生改变，我们只需要改变需要的路径就可以，其他的不会进行更改

使用这种`工厂模式+xml解析的方式，能够再次降低程序之间的耦合度`



### ioc

IOC思想基于IOC容器完成，IOC容器底层就是对象工厂

Spring提供IOC容器实现两种方式：（两个接口）

1. BeanFactory：IOC容器基本实现，是Spring内部的使用接口，不提供开发人员进行使用
    - 加载配置文件时候不会创建对象，在获取对象（使用）才去创建对象

2. ApplicationContext：BeanFactory接口的子接口，提供更多更强大的功能，一般由开发人员进行使用
    - 加载配置文件时候就会把在配置文件对象进行创建

3. ApplicationContext接口有实现类



我们在使用的时候，使用的是`ApplicationContext`，因为我们希望程序在服务器启动的时候，就进行资源文件的加载，在服务器启动时，就进行`<bean id="user" class="cn.vipblogs.chu.User"></bean>`配置文件中对象的创建，这样能够避免用户进行访问的时候，需要花费更多的时间



`BeanFactory`是在需要使用的时候，才会对配置文件中的对象进行创建，这种方式不推荐使用





### FileSystemXmlApplicationContext和ClassPathXmlApplicationContext比较



`FileSystemXmlApplicationContext`需要我们提供配置文件的在电脑盘中的路径位置，也就是这种`D://...`，只能使用系统路径，否则会出现文件找不到异常

`ClassPathXmlApplicationContext`使用的是类路径





# ioc操作Bean管理

## 什么是Bean管理

创建一个对象的时候，这个对象中可能会存在一些属性，像`name,age`等等，Bean管理就是对这些属性进行管理，比如User类中没有无参构造，如果使用入门案例的方式进行对象的创建



-  bean管理包含两个部分
    - spring创建对象
    - spring注入属性

- bean管理的两种方式实现
    - 基于xml配置文件方式进行实现
    - 基于注解方式进行实现



## 基于xml配置文件方式实现

- `<bean id="user" class="cn.vipblogs.chu.User"></bean>`这种方式进行对象的创建，默认使用的是无参构造，所以，如果在User类中，没有无参构造，那么就会出错



![image-20210418083517287](http://ooszy.cco.vin/img/blog-note/image-20210418083517287.png?x-oss-process=style/pictureProcess1)



### DI注入

DI注入，其实就是依赖注入，使用配置文件进行注入，注入属性



`DI注入的方式，是通过类中的set方法进行注入的，所以，在使用这个之前，一定要保证此属性在类中，存在set方法，否则会出错`



### 使用set方法注入属性，一定要有set方法

在创建类的时候，可以生成一个set方法，在创建对象的时候，调用这个set方法进行属性的注入



```java
private String name;

public void setName (String name) {
    this.name = name;
}

//test
@Test
public void test2() throws Exception {
    ApplicationContext context =
        new ClassPathXmlApplicationContext("bean1.xml");
    User user = context.getBean("user", User.class);
    user.setName("chuchen");
    user.add();
}
```
这种方式进行使用set注入的方式，通过对象调用set方法进行属性的注入

### spring配置文件属性注入，一定要有set方法



不使用set方法，也可以使用spring的配置文件进行属性的注入



```xml
<bean id="user" class="cn.vipblogs.chu.User">
    <property name="name" value="初尘"></property>
</bean>
```

`property`标签就是用于对User对象中属性进行注入

`property`的name值就是类中属性的名称，value就是对这个属性赋什么值



调用测试

```java
@Test
public void test3() throws Exception {
    ApplicationContext context =
        new ClassPathXmlApplicationContext("bean1.xml");
    User user = context.getBean("user", User.class);
    user.add();
}
```



成功得到name的值



### 配置文件使用有参构造注入

如果类中存在有参构造，也可以使用有参构造完成属性的注入



类结构

```java
private String name;

public User () {
}

public User (String name) {
    this.name = name;
}

public void setName (String name) {
    this.name = name;
}
```



```xml
<bean id="user" class="cn.vipblogs.chu.User">
    <constructor-arg name="name" value="chu"></constructor-arg>
</bean>
```



测试

```java
@Test
public void test4() throws Exception {
    ApplicationContext context =
        new ClassPathXmlApplicationContext("bean1.xml");
    User user = context.getBean("user", User.class);
    user.add();
}
```

成功获取到name属性的值为chu



`<constructor-arg>`标签就是用于写类中有参构造的属性，name就是属性名称，value就是属性值

此标签中，还有`index=""`也可以获取到属性名，但是这个是通过下标进行获取的，`public User (String name,int age) {}`，那么`index="0"`就取到的是name，`index="1"`取到的就是age



### 使用p名称空间注入，一定要有set方法



1. 修改配置文件约束
2. 使用p名称



```
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
```

在配置文件的头部，添加上`xmlns:p="http://www.springframework.org/schema/p"`此约束

```xml
<bean id="user" class="cn.vipblogs.chu.User" p:name="chu初尘"></bean>
```

在bean标签中，我们就可以使用这个p名称了，`p:属性名="属性值"`

但是这种方式不是很推荐使用



```java
@Test
public void test4() throws Exception {
    ApplicationContext context =
        new ClassPathXmlApplicationContext("bean1.xml");
    User user = context.getBean("user", User.class);
    user.add();
}
```



### 配置文件注入特殊属性

`一定要保证存在set方法`

类结构

```java
private String habit;

public void setHabit (String habit) {
    this.habit = habit;
}

public String getHabit () {
    return habit;
}

@Override
public String toString () {
    return "Habit{" + "habit='" + habit + '\'' + '}';
}
```

如果想要使用将habit的属性值设置为`null`或者是`<<小说>>`这种形式如何做？

- 赋值null

```xml
<bean id="habit" class="cn.vipblogs.domain.Habit">
    <property name="habit">
        <null/>
    </property>
</bean>
```

通过这种方式`<null/>`可以对habit这个属性赋值为null



但是通过这种方式也是可以的，同样可以设置值为null

```xml
<bean id="habit" class="cn.vipblogs.domain.Habit">
    <property name="habit">
        <value>null</value>
    </property>
</bean>
```

输出

```java
Habit{habit='null'}
```





- 赋值<<小说>>

```xml
<property name="habit">
    <!--<value><<小说></value>-->
    <value>&lt;&lt;小说&gt;&gt;</value>
</property>
```

`&lt`是小于的转义字符，`&gt;`是大于的转义字符

一定不能在xml文件中，使用`\`作为转义字符



另一种方式

```xml
<property name="habit">
    <value><![CDATA[<<小说>>]]></value>
</property>
```





### 注入外部bean

什么叫做外部bean？

```java
public class UserService {
    private UserDao dao;

    public void setDao (UserDao dao) {
        this.dao = dao;
    }

    public void add() {
        System.out.println("service is doing.........");
        dao.update();
    }
}
```

```java
public interface UserDao {
    void update();
}
```

```java
public class UserDaoImpl implements UserDao {
    @Override
    public void update () {
        System.out.println("dao is doing ..........");
    }
}
```

我们一般在使用的时候，都会在Service类中，使用Dao对象，调用dao中的方法完成数据的操作

在service中，存在`private UserDao dao`属性，那么我们在配置文件中，如果将Dao对象传递给Service对象中的`private UserDao dao`属性呢，这个就要用到外部注入的方式



1. 在配置文件中，创建`UserService`对象和`UserDao`对象
2. 在`<property name="dao" ref="userDao"></property>`标签中，使用`ref`引用dao对象



```
<!--创建UserService对象和UserDao对象-->

<!--UserService对象-->
<bean id="userService" class="cn.vipblogs.service.UserService">
    <property name="dao" ref="userDao"></property>
</bean>

<!--UserDao对象-->
<bean id="userDao" class="cn.vipblogs.dao.UserDaoImpl"></bean>
```



> `注意，在创建Dao对象的时候，class值不能使用Dao接口的全限定名称，因为接口不能创建对象`
>
> `<bean id="userDao" class="cn.vipblogs.dao.UserDao"></bean>`错误方式



![image-20210418105150738](http://ooszy.cco.vin/img/blog-note/image-20210418105150738.png?x-oss-process=style/pictureProcess1)



这个就是Bean外部注入



### 内部Bean注入

部门和员工类结构

```java
public class Dep {
    private String depName;

    public void setDepName (String depName) {
        this.depName = depName;
    }

    @Override
    public String toString () {
        return "Bean{" + "depName='" + depName + '\'' + '}';
    }
}
```



```java
public class Emp {
    /** 员工姓名 */
    private String eName;

    /** 员工性别 */
    private String gender;

    /** 员工部门 */
    private Dep dep;

    public void seteName (String eName) {
        this.eName = eName;
    }

    public void setGender (String gender) {
        this.gender = gender;
    }

    public void setDep (Dep dep) {
        this.dep = dep;
    }

    @Override
    public String toString () {
        return "Emp{" + "eName='" + eName + '\'' + ", gender='" + gender + '\'' + ", dep=" + dep + '}';
    }
}
```





在员工类中，存在一个部门对象，那么我们这个时候，对员工对象中的部门属性注入的方式，除了外部注入的方式外，我们还可以使用内部注入的方式进行 



`外部注入的方式也可以叫做级联注入`，通过使用`<property name="dao" ref="userDao"></property>`ref进行



- 内部注入

```xml
<!--员工对象-->
<bean id="emp" class="cn.vipblogs.bean.Emp">
    <property name="eName" value="chuchen"></property>
    <property name="gender" value="男"></property>
    <property name="dep">
        <bean id="nDept" class="cn.vipblogs.bean.Dep">
            <property name="depName" value="研发部"></property>
        </bean>
    </property>
</bean>
```



在配置文件中，当我们创建Emp对象的时候，这个对象中有一个属性部门对象，可以使用

```xml
<property name="dep">
    <bean id="nDept" class="cn.vipblogs.bean.Dep">
        <property name="depName" value="研发部"></property>
    </bean>
</property>
```

这种形式，在`<bean id="emp" class="cn.vipblogs.bean.Emp">`对象的内部再次创建一个对象，可以进行嵌套使用

所以现在在实例化`Emp`对象的时候，也实例化了`Dep`对象，并且`<property name="depName" value="研发部"></property>`给这个部门对象增加了属性





- 级联方式

级联方式就是通过`<property name="dep" ref="dep"></property>`引用当前`<bean>`标签以外的其他`<bean>`标签创建的`Dep对象`



使用级联方式的时候，如果此emp对象中的Dep对象使用的是外部，并且此外部`dep`对象并没有设置`depName`属性的是，那么可以使用下面的方式

```
<bean id="dep" class="cn.vipblogs.bean.Dep"></bean>

<!--员工对象-->
<bean  id="emp" class="cn.vipblogs.bean.Emp">
    <property name="eName" value="chuchen"></property>
    <property name="gender" value="男"></property>
    <!--使用级联方式-->
    <property name="dep" ref="dep"></property>
    <property name="dep.depName" value="ccc"></property>
</bean>
```



可以使用`<property name="dep.depName" value="ccc">`对dep对象中的`depName`属性进行设置，但是必须要保证，在`Emp`类中，存在`Dep getDep ()`方法，否则会保存



这两个是必不可少的

```
<property name="dep" ref="dep"></property>
<property name="dep.depName" value="ccc"></property>
```

因为必须要已经有这个对象，然后才能通过get方法拿到这个对象，进而通过对象对属性进行设置







## 注入集合或者是数组



如果对象中的属性有

```java
private String[] course;
private List<String> habit;
private Map<String,Object> blog;
```



那么我们就需要注入集合或者是数组



使用步骤

1. 定义属性集合或者是数组
2. 生成set方法
3. 编写配置文件





- java类

```java
public class Student {
    private String[] course;

    private List<String> habit;
    private Map<String,Object> blog;

    public void setCourse (String[] course) {
        this.course = course;
    }

    public void setHabit (List<String> habit) {
        this.habit = habit;
    }

    public void setBlog (Map<String, Object> blog) {
        this.blog = blog;
    }

    @Override
    public String toString () {
        return "Student{" + "course=" + Arrays.toString(course) + ", habit=" + habit + ", blog=" + blog + '}';
    }
}
```



- xml文件

```xml
<bean id="student" class="cn.vipblogs.col.Student">
    <property name="course">
        <array>
            <value>语文</value>
            <value>英语</value>
            <value>数学</value>
        </array>
    </property>
    <property name="habit">
        <list>
            <value>coding</value>
            <value>learn</value>
            <value>watch TV</value>
        </list>
    </property>
    <property name="blog">
        <map>
            <entry key="blog1" value="博客1"></entry>
            <entry key="blog2" value="博客2"></entry>
            <entry key="blog3" value="博客3"></entry>
        </map>
    </property>
</bean>
```



- 如果属性是一个数组类型，那么使用`<array>`数组标签，此标签中，可以存放多个`<value>`标签
- 是List集合，可以使用`<list>`集合标签
- 是Map集合，可以使用`</map>`map集合标签，因为map集合是以key和value的方式进行存储的，所以需要使用`<entry key="blog1" value="博客1"></entry>`



### 把集合注入部分提取出来

如果想要提取出来的话，就需要对配置文件进行更改



```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:util="http://www.springframework.org/schema/util"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd">
```

> 加上的部分
>
> `xmlns:util="http://www.springframework.org/schema/util"`
>
> xsi:schemaLocation中: `http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd`





使用

```
<util:list id="cList">
    <value>数据库</value>
    <value>语文</value>
    <value>数学</value>
</util:list>
<bean id="teacher" class="cn.vipblogs.col.Teacher">
    <property name="courses" ref="cList"></property>
</bean>
```





# byName和byType自动注入

## byName



这种方式只使用于，对象中的属性是一个对象的情况

对于字段是String，int等类型的，不使用



```java
//school类
private String sName;
private String sAddress;
```



```java
//student类

private String name;
private String age;
private School school;
```



```
<bean id="student" class="cn.vipblogs.domain.Student" autowire="byName">
    <property name="name" value="初尘"></property>
    <property name="age" value="21"></property>
</bean>

<bean id="school" class="cn.vipblogs.domain.School">
    <property name="sName" value="保山学院"></property>
    <property name="sAddress" value="云南"></property>
</bean>
```



1. 首先在bean标签中，写上`<bean id="student" class="cn.vipblogs.domain.Student" autowire="byName">` autowire="byName"，
2. 把基本数据类型的字段，设置属性值
3. 创建一个school对象，并将其id设置为student对象中的类型为`Student`的字段名



使用这种方式，一定要保证，`<bean id="school" class="cn.vipblogs.domain.School">`的id名和`private School school;`字段名是相同的，spring在执行的时候，首先看见`autowire="byName"`，在创建Student对象的时候，对该对象中，是引用类型的字段赋值的时候，就会在这个配置文件中进行查找，看时候有一个`<bean>`的id名和此对象中的某个引用类型的字段名相同，如果有相同的，那么就会将引用这个`<bean>`作为自己这个属性的值

![image-20210418154327772](http://ooszy.cco.vin/img/blog-note/image-20210418154327772.png?x-oss-process=style/pictureProcess1)



这种自动注入的方式，在很大程度上能够节省我们操作的时间，但是这种方式只是使用于，字段是引用类型的变量



### byType

这种方式是按`类型`进行注入

java类中引用类型的数据类型和spring容器中（配置文件）`<bean>`的class属性是同源关系的，这样的bean能够赋值给引用类型

同源就是一类的意思：

1. java类中引用类型的数据类型和bean的class的值是一样的。
2. java类中引用类型的数据类型和bean的class的值父子类关系的。
3. java类中引用类型的数据类型和bean的class的值接口和实现类关系的
    语法：
    `<bean id="xx" class="yyy" autowire="byType">`
    简单类型属性赋值
    `</bean>`



类结构

```java
//School

public class School {
    private String sName;
    private String sAddress;

    public void setsName (String sName) {
        this.sName = sName;
    }

    public void setsAddress (String sAddress) {
        this.sAddress = sAddress;
    }

    @Override
    public String toString () {
        return "School{" + "sName='" + sName + '\'' + ", sAddress='" + sAddress + '\'' + '}';
    }
}
```



小学类

```java
//继承于School
public class priSchool extends School {
}
```





xml配置文件


```
<bean id="student" class="cn.vipblogs.domain.Student" autowire="byType">
    <property name="name" value="初尘chuchen"></property>
    <property name="age" value="22"></property>
</bean>

<bean id="myschool" class="cn.vipblogs.domain.School">
    <property name="sName" value="保山学院"></property>
    <property name="sAddress" value="云南"></property>
</bean>
```

因为`private School school`的类型和`<bean id="myschool" class="cn.vipblogs.domain.School">`的类型是同一个，他们是同源的，又因为，使用了`autowire="byType"`自动注入的方式，所以在创建`cn.vipblogs.domain.Student`对象的时候，就会从这个配置文件中寻找bean，寻找和`private School school`同源的bean，就会将寻找到的这个对象的引用赋值给这个属性



但是，需要注意的时候，如果使用这种`byType`自动注入的方式，那么在这个配置文件中，就只能存在一个和其属性同源的bean，注意，是一个，否则就会报错



![image-20210418172933283](http://ooszy.cco.vin/img/blog-note/image-20210418172933283.png?x-oss-process=style/pictureProcess1)

只能存在一个

对于接口，和接口的实现，子类和父类，他们都是同源的





# 使用多配置文件

对于大型的项目来说，如果一个项目全部使用一个配置文件，那么这个配置文件一定是非常大的，那么可能就会造成加载缓慢等等情况，所以，可以使用多个配置文件解决



- 分类

    - 按模块进行拆分

        如果一个项目中，存在多个模块，那么就可以将每一个模块的配置放在一个模块中，比如学生信息模块，学生成绩模块

    - 按功能进行划分

        做数据操作的放在一个模块，做dao的放在一起，做service的放在一起 ，做事务相关的又放在一个里面



学校模块

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--此配置文件只是用于学校模块-->
    <bean id="school" class="cn.vipblogs.domain.School">
        <property name="sName" value="保山大学"></property>
        <property name="sAddress" value="不知道"></property>
    </bean>
</beans>
```



学生模块

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

   <!-- 这个配置文件就只是用于学生模块-->
    <bean id="student" class="cn.vipblogs.domain.Student" autowire="byType">
        <property name="name" value="cccchen"></property>
        <property name="age" value="2121"></property>
    </bean>
</beans>
```



总的配置文件

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <import resource="classpath:student/spring-school.xml"></import>
    <import resource="classpath:student/spring-student.xml"></import>
</beans>
```



在这个总的配置文件中，使用`<import resource="classpath:"></import>`标签将需要的配置文件进行导入，`classpath:`使用的是类路径

那么我们就可以在程序中，使用这个总的配置文件

```java
@Test
public void test3() throws Exception {
    ApplicationContext context =
        new ClassPathXmlApplicationContext("student/total.xml");
    Student student = context.getBean("student", Student.class);
    System.out.println(student);
}
```





还可以使用通配符，导入多个，但是如果使用通配符的话，就需要确保他们在一个包中



```xml
<import resource="classpath:student/spring-*.xml"></import>
```

![image-20210418183646819](http://ooszy.cco.vin/img/blog-note/image-20210418183646819.png?x-oss-process=style/pictureProcess1)

`student/spring-*.xml`这个的意思就是，导入student路劲下，所有以`spring-xxx.xml`格式的xml文件







