CREATE DATABASE IF NOT EXISTS RiskApp;

CREATE TABLE RiskApp.risk_profiles (
    `risk_profile_id` INT AUTO_INCREMENT,
    `portfolio_id` INT,
    `name` VARCHAR(255),
    `risk_level` VARCHAR(255),
    `active` BOOLEAN,
PRIMARY KEY (risk_profile_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE RiskApp.risk_limits (
    `risk_limit_id` INT AUTO_INCREMENT,
    `risk_profile_id` INT,
    `limit_type` VARCHAR(255),
    `limit_value` DOUBLE,
    `warning_threshold` DOUBLE,
PRIMARY KEY (risk_limit_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE RiskApp.risk_measurements (
    `risk_measurement_id` INT AUTO_INCREMENT,
    `portfolio_id` INT,
    `measurement_type` VARCHAR(255),
    `value` DOUBLE,
    `measured_at` DATETIME,
PRIMARY KEY (risk_measurement_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE RiskApp.risk_breachs (
    `risk_breach_id` INT AUTO_INCREMENT,
    `risk_limit_id` INT,
    `actual_value` DOUBLE,
    `severity` VARCHAR(255),
    `status` VARCHAR(255),
    `detected_at` DATETIME,
PRIMARY KEY (risk_breach_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

USE drcvelocity;
