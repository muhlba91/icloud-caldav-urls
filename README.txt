-------------------
How does this work?
-------------------

Because of some phishing related issues with Apple I decided to not host the script anymore.
As a matter of fact, you have to run it on your own and I will provide you a short howto on how you can do this easily.
Adcditionally, I also made a simple Java application.

If this script helps you, please consider at least a small donation (https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=JVTUEYXWG76MA). Thank you for your support!


------------
Side effects
------------

Before providing you the download links I want to tell you one interesting side effect I was notified about.
If you want to use the iCloud CardDAV (address book) service with third-party clients it should work if you just use the principal URL with the appropriate iCloud server: https://p01-contacts.icloud.com/<PRINCIPAL_URL>.
Please note that you can use the servers p01 to p08 if you want! (I added a select field in the script for that.) 


-------------------------
Download and restrictions
-------------------------

First of all, due to legal issues with Apple I need to prohibit:

    You are not allowed to make the script, the application or anything else publicly available or to republish them.
    You are not allowed to modify any of the files and/or source code.
    Regarding PHP version: You are only allowed to use it for your personal use on your only locally reachable server. (localhost)
       A simple workaround for this could be that you use htaccess to ask, for example, for a password (please use Google).

Additionally, I want to refer you to the appropriate blog post (http://blog.muehlbachler.org/2011/08/how-to-icloud-calendars-with-third-party-software/) where I will post updates if there are any.

So, here is the download:

    Git repository: https://github.com/muhlba91/icloud
    Java version: http://icloud.niftyside.com/icloud.jar, http://icloud.niftyside.com/icloud-src.tgz, http://icloud.niftyside.com/icloud-src.zip
    PHP .tar.gz/.tgz: http://icloud.niftyside.com/icloud.tgz
    PHP .zip: http://icloud.niftyside.com/icloud.zip

Instructions for the Java version:

    download the jar file and either open it by double-clicking on it - if it does not work, type java -jar icloud.jar into your console/command line
    download the source and compile it on your own



-------------------------------------------
How can you run the PHP script on your own?
-------------------------------------------

At first, you need a working webserver with PHP and cURL.
The easiest way to get this is the XAMPP project (http://www.apachefriends.org/de/xampp.html). There you can download an Apache/MySQL and PHP bundle for your Windows, Mac or Linux computer.
With that bundle it is really easy to get the script running:

    Download the bundle (I recommend the packed version and not the executable/self-install one) and unpack it.
    Now it depends on the version you are using:
        Windows: goto the php folder and open the file php.ini.
        Linux: goto the etc/php folder and open the file php.ini.
        Mac: goto the xamppfiles/etc/php folder and open the file php.ini.
    In this file search for "curl" and uncomment this line where it appears. (e.g. extension=php_curl.dll in Windows)
    Download my script (download link: see later), unpack it and copy the file icloud.php into the htdocs folder of your XAMPP installation.
    Now start Apache depending on the version you are using:
        Windows: double click on apache_start.bat or open xampp-control.exe and start Apache by clicking on the appropriate button
        Linux: please read through the XAMPP documentation - but I think that you just need to run lampp
        Mac: open XAMPP Controll.app and click on the appropriate button to start Apache
    Point you browser to: http://localhost/icoud.php (if this does not work just try http://localhost/icloud/icloud.php!).
    Fill in the form and click on Evaluate.
    Copy the URLs you need and stop Apache again.


-----------------
Copyright notices
-----------------

This script is copyright (C) 2011-2012 by NiftySide - Daniel Mühlbachler (http://www.niftyside.com).
iCloud is a service and is provided by Apple, Inc. (http://www.apple.com)
