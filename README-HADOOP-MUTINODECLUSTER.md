# Hadoop 学习

## Hadoop 多节点集群

本章介绍了分布式环境中Hadoop多节点集群的设置。

由于整个集群无法演示，我们将使用三个系统（一个主节点和两个从节点）解释Hadoop集群环境;下面给出他们的IP地址。

- Hadoop Master：192.168.1.15（hadoop-master）
- Hadoop Slave：192.168.1.16（hadoop-slave-1）
- Hadoop Slave：192.168.1.17（hadoop-slave-2）

按照以下步骤进行Hadoop多节点群集设置。

#### 安装Java
Java是Hadoop的主要先决条件。首先，您应该使用“java -version”验证系统中是否存在java。 java版本命令的语法如下。
```markdown
$ java -version
```

如果一切正常，它会给你以下输出。

```markdown
java version "1.7.0_71" 
Java(TM) SE Runtime Environment (build 1.7.0_71-b13) 
Java HotSpot(TM) Client VM (build 25.0-b02, mixed mode)
```

如果系统中没有安装java，请按照给定的步骤安装java。

**步骤1**

通过访问以下链接下载java（JDK - X64.tar.gz）http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html

然后 jdk-7u71-linux-x64.tar.gz 将下载到您的系统。

**第2步**

通常你会在下载文件夹中找到下载的java文件。使用以下命令验证它并解压缩jdk-7u71-linux-x64.gz文件。
```markdown
$ cd Downloads/
$ ls
jdk-7u71-Linux-x64.gz
$ tar zxf jdk-7u71-Linux-x64.gz
$ ls
jdk1.7.0_71 jdk-7u71-Linux-x64.gz
```

**第3步**

要使java可用于所有用户，您必须将其移动到位置“/ usr / local /”。打开根目录，然后键入以下命令。
```markdown
$ su
password:
# mv jdk1.7.0_71 /usr/local/
# exit
```

**第4步**

要设置PATH和JAVA_HOME变量，请将以下命令添加到〜/.bashrc文件。
```markdown
export JAVA_HOME=/usr/local/jdk1.7.0_71
export PATH=PATH:$JAVA_HOME/bin
```

现在如上所述从终端验证java -version命令。按照上述过程并在所有集群节点中安装java。

#### 创建用户帐户

在主系统和从系统上创建系统用户帐户以使用Hadoop安装。
```markdown
# useradd hadoop 
# passwd hadoop
```

#### 映射节点
您必须在所有节点上的/ etc / folder中编辑hosts文件，指定每个系统的IP地址，后跟其主机名。
```markdown
# vi /etc/hosts
enter the following lines in the /etc/hosts file.
192.168.1.109 hadoop-master 
192.168.1.145 hadoop-slave-1 
192.168.56.1 hadoop-slave-2

```

#### 配置基于Key的登录
在每个节点中设置ssh，以便它们可以彼此通信，而不需要提示输入密码。
```markdown
# su hadoop 
$ ssh-keygen -t rsa 
$ ssh-copy-id -i ~/.ssh/id_rsa.pub tutorialspoint@hadoop-master 
$ ssh-copy-id -i ~/.ssh/id_rsa.pub hadoop_tp1@hadoop-slave-1 
$ ssh-copy-id -i ~/.ssh/id_rsa.pub hadoop_tp2@hadoop-slave-2 
$ chmod 0600 ~/.ssh/authorized_keys 
$ exit
```

#### Hadoop安装
在主服务器中，使用以下命令下载并安装Hadoop。
```markdown
# mkdir /opt/hadoop 
# cd /opt/hadoop/ 
# wget http://apache.mesi.com.ar/hadoop/common/hadoop-1.2.1/hadoop-1.2.0.tar.gz 
# tar -xzf hadoop-1.2.0.tar.gz 
# mv hadoop-1.2.0 hadoop
# chown -R hadoop /opt/hadoop 
# cd /opt/hadoop/hadoop/
```

#### Hadoop配置
您必须通过进行以下更改来配置Hadoop服务器。

**core-site.xml**

打开core-site.xml文件并进行编辑，如下所示。
```markdown
<configuration>
   <property> 
      <name>fs.default.name</name> 
      <value>hdfs://hadoop-master:9000/</value> 
   </property> 
   <property> 
      <name>dfs.permissions</name> 
      <value>false</value> 
   </property> 
</configuration>
```

**hdfs-site.xml**

打开hdfs-site.xml文件并进行编辑，如下所示。
```markdown
<configuration>
   <property> 
      <name>dfs.data.dir</name> 
      <value>/opt/hadoop/hadoop/dfs/name/data</value> 
      <final>true</final> 
   </property> 

   <property> 
      <name>dfs.name.dir</name> 
      <value>/opt/hadoop/hadoop/dfs/name</value> 
      <final>true</final> 
   </property> 

   <property> 
      <name>dfs.replication</name> 
      <value>1</value> 
   </property> 
</configuration>
```

