## Springboot
### Spring框架基本实现思路
1. 创建数据库。
2. 创建实体类Entity，与数据库中的表项一一对应。
实体层: 存放的是实体类，属性值与数据库值保持一致，实现 setter 和 getter 方法。
3. 创建接口，同于连接数据库与项目功能。
4. 编写mapper.xml文件（Mybatis）定义关联SQL语言与接口操作。
5. 创建Service类，为控制层提供服务。可以单独创建接口实现解耦。
6. 创建Controller类，连接页面请求和服务层，处理前端发来的请求。
7. 前端页面调用，使用jsp或html标准编写前端页面显示。

### Spring Data JPA
**Java Persistence API即Java持久化API，SpringData JPA，即Spring对JPA的封装**

#### 实体
```java
@Entity @MappedSuperclass 
@Table(name)
```
@Entity与@MappedSuperclass都是声明实体类的注解

@Entity 表明这是一个实体类，所声明的实体类里面定义的所有属性，均与对应的表关联

@MappedSuperclass表示所注解的类为一个父类，子类可以通过继承该父类来扩展自己的属性

@Table(name)，表明该实体类与数据库中的某个表关联，但是如果表名和实体类名相同的话，@Table可以省略

#### 主键
```java
@Id
@GeneratedValue(strategy, generator)
@SequenceGenerator(name, sequenceName)
```
@Id和@GeneratedValue常常组合起来，用于主键声明，并指定主键生成策略和生成器
（若不指定，JPA会根据所引入驱动依赖的数据库进行默认策略的设置）

> @GeneratedValue注解有两个属性,分别是strategy和generator.

> > generator属性：generator属性的值是一个字符串,默认为"",其声明了主键生成器的名称

> > strategy属性：
提供四种值:

> > > -AUTO主键由程序控制, 是默认选项 ,不设置就是这个
 
> > > -IDENTITY 主键由数据库生成, 采用数据库自增长, Oracle不支持这种方式
 
> > > -SEQUENCE 通过数据库的序列产生主键, MYSQL不支持
 
> > > -Table 提供特定的数据库产生主键, 该方式更有利于数据库的移植

@SequenceGenerator注解，表示主键会采用系列号的方式生成

#### 映射
```java
@Column(name,unique,nullable,length,insertable,updatable)
@JoinTable(name) @JoinColumn(name)
```
> @Column指定一个属性所映射的字段信息:

> > name
定义了被标注字段在数据库表中所对应字段的名称；

> > unique
表示该字段是否为唯一标识，默认为false。如果表中有一个字段需要唯一标识，则既可以使用该标记，也可以使用@Table标记中的@UniqueConstraint。

> > nullable
表示该字段是否可以为null值，默认为true。

> > insertable
表示在使用“INSERT”脚本插入数据时，是否需要插入该字段的值。

> > updatable
表示在使用“UPDATE”脚本插入数据时，是否需要更新该字段的值。insertable和updatable属性一般多用于只读的属性，例如主键和外键等。这些字段的值通常是自动生成的。

> > columnDefinition（大多数情况，几乎不用）
表示创建表时，该字段创建的SQL语句，一般用于通过Entity生成表定义时使用。（也就是说，如果DB中表已经建好，该属性没有必要使用。）

> > table
表示当映射多个表时，指定表的表中的字段。默认值为主表的表名。

> > length
表示字段的长度，当字段的类型为varchar时，该属性才有效，默认为255个字符。

> > precision和scale
precision属性和scale属性表示精度，当字段类型为double时，precision表示数值的总长度，scale表示小数点所占的位数。

若存在外键，可以使用@JoinTable(name) @JoinColumn(name)组合进行定义，属性与@Column类似

#### 关系
```java
@OneToOne @OneToMany @ManyToOne @ManyToMany
@OrderBy
```
前四个注解用在属性上，分别表示表与该属性的对应关系。
> @OneToOne: 一对一关系

> > targetEntity属性表示默认关联的实体类型，默认为当前标注的实体类；
> > cascade属性表示与此实体一对一关联的实体的联级样式类型。联级样式上当对实体进行操作时的策略。
> > > 说明：在定义关系时经常会涉及是否定义Cascade(级联处理)属性，担心造成负面影响.
       ·不定义,则对关系表不会产生任何影响
       ·CascadeType.PERSIST （级联新建）
       ·CascadeType.REMOVE （级联删除）
       ·CascadeType.REFRESH （级联刷新）
       ·CascadeType.MERGE （级联更新）中选择一个或多个。
       ·还有一个选择是使用CascadeType.ALL ，表示选择全部四项
> > fetch属性是该实体的加载方式，有两种：LAZY和EAGER。
> > optional属性表示关联的实体是否能够存在null值。默认为true，表示可以存在null值。如果为false，则要同时配合使用@JoinColumn标记。
> > mappedBy属性用于双向关联实体时，标注在不保存关系的实体中。


@OrderBy同样使用在属性上，表示@oneToMany以及@ManyToMany，即一对多或者多对多时，字段的排序方式.

