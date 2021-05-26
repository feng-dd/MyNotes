## 实际项目本地GC调优

> 电脑环境
>
> 1. 系统：win10
> 2. 内存：16G
> 3. JDK版本：1.8

### 第一次，默认启动

#### JVM默认参数

```properties
-XX:-BytecodeVerificationLocal 
-XX:-BytecodeVerificationRemote
-XX:InitialHeapSize=266456256 	# 初始堆内存约254M
-XX:+ManagementServer 
-XX:MaxHeapSize=4263300096 		# 最大堆内存约 4G
-XX:MaxNewSize=697933824 		# 最大新生代约665M
-XX:MaxTenuringThreshold=6 		# 新生代经历6次回收后，进入老年代
-XX:OldPLABSize=16 
#打印GC日志
-XX:+PrintGC 					
-XX:+PrintGCDetails 
-XX:+PrintGCTimeStamps 

-XX:TieredStopAtLevel=1 
-XX:+UseCompressedClassPointers 
-XX:+UseCompressedOops 			# 系统默认，启用指针压缩：压缩 静态变量、成员变量、对象数组元素 的指针，增加内存使用的效率
-XX:+UseConcMarkSweepGC 		# 老年代使用 CMS 垃圾收集器
-XX:-UseLargePagesIndividualAllocation 
-XX:+UseParNewGC 				# 老年代使用 ParNew 垃圾收集器
```

#### GC日志

```verilog
2.896: [GC (Allocation Failure) 2.899: [ParNew: 69952K->6133K(78656K), 0.0050527 secs] 69952K->6133K(253440K), 0.0085079 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
3.357: [GC (Allocation Failure) 3.357: [ParNew: 76072K->5571K(78656K), 0.0197450 secs] 76072K->9071K(253440K), 0.0198194 secs] [Times: user=0.11 sys=0.01, real=0.02 secs] 
3.635: [GC (Allocation Failure) 3.635: [ParNew: 75523K->4128K(78656K), 0.0038181 secs] 79023K->7628K(253440K), 0.0038928 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
3.913: [GC (Allocation Failure) 3.913: [ParNew: 74080K->5520K(78656K), 0.0036720 secs] 77580K->9020K(253440K), 0.0037363 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
3.917: [GC (CMS Initial Mark) [1 CMS-initial-mark: 3499K(174784K)] 10489K(253440K), 0.0007645 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
3.918: [CMS-concurrent-mark-start]
3.923: [CMS-concurrent-mark: 0.005/0.005 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
3.923: [CMS-concurrent-preclean-start]
3.924: [CMS-concurrent-preclean: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
3.924: [CMS-concurrent-abortable-preclean-start]
4.072: [CMS-concurrent-abortable-preclean: 0.100/0.149 secs] [Times: user=0.44 sys=0.03, real=0.15 secs] 
4.073: [GC (CMS Final Remark) [YG occupancy: 49666 K (78656 K)]4.073: [Rescan (parallel) , 0.0030748 secs]4.076: [weak refs processing, 0.0004944 secs]4.076: [class unloading, 0.0019730 secs]4.078: [scrub symbol table, 0.0032724 secs]4.081: [scrub string table, 0.0003950 secs][1 CMS-remark: 3499K(174784K)] 53165K(253440K), 0.0095907 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
4.082: [CMS-concurrent-sweep-start]
4.084: [CMS-concurrent-sweep: 0.002/0.002 secs] [Times: user=0.05 sys=0.00, real=0.00 secs] 
4.084: [CMS-concurrent-reset-start]
4.104: [CMS-concurrent-reset: 0.020/0.020 secs] [Times: user=0.03 sys=0.03, real=0.02 secs] 
4.167: [GC (Allocation Failure) 4.167: [ParNew: 75472K->6526K(78656K), 0.0052786 secs] 78950K->10940K(253440K), 0.0053510 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
18.462: [GC (Allocation Failure) 18.462: [ParNew: 76478K->5175K(78656K), 0.0055365 secs] 80892K->11390K(253440K), 0.0055951 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
18.968: [GC (Allocation Failure) 18.968: [ParNew: 75127K->8023K(78656K), 0.0042879 secs] 81342K->14237K(253440K), 0.0043431 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
19.303: [GC (Allocation Failure) 19.303: [ParNew: 77975K->8704K(78656K), 0.0149873 secs] 84189K->22257K(253440K), 0.0150443 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
19.693: [GC (Allocation Failure) 19.693: [ParNew: 78656K->7709K(78656K), 0.0146775 secs] 92209K->31341K(253440K), 0.0147336 secs] [Times: user=0.11 sys=0.01, real=0.02 secs] 
20.050: [GC (Allocation Failure) 20.050: [ParNew: 77661K->5056K(78656K), 0.0026867 secs] 101293K->28688K(253440K), 0.0027461 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
20.475: [GC (Allocation Failure) 20.475: [ParNew: 75008K->8704K(78656K), 0.0072967 secs] 98640K->34543K(253440K), 0.0073572 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
20.848: [GC (Allocation Failure) 20.848: [ParNew: 78656K->8704K(78656K), 0.0107909 secs] 104495K->39370K(253440K), 0.0108523 secs] [Times: user=0.11 sys=0.01, real=0.01 secs] 
21.151: [GC (Allocation Failure) 21.151: [ParNew: 78656K->8704K(78656K), 0.0119938 secs] 109322K->44429K(253440K), 0.0120514 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
21.370: [GC (Allocation Failure) 21.370: [ParNew: 78656K->7403K(78656K), 0.0119205 secs] 114381K->52071K(253440K), 0.0119935 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
21.778: [GC (Allocation Failure) 21.778: [ParNew: 77355K->5286K(78656K), 0.0029673 secs] 122023K->49955K(253440K), 0.0030263 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
22.094: [GC (Allocation Failure) 22.094: [ParNew: 75238K->5953K(78656K), 0.0033012 secs] 119907K->50622K(253440K), 0.0033620 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
23.419: [GC (Allocation Failure) 23.419: [ParNew: 75905K->7917K(78656K), 0.0054701 secs] 120574K->54245K(253440K), 0.0055351 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
23.424: [GC (CMS Initial Mark) [1 CMS-initial-mark: 46327K(174784K)] 55963K(253440K), 0.0012241 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
```

#### 分析GC日志

1. 在启动时，就已经开始频繁GC
2. 新生代GC：一秒n次
3. 老年代GC：十几秒一次

#### 结论

> 由于新生代、老年代都频繁GC，我认为原因是因为内存根本不足导致的

### 第二次

#### JVM参数

```properties
# 改动的参数
-XX:InitialHeapSize=2147483648 	# 初始堆内存2G，可以直接使用2G
-XX:MaxHeapSize=2G 				# 最大堆内存2G
-XX:MaxNewSize=1073741824 		# 新生代最大内存1G
-XX:NewSize=1G 					# 新生代内存1G
```

