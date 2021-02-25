<p align="right"><a href="https://nuid.io"><img src="https://nuid.io/svg/logo.svg" width="20%"></a></p>

# NuID SDK for Clojure

[![nuid/sdk on Clojars](https://img.shields.io/clojars/v/lynxeyes/dotenv.svg)](https://clojars.org/NuID/sdk)
[![nuid/sdk on cljdoc](https://cljdoc.org/badge/NuID/sdk)](https://cljdoc.org/d/NuID/sdk/CURRENT)
[![portal docs](https://img.shields.io/badge/docs-platform-purple?style=for-the-badge&logo=read-the-docs)](https://portal.nuid.io/docs)
[![test](https://github.com/NuID/sdk-clojure/actions/workflows/test.yml/badge.svg)](https://github.com/NuID/sdk-clojure/actions/workflows/test.yml)

This repo provides a lib for interacting with NuID APIs within Clojure
applications.

Read the latest [lib
docs](https://cljdoc.org/d/NuID/sdk/CURRENT) or
checkout the [NuID platform docs](https://portal.nuid.io/docs) for API docs,
guides, video tutorials, and more.

## Install

### tools.deps

```edn
{:deps
 {nuid/sdk {:git/url "https://github.com/NuID/sdk-clojure"
            :sha     "..."}}}
```

Or from [clojars]():

```
# TODO coming soon
```

## Usage

Example ring handler.

For a more detailed example visit the [Integrating with
NuID](https://portal.nuid.io/docs/guides/integrating-with-nuid) guide and the
accompanying [examples repo](https://github.com/NuID/examples).
A ruby-specific code example is coming soon.


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
