
-- ----------------------------
-- Table structure for `player`
-- ----------------------------
DROP TABLE IF EXISTS `player`;
CREATE TABLE `player` (
  `id` bigint(11) NOT NULL DEFAULT '0',
  `userName` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `human`;
CREATE TABLE `human` (
  `guid` bigint(20) NOT NULL DEFAULT '0',
  `playerId` bigint(20) NOT NULL DEFAULT '0',
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `level` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`guid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

