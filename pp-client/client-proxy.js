var express = require('express'),
  path = require('path'),
  httpProxy = require('http-proxy'),
  app = express();

httpProxy.timeout = 25000;

var router =
  new httpProxy.createProxyServer({
    target: "http://localhost:3000"
  });

function apiProxy() {
  return function(req, res, next) {
    if (req.url.match(new RegExp('^\/format\/'))) {
      try {
        router.web(req, res);
      } catch (ex) {
        console.log(ex);
      }
    } else {
      next();
    }
  };
}

app.use(apiProxy('localhost', 3000));
app.use(express.static(__dirname));

app.get('*', function(req, res) {
  console.log('hitting route');
  res.sendFile(
    path.resolve('./main.html'));
});

// start server
var server = app.listen(7000, function() {
  console.log('Listening on port %d', server.address().port);
});