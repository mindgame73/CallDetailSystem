.PHONY: start create-network build-api

start: clean-api clean-mysql build

clean-api:
	docker rmi call_detail_system:latest -f
	docker rm cdr-niiar -f

clean-mysql:
	docker rmi mysql:5.7.21 -f
	docker rm mysql-niiar -f

build:
	docker-compose up -d

migration:
	@ ./migration.sh
