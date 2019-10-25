#!bin/bash

mkdir bin 
javac -cp ".:jar/*" -d bin src/*/*.java src/Exec.java;



java -Dfile.encoding=UTF-8 -classpath ./bin:./resources:./jar/commons-beanutils-1.9.2.jar:./jar/commons-io-2.6.jar:./jar/commons-logging-1.2-javadoc.jar:./jar/commons-logging-1.2.jar:./jar/fontbox-2.0.16.jar:./jar/fontbox-2.0.9.jar:./jar/ghost4j-1.0.1.jar:./jar/hamcrest-core-1.3.jar:./jar/itext-2.1.7.jar:./jar/jai-imageio-core-1.4.0.jar:./jar/jbig2-imageio-3.0.0.jar:./jar/jboss-vfs-3.2.12.Final.jar:./jar/jcl-over-slf4j-1.7.25.jar:./jar/jna-4.1.0.jar:./jar/jul-to-slf4j-1.7.25.jar:./jar/junit-4.12.jar:./jar/lept4j-1.6.4.jar:./jar/log4j-api-2.12.1.jar:./jar/log4j-core-2.12.1.jar:./jar/log4j-over-slf4j-1.7.25.jar:./jar/logback-classic-1.2.3.jar:./jar/logback-core-1.2.3.jar:./jar/pdfbox-2.0.16.jar:./jar/pdfbox-2.0.9.jar:./jar/pdfbox-app-2.0.16.jar:./jar/pdfbox-debugger-2.0.16.jar:./jar/pdfbox-tools-2.0.16.jar:./jar/pdfbox-tools-2.0.9.jar:./jar/preflight-2.0.16.jar:./jar/preflight-app-2.0.16.jar:./jar/slf4j-api-1.7.25.jar:./jar/tess4j-3.4.8.jar:./jar/xmlgraphics-commons-1.5.jar:./jar/xmpbox-2.0.16.jar:/LIB/junit-platform-console-standalone-1.5.1.jar Exec -r -help #-r -d pdf -o result.csv config.txt