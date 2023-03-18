#!/bin/bash

#Make sure you have at least Java 8 installed on your machine to run this.

# Note that this bash script will only work on Linux/Mac machines
# You can also use Windows Subsystem for Linux on Windows to run this .sh file.

#compile source files and put class files in bin directory
javac -d bin -cp src src/CheckersGame.java

#go to bin and create jar file
cd bin
jar -cfe "../Checkers.jar" CheckersGame *