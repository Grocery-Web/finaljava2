drop database if exists CrimeDB;
create database CrimeDB;
go
use CrimeDB

/* CREATE TABLES */ 

-- Create table for Account login
CREATE TABLE Account (
	UserID varchar(20) PRIMARY KEY NOT NULL,
	FullName nvarchar(50) NOT NULL,
	Email varchar(50) NOT NULL,
	PasswordHash BINARY(64) NOT NULL,
	Privilege INT NOT NULL CHECK (Privilege IN (1, 2, 3)),
)
GO

/* complaint table */ 
create table Complaint (
	id int identity(1,1) primary key,
	complaintName varchar(50),
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
	gender bit NOT NULL,
	dob date,
	address nvarchar(MAX),
	image varchar(100),
	nationality varchar(50),
	job varchar(20),
	alive bit DEFAULT 1
)

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


/* criminal table */
/* if punishment = imprisonment -> criminal move to the next level = prisoner */
create table Criminal (
	id int identity(1,1) primary key,
	personId int,
	complaintID int,
	punishment varchar(100) CHECK (punishment IN('administrative sanctions', 'imprisonment', 'in process')),
	constraint cp foreign key (personId) references Person(id),
	constraint cc foreign key (complaintID) references Complaint(id),
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
	criminalID int,
	constraint pin foreign key (criminalID) references Criminal(id)
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
	deathReason nvarchar(MAX),
	complaintID int,
	constraint vid foreign key (complaintID) references Complaint(id)
)
go




/* END CREATE TABLES */ 

/* PROCEDURE ACCOUNT */

-- Create proc to add new Account
CREATE PROCEDURE addAccount
	@UserID varchar(20),
	@FullName nvarchar(50),
	@Email varchar(50),
	@Password varchar(50),
	@Privilege INT
AS
BEGIN
	INSERT INTO Account (UserID, FullName, Email, PasswordHash, Privilege)
	VALUES(@UserID, @FullName, @Email, HASHBYTES('SHA2_512', @Password), @Privilege)
END
GO

-- Add a new Admin account
EXEC addAccount
	@UserID = 'admin',
	@FullName = 'ADMINISTRATOR',
	@Email = 'admin@gmail.com',
	@Password = 'admin',
	@Privilege = 1
GO

-- Create proc to check for Account login
CREATE PROC checkAcc
	@UserID varchar(20),
	@Password varchar(50)
AS
BEGIN
	SELECT * FROM Account
	WHERE UserID = @UserID COLLATE Latin1_General_CS_AS
	and PasswordHash = HASHBYTES('SHA2_512', @Password) 
END
GO

-- Create proc to get all Accounts
CREATE PROC getAllAcc

AS
BEGIN
	SELECT UserID, FullName, Email, Privilege FROM Account
END
GO

-- Create proc to delete Account
CREATE PROC deleteAcc
	@UserID varchar(20)
AS
BEGIN
	DELETE FROM Account
	WHERE UserID = @UserID
END
GO

-- Create proc to update Account
CREATE PROC updateAcc
	@FullName nvarchar(50),
	@Email varchar(50),
	@Password varchar(50) = NULL,
	@Privilege INT,
	@UserID varchar(20)
AS
BEGIN
	UPDATE Account
	SET FullName = @FullName,
		Email = @Email,
		PasswordHash = ISNULL(HASHBYTES('SHA2_512', @Password), PasswordHash),
		Privilege = @Privilege
	WHERE UserID = @UserID
END
GO
/* END PROCEDURE ACCOUNT */

/* PROCEDURE PERSON */

/* Select all Personal ID in table */
CREATE PROC getAllID
AS
BEGIN
	SELECT id FROM Person
END
GO

/*Select all people in table*/
create proc getAlivePeople
as
begin
	select * from Person where alive = 1
end
go

/*Find Person by ID*/
create proc findLivePersonById
@id int
as
begin
	select * FROM Person WHERE id = @id and alive = 1; 
end
go

create proc findDeadPersonById
@id int
as
begin
	select * FROM Person WHERE id = @id and alive = 0; 
end
go

