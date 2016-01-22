#   this has to be here because of memory concerns on destination server
echo "killing old jvm server process..."
pkill -f "target/pp-jvm-server-0.1.0-standalone.jar"

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
cd ./pp-jvm-server

lein clean
lein ring uberjar