**mapred-site.xml**

打开mapred-site.xml文件并进行编辑，如下所示。
```markdown
<configuration>
   <property> 
      <name>mapred.job.tracker</name> 
      <value>hadoop-master:9001</value> 
   </property> 
</configuration>
```

**hadoop-env.sh**

打开**hadoop-env.sh**文件并编辑JAVA_HOME，HADOOP_CONF_DIR和HADOOP_OPTS，如下所示。

**注意**：根据系统配置设置JAVA_HOME。
```markdown
export JAVA_HOME=/opt/jdk1.7.0_17 export HADOOP_OPTS=-Djava.net.preferIPv4Stack=true 
export HADOOP_CONF_DIR=/opt/hadoop/hadoop/conf
```

#### 在从属服务器 Slave上安装Hadoop
按照给定的命令在所有从属服务器 Slave上安装Hadoop。
```markdown
# su hadoop 
$ cd /opt/hadoop 
$ scp -r hadoop hadoop-slave-1:/opt/hadoop 
$ scp -r hadoop hadoop-slave-2:/opt/hadoop
```

#### 在主服务器上配置Hadoop
打开主服务器并按照给定的命令配置它。
```markdown
# su hadoop 
$ cd /opt/hadoop/hadoop
```

**配置主节点**

```markdown
$ vi etc/hadoop/masters
hadoop-master
```

**配置从节点**

```markdown
$ vi etc/hadoop/slaves
hadoop-slave-1 
hadoop-slave-2
```

**格式命名Hadoop Master上的节点**

```markdown
# su hadoop 
$ cd /opt/hadoop/hadoop 
$ bin/hadoop namenode –format
11/10/14 10:58:07 INFO namenode.NameNode: STARTUP_MSG: /************************************************************ 
STARTUP_MSG: Starting NameNode 
STARTUP_MSG: host = hadoop-master/192.168.1.109 
STARTUP_MSG: args = [-format] 
STARTUP_MSG: version = 1.2.0 
STARTUP_MSG: build = https://svn.apache.org/repos/asf/hadoop/common/branches/branch-1.2 -r 1479473; compiled by 'hortonfo' on Mon May 6 06:59:37 UTC 2013 
STARTUP_MSG: java = 1.7.0_71 ************************************************************/ 11/10/14 10:58:08 INFO util.GSet: Computing capacity for map BlocksMap editlog=/opt/hadoop/hadoop/dfs/name/current/edits
………………………………………………….
………………………………………………….
…………………………………………………. 11/10/14 10:58:08 INFO common.Storage: Storage directory /opt/hadoop/hadoop/dfs/name has been successfully formatted. 11/10/14 10:58:08 INFO namenode.NameNode: 
SHUTDOWN_MSG: /************************************************************ SHUTDOWN_MSG: Shutting down NameNode at hadoop-master/192.168.1.15 ************************************************************/
```

#### 启动Hadoop服务

以下命令用于启动Hadoop-Master上的所有Hadoop服务。
```markdown
$ cd $HADOOP_HOME/sbin
$ start-all.sh
```

#### 在Hadoop集群中添加新的DataNode
下面给出了将新节点添加到Hadoop集群时需要遵循的步骤。

**网络配置**

通过一些适当的网络配置向现有Hadoop集群添加新节点。假设以下网络配置。

对于新节点配置：
```markdown
IP address : 192.168.1.103 
netmask : 255.255.255.0
hostname : slave3.in
```

#### 添加用户和SSH访问

**添加用户**

在新节点上，通过使用以下命令将“hadoop”用户和Hadoop用户的密码添加到“hadoop123”或任何您想要的密码。
```markdown
useradd hadoop
passwd hadoop
```

设置从主机到新从属机的密码关联性低。

**在主站上执行以下操作**

```markdown
mkdir -p $HOME/.ssh 
chmod 700 $HOME/.ssh 
ssh-keygen -t rsa -P '' -f $HOME/.ssh/id_rsa 
cat $HOME/.ssh/id_rsa.pub >> $HOME/.ssh/authorized_keys 
chmod 644 $HOME/.ssh/authorized_keys
Copy the public key to new slave node in hadoop user $HOME directory
scp $HOME/.ssh/id_rsa.pub hadoop@192.168.1.103:/home/hadoop/
```

**在从站上执行以下操作**

登录到hadoop。如果没有，登录到hadoop用户。
```markdown
su hadoop ssh -X hadoop@192.168.1.103
```

将公共密钥的内容复制到文件“$ HOME / .ssh / authorized_keys”中，然后通过执行以下命令更改其权限。
```markdown
cd $HOME
mkdir -p $HOME/.ssh 
chmod 700 $HOME/.ssh
cat id_rsa.pub >>$HOME/.ssh/authorized_keys 
chmod 644 $HOME/.ssh/authorized_keys
```