/*insert new person*/
create proc insertPerson
@id int, @name nvarchar(50), @gender bit,  @dob date, @address nvarchar(MAX), @image varchar(100),
@nationality varchar(50),@job varchar(20),@alive bit
as
begin
	insert into Person (id, name, gender,dob,address,image,nationality,job, alive)
	values(@id, @name, @gender, @dob, @address, @image, @nationality, @job, @alive)
end
go

/*delete person*/

CREATE PROC deletePerson
	@id int
AS
BEGIN
	UPDATE Person
	SET alive = 0
	where id = @id
END
GO

/*update person info*/
CREATE PROC updatePerson 
	@name nvarchar(50), 
	@gender bit,  
	@dob date, 
	@address nvarchar(MAX), 
	@image varchar(100),
	@nationality varchar(50),
	@job varchar(20),
	@alive bit,
	@id int
AS
BEGIN
	UPDATE Person
	SET name = @name, 
		gender = @gender,  
		dob = @dob, 
		address = @address, 
		image = @image,
		nationality = @nationality,
		job = @job,
		alive = @alive
		WHERE id = @id
END
GO

/* END PROCEDURE PERSON */

/* PROCEDURE COMPLAINT */
-- select all Complaints in table
create proc getAllUnverifiedComplaints
as
begin
	select * from Complaint where verifyStatus = 0
end
go

-- insert a new Complaint
create proc addComplaint
@name varchar(50), @datetime datetime,  @place nvarchar(MAX), @declarantName nvarchar(50), @detail nvarchar(MAX),@verifyStatus bit
as
begin
	insert into Complaint (complaintName, datetime,place,declarantName,detail,verifyStatus)
	values(@name, @datetime, @place, @declarantName, @detail, @verifyStatus)
end
go

-- delete Complaint by ID
create proc deleteComplaint
@id int
as
begin
	DELETE FROM Complaint WHERE id = @id; 
end
go

-- find Complaint by ID
create proc findComplaintById
@id int
as
begin
	select * FROM Complaint WHERE id = @id; 
end
go

-- update Complaint by ID
create proc updateComplaintById
@id int, @name varchar(50), @datetime datetime,  @place nvarchar(MAX), @declarantName nvarchar(50), @detail nvarchar(MAX), @verifyStatus bit
as
begin
	update Complaint 
	set complaintName = @name, datetime = @datetime, place = @place, declarantName = @declarantName, detail = @detail, verifyStatus = @verifyStatus
	where id = @id
end
go

/* END PROCEDURE COMPLAINT */

/* PROCEDURE COMPLAINT DETAIL*/
-- insert detail
create proc setComplaintDetail
@personId int, @compId int,  @crimeType nvarchar(50)
as
begin
	insert into ComplaintDetail (personId, compId,crimeType)
	values(@personId, @compId, @crimeType)
end
go

-- find Complaint Detail by Complaint ID
create proc getComplaintDetailByComplaintId
@id int
as 
begin
	select * from ComplaintDetail where compId = @id;  
end 
go

-- select all people in table
create proc findAllPersonByComplaintId
@id int
as 
begin
	select personId from ComplaintDetail where compId = @id;  
end 
go

-- select personID and crimeType in table
create proc getAllComplaintDetail
as 
begin
	select * from ComplaintDetail;  
end 
go

-- remove person from complaint table
CREATE PROC removePerson
@personId int, @compId int
AS
BEGIN
	DELETE FROM ComplaintDetail 
	WHERE personId = @personId AND compId = @compId
END
GO

/* END PROCEDURE COMPLAINT DETAIL */


/* PROCEDURE CRIMINAL*/
-- select all Criminal in table
create proc getAllCriminals
as
begin
	select * from Criminal
end
go


-- insert a new Criminal
create proc addCriminal
@punishment varchar, @personId int, @complaintID int, @rating int
as
begin
	insert into Criminal (punishment, personId, complaintID, rating)
	values(@punishment, @personId, @complaintID, @rating)
end
go

/* END PROCEDURE CRIMINAL */

/* PROCEDURE PRISONER */


--add prisoner
create proc addPrisoner
@startDate date, @prisonID int, @releaseStatus bit, @duration bit, @type nvarchar(50), @criminalID int
as
begin
	insert into Prisoner (startDate, prisonId, releaseStatus, duration, type, criminalID)
	values (@startDate, @prisonID, @releaseStatus, @duration, @type, @criminalID)
end
go



/* END PROCEDURE PRISONER*/

