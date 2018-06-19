# Hadoop 学习

## Hadoop MapReduce

MapReduce是一个框架，我们可以使用它来编写应用程序，以可靠的方式并行地处理大量商品硬件群集上的大量数据。

#### 什么是MapReduce？
MapReduce是一种基于java的分布式计算的处理技术和程序模型。 MapReduce算法包含两个重要任务，即Map和Reduce。Map采用一组数据并将其转换为另一组数据，其中各个元素被分解为元组（键/值对）。其次，reduce任务，它将map的输出作为输入，并将这些数据元组合并成一组较小的元组。作为MapReduce名称的顺序，reduce任务总是在map作业之后执行。

MapReduce的主要优点是易于在多个计算节点上扩展数据处理。在MapReduce模型下，数据处理原语称为映射器和缩减器。将数据处理应用程序分解为映射器和简化器有时并不重要。但是，一旦我们以MapReduce形式编写应用程序，扩展应用程序以在集群中运行数百，数千甚至数万台机器只是一种配置更改。这种简单的可扩展性是吸引许多程序员使用MapReduce模型的原因。

**算法**

- 通常MapReduce范例是基于将计算机发送到数据所在的位置！
- MapReduce程序在三个阶段执行，即map阶段，shuffle阶段和reduce阶段。
- Map 阶段 ：映射或映射器的作业是处理输入数据。一般来说，输入数据是以文件或目录的形式存储在Hadoop文件系统（HDFS）中。输入文件逐行传递到映射器函数。映射器处理数据并创建几个小块的数据。
- Reduce 阶段 ：这个阶段是Shuffle阶段和Reduce阶段的组合。 Reducer的工作是处理来自映射器的数据。处理后，它产生一组新的输出，将存储在HDFS中。
- 在MapReduce作业期间，Hadoop将Map和Reduce任务发送到集群中的相应服务器。
- 该框架管理数据传递的所有细节，例如发出任务，验证任务完成，以及在节点之间复制集群周围的数据。
- 大多数计算发生在节点上，本地磁盘上的数据减少了网络流量。

