
--DROP DATABASE PharmacyManagement
--SP_WHO
--KILL 77

--USE master;
--ALTER DATABASE PharmacyManagement SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
--DROP DATABASE PharmacyManagement;



--CONTENTS PAGE:
--1. DATABASE, TABLE, CONSTRAINTS
--2. TRIGGER
--3. INSERT DATA

--1. CREATE DATABASE
CREATE DATABASE PharmacyManagement

USE PharmacyManagement
GO

--CREATE TABLE
CREATE TABLE Manager
	(managerID CHAR(5) NOT NULL PRIMARY KEY,
	managerName NVARCHAR(50),
	birthDate DATE CHECK 
		(CASE 
			WHEN (MONTH(GETDATE()) > MONTH(birthDate))
				OR (MONTH(GETDATE()) = MONTH(birthDate) AND DAY(GETDATE()) >= DAY(birthDate)) 
			THEN DATEDIFF(YEAR, birthDate, GETDATE())
			ELSE DATEDIFF(YEAR, birthDate, GETDATE()) - 1
		END >= 21),
		--Employee age over 21.
	phoneNumber VARCHAR(10))

CREATE TABLE Employee
	(employeeID CHAR(6) NOT NULL PRIMARY KEY,
	employeeName NVARCHAR(50),
	phoneNumber CHAR(10),
	birthDate DATE CHECK
		(CASE 
			WHEN (MONTH(GETDATE()) > MONTH(birthDate))
				OR (MONTH(GETDATE()) = MONTH(birthDate) AND DAY(GETDATE()) >= DAY(birthDate)) 
			THEN DATEDIFF(YEAR, birthDate, GETDATE())
			ELSE DATEDIFF(YEAR, birthDate, GETDATE()) - 1
		END >= 21),
		--Employee age over 21.
	gender BIT,
	degree NVARCHAR(30),
	email NVARCHAR(50),
	address NVARCHAR(50),
	status BIT)

CREATE TABLE Account
	(accountID VARCHAR(6) NOT NULL PRIMARY KEY,
	password VARCHAR(30),
	managerID CHAR(5) REFERENCES Manager(managerID),
	employeeID CHAR(6) REFERENCES Employee(employeeID))


CREATE TABLE AdministrationRoute
	(administrationID CHAR(4) NOT NULL PRIMARY KEY,
	administraionName NVARCHAR(40))

CREATE TABLE Customer
	(customerName NVARCHAR(30),
	phoneNumber CHAR(10) NOT NULL PRIMARY KEY,
	customerID CHAR(10) NOT NULL UNIQUE,
	gender BIT,
	birthDate DATE,
	email NVARCHAR(30),
	point INT,
	address NVARCHAR(50))

CREATE TABLE PromotionType
	(promoTypeID CHAR(5) NOT NULL PRIMARY KEY,
	promoTypeName NVARCHAR(40))

CREATE TABLE Promotion
	(promotionID CHAR(16) NOT NULL PRIMARY KEY,
	promotionName NVARCHAR(30),
	startDate DATE,
	endDate DATE, --CHECK (endDate > startDate)
	disCountPercent FLOAT,
	status BIT,
	promotionTypeID CHAR(5) REFERENCES PromotionType(promoTypeID) )

CREATE TABLE Prescription 
	(prescriptionID CHAR(14) NOT NULL PRIMARY KEY,
	createdDate DATE,
	diagnosis NVARCHAR(20),
	medicalFacility NVARCHAR(20))

CREATE TABLE Orders
	(orderID CHAR(15) NOT NULL PRIMARY KEY,
	orderDate DATETIME CHECK (orderDate <= CAST(GETDATE() AS DATETIME)),
	shipToAddress NVARCHAR(50),
	paymentMethod NVARCHAR(20),
	discount MONEY,
	totalDue MONEY,
	customerID CHAR(10) REFERENCES Customer(phoneNumber),
	employeeID CHAR(6) REFERENCES Employee(employeeID),
	prescriptionID CHAR(14) REFERENCES Prescription(prescriptionID))

