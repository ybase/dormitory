drop database dormitory;

create database dormitory character set utf8;

use dormitory;


create table dr_user(
	id int primary key auto_increment,
	passwd varchar(40) not null,
	name varchar(40) not null,
	email varchar(50) not null,
	phone varchar(20) not null,
	address varchar(200),
	visit numeric(10),
	status numeric(10), -- 0:δ��¼ 1:�ѵ�¼ 2:ͣ��
	role varchar(10), -- admin����Ա
	logindate varchar(8),
	logintime varchar(9)
);

create table dr_blog(
id int primary key auto_increment,
theme varchar(100) not null,
blogdesc blob,
crdate varchar(8) not null,
crtime varchar(9) not null,
yescount numeric(10),
crusr int,
usrname varchar(40),
imgid int,
picpath varchar(200)
);

create table dr_image(
id int primary key auto_increment,
picpath varchar(200) unique not null,
crdate varchar(8) not null,
crtime varchar(9) not null,
yescount numeric(10),
position varchar(5) -- C:��ҳλ��(������Ƭ)  O:����λ�� 
);

create table dr_talk(
id int primary key auto_increment,
talkdesc varchar(400),
crdate varchar(8) not null,
crtime varchar(9) not null,
crusr int,
usrname varchar(40),--  �û�����
yescount int,
nocount int,
blogid int
);

create table dr_top(
id int primary key auto_increment,
name varchar(20) not null,
topdesc varchar(100),
crdate varchar(8),
crtime varchar(9),
crusr int,
yescount numeric(10),
nocount numeric(10)
);

create table dr_record(
	id int primary key auto_increment,
	drtype int not null, -- 0-BLOG 1-IMAGE 2-TALK 3-TOP
	relid int not null,
	crdate varchar(8),
	crtime varchar(9),
	crusr int,
	usrname varchar(40)
);

create table dr_plan(
	id int primary key auto_increment,
	plandesc varchar(200),
	crdate varchar(8),
	crtime varchar(9),
	crusr int,
	usrname varchar(40)
);

-- ��ʼ������
insert into dr_user(id,passwd,name,email,phone,address,visit,status,role) values(419004,'ed0bf01975be7e3ade866ee9a9c365','����Ա','862968455@qq.com','88888888','��������',0,0,'admin');
insert into dr_user(id,passwd,name,email,phone,address,visit,status,role) values(419001,'5b3c3b2672015a14cefab16264d31c','��ɣ','894766302@qq.com','88888888','�����ϲ�',0,0,'');
insert into dr_user(id,passwd,name,email,phone,address,visit,status,role) values(419003,'e4ef6d69e18916de8ceefee92c7dec','������','741870293@qq.com','88888888','�����ϲ�',0,0,'');
insert into dr_user(id,passwd,name,email,phone,address,visit,status,role) values(419002,'e9aae70cee12b5968787d3d1bb0bc6','���','349781885@qq.com','88888888','��������',0,0,'');
insert into dr_user(id,passwd,name,email,phone,address,visit,status,role) values(419005,'1e0fb576a2390c6c1231b5ad0e2f83','������','623241925@qq.com','88888888','�㶫����',0,0,'');

insert into dr_top(id,name,topdesc,crdate,crtime,crusr,yescount,nocount) values(1,'��������','�������˺ü��죬޹�˺ü��죬�ܲ����ˣ�����������','20140501','120000000',5,0,0);
insert into dr_top(id,name,topdesc,crdate,crtime,crusr,yescount,nocount) values(2,'��˿�ж�','������һ�У��ú�˿�ջ������粻����������','20140501','120000000',5,0,0);
insert into dr_top(id,name,topdesc,crdate,crtime,crusr,yescount,nocount) values(3,'�޵�Ь��','��˵ĳ�У�һЬ�Ӹ��ܸ��ںü������ᣬʷ����ţЬ��','20140501','120000000',5,0,0);
insert into dr_top(id,name,topdesc,crdate,crtime,crusr,yescount,nocount) values(4,'�޵�צ��','��˵ĳ����ĳ��˺��...���ڶ���ĳ��С��������....','20140501','120000000',5,0,0);
