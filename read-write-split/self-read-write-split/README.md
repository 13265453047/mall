使用自定义的数据源路由

该项目需要引入如下依赖：springBoot、spring-aop、spring-jdbc、aspectjweaver等

# 一: 主从数据源的配置
我们需要配置主从数据库,主从数据库的配置一般都是写在配置文件里面。
通过@ConfigurationProperties注解,可以将配置文件(一般命名为:application.Properties)里的属性映射到具体的类属性上,
从而读取到写入的值注入到具体的代码配置中,
按照习惯大于约定的原则,主库我们都是注为master,从库注为slave,
本项目采用了阿里的druid数据库连接池,使用build建造者模式创建DataSource对象,
DataSource就是代码层面抽象出来的数据源,接着需要配置sessionFactory、sqlTemplate、事务管理器等
`DataSourceConfig `

# 二: 数据源路由的配置
路由在主从分离是非常重要的,基本是读写切换的核心。
Spring提供了AbstractRoutingDataSource 根据用户定义的规则选择当前的数据源，
作用就是在执行查询之前，设置使用的数据源,实现动态路由的数据源，
在每次数据库查询操作前执行它的抽象方法 determineCurrentLookupKey() 决定使用哪个数据源,
为了能有一个全局的数据源管理器,此时我们需要引入DataSourceContextHolder这个数据库上下文管理器,
可以理解为全局的变量,随时可取(见下面详细介绍),它的主要作用就是保存当前的数据源;
`DataSourceRouter`

# 三：数据源上下文环境
数据源上下文保存器,便于程序中可以随时取到当前的数据源,
它主要利用ThreadLocal封装,因为ThreadLocal是线程隔离的,天然具有线程安全的优势。
这里暴露了set和get、clear方法，
set方法用于赋值当前的数据源名,
get方法用于获取当前的数据源名称,
clear方法用于清除ThreadLocal中的内容,
因为ThreadLocal的key是weakReference是有内存泄漏风险的,通过remove方法防止内存泄漏；
`DataSourceContextHolder`

# 四：切换注解和Aop配置
首先我们来定义一个@DataSourceSwitcher注解,
拥有两个属性
①当前的数据源
②是否清除当前的数据源,并且只能放在方法上,(不可以放在类上,也没必要放在类上,因为我们在进行数据源切换的时候肯定是方法操作),
该注解的主要作用就是进行数据源的切换,在dao层进行操作数据库的时候,可以在方法上注明表示的是当前使用哪个数据源;
`DataSourceSwitcher`

# DataSourceAop配置
为了赋予@DataSourceSwitcher注解能够切换数据源的能力,
我们需要使用AOP,然后使用@Aroud注解找到方法上有@DataSourceSwitcher.class的方法,
然后取注解上配置的数据源的值,设置到DataSourceContextHolder中,就实现了将当前方法上配置的数据源注入到全局作用域当中;

# 五：用法以及测试
在配置好了读写分离之后,就可以在代码中使用了,
一般而言我们使用在service层或者dao层,在需要查询的方法上添加@DataSourceSwitcher(DataSourceEnum.SLAVE),它表示该方法下所有的操作都走的是读库;
在需要update或者insert的时候使用@DataSourceSwitcher(DataSourceEnum.MASTER)表示接下来将会走写库。
其实还有一种更为自动的写法,可以根据方法的前缀来配置AOP自动切换数据源,
比如update、insert、fresh等前缀的方法名一律自动设置为写库,
select、get、query等前缀的方法名一律配置为读库,
这是一种更为自动的配置写法。缺点就是方法名需要按照aop配置的严格来定义,否则就会失效

# 六：总结
读写分离的核心点就是数据路由,需要继承AbstractRoutingDataSource,复写它的determineCurrentLookupKey方法,
同时需要注意全局的上下文管理器DataSourceContextHolder,它是保存数据源上下文的主要类,也是路由方法中寻找的数据源取值,相当于数据源的中转站.
再结合jdbc-Template的底层去创建和管理数据源、事务等，我们的数据库读写分离就完美实现了。