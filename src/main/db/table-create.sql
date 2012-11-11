SET FOREIGN_KEY_CHECKS=0;

CREATE TABLE `book` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`version`  int(11) NOT NULL DEFAULT 0 ,
`create_date`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`title`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`id`),
INDEX `title` (`title`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=Compact
;

CREATE TABLE `book_collection` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`version`  int(11) NOT NULL DEFAULT 0 ,
`create_date`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`score`  int(11) NOT NULL DEFAULT 3 ,
`comment`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`customer_id`  bigint(20) NOT NULL ,
`book_id`  bigint(20) NOT NULL ,
PRIMARY KEY (`id`),
CONSTRAINT `book_collection_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
CONSTRAINT `book_collection_ibfk_3` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `book_id` (`book_id`) USING BTREE ,
INDEX `customer_id` (`customer_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=Compact
;

CREATE TABLE `collection_tag` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`version`  int(11) NOT NULL DEFAULT 0 ,
`create_date`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`collection_id`  bigint(20) NOT NULL ,
`tag_id`  bigint(20) NOT NULL ,
PRIMARY KEY (`id`),
CONSTRAINT `collection_tag_ibfk_2` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
CONSTRAINT `collection_tag_ibfk_3` FOREIGN KEY (`collection_id`) REFERENCES `book_collection` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `tag_id` (`tag_id`) USING BTREE ,
INDEX `collection_id` (`collection_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=Compact
;

CREATE TABLE `customer` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`version`  int(11) NOT NULL DEFAULT 0 ,
`create_date`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`id`),
INDEX `name` (`name`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=Compact
;

CREATE TABLE `tag` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`version`  int(11) NOT NULL DEFAULT 0 ,
`create_date`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`id`),
UNIQUE INDEX `name` (`name`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=Compact
;

SET FOREIGN_KEY_CHECKS=1;

