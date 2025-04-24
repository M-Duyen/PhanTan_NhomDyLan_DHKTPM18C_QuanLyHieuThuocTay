INSERT INTO pharmacy_management.administration_routes (administration_route_id, administration_route_name)
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
INSERT INTO pharmacy_management.promotion_types(promotion_type_id, promotion_type_name)
VALUES ('PR01', N'Khuyến mãi từ nhà cung cấp'),
       ('PR02', N'Khuyến mãi theo tháng'),
       ('PR03', N'Khuyến mãi theo hạn dùng'),
       ('PR00', N'Khuyến mãi ngoài');


# --Thêm dữ liệu Promotion
INSERT INTO pharmacy_management.promotions(promotion_id, promotion_name, start_date, end_date, stats, promotion_type_id, discount)
VALUES ('PR02102410102401', N'Khuyến mãi đợt 1 tháng 10', '2024-02-10', '2024-10-10', 0, 'PR01', 0.5),
       ('PR20102430102402', N'Khuyến mãi đợt 2 tháng 10', '2024-10-20', '2024-10-30', 0, 'PR02', 0.3),
       ('PR02112410112401', N'Khuyến mãi đợt 1 tháng 11', '2024-11-02', '2024-11-10', 0, 'PR02', 0.6),
       ('PR03022505052501', N'Khuyến mãi đợt 1 tháng 04', '2025-02-03', '2025-05-05', 1, 'PR03', 0.3),
       ('PR16042512062502', N'Khuyến mãi đợt 2 tháng 04', '2025-04-16', '2025-06-12', 0, 'PR00', 0.5);


#     --Thêm dữ liệu Category
INSERT INTO pharmacy_management.categories(category_id, category_name)
VALUES ('CA001', N'Nhóm thuốc giảm đau, hạ sốt'),
       ('CA002', N'Nhóm thuốc kháng sinh'),
       ('CA003', N'Nhóm thuốc kháng viêm'),
       ('CA004', N'Nhóm thuốc kháng virus'),
       ('CA005', N'Nhóm thuốc ho và long đờm'),
       ('CA006', N'Nhóm thuốc dạ dày'),
       ('CA007', N'Nhóm thuốc tiêu hóa'),
       ('CA008', N'Nhóm thuốc trị rối loạn kinh nguyệt'),
       ('CA009', N'Nhóm thuốc huyết áp - tim mạch'),
       ('CA010', N'Nhóm thuốc điều trị rối loạn lipid máu'),
       ('CA011', N'Nhóm thuốc tránh thai'),
       ('CA012', N'Nhóm thuốc kháng nấm'),
       ('CA013', N'Nhóm thuốc cải thiện tuần hoàn máu não, chóng mặt'),
       ('CA014', N'Nhóm thuốc điều trị các bệnh về gan'),
       ('CA015', N'Nhóm thuốc điều trị bệnh sỏi thận'),
       ('CA016', N'Nhóm thuốc xổ giun'),
       ('CA017', N'Nhóm thuốc nhỏ mắt'),
       ('CA018', N'Nhóm thuốc dùng ngoài'),
       ('CA019', N'Nhóm vật tư y tế'),
       ('CA020', N'Nhóm thực phẩm chức năng');

-- Thêm dữ liệu Vendor
INSERT INTO pharmacy_management.vendors(vendor_id, vendor_name, country)
VALUES ('VDVN001', N'Công ty CP Dược Phẩm Agimexpharm', N'Việt Nam'),
       ('VDVN002', N'Công ty CP Dược Phẩm Savi', N'Việt Nam'),
       ('VDVN003', N'Công ty CP Dược Phẩm Khánh Hòa', N'Việt Nam');

-- Thêm dữ liệu Manager
INSERT INTO pharmacy_management.managers(manager_id, manager_name, phone_number, birth_date)
VALUES ('MN001', N'Huỳnh Thanh Giang', '0961416115', '1994-05-12');

-- Thêm dữ liệu Employee
INSERT INTO pharmacy_management.employees(employee_id, employee_name, phone_number, birth_date, gender, degree, email, address, status)
VALUES('EP1501', N'Nguyễn Thị Mỹ Duyên', '0961416115', '1999-02-27', 1, N'Thạc sĩ', 'job@yourbusinessname.com', N'12, Nguyễn Văn Bảo, P.4, Q.GV, HCM', 1),
      ('EP0302', N'Hồ Quang Nhân', '0399754203', '1999-07-19', 0, N'Đại học', 'hqn19072004@gmail.com', N'12, Nguyễn Văn Bảo, P.4, Q.GV, HCM', 1),
      ('EP0903', N'Phan Phước Hiệp', '0961416115', '1999-11-27', 0, N'Đại học', 'job@yourbusinessname.com', N'12, Nguyễn Văn Bảo, P.4, Q.GV, HCM', 1);


-- Thêm dữ liệu Account
INSERT INTO pharmacy_management.accounts (account_id, password, manager_id, employee_id)
VALUES ('MN001', 'MN001@', 'MN001', null),
       ('EP1501', 'EP1501@', 'MN001', 'EP1501'),
       ('EP0302', 'EP0302@', 'MN001', 'EP0302'),
       ('EP0903', 'EP0903@', 'MN001', 'EP0903');


