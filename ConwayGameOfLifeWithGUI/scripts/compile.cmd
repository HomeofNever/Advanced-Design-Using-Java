@ECHO OFF
echo "====== GameOfLife Script on Windows ======"
echo "Compiling..."
javac -sourcepath src src/com/luox6/conway/Main.java
echo "Compiling test suit..."
javac -cp .\deps\junit-jupiter-api-5.7.1.jar;.\deps\apiguardian-api-1.1.1.jar;. -sourcepath src src/com/luox6/conway/test/*.java
echo "Compilaton complete. Generating Javadoc..."
cd .\src
javadoc -author -version -encoding UTF-8 com.luox6.conway -d ..\docs