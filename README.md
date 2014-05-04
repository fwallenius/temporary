Qwaya work sample
=================
Simple analytics tool.

Build from sources
------------------
Before running you will need to build the "executable". This is a java project that is built using Gradle, but since the Gradle Wrapper is included in the repository so you will not need to have Gradle installed. After un-zipping the repository cd into the top folder and run:
```console
$ ./gradlew build
```

Run the app
-----------
After having built the jar-file it can be start with the following command, still standing in the repo's top folder:
```console
$ java -jar build/libs/pixel-stats-0.1.0.jar
```
Running the app without arguments will show the help-text that explains how to either start it in server-mode or to show the report.

Gather data
-----------
Include the following image-tag in all pages where hits should be collected:
```html
<img src="http://YOUR_IP_OR_DOMAIN:8080/pixel.gif" />
```
The app reads the 'Referer' header to determine which page was visited. It will set an eternal cookie with a unique user id the first time somebody visits the site.