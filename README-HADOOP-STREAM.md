# Hadoop 学习

## Hadoop 流
Hadoop流是Hadoop发行版附带的一个实用程序。此实用程序允许您使用任何可执行文件或脚本作为映射程序和/或reducer创建和运行Map / Reduce作业。

#### 使用Python的示例
对于Hadoop流，我们正在考虑字数问题。 Hadoop中的任何作业必须有两个阶段：mapper和reducer。我们已经为python脚本中的mapper和reducer编写了代码，以便在Hadoop下运行它。也可以在Perl和Ruby中写同样的内容。

**映射器阶段代码**

```markdown
!/usr/bin/python
import sys
# Input takes from standard input for myline in sys.stdin: 
# Remove whitespace either side myline = myline.strip() 
# Break the line into words words = myline.split() 
# Iterate the words list for myword in words: 
# Write the results to standard output print '%s	%s' % (myword, 1)
```

确保此文件具有执行权限（chmod + x /home/expert /hadoop-1.2.1 / mapper.py）。

**减速器阶段代码**

```markdown
#!/usr/bin/python
from operator import itemgetter 
import sys 
current_word = ""
current_count = 0 
word = "" 
# Input takes from standard input for myline in sys.stdin: 
# Remove whitespace either side myline = myline.strip() 
# Split the input we got from mapper.py word, count = myline.split('	', 1) 
# Convert count variable to integer 
   try: 
      count = int(count) 
except ValueError: 
   # Count was not a number, so silently ignore this line continue
if current_word == word: 
   current_count += count 
else: 
   if current_word: 
      # Write result to standard output print '%s	%s' % (current_word, current_count) 
   current_count = count
   current_word = word
# Do not forget to output the last word if needed! 
if current_word == word: 
   print '%s	%s' % (current_word, current_count)
```

将mapper和reducer代码保存在Hadoop主目录中的mapper.py和reducer.py中。确保这些文件具有执行权限（chmod + x mapper.py和chmod + x reducer.py）。因为python是缩进敏感所以相同的代码可以从下面的链接下载。

#### 执行WordCount程序

```markdown
$ $HADOOP_HOME/bin/hadoop jar contrib/streaming/hadoop-streaming-1.
2.1.jar 
   -input input_dirs  
   -output output_dir  
   -mapper <path/mapper.py  
   -reducer <path/reducer.py
```

其中“\”用于行连续以便清楚可读性。

**例如：**
```markdown
./bin/hadoop jar contrib/streaming/hadoop-streaming-1.2.1.jar -input myinput -output myoutput -mapper /home/expert/hadoop-1.2.1/mapper.py -reducer /home/expert/hadoop-1.2.1/reducer.py
```

#### 流如何工作

在上面的示例中，mapper和reducer都是从标准输入读取输入并将输出发送到标准输出的python脚本。该实用程序将创建一个Map / Reduce作业，将作业提交到适当的群集，并监视作业的进度，直到作业完成。

当为映射器指定脚本时，每个映射器任务将在映射器初始化时作为单独的进程启动脚本。当映射器任务运行时，它将其输入转换为行，并将这些行馈送到进程的标准输入（STDIN）。同时，映射器从进程的标准输出（STDOUT）收集面向行的输出，并将每行转换为键/值对，作为映射器的输出收集。默认情况下，直到第一个制表符字符的行的前缀是键，行的其余部分（不包括制表符字符）将是值。如果行中没有制表符，则整个行被视为键，值为null。但是，这可以根据一个需要定制。

当为reducer指定脚本时，每个reducer任务将作为单独的进程启动脚本，然后初始化reducer。当reducer任务运行时，它将其输入键/值对转换为行，并将行馈送到进程的标准输入（STDIN）。同时，reducer从进程的标准输出（STDOUT）收集面向行的输出，将每行转换为键/值对，将其作为reducer的输出进行收集。默认情况下，直到第一个制表符字符的行的前缀是键，行的其余部分（不包括制表符字符）是值。但是，这可以根据特定要求进行定制。

**重要命令**

参数 | 描述
--- | ---
-input directory/file-name | 输入mapper的位置。（需要）
-output directory-name | 减速器的输出位置。（需要）
-mapper executable or script or JavaClassName | Mapper可执行文件。（需要）
-reducer executable or script or JavaClassName | Reducer可执行文件。（需要）
-file file-name | 使mapper，reducer或combiner可执行文件在计算节点本地可用。
-inputformat JavaClassName | 你提供的类应该返回Text类的键/值对。如果未指定，则使用TextInputFormat作为默认值。
-outputformat JavaClassName | 您提供的类应该采用Text类的键/值对。如果未指定，则使用TextOutputformat作为默认值。
-partitioner JavaClassName | 确定将键发送到哪个reduce的类。
-combiner streamingCommand or JavaClassName | 组合器可执行映射输出。
-cmdenv name=value | 将环境变量传递到流式命令。
-inputreader | 对于向后兼容性：指定记录读取器类（而不是输入格式类）。
-verbose | 详细输出。
-lazyOutput | 创建输出延迟。例如，如果输出格式基于FileOutputFormat，则输出文件仅在首次调用output.collect（或Context.write）时创建。
-numReduceTasks | 指定Reducer的数量。
-mapdebug | 映射任务失败时调用的脚本。
-reducedebug | 当reduce任务失败时调用的脚本。