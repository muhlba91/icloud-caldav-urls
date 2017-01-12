# "iCloud calendar URLs" Docker container

This docker container uses PHP-FPM and nginx to deliver the PHP version of [iCloud calendar URLs](icloud.niftyside.com).


## Usage

The conainer **exposes port 80**.
In fact, simply run the container with Docker and open <http://127.0.0.1>.

    $ docker run -d -p 80:80 muhlba91/icloud


