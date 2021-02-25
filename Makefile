clean:
	rm -rf target
	rm -rf *.pom.asc

deploy:
	mvn deploy

pom.xml:
	clj -X:deps mvn-pom

deps:
	clojure -A:test:runner -P

deps.update:
	clj -X:deps git-resolve-tags

.env:
	sh make-env.sh

test: .env deps
	clojure -M:test:runner

.PHONY: test
