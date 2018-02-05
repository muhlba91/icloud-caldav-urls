# "iCloud calendar URLs" Docker container for the PHP version
# see: https://github.com/muhlba91/icloud

# basic container
FROM webdevops/php-nginx:alpine-3-php7

# labels
LABEL maintainer "Daniel Muehlbachler daniel.muehlbachler@niftyside.com"
LABEL name "iClound calendar URLs on Docker"
LABEL description "This container provides the PHP script for retrieving iCloud calendar URLs (https://icloud.niftyside.com)."


# environment setup
ENV WEB_DOCUMENT_ROOT /app/icloud/PHP
ENV WEB_DOCUMENT_INDEX icloud.php


# install git
RUN apk update && apk upgrade && \
    apk add --no-cache bash git

# clone repo
WORKDIR /app
ENV APP_VERSION 1.4.1
RUN git clone https://github.com/muhlba91/icloud.git icloud
