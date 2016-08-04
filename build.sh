#   this has to be here because of memory concerns on destination server
echo "killing old server process..."
cd ./pp-node-server
forever stop ./target/server/index.js
cd ..

# copy default config file
echo "copying config file..."
cp ./config/config.example.json ./config/config.json

# client
echo "building client..."
cd ./pp-cljs-client

## node work
echo "building node assets for client..."
npm install -g grunt-cli
npm install
grunt prod

## cljs work
echo "building cljs assets for client..."
lein clean
lein cljsbuild once main-min

cd ..

# server
echo "building server assets for jvm server..."
cd ./pp-node-server

npm install -g forever
npm install
lein cljsbuild once server