![Image text][https://github.com/yuanhaoz/HadoopDemo/blob/master/img/mapreduce_algorithm.jpg]

- 完成给定任务后，集群收集并减少数据以形成适当的结果，并将其发送回Hadoop服务器。


**输入和输出（Java透视图）**

MapReduce框架对<key，value>对进行操作，也就是说，框架将作业的输入视为一组<key，value>对，并生成一组<key，value>对作为作业输出，可能是不同类型。

键和值类应该由框架以序列化的方式，因此，需要实现Writable接口。此外，键类必须实现Writable-Comparable接口，以方便框架进行排序。MapReduce作业的输入和输出类型：（输入）<k1，v1> - > map - > <k2，v2> - > reduce - > <k3，v3>（输出）。

table | 输入 | 输出
---|---|---
Map | <k1, v1> | list (<k2, v2>)
Reduce | <k2, list(v2)> | list (<k3, v3>)

**术语**

- **PayLoad** - 应用程序实现Map和Reduce功能，并形成作业的核心。
- **Mapper**- 映射器将输入键/值对映射到一组中间键/值对。
- **NamedNode** - 管理Hadoop分布式文件系统（HDFS）的节点。
- **DataNode** - 在任何处理发生之前提前呈现数据的节点。
- **MasterNode** - JobTracker运行并接受来自客户端的作业请求的节​​点。
- **SlaveNode** - Map和Reduce程序运行的节点。
- **JobTracker** - 计划作业并跟踪将作业分配给任务跟踪器。
- **Task Tracker** - 跟踪任务并向JobTracker报告状态。
- **Job** - 程序是跨数据集的Mapper和Reducer的执行。
- **Task** - 在一个数据片段上执行Mapper或Reducer。
- **Task Attempt** - 尝试在SlaveNode上执行任务的特定实例。

示例场景
下面给出了关于组织的电力消耗的数据。它包含每月的电力消耗和各年的年平均值。

table | 一月 | 二月 | 三月 | 四月 | 五月 | 六月 | 七月 | 八月 | 九月 | 十月 | 十一月 | 十二月 | 平均
---|---|---|---|---|---|---|---|---|---|---|---|---|---
1979年 | 23 | 23 | 2 | 43 | 24 | 25 | 26 | 26 | 26 | 26 | 25 | 26 | 25
1980年 | 26 | 27 | 28 | 28 | 28 | 30 | 31 | 31 | 31 | 30 | 30 | 30 | 29
1981年 | 31 | 32 | 32 | 32 | 33 | 34 | 35 | 36 | 36 | 34 | 34 | 34 | 34
1984年 | 39 | 38 | 39 | 39 | 39 | 41 | 42 | 43 | 40 | 39 | 38 | 38 | 40
1985年 | 38 | 39 | 39 | 39 | 39 | 41 | 41 | 41 | 00 | 40 | 39 | 39 | 45

如果上述数据作为输入，我们必须编写应用程序来处理它，并产生结果，如找到最大使用年份，最小使用年份等。这是一个对于有限数量的记录的程序员的walkover。它们将简单地写入逻辑以产生所需的输出，并将数据传递给所写的应用程序。

但是，考虑一个特定国家的所有大型产业的电力消耗的数据，因为它的形成。

当我们编写应用程序来处理这样的批量数据时，

- 他们将需要很多时间来执行。
- 当我们将数据从源服务器移动到网络服务器时，会有很大的网络流量，等等。

为了解决这些问题，我们有MapReduce框架。

**输入数据**

上述数据保存为sample.txt并作为输入。输入文件如下所示。
```markdown
1979   23   23   2   43   24   25   26   26   26   26   25   26  25 
1980   26   27   28  28   28   30   31   31   31   30   30   30  29 
1981   31   32   32  32   33   34   35   36   36   34   34   34  34 
1984   39   38   39  39   39   41   42   43   40   39   38   38  40 
1985   38   39   39  39   39   41   41   41   00   40   39   39  45 
```

**示例程序**
下面给出了程序对使用MapReduce框架的示例数据。
```markdown
package hadoop; 

import java.util.*; 

import java.io.IOException; 
import java.io.IOException; 

import org.apache.hadoop.fs.Path; 
import org.apache.hadoop.conf.*; 
import org.apache.hadoop.io.*; 
import org.apache.hadoop.mapred.*; 
import org.apache.hadoop.util.*; 

public class ProcessUnits 
{ 
   //Mapper class 
   public static class E_EMapper extends MapReduceBase implements 
   Mapper<LongWritable ,/*Input key Type */ 
   Text,                /*Input value Type*/ 
   Text,                /*Output key Type*/ 
   IntWritable>        /*Output value Type*/ 
   { 
      
      //Map function 
      public void map(LongWritable key, Text value, 
      OutputCollector<Text, IntWritable> output,   
      Reporter reporter) throws IOException 
      { 
         String line = value.toString(); 
         String lasttoken = null; 
         StringTokenizer s = new StringTokenizer(line,"	"); 
         String year = s.nextToken(); 
         
         while(s.hasMoreTokens())
            {
               lasttoken=s.nextToken();
            } 
            
         int avgprice = Integer.parseInt(lasttoken); 
         output.collect(new Text(year), new IntWritable(avgprice)); 
      } 
   } 
   
   
   //Reducer class 
   public static class E_EReduce extends MapReduceBase implements 
   Reducer< Text, IntWritable, Text, IntWritable > 
   {  
   
      //Reduce function 
      public void reduce( Text key, Iterator <IntWritable> values, 
         OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException 
         { 
            int maxavg=30; 
            int val=Integer.MIN_VALUE; 
            
            while (values.hasNext()) 
            { 
               if((val=values.next().get())>maxavg) 
               { 
                  output.collect(key, new IntWritable(val)); 
               } 
            } 
 
         } 
   }  
   
   
   //Main function 
   public static void main(String args[])throws Exception 
   { 
      JobConf conf = new JobConf(ProcessUnits.class); 
      
      conf.setJobName("max_eletricityunits"); 
      conf.setOutputKeyClass(Text.class);
      conf.setOutputValueClass(IntWritable.class); 
      conf.setMapperClass(E_EMapper.class); 
      conf.setCombinerClass(E_EReduce.class); 
      conf.setReducerClass(E_EReduce.class); 
      conf.setInputFormat(TextInputFormat.class); 
      conf.setOutputFormat(TextOutputFormat.class); 
      
      FileInputFormat.setInputPaths(conf, new Path(args[0])); 
      FileOutputFormat.setOutputPath(conf, new Path(args[1])); 
      
      JobClient.runJob(conf); 
   } 
} 
```

将上述程序保存为 **ProcessUnits.java**。程序的编译和执行说明如下。

**过程单元程序的编译和执行**

让我们假设在Hadoop用户的主目录（例如/home/hadoop）。。

按照以下步骤编译并执行上述程序。

**第1步**

以下命令是创建一个目录来存储编译的java类。
```
$ mkdir units 
$ mkdir units/classes 
```

**第2步**

下载Hadoop-core-1.2.1.jar，用于编译和执行MapReduce程序。访问以下链接http://mvnrepository.com/artifact/org.apache.hadoop/hadoop-core/1.2.1下载jar。让我们假设下载的文件夹是/ home / hadoop /。

**第3步**

以下命令用于编译ProcessUnits.java程序并为该程序创建一个jar。
```markdown
$ javac -classpath hadoop-core-1.2.1.jar -d units/classes units/ProcessUnits.java 
$ jar -cvf units.jar -C units/classes . 
```

**第4步**

以下命令用于在HDFS中创建输入目录。
```markdown
$HADOOP_HOME/bin/hadoop fs -mkdir /input_dir 
```

**第5步**

下命令用于复制名为sample.txt的输入文件，在HDFS的输入目录中。
```markdown
$HADOOP_HOME/bin/hadoop fs -put /home/hadoop/sample.txt /input_dir 
```

**第6步**

以下命令用于验证输入目录中的文件。
```markdown
$HADOOP_HOME/bin/hadoop fs -ls /input_dir/ 
```

**第7步**

以下命令用于通过从输入目录获取输入文件来运行Eleunit_max应用程序。
```markdown
$HADOOP_HOME/bin/hadoop jar units.jar hadoop.ProcessUnits /input_dir /output_dir 
```

等待一段时间，直到文件被执行。执行后，如下所示，输出将包含输入拆分的数量，Map任务的数量，reducer任务的数量等。
```markdown
INFO mapreduce.Job: Job job_1414748220717_0002 
completed successfully 
14/10/31 06:02:52 
INFO mapreduce.Job: Counters: 49 
File System Counters 
 
FILE: Number of bytes read=61 
FILE: Number of bytes written=279400 
FILE: Number of read operations=0 
FILE: Number of large read operations=0   
FILE: Number of write operations=0 
HDFS: Number of bytes read=546 
HDFS: Number of bytes written=40 
HDFS: Number of read operations=9 
HDFS: Number of large read operations=0 
HDFS: Number of write operations=2 Job Counters 


   Launched map tasks=2  
   Launched reduce tasks=1 
   Data-local map tasks=2  
   Total time spent by all maps in occupied slots (ms)=146137 
   Total time spent by all reduces in occupied slots (ms)=441   
   Total time spent by all map tasks (ms)=14613 
   Total time spent by all reduce tasks (ms)=44120 
   Total vcore-seconds taken by all map tasks=146137 
   
   Total vcore-seconds taken by all reduce tasks=44120 
   Total megabyte-seconds taken by all map tasks=149644288 
   Total megabyte-seconds taken by all reduce tasks=45178880 
   
Map-Reduce Framework 
 
Map input records=5  
   Map output records=5   
   Map output bytes=45  
   Map output materialized bytes=67  
   Input split bytes=208 
   Combine input records=5  
   Combine output records=5 
   Reduce input groups=5  
   Reduce shuffle bytes=6  
   Reduce input records=5  
   Reduce output records=5  
   Spilled Records=10  
   Shuffled Maps =2  
   Failed Shuffles=0  
   Merged Map outputs=2  
   GC time elapsed (ms)=948  
   CPU time spent (ms)=5160  
   Physical memory (bytes) snapshot=47749120  
   Virtual memory (bytes) snapshot=2899349504  
   Total committed heap usage (bytes)=277684224
     
File Output Format Counters 
 
   Bytes Written=40 
```

**第8步**

以下命令用于验证输出文件夹中的结果文件。
```markdown
$HADOOP_HOME/bin/hadoop fs -ls /output_dir/ 
```

**第9步**

以下命令用于查看Part-00000文件中的输出。此文件由HDFS生成。
```markdown
$HADOOP_HOME/bin/hadoop fs -cat /output_dir/part-00000 
```

下面是MapReduce程序生成的输出。
```markdown
1981    34 
1984    40 
1985    45
```
 
**第10步**

以下命令用于将输出文件夹从HDFS复制到本地文件系统进行分析。
```markdown
$HADOOP_HOME/bin/hadoop fs -cat output_dir/part-00000/bin/hadoop dfs get /output_dir /home/hadoop 
```

**重要命令**

所有Hadoop命令都由$ HADOOP_HOME / bin / hadoop命令调用。运行不带任何参数的Hadoop脚本会打印所有命令的描述。

**用法** ：hadoop [--config confdir] COMMAND

下表列出了可用的选项及其说明。

选项 | 描述
---|---
namenode -format | 格式化DFS文件系统。
secondarynamenode | 运行DFS二次名称节点。
namenode | 运行DFS名称节点。
datanode | 运行DFS数据节点。
dfsadmin | 运行DFS管理客户端。
mradmin | 运行Map-Reduce管理客户端。
fsck | 运行DFS文件系统检查实用程序。
fs | 运行通用文件系统用户客户端。
balancer | 运行集群平衡实用程序。
oiv | 将离线fsimage查看器应用于fsimage。
fetchdt | 从NameNode获取委派令牌。
jobtracker | 运行MapReduce作业跟踪节点。
pipes | 运行管道作业。
tasktracker | 运行MapReduce任务跟踪节点。
historyserver | 作为独立的守护程序运行作业历史记录服务器。
job | 操作MapReduce作业。
queue | 获取有关JobQueues的信息。
version | 打印版本。
jar <jar> | 运行jar文件。
distcp <srcurl> <desturl> | 递归复制文件或目录。
distcp2 <srcurl> <desturl> | DistCp版本2。
archive -archiveName NAME -p | 创建hadoop归档。
<parent path> <src>* <dest>  | null	 
classpath | 打印获取Hadoop jar所需的类路径和所需的库。
daemonlog | 获取/设置每个守护程序的日志级别

**如何使用MapReduce任务交互**

用法：Hadoop的工作[GENERIC_OPTIONS]

以下是在Hadoop作业的可用通用的选项。

通用选项 | 描述
-submit <job-file> | 提交作业。
-status <job-id> | 打印映射并减少完成百分比和所有作业计数器。
-counter <job-id> <group-name> <countername> | 打印计数器值。
-kill <job-id> | 终止作业
-events <job-id> <fromevent-#> <#-of-events> | 打印jobtracker为给定范围接收的事件详细信息。
-history [all] <jobOutputDir> - history < jobOutputDir> | 打印作业详细信息，失败并停用提示详细信息。可以通过指定[all]选项查看有关作业的更多详细信息，如每个任务的成功任务和任务尝试。
-list[all] | 显示所有作业。 -list仅显示尚未完成的作业。
-kill-task <task-id> | 终止任务。已终止的任务不会计入失败的尝试次数。
-fail-task <task-id> | 失败的任务。失败的任务将根据失败的尝试进行计数。
-set-priority <job-id> <priority> | 更改作业的优先级。允许的优先级值为VERY_HIGH，HIGH，NORMAL，LOW，VERY_LOW

**查看作业的状态**
```markdown
$ $HADOOP_HOME/bin/hadoop job -status <JOB-ID> 
e.g. 
$ $HADOOP_HOME/bin/hadoop job -status job_201310191043_0004 
```

**查看job output-dir的历史**
```markdown
$ $HADOOP_HOME/bin/hadoop job -history <DIR-NAME> 
e.g. 
$ $HADOOP_HOME/bin/hadoop job -history /user/expert/output 
```

**终止作业**
```markdown
$ $HADOOP_HOME/bin/hadoop job -kill <JOB-ID> 
e.g. 
$ $HADOOP_HOME/bin/hadoop job -kill job_201310191043_0004 
```