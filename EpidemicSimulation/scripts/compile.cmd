@ECHO OFF
echo "====== Epidemic Simulation Script on Windows ======"
echo "Compiling..."
javac -cp .\lib\xchart-3.8.0\xchart-3.8.0.jar -sourcepath src src/com/luox6/epidemic/Main.java
echo "Compiling test suit..."
javac -cp .\lib\junit-jupiter-api-5.4.2.jar;.\lib\apiguardian-api-1.0.0.jar;. -sourcepath src src/com/luox6/epidemic/test/*.java
echo "Compilation complete. Generating Javadoc..."
cd .\src
javadoc -classpath .\:..\lib\xchart-3.8.0\xchart-3.8.0.jar -d ..\docs -author -version -encoding UTF-8 com.luox6.epidemic