echo "killing old jvm server process..."
pkill -f "target/pp-jvm-server-0.1.0-standalone.jar"

echo "starting new server build..."
cd ./pp-jvm-server
nohup java -jar -Xmx384m -Xms384m target/pp-jvm-server-0.1.0-standalone.jar &
