# yaml使用

YAML 是 "YAML Ain't Markup Language"（YAML 不是一种标记语言）的递归缩写。在开发的这种语言时，YAML 的意思其实是："Yet Another Markup Language"（仍是一种标记语言）。

非常适合用来做以数据为中心的配置文件

在springboot中，推荐使用这种yaml文件作为配置文件

### 基本语法

- key: value；kv之间有空格
- 大小写敏感

- 使用缩进表示层级关系
- 缩进不允许使用tab，只允许空格

- 缩进的空格数不重要，只要相同层级的元素左对齐即可
- '#'表示注释

- 字符串无需加引号，如果要加，''与""表示字符串内容 会被 转义/不转义





### 数据类型

- 字面量：单个的、不可再分的值。date、boolean、string、number、null

    ```yaml
    k: v
    ```

- 对象：键值对的集合。map、hash、set、object 

    ```yaml
    行内写法：  k: {k1:v1,k2:v2,k3:v3}
    #或
    k: 
    	k1: v1
      k2: v2
      k3: v3
    ```

- 数组：一组按次序排列的值。array、list、queue

    ```yaml
    行内写法：  k: [v1,v2,v3]
    #或者
    k:
     - v1
     - v2
     - v3
    ```

    

使用下面的这个实例作为演示

```java
@Component
@ConfigurationProperties(
        prefix = "person"
)
public class Person {
    private String userName;
    private Boolean boss;
    private Date birth;
    private Integer age;
    private Pet pet;
    private String[] interests;
    private List<String> animal;
    private Map<String, Object> score;
    private Set<Double> salarys;
    private Map<String, List<Pet>> allPets;
}

public class Pet {
    private String name;
    private Double weight;
}
```



```yaml
person:
  userName: chuchen
  boss: true
  birth: 2019/12/9
  age: 22
  interests: [coding, com, video]
  animal:
    - 小八
    - 小猫
    - 小马
  score: {math: 80, english: 90, chinese: 80}
  salarys:
    - 324.65
    - 56.8
  pet:
    name: 无牙仔
    weight: 80.4
  allPets:
    allpet1:
      - name: allname
        weight: 34.34

```

> `Date`格式不能写成2021.7.2这种形式
>
> 像map这种对象的书写形式，可以有两种
>
> - score: {math: 80, english: 90, chinese: 80}
>
> - 获取这种
>
>     ```yaml
>     Map<String,String> map
>     
>     map: 
>       key1: v1
>       key2: v2
>     ```
>
> 同样数组也是有两种书写格式
>
> - interests: [coding, com, video]
>
> - 或者
>
>     ```yaml
>     List<String> animal
>     animal:
>         - 小八
>         - 小猫
>         - 小马
>     ```



其实yaml就是嵌套的形式



使用‘’和“”都可以代表字符串，但是他们却有区别，如果我们`‘chu\nchen’`和`“chu\nchen”`，那么在控制台输出这个结果，`“chu\nchen”`这种打印的时候，会换行，但是`‘chu\nchen’`不会换行，在控制台中测试，不要在浏览器中



## idea依赖

但是如果我们像上面这种做的话，那么我们在写这些配置属性的时候，idea没有提示，就需要加入一个依赖，此依赖可以在这里进行查看，并且如果不添加这个依赖的话，idea会一直提示

![image-20210702152558377](http://ooszy.cco.vin/img/blog-note/image-20210702152558377.png?x-oss-process=style/pictureProcess1)

https://docs.spring.io/spring-boot/docs/2.5.2/reference/htmlsingle/#configuration-metadata.format.repeated-items



```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
</dependency>
```



添加这个依赖，刷新之后，我们在yml中写的时候，就会出现提示，但是我加入之后，idea还是爆红，也不提示，不影响程序运行

但是这个依赖，并不是我们项目必须的，其只是方便我们开发，推荐添加下面的插件，这样进行打包的时候，不会将此依赖打包进去

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <excludes>
                    <exclude>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-configuration-processor</artifactId>
                    </exclude>
                </excludes>
            </configuration>
        </plugin>
    </plugins>
</build>
```

