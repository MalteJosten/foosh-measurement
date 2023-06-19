# Taken from peppe80 "How to use a photoresistor with Raspberry Pi Pico", https://peppe8o.com/how-to-use-a-photoresistor-with-raspberry-pi-pico/
from machine import ADC, Pin
from time import sleep

class LightSensor():
    def __init__(self, pin):
        self.adc = ADC(Pin(pin))

    def readLight(self):
        light = self.adc.read_u16()
        # light = round(light / 65535 * 100, 2)

        return light

ls = LightSensor(28)

while True:
    # print("Light: {} %".format(ls.readLight()))
    print("Light: {}".format(ls.readLight()))
    sleep(1)