CREATE TABLE Category
	(categoryID CHAR(5) NOT NULL PRIMARY KEY,
	categoryName NVARCHAR(50))

CREATE TABLE Vendor
	(vendorID CHAR(7) NOT NULL PRIMARY KEY,
	vendorName NVARCHAR(50),
	country NVARCHAR(30))

CREATE TABLE Product(
	productID CHAR(14) NOT NULL PRIMARY KEY,
	productName NVARCHAR(50),
	taxPercentage FLOAT,
	purchasePrice MONEY,
	registrationNumber VARCHAR(16) UNIQUE,
	endDate DATE,
	promotionID CHAR(16) REFERENCES Promotion(promotionID),
	vendorID CHAR(7) REFERENCES Vendor(vendorID),
	categoryID CHAR(5) REFERENCES Category(categoryID),
	unitNote VARCHAR(60)
	)

CREATE TABLE Unit(
	unitID CHAR(3) NOT NULL PRIMARY KEY,
	name VARCHAR(20) NULL,
	describe NVARCHAR(50) NULL,)

CREATE TABLE ProductUnit(
	productID CHAR(14) NOT NULL REFERENCES Product(productID),
	unitID CHAR(3) NOT NULL REFERENCES Unit(unitID),
	inStock INT NULL,
	sellPrice MONEY NOT NULL, 
	PRIMARY KEY (productID, unitID))

CREATE TABLE Medicine
	(medicineID CHAR(14) NOT NULL PRIMARY KEY,
	activeIngredient NVARCHAR(20),
	conversionUnit NVARCHAR(20),
	administrationID CHAR(4) REFERENCES AdministrationRoute(administrationID),
	CONSTRAINT FK_medicineID
		FOREIGN KEY (medicineID) REFERENCES Product(productID))

CREATE TABLE FunctionalFood
	(functionalFoodID CHAR(14) NOT NULL PRIMARY KEY REFERENCES Product(productID),
	mainNutrients NVARCHAR(20),
	supplementaryIngredients NVARCHAR(20))

CREATE TABLE MedicalSupplies
	(medicalSupplyID CHAR(14) NOT NULL PRIMARY KEY REFERENCES Product(productID),
	medicalSupplyName NVARCHAR(20))

CREATE TABLE OrderDetail (       
    orderID CHAR(15) NOT NULL REFERENCES Orders(orderID),                 
    productID CHAR(14) NOT NULL REFERENCES Product(productID), 
	unitID CHAR(3) NOT NULL REFERENCES Unit(unitID),
    orderQuantity INT CHECK (orderQuantity > 0), 
    lineTotal MONEY,                          
    PRIMARY KEY (orderID, productID, unitID))
GO

--2. TRIGGER 
------------------------- TRIGGER TÍNH TOTALDUE -----------------------
CREATE TRIGGER UpdateTotalDue
ON OrderDetail
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
	UPDATE Orders
    SET totalDue = (
        SELECT SUM(lineTotal) - Orders.discount
        FROM OrderDetail OD
        WHERE OD.orderID = Orders.orderID)
    WHERE orderID IN (SELECT DISTINCT orderID FROM Inserted)
       OR orderID IN (SELECT DISTINCT orderID FROM Deleted)
END
GO

----------------------- TRIGGER TÍNH LINETOTAL ---------------------
CREATE TRIGGER UpdateLineTotal
ON OrderDetail
AFTER INSERT, UPDATE
AS
BEGIN
	UPDATE OrderDetail
	SET lineTotal = od.orderQuantity * p.sellPrice
	FROM OrderDetail od
		JOIN ProductUnit p ON od.productID = p.productID
		AND	od.unitID = p.unitID 
	WHERE od.orderID IN (SELECT orderID FROM inserted)
		AND od.productID IN (SELECT productID FROM inserted)
		AND od.unitID IN (SELECT unitID FROM inserted)
		AND od.orderID LIKE 'OR%'
END
GO

alter table [dbo].[OrderDetail]
add unitID char(3) not null default 'U03' references Unit (unitID)

alter table [dbo].[OrderDetail]
add constraint PK_O123455 primary key ([orderID],[productID], unitID )

