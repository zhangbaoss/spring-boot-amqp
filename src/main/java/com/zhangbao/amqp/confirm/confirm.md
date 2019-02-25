###Confirm模式
* 消息发送到rabbitMq后,在发送端使用waitConfirms方法会收到一条响应
确认是否确认发送到rabbitMq中
* 已经使用事务模式的队列不能更改其模式为confirm模式
* 如果rabbitMq持久化,则在持久化后才会返回确认


####Confirm异步模式:
Channel对象提供的ConfirmListener()回调方法只包含deliveryTag(当前Channel发出的消息序列号)
我们需要自己为每一个Channel维护一个unconfirm的消息序号集合,每publish
一条数据,集合中元素加1,每回调一次handleAck方法,unconfirm集合删掉相应的一条(multiple=false)
或多条(multiple=true)记录,从程序运行效率上看,这个unconfirm集合最好采用有序集合SortedSet存储结构
