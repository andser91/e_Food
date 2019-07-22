

CREATE TABLE `restaurant` (
`id` bigint NOT NULL AUTO_INCREMENT,
`name` varchar(255) NOT NULL,
`city` varchar(255) NOT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `restaurant_menu_items` (
`id` bigint NOT NULL,
`name` varchar(255) NOT NULL,
`price` double NOT NULL,
`restaurant_id` bigint NOT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
