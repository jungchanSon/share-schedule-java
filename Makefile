#!make
service_name=mysql
db_pass=test1234
MYSQL_USER=root
MYSQL_PASSWORD=test1234
main_db=test

help:
	@echo 명령 목록
	@echo \'make db-start\': 도커 컴포즈 실행. db 서비스 실행 \(재시작\)
	@echo \'make db-quit\': 종료
	@echo \'make db-enter\': db 접속
	@echo \'make db-list\': 종료
	@echo \'make db-table\': 종료
db-start:
	@echo 도커 컴포즈 실행
	@docker-compose up -d --force-recreate
	@docker-compose ps
db-quit:
	@echo 도커 컴포즈 종료
	@docker-compose down -v
db-enter:
	@echo db 접속
	@docker-compose exec -it $(service_name) mysql -u"$(MYSQL_USER)" -p"$(MYSQL_PASSWORD)"
db-list:
	@echo db 목록
	@docker-compose exec -it $(service_name) mysql -u"$(MYSQL_USER)" -p"$(MYSQL_PASSWORD)" -e"show databases;"
db-table:
	@echo 테이블들
	@docker-compose exec -it $(service_name) mysql -u"$(MYSQL_USER)" -p"$(MYSQL_PASSWORD)" -e"use $(main_db);" -e"show tables;"
