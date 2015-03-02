# frontend

## Dev Workflow

1. Install Dependencies

```sh
$> npm install -g grunt-cli nodemon
$> npm install
```

2. Run NodeJS Server

```sh
$> nodemon dev-server.js
```

3. Run CLJS build (new terminal)

```sh
$> lein clean && lein cljsbuild auto
```

4. Run grunt (new terminal)

```sh
$> grunt
```

5. Open your browser

`http://localhost:<port>/`


## License

Copyright (c) 2015 Mitch Comardo and contributors

