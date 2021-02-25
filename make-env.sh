#!/usr/bin/env sh

default_host="https://auth.nuid.io"
host=$NUID_AUTH_API_HOST
api_key=$NUID_AUTH_API_KEY

if [ -z "$host" ]; then
    printf "What's your NuID Auth API Host? (press <enter> for default: %s) " "${default_host}"
    read -r host
    if [ -z "$host" ]; then
        host="${default_host}"
    fi
fi

if [ -z "$api_key" ]; then
    printf "What's your NuID Auth API Key? "
    read -r api_key
fi

echo "NUID_AUTH_API_HOST=${host}" > .env
echo "NUID_AUTH_API_KEY=${api_key}" >> .env
