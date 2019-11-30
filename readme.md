​	opipe是一个操作管道，其设计思路与linux的命令管道一致。不同的是，linux的命令管道串联了一组命令，命令间是以输入、输出流串联在一起的。而opipe实际为一个操作(函数)的管道，操作间以返回值及调用参数的方式串联在一起，即opipe会用前一个操作的返回值作为输入参数调用后一个操作，并重复这样的调度直到整个管道中的所有操作都执行完成。
opipe是由短语operation pipe变化而来。其中operation在opipe中被表示为一个函数形式；多个operation用符号”|”进行串联构成一个管道，格式如下：

```
“operation1 | operation2 | ……”
```


​	operation实现时采用了moql的Function技术，每个operation其实就是一个Function。但由于Function技术只接受EntityMap类型的参数，故opipe方法重新定义了一个接口operation，该接口可以接受一个Object类型的参数，这样方便使用者根据需要去扩展operation的实现。但需要注意的是，使用者需要确保管道中的operation的返回值能够成功作为下一个operation的调用参数。

```
public interface Operation {
  Object operate(Object in);
}
```

​	opipe中有两种采用了moql Function技术的概念。一种是operation；一种是function。operation实现了Operation接口，能够通过管道符串接为一个调用管道；而function不能进行串联，它被视为一种opipe能力的有效补充。因为function实现的返回值没有特定的约束，所以它有更广泛的应用环境，它可以在operation中被嵌套调用，如：对某个operation的输入参数进行特殊的预处理等。如下：

```
assignment(ipAndPort, strFormat('%s:%s', ip, port))
```

​	assignment是一个operation，而strFormat是一个function。
​	下面是关于opipe的一个完整的示例：

```
String s = "2019-10-26 192.1.32.4:8081";
String pipeline =
    "regParse('data','(?<time>[^\\s]+)\\s(?<ip>[^:]+):(?<port>\\d+)') "
- "| assignment(ipAndPort, strFormat('%s:%s', ip, port)) "
- "| printOut(strFormat('%s %s', ipAndPort, time))";
  try {
    Opipe opipe = OpipeBuilder.createOpipe(pipeline);
    EntityMap entityMap = new EntityMapImpl();
    entityMap.putEntity("data", s);
    opipe.operate(entityMap);
  } catch (MoqlException e) {
    e.printStackTrace();
  }
```

​	在例子中regParse、assignment以及printOut三个operation被串接为一个管道。regParse的功能为将输入的字符串按照正则表达式的描述捕获为一个EntityMap；示例中该EntityMap内包含4个键值对，它们的key分别为time、ip、port及data。assignment为赋值操作，参数1为待赋值变量的名字，参数2为值，赋值完成后，它们将作为一个键值对写入输入的EntityMap中并返回；strFormat为字符串格式化函数(参见：JDK关于String.format的相关说明)。示例中assignment执行后EntitMap中会多出一个名为ipAndPort，值为192.1.32.4:8081的键值对。printOut为一个打印输出操作，它将输入的对象打印输出到控制台。示例最后的输出为“192.1.32.4:8081 2019-10-26”。
​	opipe项目只提供了几个简单的操作实现，主要是为了方便开发者理解这个设计思路。开发者可以在理解后，根据自己的应用需要去扩展operation或function。并可通过调用OpipeBuilder的registFunction注册自己的operation或function。