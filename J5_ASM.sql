USE MASTER
GO

IF db_id('J5_ASM') is not null
	BEGIN
		DROP DATABASE J5_ASM
	END
GO

CREATE DATABASE J5_ASM
GO

USE J5_ASM
GO

CREATE TABLE USERS
(
	username VARCHAR(50) PRIMARY KEY,
	password VARCHAR(50) NOT NULL,
	fullname NVARCHAR(100) NOT NULL
)
GO

-- pass để đăng nhập đều là 123
INSERT INTO USERS
VALUES
	('hao', '$MD5$pirm8Vr$5$99C4E7E93DF860C54DAD63735B58C87A', N'Nguyễn Đại Hào'),
	('thong', '$MD5$pirm8Vr$5$99C4E7E93DF860C54DAD63735B58C87A', N'Đinh Đạt Thông'),
	('tien', '$MD5$pirm8Vr$5$99C4E7E93DF860C54DAD63735B58C87A', N'Đào Quang Tiến'),
	('thanh', '$MD5$pirm8Vr$5$99C4E7E93DF860C54DAD63735B58C87A', N'Lê Long Thành'),
	('duong', '$MD5$pirm8Vr$5$99C4E7E93DF860C54DAD63735B58C87A', N'Đỗ Đại Dương'),
	('tri', '$MD5$pirm8Vr$5$99C4E7E93DF860C54DAD63735B58C87A', N'Vũ Thành Trí')
GO

CREATE TABLE DEPARTS
(
	id VARCHAR(50) PRIMARY KEY,
	name NVARCHAR(150) NOT NULL
)
GO

CREATE TABLE STAFFS
(
	id INT IDENTITY(100, 1) PRIMARY KEY,
	name NVARCHAR(100) NOT NULL,
	gender BIT NOT NULL,
	birthday DATE,
	photo NVARCHAR(256),
	email VARCHAR(150) NOT NULL,
	phone VARCHAR(15) NOT NULL,
	salary MONEY NOT NULL,
	notes NVARCHAR(256) NULL,
	depart_id VARCHAR(50) FOREIGN KEY REFERENCES dbo.DEPARTS(id) ON DELETE SET NULL
)
GO

CREATE TABLE RECORDS
(
	id INT IDENTITY(1000, 1) PRIMARY KEY,
	type SMALLINT NOT NULL,
	reason NVARCHAR(256),
	date DATE NOT NULL DEFAULT(GETDATE()),
	staff_id INT FOREIGN KEY REFERENCES dbo.STAFFS(id) ON DELETE CASCADE ON UPDATE CASCADE
)
GO


CREATE TABLE REMEMBER_USER
(
	id INT IDENTITY(1000, 1) PRIMARY KEY,
	user_id VARCHAR(50) REFERENCES dbo.USERS(username) ON DELETE CASCADE ON UPDATE CASCADE,
	date_login DATE DEFAULT GETDATE(),
	expired DATE DEFAULT DATEADD(DAY, 7, GETDATE())
)
GO

--Lấy về top 10 phòng ban có điểm record tốt nhất trong tháng
--nếu @month = 0 thì lấy về cả năm
CREATE PROC sp_getTop10DepartWithGoodRecord @month INT
AS
BEGIN
	IF (@month != 0)
	BEGIN
		SELECT TOP 10 d.id, d.name, 
		(SELECT COUNT(*) FROM dbo.STAFFS s WHERE s.depart_id = d.id) AS staffCount,
		SUM(CASE WHEN r.type = 0 THEN 1 ELSE 0 END) AS numGoodRecord, 
		SUM(CASE WHEN r.type = 1 THEN 1 ELSE 0 END) numBadRecord,  
		SUM(CASE WHEN r.type = 0 THEN 1 ELSE 0 END) - SUM(CASE WHEN r.type = 1 THEN 1 ELSE 0 END) AS totalPoint
		FROM dbo.DEPARTS d JOIN dbo.STAFFS s ON s.depart_id = d.id 
		JOIN dbo.RECORDS r ON r.staff_id = s.id
		WHERE YEAR(r.date) = YEAR(GETDATE()) AND MONTH(r.date) = @month
		GROUP BY d.id, d.name
		HAVING  ( SUM(CASE WHEN r.type = 0 THEN 1 ELSE 0 END) - SUM(CASE WHEN r.type = 1 THEN 1 ELSE 0 END) ) > 0
		ORDER BY ( SUM(CASE WHEN r.type = 0 THEN 1 ELSE 0 END) - SUM(CASE WHEN r.type = 1 THEN 1 ELSE 0 END) ) DESC
	END
	ELSE
	BEGIN
		SELECT TOP 10 d.id, d.name, 
		(SELECT COUNT(*) FROM dbo.STAFFS s WHERE s.depart_id = d.id) AS staffCount,
		SUM(CASE WHEN r.type = 0 THEN 1 ELSE 0 END) AS numGoodRecord, 
		SUM(CASE WHEN r.type = 1 THEN 1 ELSE 0 END) numBadRecord,  
		SUM(CASE WHEN r.type = 0 THEN 1 ELSE 0 END) - SUM(CASE WHEN r.type = 1 THEN 1 ELSE 0 END) AS totalPoint
		FROM dbo.DEPARTS d JOIN dbo.STAFFS s ON s.depart_id = d.id 
		JOIN dbo.RECORDS r ON r.staff_id = s.id
		WHERE YEAR(r.date) = YEAR(GETDATE())
		GROUP BY d.id, d.name
		HAVING  ( SUM(CASE WHEN r.type = 0 THEN 1 ELSE 0 END) - SUM(CASE WHEN r.type = 1 THEN 1 ELSE 0 END) ) > 0
		ORDER BY ( SUM(CASE WHEN r.type = 0 THEN 1 ELSE 0 END) - SUM(CASE WHEN r.type = 1 THEN 1 ELSE 0 END) ) DESC
	END
	
