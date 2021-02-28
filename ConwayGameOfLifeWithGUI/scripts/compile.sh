echo "====== GameOfLife Script on Linux ======"
echo "[MSG] Compiling..."
javac -sourcepath src src/com/luox6/conway/Main.java
echo "[MSG] Compiling test suit..."
javac -cp ./deps/junit-jupiter-api-5.7.1.jar:./deps/apiguardian-api-1.1.1.jar:. -sourcepath src src/com/luox6/conway/test/*.java
echo "[MSG] Compilaton success. Generating Javadoc..."
cd ./src
javadoc -author -version -encoding UTF-8 com.luox6.conway -d ../docs