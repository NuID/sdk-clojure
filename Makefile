deps:
	clojure -A:test:runner -P

.env:
	sh make-env.sh

test: .env deps
	clojure -M:test:runner

.PHONY: test