END
GO

--EXEC dbo.sp_getTop10DepartWithGoodRecord @month = 0 -- int


--lấy về top 10 staff có điểm record tốt nhất trong tháng @month
--Nếu @month = 0 thì lấy về cả năm

CREATE PROC sp_getTop10StaffWithGoodRecord @month INT
AS
BEGIN

	IF (@month != 0)
	BEGIN
		SELECT TOP 10 s.id, s.name, 
		SUM(CASE WHEN r.type = 0 THEN 1 ELSE 0 END) AS numGoodRecord, 
		SUM(CASE WHEN r.type = 1 THEN 1 ELSE 0 END) numBadRecord,  
		SUM(CASE WHEN r.type = 0 THEN 1 ELSE 0 END) - SUM(CASE WHEN r.type = 1 THEN 1 ELSE 0 END) AS totalPoint
		FROM dbo.STAFFS s JOIN dbo.RECORDS r ON r.staff_id = s.id
		WHERE YEAR(r.date) = YEAR(GETDATE()) AND MONTH(r.date) = @month
		GROUP BY s.id, s.name
		HAVING ( SUM(CASE WHEN r.type = 0 THEN 1 ELSE 0 END) - SUM(CASE WHEN r.type = 1 THEN 1 ELSE 0 END) ) > 0
		ORDER BY ( SUM(CASE WHEN r.type = 0 THEN 1 ELSE 0 END) - SUM(CASE WHEN r.type = 1 THEN 1 ELSE 0 END) ) DESC
	END
	ELSE
	BEGIN
		SELECT TOP 10 s.id, s.name, 
		SUM(CASE WHEN r.type = 0 THEN 1 ELSE 0 END) AS numGoodRecord, 
		SUM(CASE WHEN r.type = 1 THEN 1 ELSE 0 END) numBadRecord,  
		SUM(CASE WHEN r.type = 0 THEN 1 ELSE 0 END) - SUM(CASE WHEN r.type = 1 THEN 1 ELSE 0 END) AS totalPoint
		FROM dbo.STAFFS s JOIN dbo.RECORDS r ON r.staff_id = s.id
		WHERE YEAR(r.date) = YEAR(GETDATE())
		GROUP BY s.id, s.name
		HAVING ( SUM(CASE WHEN r.type = 0 THEN 1 ELSE 0 END) - SUM(CASE WHEN r.type = 1 THEN 1 ELSE 0 END) ) > 0
		ORDER BY ( SUM(CASE WHEN r.type = 0 THEN 1 ELSE 0 END) - SUM(CASE WHEN r.type = 1 THEN 1 ELSE 0 END) ) DESC
	END
END
GO

--EXEC dbo.sp_getTop10StaffWithGoodRecord @month = 0 -- int
GO


