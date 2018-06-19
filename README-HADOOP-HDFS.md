# Hadoop 学习

## Hadoop HDFS概述

Hadoop文件系统是使用分布式文件系统设计开发的。它运行在商用硬件上。与其他分布式系统不同，HDFS是高度容错的，并且使用低成本硬件设计。

HDFS拥有大量的数据并提供更容易的访问。为了存储这样巨大的数据，文件存储在多个机器。这些文件以冗余方式存储，以在发生故障时避免系统可能的数据丢失。 HDFS还使应用程序可用于并行处理。

#### HDFS的特点
- 它适用于在分布式存储和处理。
- Hadoop提供了一个与HDFS交互的命令接口。
- namenode和datanode的内置服务器帮助用户轻松检查集群的状态。
- 流式访问文件系统数据。
- HDFS提供文件权限和身份验证。
- HDFS架构
- 下面给出了Hadoop文件系统的体系结构。

#### HDFS架构

下面给出了Hadoop文件系统的体系结构。

![Image text](https://github.com/yuanhaoz/HadoopDemo/blob/master/img/hdfs_architecture.jpg)

HDFS遵循主从架构，并具有以下元素。

**Namenode**

namenode是包含GNU / Linux操作系统和namenode软件的商用硬件。它是一个可以在商用硬件上运行的软件。具有namenode的系统充当主服务器，它执行以下任务：

- 管理文件系统命名空间。
- 调整客户端对文件的访问。
- 它还执行文件系统操作，例如重命名，关闭和打开文件和目录。

**Datanode**

datanode是具有GNU / Linux操作系统和datanode软件的商用硬件。对于集群中的每个节点（商品硬件/系统），都会有一个datanode。这些节点管理其系统的数据存储。

- Datanodes根据客户端请求对文件系统执行读写操作。
- 它们还根据namenode的指令执行诸如块创建，删除和复制的操作。

**Block**

一般用户数据存储在HDFS的文件中。文件系统中的文件将被分成一个或多个段和/或存储在各个数据节点中。这些文件段称为块。换句话说，HDFS可以读取或写入的最小数据量称为块。默认块大小为64MB，但可以根据需要更改HDFS配置来增加。

**HDFS的目的**

- **故障检测和恢复** ：由于HDFS包括大量的商品硬件，组件的故障频繁。因此，HDFS应该具有快速和自动故障检测和恢复的机制。

- **巨大的数据集** ：HDFS应该每个集群有数百个节点来管理具有巨大数据集的应用程序。

- **硬件数据** ：当在数据附近进行计算时，可以有效地完成所请求的任务。特别是在涉及巨大数据集的情况下，它减少了网络流量并增加了吞吐量。


## Hadoop HDFS操作

#### 启动HDFS
首先，您必须格式化配置的HDFS文件系统，打开namenode（HDFS服务器），然后执行以下命令。
```markdown
$ hadoop namenode -format 
```

格式化HDFS后，启动分布式文件系统。以下命令将启动namenode以及数据节点作为集群。
```markdown
$ start-dfs.sh 
```

**在HDFS中列出文件**

在服务器中加载信息后，我们可以在一个目录中找到文件列表，文件的状态，使用'ls'。下面给出了ls的语法，您可以将其传递到目录或文件名作为参数。
```markdown
$ $HADOOP_HOME/bin/hadoop fs -ls <args>
```

**将数据插入HDFS**

假设我们在本地系统中的称为file.txt的文件中有数据，应该保存在hdfs文件系统中。按照以下步骤在Hadoop文件系统中插入所需的文件。

**第1步**

您必须创建一个输入目录。
```markdown
$ $HADOOP_HOME/bin/hadoop fs -mkdir /user/input 
```

**第2步**

使用put命令将数据文件从本地系统传输并存储到Hadoop文件系统。
```markdown
$ $HADOOP_HOME/bin/hadoop fs -put /home/file.txt /user/input 
```

**第3步**

您可以使用ls命令验证文件。
```markdown
$ $HADOOP_HOME/bin/hadoop fs -ls /user/input 
```

#### 从HDFS检索数据
假设我们在HDFS中有一个名为outfile的文件。下面给出的是从Hadoop文件系统中检索所需文件的简单示例。

**第1步**

最初，使用cat命令查看HDFS中的数据。
```markdown
$ $HADOOP_HOME/bin/hadoop fs -cat /user/output/outfile 
```

**第2步**

使用get命令将文件从HDFS获取到本地文件系统。
```markdown
$ $HADOOP_HOME/bin/hadoop fs -get /user/output/ /home/hadoop_tp/ 
```

#### 关闭HDFS
您可以使用以下命令关闭HDFS。
```markdown
$ stop-dfs.sh 
```
