# Beacon

[![build](https://github.com/kou64yama/beacon/workflows/build/badge.svg)](https://github.com/kou64yama/beacon/actions?query=workflow%3Abuild)
[![codecov](https://codecov.io/gh/kou64yama/beacon/branch/main/graph/badge.svg?token=i9YDV23orl)](https://codecov.io/gh/kou64yama/beacon)
[![FOSSA Status](https://app.fossa.com/api/projects/custom%2B21095%2Fbeacon.svg?type=shield)](https://app.fossa.com/projects/custom%2B21095%2Fbeacon?ref=badge_shield)

A simple beacon server.

![demo](https://user-images.githubusercontent.com/1706782/97574698-f1a6f280-1a2e-11eb-9b6f-2894ba656a81.gif)

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


[![FOSSA Status](https://app.fossa.com/api/projects/custom%2B21095%2Fbeacon.svg?type=large)](https://app.fossa.com/projects/custom%2B21095%2Fbeacon?ref=badge_large)
