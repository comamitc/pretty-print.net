var express = require('express'),
  path = require('path'),
  httpProxy = require('http-proxy'),
  app = express();

httpProxy.timeout = 25000;

var clojureRouter = new httpProxy.createProxyServer({
  target: "http://localhost:3000"
});

var nodeRouter = new httpProxy.createProxyServer({
  target: "http://localhost:5000"
});

function clojureProxy() {
  return function(req, res, next) {
    if (req.url.match(new RegExp('^\/data\/format\/'))) {
      try {
        clojureRouter.web(req, res);
      } catch (ex) {
        console.log(ex);
      }
    } else {
      next();
    }
  };
}

function nodeProxy() {
  return function(req, res, next) {
    if (req.url.match(new RegExp('^\/lang\/format\/'))) {
      try {
        nodeRouter.web(req, res);
      } catch (ex) {
        console.log(ex);
      }
    } else {
      next();
    }
  };
}

app.use(clojureProxy('localhost', 3000));
app.use(express.static(__dirname));

app.get('*', function(req, res) {
  console.log('hitting route');
  res.sendFile(
    path.resolve('./index.html'));
});

// start server
var server = app.listen(7000, function() {
  console.log('Listening on port %d', server.address().port);
});