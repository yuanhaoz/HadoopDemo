# Hadoop 学习

## Hadoop 介绍

Hadoop是一个用Java编写的Apache开源框架，允许使用简单的编程模型跨计算机集群分布式处理大型数据集。Hadoop框架工作的应用程序在跨计算机集群提供分布式存储和计算的环境中工作。Hadoop旨在从单个服务器扩展到数千个机器，每个都提供本地计算和存储。

#### Hadoop架构
在这种方法中，企业将具有存储和处理大数据的计算机。这里的数据将存储在RDBMS如Oracle数据库，MS SQL Server或DB2和复杂的软件可以写入与数据库交互，处理所需的数据，并将其呈现给用户进行分析。

Hadoop框架包括以下四个模块：

- Hadoop Common: 这些是其他Hadoop模块所需的Java库和实用程序。这些库提供文件系统和操作系统级抽象，并包含启动Hadoop所需的Java文件和脚本。
- Hadoop YARN: 这是一个用于作业调度和集群资源管理的框架。
- Hadoop Distributed File System (HDFS™): 分布式文件系统，提供对应用程序数据的高吞吐量访问。
- Hadoop MapReduce：这是基于YARN的用于并行处理大数据集的系统。

我们可以使用下面的图来描述这四个组件在Hadoop框架中可用。

