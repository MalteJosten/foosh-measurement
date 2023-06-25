# Taken from peppe80 "How to use a photoresistor with Raspberry Pi Pico", https://peppe8o.com/how-to-use-a-photoresistor-with-raspberry-pi-pico/
from machine import ADC, Pin
from time import sleep
from math import pow
import urequests
import ujson
import network
import secrets


class LightSensor():
    def __init__(self, pin):
        self.adc = ADC(Pin(pin))
        self.has_connection = False

    def connect(self):
        try:
            wlan = network.WLAN(network.STA_IF)
            wlan.active(True)

            if not(wlan.isconnected()):
                wlan.connect(secrets.SSID, secrets.PWD)

                while not(wlan.isconnected()):
                    sleep(1)

            self.has_connection = True
        except Exception as e:
            print("Network Error:", e)
            sleep(2)
            machine.reset()

    def send(self, value):
        try:
            request_url = "http://192.168.108.65:3000/add"
            post_data = ujson.dumps({'value': value})
            res = urequests.post(request_url, headers={'content-type': 'application/json'}, data=post_data)
            res.close()
        except Exception as e:
            print("HTTP Error:", e)
            sleep(2)
            machine.reset()

    def read_light(self):
        light = self.adc.read_u16()
        light = self.map_range(light, 3900, 65535, 0, 1)
        light = 100 * pow(light, 10)

        return light

    def map_range(self, x, in_min, in_max, out_min, out_max):
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min


ls = LightSensor(28)
ls.connect()

while not(ls.has_connection):
    sleep(0.1)

while True:
    ls.send(ls.read_light())
    sleep(2)