-----------THỦ TỤC TÌM KIẾM SẢN PHẨM---------------
CREATE PROCEDURE sp_SearchProduct
    @ProductName NVARCHAR(100) = NULL,
    @CategoryName NVARCHAR(100) = NULL,
    @VendorName NVARCHAR(100) = NULL,
    @AdministrationRouteID NVARCHAR(100) = NULL
AS
BEGIN
    SELECT *
    FROM Product p
    LEFT JOIN Vendor v ON p.vendorID = v.vendorID
    LEFT JOIN Category c ON p.categoryID = c.categoryID
    LEFT JOIN Medicine m ON p.productID = m.medicineID
	LEFT JOIN AdministrationRoute ar ON m.administrationID = ar.administrationID
	LEFT JOIN MedicalSupplies s ON p.productID = s.medicalSupplyID
	LEFT JOIN FunctionalFood f ON p.productID = f.functionalFoodID

    WHERE
        (@ProductName IS NULL OR p.productName LIKE '%' + @ProductName + '%')
        --AND (@DateColumn IS NULL OR p.endDate = @DateColumn)
        AND (@CategoryName IS NULL OR c.categoryName LIKE '%' + @CategoryName + '%')
        AND (@VendorName IS NULL OR v.vendorName LIKE '%' + @VendorName + '%')
        AND (@AdministrationRouteID IS NULL OR m.administrationID LIKE '%' + @AdministrationRouteID + '%')
END;
GO
-----------THỦ TỤC CẬP NHẬT NGÀY HẾT HẠN CỦA KHUYẾN MÃI ---------------
CREATE PROCEDURE UpdatePromotionStatus
AS
BEGIN
    UPDATE Promotion
    SET Status = 0
    WHERE EndDate < GETDATE() - 1
    AND Status = 1;
END;

--3. INSERT DATA
------------------- THÊM DỮ LIỆU ---------------------------
# --Thêm dữ liệu đường dùng
INSERT INTO AdministrationRoute (administrationID ,administraionName)
VALUES ('1.01', N'Uống'),
('1.02', N'Ngậm'),
('1.03', N'Nhai'),
('1.04', N'Đặt dưới lưỡi'),
('1.05', N'Ngậm dưới lưỡi'),
('2.01', N'Tiêm bắp'),
('2.02', N'Tiêm dưới da'),
('2.03', N'Tiêm trong da'),
('2.04', N'Tiêm tĩnh mạch'),
('2.05', N'Tiêm truyền tĩnh mạch'),
('2.06', N'Tiêm vào ổ khớp'),
('2.07', N'Tiêm nội nhãn cầu'),
('2.08', N'Tiêm trong dịch kính của mắt'),
('2.09', N'Tiêm vào các khoang của cơ thể'),
('2.10', N'Tiêm'),
('2.11', N'Tiêm động mạch khối u'),
('2.12', N'Tiêm vào khoang tự nhiên'),
('2.13', N'Tiêm vào khối u'),
('2.14', N'Truyền tĩnh mạch'),
('2.15', N'Tiêm truyền'),
('3.01', N'Bôi'),
('3.02', N'Xoa ngoài'),
('3.03', N'Dán trên da'),
('3.04', N'Xịt ngoài da'),
('3.05', N'Dùng ngoài'),
('4.01', N'Đặt âm đạo'),
('4.02', N'Đặt hậu môn'),
('4.03', N'Thụt hậu môn - trực tràng'),
('4.04', N'Đặt'),
('4.05', N'Đặt tử cung'),
('4.06', N'Thụt'),
('5.01', N'Phun mù'),
('5.02', N'Dạng hít'),
('5.03', N'Bột hít'),
('5.04', N'Xịt'),
('5.05', N'Khí dung'),
('5.06', N'Đường hô hấp'),
('5.07', N'Xịt mũi'),
('5.08', N'Xịt họng'),
('5.09', N'Thuốc mũi'),
('5.10', N'Nhỏ mũi'),
('6.01', N'Nhỏ mắt'),
('6.02', N'Tra mắt'),
('6.03', N'Thuốc mắt'),
('6.04', N'Nhỏ tai'),
('9.01', N'Áp ngoài da'),
('9.02', N'Áp sát khối u'),
('9.03', N'Bình khí lỏng hoặc nến'),
('9.04', N'Bính khí nén'),
('9.05', N'Bôi trực tràng'),
('9.06', N'Đánh tưa lưỡi'),
('9.07', N'Cấy vào khối u'),
('9.08', N'Chiếu ngoài'),
('9.09', N'Dung dịch'),
('9.10', N'Dung dịch rửa'),
('9.11', N'Dung dịch thẩm phân'),
('9.12', N'Phun'),
('9.13', N'Túi'),
('9.14', N'Hỗn dịch'),
('9.15', N'Bột đông khô để pha hỗn dịch'),
('9.16', N'Phức hợp lipid'),
('9.17', N'Liposome'),
('9.18', N'Polymeric micelle');

