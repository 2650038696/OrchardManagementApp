CREATE DATABASE orchard_management;

USE orchard_management;


-- 地块表
CREATE TABLE plots (
    id VARCHAR(20) PRIMARY KEY,
    location VARCHAR(255),			-- 地块的位置
    AREA FLOAT,			-- 地块的面积（单位：平方米）
    sunlight_duration FLOAT,		-- 地块在不同季节的平均日照时长（单位：小时）
    soil_features VARCHAR(255)		-- 地块的土壤特征描述
);


-- 果树品种表
CREATE TABLE tree_varieties (
    id VARCHAR(20) PRIMARY KEY,
    NAME VARCHAR(255),		-- 果树的品种名称
    planting_time DATE,		-- 果树的种植时间
    age INT,		-- 果树的年龄（单位：年）	
    plot_id VARCHAR(20),		-- 对应种植地块的ID
    quantity INT,		-- 果树的数量（棵）
    AREA FLOAT,		-- 果树占地的面积（单位：平方米）
    yield_per_year FLOAT,		-- 每年果树的产量（单位：公斤）
    quality_ratio FLOAT,		-- 不同品质水果的比例
    FOREIGN KEY (plot_id) REFERENCES plots(id)		
);


-- 灌溉记录表
CREATE TABLE irrigation_records (
    id VARCHAR(20) PRIMARY KEY,
    plot_id VARCHAR(20),		-- 对应地块的ID
    DATE DATE,		-- 灌溉日期
    details VARCHAR(255),		-- 灌溉的具体内容和细节
    FOREIGN KEY (plot_id) REFERENCES plots(id)
);


-- 施肥记录表
CREATE TABLE fertilization_records (
    id VARCHAR(20) PRIMARY KEY,
    plot_id VARCHAR(20),		-- 对应地块的ID
    DATE DATE,		-- 施肥日期
    details VARCHAR(255),		-- 施肥的具体内容和细节
    FOREIGN KEY (plot_id) REFERENCES plots(id)
);

-- 防病记录表
CREATE TABLE disease_prevention_records (
    id VARCHAR(20) PRIMARY KEY,
    plot_id VARCHAR(20),		-- 对应地块的ID
    DATE DATE,		-- 防病日期
    details VARCHAR(255),		-- 防病措施的具体内容和细节
    FOREIGN KEY (plot_id) REFERENCES plots(id)
);


-- 病害记录表
CREATE TABLE disease_records (
    id VARCHAR(20) PRIMARY KEY,
    plot_id VARCHAR(20),		-- 对应地块的ID
    DATE DATE,		-- 病害发生的日期
    details VARCHAR(255),		-- 病害的具体描述和处理细节
    FOREIGN KEY (plot_id) REFERENCES plots(id)
);


-- 防虫记录表
CREATE TABLE pest_prevention_records (
    id VARCHAR(20) PRIMARY KEY,
    plot_id VARCHAR(20),		-- 对应地块的ID
    DATE DATE,		-- 防虫日期
    details VARCHAR(255),		-- 防虫措施的具体内容和细节
    FOREIGN KEY (plot_id) REFERENCES plots(id)
);


-- 虫害记录表
CREATE TABLE pest_records (
    id VARCHAR(20) PRIMARY KEY,
    plot_id VARCHAR(20),		-- 对应地块的ID
    DATE DATE,		-- 虫害发生的日期
    details VARCHAR(255),		-- 虫害的具体描述和处理细节
    FOREIGN KEY (plot_id) REFERENCES plots(id) 
);


-- 除草记录表
CREATE TABLE weeding_records (
    id VARCHAR(20) PRIMARY KEY,
    plot_id VARCHAR(20),		-- 对应地块的ID
    DATE DATE,		-- 除草日期
    details VARCHAR(255),		-- 除草的具体内容和细节
    FOREIGN KEY (plot_id) REFERENCES plots(id)
);


-- 修剪记录表
CREATE TABLE pruning_records (
    id VARCHAR(20) PRIMARY KEY,
    plot_id VARCHAR(20),		-- 对应地块的ID
    DATE DATE,		-- 修剪日期
    details VARCHAR(255),		-- 修剪的具体内容和细节
    FOREIGN KEY (plot_id) REFERENCES plots(id)
);


-- 土壤管理记录表
CREATE TABLE soil_management_records (
    id VARCHAR(20) PRIMARY KEY,
    plot_id VARCHAR(20),		-- 对应地块的ID
    DATE DATE,		-- 土壤管理的日期
    details VARCHAR(255),		-- 土壤管理的具体内容和细节（如深翻、改良等）
    FOREIGN KEY (plot_id) REFERENCES plots(id)
);


-- 水果采摘记录表
CREATE TABLE harvest_records (
    id VARCHAR(20) PRIMARY KEY,
    variety_id VARCHAR(20),		-- 对应果树品种的ID	
    DATE DATE,		-- 采摘日期
    quality VARCHAR(255),		-- 水果的品质
    quantity INT,		-- 采摘的水果数量（单位：公斤）
    price FLOAT,		-- 水果的销售价格（单位：元）
    destination VARCHAR(255),		-- 水果的销售去向（如市场、超市等）
    FOREIGN KEY (variety_id) REFERENCES tree_varieties(id)
);





-- 删除地块触发器
DROP TRIGGER IF EXISTS after_plot_delete;
DELIMITER $$
CREATE TRIGGER after_plot_delete
BEFORE DELETE ON plots
FOR EACH ROW
BEGIN
DECLARE D_id VARCHAR(20);
SET D_id = old.id;
-- 删除与该地块相关的灌溉记录
DELETE FROM irrigation_records WHERE plot_id = D_id;
-- 删除与该地块相关的施肥记录
DELETE FROM fertilization_records WHERE plot_id = D_id;
-- 删除与该地块相关的果树信息
DELETE FROM tree_varieties WHERE plot_id = D_id;
END;
$$
DELIMITER ;

