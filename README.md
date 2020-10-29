# Beacon

[![build](https://github.com/kou64yama/beacon/workflows/build/badge.svg)](https://github.com/kou64yama/beacon/actions?query=workflow%3Abuild)

A simple beacon server.

## Requirements

Beacon requires the following to run:

- Java 11+

## Getting started

Runs the app in the development mode.

```shell
$ ./gradlew bootRun
```

Accessing http://localhost:7888/epoch returns the epoch time every second.

```shell
$ curl -Ns http://localhost:7888/epoch | head -n3
{"millis":1603901928798}
{"millis":1603901929797}
{"millis":1603901930797}
```

You can also use RSocket.

```shell
$ rsc ws://localhost:7888/rsocket --route epoch --stream --take 3
{"millis":1603901928798}
{"millis":1603901929797}
{"millis":1603901930797}
```

## License

Beacon is licensed under the [MIT](https://github.com/kou64yama/beacon/blob/main/LICENSE) license.