# --Thêm dữ liệu promotionType
INSERT INTO PromotionType(promoTypeID, promoTypeName)
VALUES('PR01', N'Khuyến mãi từ nhà cung cấp'),
('PR02', N'Khuyến mãi theo tháng'),
('PR03', N'Khuyến mãi theo hạn dùng'),
('PR00', N'Khuyến mãi ngoài');


--Thêm dữ liệu Promotion 
INSERT INTO Promotion(promotionID, promotionName, startDate, endDate)
VALUES ('PR02102410102401', N'Khuyến mãi đợt 1 tháng 10', '10-02-2024', '10-10-2024'),
('PR20102430102402', N'Khuyến mãi đợt 2 tháng 10', '10-20-2024', '10-30-2024'),
('PR02112410112401', N'Khuyến mãi đợt 1 tháng 11', '11-02-2024', '11-10-2024')

--Thêm dữ liệu Category
INSERT INTO Category(categoryID, categoryName)
VALUES('CA001', N'Nhóm thuốc giảm đau, hạ sốt'),
('CA002',N'Nhóm thuốc kháng sinh'),
('CA003',N'Nhóm thuốc kháng viêm'),
('CA004',N'Nhóm thuốc kháng virus'),
('CA005',N'Nhóm thuốc ho và long đờm'),
('CA006',N'Nhóm thuốc dạ dày'),
('CA007',N'Nhóm thuốc tiêu hóa'),
('CA008',N'Nhóm thuốc trị rối loạn kinh nguyệt'),
('CA009',N'Nhóm thuốc huyết áp - tim mạch'),
('CA010',N'Nhóm thuốc điều trị rối loạn lipid máu'),
('CA011',N'Nhóm thuốc tránh thai'),
('CA012',N'Nhóm thuốc kháng nấm'),
('CA013',N'Nhóm thuốc cải thiện tuần hoàn máu não, chóng mặt'),
('CA014',N'Nhóm thuốc điều trị các bệnh về gan'),
('CA015',N'Nhóm thuốc điều trị bệnh sỏi thận'),
('CA016',N'Nhóm thuốc xổ giun'),
('CA017',N'Nhóm thuốc nhỏ mắt'),
('CA018',N'Nhóm thuốc dùng ngoài'),
('CA019',N'Nhóm vật tư y tế'),
('CA020',N'Nhóm thực phẩm chức năng')

-- Thêm dữ liệu Vendor
INSERT INTO Vendor(vendorID, vendorName, country)
VALUES('VDVN001', N'Công ty CP Dược Phẩm Agimexpharm', N'Việt Nam'),
('VDVN002', N'Công ty CP Dược Phẩm Savi', N'Việt Nam'),
('VDVN003', N'Công ty CP Dược Phẩm Khánh Hòa', N'Việt Nam')

-- Thêm dữ liệu Manager
INSERT INTO Manager(managerID, managerName, phoneNumber, birthDate)
VALUES('MN001', N'Huỳnh Thanh Giang', '0961416115', '05-12-1994')

