:: Make sure you have at least Java 8 installed on your machine to run this.

:: Note that this bash script will only work on Windows
:: For Linux/Mac users, run compile-and-make-jar-file.sh instead.

::compile source files and put class files in bin directory
javac -d bin -cp src src\CheckersGame.java

:: go to bin and create jar file
cd bin
jar -cfe "../Checkers.jar" CheckersGame *