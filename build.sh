echo "stopping current server..."
kill `lsof -n -iTCP:5000 -sTCP:LISTEN -t`

# copy default config file
echo "copying config file..."
cp ./config/config.example.json ./config/config.json

echo "install npm dependencies..."
lein npm install

echo "building artifacts..."
lein clean
lein prod