-- Thêm dữ liệu Employee
INSERT INTO Employee(employeeID, employeeName, phoneNumber, birthDate, gender, degree, email, address, status)
VALUES('EP1501', N'Nguyễn Thị Mỹ Duyên', '0961416115', '02-27-1999', 1, N'Thạc sĩ', 'job@yourbusinessname.com',N'12, Nguyễn Văn Bảo, P.4, Q.GV, HCM',1),
('EP0302', N'Hồ Quang Nhân', '0399754203', '07-19-1999', 0, N'Đại học', 'hqn19072004@gmail.com',N'12, Nguyễn Văn Bảo, P.4, Q.GV, HCM',1),
('EP0903', N'Phan Phước Hiệp', '0961416115', '11-27-1999', 0, N'Đại học','job@yourbusinessname.com',N'12, Nguyễn Văn Bảo, P.4, Q.GV, HCM',1)

-- Thêm dữ liệu Account
INSERT INTO Account (accountID, password, managerID, employeeID)
VALUES('MN001', 'MN001@', 'MN001', null),
('EP1501', 'EP1501@', 'MN001','EP1501' ),
('EP0302', 'EP0302@', 'MN001', 'EP0302'),
('EP0903', 'EP0903@', 'MN001', 'EP0903')


-- Thêm dữ liệu Customer
INSERT INTO Customer (customerName, phoneNumber, customerID, gender, birthDate, point,email,address)
VALUES(N'Phạm Phương Nam', '0123456789', 'C271024001', 0, '05-07-2000', 20,'job@yourbusinessname.com',N'12, Nguyễn Văn Bảo, P.4, Q.GV, HCM'),
	  (N'Hồ Quang Minh', '0123456798', 'C271024002', 0,'12-26-2004', 0,'job@yourbusinessname.com',N'12, Nguyễn Văn Bảo, P.4, Q.GV, HCM'),
	  (N'Nguyễn Hiền', '0123456987', 'C271024003', 1,'09-02-2000', 10,'job@yourbusinessname.com',N'12, Nguyễn Văn Bảo, P.4, Q.GV, HCM')

--Thêm dữ liệu Presciption
INSERT INTO [Prescription] (prescriptionID, createdDate, diagnosis, medicalFacility)
VALUES('790250000001-c', '10-02-2024', N'Mỡ máu', '79025'),
('790250000002-c', '10-02-2024', N'Tiểu đường', '79025'),
('790240000001-c', '10-02-2024', N'Hở mạch vành', '79024')


--Thêm dữ liệu Order
INSERT INTO Orders (orderID, orderDate, shipToAddress, paymentMethod, customerID, employeeID, prescriptionID)
VALUES ('OR3009241501001', CAST('2024-09-30 10:30:00' AS DATETIME), N'Vười Lài', 'BANK_TRANSFER', '0123456789', 'EP1501', '790250000001-c'),
('OR3010240302002', CAST(GETDATE() AS DATETIME), N'Vười Lài', 'BANK_TRANSFER', '0123456987', 'EP0302', '790250000002-c'),
('OR3010240903003', CAST(GETDATE() AS DATETIME), N'Quận Tân Bình', 'BANK_TRANSFER', '0123456798', 'EP0903', '790240000001-c'),
('OR2010241501004', CAST('2024-09-30 10:30:00' AS DATETIME), N'Quận Bình Tân', 'BANK_TRANSFER', '0123456798', 'EP1501', '790250000002-c'),
('OR3010241501005', CAST(GETDATE() AS DATETIME), N'Quận 1', 'BANK_TRANSFER', '0123456987', 'EP1501', null),
('OR0806241501006', CAST('2024-06-08 10:30:00' AS DATETIME), N'Vười Lài', 'BANK_TRANSFER', '0123456789', 'EP1501', null)


