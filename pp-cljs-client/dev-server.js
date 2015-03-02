// This server just makes it easier to do frontend development without having
// to load the Clojure server at the same time.

var express = require('express'),
  path = require('path'),
  httpProxy = require('http-proxy'),
  config = require('../config/config.json'),
  app = express();

httpProxy.timeout = 25000;

const devServerPort = config["dev-server-port"],
  clojureProxyPort = config["jvm-server-port"],
  nodeProxyPort = config["cljs-server-port"];

var clojureRouter = new httpProxy.createProxyServer({
  target: "http://localhost:" + clojureProxyPort
});

var nodeRouter = new httpProxy.createProxyServer({
  target: "http://localhost:" + nodeProxyPort
});

function clojureProxy() {
  return function(req, res, next) {
    if (req.url.match(new RegExp('^\/jvm\/format\/'))) {
      console.log("clojure proxy");
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
    if (req.url.match(new RegExp('^\/node\/format\/'))) {
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
  console.log('pretty-print.net dev server listening on port ' +
    devServerPort);
});