/* INSERT DATA IN TABLE*/ 

-- table Person
insert into Person values (181, 'Thang', 1, '1999-08-05', 'HCM', '181.png', 'Vietnamese', 'Student',1)
insert into Person values (182, 'Hoang', 1, '1989-02-15', 'HN', '182.png', 'Vietnamese', 'Teacher',1)
insert into Person values (183, 'Son', 1, '1995-12-05', 'Da Nang', '183.png', 'Vietnamese', 'Engineer',1)
insert into Person values (184, 'Kien', 1, '1997-10-21', 'HCM', '184.png', 'Vietnamese', 'Student',1)
insert into Person values (152, 'Trung', 1, '1992-07-12', 'HN', '152.png', 'Vietnamese', 'Singer',1)
insert into Person values (153, 'Jenny', 0, '1982-05-26', 'Lao Cai', '153.png', 'American', 'Tailor',1)
insert into Person values (155, 'Nguyen', 1, '1985-12-12', 'HCM', '155.png', 'Vietnamese', 'Builder',1)
insert into Person values (174, 'Thao', 0, '1996-06-09', 'HCM', '174.png', 'Vietnamese', 'Dancer',1)
insert into Person values (175, 'Ha', 0, '1980-02-01', 'HN', '175.png', 'Vietnamese', 'Farmer',1)
insert into Person values (176, 'Linh', 0, '2000-09-18', 'Can Tho', '176.png', 'Vietnamese', 'Student',1)
insert into Person values (177, 'Hung', 1, '1983-11-29', 'Ha Tinh', '177.png', 'Vietnamese', 'Teacher',1)
insert into Person values (115, 'Phong', 1, '1976-02-14', 'HCM', '115.png', 'Vietnamese', 'Doctor',1)
insert into Person values (116, 'Xuan', 0, '1988-06-02', 'Thanh Hoa', '116.png', 'Vietnamese', 'Freelancer',1)
insert into Person values (118, 'Hang', 0, '1986-11-10', 'HCM', '118.png', 'Vietnamese', 'Cashier',1)
insert into Person values (121, 'Cuong', 1, '1996-04-26', 'HN', '121.png', 'Vietnamese', 'Pilot',1)
insert into Person values (122, 'Tra', 0, '1990-07-17', 'HCM', '122.png', 'Vietnamese', 'Nurse',1)
insert into Person values (125, 'Long', 1, '1986-10-20', 'HCM', '125.png', 'Vietnamese', 'Painter',1)
insert into Person values (166, 'Ngoc', 0, '1992-08-07', 'Ca Mau', '166.png', 'Vietnamese', 'Secretary',1)
go

-- table Complaints
insert into Complaint values ('Bomb Threats by Telephone', '2001-08-05 13:05:30', 'Ho Chi Minh', 'Thuy', 
'Bomb threats or suspicious items should always be taken seriously. How quickly and safely you react to a bomb 
threat could save lives, including your own. What should you do?',0)
insert into Complaint values ('Human Trafficking', '2018-12-01 01:55:12', 'Soc Trang', 'Cuc', 
'Traffickers use force, fraud, or coercion to exploit their victims for labor or commercial sex. 
Human trafficking happens around the world and in the VietNam?',0)
insert into Complaint values ('Sexual Assault', '2007-07-13 07:07:33', 'Ho Chi Minh', 'Linda',
'Sexual assault is any kind of unwanted sexual activity, from touching to rape',0)
insert into Complaint values ('File a Restraining Order', '2011-01-30 17:37:22', 'Ha Noi', 'Tan', 
'Generally, you have to fill out paperwork and submit it to the county courthouse. If you need protection right away',0)
insert into Complaint values ('Report Child Pornography', '2018-05-05 21:09:36', 'Ninh Bình', 'Truc', 
'Report suspected crime, like traffic violations and illegal drug use, to local authorities. Or you can report it to your 
nearest state police office',0)
insert into Complaint values ('Vehicle Misuse or Reckless Driving', '2017-02-27 22:56:01', 'An Giang', 'Tra My', 
'GSA leased vehicles all have license plates that have the following structure (GXX-XXXXX).  If the license plate does not begin with a G, 
then it is not owned by GSA',0)
go

/* END INSERT DATA IN TABLE*/ 

