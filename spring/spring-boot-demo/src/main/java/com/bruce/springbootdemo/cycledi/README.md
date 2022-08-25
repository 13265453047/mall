# Spring 循环依赖注入
https://www.jb51.net/article/236917.htm

# 一. 什么是循环依赖？

## Spring中循环依赖场景有： 

（1）构造器的循环依赖 

（2）field属性的循环依赖

其中，构造器的循环依赖问题无法解决，只能拋出BeanCurrentlyInCreationException异常，在解决属性循环依赖时，spring采用的是提前暴露对象的方法。

# 二. 怎么检测是否存在循环依赖

检测循环依赖相对比较容易，Bean在创建的时候可以给该Bean打标，如果递归调用回来发现正在创建中的话，即说明了循环依赖了。

# 三、三种循环依赖
1. 构造器的循环依赖。【这个Spring解决不了】

　　Spring容器会将每一个正在创建的Bean 标识符放在一个“当前创建Bean池”中，Bean标识符在创建过程中将一直保持在这个池中，因此如果在创建Bean过程中发现自己已经在“当前创建Bean池”里时将抛出BeanCurrentlyInCreationException异常表示循环依赖；而对于创建完毕的Bean将从“当前创建Bean池”中清除掉。

　　Spring容器先创建单例A，A依赖B，然后将A放在“当前创建Bean池”中，此时创建B,B依赖C ,然后将B放在“当前创建Bean池”中,此时创建C，C又依赖A， 但是，此时A已经在池中，所以会报错，因为在池中的Bean都是未初始化完的，所以会依赖错误 ，（初始化完的Bean会从池中移除）

2. setter方式单例，默认方式
    
    Spring是先将Bean对象实例化【依赖无参构造函数】--->再设置对象属性的
    Spring先是用构造实例化Bean对象 ，此时Spring会将这个实例化结束的对象放到一个Map中，并且Spring提供了获取这个未设置属性的实例化对象引用的方法。   结合我们的实例来看，，当Spring实例化了StudentA、StudentB、StudentC后，紧接着会去设置对象的属性，此时StudentA依赖StudentB，就会去Map中取出存在里面的单例StudentB对象，以此类推，不会出来循环的问题
    
3. setter方式原型，prototype

    scope="prototype" 意思是 每次请求都会创建一个实例对象。两者的区别是：有状态的bean都使用Prototype作用域，无状态的一般都使用singleton单例作用域。

# 四、Spring怎么解决循环依赖

Spring的循环依赖的理论依据基于Java的引用传递，当获得对象的引用时，对象的属性是可以延后设置的。（但是构造器必须是在获取引用之前）

Spring的单例对象的初始化主要分为三步： 
（1）createBeanInstance：实例化，其实也就是调用对象的构造方法实例化对象
（2）populateBean：填充属性，这一步主要是多bean的依赖属性进行填充
（3）initializeBean：调用spring xml中的init 方法
从上面单例bean的初始化可以知道：循环依赖主要发生在第一、二步，也就是构造器循环依赖和field循环依赖。那么我们要解决循环引用也应该从初始化过程着手，对于单例来说，在Spring容器整个生命周期内，有且只有一个对象，所以很容易想到这个对象应该存在Cache中，Spring为了解决单例的循环依赖问题，使用了三级缓存。

+ 一级缓存
singletonObjects：
单例对象的cache；
用于保存实例化、注入、初始化完成的bean实例；
主要存放的是单例对象，属于第一级缓存，可以说成品对象；
用于存放完全初始化好的 bean，从该缓存中取出的 bean 可以直接使用
```java
// Cache of singleton objects: bean name to bean instance. 
 
private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);
```
+ 二级缓存
earlySingletonObjects：
提前暴光的单例对象的Cache；
用于保存实例化完成的bean实例；
作用是防止重复创建代理获取提前引用，并且防止循环依赖，可以说半成品对象；
提前曝光的单例对象的cache，存放原始的 bean 对象（尚未填充属性），用于解决循环依赖
```java
/** Cache of early singleton objects: bean name to bean instance. */
 
private final Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>(16);
```
+ 三级缓存
singletonFactories：
单例对象工厂的cache；
用于保存bean创建工厂，以便于后面扩展有机会创建代理对象。（前提是执行了构造器）；
属于单例工厂对象；
单例对象工厂的cache，存放 bean 工厂对象，用于解决循环依赖
```java
/** Cache of singleton factories: bean name to ObjectFactory. */
 
private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);
```
获取Bean简单流程：
5212e7097e104d0999fe9d41800c78fb.png
为什么先展示这个流程呢，因为在你去查看 Spring 解决循环依赖的整个源码的时候，你会发现会多次出现这个流程。
26e559be8d5e4553a536ceb8ed382b77.png