--Thêm dữ liệu Product
INSERT INTO Product(productID, productName, quantityInStock, taxPercentage, promotionID, vendorID, categoryID, registrationNumber, purchasePrice, endDate)
VALUES('PM021024000001', 'MORIHEPAMIN', 50, 0.05, null, 'VDVN001', 'CA014', '10040.KD.13.1', 190000, '2024-12-12'),
('PM021024000002', 'Optimox Sterile eye Drops', 20, 0.05, null, 'VDVN002', 'CA017', '10045.KD.13.1', 39000, '2025-12-12'),
('PS021024000003', N'Băng gạc', 100, 0.05, null, 'VDVN003', 'CA019', '10045.KD.13.2', 2000, '2026-12-01'),
('PF021024000004', N'Beroglobin', 150, 0.1, null, 'VDVN001', 'CA020', '10045.KD.13.3', 333000, '2027-01-01'),
('PS021024000005', N'Kim tiêm', 100, 0.05, null, 'VDVN003', 'CA019', '10045.KD.13.4', 2000, '2024-11-20'),
('PF021024000006', N'Beroglobin New', 10, 0.1, null, 'VDVN001', 'CA020', '10045.KD.13.5', 333000, '2025-01-01')


--Thêm dữ liệu cho bảng Unit
INSERT [dbo].[Unit] ([unitID], [name], [describe]) VALUES (N'U01', N'PILL', N'Viên')
INSERT [dbo].[Unit] ([unitID], [name], [describe]) VALUES (N'U02', N'BLISTER_PACK', N'Vỉ')
INSERT [dbo].[Unit] ([unitID], [name], [describe]) VALUES (N'U03', N'PACK', N'Gói')
INSERT [dbo].[Unit] ([unitID], [name], [describe]) VALUES (N'U04', N'BOTTLE', N'Chai')
INSERT [dbo].[Unit] ([unitID], [name], [describe]) VALUES (N'U05', N'JAR', N'Lọ')
INSERT [dbo].[Unit] ([unitID], [name], [describe]) VALUES (N'U06', N'TUBE', N'Tuýp')
INSERT [dbo].[Unit] ([unitID], [name], [describe]) VALUES (N'U07', N'BAG', N'Túi')
INSERT [dbo].[Unit] ([unitID], [name], [describe]) VALUES (N'U08', N'AMPOULE', N'Ống')
INSERT [dbo].[Unit] ([unitID], [name], [describe]) VALUES (N'U09', N'SPRAY_BOTTLE', N'Chai xịt')
INSERT [dbo].[Unit] ([unitID], [name], [describe]) VALUES (N'U10', N'AEROSOL_CAN', N'Lọ xịt')
INSERT [dbo].[Unit] ([unitID], [name], [describe]) VALUES (N'U11', N'KIT', N'Bộ kit')
INSERT [dbo].[Unit] ([unitID], [name], [describe]) VALUES (N'U12', N'BIN', N'Thùng')
INSERT [dbo].[Unit] ([unitID], [name], [describe]) VALUES (N'U13', N'BOX', N'Hộp')
GO


SELECT * FROM ProductUnit

SELECT SUM(OD.orderQuantity) AS TotalSold, SUM(P.inStock) AS TotalInStock, 
	CASE 
		WHEN SUM(P.inStock) = 0 THEN 0 
		ELSE (CAST(SUM(OD.orderQuantity) AS FLOAT) / SUM(P.inStock)) * 100 
	END AS SaleToStockRatioPercentage 
FROM OrderDetail OD JOIN ProductUnit P ON OD.productID = P.productID


SELECT * FROM Product
WHERE productID = 'PF101224000001'
SELECT * FROM ProductUnit
WHERE productID = 'PF101224000001'

SELECT SUM((PU.sellPrice - purchasePrice) * inStock) AS TotalCost 
FROM ProductUnit PU JOIN Product P ON PU.productID = P.productID

SELECT OD.productID, productName, inStock, purchasePrice, PU.sellPrice, SUM(PU.sellPrice - purchasePrice - purchasePrice * taxPercentage) AS profit, COUNT(*) AS Sold
FROM OrderDetail OD JOIN Orders O ON OD.orderID = o.orderID 
	JOIN Product P ON P.productID = OD.productID 
	JOIN ProductUnit PU ON PU.productID = P.productID
WHERE O.orderDate >= '2024-12-10' AND O.orderDate <= '2024-08-27' 
GROUP BY OD.productID, productName, inStock, purchasePrice, PU.sellPrice

ALTER TABLE Product
  ADD unitNote VARCHAR(60)

