drop database if exists CrimeDB;
create database CrimeDB;
go
use CrimeDB


/* account table */
create table Account (
	id int identity(1,1) primary key,
	userName varchar(20),
	passWord varchar(20),
	level varchar(10)
)
go

insert into Account values ('master', 'master', 'master')
insert into Account values ('admin', 'admin', 'admin')
insert into Account values ('user', 'user', 'user')


/* complaint table */ 
create table Complaint (
	id int identity(1,1) primary key,
	datetime datetime,
	place nvarchar(MAX),
	declarantName nvarchar(50),
	detail nvarchar(MAX),
	verifyStatus bit   /* approved or not */
)
go

/* person table */
create table Person (
	id int primary key,
	name nvarchar(50),
	gender bit,
	dob date,
	address nvarchar(MAX),
	image varchar(100),
	nationality varchar(50),
	job varchar(20)
)
insert into Person values (181, 'Thang', 1, '1999-08-05', 'HCM', '181.png', 'Vietnamese', 'Student')
insert into Person values (182, 'Hoang', 1, '1989-02-15', 'HN', '182.png', 'Vietnamese', 'Teacher')
insert into Person values (183, 'Son', 1, '1995-12-05', 'Da Nang', '183.png', 'Vietnamese', 'Engineer')
insert into Person values (184, 'Kien', 1, '1997-10-21', 'HCM', '184.png', 'Vietnamese', 'Student')
insert into Person values (152, 'Trung', 1, '1992-07-12', 'HN', '152.png', 'Vietnamese', 'Singer')
insert into Person values (153, 'Jenny', 0, '1982-05-26', 'Lao Cai', '153.png', 'American', 'Tailor')
insert into Person values (155, 'Nguyen', 1, '1985-12-12', 'HCM', '155.png', 'Vietnamese', 'Builder')
insert into Person values (174, 'Thao', 0, '1996-06-09', 'HCM', '174.png', 'Vietnamese', 'Dancer')
insert into Person values (175, 'Ha', 0, '1980-02-01', 'HN', '175.png', 'Vietnamese', 'Farmer')
insert into Person values (176, 'Linh', 0, '2000-09-18', 'Can Tho', '176.png', 'Vietnamese', 'Student')
insert into Person values (177, 'Hung', 1, '1983-11-29', 'Ha Tinh', '177.png', 'Vietnamese', 'Teacher')
insert into Person values (115, 'Phong', 1, '1976-02-14', 'HCM', '115.png', 'Vietnamese', 'Doctor')
insert into Person values (116, 'Xuan', 0, '1988-06-02', 'Thanh Hoa', '116.png', 'Vietnamese', 'Freelancer')
insert into Person values (118, 'Hang', 0, '1986-11-10', 'HCM', '118.png', 'Vietnamese', 'Cashier')
insert into Person values (121, 'Cuong', 1, '1996-04-26', 'HN', '121.png', 'Vietnamese', 'Pilot')
insert into Person values (122, 'Tra', 0, '1990-07-17', 'HCM', '122.png', 'Vietnamese', 'Nurse')
insert into Person values (125, 'Long', 1, '1986-10-20', 'HCM', '125.png', 'Vietnamese', 'Painter')
insert into Person values (166, 'Ngoc', 0, '1992-08-07', 'Ca Mau', '166.png', 'Vietnamese', 'Secretary')
go


/* complaint detail table */
create table ComplaintDetail (
	id int identity(1,1) primary key,
	personId int,
	constraint cdp foreign key (personId) references Person(id),
	compId int,
	constraint cpc foreign key (compId) references Complaint(id),
	crimeType nvarchar(50)
)
go


/* incident table */
create table Incident (
	id int identity(1,1) primary key,
	datetime datetime,
	place nvarchar(MAX), 
	detail nvarchar(MAX)
)
go


/* criminal table */
create table Criminal (
	id int identity(1,1) primary key,
	catchStatus bit,
	personId int,
	constraint cp foreign key (personId) references Person(id),
	incidentId int,
	constraint cc foreign key (incidentId) references Incident(id),
	rating int
)
go


/* prison list table */
create table PrisonList (
	id int identity(1,1) primary key,
	name nvarchar(50),
	address nvarchar(MAX),
	limit int,
	prisonerNum int
)
go


/* prisoner table */
create table Prisoner (
	id int identity(1,1) primary key,
	startDate date,
	prisonId int,
	constraint pp foreign key (prisonId) references PrisonList(id),
	releaseStatus bit null,
	duration int null,
	type nvarchar(50),  /* type of crime */
	personId int,
	constraint ppi foreign key (personId) references Person(id),
	incidentId int,
	constraint pin foreign key (incidentId) references Incident(id)
)
go


/* victim table */
create table Victim (
	id int identity(1,1) primary key,
	name nvarchar(50),
	gender bit,
	dob date,
	address nvarchar(MAX),
	image varchar(100),
	nationality varchar(50),
	status bit,  /* dead or not */
	deathTime datetime null,
	deathPlace nvarchar(MAX),
	deathReason nvarchar(MAX)
)
go


/* incident detail table */
create table IncidentDetail (
	id int identity(1,1) primary key,
	victimId int,
	constraint idv foreign key (victimId) references Victim(id),
	incidentId int,
	constraint idi foreign key (incidentId) references Incident(id),
	crimeType nvarchar(50)
)
go

/* PROCEDURE PERSON */

create proc getAllPerson
as
begin
	select * from Person
end
go


/* END PROCEDURE PERSON */

/* PROCEDURE COMPLAINT */

/* END PROCEDURE COMPLAINT */