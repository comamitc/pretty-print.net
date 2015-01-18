// This server just makes it easier to do frontend development without having
// to load the Clojure server at the same time.

var express = require('express'),
  path = require('path'),
  httpProxy = require('http-proxy'),
  app = express();

httpProxy.timeout = 25000;

// TODO: read these ports from a config file
const devServerPort = 7000,
  clojureProxyPort = 3000,
  nodeProxyPort = 5000;

var clojureRouter = new httpProxy.createProxyServer({
  target: "http://localhost:" + clojureProxyPort
});

var nodeRouter = new httpProxy.createProxyServer({
  target: "http://localhost:" + nodeProxyPort
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

app.use(clojureProxy('localhost', clojureProxyPort));

// serve static files out of /public
app.use(express.static(__dirname + '/public'));

// start server
var server = app.listen(devServerPort, function() {
  console.log('pretty-print.net dev server listening on port ' + devServerPort);
});
