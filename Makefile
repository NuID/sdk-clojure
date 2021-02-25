image=nuid/sdk-clojure
container=nuid-sdk-clojure

build:
	docker build -t "$(image):latest" .

clean: stop rm rmi

rm:
	docker rm $(container)

rmi:
	docker rmi $(image)

run:
	docker run -v $$PWD:/nuid/sdk-clojure -it -d --env-file .env --name $(container) $(image) /bin/sh

shell:
	docker exec -it $(container) /bin/sh

stop:
	docker stop $(container)

test:
	docker exec -it $(container) clj -M:test:runner

.PHONY: test
