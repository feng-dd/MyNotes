create table studentInfo
(
id int not null primary key,
sName char(10) not null,
sClass char(10)	not null
);
--创建关联表s_id是外键
create table score
(
id int not null primary key,
score float not null;
s_id int not null;
object char(10) not null
foreign key(s_id) references studentInfo(id));
);

1.内连接sql
查询俩个表中都有的学生的所有信息
select * from studentInfo st , score sc where st.id=sc.s_id;
2.左外连接sql——以左边的表(studentInfo st left)studentInfo 为主表，查询结果中全部展示主表的信息
查询出所有学生相关的信息
select * from studentInfo st left join score sc on st.id=sc.s_id;
3.右外连接sql——以右边的表(score sc right)score 为主表，查询结果中全部展示主表的信息
查询出所有有成绩的学生信息
select * from studentInfo st right join score sc on st.id=sc.s_id;
4.查询0802班的学生成绩
select score from score where s_id in (select id from studentInfo where sClass='0802');
5.查询0801班的学生的平均分
select avg(score) as '平均分' from score where s_id in (select id from studentInfo where sClass='0801');