var urlParams = new URLSearchParams(window.location.search);
const item_id = urlParams.has('item_id') ? urlParams.get('item_id') : 24;

document.getElementById('chat_link').onclick = function() {
      this.select();
      this.setSelectionRange(0, 99999); /* For mobile devices */

      navigator.clipboard.writeText(this.value);
};

function formatPrice(price) {
    if (price < 100) {
        return `${price}c`
    }
    if (price < 10000) {
        return `${Math.floor(price/100)}s${price%100}c`
    }
    return `${Math.floor(price/10000)}g${Math.floor(price/100)%100}s${price%100}c`
}

async function retrieveItemDetails() {
    const response = await fetch(`https://api.guildwars2.com/v2/items?id=${item_id}`);
    const details = await response.json();
    document.getElementById('thumbnail').src = details.icon;
    document.getElementById('name').innerText = details.name;
    document.getElementById('chat_link').value = details.chat_link;
    document.getElementById('chat_link').setAttribute("size", document.getElementById('chat_link').value.length);
    document.getElementById('type').innerText = details.type.replace(/([A-Z])/g, ' $1').replace(/^./, function(str){ return str.toUpperCase(); });
    document.getElementById('description').innerText = details.description;
}

async function plot() {
    const response = await fetch(`https://api.guildwars2.com/v2/commerce/listings?ids=${item_id}`);
    const listings = await response.json();
    
    var sell = 0;
    var buy = 0;
    var supply = 0;
    var demand = 0;
    var sells_x = [];
    var sells_y = [];
    var buys_x = [];
    var buys_y = [];
    var sells_columns = [[], [], []];
    var buys_columns = [[], [], []];

    sell = listings[0].sells[0].unit_price;
    buy = listings[0].buys[0].unit_price;
    document.getElementById('buy').innerText = formatPrice(buy);
    document.getElementById('sell').innerText = formatPrice(sell);
    document.getElementById('spread').innerText = formatPrice(sell-buy);

    for (i=0; i < listings[0].sells.length; i++){
        e = listings[0].sells[i];
        supply += e.quantity;
        // sells_x.push(e.quantity);
        // sells_y.push(e.unit_price);
        sells_columns[0].push(e.quantity);
        sells_columns[1].push(e.listings);
        sells_columns[2].push(formatPrice(e.unit_price));
    }
    for (i=0; i < listings[0].buys.length; i++){
        e = listings[0].buys[i];
        demand += e.quantity;
        // buys_x.push(e.quantity);
        // buys_y.push(e.unit_price);
        buys_columns[0].push(e.quantity);
        buys_columns[1].push(e.listings);
        buys_columns[2].push(formatPrice(e.unit_price));
    }

    document.getElementById('supply').innerText = supply;
    document.getElementById('demand').innerText = demand;

    var cumulative_supply = 0;
    var cumulative_demand = 0;
    const cutoff_percent = 0.75;
    var price_cutoffs = [0, 0];
    var last_price = listings[0].sells[0].unit_price;
    for (i=0; i < listings[0].sells.length; i++) {
        e = listings[0].sells[i];
        cumulative_supply += e.quantity;
        for (price = e.unit_price; price > last_price; price--) {
            sells_x.push(cumulative_supply);
            sells_y.push(price);
        }
        last_price = e.unit_price;
        if (cumulative_supply / supply >= cutoff_percent) {
            price_cutoffs[1] = e.unit_price;
            break;
        }
    }
    var last_price = listings[0].buys[0].unit_price;
    for (i=0; i < listings[0].buys.length; i++){
        e = listings[0].buys[i];
        cumulative_demand += e.quantity;
        for (price = e.unit_price; price < last_price; price++) {
            buys_x.push(cumulative_demand);
            buys_y.push(price);
        }
        last_price = e.unit_price;
        if (cumulative_demand / demand >= cutoff_percent) {
            price_cutoffs[0] = e.unit_price;
            break;
        }
    }

    depth_chart = document.getElementById('depth_chart');
    var data = [{
            x: sells_x,
            y: sells_y,
            type: "bar",
            orientation: "h",
            opacity: 0.5,
            marker: {
            color: 'red',
        }},
        {
            x: buys_x,
            y: buys_y,
            type: "bar",
            orientation: "h",
            opacity: 0.5,
            marker: {
            color: 'green',
        },
    }];
    var layout = {
        title: 'Depth Chart',
        barmode: "overlay",
        bargap: 0,
        yaxis: {
            range: price_cutoffs
        },
        showlegend: false,
        margin: {
            l: 50,
            r: 0,
            t: 40,
            b: 20
        }
    };
    Plotly.newPlot(depth_chart, data, layout);
    
    sells_table = document.getElementById('sells');
    Plotly.newPlot(sells_table,
        [{
            type: 'table',
            header: {
                values: [['Quantity'], ['Sellers'], ['Price']],
            },
            cells: {
                values: sells_columns,
            }
        }],
        {
            title: 'Sells',
            margin: {
                l: 0,
                r: 20,
                t: 40,
                b: 20
            }
        }
    );
    buys_table = document.getElementById('buys');
    Plotly.newPlot(buys_table, 
        [{
            type: 'table',
            header: {
                values: [['Quantity'], ['Buyers'], ['Price']],
            },
            cells: {
                values: buys_columns,
            }
        }],
        {
            title: 'Buys',
            margin: {
                l: 0,
                r: 20,
                t: 40,
                b: 20
            }
        }
    );
}

function plotTimeSeriesData() {
    const priceTimeSeriesPlot = document.getElementById("price_time_series");
    const ordersTimeSeriesPlot = document.getElementById("orders_time_series");
    const n = observations.length;
    var buyPrice = new Array(n);
    var sellPrice = new Array(n);
    var buyOrders = new Array(n);
    var sellOrders = new Array(n);
    var timestamps = new Array(n);

    for (var i=0; i<n; i++) {
        const observation = observations[i];

        buyPrice[i] = observation.buyPrice;
        sellPrice[i] = observation.sellPrice;
        buyOrders[i] = observation.buyOrders;
        sellOrders[i] = observation.sellOrders;
        timestamps[i] = observation.timestamp;
    }

    const priceData = [
        {
            x: timestamps,
            y: buyPrice,
            type: 'scatter',
            name: 'Buy Price',
            line: {
                color: 'green'
            }
        },
        {
            x: timestamps,
            y: sellPrice,
            type: 'scatter',
            name: 'Sell Price',
            line: {
                color: 'red'
            }
        }
    ];
    const ordersData = [
        {
            x: timestamps,
            y: buyOrders,
            type: 'scatter',
            name: 'Buy Orders',
            line: {
                color: 'green'
            }
        },
        {
            x: timestamps,
            y: sellOrders,
            type: 'scatter',
            name: 'Sell Orders',
            line: {
                color: 'red'
            }
        },
    ];

    const priceTimeSeriesPlotLayout = {
        title: 'Price'
    };
    const ordersTimeSeriesPlotLayout = {
        title: 'Order Quantity'
    };

    Plotly.newPlot(priceTimeSeriesPlot, priceData, priceTimeSeriesPlotLayout);
    Plotly.newPlot(ordersTimeSeriesPlot, ordersData, ordersTimeSeriesPlotLayout);
}

retrieveItemDetails();
plot();
plotTimeSeriesData();
