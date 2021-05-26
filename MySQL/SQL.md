建表语句

```sql
-- 学生表
CREATE TABLE person(
	id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(20) NOT NULL,
	address VARCHAR(10) NOT NULL,
	age TINYINT DEFAULT 0,
	lastTime timestamp
);
-- 班级表
CREATE TABLE class(
	id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	class_name VARCHAR(10) NOT NULL,
	student_name VARCHAR(20) NOT NULL,
	lastTime timestamp
);
```

插入语句

```sql
INSERT INTO person (name,address,age,lastTime) VALUES ("小芳","安徽",22,now());
INSERT INTO person (name,address,age,lastTime) VALUES ("小红","四川",24,now());
INSERT INTO person (name,address,age,lastTime) VALUES ("小李","上海",21,now());
INSERT INTO person (name,address,age,lastTime) VALUES ("小冯","河南",25,now());
INSERT INTO person (name,address,age,lastTime) VALUES ("小王","上海",26,now()),
("小田","河南",27,now());
INSERT INTO person (name,address,age,lastTime) VALUES ("小冯","河北",21,now());
INSERT INTO person (name,address,age,lastTime) VALUES ("小红","河南",25,now());

INSERT INTO class (class_name,student_name,lastTime) 
VALUES 	("一班","小芳",now()),
        ("二班","小红",now()),
        ("三班","小李",now()),
        ("一班","小冯",now()),
        ("二班","小王",now()),
        ("二班","小邓",now()),
        ("一班","小田",now());
```

更新语句

```sql
UPDATE person SET age = 23 WHERE id = 3;
```

删除语句

```sql
DELETE FROM person WHERE id = 1;
```

查询语句

```sql
SELETE * FROM person;
-- 查询一班所有学生的信息
SELECT person.*,class_name FROM person
LEFT JOIN class ON person.name = class.student_name
WHERE class.class_name = "一班";
-- 获取没有班级的学生
SELECT * FROM person
LEFT JOIN class ON person.id = class.id
WHERE class.id IS NULL
-- 获取没有班级的学生&没有登记到学生名单中的人
SELECT * FROM person
LEFT JOIN class ON person.id = class.id
WHERE class.id IS NULL
UNION
SELECT * FROM person
RIGHT JOIN class ON person.id = class.id
WHERE person.id IS NULL
-- 行转列
SELECT student_name
SUM(CASE course WHEN "语文" score ELSE 0) AS "语文",
SUM(CASE course WHEN "数学" score ELSE 0) AS "数学",
SUM(CASE course WHEN "英语" score ELSE 0) AS "英语"
FROM class
GROUP BY student_name;
-- 查询重名的学生的个数
SELECT COUNT(*) as conut FROM person GROUP BY name HAVING COUNT(name) > 1;
-- 查询重名的学生的信息
SELECT * FROM person WHERE name IN (SELECT name FROM person GROUP BY name HAVING COUNT(name) > 1);
-- 过滤重复的数据
SELECT DISTINCT name FROM person;

SELECT * FROM person GROUP BY name;
-- 查询重名的学生中各组id最大的记录
SELECT * FROM person WHERE id IN (SELECT MAX(id) FROM person GROUP BY name HAVING COUNT(name) > 1);
-- 查询重名的学生中去除id最小的记录
SELECT * FROM person WHERE name IN (SELECT name FROM person GROUP BY name HAVING COUNT(name) > 1)
AND id NOT IN (SELECT MIN(id) FROM person GROUP BY name HAVING COUNT(name) > 1);
-- 去重,保留最小id的记录
DELETE FROM person WHERE name IN(
    SELECT name FROM (
        SELECT name FROM person WHERE name IN (SELECT name FROM person GROUP BY name HAVING 	COUNT(name) > 1)
    ) AS tmp1
)
AND id NOT IN (
    SELECT id FROM (
        SELECT MIN(id) AS id FROM person GROUP BY name HAVING COUNT(name) > 1
    ) AS tmp2
)
```

