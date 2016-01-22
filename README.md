# pretty-print.net

[![Codeship Status](https://codeship.com/projects/8d600b00-a35d-0133-0ef4-1ac8bff03ae9/status?branch=master
)](https://www.codeship.io/projects/129215)


## Rationale

EDN is a powerful data interchange format commonly used with Clojure and ClojureScript programs. It also doubles as a literal representation of most Clojure data structures, which are often printed to a REPL or console environment for debugging purposes.

Clojure core comes with clojure.pprint - which is a library to format Clojure code (and thus EDN) in a human-readable fashion.

As of January 2015, there are numerous online services to pretty print various data interchange formats (JSON, YAML, XML, etc), but there is no online service for printing an EDN string.

This project aims to build such a service and improve the usability of working with EDN data.
