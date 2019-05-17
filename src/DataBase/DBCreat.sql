CREATE TABLE `spectra_data_dstar` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `versionid` varchar(255) NOT NULL COMMENT 'project_name',
  `bugid` int(11) NOT NULL COMMENT 'bugid',
  `filename` varchar(255) NOT NULL COMMENT '文件名',
  `line` bigint(11) NOT NULL COMMENT '行号',
  `suspicion` double DEFAULT NULL COMMENT '怀疑度',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `spectra_data_ochiai` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `versionid` varchar(255) NOT NULL COMMENT 'project_name',
  `bugid` int(11) NOT NULL COMMENT 'bugid',
  `filename` varchar(255) NOT NULL COMMENT '文件名',
  `line` bigint(11) NOT NULL COMMENT '行号',
  `suspicion` double DEFAULT NULL COMMENT '怀疑度',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `spectra_data_Metall_dstar` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `versionid` varchar(255) NOT NULL COMMENT 'project_name',
  `bugid` int(11) NOT NULL COMMENT 'bugid',
  `filename` varchar(255) NOT NULL COMMENT '文件名',
  `line` bigint(11) NOT NULL COMMENT '行号',
  `suspicion` double DEFAULT NULL COMMENT '怀疑度',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `spectra_data_Metall_ochiai` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `versionid` varchar(255) NOT NULL COMMENT 'project_name',
  `bugid` int(11) NOT NULL COMMENT 'bugid',
  `filename` varchar(255) NOT NULL COMMENT '文件名',
  `line` bigint(11) NOT NULL COMMENT '行号',
  `suspicion` double DEFAULT NULL COMMENT '怀疑度',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `spectra_data_Metallaxis` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `versionid` varchar(255) NOT NULL COMMENT 'project_name',
  `bugid` int(11) NOT NULL COMMENT 'bugid',
  `filename` varchar(255) NOT NULL COMMENT '文件名',
  `line` bigint(11) NOT NULL COMMENT '行号',
  `suspicion` double DEFAULT NULL COMMENT '怀疑度',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;