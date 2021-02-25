version=0.2.0
jar=target/nuid-sdk-v$(version).jar

version:
	@echo $(version)

.env:
	sh scripts/make-env.sh

target:
	mkdir -f target

$(jar):
	clj -X:jar :jar $@

compile: $(jar)

clean:
	rm -rf target
	rm -rf *.pom.asc

deploy: $(jar)
	mvn deploy:deploy-file -Dfile=$(jar) -DpomFile=pom.xml -DrepositoryId=clojars -Durl=https://clojars.org/repo/

deps:
	clojure -A:test:runner -P

deps.update:
	clj -X:deps git-resolve-tags

pom.xml:
	clj -X:deps mvn-pom

test: .env deps
	clojure -M:test:runner

.PHONY: test
