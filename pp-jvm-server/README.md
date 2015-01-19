# pp-jvm-server

# API

### POST /jvm/format/:type `{clj|edn|json|xml|scala}`

+ Request (application/json)
```
{
    "input": "(defn foo [x] (+ x 1))"
}
```
+ Response 200 (text/plain)
```
(defn foo [x]
    (+ x 1))
```
+ Response 400 (application/json)
```
{
    "line": "1",
    "column": "13",
    "msg": "Expected ';' but found ')'"
}
```

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server-headless

## License

Copyright (c) 2015 Mitch Comardo and contributors
