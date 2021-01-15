drop table member

CREATE table aa(
id VARCHAR2(6) PRIMARY KEY,
name VARCHAR2(30) NOT NULL
)


RENAME member TO mmm1

CREATE TABLE member(
id VARCHAR2(10) PRIMARY KEY,
password VARCHAR2(15),
name VARCHAR2(15),
email VARCHAR2(30),
phoneNumber VARCHAR2(12),
address VARCHAR2(200),
gender VARCHAR2(10),
profileImgName VARCHAR2(300),
rights VARCHAR2(5)
)


commit

SELECT * FROM member

DELETE FROM member where id = 'lch5592'

UPDATE member SET rights = 'user' WHERE id = 'm012'


INSERT INTO member VALUES ('m012','123123','m012','m012@naver.com','01022674410','경기도','m','','user')
INSERT INTO member VALUES ('m013','123123','m013','m013@naver.com','01022674411','경기도','w','','user')
INSERT INTO member VALUES ('m014','123123','m014','m014@naver.com','01022674412','경기도','w','','user')
INSERT INTO member VALUES ('m015','123123','m015','m015@naver.com','01022674413','경기도','w','','user')
INSERT INTO member VALUES ('m016','123123','m016','m016@naver.com','01022674414','경기도','m','','user')
INSERT INTO member VALUES ('m017','123123','m017','m017@naver.com','01022674415','경기도','m','','user')