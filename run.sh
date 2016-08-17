echo "stopping current server..."
kill `lsof -n -iTCP:7000 -sTCP:LISTEN -t`
