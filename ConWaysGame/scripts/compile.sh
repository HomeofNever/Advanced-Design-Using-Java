echo "====== GameOfLife Script on Linux ======"
echo "[MSG] Compiling..."
javac -cp './deps/*' ./src/**/*.java
echo "[MSG] Compilaton success. Generating Javadoc..."
cd ./src
javadoc -encoding UTF-8 com.luox6.conway -d ../docs