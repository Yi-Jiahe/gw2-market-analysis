import requests
import random
import json
# import matplotlib.pyplot as plt

base_URL = "https://api.guildwars2.com"

def get_item_prices():
    url = base_URL + "/v2/commerce/prices/"
    r = requests.get(url)
    # with open("item_ids.txt", 'w') as f:
    #     f.write(r.content.decode("utf-8"))
    print("Data received")
    ids = [id.strip() for id in r.content.decode("utf-8").strip('[]').split(',')]
    print(f"{len(ids)} ids added")

    # id = random.choice(ids)
    id = '87434'

    url = base_URL + "/v1/item_details"
    r = requests.get(url, params={"item_id": id})
    item = json.loads(r.content.decode("utf-8"))
    print(item)

    # url = base_URL + "/v2/commerce/prices"
    # r = requests.get(url, params={"ids": id})
    # print(json.loads(r.content.decode("utf-8")))

    url = base_URL + "/v2/commerce/listings"
    r = requests.get(url, params={"ids": id})
    listings = json.loads(r.content.decode("utf-8"))[0]
    print(listings)
    buy_vols, buy_prices = list(), list()
    sell_vols, sell_prices = list(), list()
    for price in listings["buys"]:
        buy_vols.append(price["quantity"])
        buy_prices.append(price["unit_price"])
    for price in listings["sells"]:
        sell_vols.append(price["quantity"])
        sell_prices.append(price["unit_price"])
    # fig, ax = plt.subplots()
    # buy, = ax.plot(buy_vols, buy_prices)
    # buy.set_label("buy")
    # sell, = ax.plot(sell_vols, sell_prices)
    # sell.set_label("sell")
    # ax.set_xlabel("quantity")
    # ax.set_ylabel("price")
    # ax.set_title(item["name"])
    # plt.show()

if __name__ == "__main__":
    get_item_prices()