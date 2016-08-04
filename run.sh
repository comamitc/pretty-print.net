echo "starting new server build..."

nohup node target/server/index.js > output.log &