在创建bean的时候，首先想到的是从cache中获取这个单例的bean，这个缓存就是singletonObjects。
如果获取不到，并且对象正在创建中，就再从二级缓存earlySingletonObjects中获取。
如果还是获取不到且允许singletonFactories通过getObject()获取，
就从三级缓存singletonFactory.getObject()(三级缓存)获取，
如果获取到了则：从singletonFactories中移除，并放入earlySingletonObjects中。
其实也就是从三级缓存移动到了二级缓存。

从上面三级缓存的分析，我们可以知道，
Spring解决循环依赖的诀窍就在于singletonFactories这个三级cache。
这个cache的类型是ObjectFactory。
这里就是解决循环依赖的关键，发生在createBeanInstance之后，也就是说单例对象此时已经被创建出来(调用了构造器)。
这个对象已经被生产出来了，虽然还不完美（还没有进行初始化的第二步和第三步），
但是已经能被人认出来了（根据对象引用能定位到堆中的对象），所以Spring此时将这个对象提前曝光出来让大家认识，让大家使用。

这样做有什么好处呢？让我们来分析一下“A的某个field或者setter依赖了B的实例对象，同时B的某个field或者setter依赖了A的实例对象”这种循环依赖的情况。
A首先完成了初始化的第一步，并且将自己提前曝光到singletonFactories中，
此时进行初始化的第二步，发现自己依赖对象B，此时就尝试去get(B)，
发现B还没有被create，所以走create流程，B在初始化第一步的时候发现自己依赖了对象A，
于是尝试get(A)，
尝试一级缓存singletonObjects(肯定没有，因为A还没初始化完全)，
尝试二级缓存earlySingletonObjects(也没有)，
尝试三级缓存singletonFactories，由于A通过ObjectFactory将自己提前曝光了，
所以B能够通过ObjectFactory.getObject拿到A对象(虽然A还没有初始化完全，但是总比没有好呀)，
B拿到A对象后顺利完成了初始化阶段1、2、3，完全初始化之后将自己放入到一级缓存singletonObjects中。
此时返回A中，A此时能拿到B的对象顺利完成自己的初始化阶段2、3，最终A也完成了初始化，进去了一级缓存singletonObjects中，
而且更加幸运的是，由于B拿到了A的对象引用，所以B现在hold住的A对象完成了初始化。

知道了这个原理时候，肯定就知道为啥Spring不能解决“A的构造方法中依赖了B的实例对象，
同时B的构造方法中依赖了A的实例对象”这类问题了！
因为加入singletonFactories三级缓存的前提是执行了构造器，所以构造器的循环依赖没法解决。



---
1.先从一级缓存singletonObjects中去获取。（如果获取到就直接return）
2.如果获取不到或者对象正在创建中（isSingletonCurrentlyInCreation()），那就再从二级缓存earlySingletonObjects中获取。（如果获取到就直接return）
3.如果还是获取不到，且允许singletonFactories（allowEarlyReference=true）通过getObject()获取。就从三级缓存singletonFactory.getObject()获取。（如果获取到了就从singletonFactories中移除，并且放进earlySingletonObjects。其实也就是从三级缓存移动（是剪切、不是复制哦~）到了二级缓存）

> 加入singletonFactories三级缓存的前提是执行了构造器，所以构造器的循环依赖没法解决
> getSingleton()从缓存里获取单例对象步骤分析可知，Spring解决循环依赖的诀窍：就在于singletonFactories这个三级缓存。这个Cache里面都是ObjectFactory，它是解决问题的关键。
```java
// 它可以将创建对象的步骤封装到ObjectFactory中 交给自定义的Scope来选择是否需要创建对象来灵活的实现scope。  具体参见Scope接口
@FunctionalInterface
public interface ObjectFactory<T> {
	T getObject() throws BeansException;
}
```
> 经过ObjectFactory.getObject()后，此时放进了二级缓存earlySingletonObjects内。这个时候对象已经实例化了，虽然还不完美，但是对象的引用已经可以被其它引用了。

此处说一下二级缓存earlySingletonObjects它里面的数据什么时候添加什么移除？？?

添加：向里面添加数据只有一个地方，就是上面说的getSingleton()里从三级缓存里挪过来

移除：addSingleton、addSingletonFactory、removeSingleton从语义中可以看出添加单例、添加单例工厂ObjectFactory的时候都会删除二级缓存里面对应的缓存值，是互斥的。

---



# 五、总结

不要使用基于构造函数的依赖注入，可以通过以下方式解决：

　　1.在字段上使用@Autowired注解，让Spring决定在合适的时机注入

　　2.用基于setter方法的依赖注入。

