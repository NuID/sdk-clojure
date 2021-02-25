version=0.1.0
jar=target/nuid-sdk-v$(version).jar

version:
	@echo $(version)

.env:
	sh make-env.sh

target:
	mkdir -f target

$(jar):
	clj -X:jar :jar $@

compile: $(jar)

clean:
	rm -rf target
	rm -rf *.pom.asc

deploy: $(jar)
	clj -X:deploy :artifact $(jar)

deps:
	clojure -A:test:runner -P

deps.update:
	clj -X:deps git-resolve-tags

pom.xml:
	clj -X:deps mvn-pom

test: .env deps
	clojure -M:test:runner

.PHONY: test
