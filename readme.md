# GW2 BLTC Market Analysis

![index](assets/index_item_id=86269.png) 

This [tool](http://bltc.jiahe.dev/?item_id=86269) provides at a glace information about items traded on the BLTC, including
- Item Info
    - Icon
    - Name
    - Chat link
    - Type
    - Description
- Market Info
    - Highest buy price
    - Lowest sell price
    - Bid-Ask spread
    - Total supply
    - Total demand
- Listings
    - Graphical representation of 75% of buy and sell quantity
    - Tabular representaion of all listings at their listed unit price
    - Time series data for buy and sell price and quanity

# CI/CD
The server hosting the DB and webpage is an AWS Lightsail instance. Systemd is used to manage the scraper and webserver services, with a timer used to scrape the [GW2 API](https://wiki.guildwars2.com/wiki/API:Main) every 6 hours at 0000, 0600, 1200 and 1800 hours UTC.

A GitHub actions workflow is setup to build the jar with Gradle and upload the artifacts to the server.

# Version 
- 0.2 New app entry point and webserver
- 0.1 Scraper used as app entry point, program to be run periodically by systemd
