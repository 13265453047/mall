# **Java并发编程的学习**


## ConcurrentMap 使用

### get

### getOrDefault
> 获取到则返回 value，获取不到(即 null) 返回指定的默认值

### compute
+ 给定 key 值找到节点 p，与 remappingFunction(key, value) 生成的 val 值
    + p 为 null，val 为null ：没有改变，相当于无需进行操作
    + p 为 null，val 不为null ：新添加节点 Node(key, val)
    + p 不为 null，val 为null ：相当于删除 key 对应的节点
    + p 不为 null，val 不为null ：相当于更换 p 的 value 值

### computeIfAbsent
+ 给定 key 值找到节点 p，与 remappingFunction(key) 生成的 val 值
    + p 为 null，val 为null ：没有改变，相当于无需进行操作
    + p 为 null，val 不为null ：新添加节点 Node(key, val)
    + p 不为 null，val 为null ：返回 val 值
    + p 不为 null，val 不为null ：返回 val 值

### computeIfPresent
+ 给定 key 值找到节点 p，与 remappingFunction(key,value) 生成的 val 值
    + p 为 null ：不进行任何操作
    + p 不为 null，val 为null ：删除 p 节点
    + p 不为 null，val 不为null ：替换 p 节点的 value 值
    
### merge
> ConcurrentMap merge(key, 5, Integer::sum)
> 5：为默认值
> Integer::sum 发现相同key的操作处理
> map 中含有key，则将相同key取出其value进行累加操作，没有key时，则设置默认值

