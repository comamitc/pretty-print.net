# pp-node-server

A cljs server that will format other things. JS has more libraries!

# API

### POST /node/format/:type `{js|html|css}`

+ Request (application/json)
```
{
    "input": "function foo(x) { return x + 1; }"
}
```
+ Response 200 (text/plain)
```
function foo(x) {
  return x + 1;
}
```
+ Response 400 (application/json)
```
{
    "line": "1",
    "column": "13",
    "msg": "Expected ';' but found ')'"
}
```

## License

Copyright © 2015 Mitch Comardo and contributors

Distributed under the MIT License