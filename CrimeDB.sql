CREATE DATABASE CrimeDB
GO

USE CrimeDB
GO

-- Create table for Account login
CREATE TABLE Account (
	UserID varchar(20) PRIMARY KEY NOT NULL,
	FullName nvarchar(50) NOT NULL,
	Email varchar(50) NOT NULL,
	PasswordHash BINARY(64) NOT NULL,
	Privilege INT NOT NULL CHECK (Privilege IN (1, 2, 3)),
)
GO

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
	@Email = 'leliem28@gmail.com',
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