drop database if exists CrimeDB;
create database CrimeDB;
go
use CrimeDB
go

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
	appliedDate date NULL,
	hisOfViolent varchar(Max) NULL,
	punishment varchar(100) CHECK (punishment in('administrative sanctions', 'imprisoner', 'in process')),
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
	img nvarchar(100),
	limit int,
	prisonerNum int
)
go

/* prisoner table */
create table Prisoner (
	id int identity(1,1) primary key,
	prisonId int,
	criminalID int,
	startDate date,
	endDate date,
	constraint pp foreign key (prisonId) references PrisonList(id),
	duration int null,
	releaseStatus bit null,
	type nvarchar(50),  /* type of crime */
	constraint pin foreign key (criminalID) references Criminal(id)
)
go


/* victim table */
create table Victim (
	id int identity(1,1) primary key,
	personalID int,
	status bit, -- dead or not
	deathTime datetime null,
	deathPlace nvarchar(MAX),
	deathReason nvarchar(MAX),
	complaintID int,
	constraint vid foreign key (complaintID) references Complaint(id),
	constraint pid foreign key (personalID) references Person(id),
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

create proc getPersonById
@id int
as
begin
	select * FROM Person WHERE id = @id; 
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

/*Find if person in jail*/
create proc checkPersonInJail
@personalId int
as
begin
	select count(*) as count from Person per
	inner join Criminal cri on per.id = cri.personId
	inner join Prisoner pri on cri.id = pri.criminalID
	where per.id = @personalId and releaseStatus = 0
end
go

/*Find if person is a Criminal*/
create proc checkPersonIsCriminal
@personalId int
as
begin
	select count(*) as count from Criminal
	where personId = @personalId and punishment like 'in process'
end
go

/*Find if person existed in two defferent Complaints*/
create proc checkPersonExistedInComplaint
@compId int, @personalId int
as
begin
	select count(*) as count from person per
	inner join (select * from ComplaintDetail  where compId <> @compId) temp
	on per.id = temp.personId
	inner join Complaint com
	on temp.compId = com.id
	where per.id = @personalId and com.verifyStatus = 0
end
go

/* END PROCEDURE PERSON */

/* PROCEDURE COMPLAINT */
-- select all Unverified Complaints in table
create proc getAllUnverifiedComplaints
as
begin
	select * from Complaint where verifyStatus = 0
end
go

-- select all Approved Complaints in table
create proc getAllApprovedComplaints
as
begin
	select * from Complaint where verifyStatus = 1
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
CREATE PROC removePersoninComplaintDetail
@personId int, @compId int
AS
BEGIN
	DELETE FROM ComplaintDetail 
	WHERE personId = @personId AND compId = @compId
END
GO

-- remove person from complaint table
create proc getCrimeTypeOfPerson
@personId int, @compId int
as 
begin
	select crimeType from ComplaintDetail where personId = @personId AND compId = @compId ;  
end 
go

-- get victim list by incident id
CREATE PROC getVictimListByIncidentId
@incidentId int
AS
BEGIN
	SELECT * FROM Victim WHERE complaintID = @incidentId
END 
GO

/* END PROCEDURE COMPLAINT DETAIL */


/* PROCEDURE CRIMINAL*/
-- select all Criminal in table
create proc getCriminalsInProcess
as
begin
	select p.*, cr.id as criminalId, cr.punishment, cr.rating, cpl.complaintName from Criminal cr
	inner join Person p on cr.personId = p.id
	inner join Complaint cpl on cr.complaintID = cpl.id
	where cr.punishment like 'in process'
end
go

-- insert a new Criminal
create proc addCriminal
@personId int, @complaintID int, @punishment varchar(100), @rating int,@appliedDate date, @hisOfViolent varchar(MAX)
as
begin
	insert into Criminal (personId, complaintID, punishment, rating, appliedDate, hisOfViolent)
	values(@personId, @complaintID, @punishment, @rating, @appliedDate, @hisOfViolent)
end
go

-- update Criminal
CREATE PROC updateCriminal
	@personId int, 
	@complaintID int,  
	@appliedDate date,
	@hisOfViolent varchar(MAX),
	@punishment varchar(100), 
	@rating int,
	@criminalId int
AS
BEGIN
	UPDATE Criminal
	SET personId = @personId, 
		complaintID = @complaintID,  
		appliedDate = @appliedDate, 
		hisOfViolent = @hisOfViolent, 
		punishment = @punishment,
		rating = @rating
	WHERE id = @criminalId
END
GO

-- Find Criminal by Personal Id
create proc findLastUpdatedByPersonalId
@personId int
as
begin
	SELECT TOP 1 * FROM
	(select * from Criminal where personId = @personId) as temp
	ORDER BY id DESC
end
go

-- Find Criminal by Criminal Id
create proc findCriminalbyId
@criminalId int
as
begin
	select * from Criminal where id = @criminalId
end
go

-- find Criminal by id
create proc findCriminalByPersonAndComplaintId
@personId int, @complaintId int
as
begin
	select * from Criminal where personId = @personId and complaintId = @complaintId
end
go

-- Find all verified incidents commited by a Person
CREATE PROC findIncidentsCommitedByPerson
@personID int
AS
BEGIN
	SELECT complaintID FROM Criminal
	WHERE Criminal.personId = @personID
END
GO

/* END PROCEDURE CRIMINAL */

/* PROCEDURE VICTIM */
-- Link new victim to a verified Incident
CREATE PROC linkNewVictim
	@personalID int,
	@status bit,
	@deathTime datetime,
	@deathPlace nvarchar(MAX),
	@deathReason nvarchar(MAX),
	@complaintID int
AS
BEGIN
	INSERT INTO Victim (personalID, status, deathTime, deathPlace, deathReason, complaintID)
	VALUES (@personalID, @status, @deathTime, @deathPlace, @deathReason, @complaintID)

	UPDATE Person
	SET alive = @status
	WHERE Person.id = @personalID
END
GO

-- Get all Victims
CREATE PROC getAllVictims
AS
BEGIN
	SELECT vt.*,p.name,p.gender,p.nationality ,cl.complaintName
	FROM Victim vt
	INNER JOIN Person p ON vt.personalID = p.id
	INNER JOIN Complaint cl ON vt.complaintID = cl.id
END
GO

-- remove Victim by personal Id
CREATE PROC removeVictimbyPersonalId
@personId int
AS
BEGIN
	DELETE FROM Victim WHERE personalID in
	(
		SELECT TOP 1 personalID FROM
		(select * from Victim where personalID = @personId) as temp
		ORDER BY id DESC
	) 

	UPDATE Person
	SET alive = 1
	WHERE Person.id = @personId
END
GO

/* END PROCEDURE VICTIM */

/* PROCEDURE PRISONER */

--get all prisoners
create proc getAllPrisoners
as
begin
	select * from Prisoner where releaseStatus = 0
end
go

--add prisoner
create proc addPrisoner
@prisonID int, @criminalID int, @startDate date, @endDate date, @duration int, @releaseStatus bit, @type nvarchar(50)
as
begin
	insert into Prisoner (prisonId, criminalID, startDate, endDate, duration, releaseStatus, type)
	values (@prisonId, @criminalID, @startDate, @endDate, @duration, @releaseStatus, @type)
end
go

--get Prisoners by Prisonlist ID
create proc getAllPrisonerByPrisonListID
@id int
as
begin
	select Prisoner.* , personId, Person.name, dob, person.gender, nationality
	from PrisonList 
		inner join Prisoner on PrisonList.id = Prisoner.prisonId
		inner join Criminal on Criminal.id = Prisoner.criminalID
		inner join Person on Criminal.personId = Person.id
	where (PrisonList.id = @id) and (releaseStatus = 0)
end
go

--find Prisoners by criminal ID
create proc findUnreleasedPrisoners
as
begin
	select pr.*, p.name as personName, p.gender, p.nationality, pl.name as prisonName
	from Prisoner pr
	inner join PrisonList pl on pr.prisonId = pl.id
	inner join Criminal cr on pr.criminalID = cr.id
	inner join Person p on cr.personId = p.id
	where releaseStatus = 0
end
go


/* END PROCEDURE PRISONER*/

/*TRIGGER */

create trigger insertPrisoner
on Prisoner 
after insert
as
begin
	update PrisonList
	set PrisonList.prisonerNum= (
		select count(*)
		from Prisoner
		where (Prisoner.prisonId = prisonlist.id) and (Prisoner.releaseStatus = 0)
	)
end
go

create trigger updatePrisoner
on Prisoner 
after update
as
begin
	update PrisonList
	set PrisonList.prisonerNum= (
		select count(*)
		from Prisoner
		where (Prisoner.prisonId = prisonlist.id) and (Prisoner.releaseStatus = 0)
	)
end
go

/* END TRIGGER*/

/* PROCEDURE PRISONLIST */

create proc getAllPrisonList
as
begin
	select *
	from PrisonList	
end
go

create proc getPrisonListByID
@id int
as
begin
	select * 
	from PrisonList
	where id = @id
end
go

create proc getAllPrisonListExceptPrisonID
@id int
as
begin
	select *
	from PrisonList
	where @id != id
end
go

/* END PROCEDURE PRISONER */


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
insert into Complaint values ('Report Child Pornography', '2018-05-05 21:09:36', 'Ninh Binh', 'Truc', 
'Report suspected crime, like traffic violations and illegal drug use, to local authorities. Or you can report it to your 
nearest state police office',0)
insert into Complaint values ('Vehicle Misuse or Reckless Driving', '2017-02-27 22:56:01', 'An Giang', 'Tra My', 
'GSA leased vehicles all have license plates that have the following structure (GXX-XXXXX).  If the license plate does not begin with a G, 
then it is not owned by GSA',0)
go


--table prison list
insert into PrisonList values ('Fox River State Penitentiary', 'Fox River State Penitentiary, Joliet, Illinois', 'fox.png', 4, 0)
insert into PrisonList values ('Sona Federal Penitentiary', ' Panama. Colonel Escamilla', 'sona.png', 1, 0)
insert into PrisonList values ('Ogygia Prison', 'Sana, Yemen', 'ogyia.png', 3, 0)

/* END INSERT DATA IN TABLE*/ 
