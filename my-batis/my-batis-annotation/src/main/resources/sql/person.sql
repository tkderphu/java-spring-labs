CREATE TABLE `persons` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'username',
  `password` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'password',
  `created_time` datetime DEFAULT NULL COMMENT 'createdTime',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;