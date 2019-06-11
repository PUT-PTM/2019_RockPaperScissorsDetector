# 2019_RockPaperScissorsDetector

## Overview
````
Arbiter (server) and client to play game: "Rock Paper Scissorss".
````
## Description
```
The project conttains two apps, server written in Android Studio for your mobal device, and client written in C for your STM32 microcontroller.
ESP8266 is used to communicate between client and server via WiFi. 
```

## Tools
```
Android Studio v3.4.1 
CubeMX v4.27.0
Eclipse 
```
## How to run 
```
RELEASE version: 1.0

1. Clone project
2. Change your AP name to: "XPERIA5ESP"
3. Change your password to: "12345678"
4. Change your static IP address of ur device to "192.168.43.1"
5. If some points from 2 to 4 are impossible to be done u can change code in 
"inline void connectToWiFi()" and "inline void connectToServer()" functions (client/src/main.c).

```

## How to compile
All settings above should enable to clone and free run this code on your devices.

## Future improvements
```
Make a two-way communication.
When one of the players make undefined move make him lose.
Settings [...] graph (graph below) can be accelerated.
```


### Settings and game graph
![Client States](https://i.imgur.com/LdbJaib.png)

## Attributions

```
Server code is copied from: http://androidsrc.net/android-client-server-using-sockets-server-implementation/ and modified to meet our requirements.
```

## License

```
MIT
```

The project was conducted during the Microprocessor Lab course held by the Institute of Control and Information Engineering, Poznan University of Technology.
Supervisor: Tomasz Mańkowski
