FROM clojure:openjdk-8-tools-deps-slim-buster
LABEL maintainer="NuID Developers <dev@nuid.io>"
WORKDIR /nuid/sdk-clojure
ADD . .
RUN apt-get -y update
RUN apt-get -y install --no-install-recommends nodejs npm
RUN npm install
ENV PATH=$PATH:/nuid/sdk-clojure/node_modules/.bin
RUN clj -A:test:runner -P
CMD /bin/sh
