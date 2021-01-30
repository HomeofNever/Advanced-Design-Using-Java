@ECHO OFF
echo "====== GameOfLife Script on Windows ======"
echo "Compiling..."
javac -cp '.\deps\*' .\src\**\*.java
echo "Compilaton success. Generating Javadoc..."
cd .\src
javadoc -encoding UTF-8 com.luox6.conway -d ..\doc