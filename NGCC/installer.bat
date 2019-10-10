#!bin/bash


mkdir bin;

javac -cp ".;NGCC/jar/*" -d bin NGCC/src/*/*.java NGCC/src/Exec.java

cd bin

java -cp ".;NGCC/jar/*" Exec