#### GC日志

```verilog
4.495: [GC (CMS Initial Mark) [1 CMS-initial-mark: 0K(1048576K)] 553709K(1992320K), 0.0598327 secs] [Times: user=0.14 sys=0.00, real=0.06 secs] 
4.555: [CMS-concurrent-mark-start]
4.555: [CMS-concurrent-mark: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
4.555: [CMS-concurrent-preclean-start]
4.557: [CMS-concurrent-preclean: 0.002/0.002 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
4.557: [CMS-concurrent-abortable-preclean-start]
 CMS: abort preclean due to time 9.624: [CMS-concurrent-abortable-preclean: 0.195/5.066 secs] [Times: user=0.30 sys=0.02, real=5.07 secs] 
9.624: [GC (CMS Final Remark) [YG occupancy: 721491 K (943744 K)]9.624: [Rescan (parallel) , 0.0298023 secs]9.654: [weak refs processing, 0.0002541 secs]9.654: [class unloading, 0.0021579 secs]9.656: [scrub symbol table, 0.0037543 secs]9.660: [scrub string table, 0.0003183 secs][1 CMS-remark: 0K(1048576K)] 721491K(1992320K), 0.0367262 secs] [Times: user=0.22 sys=0.00, real=0.04 secs] 
9.661: [CMS-concurrent-sweep-start]
9.661: [CMS-concurrent-sweep: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
9.661: [CMS-concurrent-reset-start]
9.667: [CMS-concurrent-reset: 0.006/0.006 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
13.578: [GC (Allocation Failure) 13.578: [ParNew: 838912K->20187K(943744K), 0.0205052 secs] 838912K->20187K(1992320K), 0.0205735 secs] [Times: user=0.02 sys=0.00, real=0.02 secs] 
21.236: [GC (Allocation Failure) 21.236: [ParNew: 859099K->58152K(943744K), 0.0455801 secs] 859099K->58152K(1992320K), 0.0456526 secs] [Times: user=0.23 sys=0.02, real=0.05 secs] 
25.281: [GC (CMS Initial Mark) [1 CMS-initial-mark: 0K(1048576K)] 548554K(1992320K), 0.1071698 secs] [Times: user=0.30 sys=0.00, real=0.11 secs] 
25.389: [CMS-concurrent-mark-start]
25.389: [CMS-concurrent-mark: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
25.389: [CMS-concurrent-preclean-start]
25.391: [CMS-concurrent-preclean: 0.002/0.002 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
25.391: [CMS-concurrent-abortable-preclean-start]
29.284: [GC (Allocation Failure) 29.284: [ParNew: 897064K->75508K(943744K), 0.0616487 secs] 897064K->83175K(1992320K), 0.0617167 secs] [Times: user=0.34 sys=0.03, real=0.06 secs] 
 CMS: abort preclean due to time 30.400: [CMS-concurrent-abortable-preclean: 4.256/5.009 secs] [Times: user=8.91 sys=1.91, real=5.01 secs] 
30.400: [GC (CMS Final Remark) [YG occupancy: 160490 K (943744 K)]30.400: [Rescan (parallel) , 0.0196591 secs]30.420: [weak refs processing, 0.0006107 secs]30.421: [class unloading, 0.0055093 secs]30.426: [scrub symbol table, 0.0121032 secs]30.438: [scrub string table, 0.0007550 secs][1 CMS-remark: 7666K(1048576K)] 168157K(1992320K), 0.0402420 secs] [Times: user=0.25 sys=0.01, real=0.04 secs] 
30.441: [CMS-concurrent-sweep-start]
30.444: [CMS-concurrent-sweep: 0.003/0.003 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
30.444: [CMS-concurrent-reset-start]
30.445: [CMS-concurrent-reset: 0.002/0.002 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
57.028: [GC (Allocation Failure) 57.028: [ParNew: 914420K->66531K(943744K), 0.0965155 secs] 922087K->112765K(1992320K), 0.0966245 secs] [Times: user=0.53 sys=0.02, real=0.10 secs] 
62.915: [GC (Allocation Failure) 62.915: [ParNew: 905443K->61744K(943744K), 0.0254667 secs] 951677K->107978K(1992320K), 0.0255490 secs] [Times: user=0.25 sys=0.00, real=0.03 secs] 
70.743: [GC (Allocation Failure) 70.743: [ParNew: 900656K->89249K(943744K), 0.0320668 secs] 946890K->135483K(1992320K), 0.0321436 secs] [Times: user=0.23 sys=0.02, real=0.03 secs] 
70.776: [GC (CMS Initial Mark) [1 CMS-initial-mark: 46234K(1048576K)] 146087K(1992320K), 0.0104003 secs] [Times: user=0.13 sys=0.00, real=0.01 secs] 
```

#### 分析GC日志

第二次我将堆内存整体调整为2G，新生代设为1G

1. 在启动时，就已经开始频繁GC，但相对好于默认参数的时候
2. 新生代GC：基本上没十几秒产生一两次、相比第一次减少了GC的频率
3. 老年代GC：十几秒1次、与第一次GC频率几乎一致

#### 结论

> 第一次JVM最大堆内存约4G，新生代最大为600多M，老年代最大就是3G多；
>
> 第二次我将JVM最大堆内存设为2G，新生代初始 和最大都设为1G，老年大最大则约为1G；
>
> 新生代内存变大之后，新生代GC确实减少了；
>
> 老年代最大内存变小，老年代GC未变；
>
> 我认为老年代不是因为堆内存的原因，网上查到可能是因为JDK1.8引入的 `metaspace`（元空间）内存过小的问题

### 第三次

#### JVM参数

```properties
# 新增参数
-XX:MetaspaceSize=33554432 				# 元空间大小 32M
-XX:PretenureSizeThreshold=10485760 	# 大对象阈值 10M
-XX:+PrintGCDateStamps 					# 打印日期时间戳
-XX:+PrintHeapAtGC						# 打印堆内存信息
```

#### GC日志