检查主机的ssh登录。现在检查是否可以ssh到新节点没有主密码。
```markdown
ssh hadoop@192.168.1.103 or hadoop@slave3
```

#### 设置新节点的主机名
您可以在文件/etc /sysconfig /network中设置主机名
```markdown
On new slave3 machine
NETWORKING=yes 
HOSTNAME=slave3.in
```

要使更改生效，请重新启动计算机或运行hostname命令到具有相应主机名的新计算机（重新启动是一个好的选项）。

在slave3节点机器上：

主机名 slave3.in

使用以下行在集群的所有计算机上更新/etc/hosts：
```markdown
192.168.1.102 slave3.in slave3
```

现在尝试用主机名ping机器，以检查它是否正在解析到IP。

在新节点机器上：
```markdown
ping master.in
```

#### 在新节点上启动DataNode
使用$HADOOP_HOME / bin / hadoop-daemon.sh script手动启动datanode守护程序。它将自动联系主节点（NameNode）并加入集群。我们还应该将新节点添加到主服务器中的conf / slaves文件。基于脚本的命令将识别新节点。

**登录新节点**

```markdown
su hadoop or ssh -X hadoop@192.168.1.103
```

**使用以下命令在新添加的从属节点上启动HDFS**

```markdown
./bin/hadoop-daemon.sh start datanode
```

**在新节点上检查jps命令的输出。它看起来如下。**

```markdown
$ jps
7141 DataNode
10312 Jps
```

#### 从Hadoop集群中删除DataNode
我们可以在运行时从群集中删除节点，而不会有任何数据丢失。 HDFS提供了一个停用功能，确保安全地执行删除节点。要使用它，请按照下面的步骤：

**第1步：登录master**

登录到安装了Hadoop的主机用户。
```markdown
$ su hadoop
```

**第2步：更改集群配置**

必须在启动集群之前配置排除文件。将名为dfs.hosts.exclude的键添加到我们的$HADOOP_HOME/etc/hadoop/hdfs-site.xml文件中。与此键相关的值提供了NameNode本地文件系统上文件的完整路径，该文件包含不允许连接到HDFS的计算机列表。

例如，将这些行添加到etc/hadoop/hdfs-site.xml文件。
```markdown
<property> 
   <name>dfs.hosts.exclude</name> 
   <value>/home/hadoop/hadoop-1.2.1/hdfs_exclude.txt</value> 
   <description>DFS exclude</description> 
</property>
```

**第3步：确定要停用的主机**

要停用的每台计算机都应添加到由hdfs_exclude.txt标识的文件中，每行一个域名。这将阻止他们连接到NameNode。如果要删除DataNode2，“/home/hadoop/hadoop-1.2.1/hdfs_exclude.txt”文件的内容如下所示。
```markdown
slave2.in
```

**第4步：步骤4：强制配置重新加载**

运行命令“$ HADOOP_HOME/bin/hadoop dfsadmin -refreshNodes”，不带引号。
```markdown
$ $HADOOP_HOME/bin/hadoop dfsadmin -refreshNodes
```

这将强制NameNode重新读取其配置，包括新更新的“excludes”文件。它将在一段时间内停止节点，从而允许将每个节点的块的时间复制到被调度为保持活动的机器上。

在**slave2.in**上，检查jps命令输出。一段时间后，您将看到DataNode进程被自动关闭。

**第5步：关闭节点**

在停用过程完成后，停用的硬件可以安全关闭进行维护。运行report命令dfsadmin以检查停用的状态。以下命令将描述退役节点和连接到集群的节点的状态。
```markdown
$ $HADOOP_HOME/bin/hadoop dfsadmin -report
```

**第6步：编辑再次排除文件**
一旦计算机已停用，它们可以从“excludees”文件中删除。再次运行“**$ HADOOP_HOME / bin / hadoop dfsadmin -refreshNodes**”会将excludees文件读回NameNode;允许DataNodes在维护完成后重新加入群集，或者在群集中再次需要额外的容量等。

**特别注意**：如果遵循上述过程，并且tasktracker进程仍在节点上运行，则需要关闭它。一种方法是断开机器，就像我们在上面的步骤。

tasktracker可以在任何时间点通过以下命令即时运行/关闭。Master将自动识别过程，并宣布为死亡。没有必要遵循相同的过程来删除tasktracker，因为它与DataNode相比并不重要。DataNode包含要安全删除的数据，而不会有任何数据丢失。

tasktracker可以在任何时间点通过以下命令即时运行/关闭。

```markdown
$ $HADOOP_HOME/bin/hadoop-daemon.sh stop tasktracker $HADOOP_HOME/bin/hadoop-daemon.sh 
start tasktracker
```
