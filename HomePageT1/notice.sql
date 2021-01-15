CREATE TABLE NOTICE(
num NUMBER(5) PRIMARY KEY,
author VARCHAR2(20) NOT NULL,
title VARCHAR2(200) NOT NULL,
content VARCHAR2(2000) NOT NULL,
writeday DATE DEFAULT SYSDATE,
readcnt NUMBER(8) DEFAULT 0,
reproot NUMBER(5),
repstep NUMBER(5),
repindent number(3)
)

CREATE TABLE menu1(
id VARCHAR2(6) PRIMARY KEY,
name VARCHAR2(30) NOT NULL
)

SELECT MAX(NUM) FROM NOTICE

SELECT NVL2(MAX(num), MAX(num)+1, 1) FROM NOTICE

SELECT * FROM NOTICE

SELECT COUNT(num) FROM NOTICE

SELECT * FROM NOTICE order by repRoot desc, repStep asc

SELECT ROWNUM rnum, num, title, author, writeday, readcnt, repIndent from(SELECT * FROM NOTICE order by repRoot desc, repStep asc)

SELECT * FROM (SELECT ROWNUM rnum, num, title, author, writeday, readcnt, repIndent from(SELECT * FROM NOTICE order by repRoot desc, repStep asc)) WHERE rnum >= ? AND rnum <= ?
