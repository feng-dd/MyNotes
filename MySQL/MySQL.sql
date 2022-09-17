创建表
create table student
(
id char(10) not null,
name char(255) not null,
Chinese int not null,
English int not null,
math int not null,
primary key(id)
);

新增表字段
alter table student add sex char(10) not null;
修改表字段名及类型
alter table student change sex newsex varchar;
增加索引
alter table student add index indexname(id);
设置主键
alter table student add primary key(id);
删除某个索引
alter table student drop index indexname;
 
新增数据
insert into student (id,name,Chinese,English,math)
values('1','张三',89,87,76),
('2','李四',67,78,76),
('3','王五',84,68,80);

修改数据
update student set Chinese=90,math=70 where name='张三';

查询数据
1.查询所有学生的信息
select * from student;
2.查询所有学生的姓名和对应的英语成绩
select name,English from student;
3.过滤表中英语成绩的重复数据
select distinct English from student;
4.使用别名表示学生分数
select Chinese 	as '语文',English as '英语',math as '数学' from student;
5.查询姓名为李四的学生信息
select * from student where name='李四';
6.查询英语成绩大于等于90分的同学
select * from student where English>=90;
7.查询总分大于200分的所有同学
select *, (Chinese+English+math) as '总成绩' from student where (Chinese+English+math)>200;
8.查询所有姓李的学生英语成绩。
select * from student where name like '李%';
9.查询英语>80或者总分>200的同学
select * from student where English>80 or (Chinese+English+math)>200;
10.查询英语>80且总分>200的同学
select * from student where English>80 and (Chinese+English+math)>200;
11.统计每个学生的总分
select * ,(Chinese+English+math) as '总分' from student;
12.在所有学生总分数上加10分特长分
select * ,(Chinese+English+math) as '总分',(Chinese+English+math)+10 as '特长分' from student;

聚合查询
1.查询学生的数学的总成绩
select sum(math) as '数学总成绩' from student;
2.查询学生的数学的平均分
select avg(math) as '数学平均分' from student;
3.查询数学的最高分/最低分
select max(math) as '数学最高分' from student;
select min(math) as '数学最低分' from student;
4.统计当前学生的人数
select count(*) as '学生总数' from student;

分页查询
1.查询1、2条
select * from student limit 0,2;
1.查询3、4条
select * from student limit 2,2;

查询并排序
1.按照id正序(递增)排序：
select * from student order by id;
select * from student order by id asc;
2.按照id反序(递减)排序：
select * from student order by id desc;
3.语文增、数学降(先按照第一个条件排序，再第二个)
select * from student order by Chinese ,math desc;

分组查询
1.把学生按照性别分组
select sex from student group by sex;
2.筛选出人数大于2的记录
分组之前的筛选用where
分组以后再筛选用having
select sex , count(*) from student group by sex having count(*)>2;

