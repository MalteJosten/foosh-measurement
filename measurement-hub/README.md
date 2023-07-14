# Measurement Hub
## Hardware
- [Raspberry Pi 4B](https://www.raspberrypi.com/products/raspberry-pi-4-model-b/)
- [Shelly Smart Plug](https://www.shelly.cloud/en/products/shop/shelly-plus-plug-s)
- Lamp

## Prerequisities
Initially, some kind of smart home and/or smart home API needs to be set up, including the smart devices.
The project is build on top of Node.js with Express.js.<br>
You therefore need to install Node.js and npm. ([Installation documentation](https://docs.npmjs.com/downloading-and-installing-node-js-and-npm))

## Setup
In this example, I've installed and set up an [openHAB](https://www.openhab.org/) smart home (API) with one Shelly Smart Plug.
The Lamp was plugged into the Smart Plug, so it could be controlled via the hub.

To start the hub, you need to
1. Run the python script which randomly turns the Smart Plug, i.e. the lamp, on and off. You may want to adapt the time interval to fit your needs.
2. Run the Node.js project by calling `node app.js`. Make sure to install all dependencies with `npm i` before starting the server for the first time.

## Configuration
The Node.js project uses an .env file to manage sensitve and configuration data.
You therefore need to provide an .env file containing the following variables:
- `uri`: The URL to your mongoDB collection to be able to connect and write to your database.
- `db_name`: The name of the mongoDB database/collection where the measurements should be stored in.
- `port`: The port which should be used by your Node.js server.