```verilog
2020-12-09T17:09:49.109+0800: 13.548: [GC (Allocation Failure) 2020-12-09T17:09:49.114+0800: 13.549: [ParNew: 838912K->20187K(943744K), 0.0407911 secs] 838912K->20187K(1992320K), 0.0450397 secs] [Times: user=0.16 sys=0.00, real=0.05 secs] 
Heap after GC invocations=1 (full 0):
 par new generation   total 943744K, used 20187K [0x0000000080000000, 0x00000000c0000000, 0x00000000c0000000)
  eden space 838912K,   0% used [0x0000000080000000, 0x0000000080000000, 0x00000000b3340000)
  from space 104832K,  19% used [0x00000000b99a0000, 0x00000000bad56c90, 0x00000000c0000000)
  to   space 104832K,   0% used [0x00000000b3340000, 0x00000000b3340000, 0x00000000b99a0000)
 concurrent mark-sweep generation total 1048576K, used 0K [0x00000000c0000000, 0x0000000100000000, 0x0000000100000000)
 Metaspace       used 26408K, capacity 27114K, committed 27264K, reserved 1073152K
  class space    used 3411K, capacity 3587K, committed 3712K, reserved 1048576K
}
{Heap before GC invocations=1 (full 0):
 par new generation   total 943744K, used 859099K [0x0000000080000000, 0x00000000c0000000, 0x00000000c0000000)
  eden space 838912K, 100% used [0x0000000080000000, 0x00000000b3340000, 0x00000000b3340000)
  from space 104832K,  19% used [0x00000000b99a0000, 0x00000000bad56c90, 0x00000000c0000000)
  to   space 104832K,   0% used [0x00000000b3340000, 0x00000000b3340000, 0x00000000b99a0000)
 concurrent mark-sweep generation total 1048576K, used 0K [0x00000000c0000000, 0x0000000100000000, 0x0000000100000000)
 Metaspace       used 37637K, capacity 38630K, committed 38744K, reserved 1083392K
  class space    used 4740K, capacity 4985K, committed 4988K, reserved 1048576K
2020-12-09T17:09:56.611+0800: 21.046: [GC (Allocation Failure) 2020-12-09T17:09:56.611+0800: 21.046: [ParNew: 859099K->57420K(943744K), 0.0361186 secs] 859099K->57420K(1992320K), 0.0361863 secs] [Times: user=0.13 sys=0.01, real=0.04 secs] 
Heap after GC invocations=2 (full 0):
 par new generation   total 943744K, used 57420K [0x0000000080000000, 0x00000000c0000000, 0x00000000c0000000)
  eden space 838912K,   0% used [0x0000000080000000, 0x0000000080000000, 0x00000000b3340000)
  from space 104832K,  54% used [0x00000000b3340000, 0x00000000b6b533a8, 0x00000000b99a0000)
  to   space 104832K,   0% used [0x00000000b99a0000, 0x00000000b99a0000, 0x00000000c0000000)
 concurrent mark-sweep generation total 1048576K, used 0K [0x00000000c0000000, 0x0000000100000000, 0x0000000100000000)
 Metaspace       used 37637K, capacity 38630K, committed 38744K, reserved 1083392K
  class space    used 4740K, capacity 4985K, committed 4988K, reserved 1048576K
}
2020-12-09T17:09:56.647+0800: 21.083: [GC (CMS Initial Mark) [1 CMS-initial-mark: 0K(1048576K)] 69256K(1992320K), 0.0073224 secs] [Times: user=0.13 sys=0.00, real=0.01 secs] 
2020-12-09T17:09:56.654+0800: 21.090: [CMS-concurrent-mark-start]
2020-12-09T17:09:56.655+0800: 21.091: [CMS-concurrent-mark: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-12-09T17:09:56.655+0800: 21.091: [CMS-concurrent-preclean-start]
2020-12-09T17:09:56.657+0800: 21.093: [CMS-concurrent-preclean: 0.002/0.002 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-12-09T17:09:56.657+0800: 21.093: [CMS-concurrent-abortable-preclean-start]
2020-12-09T17:10:00.134+0800: 24.570: [CMS-concurrent-abortable-preclean: 2.768/3.477 secs] [Times: user=5.73 sys=0.58, real=3.48 secs] 
2020-12-09T17:10:00.134+0800: 24.570: [GC (CMS Final Remark) [YG occupancy: 498757 K (943744 K)]2020-12-09T17:10:00.134+0800: 24.570: [Rescan (parallel) , 0.0975879 secs]2020-12-09T17:10:00.232+0800: 24.668: [weak refs processing, 0.0003669 secs]2020-12-09T17:10:00.232+0800: 24.668: [class unloading, 0.0045820 secs]2020-12-09T17:10:00.237+0800: 24.673: [scrub symbol table, 0.0095333 secs]2020-12-09T17:10:00.246+0800: 24.682: [scrub string table, 0.0004581 secs][1 CMS-remark: 0K(1048576K)] 498757K(1992320K), 0.1137508 secs] [Times: user=0.83 sys=0.00, real=0.11 secs] 
2020-12-09T17:10:00.248+0800: 24.684: [CMS-concurrent-sweep-start]
2020-12-09T17:10:00.248+0800: 24.684: [CMS-concurrent-sweep: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-12-09T17:10:00.248+0800: 24.684: [CMS-concurrent-reset-start]
2020-12-09T17:10:00.253+0800: 24.689: [CMS-concurrent-reset: 0.006/0.006 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
{Heap before GC invocations=2 (full 1):
 par new generation   total 943744K, used 896332K [0x0000000080000000, 0x00000000c0000000, 0x00000000c0000000)
  eden space 838912K, 100% used [0x0000000080000000, 0x00000000b3340000, 0x00000000b3340000)
  from space 104832K,  54% used [0x00000000b3340000, 0x00000000b6b533a8, 0x00000000b99a0000)
  to   space 104832K,   0% used [0x00000000b99a0000, 0x00000000b99a0000, 0x00000000c0000000)
 concurrent mark-sweep generation total 1048576K, used 0K [0x00000000c0000000, 0x0000000100000000, 0x0000000100000000)
 Metaspace       used 54153K, capacity 56172K, committed 56508K, reserved 1099776K
  class space    used 6689K, capacity 7075K, committed 7156K, reserved 1048576K
2020-12-09T17:10:03.507+0800: 27.943: [GC (Allocation Failure) 2020-12-09T17:10:03.507+0800: 27.943: [ParNew: 896332K->71392K(943744K), 0.0617200 secs] 896332K->79091K(1992320K), 0.0617939 secs] [Times: user=0.34 sys=0.02, real=0.06 secs] 
Heap after GC invocations=3 (full 1):
 par new generation   total 943744K, used 71392K [0x0000000080000000, 0x00000000c0000000, 0x00000000c0000000)
  eden space 838912K,   0% used [0x0000000080000000, 0x0000000080000000, 0x00000000b3340000)
  from space 104832K,  68% used [0x00000000b99a0000, 0x00000000bdf582f0, 0x00000000c0000000)
  to   space 104832K,   0% used [0x00000000b3340000, 0x00000000b3340000, 0x00000000b99a0000)
 concurrent mark-sweep generation total 1048576K, used 7698K [0x00000000c0000000, 0x0000000100000000, 0x0000000100000000)
 Metaspace       used 54153K, capacity 56172K, committed 56508K, reserved 1099776K
  class space    used 6689K, capacity 7075K, committed 7156K, reserved 1048576K
}
{Heap before GC invocations=3 (full 1):
 par new generation   total 943744K, used 910304K [0x0000000080000000, 0x00000000c0000000, 0x00000000c0000000)
  eden space 838912K, 100% used [0x0000000080000000, 0x00000000b3340000, 0x00000000b3340000)
  from space 104832K,  68% used [0x00000000b99a0000, 0x00000000bdf582f0, 0x00000000c0000000)
  to   space 104832K,   0% used [0x00000000b3340000, 0x00000000b3340000, 0x00000000b99a0000)
 concurrent mark-sweep generation total 1048576K, used 7698K [0x00000000c0000000, 0x0000000100000000, 0x0000000100000000)
 Metaspace       used 69814K, capacity 72475K, committed 72764K, reserved 1114112K
  class space    used 8358K, capacity 8804K, committed 8820K, reserved 1048576K
2020-12-09T17:10:27.566+0800: 52.005: [GC (Allocation Failure) 2020-12-09T17:10:27.566+0800: 52.005: [ParNew: 910304K->67260K(943744K), 0.0663694 secs] 918003K->113546K(1992320K), 0.0664575 secs] [Times: user=0.28 sys=0.05, real=0.07 secs] 
Heap after GC invocations=4 (full 1):
 par new generation   total 943744K, used 67260K [0x0000000080000000, 0x00000000c0000000, 0x00000000c0000000)
  eden space 838912K,   0% used [0x0000000080000000, 0x0000000080000000, 0x00000000b3340000)
  from space 104832K,  64% used [0x00000000b3340000, 0x00000000b74ef1a8, 0x00000000b99a0000)
  to   space 104832K,   0% used [0x00000000b99a0000, 0x00000000b99a0000, 0x00000000c0000000)
 concurrent mark-sweep generation total 1048576K, used 46286K [0x00000000c0000000, 0x0000000100000000, 0x0000000100000000)
 Metaspace       used 69814K, capacity 72475K, committed 72764K, reserved 1114112K
  class space    used 8358K, capacity 8804K, committed 8820K, reserved 1048576K
}
2020-12-09T17:10:31.632+0800: 56.071: [GC (CMS Initial Mark) [1 CMS-initial-mark: 46286K(1048576K)] 654991K(1992320K), 0.1086876 secs] [Times: user=0.23 sys=0.00, real=0.11 secs] 
2020-12-09T17:10:31.741+0800: 56.180: [CMS-concurrent-mark-start]
2020-12-09T17:10:31.780+0800: 56.219: [CMS-concurrent-mark: 0.040/0.040 secs] [Times: user=0.13 sys=0.00, real=0.04 secs] 
2020-12-09T17:10:31.780+0800: 56.219: [CMS-concurrent-preclean-start]
2020-12-09T17:10:31.782+0800: 56.221: [CMS-concurrent-preclean: 0.002/0.002 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-12-09T17:10:31.783+0800: 56.222: [CMS-concurrent-abortable-preclean-start]
{Heap before GC invocations=4 (full 2):
 par new generation   total 943744K, used 906172K [0x0000000080000000, 0x00000000c0000000, 0x00000000c0000000)
  eden space 838912K, 100% used [0x0000000080000000, 0x00000000b3340000, 0x00000000b3340000)
  from space 104832K,  64% used [0x00000000b3340000, 0x00000000b74ef1a8, 0x00000000b99a0000)
  to   space 104832K,   0% used [0x00000000b99a0000, 0x00000000b99a0000, 0x00000000c0000000)
 concurrent mark-sweep generation total 1048576K, used 46286K [0x00000000c0000000, 0x0000000100000000, 0x0000000100000000)
 Metaspace       used 82928K, capacity 86162K, committed 86256K, reserved 1126400K
  class space    used 9155K, capacity 9740K, committed 9788K, reserved 1048576K
2020-12-09T17:10:33.692+0800: 58.131: [GC (Allocation Failure) 2020-12-09T17:10:33.692+0800: 58.131: [ParNew: 906172K->66224K(943744K), 0.0865918 secs] 952458K->112510K(1992320K), 0.0867464 secs] [Times: user=0.70 sys=0.00, real=0.09 secs] 
Heap after GC invocations=5 (full 2):
 par new generation   total 943744K, used 66224K [0x0000000080000000, 0x00000000c0000000, 0x00000000c0000000)
  eden space 838912K,   0% used [0x0000000080000000, 0x0000000080000000, 0x00000000b3340000)
  from space 104832K,  63% used [0x00000000b99a0000, 0x00000000bda4c1d8, 0x00000000c0000000)
  to   space 104832K,   0% used [0x00000000b3340000, 0x00000000b3340000, 0x00000000b99a0000)
 concurrent mark-sweep generation total 1048576K, used 46286K [0x00000000c0000000, 0x0000000100000000, 0x0000000100000000)
 Metaspace       used 82928K, capacity 86162K, committed 86256K, reserved 1126400K
  class space    used 9155K, capacity 9740K, committed 9788K, reserved 1048576K
}
2020-12-09T17:10:36.746+0800: 61.185: [CMS-concurrent-abortable-preclean: 3.869/4.964 secs] [Times: user=8.03 sys=0.51, real=4.96 secs] 
2020-12-09T17:10:36.746+0800: 61.186: [GC (CMS Final Remark) [YG occupancy: 520515 K (943744 K)]2020-12-09T17:10:36.746+0800: 61.186: [Rescan (parallel) , 0.1200168 secs]2020-12-09T17:10:36.866+0800: 61.306: [weak refs processing, 0.0007180 secs]2020-12-09T17:10:36.867+0800: 61.307: [class unloading, 0.0119324 secs]2020-12-09T17:10:36.879+0800: 61.319: [scrub symbol table, 0.0160661 secs]2020-12-09T17:10:36.895+0800: 61.335: [scrub string table, 0.0011473 secs][1 CMS-remark: 46286K(1048576K)] 566801K(1992320K), 0.1517887 secs] [Times: user=0.89 sys=0.00, real=0.15 secs] 
2020-12-09T17:10:36.898+0800: 61.338: [CMS-concurrent-sweep-start]
2020-12-09T17:10:36.915+0800: 61.355: [CMS-concurrent-sweep: 0.017/0.017 secs] [Times: user=0.13 sys=0.00, real=0.02 secs] 
2020-12-09T17:10:36.915+0800: 61.355: [CMS-concurrent-reset-start]
2020-12-09T17:10:36.917+0800: 61.356: [CMS-concurrent-reset: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
{Heap before GC invocations=5 (full 2):
 par new generation   total 943744K, used 905136K [0x0000000080000000, 0x00000000c0000000, 0x00000000c0000000)
  eden space 838912K, 100% used [0x0000000080000000, 0x00000000b3340000, 0x00000000b3340000)
  from space 104832K,  63% used [0x00000000b99a0000, 0x00000000bda4c1d8, 0x00000000c0000000)
  to   space 104832K,   0% used [0x00000000b3340000, 0x00000000b3340000, 0x00000000b99a0000)
 concurrent mark-sweep generation total 1048576K, used 46253K [0x00000000c0000000, 0x0000000100000000, 0x0000000100000000)
 Metaspace       used 96524K, capacity 99972K, committed 100252K, reserved 1138688K
  class space    used 10163K, capacity 10767K, committed 10784K, reserved 1048576K
2020-12-09T17:10:40.849+0800: 65.289: [GC (Allocation Failure) 2020-12-09T17:10:40.849+0800: 65.289: [ParNew: 905136K->90857K(943744K), 0.0326292 secs] 951389K->137110K(1992320K), 0.0327037 secs] [Times: user=0.25 sys=0.00, real=0.03 secs] 
Heap after GC invocations=6 (full 2):
 par new generation   total 943744K, used 90857K [0x0000000080000000, 0x00000000c0000000, 0x00000000c0000000)
  eden space 838912K,   0% used [0x0000000080000000, 0x0000000080000000, 0x00000000b3340000)
  from space 104832K,  86% used [0x00000000b3340000, 0x00000000b8bfa5f0, 0x00000000b99a0000)
  to   space 104832K,   0% used [0x00000000b99a0000, 0x00000000b99a0000, 0x00000000c0000000)
 concurrent mark-sweep generation total 1048576K, used 46253K [0x00000000c0000000, 0x0000000100000000, 0x0000000100000000)
 Metaspace       used 96524K, capacity 99972K, committed 100252K, reserved 1138688K
  class space    used 10163K, capacity 10767K, committed 10784K, reserved 1048576K
}
{Heap before GC invocations=6 (full 2):
 par new generation   total 943744K, used 929769K [0x0000000080000000, 0x00000000c0000000, 0x00000000c0000000)
  eden space 838912K, 100% used [0x0000000080000000, 0x00000000b3340000, 0x00000000b3340000)
  from space 104832K,  86% used [0x00000000b3340000, 0x00000000b8bfa5f0, 0x00000000b99a0000)
  to   space 104832K,   0% used [0x00000000b99a0000, 0x00000000b99a0000, 0x00000000c0000000)
 concurrent mark-sweep generation total 1048576K, used 46253K [0x00000000c0000000, 0x0000000100000000, 0x0000000100000000)
 Metaspace       used 104097K, capacity 107968K, committed 108188K, reserved 1146880K
  class space    used 11118K, capacity 11746K, committed 11808K, reserved 1048576K
2020-12-09T17:10:50.510+0800: 74.951: [GC (Allocation Failure) 2020-12-09T17:10:50.510+0800: 74.951: [ParNew: 929769K->104832K(943744K), 0.0705490 secs] 976022K->171908K(1992320K), 0.0706773 secs] [Times: user=0.38 sys=0.00, real=0.07 secs] 
```