在实例化过程中，将处于半成品的对象地址全部放在缓存中，提前暴露对象，在后续的过程中，再次对提前暴露的对象进行赋值，然后将赋值完成的对象，也就是成品对象放在一级缓存中，删除二级和三级缓存。
如果不要二级缓存的话，一级缓存会存在半成品和成品的对象，获取的时候，可能会获取到半成品的对象，无法使用。
如果不要三级缓存的话，未使用AOP的情况下，只需要一级和二级缓存即可解决Spring循环依赖；但是如果使用了AOP进行增强功能的话，必须使用三级缓存，因为在获取三级缓存过程中，会用代理对象替换非代理对象，如果没有三级缓存，那么就无法得到代理对象
三级缓存时为了解决AOP代理过程中产生的循环依赖问题。



#########################################################

# 为什么要用三级缓存，两级缓存不可以吗？
https://blog.csdn.net/lzb348110175/article/details/125086262
https://www.jb51.net/article/206470.htm

1. 在构造Bean对象之后，将对象提前曝光到缓存中，这时候曝光的对象仅仅是构造完成，还没注入属性和初始化。
2. 提前曝光的对象被放入Map<String, ObjectFactory<?>> singletonFactories缓存中，这里并不是直接将Bean放入缓存，而是包装成ObjectFactory对象再放入。

3. 为什么要包装一层ObjectFactory对象？

如果创建的Bean有对应的代理，那其他对象注入时，注入的应该是对应的代理对象；但是Spring无法提前知道这个对象是不是有循环依赖的情况，而正常情况下（没有循环依赖情况），Spring都是在创建好完成品Bean之后才创建对应的代理。这时候Spring有两个选择：

+ 不管有没有循环依赖，都提前创建好代理对象，并将代理对象放入缓存，出现循环依赖时，其他对象直接就可以取到代理对象并注入。（仅一级缓存就可以解决）
+ 不提前创建好代理对象，在出现循环依赖被其他对象注入时，才实时生成代理对象。这样在没有循环依赖的情况下，Bean就可以按着Spring设计原则的步骤来创建。

Spring选择了第二种方式，那怎么做到提前曝光对象而又不生成代理呢？
Spring就是在对象外面包一层ObjectFactory，提前曝光的是ObjectFactory对象，在被注入时才在ObjectFactory.getObject方式内实时生成代理对象，并将生成好的代理对象放入到第二级缓存Map<String, Object> earlySingletonObjects。
为了防止对象在后面的初始化（init）时重复代理，在创建代理时，earlyProxyReferences缓存会记录已代理的对象。
删除三级缓存，放入二级缓存（也是为了多次被循环依赖注入使用，只创建一次，同一个对象）

4. 注入属性和初始化

提前曝光之后：
通过populateBean方法注入属性，在注入其他Bean对象时，会先去缓存里取，如果缓存没有，就创建该对象并注入。
通过initializeBean方法初始化对象，包含创建代理。

5. 放入已完成创建的单例缓存
最终通过addSingleton方法将最终生成的可用的Bean放入到单例缓存里。

org.springframework.beans.factory.support.AbstractBeanFactory.getBean(java.lang.String)
org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(java.lang.String, org.springframework.beans.factory.ObjectFactory<?>)
org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, java.lang.Object[])
org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.addSingletonFactory
org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.getEarlyBeanReference
org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.addSingleton

---
Sping选择了第二种，如果是第一种，就会有以下不同的处理逻辑：

在提前曝光半成品时，直接执行getEarlyBeanReference创建到代理，并放入到缓存earlySingletonObjects中。

有了上一步，那就不需要通过ObjectFactory来延迟执行getEarlyBeanReference，也就不需要singletonFactories这一级缓存。

这种处理方式可行吗？

这里做个试验，对AbstractAutowireCapableBeanFactory做个小改造，在放入三级缓存之后立刻取出并放入二级缓存，这样三级缓存的作用就完全被忽略掉，就相当于只有二级缓存。

测试结果是可以的，并且从源码上分析可以得出两种方式性能是一样的，并不会影响到Sping启动速度。那为什么Sping不选择二级缓存方式，而是要额外加一层缓存？

如果要使用二级缓存解决循环依赖，意味着Bean在构造完后就创建代理对象，这样违背了Spring设计原则。

设计原则：Spring结合AOP跟Bean的生命周期，是在Bean创建完全之后通过AnnotationAwareAspectJAutoProxyCreator这个后置处理器来完成的，
     在这个后置处理的postProcessAfterInitialization方法中对初始化后的Bean完成AOP代理。
     如果出现了循环依赖，那没有办法，只有给Bean先创建代理，但是没有出现循环依赖的情况下，设计之初就是让Bean在生命周期的最后一步完成代理而不是在实例化后就立马完成代理。
     
---


https://www.zhihu.com/question/445446018
https://zhuanlan.zhihu.com/p/496273636
https://zhuanlan.zhihu.com/p/358802637













