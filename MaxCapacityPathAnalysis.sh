#!/bin/bash

javac src/*.java
mkdir classes
mv src/*.class classes/

echo "Enter 6 for sparse graph OR 1000 for dense graph"
read degree

cd classes
java MaxCapPathAnalysis $degree
cd ..

rm -rf classes
