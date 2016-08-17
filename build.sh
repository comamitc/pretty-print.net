echo "install npm dependencies..."
lein npm install

echo "building artifacts..."
lein clean
lein prod
