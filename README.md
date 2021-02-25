<p align="right"><a href="https://nuid.io"><img src="https://nuid.io/svg/logo.svg" width="20%"></a></p>

# NuID SDK for Clojure

[![nuid/sdk on Clojars](https://img.shields.io/clojars/v/nuid/sdk.svg)](https://clojars.org/nuid/sdk)
[![nuid/sdk on cljdoc](https://cljdoc.org/badge/nuid/sdk)](https://cljdoc.org/d/nuid/sdk/CURRENT)
[![portal docs](https://img.shields.io/badge/docs-platform-purple?logo=read-the-docs)](https://portal.nuid.io/docs)
[![test](https://github.com/NuID/sdk-clojure/actions/workflows/test.yml/badge.svg)](https://github.com/NuID/sdk-clojure/actions/workflows/test.yml)

This repo provides a lib for interacting with NuID APIs within Clojure
applications.

Read the latest [lib
docs](https://cljdoc.org/d/nuid/sdk/CURRENT) or
checkout the [NuID platform docs](https://portal.nuid.io/docs) for API docs,
guides, video tutorials, and more.

## Install

### Clojure CLI/deps.edn

``` clojure
nuid/sdk {:mvn/version "0.3.0"}
;; or
nuid/sdk {:git/url "https://github.com/NuID/sdk-clojure" :sha "..."}
```

### Leiningen/Boot

``` clojure
[nuid/sdk "0.3.0"]
```

### Gradle

``` clojure
compile 'nuid:sdk:0.3.0'
```

### Maven

``` maven-pom
<dependency>
  <groupId>nuid</groupId>
  <artifactId>sdk</artifactId>
  <version>0.3.0</version>
</dependency>
```

## Usage

Example ring handler.

For a more detailed example visit the [Integrating with
NuID](https://portal.nuid.io/docs/guides/integrating-with-nuid) guide and the
accompanying [examples repo](https://github.com/NuID/examples).
A clojure-specific code example is coming soon.


```clojure
;; TODO
```

## Development

You'll need the following dependencies on your system:

+ OpenJDK 8
+ Clojure 1.10
+ NodeJS 12 (with npm)

You can invoke the tests using `make test`. On your first run you'll be prompted
for the Auth API Host and Key.

## Contributing

Bug reports and pull requests are welcome on GitHub at https://github.com/NuID/sdk-clojure.

## License

The gem is available as open source under the terms of the [MIT License](https://opensource.org/licenses/MIT).