--Lấy về thống kê tiêu chuẩn của phòng ban tron tháng
CREATE PROC sp_getStatisticDepartStandardInMonth @month int
AS
BEGIN
	DECLARE @total SMALLINT = 0
	DECLARE @totalGood SMALLINT = 0
	DECLARE @totalBad SMALLINT = 0

	--Lấy về tổng số phòng ban
	SELECT @total = COUNT(*) FROM dbo.DEPARTS

	--Lấy về số phòng ban xuất sắc
	SELECT @totalGood += COUNT(DISTINCT d.id) FROM dbo.DEPARTS d 
	JOIN dbo.STAFFS s ON s.depart_id = d.id 
	JOIN dbo.RECORDS r ON r.staff_id = s.id
	WHERE MONTH(r.date) = @month AND YEAR(r.date) = YEAR(GETDATE())
	GROUP BY d.id
	HAVING SUM(CASE WHEN r.type = 0 THEN 1 ELSE 0 END) > SUM(CASE WHEN r.type = 1 THEN 1 ELSE 0 END)

	--Lấy về số phòng ban kỷ luật
	SELECT @totalBad = COUNT(DISTINCT d.id) FROM dbo.DEPARTS d 
	INNER JOIN dbo.STAFFS s ON s.depart_id = d.id 
	INNER JOIN dbo.RECORDS r ON r.staff_id = s.id
	WHERE MONTH(r.date) = @month AND YEAR(r.date) = YEAR(GETDATE())
	GROUP BY d.id
	HAVING SUM(CASE WHEN r.type = 0 THEN 1 ELSE 0 END) < SUM(CASE WHEN r.type = 1 THEN 1 ELSE 0 END)

	SELECT @total AS total, @totalGood AS totalGood, @totalBad AS totalBad

END
GO

--EXEC sp_getStatisticDepartStandardInMonth 9

-- Lấy ra thống kê tiêu chuẩn của Record theo tháng | Tổng phiếu, phiếu thưởng, phiếu phạt
CREATE PROC sp_getStatisticRecordStandardInMonth @month INT
AS
BEGIN
	IF (@month = 0)
	BEGIN
		SELECT COUNT(*) AS 'totalRecord', 
				SUM(CASE WHEN r.type = 0 THEN 1 ELSE 0 END) AS 'totalGood',
				SUM(CASE WHEN r.type = 1 THEN 1 ELSE 0 END) AS 'totalBad'
		FROM dbo.RECORDS r
		WHERE YEAR(r.date) = YEAR(GETDATE())
	END
	ELSE
	BEGIN
		SELECT COUNT(*) AS 'totalRecord', 
				SUM(CASE WHEN r.type = 0 THEN 1 ELSE 0 END) AS 'totalGood',
				SUM(CASE WHEN r.type = 1 THEN 1 ELSE 0 END) AS 'totalBad'
		FROM dbo.RECORDS r
		WHERE YEAR(r.date) = YEAR(GETDATE()) AND MONTH(r.date) = @month
	END
END
GO

--Lấy ra thống kê tiêu chuẩn của Staff , |tổng số, số nam, số nữ
CREATE PROC sp_getStatisticStaffStandard
AS
BEGIN
	SELECT COUNT(*) AS 'totalCount', 
			SUM(CASE WHEN s.gender = 1 THEN 1 ELSE 0 END) AS 'maleCount', 
			SUM(CASE WHEN s.gender = 0 THEN 1 ELSE 0 END) AS 'femaleCount' 
			FROM dbo.STAFFS s
END
GO

