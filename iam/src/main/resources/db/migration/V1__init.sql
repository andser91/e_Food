CREATE TABLE `user` (
                      `id` bigint NOT NULL AUTO_INCREMENT,
                      `email` varchar(255) default null,
                      `username` varchar(255) default null,
                      `password` varchar(255) default null,
                      `firstname` varchar(255) DEFAULT NULL,
                      `lastname` varchar(255) DEFAULT NULL,
                      `version` bigint,
                      `enabled` boolean,
                      `credentials_expired` boolean,
                      `account_locked` boolean,
                      `account_expired` boolean,
                      `created_at` TIMESTAMP DEFAULT NULL,
                      `updated_at` TIMESTAMP DEFAULT NULL,
                      `deleted_at` TIMESTAMP DEFAULT NULL,
                      PRIMARY KEY (`id`),
                      UNIQUE (`email`),
                      UNIQUE (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE role (
                    `id` bigint NOT NULL AUTO_INCREMENT,
                    `name` varchar(255) DEFAULT NULL,
                    `created_at` TIMESTAMP DEFAULT NULL,
                    `updated_at` TIMESTAMP DEFAULT NULL,
                    `deleted_at` TIMESTAMP DEFAULT NULL,
                    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE user_roles (
                          `user_id` bigint NOT NULL AUTO_INCREMENT,
                          `roles_id` bigint references role,
                          PRIMARY KEY (`user_id`,`roles_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `role` (id,name, created_at, updated_at, deleted_at) VALUES (1,'User', '2019-08-13 14:31:41.538822',null, null);
