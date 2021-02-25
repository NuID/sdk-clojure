<p align="right"><a href="https://nuid.io"><img src="https://nuid.io/svg/logo.svg" width="20%"></a></p>

# NuID SDK for Clojure

[![nuid/sdk on Clojars](https://img.shields.io/clojars/v/lynxeyes/dotenv.svg)](https://clojars.org/NuID/sdk)
[![nuid/sdk on cljdoc](https://cljdoc.org/badge/NuID/sdk)](https://cljdoc.org/d/NuID/sdk/CURRENT)
[![portal docs](https://img.shields.io/badge/docs-platform-purple?style=for-the-badge&logo=read-the-docs)](https://portal.nuid.io/docs)

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

You'll want to download docker to run the tests, as we depend on the
`@nuid/cli` npm package to provide a CLI you can shell out to
in the tests for generating zk crypto. After checking out the repo, run
`bin/setup` to install dependencies and create the docker environment. Then, run
`make test` to run the tests inside the running container. You can also run
`bin/console` for an interactive prompt that will allow you to experiment, but
you'll probably want to run that in the container (use `make shell` to get a
prompt in the container).

`make clean` will stop and destroy the container and image. `make build run`
will rebuild the image and run the container.

## Contributing

Bug reports and pull requests are welcome on GitHub at https://github.com/NuID/sdk-clojure.

## License

The gem is available as open source under the terms of the [MIT License](https://opensource.org/licenses/MIT).
