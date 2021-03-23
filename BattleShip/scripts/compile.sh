echo "====== BattleShip Script on Linux ======"
echo "[MSG] Compiling..."
javac -sourcepath src src/com/luox6/battleship/Main.java
echo "[MSG] Compiling test suit..."
javac -cp ./lib/junit-jupiter-api-5.4.2.jar:./lib/apiguardian-api-1.0.0.jar:. -sourcepath src src/com/luox6/battleship/test/*.java
echo "[MSG] Compilation success. Generating Javadoc..."
cd ./src
javadoc -author -version -encoding UTF-8 com.luox6.battleship -d ../docs