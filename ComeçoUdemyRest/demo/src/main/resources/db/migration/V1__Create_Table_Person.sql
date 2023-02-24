CREATE TABLE `person` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(100) NOT NULL,
  `first_name` varchar(80) NOT NULL,
  `gender` varchar(20) NOT NULL,
  `last_name` varchar(80) NOT NULL,
  `enabled` BIT(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`id`)
);
