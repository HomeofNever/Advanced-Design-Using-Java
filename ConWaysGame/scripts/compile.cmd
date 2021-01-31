@ECHO OFF
echo "====== GameOfLife Script on Windows ======"
echo "Compiling..."
javac -cp '.\deps\*' -sourcepath src src/com/luox6/conway/Main.java
echo "Compilaton complete. Generating Javadoc..."
cd .\src
javadoc -encoding UTF-8 com.luox6.conway -d ..\docs