.PHONY: start create-network build-api

CDR_ROOT_DIRECTORY := /home/ribragimov/IdeaProjects/CallDetailSystem

start: clean-api clean-mysql build

clean-api:
	docker rmi call_detail_system:latest -f
	docker rm cdr-niiar -f

clean-mysql:
	docker rmi mysql:5.7.21 -f
	docker rm mysql-niiar -f

build:
	CDR_ROOT_DIRECTORY=$(CDR_ROOT_DIRECTORY) \
	docker-compose up -d

migration:
	@ ./migration.sh
