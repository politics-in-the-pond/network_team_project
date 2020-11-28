# 애플리케이션 DB 스키마 생성 (스키마 이름: chat) 
CREATE SCHEMA `chat` ;

# 사용자 테이블 생성
CREATE TABLE `chat`.`users` (
  `id` VARCHAR(15) NOT NULL,
  `nickname` VARCHAR(15) NOT NULL,
  `name` VARCHAR(30) NOT NULL,
  `pw` VARCHAR(12) NOT NULL,
  `birthdate` DATE  NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `tel` VARCHAR(20) NOT NULL,
  `web` VARCHAR(45) NULL,
  `status` TINYINT NOT NULL DEFAULT 0,
  `comment` VARCHAR(200) NULL,
  `last_connected` DATETIME,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `nickname_UNIQUE` (`nickname` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
COMMENT = '사용자 등록 & 상태 표시를 위한 테이블';

# 테스트 사용자 입력
INSERT INTO `chat`.`users` (`id`, `nickname`, `name`, `pw`, `birthdate`, `email`, `tel`, `web`) VALUES ('000001', '오리', '고라파덕', '000110', '20200101', 'paduck@pocket.mon', '01012345678', 'http://www.pocket.mon/paduck');
INSERT INTO `chat`.`users` (`id`, `nickname`, `name`, `pw`, `birthdate`, `email`, `tel`) VALUES ('000002', '쥐', '피카츄', '000110', '20200202', 'pakka@pocket.mon', '01012345679');
INSERT INTO `chat`.`users` (`id`, `nickname`, `name`, `pw`, `birthdate`, `email`, `tel`) VALUES ('000003', '거북', '꼬부기', '000110', '20200303', 'kkobu@pocket.mon', '01012345670');
INSERT INTO `chat`.`users` (`id`, `nickname`, `name`, `pw`, `birthdate`, `email`, `tel`) VALUES ('000004', '도롱뇽', '파이리', '000110', '20200404', 'pa@pocket.mon', '01012345671');
INSERT INTO `chat`.`users` (`id`, `nickname`, `name`, `pw`, `birthdate`, `email`, `tel`) VALUES ('000005', '식물', '이상해씨', '000110', '20200505', 'strange@pocket.mon', '01012345672');

# SELECT * FROM chat.users;

# 친구 등록 테이블 생성
CREATE TABLE `chat`.`friends` (
  `id` VARCHAR(15) NOT NULL,
  `friend_id` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`id`, `friend_id`))
COMMENT = '친구 등록을 위한 테이블';

# test user 1
INSERT INTO `chat`.`friends` (`id`, `friend_id`) VALUES ('000001', '000002');
INSERT INTO `chat`.`friends` (`id`, `friend_id`) VALUES ('000001', '000003');
INSERT INTO `chat`.`friends` (`id`, `friend_id`) VALUES ('000001', '000005');

# test user 2
INSERT INTO `chat`.`friends` (`id`, `friend_id`) VALUES ('000002', '000001');
INSERT INTO `chat`.`friends` (`id`, `friend_id`) VALUES ('000002', '000004');



#select id, name, nickname, status , comment, last_connected
#from users
#where id in (select friend_id from friends where id = '000001');
