# JDBC 기초 다지기 : 

## YouTube
아래 비디오를 보고 따라 해보기
**[Java MySQL Basics Tutorial by Steven Bryrne](https://www.youtube.com/watch?v=Nj76fBhfCxE&list=PLP63X8dYTuaJTAMBZUHScyUCsJVPjSQgJ)*
0. MySQL 설치 및 데이터 베이스 생성 - [MySQL Setup and Database Creation Windows 7](https://youtu.be/Nj76fBhfCxE)
1. MySQL 접속 하기 (Connect) - [Java- MySQL Connection](https://youtu.be/KRhv4iPgzHE)
2. 테이블 생성하기 (CREATE TABLE) - [Java-MySQL Creating a table](https://youtu.be/CBKWoHGWTQE)
3. 데이블에 데이터 저장하기 (INSERT) - [Java-MySQL Insert](https://youtu.be/ru2Mqs5AUuo)
4. 테이블에 저장된 데이터 가져오기 (SELECT) - [Java-MySQL Select](https://youtu.be/HE6ZHSuHcu0)
5. 여러 테이블에 저장된 데이터 가져오기  (SELECT) - [Java MySQL Multi-table Select (WHERE, LIMIT, ORDER, AND)](https://youtu.be/Slw4HFlBm18)

## (참고)  Docker를 이용한 테스트 환경 준비
아래 도구들을 다운로드 해서 설치
- [eclipse](https://www.eclipse.org/downloads/) : Java 개발 IDE
- [Docker Desktop](https://www.docker.com/products/docker-desktop): MySQL 가상환경을 생성

```bash
# docker로 mysql 생성 (root password: 000110)
$ docker run --name chatdb --rm -d -p 3306:3306 -v $(pwd):/host -e MYSQL_ROOT_PASSWORD=000110 mysql --character-set-server=utf8 --collation-server=utf8_unicode_ci --skip-character-set-client-handshake


# database 생성
$ docker exec -it chatdb mysql -uroot -p000110 -e 'CREATE SCHEMA testdb'


# Chatdb create table script 실행
$ docker exec -it chatdb bash -c "ls /host"


$ docker exec chatdb bash -c "mysql -uroot -p{MYSQL_ROOT_PW} < /host/CHATDB_CREATE.sql"
```

## (참고) 기타 docker 상에서 mysql 관련 명령 실행

```bash

$ docker exec -it chatdb mysql -uroot -p000110 -e 'show databases'

$ docker exec -it chatdb mysql -uroot -p000110 -e 'DESCRIBE testdb.testtable'

$ docker exec -it chatdb mysql -uroot -p000110 -e 'SELECT * FROM testdb.testtable'


```