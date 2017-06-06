CREATE DATABASE `simple_company` /*!40100 DEFAULT CHARACTER SET utf8 */;

CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(45) DEFAULT NULL,
  `lastName` varchar(45) DEFAULT NULL,
  `gender` char(1) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=167 DEFAULT CHARSET=utf8;

CREATE TABLE `address` (
  `address1` varchar(45) DEFAULT NULL,
  `address2` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `state` varchar(45) DEFAULT NULL,
  `zipcode` varchar(45) DEFAULT NULL,
  `customerID` int(11) DEFAULT NULL,
  KEY `customerID_idx` (`customerID`),
  CONSTRAINT `add_customerID` FOREIGN KEY (`customerID`) REFERENCES `customer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `creditcard` (
  `name` varchar(45) DEFAULT NULL,
  `ccNumber` varchar(45) DEFAULT NULL,
  `expDate` varchar(45) DEFAULT NULL,
  `securityCode` varchar(45) DEFAULT NULL,
  `customerID` int(11) DEFAULT NULL,
  KEY `customerID_idx` (`customerID`),
  CONSTRAINT `cc_customerID` FOREIGN KEY (`customerID`) REFERENCES `customer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `prodName` varchar(225) DEFAULT NULL,
  `prodDescription` varchar(225) DEFAULT NULL,
  `prodCategory` int(11) DEFAULT NULL,
  `prodUPC` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8;


CREATE TABLE `purchase` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productID` int(11) DEFAULT NULL,
  `customerID` int(11) DEFAULT NULL,
  `purchaseDate` date DEFAULT NULL,
  `purchaseAmount` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1044 DEFAULT CHARSET=utf8;
