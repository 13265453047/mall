#JDBC封装工具（还不能成为ORM框架）（工具类）
1. Spring 中的JdbcTemplate (RowMapper)
2. Apache 的 DbUtils（ResultSetHandle）

#ORM框架
mybatis官网
> https://mybatis.org/mybatis-3/zh/
https://blog.csdn.net/m0_67645544/article/details/124077155

疑问：
一个SqlSession 对应一个连接(Transaction)，MapperProxy 这个业务接口的动态代理对象又持有一个 SqlSession 对象，那么总不可能一直用同一个连接吧？
接下来就是来打消这个疑问，MapperProxy 持有的 SqlSession 和 SqlSessionFactory 创建的这个有什么关系？
实际上答案就在 SqlSessionTemplate，SqlSessionTemplate 是 spring 对 mybatis SqlSessionFactory 的封装，同时，它还是 SqlSession 的代理。
SqlSessionTemplate 和 mybatis 提供的 SqlSessionManager
( SqlSessionFactory 的另一个实现类，也是DefaultSqlSessionFactory 的代理类，可以细想一下，业务是否共用同一个 sqlSession 还要在业务里面去传递，去控制是不是很麻烦) 
是一样的思路，
不过 spring 直接代理了 sqlSession：
还是熟悉的配方，就是 jdk 的动态代理，SqlSessionTemplate 在初始化时创建了一个 SqlSession 代理，
也内置了 ExecutorType，SqlSessionFactory 等 defaultSqlSession 初始化的必要组件
我们对 SqlSession 的操作都是经由这个代理来完成，代理的内部，实现了真正 SqlSession 的创建与销毁，回滚与提交等，
+ 1、getSqlSession() 创建 sqlSession
+ 2、执行 MapperProxy，也就是前面讲了一大堆的，MapperProxy 中，通过 MapperMethod 来调用 sqlSession 和我们生成好的 mappedStatement 操作 sql 语句。
+ 3、提交事务
+ 4、关闭事务

TransactionSynchronizationManager完成事务以及事务的传播（@Transactional 注解、我的事务传播等级）
它的内部持有了很多个元素为 Map<Object, Object> 的 ThreadLocal
也就是说，spring 的事务，是借助 
TransactionSynchronizationManager + SqlSessionHolder 对 sqlSession 的控制来实现的。

那么这样就很清晰了，如下总结，也如图：(img -> Spring托管Mybatis事务)

+ MapperProxy 内置的 sqlSession 是 sqlSessiontemplate
+ sqlSessiontemplate 通过持有 SqlSessionFactory 来创建真正的 SqlSession
+ TransactionSynchronizationManager + SqlSessionHolder 则扮演着 SqlSession 管理的角色














