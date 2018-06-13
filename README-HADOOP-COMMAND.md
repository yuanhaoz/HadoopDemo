# Hadoop 学习

## Hadoop 命令参考

在“$ HADOOP_HOME/bin/hadoop fs”中还有比这里演示的更多的命令，虽然这些基本操作将让你开始。运行./bin/hadoop dfs而没有其他参数将列出可以使用FsShell系统运行的所有命令。此外，$HADOOP_HOME/bin/hadoop fs -help commandName将显示有问题的操作的简短使用摘要，如果您卡住了。

所有操作的表如下所示。以下约定用于参数：
```markdown
"<path>" means any file or directory name. 
"<path>..." means one or more file or directory names. 
"<file>" means any filename. 
"<src>" and "<dest>" are path names in a directed operation. 
"<localSrc>" and "<localDest>" are paths as above, but on the local file system. 
```

所有其他文件和路径名称引用HDFS中的对象。
- ls < path >

列出由path指定的目录的内容，显示每个条目的名称，权限，所有者，大小和修改日期。

- lsr < path >

行为像-ls，但递归显示路径的所有子目录中的条目。

- du < path >

显示与路径匹配的所有文件的磁盘使用情况（以字节为单位）文件名用完整的HDFS协议前缀报告。

- dus < path >

像-du，但打印路径中所有文件/目录的磁盘使用情况的摘要。

- mv < src >< dest >

在HDFS中将src指示的文件或目录移动到dest。

- cp < src > < dest >

副本由src标识的文件或目录什特内HDFS。

- rm < path >

删除由路径标识的文件或空目录。

- rmr < path >

删除由路径标识的文件或目录。递归删除任何子条目（即路径的文件或子目录）。

- put < localSrc > < dest >

将文件或目录从localSrc标识的本地文件系统复制到DFS中的dest。

- copyFromLocal < localSrc > < dest >

与输入 -put 相同

- moveFromLocal < localSrc > < dest >

将文件或目录从localSrc标识的本地文件系统复制到HDFS中的dest，然后在成功时删除本地副本。

- get [-crc] < src > < localDest >

将由src标识的HDFS中的文件或目录复制到localDest标识的本地文件系统路径。

- getmerge < src > < localDest >

检索与HDFS中的路径src匹配的所有文件，并将它们复制到由localDest标识的本地文件系统中的单个合并文件。

- cat < filen-ame >

显示stdout上的文件名的内容。

- copyToLocal < src > < localDest >

与-get相同

- moveToLocal < src > < localDest >

像-get一样工作，但在成功时删除HDFS副本。

- mkdir < path >

在HDFS中创建一个名为path的目录。 

创建路径中缺少的任何父目录（例如，Linux中的mkdir -p）。

- setrep [-R] [-w] rep < path >

将路径所标识的文件的目标复制因子设置为rep。（实际复制因子将随着时间向目标移动）

- touchz < path >

在包含当前时间的路径上创建一个文件作为时间戳。如果文件在路径中已存在，则失败，除非文件已经为0。

- test -[ezd] < path >

如果路径存在则返回1;具有零长度;或者是目录，否则为0。

- stat [format] < path >

打印有关路径的信息。格式是接受块（％b），文件名（％n），块大小（％o），复制（％r）和修改日期（％y，％Y）中的文件大小的字符串。

- tail [-f] < file2name >

在stdout上显示最后1KB的文件。

- chmod [-R] mode,mode,... < path >...

更改与由路径标识的一个或多个对象关联的文件权限...使用R.模式递归执行更改是一个3位八进制模式，或{augo} +/- {rwxX}。假设没有指定范围，并且不应用umask。

- chown [-R] [owner][:[group]] < path >...

设置由路径标识的文件或目录的所属用户和/或组...如果指定了-R，则递归地设置所有者。

- chgrp [-R] group < path > ... 

设置由path ...标识的文件或目录的所属组。如果指定了-R，则以递归方式设置组。

- help < cmd-name > 返回上面列出的某个命令的用法信息。您必须在cmd中省略前导' - '字符。