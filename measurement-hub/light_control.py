import requests
import time
from datetime import datetime
import random

# Number of seconds between switching on/off lamp
INTERVAL = 120


class LightControl:
    api_point = "localhost:8080"
    lamp      = "plug1"
    other     = "plug2"

    def turnOnOff(self, to, item):
        state = "OFF"
        if to:
            state = "ON"

        res = requests.post(
                "http://" + self.api_point + "/rest/items/" + item,
                headers = {
                    'Content-Type': 'text/plain',
                    'Accept': 'application/json'
                    },
                data = state)

        with open("log.txt", "a") as log_file:
            log_file.write("{}: Turned light {} {}\n".format(datetime.now(), item, state))

    def chooseBehavior(self, item):
        choice = random.randint(0, 1)

        self.turnOnOff((choice == 1), item)


lc = LightControl()
lc.turnOnOff(False)

while (True):
    time.sleep(INTERVAL)
    lc.chooseBehavior(lamp)
    lc.chooseBehavior(other)
