DROP TABLE USERS;
CREATE table USERS(
 id bigint NOT NULL PRIMARY KEY,
 name varchar(50),
 email varchar(50) NOT NULL,
 role varchar(20),
 charges decimal(10,5),
 version bigint
);

INSERT into USERS(id,name,email,role,charges,version) values(1,'ajay','ajay@gmail.com','user',100,1);