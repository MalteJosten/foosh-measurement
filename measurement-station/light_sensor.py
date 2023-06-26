# Inspired by peppe80 "How to use a photoresistor with Raspberry Pi Pico", https://peppe8o.com/how-to-use-a-photoresistor-with-raspberry-pi-pico/
# and "Connecting to the Internet with Raspberry Pi Pico W', https://datasheets.raspberrypi.com/picow/connecting-to-the-internet-with-pico-w.pdf
from machine import ADC, Pin, reset
from time import sleep
from math import pow
from urequests import post
from ujson import dumps
from network import WLAN, STA_IF
import secrets


# Number of probes which are used to return an averaged value.
PROBES = 10
# Number of seconds to wait before starting with the next measurement cycle.
INTERVAL = 15
# URL to which the POST-Request is sent to.
REQUEST_URL = "http://192.168.108.65:3000/add"
# Number of seconds to wait for the network connection to be established.
CON_TIMEOUT = 10


class LightSensor():
    def __init__(self, pin):
        self.adc = ADC(Pin(pin))
        self.has_connection = False

    def connect(self):
        try:
            wlan = WLAN(STA_IF)
            wlan.active(True)

            # Check if device is already connected to a network...
            if not(wlan.isconnected()):
                # ...if not, try connect.
                wlan.connect(secrets.SSID, secrets.PWD)

                # Wait for the connection to be established.
                timeout = CON_TIMEOUT
                while timeout > 0:
                    # Something went wrong...
                    if (wlan.status() < 0 or wlan.status() > 3):
                        break

                    timeout -= 1
                    sleep(1)

                # wlan.status = 3 indicates success.
                if (wlan.status != 3):
                    print("Could not connect to network.\n Resetting...")
                    sleep(1)
                    reset()

            self.has_connection = True
        except Exception as e:
            print("Network Error:", e)
            sleep(2)
            reset()

    def send(self, value):
        try:
            post_data = dumps({'value': value})
            res = post(
                    REQUEST_URL,
                    headers={'content-type': 'application/json'},
                    data=post_data)
            res.close()
        except Exception as e:
            print("HTTP Error:", e)
            sleep(2)
            reset()

    def read_light(self):
        light = self.adc.read_u16()
        light = self.map_range(light, 3900, 65535, 0, 1)
        light = 100 * pow(light, 10)

        return light

    def map_range(self, x, in_min, in_max, out_min, out_max):
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min


def average(list):
    sum = 0
    for x in list:
        sum += x

    avg = sum / len(list)

    return avg


ls = LightSensor(28)
ls.connect()

# Wait for the connection.
while not(ls.has_connection):
    sleep(0.1)

# Start measuremnt loop.
while True:
    readings = []
    for i in range(0, PROBES):
        readings.append(ls.read_light())
        sleep(1)

    ls.send(average(readings))
    sleep(INTERVAL)
