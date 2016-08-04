echo "starting new server build..."
cd ./pp-cljs-server

forever -w start ./target/server/index.js