#### 分析GC日志

相比上次，我设置了元空间内存为32M

1. 在启动时，就已经开始频繁GC，与第二次类似
2. 新生代GC：基本上没十几秒产生一两次、与第二次类似
3. 老年代GC：四十秒一次、相比第二次减少了频率，说明元空间的内存是老年代GC的原因之一。

我又将元空间的大小调大为64M，启动时只产生了一次老年代GC。

调大为128M时，启动时没有老年代GC了。

#### 结论

> 1. FGC时，初始标记，老年代堆空间未使用，总共堆空间是1G，没有达到FGC默认92%的条件。
> 2. 老年代剩余堆空间是1G，堆内存总共使用了69M，老年代剩余内存 > 新生代对象总大小，担保可成功，FGC不是担保失败产生的。
>
> ```verilog
> Metaspace       used 26408K, capacity 27114K, committed 27264K, reserved 1073152K
> Metaspace       used 37637K, capacity 38630K, committed 38744K, reserved 1083392K # fgc前
> Metaspace       used 54153K, capacity 56172K, committed 56508K, reserved 1099776K # fgc后
> Metaspace       used 69814K, capacity 72475K, committed 72764K, reserved 1114112K
> 
> # 查看metaspace的默认值
> java -XX:+PrintFlagsInitial|grep Meta
> ```
>
> committed最后占用到了70M以上，所以元空间大小需要设置超过这个值。
>
> ![image-20201214160249921](https://i.loli.net/2021/01/28/BUPI871n5Rqdjmw.png)
>
> 1. used：metaspace已经占用的空间
> 2. capacity：metaspace已经占用的chunk空间
> 3. commited：已经分配给 metaspace 的空间，包括空闲的chunk
> 4. reserved：操作系统预留的所有空间

### 最后一次

#### JVM参数

```properties
# 改动或新增的参数
-XX:InitialHeapSize=4294967296 	# 初始堆内存4G
-XX:MaxHeapSize=4294967296 		# 最大堆内存4G
-XX:MaxNewSize=1073741824 		# 最大新生代1G
-XX:MetaspaceSize=134217728 	# 元空间 128M
-XX:SurvivorRatio=4 			# eden:survivor = 4

# 等价于
-Xms4096M # 初始堆内存4G
-Xmx4096M # 最大堆内存4G
-Xmn3072M # 新生代内存3G
-Xss1M	  # 栈内存1M
```

#### GC日志

```verilog
{Heap before GC invocations=0 (full 0):
 par new generation   total 2621440K, used 2097152K [0x00000006c0000000, 0x0000000780000000, 0x0000000780000000)
  eden space 2097152K, 100% used [0x00000006c0000000, 0x0000000740000000, 0x0000000740000000)
  from space 524288K,   0% used [0x0000000740000000, 0x0000000740000000, 0x0000000760000000)
  to   space 524288K,   0% used [0x0000000760000000, 0x0000000760000000, 0x0000000780000000)
 concurrent mark-sweep generation total 1048576K, used 0K [0x0000000780000000, 0x00000007c0000000, 0x00000007c0000000)
 Metaspace       used 27076K, capacity 27818K, committed 28160K, reserved 1073152K
  class space    used 3499K, capacity 3715K, committed 3840K, reserved 1048576K
17.003: [GC (Allocation Failure) 17.003: [ParNew: 2097152K->20762K(2621440K), 0.0215685 secs] 2097152K->20762K(3670016K), 0.0218876 secs] [Times: user=0.13 sys=0.02, real=0.02 secs] 
Heap after GC invocations=1 (full 0):
 par new generation   total 2621440K, used 20762K [0x00000006c0000000, 0x0000000780000000, 0x0000000780000000)
  eden space 2097152K,   0% used [0x00000006c0000000, 0x00000006c0000000, 0x0000000740000000)
  from space 524288K,   3% used [0x0000000760000000, 0x00000007614469e8, 0x0000000780000000)
  to   space 524288K,   0% used [0x0000000740000000, 0x0000000740000000, 0x0000000760000000)
 concurrent mark-sweep generation total 1048576K, used 0K [0x0000000780000000, 0x00000007c0000000, 0x00000007c0000000)
 Metaspace       used 27076K, capacity 27818K, committed 28160K, reserved 1073152K
  class space    used 3499K, capacity 3715K, committed 3840K, reserved 1048576K
}
{Heap before GC invocations=1 (full 0):
 par new generation   total 2621440K, used 2117914K [0x00000006c0000000, 0x0000000780000000, 0x0000000780000000)
  eden space 2097152K, 100% used [0x00000006c0000000, 0x0000000740000000, 0x0000000740000000)
  from space 524288K,   3% used [0x0000000760000000, 0x00000007614469e8, 0x0000000780000000)
  to   space 524288K,   0% used [0x0000000740000000, 0x0000000740000000, 0x0000000760000000)
 concurrent mark-sweep generation total 1048576K, used 0K [0x0000000780000000, 0x00000007c0000000, 0x00000007c0000000)
 Metaspace       used 61344K, capacity 63630K, committed 63872K, reserved 1105920K
  class space    used 7577K, capacity 8000K, committed 8064K, reserved 1048576K
30.076: [GC (Allocation Failure) 30.076: [ParNew: 2117914K->98746K(2621440K), 0.0760345 secs] 2117914K->98746K(3670016K), 0.0761072 secs] [Times: user=0.30 sys=0.06, real=0.08 secs] 
Heap after GC invocations=2 (full 0):
 par new generation   total 2621440K, used 98746K [0x00000006c0000000, 0x0000000780000000, 0x0000000780000000)
  eden space 2097152K,   0% used [0x00000006c0000000, 0x00000006c0000000, 0x0000000740000000)
  from space 524288K,  18% used [0x0000000740000000, 0x000000074606e8c0, 0x0000000760000000)
  to   space 524288K,   0% used [0x0000000760000000, 0x0000000760000000, 0x0000000780000000)
 concurrent mark-sweep generation total 1048576K, used 0K [0x0000000780000000, 0x00000007c0000000, 0x00000007c0000000)
 Metaspace       used 61344K, capacity 63630K, committed 63872K, reserved 1105920K
  class space    used 7577K, capacity 8000K, committed 8064K, reserved 1048576K
}
{Heap before GC invocations=2 (full 0):
 par new generation   total 2621440K, used 2195898K [0x00000006c0000000, 0x0000000780000000, 0x0000000780000000)
  eden space 2097152K, 100% used [0x00000006c0000000, 0x0000000740000000, 0x0000000740000000)
  from space 524288K,  18% used [0x0000000740000000, 0x000000074606e8c0, 0x0000000760000000)
  to   space 524288K,   0% used [0x0000000760000000, 0x0000000760000000, 0x0000000780000000)
 concurrent mark-sweep generation total 1048576K, used 0K [0x0000000780000000, 0x00000007c0000000, 0x00000007c0000000)
 Metaspace       used 85726K, capacity 88977K, committed 89088K, reserved 1128448K
  class space    used 9347K, capacity 9913K, committed 9984K, reserved 1048576K
60.856: [GC (Allocation Failure) 60.857: [ParNew: 2195898K->122756K(2621440K), 0.0789503 secs] 2195898K->122756K(3670016K), 0.0791107 secs] [Times: user=0.42 sys=0.06, real=0.08 secs] 
Heap after GC invocations=3 (full 0):
 par new generation   total 2621440K, used 122756K [0x00000006c0000000, 0x0000000780000000, 0x0000000780000000)
  eden space 2097152K,   0% used [0x00000006c0000000, 0x00000006c0000000, 0x0000000740000000)
  from space 524288K,  23% used [0x0000000760000000, 0x00000007677e1030, 0x0000000780000000)
  to   space 524288K,   0% used [0x0000000740000000, 0x0000000740000000, 0x0000000760000000)
 concurrent mark-sweep generation total 1048576K, used 0K [0x0000000780000000, 0x00000007c0000000, 0x00000007c0000000)
 Metaspace       used 85726K, capacity 88977K, committed 89088K, reserved 1128448K
  class space    used 9347K, capacity 9913K, committed 9984K, reserved 1048576K
}
{Heap before GC invocations=3 (full 0):
 par new generation   total 2621440K, used 2219908K [0x00000006c0000000, 0x0000000780000000, 0x0000000780000000)
  eden space 2097152K, 100% used [0x00000006c0000000, 0x0000000740000000, 0x0000000740000000)
  from space 524288K,  23% used [0x0000000760000000, 0x00000007677e1030, 0x0000000780000000)
  to   space 524288K,   0% used [0x0000000740000000, 0x0000000740000000, 0x0000000760000000)
 concurrent mark-sweep generation total 1048576K, used 0K [0x0000000780000000, 0x00000007c0000000, 0x00000007c0000000)
 Metaspace       used 108798K, capacity 113419K, committed 113664K, reserved 1150976K
  class space    used 11670K, capacity 12426K, committed 12544K, reserved 1048576K
80.954: [GC (Allocation Failure) 80.954: [ParNew: 2219908K->176555K(2621440K), 0.0869319 secs] 2219908K->176555K(3670016K), 0.0870648 secs] [Times: user=0.58 sys=0.08, real=0.09 secs] 
Heap after GC invocations=4 (full 0):
 par new generation   total 2621440K, used 176555K [0x00000006c0000000, 0x0000000780000000, 0x0000000780000000)
  eden space 2097152K,   0% used [0x00000006c0000000, 0x00000006c0000000, 0x0000000740000000)
  from space 524288K,  33% used [0x0000000740000000, 0x000000074ac6add8, 0x0000000760000000)
  to   space 524288K,   0% used [0x0000000760000000, 0x0000000760000000, 0x0000000780000000)
 concurrent mark-sweep generation total 1048576K, used 0K [0x0000000780000000, 0x00000007c0000000, 0x00000007c0000000)
 Metaspace       used 108798K, capacity 113419K, committed 113664K, reserved 1150976K
  class space    used 11670K, capacity 12426K, committed 12544K, reserved 1048576K
}
{Heap before GC invocations=4 (full 0):
 par new generation   total 2621440K, used 2273707K [0x00000006c0000000, 0x0000000780000000, 0x0000000780000000)
  eden space 2097152K, 100% used [0x00000006c0000000, 0x0000000740000000, 0x0000000740000000)
  from space 524288K,  33% used [0x0000000740000000, 0x000000074ac6add8, 0x0000000760000000)
  to   space 524288K,   0% used [0x0000000760000000, 0x0000000760000000, 0x0000000780000000)
 concurrent mark-sweep generation total 1048576K, used 0K [0x0000000780000000, 0x00000007c0000000, 0x00000007c0000000)
 Metaspace       used 111726K, capacity 116557K, committed 116864K, reserved 1153024K
  class space    used 12007K, capacity 12809K, committed 12928K, reserved 1048576K
551.826: [GC (Allocation Failure) 551.826: [ParNew: 2273707K->228525K(2621440K), 0.0903694 secs] 2273707K->228525K(3670016K), 0.0905120 secs] [Times: user=0.63 sys=0.01, real=0.09 secs] 
Heap after GC invocations=5 (full 0):
 par new generation   total 2621440K, used 228525K [0x00000006c0000000, 0x0000000780000000, 0x0000000780000000)
  eden space 2097152K,   0% used [0x00000006c0000000, 0x00000006c0000000, 0x0000000740000000)
  from space 524288K,  43% used [0x0000000760000000, 0x000000076df2b798, 0x0000000780000000)
  to   space 524288K,   0% used [0x0000000740000000, 0x0000000740000000, 0x0000000760000000)
 concurrent mark-sweep generation total 1048576K, used 0K [0x0000000780000000, 0x00000007c0000000, 0x00000007c0000000)
 Metaspace       used 111726K, capacity 116557K, committed 116864K, reserved 1153024K
  class space    used 12007K, capacity 12809K, committed 12928K, reserved 1048576K
}
```

#### 分析GC日志

相比上次，我调大了堆内存为4G、新生代为1G、元空间内存为128M

1. 在启动时，只剩下新生代GC、相比之前GC次数少了很多
2. 新生代GC：启动时，每二十秒产生一次；启动后，十分钟一次
3. 老年代GC：不存在

### 总结

GC调优时，需要通过GC日志去分析性能GC频发的问题是新生代还是老年代，根据不同区域做出合理的调整。

新生代GC频率过高时，建议调大新生代的内存大小。

老年代GC频率过高时，判断具体情况：

1. 新生代、老年代内存同时堪忧时，应该分配更大的堆内存；
2. 伴随新生代GC，survivor区频繁撑满，应该调小Eden区与Survivor区的比例；
3. 当有对象多次逃避新生代GC，动态年龄判断频发且老年代内存足够使用时，可以调低对象晋升年龄；

原则上：

1. 避免 full GC，控制 Yong GC 的频率。
2. 短命对象尽量在YGC回收，长期存活对象尽早进入老年代。
3. 系统响应时间敏感、大内存的项目使用G1垃圾回收器。

### 问题

#### 1. 项目提升十倍百倍带来的问题

公司的运营很牛，过了一段时间，系统负载增加了10倍，100倍： 
方案1：增加服务器数量 根据系统负载的增比，同比增加机器数量，机器配置，和jvm的配置可以保持不变。 
方案2：使用更高配置的机器 更高的配置，意味着更快速的处理速度和更大的内存。响应时间敏感且内存需求大的使
用G1回收器 这时候需要和‘项目上线初期’一样，合理的使用配置和分配内存

#### 2. 元空间

Java8 取消了PermGen。取而代之的是MetaSpace，方法区在java8以后移至MetaSpace。 Jdk8开始把类的元数据
放到本地内存（native heap），称之为MetaSpace
理论上本地内存剩余多少，MetaSpace就有多大，当然我们也不可能无限制的增大MetaSpace，需要用-
XX:MaxMetaSpaceSize来指定MetaSpace区域大小。 
关于used capacity commited 和reserved，在stackoverflow找到个比较靠谱的答案，我尝试翻译一下：
MetaSpace由一个或多个Virtual Space（虚拟空间）组成。虚拟空间是操作系统的连续存储空间，虚拟空间是按需分
配的。当被分配时，虚拟空间会向操作系统预留（reserve）空间，但还没有被提交（committed）。
MetaSpace的预留空间（reserved）是全部虚拟空间的大小。 虚拟空间的最小分配单元是MetaChunk（也可以说是
Chunk）。
当新的Chunk被分配至虚拟空间时，与Chunk相关的内存空间被提交了（committed）。MetaSpace的committed
指的是所有Chunk占有的空间。 
每个Chunk占据空间不同，当一个类加载器（Class Loader）被gc时，所有与之关联的Chunk被释放（freed）。这些
被释放的Chunk被维护在一个全局的释放数组里。
MetaSpace的capacity指的是所有未被释放的Chunk占据的空间。 这么看gc日志发现自己committed是4864K，
capacity4486K。有一部分的Chunk已经被释放了，代表有类加载器被回收了

##### 参考

> https://stackoverflow.com/questions/40891433/understanding-metaspace-line-in-jvm-heap-printout 

## 课件案例总结

### 1. 每秒十万QPS社交APP优化性能

#### 案例背景

> 社交APP客户最常见的操作之一是查看某人的主页，高峰期会有许多查看他人主页的请求，个人主页的数据量通常还很多，照片、心情、个人信息等，可能是几MB的数据。这类数据一般缓存在redis中。
>
> ![image-20201221153904952](https://i.loli.net/2021/01/28/s8kuqXP23nwGLyx.png)

#### 优化分析

> 高峰期，高并发情况下
>
> 1. 请求多，对象创建频繁，新生代Eden区会快速充满，导致Young GC频繁。
> 2. 服务器负载高，处理请求的效率降低，Young GC后存活对象变多。
> 3. 以上会导致对象快速进入老年代，老年代 GC 变频繁。

#### 优化策略

> 1. 由于高峰期并发量激增导致，应该添加服务器分摊请求，降低单个服务器的压力。
> 2. 增加 survivor 区内存，尽量保证GC后的存活对象进入survivor 区，避免进入老年代。
> 3. 由于老年代回收器CMS使用标记清理，造成老年代内存存在大量内存碎片，进而导致大对象无法放入老年代，从而导致FGC；`-XX:+UseCMSCompactAtFullCollection -XX:CMSFullGCsBeforeCompaction=0`开启FGC后压缩整理，频率为每次FGC后都进行；如果设置为多次FGC进行一次压缩整理，那么在两次压缩整理之间的FGC会越来越频繁，每次FGC后的内存碎片越来越多，可用的连续内存空间越来越少。

### 2.新手工程师设置了一个jvm参数

#### 案例背景

>  某天团队里一个新手工程师大概是心血来潮，自作主张设置了一个JVM参数。导致频繁FGC。
>
> 通过GC日志发现在日志里，看到了一个`Metadata GC Threshold`的字样，类似于如下日志：
> `【Full GC（Metadata GC Threshold）xxxxx, xxxxx】`

####  优化分析

> 从日志分析是因为`MetaSpace`被塞满导致的FGC。`MetaSpace`用来存放类信息。
>
> 通过`jstat`命令可以查看堆内存各部分的使用详情和加载类的数量。
>
> 通过添加JVM启动参数`-XX:TraceClassLoading -XX:TraceClassUnloading`查看加载类和卸载类的情况。里面显示类似如下的内容：
> `【Loaded sun.reflect.GeneratedSerializationConstructorAccessor from __JVM_Defined_Class】`，在JVM运行期间不停加载；
>
> 该类是由系统中反射调用一定次数的类方法后，JVM动态生成的类，是JVM底层优化机制。
>
> 所有类都是Class类的对象，GeneratedSerializationConstructorAccessor类是Class类的对象，且为SoftReference(软引用：在内存不足时，会回收)对象。
>
> 软引用回收的公式：`clock - timestamp <= freespace * SoftRefLRUPolicyMSPerMB`，`clock - timestamp` 一个软引用对象多久未被调用，`freespace `JVM空闲内存空间，`SoftRefLRUPolicyMSPerMB` 每MB空闲空间可以允许软引用对象存活多久，默认1000 ms；若当前JVM空闲空间未3000MB，那么软引用的Class对象可以存活大约50分钟。
>
> 一般发生GC时，JVM内部会有部分空间，如果不是要发生OOM(内存溢出)，一般软引用也不会被回收。

#### 优化策略

> 在大量反射代码的项目中，设置`-XX:SoftRefLRUPolicyMSPerMB=0`会造成YGC时，回收大量JVM创建的反射类，导致`GeneratedSerializationConstructorAccessor`类频繁创建，存放的元空间频繁被塞满，导致频繁FGC。所以应该设置大一些，如1000、2000、5000等，避免JVM自动创建的软引用的一些类的Class对象随便被回收。

### 3.系统每天数十次FGC导致卡死的优化

#### 案例背景

> 根据`jstat`和可视化监控平台`(Zabbix、Ganglia、Open-Falcon、Prometheus)`分析得出：
>
> 机器配置：2核4G
> JVM堆内存大小：2G
> 系统运行时间：6天
> 系统运行6天内发生的Full GC次数和耗时：250次，70多秒
> 系统运行6天内发生的Young GC次数和耗时：2.6万次，1400秒
>
> JVM参数
>
> ```properties
> -Xms1536M -Xmx1536M -Xmn512M -Xss256K -XX:SurvivorRatio=5 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC 
> -XX:CMSInitiatingOccupancyFraction=68 -XX:+CMSParallelRemarkEnabled -XX:+UseCMSInitiatingOccupancyOnly 
> -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintHeapAtGC
> ```

#### 优化分析

> YGC：平均每天 4000多次、每分钟3次，每次耗时50ms。
>
> FGC：平均每天40多次、每小时2次，每次耗时300ms。
>
> Eden区：365MB
>
> Survivor区：70MB
>
> 老年代占用达到68%≈680MB时FGC。
>
> 每20s，Eden区填满触发YGC，也就是每秒新增15~20MB对象；
>
> 1. 如果每次YGC后，存活对象小于35MB，那么老年代内存占用几乎不会变大。
> 2. 如果每次YGC后，存活对象大于35MB小于70MB，触发动态年龄判断，会有几十MB对象进入老年代，假如20MB，那么34次YGC，也就是十二分钟左右就会触发FGC，不符合，排除。
> 3. 如果每次YGC后，存活对象大于70MB，存活对象直接进入老年代，老年代触发FGC的时间更短，排除。
>
> 那么很可能每次存货对象很可能小于35M，对象年龄达到阈值时，才会晋升到老年代；或大对象直接进入老年代；所以需要通过`jstat`命令查看老年代内存变化是匀速的还是激增，从而分析出具体原因。

#### 优化策略

> 如果是因为对象晋升导致的，可以提高新生代大小、Survivor大小，使得对象尽量存在于新生代，避免进入老年代。
>
> 如果是因为大对象直接进入老年代，需要排查大对象是怎么产生的，具体进行优化，本案例中是因为SQL存在类似`select * from table`的语句，查出全表数据，高达几百MB的大对象出现，所以优化策略就是SQL添加查询条件，避免数据量过大，产生大对象。
>
> 案例中的老年代FGC阈值设置68%，过低，建议设置大一些。手动设置元空间大小，因为默认元空间大小只有几十兆，如果反射代码较多，会导致元空间占满，从而导致FGC。

### 4.System.gc()导致电商大促时系统FGC卡死

#### 案例背景

> 根据`jstat`命令发现，JVM几乎每秒都执行一次Full GC，每次都耗时几百毫秒；而JVM内存各区域使用量都是正常的。

#### 优化分析

> 很可能是代码中存在`System.gc()`,通过IDE全局搜索，果然查到了。

#### 优化策略

> 添加`-XX:+DisableExplicitGC`JVM参数，禁用显式执行GC。

### JVM模板

8G服务器常规设置

```properties
-Xms4096M 								# 初始堆大小 4G
-Xmx4096M 								# 最大堆大小 4G
-Xmn3072M 								# 新生代大小 3G
-Xss1M 									# 栈大小 1M
-XX:MetaspaceSize=256M 					# 初始元空间大小 256M
-XX:MaxPermSize=256M 					# 最大元空间大小 256M
-XX:+UseParNewGC 						# 使用 ParNew GC 
-XX:+UseConcMarkSweepGC 				# 使用 CMS GC 
-XX:CMSInitiatingOccupancyFaction=92 	 # 老年代FGC阈值
-XX:+UseCMSCompactAtFullCollection 		# CMS 开启压缩整理
-XX:CMSFullGCsBeforeCompaction=5 		# CMS 每5+1次FGC后进行压缩整理，配合上面使用，一些情况调小会使得FGC频率变低
-XX:+CMSParallelInitialMarkEnabled 		# CMS 初始标记使用多线程(不 stop the word)
-XX:+CMSScavengeBeforeRemark			# CMS 重新标记前执行一次YGC(当老年代对象与新生代对象存在引用关系时，提前回收部分新生代对象，能提升重新标记阶段的性能)
```

#### 参考

> `jsta`命令 https://www.cnblogs.com/sxdcgaq8080/p/11089841.html

#### 总结

1. 分析机器情况（机器配置，堆内存大小，运行时长，FullGC次数、时间，YoungGC次数、时间） 
2. 查看具体的jvm参数配置 
3. 然后根据JVM参数配置梳理出JVM模型，每个区间的大小是多少，画出来JVM模型（考虑每个设置在申请情况下会执行GC） 
4. 结合jstat查看的GC情况，在结合JVM模型进行二次分析 
5. jmap dump内存快照，通过jhat或者Visual VM之类的工具查看具体的对象分类情况 
6. 根据分析的情况再具体到问题（Bug、或者参数设置等问题） 
7. 修复Bug，优化JVM参数