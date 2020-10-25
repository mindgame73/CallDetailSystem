.PHONY: start create-network build-api

CDR_ROOT_DIRECTORY := /home/ribragimov/IdeaProjects/CallDetailSystem

start: clean build-api

clean-api:
	docker rmi call_detail_system:latest -f
	docker rm cdr-niiar -f

build-api:
	CDR_ROOT_DIRECTORY=$(CDR_ROOT_DIRECTORY) \
	docker-compose up -d