-- Thêm dữ liệu Customer
INSERT INTO pharmacy_management.customers (customer_name, phone_number, customer_id, gender, birth_date, point, email, addr)
VALUES (N'Trần Nguyễn Gia Bảo', '0123456789', 'C271024001', 0, '2000-07-05', 20, 'giabao@gmail.com',
        N'12, Nguyễn Văn Bảo, P.4, Q.GV, HCM'),
       (N'Nguyễn Thanh Kiều', '0123456798', 'C271024002', 0, '2004-12-26', 0, 'thanhkieu@gmail.com',
        N'12, Nguyễn Văn Bảo, P.4, Q.GV, HCM'),
       (N'Nguyễn Hiền', '0123456987', 'C271024003', 1, '2000-02-09', 10, 'hien2281@gmail.com',
        N'12, Nguyễn Văn Bảo, P.4, Q.GV, HCM');

-- Thêm dữ liệu Presciption
INSERT INTO pharmacy_management.prescriptions (prescription_id, create_date, diagnosis, medical_facility)
VALUES ('790250000001-c', '2024-02-10', N'Mỡ máu', '79025'),
       ('790250000002-c', '2024-02-10', N'Tiểu đường', '79025'),
       ('790240000001-c', '2024-02-10', N'Hở mạch vành', '79024');

INSERT INTO pharmacy_management.orders (order_id, order_date, ship_to_address, payment_method, customer_id, employee_id, prescription_id, discount)
VALUES ('OR3009241501001', CAST('2024-09-30 10:30:00' AS DATETIME), N'Vười Lài', 'BANK_TRANSFER', '0123456789',
        'EP1501', '790250000001-c',0),
       ('OR3010240302002', NOW(), N'Vười Lài', 'BANK_TRANSFER', '0123456987', 'EP0302', '790250000002-c',0),
       ('OR3010240903003', NOW(), N'Quận Tân Bình', 'BANK_TRANSFER', '0123456798', 'EP0903', '790240000001-c',0),
       ('OR2010241501004', CAST('2024-09-30 10:30:00' AS DATETIME), N'Quận Bình Tân', 'BANK_TRANSFER', '0123456798',
        'EP1501', '790250000002-c',0),
       ('OR3010241501005', NOW(), N'Quận 1', 'BANK_TRANSFER', '0123456987', 'EP1501', null,0),
       ('OR0806241501006', CAST('2024-06-08 10:30:00' AS DATETIME), N'Vười Lài', 'BANK_TRANSFER', '0123456789',
        'EP1501', null,0);


-- Thêm dữ liệu Product
INSERT INTO pharmacy_management.products(product_id, product_name, quantity_in_stock, tax_percentage, promotion_id, vendor_id, category_id,
                     registration_number, purchase_price, end_date, unit_note)
VALUES ('PM021024000001', 'MORIHEPAMIN', 50, 0.05, null, 'VDVN001', 'CA014', '10040.KD.13.1', 190000, '2026-12-12', ' BOX(293), BLISTER_PACK(10), PILL(6)'),
       ('PM021024000002', 'Optimox Sterile eye Drops', 20, 0.05, null, 'VDVN002', 'CA017', '10045.KD.13.1', 39000,
        '2025-12-12', 'BOX(293), BLISTER_PACK(10), PILL(6)'),
       ('PS021024000003', N'Băng gạc', 100, 0.05, null, 'VDVN003', 'CA019', '10045.KD.13.2', 2000, '2026-12-01', ' BOX(293), BLISTER_PACK(10), PILL(6)'),
       ('PF021024000004', N'Beroglobin', 150, 0.1, null, 'VDVN001', 'CA020', '10045.KD.13.3', 333000, '2027-01-01', ' BOX(293), BLISTER_PACK(10), PILL(6)'),
       ('PS021024000005', N'Kim tiêm', 100, 0.05, null, 'VDVN003', 'CA019', '10045.KD.13.4', 2000, '2025-11-20', ' BOX(293), BLISTER_PACK(10), PILL(6)'),
       ('PF021024000006', N'Beroglobin New', 10, 0.1, null, 'VDVN001', 'CA020', '10045.KD.13.5', 333000,
        '2025-09-05', 'BOX(293), BLISTER_PACK(10), PILL(6)');

INSERT INTO pharmacy_management.order_details (unit, order_quantity, product_id, order_id)
VALUES ('PILL', 2, 'PM021024000001', 'OR3009241501001'),
       ('BOTTLE', 3, 'PM021024000002', 'OR3010240302002'),
       ('JAR', 5, 'PS021024000003', 'OR3010240903003'),
       ('PACK', 10, 'PF021024000004', 'OR2010241501004'),
       ('BLISTER_PACK', 7, 'PF021024000006', 'OR3010241501005');


-- Thêm dữ liệu cho bảng Unit
# INSERT INTO pharmacy_management.units (unit_id, unit_name, description)
# VALUES (N'U01', N'PILL', N'Viên'),
#        (N'U02', N'BLISTER_PACK', N'Vỉ'),
#        (N'U03', N'PACK', N'Gói'),
#        (N'U04', N'BOTTLE', N'Chai'),
#        (N'U05', N'JAR', N'Lọ'),
#        (N'U06', N'TUBE', N'Tuýp'),
#        (N'U07', N'BAG', N'Túi'),
#        (N'U08', N'AMPOULE', N'Ống'),
#        (N'U09', N'SPRAY_BOTTLE', N'Chai xịt'),
#        (N'U10', N'AEROSOL_CAN', N'Lọ xịt'),
#        (N'U11', N'KIT', N'Bộ kit'),
#        (N'U12', N'BIN', N'Thùng'),
#        (N'U13', N'BOX', N'Hộp');


# DROP DATABASE pharmacy_management