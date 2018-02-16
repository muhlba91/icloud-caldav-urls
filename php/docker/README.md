# "iCloud calendar URLs" Docker container

This docker container uses PHP-FPM and nginx to deliver the PHP version of [iCloud calendar URLs](https://icloud.niftyside.com).

## Project Status

[![Build Status](https://travis-ci.org/muhlba91/icloud.svg?branch=master)](https://travis-ci.org/muhlba91/icloud)

## Donations

If this software helps you to achieve your goal please consider at least a small donation.

[![Donate](https://img.shields.io/badge/Donate-PayPal-green.svg)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=JVTUEYXWG76MA)

(Even a small amount helps financing the server and development costs. :-) )

## Usage

The conainer **exposes port 80**.
In fact, simply run the container with Docker and open <http://127.0.0.1>.
```
docker run -d -p 80:80 muhlba91/icloud:latest
```
