# iCloud calendar URLs

This software provides a possibility to gather all **CalDAV URLs** for usage in third-party clients, like Mozilla Thunderbird.
Additionally, it also provides the - in most cases - correct **CardDAV URL**.

## Donations

If this software helps you to achieve your goal please consider at least a small donation.

[![Donate](https://img.shields.io/badge/Donate-PayPal-green.svg)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=JVTUEYXWG76MA)

(Even a small amount helps financing the server and development costs. :-) )

## Usage

### Webserver requirements

  - PHP
  - cURL (has to be enabled in PHP as well)

For example, you can you the [XAMPP project](http://www.apachefriends.org/de/xampp.html)

#### cURL in PHP

To enable cURL in PHP, modify the `php.ini` file, search for a line like `extension=php_curl.dll` and uncomment it.

### Installation

  - download the PHP script `icloud.php` and place it into the webserver directory
  - open the URL
    - if you are using XAMPP, it will most likely be http://localhost/icoud.php
  - fill in the form and copy your URLs
