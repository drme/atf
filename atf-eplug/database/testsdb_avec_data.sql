/*
Navicat MySQL Data Transfer
Source Host     : localhost:3306
Source Database : testsdb
Target Host     : localhost:3306
Target Database : testsdb
Date: 2009-05-17 16:51:12
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for kalba
-- ----------------------------
DROP TABLE IF EXISTS `kalba`;
CREATE TABLE `kalba` (
  `id_kalba` int(11) NOT NULL,
  `kalba_pav` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_kalba`),
  KEY `id_kalb` (`id_kalba`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of kalba
-- ----------------------------

-- ----------------------------
-- Table structure for klase
-- ----------------------------
DROP TABLE IF EXISTS `klase`;
CREATE TABLE `klase` (
  `id_tipas` int(11) DEFAULT NULL,
  `id_klase` int(11) NOT NULL,
  `klase_pav` varchar(50) DEFAULT NULL,
  `abstract` tinyint(4) DEFAULT NULL,
  `interface` tinyint(4) DEFAULT NULL,
  `id_testas_klase` int(11) DEFAULT NULL,
  `id_programa` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_klase`),
  KEY `id_klase` (`id_klase`),
  KEY `id_programa` (`id_programa`),
  KEY `id_testas` (`id_testas_klase`),
  KEY `id_tipas` (`id_tipas`),
  KEY `ProgramaKlase` (`id_programa`),
  KEY `TipasKlase` (`id_tipas`),
  CONSTRAINT `klase_ibfk_1` FOREIGN KEY (`id_programa`) REFERENCES `programa` (`id_programa`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `klase_ibfk_2` FOREIGN KEY (`id_tipas`) REFERENCES `tipas` (`id_tipas`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of klase
-- ----------------------------

-- ----------------------------
-- Table structure for many to many (klas-metd)
-- ----------------------------
DROP TABLE IF EXISTS `many to many (klas-metd)`;
CREATE TABLE `many to many (klas-metd)` (
  `id_testas_metodas` int(11) NOT NULL,
  `id_testas_klase` int(11) NOT NULL,
  PRIMARY KEY (`id_testas_metodas`,`id_testas_klase`),
  KEY `id_testas_klase` (`id_testas_klase`),
  KEY `id_testas_metodas` (`id_testas_metodas`),
  KEY `Testas_Klasesmany to many (Klas-Metd)` (`id_testas_klase`),
  KEY `Testas_Metodomany to many (Klas-Metd)` (`id_testas_metodas`),
  CONSTRAINT `many@0020to@0020many@0020@0028klas@002dmetd@0029_ibfk_1` FOREIGN KEY (`id_testas_klase`) REFERENCES `testas_klases` (`id_testas_klase`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `many@0020to@0020many@0020@0028klas@002dmetd@0029_ibfk_2` FOREIGN KEY (`id_testas_metodas`) REFERENCES `testas_metodo` (`id_testas_metodas`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of many to many (klas-metd)
-- ----------------------------

-- ----------------------------
-- Table structure for metodas
-- ----------------------------
DROP TABLE IF EXISTS `metodas`;
CREATE TABLE `metodas` (
  `id_metodas` int(11) NOT NULL,
  `metodas_pav` varchar(50) DEFAULT NULL,
  `gaunama_reiksme` varchar(50) DEFAULT NULL,
  `priemimimas_metodas` varchar(50) DEFAULT NULL,
  `id_klase` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_metodas`),
  KEY `id_klase` (`id_klase`),
  KEY `id_metodas` (`id_metodas`),
  KEY `KlaseMetodas` (`id_klase`),
  CONSTRAINT `metodas_ibfk_1` FOREIGN KEY (`id_klase`) REFERENCES `klase` (`id_klase`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of metodas
-- ----------------------------

-- ----------------------------
-- Table structure for ocl_apribojimas
-- ----------------------------
DROP TABLE IF EXISTS `ocl_apribojimas`;
CREATE TABLE `ocl_apribojimas` (
  `id_OCL` int(11) NOT NULL,
  `OCL_pav` varchar(50) DEFAULT NULL,
  `israiska` varchar(50) DEFAULT NULL,
  `tipas` varchar(50) DEFAULT NULL,
  `operacijos` varchar(50) DEFAULT NULL,
  `konstanta` varchar(50) DEFAULT NULL,
  `atributo_vardas` varchar(50) DEFAULT NULL,
  `id_klase` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_OCL`),
  KEY `id_klase` (`id_klase`),
  KEY `id_OCL` (`id_OCL`),
  KEY `KlaseOCL_apribojimas` (`id_klase`),
  CONSTRAINT `ocl_apribojimas_ibfk_1` FOREIGN KEY (`id_klase`) REFERENCES `klase` (`id_klase`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ocl_apribojimas
-- ----------------------------

-- ----------------------------
-- Table structure for parametras
-- ----------------------------
DROP TABLE IF EXISTS `parametras`;
CREATE TABLE `parametras` (
  `id_parametras` int(11) NOT NULL AUTO_INCREMENT,
  `parametras_pav` varchar(50) DEFAULT NULL,
  `pozicija` varchar(50) DEFAULT NULL,
  `in` tinyint(4) DEFAULT NULL,
  `out` tinyint(4) DEFAULT NULL,
  `id_metodas` int(11) DEFAULT NULL,
  `reiksme` text,
  `id_testas_metodo` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_parametras`),
  KEY `id_metodas` (`id_metodas`),
  KEY `id_parametras` (`id_parametras`),
  KEY `MetodasParametras` (`id_metodas`)
) ENGINE=InnoDB AUTO_INCREMENT=3950 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of parametras
-- ----------------------------
INSERT INTO `parametras` VALUES ('3796', 'p0', null, null, null, null, 'null', '6699');
INSERT INTO `parametras` VALUES ('3797', 'p0', null, null, null, null, 'null', '6658');
INSERT INTO `parametras` VALUES ('3798', 'p0', null, null, null, null, 'null', '6674');
INSERT INTO `parametras` VALUES ('3799', 'p0', null, null, null, null, 'null', '6683');
INSERT INTO `parametras` VALUES ('3800', 'p2', null, null, null, null, 'null', '6606');
INSERT INTO `parametras` VALUES ('3801', 'p0', null, null, null, null, 'null', '6642');
INSERT INTO `parametras` VALUES ('3802', 'p0', null, null, null, null, 'null', '6620');
INSERT INTO `parametras` VALUES ('3803', 'p0', null, null, null, null, 'null', '6607');
INSERT INTO `parametras` VALUES ('3804', 'p1', null, null, null, null, 'null', '6634');
INSERT INTO `parametras` VALUES ('3805', 'p0', null, null, null, null, 'null', '6668');
INSERT INTO `parametras` VALUES ('3806', 'p1', null, null, null, null, 'null', '6576');
INSERT INTO `parametras` VALUES ('3807', 'p2', null, null, null, null, 'null', '6610');
INSERT INTO `parametras` VALUES ('3808', 'p0', null, null, null, null, 'null', '6645');
INSERT INTO `parametras` VALUES ('3809', 'p0', null, null, null, null, 'null', '6665');
INSERT INTO `parametras` VALUES ('3810', 'p0', null, null, null, null, 'null', '6575');
INSERT INTO `parametras` VALUES ('3811', 'p2', null, null, null, null, 'null', '6571');
INSERT INTO `parametras` VALUES ('3812', 'p0', null, null, null, null, 'null', '6593');
INSERT INTO `parametras` VALUES ('3813', 'p1', null, null, null, null, 'null', '6710');
INSERT INTO `parametras` VALUES ('3814', 'p1', null, null, null, null, 'null', '6598');
INSERT INTO `parametras` VALUES ('3815', 'p0', null, null, null, null, 'null', '6610');
INSERT INTO `parametras` VALUES ('3816', 'p2', null, null, null, null, 'null', '6709');
INSERT INTO `parametras` VALUES ('3817', 'p0', null, null, null, null, 'null', '6627');
INSERT INTO `parametras` VALUES ('3818', 'p0', null, null, null, null, 'null', '6605');
INSERT INTO `parametras` VALUES ('3819', 'p2', null, null, null, null, 'null', '6569');
INSERT INTO `parametras` VALUES ('3820', 'p0', null, null, null, null, 'null', '6631');
INSERT INTO `parametras` VALUES ('3821', 'p1', null, null, null, null, 'null', '6644');
INSERT INTO `parametras` VALUES ('3822', 'p0', null, null, null, null, 'null', '6678');
INSERT INTO `parametras` VALUES ('3823', 'p0', null, null, null, null, 'null', '6640');
INSERT INTO `parametras` VALUES ('3824', 'p0', null, null, null, null, 'null', '6587');
INSERT INTO `parametras` VALUES ('3825', 'p0', null, null, null, null, 'null', '6661');
INSERT INTO `parametras` VALUES ('3826', 'p0', null, null, null, null, 'null', '6663');
INSERT INTO `parametras` VALUES ('3827', 'p1', null, null, null, null, 'null', '6610');
INSERT INTO `parametras` VALUES ('3828', 'p3', null, null, null, null, 'null', '6606');
INSERT INTO `parametras` VALUES ('3829', 'p0', null, null, null, null, 'null', '6624');
INSERT INTO `parametras` VALUES ('3830', 'p3', null, null, null, null, 'null', '6574');
INSERT INTO `parametras` VALUES ('3831', 'p0', null, null, null, null, 'null', '6598');
INSERT INTO `parametras` VALUES ('3832', 'p0', null, null, null, null, 'null', '6653');
INSERT INTO `parametras` VALUES ('3833', 'p1', null, null, null, null, 'null', '6574');
INSERT INTO `parametras` VALUES ('3834', 'p1', null, null, null, null, 'null', '6677');
INSERT INTO `parametras` VALUES ('3835', 'p1', null, null, null, null, 'null', '6571');
INSERT INTO `parametras` VALUES ('3836', 'p0', null, null, null, null, 'null', '6578');
INSERT INTO `parametras` VALUES ('3837', 'p1', null, null, null, null, 'null', '6701');
INSERT INTO `parametras` VALUES ('3838', 'p0', null, null, null, null, 'null', '6692');
INSERT INTO `parametras` VALUES ('3839', 'p0', null, null, null, null, 'null', '6611');
INSERT INTO `parametras` VALUES ('3840', 'p1', null, null, null, null, 'null', '6618');
INSERT INTO `parametras` VALUES ('3841', 'p0', null, null, null, null, 'null', '6621');
INSERT INTO `parametras` VALUES ('3842', 'p0', null, null, null, null, 'null', '6643');
INSERT INTO `parametras` VALUES ('3843', 'p0', null, null, null, null, 'null', '6705');
INSERT INTO `parametras` VALUES ('3844', 'p0', null, null, null, null, 'null', '6707');
INSERT INTO `parametras` VALUES ('3845', 'p2', null, null, null, null, 'null', '6637');
INSERT INTO `parametras` VALUES ('3846', 'p1', null, null, null, null, 'null', '6624');
INSERT INTO `parametras` VALUES ('3847', 'p0', null, null, null, null, 'null', '6604');
INSERT INTO `parametras` VALUES ('3848', 'p1', null, null, null, null, 'null', '6692');
INSERT INTO `parametras` VALUES ('3849', 'p1', null, null, null, null, 'null', '6569');
INSERT INTO `parametras` VALUES ('3850', 'p1', null, null, null, null, 'null', '6709');
INSERT INTO `parametras` VALUES ('3851', 'p0', null, null, null, null, 'null', '6623');
INSERT INTO `parametras` VALUES ('3852', 'p1', null, null, null, null, 'null', '6620');
INSERT INTO `parametras` VALUES ('3853', 'p1', null, null, null, null, 'null', '6689');
INSERT INTO `parametras` VALUES ('3854', 'p0', null, null, null, null, 'null', '6576');
INSERT INTO `parametras` VALUES ('3855', 'p0', null, null, null, null, 'null', '6628');
INSERT INTO `parametras` VALUES ('3856', 'p0', null, null, null, null, 'null', '6612');
INSERT INTO `parametras` VALUES ('3857', 'p2', null, null, null, null, 'null', '6574');
INSERT INTO `parametras` VALUES ('3858', 'p0', null, null, null, null, 'null', '6685');
INSERT INTO `parametras` VALUES ('3859', 'p0', null, null, null, null, 'null', '6698');
INSERT INTO `parametras` VALUES ('3860', 'p0', null, null, null, null, 'null', '6672');
INSERT INTO `parametras` VALUES ('3861', 'p0', null, null, null, null, 'null', '6659');
INSERT INTO `parametras` VALUES ('3862', 'p0', null, null, null, null, 'null', '6693');
INSERT INTO `parametras` VALUES ('3863', 'p2', null, null, null, null, 'null', '6710');
INSERT INTO `parametras` VALUES ('3864', 'p0', null, null, null, null, 'null', '6584');
INSERT INTO `parametras` VALUES ('3865', 'p0', null, null, null, null, 'null', '6709');
INSERT INTO `parametras` VALUES ('3866', 'p2', null, null, null, null, 'null', '6668');
INSERT INTO `parametras` VALUES ('3867', 'p1', null, null, null, null, 'null', '6678');
INSERT INTO `parametras` VALUES ('3868', 'p3', null, null, null, null, 'null', '6697');
INSERT INTO `parametras` VALUES ('3869', 'p0', null, null, null, null, 'null', '6637');
INSERT INTO `parametras` VALUES ('3870', 'p1', null, null, null, null, 'null', '6587');
INSERT INTO `parametras` VALUES ('3871', 'p3', null, null, null, null, 'null', '6610');
INSERT INTO `parametras` VALUES ('3872', 'p0', null, null, null, null, 'null', '6641');
INSERT INTO `parametras` VALUES ('3873', 'p0', null, null, null, null, 'null', '6606');
INSERT INTO `parametras` VALUES ('3874', 'p1', null, null, null, null, 'null', '6607');
INSERT INTO `parametras` VALUES ('3875', 'p3', null, null, null, null, 'null', '6710');
INSERT INTO `parametras` VALUES ('3876', 'p0', null, null, null, null, 'null', '6710');
INSERT INTO `parametras` VALUES ('3877', 'p0', null, null, null, null, 'null', '6670');
INSERT INTO `parametras` VALUES ('3878', 'p3', null, null, null, null, 'null', '6569');
INSERT INTO `parametras` VALUES ('3879', 'p0', null, null, null, null, 'null', '6704');
INSERT INTO `parametras` VALUES ('3880', 'p0', null, null, null, null, 'null', '6681');
INSERT INTO `parametras` VALUES ('3881', 'p0', null, null, null, null, 'null', '6591');
INSERT INTO `parametras` VALUES ('3882', 'p0', null, null, null, null, 'null', '6648');
INSERT INTO `parametras` VALUES ('3883', 'p0', null, null, null, null, 'null', '6702');
INSERT INTO `parametras` VALUES ('3884', 'p1', null, null, null, null, 'null', '6684');
INSERT INTO `parametras` VALUES ('3885', 'p0', null, null, null, null, 'null', '6679');
INSERT INTO `parametras` VALUES ('3886', 'p0', null, null, null, null, 'null', '6660');
INSERT INTO `parametras` VALUES ('3887', 'p3', null, null, null, null, 'null', '6668');
INSERT INTO `parametras` VALUES ('3888', 'p0', null, null, null, null, 'null', '6639');
INSERT INTO `parametras` VALUES ('3889', 'p0', null, null, null, null, 'null', '6676');
INSERT INTO `parametras` VALUES ('3890', 'p2', null, null, null, null, 'null', '6618');
INSERT INTO `parametras` VALUES ('3891', 'p1', null, null, null, null, 'null', '6623');
INSERT INTO `parametras` VALUES ('3892', 'p0', null, null, null, null, 'null', '6594');
INSERT INTO `parametras` VALUES ('3893', 'p0', null, null, null, null, 'null', '6697');
INSERT INTO `parametras` VALUES ('3894', 'p0', null, null, null, null, 'null', '6574');
INSERT INTO `parametras` VALUES ('3895', 'p0', null, null, null, null, 'null', '6569');
INSERT INTO `parametras` VALUES ('3896', 'p1', null, null, null, null, 'null', '6606');
INSERT INTO `parametras` VALUES ('3897', 'p0', null, null, null, null, 'null', '6701');
INSERT INTO `parametras` VALUES ('3898', 'p1', null, null, null, null, 'null', '6637');
INSERT INTO `parametras` VALUES ('3899', 'p3', null, null, null, null, 'null', '6637');
INSERT INTO `parametras` VALUES ('3900', 'p0', null, null, null, null, 'null', '6684');
INSERT INTO `parametras` VALUES ('3901', 'p0', null, null, null, null, 'null', '6568');
INSERT INTO `parametras` VALUES ('3902', 'p0', null, null, null, null, 'null', '6647');
INSERT INTO `parametras` VALUES ('3903', 'p0', null, null, null, null, 'null', '6651');
INSERT INTO `parametras` VALUES ('3904', 'p0', null, null, null, null, 'null', '6646');
INSERT INTO `parametras` VALUES ('3905', 'p0', null, null, null, null, 'null', '6635');
INSERT INTO `parametras` VALUES ('3906', 'p0', null, null, null, null, 'null', '6570');
INSERT INTO `parametras` VALUES ('3907', 'p0', null, null, null, null, 'null', '6689');
INSERT INTO `parametras` VALUES ('3908', 'p0', null, null, null, null, 'null', '6622');
INSERT INTO `parametras` VALUES ('3909', 'p0', null, null, null, null, 'null', '6686');
INSERT INTO `parametras` VALUES ('3910', 'p0', null, null, null, null, 'null', '6632');
INSERT INTO `parametras` VALUES ('3911', 'p3', null, null, null, null, 'null', '6571');
INSERT INTO `parametras` VALUES ('3912', 'p0', null, null, null, null, 'null', '6690');
INSERT INTO `parametras` VALUES ('3913', 'p0', null, null, null, null, 'null', '6590');
INSERT INTO `parametras` VALUES ('3914', 'p0', null, null, null, null, 'null', '6656');
INSERT INTO `parametras` VALUES ('3915', 'p0', null, null, null, null, 'null', '6595');
INSERT INTO `parametras` VALUES ('3916', 'p1', null, null, null, null, 'null', '6641');
INSERT INTO `parametras` VALUES ('3917', 'p0', null, null, null, null, 'null', '6638');
INSERT INTO `parametras` VALUES ('3918', 'p0', null, null, null, null, 'null', '6625');
INSERT INTO `parametras` VALUES ('3919', 'p0', null, null, null, null, 'null', '6695');
INSERT INTO `parametras` VALUES ('3920', 'p0', null, null, null, null, 'null', '6577');
INSERT INTO `parametras` VALUES ('3921', 'p0', null, null, null, null, 'null', '6703');
INSERT INTO `parametras` VALUES ('3922', 'p0', null, null, null, null, 'null', '6589');
INSERT INTO `parametras` VALUES ('3923', 'p0', null, null, null, null, 'null', '6582');
INSERT INTO `parametras` VALUES ('3924', 'p3', null, null, null, null, 'null', '6709');
INSERT INTO `parametras` VALUES ('3925', 'p1', null, null, null, null, 'null', '6705');
INSERT INTO `parametras` VALUES ('3926', 'p0', null, null, null, null, 'null', '6626');
INSERT INTO `parametras` VALUES ('3927', 'p0', null, null, null, null, 'null', '6599');
INSERT INTO `parametras` VALUES ('3928', 'p0', null, null, null, null, 'null', '6634');
INSERT INTO `parametras` VALUES ('3929', 'p0', null, null, null, null, 'null', '6675');
INSERT INTO `parametras` VALUES ('3930', 'p1', null, null, null, null, 'null', '6697');
INSERT INTO `parametras` VALUES ('3931', 'p1', null, null, null, null, 'null', '6612');
INSERT INTO `parametras` VALUES ('3932', 'p0', null, null, null, null, 'null', '6613');
INSERT INTO `parametras` VALUES ('3933', 'p0', null, null, null, null, 'null', '6644');
INSERT INTO `parametras` VALUES ('3934', 'p0', null, null, null, null, 'null', '6618');
INSERT INTO `parametras` VALUES ('3935', 'p0', null, null, null, null, 'null', '6673');
INSERT INTO `parametras` VALUES ('3936', 'p2', null, null, null, null, 'null', '6697');
INSERT INTO `parametras` VALUES ('3937', 'p0', null, null, null, null, 'null', '6571');
INSERT INTO `parametras` VALUES ('3938', 'p1', null, null, null, null, 'null', '6668');
INSERT INTO `parametras` VALUES ('3939', 'p0', null, null, null, null, 'null', '6682');
INSERT INTO `parametras` VALUES ('3940', 'p1', null, null, null, null, 'null', '6578');
INSERT INTO `parametras` VALUES ('3941', 'p1', null, null, null, null, 'null', '6703');
INSERT INTO `parametras` VALUES ('3942', 'p3', null, null, null, null, 'null', '6618');
INSERT INTO `parametras` VALUES ('3943', 'p1', null, null, null, null, 'null', '6613');
INSERT INTO `parametras` VALUES ('3944', 'p0', null, null, null, null, 'null', '6666');
INSERT INTO `parametras` VALUES ('3945', 'p0', null, null, null, null, 'null', '6601');
INSERT INTO `parametras` VALUES ('3946', 'p1', null, null, null, null, 'null', '6704');
INSERT INTO `parametras` VALUES ('3947', 'p0', null, null, null, null, 'null', '6677');
INSERT INTO `parametras` VALUES ('3948', 'p0', null, null, null, null, 'null', '6588');
INSERT INTO `parametras` VALUES ('3949', 'p0', null, null, null, null, 'null', '6580');

-- ----------------------------
-- Table structure for programa
-- ----------------------------
DROP TABLE IF EXISTS `programa`;
CREATE TABLE `programa` (
  `id_programa` int(11) NOT NULL,
  `programa_pav` varchar(50) DEFAULT NULL,
  `id_kalba` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_programa`),
  KEY `id_kalba` (`id_kalba`),
  KEY `id_programa` (`id_programa`),
  KEY `KalbaPrograma` (`id_kalba`),
  CONSTRAINT `programa_ibfk_1` FOREIGN KEY (`id_kalba`) REFERENCES `kalba` (`id_kalba`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of programa
-- ----------------------------

-- ----------------------------
-- Table structure for test_projektas
-- ----------------------------
DROP TABLE IF EXISTS `test_projektas`;
CREATE TABLE `test_projektas` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pavadinimas` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of test_projektas
-- ----------------------------
INSERT INTO `test_projektas` VALUES ('13', 'HelloWorkd');

-- ----------------------------
-- Table structure for testas
-- ----------------------------
DROP TABLE IF EXISTS `testas`;
CREATE TABLE `testas` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `testu_rinkinys_id` bigint(20) NOT NULL,
  `klase` varchar(255) NOT NULL,
  `metodas` varchar(255) NOT NULL,
  `paketas` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `f1` (`testu_rinkinys_id`),
  CONSTRAINT `f1` FOREIGN KEY (`testu_rinkinys_id`) REFERENCES `testu_rinkinys` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24988 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of testas
-- ----------------------------
INSERT INTO `testas` VALUES ('24845', '1291', 'FileList', 'getFilesToList', 'com.me.util');
INSERT INTO `testas` VALUES ('24846', '1295', 'SplitString', 'split', 'com.me.util');
INSERT INTO `testas` VALUES ('24847', '1286', 'FileFilter', 'accept', 'com.me.util');
INSERT INTO `testas` VALUES ('24848', '1287', 'FileStringPrinter', 'clear', 'com.me.util');
INSERT INTO `testas` VALUES ('24849', '1291', 'FileList', 'getFilesToList', 'com.me.util');
INSERT INTO `testas` VALUES ('24850', '1289', 'FileStringPrinter', 'close', 'com.me.util');
INSERT INTO `testas` VALUES ('24851', '1289', 'FileStringPrinter', 'close', 'com.me.util');
INSERT INTO `testas` VALUES ('24852', '1297', 'FileList', 'getFiles', 'com.me.util');
INSERT INTO `testas` VALUES ('24853', '1279', 'IStringPrinter', 'print', 'com.me.util');
INSERT INTO `testas` VALUES ('24854', '1285', 'FileStringPrinter', 'print', 'com.me.util');
INSERT INTO `testas` VALUES ('24855', '1294', 'IStringPrinter', 'clear', 'com.me.util');
INSERT INTO `testas` VALUES ('24856', '1289', 'FileStringPrinter', 'close', 'com.me.util');
INSERT INTO `testas` VALUES ('24857', '1292', 'IFunc', 'toString', 'com.me.util');
INSERT INTO `testas` VALUES ('24858', '1293', 'FileList', 'setExtension', 'com.me.util');
INSERT INTO `testas` VALUES ('24859', '1290', 'IFunc', 'func', 'com.me.util');
INSERT INTO `testas` VALUES ('24860', '1284', 'StringComparator', 'compare', 'com.me.util');
INSERT INTO `testas` VALUES ('24861', '1284', 'StringComparator', 'compare', 'com.me.util');
INSERT INTO `testas` VALUES ('24862', '1285', 'FileStringPrinter', 'print', 'com.me.util');
INSERT INTO `testas` VALUES ('24863', '1286', 'FileFilter', 'accept', 'com.me.util');
INSERT INTO `testas` VALUES ('24864', '1289', 'FileStringPrinter', 'close', 'com.me.util');
INSERT INTO `testas` VALUES ('24865', '1293', 'FileList', 'setExtension', 'com.me.util');
INSERT INTO `testas` VALUES ('24866', '1284', 'StringComparator', 'compare', 'com.me.util');
INSERT INTO `testas` VALUES ('24867', '1294', 'IStringPrinter', 'clear', 'com.me.util');
INSERT INTO `testas` VALUES ('24868', '1294', 'IStringPrinter', 'clear', 'com.me.util');
INSERT INTO `testas` VALUES ('24869', '1295', 'SplitString', 'split', 'com.me.util');
INSERT INTO `testas` VALUES ('24870', '1291', 'FileList', 'getFilesToList', 'com.me.util');
INSERT INTO `testas` VALUES ('24871', '1284', 'StringComparator', 'compare', 'com.me.util');
INSERT INTO `testas` VALUES ('24872', '1294', 'IStringPrinter', 'clear', 'com.me.util');
INSERT INTO `testas` VALUES ('24873', '1279', 'IStringPrinter', 'print', 'com.me.util');
INSERT INTO `testas` VALUES ('24874', '1290', 'IFunc', 'func', 'com.me.util');
INSERT INTO `testas` VALUES ('24875', '1289', 'FileStringPrinter', 'close', 'com.me.util');
INSERT INTO `testas` VALUES ('24876', '1295', 'SplitString', 'split', 'com.me.util');
INSERT INTO `testas` VALUES ('24877', '1290', 'IFunc', 'func', 'com.me.util');
INSERT INTO `testas` VALUES ('24878', '1286', 'FileFilter', 'accept', 'com.me.util');
INSERT INTO `testas` VALUES ('24879', '1286', 'FileFilter', 'accept', 'com.me.util');
INSERT INTO `testas` VALUES ('24880', '1289', 'FileStringPrinter', 'close', 'com.me.util');
INSERT INTO `testas` VALUES ('24881', '1293', 'FileList', 'setExtension', 'com.me.util');
INSERT INTO `testas` VALUES ('24882', '1284', 'StringComparator', 'compare', 'com.me.util');
INSERT INTO `testas` VALUES ('24883', '1286', 'FileFilter', 'accept', 'com.me.util');
INSERT INTO `testas` VALUES ('24884', '1291', 'FileList', 'getFilesToList', 'com.me.util');
INSERT INTO `testas` VALUES ('24885', '1295', 'SplitString', 'split', 'com.me.util');
INSERT INTO `testas` VALUES ('24886', '1295', 'SplitString', 'split', 'com.me.util');
INSERT INTO `testas` VALUES ('24887', '1284', 'StringComparator', 'compare', 'com.me.util');
INSERT INTO `testas` VALUES ('24888', '1287', 'FileStringPrinter', 'clear', 'com.me.util');
INSERT INTO `testas` VALUES ('24889', '1292', 'IFunc', 'toString', 'com.me.util');
INSERT INTO `testas` VALUES ('24890', '1290', 'IFunc', 'func', 'com.me.util');
INSERT INTO `testas` VALUES ('24891', '1297', 'FileList', 'getFiles', 'com.me.util');
INSERT INTO `testas` VALUES ('24892', '1287', 'FileStringPrinter', 'clear', 'com.me.util');
INSERT INTO `testas` VALUES ('24893', '1290', 'IFunc', 'func', 'com.me.util');
INSERT INTO `testas` VALUES ('24894', '1286', 'FileFilter', 'accept', 'com.me.util');
INSERT INTO `testas` VALUES ('24895', '1291', 'FileList', 'getFilesToList', 'com.me.util');
INSERT INTO `testas` VALUES ('24896', '1290', 'IFunc', 'func', 'com.me.util');
INSERT INTO `testas` VALUES ('24897', '1293', 'FileList', 'setExtension', 'com.me.util');
INSERT INTO `testas` VALUES ('24898', '1290', 'IFunc', 'func', 'com.me.util');
INSERT INTO `testas` VALUES ('24899', '1286', 'FileFilter', 'accept', 'com.me.util');
INSERT INTO `testas` VALUES ('24900', '1297', 'FileList', 'getFiles', 'com.me.util');
INSERT INTO `testas` VALUES ('24901', '1293', 'FileList', 'setExtension', 'com.me.util');
INSERT INTO `testas` VALUES ('24902', '1279', 'IStringPrinter', 'print', 'com.me.util');
INSERT INTO `testas` VALUES ('24903', '1287', 'FileStringPrinter', 'clear', 'com.me.util');
INSERT INTO `testas` VALUES ('24904', '1284', 'StringComparator', 'compare', 'com.me.util');
INSERT INTO `testas` VALUES ('24905', '1292', 'IFunc', 'toString', 'com.me.util');
INSERT INTO `testas` VALUES ('24906', '1297', 'FileList', 'getFiles', 'com.me.util');
INSERT INTO `testas` VALUES ('24907', '1284', 'StringComparator', 'compare', 'com.me.util');
INSERT INTO `testas` VALUES ('24908', '1295', 'SplitString', 'split', 'com.me.util');
INSERT INTO `testas` VALUES ('24909', '1294', 'IStringPrinter', 'clear', 'com.me.util');
INSERT INTO `testas` VALUES ('24910', '1297', 'FileList', 'getFiles', 'com.me.util');
INSERT INTO `testas` VALUES ('24911', '1291', 'FileList', 'getFilesToList', 'com.me.util');
INSERT INTO `testas` VALUES ('24912', '1287', 'FileStringPrinter', 'clear', 'com.me.util');
INSERT INTO `testas` VALUES ('24913', '1290', 'IFunc', 'func', 'com.me.util');
INSERT INTO `testas` VALUES ('24914', '1279', 'IStringPrinter', 'print', 'com.me.util');
INSERT INTO `testas` VALUES ('24915', '1289', 'FileStringPrinter', 'close', 'com.me.util');
INSERT INTO `testas` VALUES ('24916', '1294', 'IStringPrinter', 'clear', 'com.me.util');
INSERT INTO `testas` VALUES ('24917', '1294', 'IStringPrinter', 'clear', 'com.me.util');
INSERT INTO `testas` VALUES ('24918', '1291', 'FileList', 'getFilesToList', 'com.me.util');
INSERT INTO `testas` VALUES ('24919', '1287', 'FileStringPrinter', 'clear', 'com.me.util');
INSERT INTO `testas` VALUES ('24920', '1279', 'IStringPrinter', 'print', 'com.me.util');
INSERT INTO `testas` VALUES ('24921', '1294', 'IStringPrinter', 'clear', 'com.me.util');
INSERT INTO `testas` VALUES ('24922', '1292', 'IFunc', 'toString', 'com.me.util');
INSERT INTO `testas` VALUES ('24923', '1284', 'StringComparator', 'compare', 'com.me.util');
INSERT INTO `testas` VALUES ('24924', '1294', 'IStringPrinter', 'clear', 'com.me.util');
INSERT INTO `testas` VALUES ('24925', '1293', 'FileList', 'setExtension', 'com.me.util');
INSERT INTO `testas` VALUES ('24926', '1285', 'FileStringPrinter', 'print', 'com.me.util');
INSERT INTO `testas` VALUES ('24927', '1286', 'FileFilter', 'accept', 'com.me.util');
INSERT INTO `testas` VALUES ('24928', '1295', 'SplitString', 'split', 'com.me.util');
INSERT INTO `testas` VALUES ('24929', '1290', 'IFunc', 'func', 'com.me.util');
INSERT INTO `testas` VALUES ('24930', '1292', 'IFunc', 'toString', 'com.me.util');
INSERT INTO `testas` VALUES ('24931', '1297', 'FileList', 'getFiles', 'com.me.util');
INSERT INTO `testas` VALUES ('24932', '1290', 'IFunc', 'func', 'com.me.util');
INSERT INTO `testas` VALUES ('24933', '1279', 'IStringPrinter', 'print', 'com.me.util');
INSERT INTO `testas` VALUES ('24934', '1279', 'IStringPrinter', 'print', 'com.me.util');
INSERT INTO `testas` VALUES ('24935', '1285', 'FileStringPrinter', 'print', 'com.me.util');
INSERT INTO `testas` VALUES ('24936', '1292', 'IFunc', 'toString', 'com.me.util');
INSERT INTO `testas` VALUES ('24937', '1287', 'FileStringPrinter', 'clear', 'com.me.util');
INSERT INTO `testas` VALUES ('24938', '1297', 'FileList', 'getFiles', 'com.me.util');
INSERT INTO `testas` VALUES ('24939', '1285', 'FileStringPrinter', 'print', 'com.me.util');
INSERT INTO `testas` VALUES ('24940', '1295', 'SplitString', 'split', 'com.me.util');
INSERT INTO `testas` VALUES ('24941', '1279', 'IStringPrinter', 'print', 'com.me.util');
INSERT INTO `testas` VALUES ('24942', '1289', 'FileStringPrinter', 'close', 'com.me.util');
INSERT INTO `testas` VALUES ('24943', '1291', 'FileList', 'getFilesToList', 'com.me.util');
INSERT INTO `testas` VALUES ('24944', '1293', 'FileList', 'setExtension', 'com.me.util');
INSERT INTO `testas` VALUES ('24945', '1293', 'FileList', 'setExtension', 'com.me.util');
INSERT INTO `testas` VALUES ('24946', '1286', 'FileFilter', 'accept', 'com.me.util');
INSERT INTO `testas` VALUES ('24947', '1297', 'FileList', 'getFiles', 'com.me.util');
INSERT INTO `testas` VALUES ('24948', '1291', 'FileList', 'getFilesToList', 'com.me.util');
INSERT INTO `testas` VALUES ('24949', '1279', 'IStringPrinter', 'print', 'com.me.util');
INSERT INTO `testas` VALUES ('24950', '1289', 'FileStringPrinter', 'close', 'com.me.util');
INSERT INTO `testas` VALUES ('24951', '1294', 'IStringPrinter', 'clear', 'com.me.util');
INSERT INTO `testas` VALUES ('24952', '1294', 'IStringPrinter', 'clear', 'com.me.util');
INSERT INTO `testas` VALUES ('24953', '1289', 'FileStringPrinter', 'close', 'com.me.util');
INSERT INTO `testas` VALUES ('24954', '1297', 'FileList', 'getFiles', 'com.me.util');
INSERT INTO `testas` VALUES ('24955', '1290', 'IFunc', 'func', 'com.me.util');
INSERT INTO `testas` VALUES ('24956', '1279', 'IStringPrinter', 'print', 'com.me.util');
INSERT INTO `testas` VALUES ('24957', '1292', 'IFunc', 'toString', 'com.me.util');
INSERT INTO `testas` VALUES ('24958', '1292', 'IFunc', 'toString', 'com.me.util');
INSERT INTO `testas` VALUES ('24959', '1293', 'FileList', 'setExtension', 'com.me.util');
INSERT INTO `testas` VALUES ('24960', '1285', 'FileStringPrinter', 'print', 'com.me.util');
INSERT INTO `testas` VALUES ('24961', '1297', 'FileList', 'getFiles', 'com.me.util');
INSERT INTO `testas` VALUES ('24962', '1285', 'FileStringPrinter', 'print', 'com.me.util');
INSERT INTO `testas` VALUES ('24963', '1284', 'StringComparator', 'compare', 'com.me.util');
INSERT INTO `testas` VALUES ('24964', '1286', 'FileFilter', 'accept', 'com.me.util');
INSERT INTO `testas` VALUES ('24965', '1285', 'FileStringPrinter', 'print', 'com.me.util');
INSERT INTO `testas` VALUES ('24966', '1285', 'FileStringPrinter', 'print', 'com.me.util');
INSERT INTO `testas` VALUES ('24967', '1287', 'FileStringPrinter', 'clear', 'com.me.util');
INSERT INTO `testas` VALUES ('24968', '1287', 'FileStringPrinter', 'clear', 'com.me.util');
INSERT INTO `testas` VALUES ('24969', '1297', 'FileList', 'getFiles', 'com.me.util');
INSERT INTO `testas` VALUES ('24970', '1292', 'IFunc', 'toString', 'com.me.util');
INSERT INTO `testas` VALUES ('24971', '1287', 'FileStringPrinter', 'clear', 'com.me.util');
INSERT INTO `testas` VALUES ('24972', '1284', 'StringComparator', 'compare', 'com.me.util');
INSERT INTO `testas` VALUES ('24973', '1295', 'SplitString', 'split', 'com.me.util');
INSERT INTO `testas` VALUES ('24974', '1292', 'IFunc', 'toString', 'com.me.util');
INSERT INTO `testas` VALUES ('24975', '1279', 'IStringPrinter', 'print', 'com.me.util');
INSERT INTO `testas` VALUES ('24976', '1295', 'SplitString', 'split', 'com.me.util');
INSERT INTO `testas` VALUES ('24977', '1285', 'FileStringPrinter', 'print', 'com.me.util');
INSERT INTO `testas` VALUES ('24978', '1285', 'FileStringPrinter', 'print', 'com.me.util');
INSERT INTO `testas` VALUES ('24979', '1295', 'SplitString', 'split', 'com.me.util');
INSERT INTO `testas` VALUES ('24980', '1291', 'FileList', 'getFilesToList', 'com.me.util');
INSERT INTO `testas` VALUES ('24981', '1293', 'FileList', 'setExtension', 'com.me.util');
INSERT INTO `testas` VALUES ('24982', '1286', 'FileFilter', 'accept', 'com.me.util');
INSERT INTO `testas` VALUES ('24983', '1291', 'FileList', 'getFilesToList', 'com.me.util');
INSERT INTO `testas` VALUES ('24984', '1287', 'FileStringPrinter', 'clear', 'com.me.util');
INSERT INTO `testas` VALUES ('24985', '1292', 'IFunc', 'toString', 'com.me.util');
INSERT INTO `testas` VALUES ('24986', '1289', 'FileStringPrinter', 'close', 'com.me.util');
INSERT INTO `testas` VALUES ('24987', '1293', 'FileList', 'setExtension', 'com.me.util');

-- ----------------------------
-- Table structure for testas_klases
-- ----------------------------
DROP TABLE IF EXISTS `testas_klases`;
CREATE TABLE `testas_klases` (
  `id_testas_klase` int(11) NOT NULL,
  `testas_klase_pav` varchar(50) DEFAULT NULL,
  `id_testuotojas` int(11) DEFAULT NULL,
  `id_klase` int(11) DEFAULT NULL,
  `id_testas_programa` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_testas_klase`),
  KEY `id_klase` (`id_klase`),
  KEY `id_testas` (`id_testas_klase`),
  KEY `id_testas_programa` (`id_testas_programa`),
  KEY `id_testutojas` (`id_testuotojas`),
  KEY `KlaseTestas_Klases` (`id_klase`),
  KEY `Testas_ProgramosTestas_Klases` (`id_testas_programa`),
  KEY `TestuotojasTestas_Klases` (`id_testuotojas`),
  CONSTRAINT `testas_klases_ibfk_1` FOREIGN KEY (`id_klase`) REFERENCES `klase` (`id_klase`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `testas_klases_ibfk_2` FOREIGN KEY (`id_testas_programa`) REFERENCES `testas_programos` (`id_testas_programa`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `testas_klases_ibfk_3` FOREIGN KEY (`id_testuotojas`) REFERENCES `testuotojas` (`id_testuotojas`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of testas_klases
-- ----------------------------

-- ----------------------------
-- Table structure for testas_metodo
-- ----------------------------
DROP TABLE IF EXISTS `testas_metodo`;
CREATE TABLE `testas_metodo` (
  `id_testas_metodas` int(11) NOT NULL AUTO_INCREMENT,
  `id_metodas` int(11) DEFAULT NULL,
  `id_testas` bigint(20) NOT NULL,
  PRIMARY KEY (`id_testas_metodas`),
  KEY `id_metodas` (`id_metodas`),
  KEY `id_testas_metodas` (`id_testas_metodas`),
  KEY `MetodasTestas_Metodo` (`id_metodas`),
  KEY `ff1` (`id_testas`),
  CONSTRAINT `ff1` FOREIGN KEY (`id_testas`) REFERENCES `testas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6711 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of testas_metodo
-- ----------------------------
INSERT INTO `testas_metodo` VALUES ('6568', null, '24947');
INSERT INTO `testas_metodo` VALUES ('6569', null, '24913');
INSERT INTO `testas_metodo` VALUES ('6570', null, '24900');
INSERT INTO `testas_metodo` VALUES ('6571', null, '24932');
INSERT INTO `testas_metodo` VALUES ('6572', null, '24851');
INSERT INTO `testas_metodo` VALUES ('6573', null, '24850');
INSERT INTO `testas_metodo` VALUES ('6574', null, '24893');
INSERT INTO `testas_metodo` VALUES ('6575', null, '24869');
INSERT INTO `testas_metodo` VALUES ('6576', null, '24907');
INSERT INTO `testas_metodo` VALUES ('6577', null, '24926');
INSERT INTO `testas_metodo` VALUES ('6578', null, '24982');
INSERT INTO `testas_metodo` VALUES ('6579', null, '24986');
INSERT INTO `testas_metodo` VALUES ('6580', null, '24911');
INSERT INTO `testas_metodo` VALUES ('6581', null, '24917');
INSERT INTO `testas_metodo` VALUES ('6582', null, '24944');
INSERT INTO `testas_metodo` VALUES ('6583', null, '24957');
INSERT INTO `testas_metodo` VALUES ('6584', null, '24858');
INSERT INTO `testas_metodo` VALUES ('6585', null, '24889');
INSERT INTO `testas_metodo` VALUES ('6586', null, '24875');
INSERT INTO `testas_metodo` VALUES ('6587', null, '24899');
INSERT INTO `testas_metodo` VALUES ('6588', null, '24966');
INSERT INTO `testas_metodo` VALUES ('6589', null, '24940');
INSERT INTO `testas_metodo` VALUES ('6590', null, '24906');
INSERT INTO `testas_metodo` VALUES ('6591', null, '24854');
INSERT INTO `testas_metodo` VALUES ('6592', null, '24856');
INSERT INTO `testas_metodo` VALUES ('6593', null, '24931');
INSERT INTO `testas_metodo` VALUES ('6594', null, '24876');
INSERT INTO `testas_metodo` VALUES ('6595', null, '24949');
INSERT INTO `testas_metodo` VALUES ('6596', null, '24953');
INSERT INTO `testas_metodo` VALUES ('6597', null, '24950');
INSERT INTO `testas_metodo` VALUES ('6598', null, '24879');
INSERT INTO `testas_metodo` VALUES ('6599', null, '24870');
INSERT INTO `testas_metodo` VALUES ('6600', null, '24857');
INSERT INTO `testas_metodo` VALUES ('6601', null, '24885');
INSERT INTO `testas_metodo` VALUES ('6602', null, '24952');
INSERT INTO `testas_metodo` VALUES ('6603', null, '24942');
INSERT INTO `testas_metodo` VALUES ('6604', null, '24960');
INSERT INTO `testas_metodo` VALUES ('6605', null, '24901');
INSERT INTO `testas_metodo` VALUES ('6606', null, '24877');
INSERT INTO `testas_metodo` VALUES ('6607', null, '24847');
INSERT INTO `testas_metodo` VALUES ('6608', null, '24916');
INSERT INTO `testas_metodo` VALUES ('6609', null, '24936');
INSERT INTO `testas_metodo` VALUES ('6610', null, '24890');
INSERT INTO `testas_metodo` VALUES ('6611', null, '24853');
INSERT INTO `testas_metodo` VALUES ('6612', null, '24882');
INSERT INTO `testas_metodo` VALUES ('6613', null, '24860');
INSERT INTO `testas_metodo` VALUES ('6614', null, '24924');
INSERT INTO `testas_metodo` VALUES ('6615', null, '24922');
INSERT INTO `testas_metodo` VALUES ('6616', null, '24921');
INSERT INTO `testas_metodo` VALUES ('6617', null, '24951');
INSERT INTO `testas_metodo` VALUES ('6618', null, '24898');
INSERT INTO `testas_metodo` VALUES ('6619', null, '24872');
INSERT INTO `testas_metodo` VALUES ('6620', null, '24887');
INSERT INTO `testas_metodo` VALUES ('6621', null, '24902');
INSERT INTO `testas_metodo` VALUES ('6622', null, '24975');
INSERT INTO `testas_metodo` VALUES ('6623', null, '24878');
INSERT INTO `testas_metodo` VALUES ('6624', null, '24863');
INSERT INTO `testas_metodo` VALUES ('6625', null, '24884');
INSERT INTO `testas_metodo` VALUES ('6626', null, '24918');
INSERT INTO `testas_metodo` VALUES ('6627', null, '24973');
INSERT INTO `testas_metodo` VALUES ('6628', null, '24928');
INSERT INTO `testas_metodo` VALUES ('6629', null, '24868');
INSERT INTO `testas_metodo` VALUES ('6630', null, '24892');
INSERT INTO `testas_metodo` VALUES ('6631', null, '24910');
INSERT INTO `testas_metodo` VALUES ('6632', null, '24925');
INSERT INTO `testas_metodo` VALUES ('6633', null, '24848');
INSERT INTO `testas_metodo` VALUES ('6634', null, '24963');
INSERT INTO `testas_metodo` VALUES ('6635', null, '24934');
INSERT INTO `testas_metodo` VALUES ('6636', null, '24971');
INSERT INTO `testas_metodo` VALUES ('6637', null, '24929');
INSERT INTO `testas_metodo` VALUES ('6638', null, '24935');
INSERT INTO `testas_metodo` VALUES ('6639', null, '24849');
INSERT INTO `testas_metodo` VALUES ('6640', null, '24981');
INSERT INTO `testas_metodo` VALUES ('6641', null, '24946');
INSERT INTO `testas_metodo` VALUES ('6642', null, '24965');
INSERT INTO `testas_metodo` VALUES ('6643', null, '24980');
INSERT INTO `testas_metodo` VALUES ('6644', null, '24894');
INSERT INTO `testas_metodo` VALUES ('6645', null, '24943');
INSERT INTO `testas_metodo` VALUES ('6646', null, '24862');
INSERT INTO `testas_metodo` VALUES ('6647', null, '24987');
INSERT INTO `testas_metodo` VALUES ('6648', null, '24914');
INSERT INTO `testas_metodo` VALUES ('6649', null, '24912');
INSERT INTO `testas_metodo` VALUES ('6650', null, '24855');
INSERT INTO `testas_metodo` VALUES ('6651', null, '24938');
INSERT INTO `testas_metodo` VALUES ('6652', null, '24888');
INSERT INTO `testas_metodo` VALUES ('6653', null, '24983');
INSERT INTO `testas_metodo` VALUES ('6654', null, '24974');
INSERT INTO `testas_metodo` VALUES ('6655', null, '24970');
INSERT INTO `testas_metodo` VALUES ('6656', null, '24969');
INSERT INTO `testas_metodo` VALUES ('6657', null, '24984');
INSERT INTO `testas_metodo` VALUES ('6658', null, '24976');
INSERT INTO `testas_metodo` VALUES ('6659', null, '24941');
INSERT INTO `testas_metodo` VALUES ('6660', null, '24897');
INSERT INTO `testas_metodo` VALUES ('6661', null, '24845');
INSERT INTO `testas_metodo` VALUES ('6662', null, '24864');
INSERT INTO `testas_metodo` VALUES ('6663', null, '24948');
INSERT INTO `testas_metodo` VALUES ('6664', null, '24905');
INSERT INTO `testas_metodo` VALUES ('6665', null, '24977');
INSERT INTO `testas_metodo` VALUES ('6666', null, '24959');
INSERT INTO `testas_metodo` VALUES ('6667', null, '24909');
INSERT INTO `testas_metodo` VALUES ('6668', null, '24896');
INSERT INTO `testas_metodo` VALUES ('6669', null, '24903');
INSERT INTO `testas_metodo` VALUES ('6670', null, '24920');
INSERT INTO `testas_metodo` VALUES ('6671', null, '24958');
INSERT INTO `testas_metodo` VALUES ('6672', null, '24846');
INSERT INTO `testas_metodo` VALUES ('6673', null, '24908');
INSERT INTO `testas_metodo` VALUES ('6674', null, '24933');
INSERT INTO `testas_metodo` VALUES ('6675', null, '24939');
INSERT INTO `testas_metodo` VALUES ('6676', null, '24954');
INSERT INTO `testas_metodo` VALUES ('6677', null, '24866');
INSERT INTO `testas_metodo` VALUES ('6678', null, '24871');
INSERT INTO `testas_metodo` VALUES ('6679', null, '24979');
INSERT INTO `testas_metodo` VALUES ('6680', null, '24915');
INSERT INTO `testas_metodo` VALUES ('6681', null, '24945');
INSERT INTO `testas_metodo` VALUES ('6682', null, '24956');
INSERT INTO `testas_metodo` VALUES ('6683', null, '24895');
INSERT INTO `testas_metodo` VALUES ('6684', null, '24923');
INSERT INTO `testas_metodo` VALUES ('6685', null, '24852');
INSERT INTO `testas_metodo` VALUES ('6686', null, '24962');
INSERT INTO `testas_metodo` VALUES ('6687', null, '24968');
INSERT INTO `testas_metodo` VALUES ('6688', null, '24880');
INSERT INTO `testas_metodo` VALUES ('6689', null, '24964');
INSERT INTO `testas_metodo` VALUES ('6690', null, '24886');
INSERT INTO `testas_metodo` VALUES ('6691', null, '24867');
INSERT INTO `testas_metodo` VALUES ('6692', null, '24904');
INSERT INTO `testas_metodo` VALUES ('6693', null, '24873');
INSERT INTO `testas_metodo` VALUES ('6694', null, '24919');
INSERT INTO `testas_metodo` VALUES ('6695', null, '24978');
INSERT INTO `testas_metodo` VALUES ('6696', null, '24937');
INSERT INTO `testas_metodo` VALUES ('6697', null, '24874');
INSERT INTO `testas_metodo` VALUES ('6698', null, '24881');
INSERT INTO `testas_metodo` VALUES ('6699', null, '24891');
INSERT INTO `testas_metodo` VALUES ('6700', null, '24930');
INSERT INTO `testas_metodo` VALUES ('6701', null, '24883');
INSERT INTO `testas_metodo` VALUES ('6702', null, '24865');
INSERT INTO `testas_metodo` VALUES ('6703', null, '24927');
INSERT INTO `testas_metodo` VALUES ('6704', null, '24972');
INSERT INTO `testas_metodo` VALUES ('6705', null, '24861');
INSERT INTO `testas_metodo` VALUES ('6706', null, '24967');
INSERT INTO `testas_metodo` VALUES ('6707', null, '24961');
INSERT INTO `testas_metodo` VALUES ('6708', null, '24985');
INSERT INTO `testas_metodo` VALUES ('6709', null, '24955');
INSERT INTO `testas_metodo` VALUES ('6710', null, '24859');

-- ----------------------------
-- Table structure for testas_programos
-- ----------------------------
DROP TABLE IF EXISTS `testas_programos`;
CREATE TABLE `testas_programos` (
  `id_testas_programa` int(11) NOT NULL,
  `id_programa` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_testas_programa`),
  KEY `id_programa` (`id_programa`),
  KEY `id_testas_programa` (`id_testas_programa`),
  KEY `ProgramaTestas_Programos` (`id_programa`),
  CONSTRAINT `testas_programos_ibfk_1` FOREIGN KEY (`id_programa`) REFERENCES `programa` (`id_programa`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of testas_programos
-- ----------------------------

-- ----------------------------
-- Table structure for testu_rinkinys
-- ----------------------------
DROP TABLE IF EXISTS `testu_rinkinys`;
CREATE TABLE `testu_rinkinys` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pavadinimas` varchar(1024) NOT NULL,
  `id_test_projektas` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk1` (`id_test_projektas`),
  CONSTRAINT `gk22` FOREIGN KEY (`id_test_projektas`) REFERENCES `test_projektas` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1298 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of testu_rinkinys
-- ----------------------------
INSERT INTO `testu_rinkinys` VALUES ('1278', 'TestSuiteStringComparator', '13');
INSERT INTO `testu_rinkinys` VALUES ('1279', 'TestSuiteprint', '13');
INSERT INTO `testu_rinkinys` VALUES ('1280', 'TestSuiteIStringPrinter', '13');
INSERT INTO `testu_rinkinys` VALUES ('1281', 'TestSuiteFileList', '13');
INSERT INTO `testu_rinkinys` VALUES ('1282', 'TestSuiteIFunc', '13');
INSERT INTO `testu_rinkinys` VALUES ('1283', 'TestSuiteFileFilter', '13');
INSERT INTO `testu_rinkinys` VALUES ('1284', 'TestSuitecompare', '13');
INSERT INTO `testu_rinkinys` VALUES ('1285', 'TestSuiteprint', '13');
INSERT INTO `testu_rinkinys` VALUES ('1286', 'TestSuiteaccept', '13');
INSERT INTO `testu_rinkinys` VALUES ('1287', 'TestSuiteclear', '13');
INSERT INTO `testu_rinkinys` VALUES ('1288', 'TestSuiteSplitString', '13');
INSERT INTO `testu_rinkinys` VALUES ('1289', 'TestSuiteclose', '13');
INSERT INTO `testu_rinkinys` VALUES ('1290', 'TestSuitefunc', '13');
INSERT INTO `testu_rinkinys` VALUES ('1291', 'TestSuitegetFilesToList', '13');
INSERT INTO `testu_rinkinys` VALUES ('1292', 'TestSuitetoString', '13');
INSERT INTO `testu_rinkinys` VALUES ('1293', 'TestSuitesetExtension', '13');
INSERT INTO `testu_rinkinys` VALUES ('1294', 'TestSuiteclear', '13');
INSERT INTO `testu_rinkinys` VALUES ('1295', 'TestSuitesplit', '13');
INSERT INTO `testu_rinkinys` VALUES ('1296', 'TestSuiteFileStringPrinter', '13');
INSERT INTO `testu_rinkinys` VALUES ('1297', 'TestSuitegetFiles', '13');

-- ----------------------------
-- Table structure for testuotojas
-- ----------------------------
DROP TABLE IF EXISTS `testuotojas`;
CREATE TABLE `testuotojas` (
  `id_testuotojas` int(11) NOT NULL,
  `vardas` varchar(50) DEFAULT NULL,
  `pavarde` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_testuotojas`),
  KEY `id_testuotojas` (`id_testuotojas`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of testuotojas
-- ----------------------------

-- ----------------------------
-- Table structure for tipas
-- ----------------------------
DROP TABLE IF EXISTS `tipas`;
CREATE TABLE `tipas` (
  `id_tipas` int(11) NOT NULL,
  `tipas_pav` varchar(50) DEFAULT NULL,
  `paprastas` tinyint(4) DEFAULT NULL,
  `sudetingas` tinyint(4) DEFAULT NULL,
  `id_parametras` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_tipas`),
  KEY `id_parametras` (`id_parametras`),
  KEY `id_tipas` (`id_tipas`),
  KEY `ParametrasTipas` (`id_parametras`),
  CONSTRAINT `tipas_ibfk_1` FOREIGN KEY (`id_parametras`) REFERENCES `parametras` (`id_parametras`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tipas
-- ----------------------------
