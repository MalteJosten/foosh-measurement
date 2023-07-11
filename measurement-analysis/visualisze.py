from pymongo.mongo_client import MongoClient
from pymongo.server_api import ServerApi
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
from creds import uri

sns.set(style="ticks")

def connect():
    client = MongoClient(uri, server_api=ServerApi("1"))
    db = client.brightness
    collection = db.run2

    raw = list(collection.find({}))
    
    data = {
        "Time": [],
        "Brightness": [],
        "State": []
    }

    for datapoint in raw:
        data["Time"].append(datapoint["timestamp"])
        data["Brightness"].append(datapoint["value"])
        data["State"].append(datapoint["items"][0]["state"])
    
    df = pd.DataFrame(data)

    print(df)
    
    scatter = sns.scatterplot(data=df, x="Time", y="Brightness", hue="State")
    plt.show()
    scatter.fig.savefig("out2.png")

connect()
