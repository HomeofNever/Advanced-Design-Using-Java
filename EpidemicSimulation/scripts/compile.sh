echo "====== Epidemic Simulation Script on Linux ======"
echo "[MSG] Compiling..."
javac -cp ./lib/xchart-3.8.0/xchart-3.8.0.jar -sourcepath src src/com/luox6/epidemic/Main.java
echo "[MSG] Compiling test suit..."
javac -cp ./lib/junit-jupiter-api-5.4.2.jar:./lib/apiguardian-api-1.0.0.jar:. -sourcepath src src/com/luox6/epidemic/test/*.java
echo "[MSG] Compilation success. Generating Javadoc..."
cd ./src
javadoc -cp ./:../lib/xchart-3.8.0/xchart-3.8.0.jar -d ../docs -author -version -encoding UTF-8 com.luox6.epidemic