--Lấy ra danh sách recordDTO
CREATE PROC sp_getRecordDTO @month INT, @start INT, @size INT, @search NVARCHAR(100)
AS
BEGIN
	IF(@month = 0)
	BEGIN
		IF (@search IS NULL)
		BEGIN
			SELECT s.id AS 'staffId', s.photo AS 'staffPhoto', s.name AS 'staffName', d.name AS 'departName', 
			SUM(CASE WHEN r.type = 0 THEN 1 ELSE 0 END) AS 'numRecordGood', 
			SUM(CASE WHEN r.type = 1 THEN 1 ELSE 0 END)  AS 'numRecordBad'
			FROM dbo.STAFFS s LEFT JOIN dbo.RECORDS r ON r.staff_id = s.id LEFT JOIN dbo.DEPARTS d ON d.id = s.depart_id
			WHERE YEAR(r.date) = YEAR(GETDATE())
			GROUP BY s.id, s.name, d.name, s.photo
			ORDER BY s.id OFFSET @start ROWS FETCH NEXT @size ROW ONLY
		END
		ELSE
		BEGIN
			SELECT s.id AS 'staffId', s.photo AS 'staffPhoto', s.name AS 'staffName', d.name AS 'departName', 
			SUM(CASE WHEN r.type = 0 THEN 1 ELSE 0 END) AS 'numRecordGood', 
			SUM(CASE WHEN r.type = 1 THEN 1 ELSE 0 END)  AS 'numRecordBad'
			FROM dbo.STAFFS s LEFT JOIN dbo.RECORDS r ON r.staff_id = s.id LEFT JOIN dbo.DEPARTS d ON d.id = s.depart_id
			WHERE YEAR(r.date) = YEAR(GETDATE())
			GROUP BY s.id, s.name, d.name, s.photo
			HAVING s.id LIKE N'%'+@search+'%' 
			OR s.name LIKE N'%'+@search+'%' 
			OR d.name LIKE N'%'+@search+'%'
			ORDER BY s.id OFFSET @start ROWS FETCH NEXT @size ROW ONLY
		END
	END
	ELSE
	BEGIN
		IF (@search IS NULL)
		BEGIN
			SELECT s.id AS 'staffId', s.photo AS 'staffPhoto', s.name AS 'staffName', d.name AS 'departName', 
			SUM(CASE WHEN r.type = 0 THEN 1 ELSE 0 END) AS 'numRecordGood', 
			SUM(CASE WHEN r.type = 1 THEN 1 ELSE 0 END)  AS 'numRecordBad'
			FROM dbo.STAFFS s LEFT JOIN dbo.RECORDS r ON r.staff_id = s.id LEFT JOIN dbo.DEPARTS d ON d.id = s.depart_id
			WHERE YEAR(r.date) = YEAR(GETDATE())
			AND MONTH(r.date) = @month
			GROUP BY s.id, s.name, d.name, s.photo
			ORDER BY s.id OFFSET @start ROWS FETCH NEXT @size ROW ONLY
		END
		ELSE
		BEGIN
			SELECT s.id AS 'staffId', s.photo AS 'staffPhoto', s.name AS 'staffName', d.name AS 'departName', 
			SUM(CASE WHEN r.type = 0 THEN 1 ELSE 0 END) AS 'numRecordGood', 
			SUM(CASE WHEN r.type = 1 THEN 1 ELSE 0 END)  AS 'numRecordBad'
			FROM dbo.STAFFS s LEFT JOIN dbo.RECORDS r ON r.staff_id = s.id LEFT JOIN dbo.DEPARTS d ON d.id = s.depart_id
			WHERE YEAR(r.date) = YEAR(GETDATE())
			AND MONTH(r.date) = @month
			GROUP BY s.id, s.name, d.name, s.photo
			HAVING s.id LIKE N'%'+@search+'%' 
			OR s.name LIKE N'%'+@search+'%' 
			OR d.name LIKE N'%'+@search+'%'
			ORDER BY s.id OFFSET @start ROWS FETCH NEXT @size ROW ONLY
		END
	END
END
GO

/* 5 tạo người dùng */

-- 1. quản trị viên của cơ sở dữ liệu J5_ASM

	-- 1.1 tạo tên đăng nhập
	use master
	if exists (select name from master.sys.server_principals where name = 'J5_ASM_QTV')
		begin
			drop login J5_ASM_QTV
		end
	go
	create login J5_ASM_QTV with password = '123', default_database = J5_ASM

	-- 1.2 tạo người dùng cho tên đăng nhập trên
	use J5_ASM
	if exists (select name from sys.database_principals where name = 'J5_ASM_QTV')
		begin
			drop user J5_ASM_QTV
		end
	go
	create user J5_ASM_QTV for login J5_ASM_QTV

	-- 1.3 thêm vai trò cho người dùng trên
	use J5_ASM
	alter role [db_owner] add member J5_ASM_QTV;

/* hết phần 5 tạo người dùng */


--EXEC dbo.sp_getRecordDTO 0, 0, 5, NULL -- int
--SELECT d.id, d.name, 
--SUM(CASE WHEN s.gender = 0 THEN 1 ELSE 0 END) AS 'tong nhan vien nam',
--SUM(CASE WHEN s.gender = 1 THEN 1 ELSE 0 END) AS 'tong nhan vien Nu',
--SUM(CASE WHEN r.type = 0 THEN 1 ELSE 0 END) AS 'TongDiemGhiThuong',
--SUM(CASE WHEN r.type = 1 THEN 1 ELSE 0 END) AS 'TongDiemNhanPhat'
--FROM dbo.DEPARTS d LEFT JOIN dbo.STAFFS s ON s.depart_id = d.id 
--LEFT JOIN dbo.RECORDS r ON r.staff_id = s.id
--GROUP BY d.id, d.name