![Image text](https://github.com/yuanhaoz/HadoopDemo/blob/master/img/hadoop_architecture.jpg)

自2012年以来，“Hadoop”这个术语通常不仅指上述基本模块，而且还指向可以安装在Hadoop之上或之上的附加软件包的收集，例如Apache Pig，Apache Hive，Apache HBase，Apache Spark等。

#### MapReduce
Hadoop MapReduce是一个软件框架，用于轻松编写应用程序，以可靠，容错的方式在大型集群（数千个节点）的商用硬件上并行处理大量数据。

术语MapReduce实际上指的是Hadoop程序执行的以下两个不同任务：
- The Map Task: 术语MapReduce实际上指的是Hadoop程序执行的以下两个不同任务：
- The Reduce Task: 此任务将map任务的输出作为输入，并将这些数据元组合并为较小的元组集合。 reduce任务总是在map任务之后执行。

通常输入和输出都存储在文件系统中。该框架负责调度任务，监视它们并重新执行失败的任务。

MapReduce框架由每个集群节点的单个主JobTracker和一个从属TaskTracker组成。主机负责资源管理，跟踪资源消耗/可用性以及调度从机上的作业组件任务，监视它们并重新执行失败的任务。从属TaskTracker按主控器指示执行任务，并定期向主控器提供任务状态信息。

JobTracker是Hadoop MapReduce服务的单点故障，这意味着如果JobTracker关闭，所有正在运行的作业都将停止。

#### Hadoop分布式文件系统
Hadoop可以直接与任何可安装的分布式文件系统（如本地FS，HFTP FS，S3 FS等）一起工作，但Hadoop使用的最常见的文件系统是Hadoop分布式文件系统（HDFS）。

Hadoop分布式文件系统（HDFS）基于Google文件系统（GFS），并提供一个分布式文件系统，该系统设计为在大型集群（数千台计算机）上运行小型计算机机器以可靠，容错方式。

HDFS使用主/从架构，其中主节点由管理文件系统元数据的单个NameNode和存储实际数据的一个或多个从节点DataNode组成。

HDFS命名空间中的文件被拆分为几个块，这些块存储在一组DataNode中。 NameNode决定块到DataNode的映射。DataNodes负责与文件系统的读写操作。它们还根据NameNode给出的指令来处理块创建，删除和复制。

HDFS提供了一个类似任何其他文件系统的shell，并且有一个命令列表可用于与文件系统交互。这些shell命令将在单独的章节以及适当的示例中介绍。

#### Hadoop如何工作？
**阶段 1**

用户/应用程序可以通过指定以下项目来向Hadoop（hadoop作业客户端）提交作业以获取所需的进程：
- 分布式文件系统中输入和输出文件的位置。
- java类以jar文件的形式包含map和reduce函数的实现。
- 通过设置作业的不同参数来配置作业。

**阶段 2**

Hadoop作业客户端然后将作业（jar /可执行文件等）和配置提交给JobTracker，JobTracker然后承担将软件/配置分发给从属的责任，Hadoop作业客户端然后将作业（jar /可执行文件等）和配置提交给JobTracker，JobTracker然后承担将软件/配置分发给从属的责任，

**阶段 3**

不同节点上的TaskTracker根据MapReduce实现执行任务，reduce函数的输出存储在文件系统上的输出文件中。

#### Hadoop的优势
- Hadoop框架允许用户快速编写和测试分布式系统。它是高效的，它自动分配数据和工作在整个机器，反过来，利用CPU核心的底层并行性。
- Hadoop不依赖硬件来提供容错和高可用性（FTHA），相反Hadoop库本身设计用于检测和处理应用程序层的故障。
- 服务器可以动态添加或从集群中删除，Hadoop继续运行而不中断。
- Hadoop的另一个大的优点是，除了开源之外，它在所有平台上兼容，因为它是基于Java的。



## Hadoop 环境设置

Hadoop由GNU / Linux平台及其版本支持。因此，我们必须安装一个Linux操作系统来设置Hadoop环境。如果您有除Linux以外的操作系统，您可以在其中安装Virtualbox软件，并在Virtualbox内部安装Linux。

#### 安装前设置
在将Hadoop安装到Linux环境之前，我们需要使用ssh（Secure Shell）来设置Linux。按照以下步骤设置Linux环境。

#### 创建用户
在开始时，建议为Hadoop创建一个单独的用户，以便将Hadoop文件系统与Unix文件系统隔离。按照以下步骤创建用户：

- 使用命令“su”打开根。
- 使用命令“useradd username”从root帐户创建用户。
- 现在您可以使用命令“su username”打开现有的用户帐户。

打开Linux终端并键入以下命令以创建用户。

```markdown
$ su 
   password: 
# useradd hadoop 
# passwd hadoop 
   New passwd: 
   Retype new passwd 
```

#### SSH设置和密钥生成
需要SSH设置在集群上执行不同的操作，如启动，停止，分布式守护程序shell操作。要对Hadoop的不同用户进行身份验证，需要为Hadoop用户提供公钥/私钥对，并与不同的用户共享。

以下命令用于使用SSH生成键值对。将公共密钥表单id_rsa.pub复制到authorized_keys，并分别向拥有者授予authorized_keys文件的读取和写入权限。
```markdown
$ ssh-keygen -t rsa 
$ cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys 
$ chmod 0600 ~/.ssh/authorized_keys 
```

#### 安装Java
Java是Hadoop的主要先决条件。首先，您应该使用命令“java -version”验证系统中是否存在java。 java版本命令的语法如下。
```markdown
$ java -version 
```
如果一切正常，它会给你以下输出。
```markdown
java version "1.7.0_71" 
Java(TM) SE Runtime Environment (build 1.7.0_71-b13) 
Java HotSpot(TM) Client VM (build 25.0-b02, mixed mode)  
```

如果系统中没有安装java，请按照以下步骤安装java。

**第1步**

通过访问以下链接http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads1880260.html下载java（JDK <latest version> - X64.tar.gz）。

然后jdk-7u71-linux-x64.tar.gz将下载到您的系统。

**第2步**

通常你会在下载文件夹中找到下载的java文件。使用以下命令验证它并解压缩jdk-7u71-linux-x64.gz文件。
```markdown
$ cd Downloads/ 
$ ls 
jdk-7u71-linux-x64.gz 
$ tar zxf jdk-7u71-linux-x64.gz 
$ ls 
jdk1.7.0_71   jdk-7u71-linux-x64.gz 
```

**第3步**

要使java可用于所有用户，您必须将其移动到位置“/ usr / local /”。打开root，然后键入以下命令。
```markdown
$ su 
password: 
# mv jdk1.7.0_71 /usr/local/ 
# exit 
```

**第4步**

要设置PATH和JAVA_HOME变量，请将以下命令添加到〜/ .bashrc文件。
```markdown
export JAVA_HOME=/usr/local/jdk1.7.0_71 
export PATH=$PATH:$JAVA_HOME/bin 
```

现在将所有更改应用到当前运行的系统。
```markdown
$ source ~/.bashrc
```

**第5步**

现在将所有更改应用到当前运行的系统。
```markdown
# alternatives --install /usr/bin/java java usr/local/java/bin/java 2
# alternatives --install /usr/bin/javac javac usr/local/java/bin/javac 2
# alternatives --install /usr/bin/jar jar usr/local/java/bin/jar 2
# alternatives --set java usr/local/java/bin/java
# alternatives --set javac usr/local/java/bin/javac
# alternatives --set jar usr/local/java/bin/jar
```

现在如上所述从终端验证java -version命令。

#### 下载Hadoop

使用以下命令从Apache Software Foundation下载并提取Hadoop 2.4.1。
```markdown
$ su 
password: 
# cd /usr/local 
# wget http://apache.claz.org/hadoop/common/hadoop-2.4.1/ hadoop-2.4.1.tar.gz 
# tar xzf hadoop-2.4.1.tar.gz 
# mv hadoop-2.4.1/* to hadoop/ 
# exit 
```

#### Hadoop操作模式
一旦下载了Hadoop，您就可以使用以下三种支持模式之一来操作Hadoop集群：

- **本地/独立模式** ：在系统中下载Hadoop之后，默认情况下，它以独立模式配置，并且可以作为单个Java进程运行。
- **伪分布式模式** ：它是单机上的分布式仿真。每个Hadoop守护进程（如hdfs，yarn，MapReduce等）都将作为单独的java进程运行。此模式对开发有用。
- **完全分布式的模式** ：此模式是完全分布式的，至少有两台或多台机器作为集群。我们将在接下来的章节中详细讨论这种模式。

#### 在独立模式下安装Hadoop
这里我们将讨论Hadoop 2.4.1在独立模式下的安装。

没有运行的守护程序，并且一切都在单个JVM中运行。独立模式适合在开发期间运行MapReduce程序，因为它很容易测试和调试。

**设置Hadoop**

您可以通过将以下命令附加到〜/.bashrc文件来设置Hadoop环境变量。
```markdown
export HADOOP_HOME=/usr/local/hadoop 
```

在继续进行之前，您需要确保Hadoop正常工作。只需发出以下命令：
```markdown
$ hadoop version 
```

如果您的设置一切正常，那么你应该看到以下结果：
```markdown
Hadoop 2.4.1 
Subversion https://svn.apache.org/repos/asf/hadoop/common -r 1529768 
Compiled by hortonmu on 2013-10-07T06:28Z 
Compiled with protoc 2.5.0
From source with checksum 79e53ce7994d1628b240f09af91e1af4 
```

这意味着你的Hadoop的独立模式设置工作正常。默认情况下，Hadoop配置为在单台计算机上以非分布式方式运行。

**例子**

让我们检查一个简单的Hadoop示例。 Hadoop安装提供了以下示例MapReduce jar文件，它提供了MapReduce的基本功能，可用于计算，如Pi值，文件列表中的字数等。
```markdown
$HADOOP_HOME/share/hadoop/mapreduce/hadoop-mapreduce-examples-2.2.0.jar 
```

让我们有一个输入目录，我们将推送几个文件，我们的要求是计数这些文件中的字的总数。要计算总字数，我们不需要写我们的MapReduce，只要.jar文件包含字计数的实现。您可以尝试使用相同的.jar文件的其他示例;只需发出以下命令来检查hadoop-mapreduce-examples-2.2.0.jar文件支持的MapReduce功能程序。
```markdown
$ hadoop jar $HADOOP_HOME/share/hadoop/mapreduce/hadoop-mapreduceexamples-2.2.0.jar 
```

**步骤1**

在输入目录中创建临时内容文件。您可以在要工作的任何位置创建此输入目录。
```markdown
$ mkdir input 
$ cp $HADOOP_HOME/*.txt input 
$ ls -l input 
```

它将在您的输入目录中提供以下文件：
```markdown
total 24 
-rw-r--r-- 1 root root 15164 Feb 21 10:14 LICENSE.txt 
-rw-r--r-- 1 root root   101 Feb 21 10:14 NOTICE.txt
-rw-r--r-- 1 root root  1366 Feb 21 10:14 README.txt 
```

这些文件已从Hadoop安装主目录复制。对于您的实验，您可以有不同的和大的文件集。

**第2步**

让我们开始Hadoop进程来计算输入目录中所有可用文件中的总字数，如下所示：
```markdown
$ hadoop jar $HADOOP_HOME/share/hadoop/mapreduce/hadoop-mapreduceexamples-2.2.0.jar  wordcount input output 
```

**第3步**

第2步将执行所需的处理并将输出保存在output / part-r00000文件中，您可以使用以下命令检查：
```markdown
$cat output/* 
```

它将列出所有字以及它们在输入目录中可用的所有文件中的总计数。
```markdown
"AS      4 
"Contribution" 1 
"Contributor" 1 
"Derivative 1
"Legal 1
"License"      1
"License");     1 
"Licensor"      1
"NOTICE”        1 
"Not      1 
"Object"        1 
"Source”        1 
"Work”    1 
"You"     1 
"Your")   1 
"[]"      1 
"control"       1 
"printed        1 
"submitted"     1 
(50%)     1 
(BIS),    1 
(C)       1 
(Don't)   1 
(ECCN)    1 
(INCLUDING      2 
(INCLUDING,     2 
.............
```

#### 在伪分布式模式下安装Hadoop

按照下面给出的步骤在伪分布式模式下安装Hadoop 2.4.1。

**第1步：设置Hadoop**

您可以通过将以下命令附加到〜/.bashrc文件来设置Hadoop环境变量。
```markdown
export HADOOP_HOME=/usr/local/hadoop 
export HADOOP_MAPRED_HOME=$HADOOP_HOME 
export HADOOP_COMMON_HOME=$HADOOP_HOME 
export HADOOP_HDFS_HOME=$HADOOP_HOME 
export YARN_HOME=$HADOOP_HOME 
export HADOOP_COMMON_LIB_NATIVE_DIR=$HADOOP_HOME/lib/native 
export PATH=$PATH:$HADOOP_HOME/sbin:$HADOOP_HOME/bin 
export HADOOP_INSTALL=$HADOOP_HOME 
```

现在将所有更改应用到当前运行的系统。
```markdown
$ source ~/.bashrc 
```

**第2步：Hadoop配置**

您可以在位置“$ HADOOP_HOME/etc/hadoop”中找到所有Hadoop配置文件。需要根据您的Hadoop基础结构对这些配置文件进行更改。
```markdown
$ cd $HADOOP_HOME/etc/hadoop
```

为了在java中开发Hadoop程序，您必须通过用系统中java的位置替换JAVA_HOME值来重置hadoop-env.sh文件中的java环境变量。
```markdown
export JAVA_HOME=/usr/local/jdk1.7.0_71
```

以下是您必须编辑以配置Hadoop的文件列表。

**core-site.xml**

**core-site.xml**文件包含诸如用于Hadoop实例的端口号，为文件系统分配的内存，用于存储数据的内存限制以及读/写缓冲区大小的信息。

打开core-site.xml并在<configuration>，</ configuration>标签之间添加以下属性。
```markdown
<configuration>

   <property>
      <name>fs.default.name </name>
      <value> hdfs://localhost:9000 </value> 
   </property>
 
</configuration>
```

**hdfs-site.xml**

**hdfs-site.xml**文件包含本地文件系统的复制数据值，namenode路径和datanode路径等信息。这意味着您要存储Hadoop基础架构的位置。

让我们假设以下数据。
```markdown
dfs.replication (data replication value) = 1 
(In the below given path /hadoop/ is the user name. 
hadoopinfra/hdfs/namenode is the directory created by hdfs file system.) 
namenode path = //home/hadoop/hadoopinfra/hdfs/namenode 
(hadoopinfra/hdfs/datanode is the directory created by hdfs file system.) 
datanode path = //home/hadoop/hadoopinfra/hdfs/datanode 
```

打开此文件，并在此文件中的<configuration> </ configuration>标记之间添加以下属性。
```markdown
<configuration>

   <property>
      <name>dfs.replication</name>
      <value>1</value>
   </property>
    
   <property>
      <name>dfs.name.dir</name>
      <value>file:///home/hadoop/hadoopinfra/hdfs/namenode </value>
   </property>
    
   <property>
      <name>dfs.data.dir</name> 
      <value>file:///home/hadoop/hadoopinfra/hdfs/datanode </value> 
   </property>
       
</configuration>
```

**注意**：在上述文件中，所有属性值都是用户定义的，您可以根据Hadoop基础结构进行更改。

**yarn-site.xml**

此文件用于将Yarn为Hadoop配置为Hadoop。打开yarn-site.xml文件，并在此文件中的<configuration>，</ configuration>标记之间添加以下属性。
```markdown
<configuration>
 
   <property>
      <name>yarn.nodemanager.aux-services</name>
      <value>mapreduce_shuffle</value> 
   </property>
  
</configuration>
```

**mapred-site.xml**

此文件用于指定我们使用的MapReduce框架。默认情况下，Hadoop包含yarn-site.xml的模板。首先，需要使用以下命令将文件从mapred-site，xml.template复制到mapred-site.xml文件。
```markdown
$ cp mapred-site.xml.template mapred-site.xml
```
 
打开mapred-site.xml文件，并在此文件中的<configuration>，</ configuration>标记之间添加以下属性。
```markdown
<configuration>
 
   <property> 
      <name>mapreduce.framework.name</name>
      <value>yarn</value>
   </property>
   
</configuration>
```

#### 验证Hadoop安装
以下步骤用于验证Hadoop安装。

**第1步：名称节点设置**

使用命令“HDFS的NameNode -format”如下设置名称节点。
```markdown
$ cd ~ 
$ hdfs namenode -format 
```

预期结果如下。
```markdown
10/24/14 21:30:55 INFO namenode.NameNode: STARTUP_MSG: 
/************************************************************ 
STARTUP_MSG: Starting NameNode 
STARTUP_MSG:   host = localhost/192.168.1.11 
STARTUP_MSG:   args = [-format] 
STARTUP_MSG:   version = 2.4.1 
...
...
10/24/14 21:30:56 INFO common.Storage: Storage directory 
/home/hadoop/hadoopinfra/hdfs/namenode has been successfully formatted. 
10/24/14 21:30:56 INFO namenode.NNStorageRetentionManager: Going to 
retain 1 images with txid >= 0 
10/24/14 21:30:56 INFO util.ExitUtil: Exiting with status 0 
10/24/14 21:30:56 INFO namenode.NameNode: SHUTDOWN_MSG: 
/************************************************************ 
SHUTDOWN_MSG: Shutting down NameNode at localhost/192.168.1.11 
************************************************************/
```

**第2步：验证HadoopDFS**

以下命令用于启动dfs。执行此命令将启动您的Hadoop文件系统。
```markdown
$ start-dfs.sh 
```

预期输出如下：
```markdown
10/24/14 21:37:56 
Starting namenodes on [localhost] 
localhost: starting namenode, logging to /home/hadoop/hadoop
2.4.1/logs/hadoop-hadoop-namenode-localhost.out 
localhost: starting datanode, logging to /home/hadoop/hadoop
2.4.1/logs/hadoop-hadoop-datanode-localhost.out 
Starting secondary namenodes [0.0.0.0]
```

**第3步：验证Yarn脚本**

下面的命令被用于启动Yarn脚本。执行该命令将启动纱守护进程。
```markdown
$ start-yarn.sh 
```

预期输出如下：
```markdown
starting yarn daemons 
starting resourcemanager, logging to /home/hadoop/hadoop
2.4.1/logs/yarn-hadoop-resourcemanager-localhost.out 
localhost: starting nodemanager, logging to /home/hadoop/hadoop
2.4.1/logs/yarn-hadoop-nodemanager-localhost.out 
```

**第4步：在浏览器上访问Hadoop**

访问Hadoop的默认端口号为50070.使用以下URL在浏览器上获取Hadoop服务。
```markdown
http://localhost:50070/
```

![Image text](https://github.com/yuanhaoz/HadoopDemo/blob/master/img/hadoop_on_browser.jpg)

**第5步：验证集群的所有应用程序**

访问群集的所有应用程序的默认端口号为8088.使用以下URL访问此服务。
```markdown
http://localhost:8088/
```

![Image text](https://github.com/yuanhaoz/HadoopDemo/blob/master/img/hadoop_application_cluster.jpg)