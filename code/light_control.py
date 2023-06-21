import requests
import time
import random


class LightControl:
    api_point = "192.168.108.103:8080"
    api_path  = "Shelly_PlugS_Power"

    def turnOnOff(self, to):
        state = "OFF"
        if to:
            state = "ON"

        res = requests.post(
                "http://" + self.api_point + "/rest/items/" + self.api_path,
                headers = {
                    'Content-Type': 'text/plain',
                    'Accept': 'application/json'
                    },
                data = state)

        print(res)

        print("Turned light {}".format(state))

    def chooseBehavior(self):
        choice = random.randint(0, 1)

        self.turnOnOff((choice == 1))


lc = LightControl()
lc.turnOnOff(False)

while (True):
    time.sleep(5)
    lc.chooseBehavior()
