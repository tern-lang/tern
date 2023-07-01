var state = {}
var rows = 20

subscribe()

function subscribe() {
   var ws = new WebSocket("ws://localhost:4444/");

   ws.onopen = function() {
      console.log("Connected...")
   };

   ws.onmessage = function (evt) {
      var update = JSON.parse(evt.data)
      var element = document.getElementById(update.id)

      if(!element) {
        createTable(update.id)
      }
      updateTable(update)
   };

   ws.onclose = function() {
     setTimeout(subscribe, 2000)
   };
}

function createTable(id) {
  var body = document.body
  var newTable = document.createElement('table');

  newTable.setAttribute("id", "" + id)
  newTable.style.width = '100px';
  newTable.style.border = '1px solid black';

  createHeading(newTable)

  for (i = 0; i < rows; i++) {
    createRow(newTable, id, i)
  }
  var parent = document.getElementById("panel-" + id)
  parent.appendChild(newTable);
}

function createRow(newTable, instrument, index) {
    var newRow = newTable.insertRow();
    var ids = [
        "bid-" + instrument + "-" + index + "-qty",
        "bid-" + instrument + "-" + index + "-px",
        "offer-" + instrument + "-" + index + "-px",
        "offer-" + instrument + "-" + index + "-qty",
    ];
    var align = [
        "left",
        "left",
        "right",
        "right"
    ];

    for (j = 0; j < 4; j++) {
       var newCell = newRow.insertCell();

       newCell.setAttribute("id", ids[j])
       newCell.innerHTML = "";
       newCell.style.border = '1px solid black';
       newCell.style.height = '14px';
       newCell.style.textAlign = align[j]
    }
}

function createHeading(newTable) {
    var newRow = newTable.insertRow();
    var headings = ["Quantity", "Buy", "Sell", "Quantity"]
    var align = [
        "left",
        "left",
        "right",
        "right"
    ];

    for (j = 0; j < 4; j++) {
       var newCell = newRow.insertCell();

       newCell.innerHTML = headings[j];
       newCell.style.border = '1px solid black';
       newCell.style.textAlign = align[j]
    }
}

function updateTable(update) {
    var element = document.getElementById(update.id)

    if(element) {
        var orderBook = state[update.id] || {id: update.id, bids: {}, offers: {}}

        for(i = 0; i < update.bids.length; i++) {
            var newBid = update.bids[i]
            var oldBid = orderBook.bids[newBid.px] || {...newBid, qty: 0}

            if(newBid.quantity == 0) {
               delete orderBook.bids[newBid.px]
            } else {
               var className = "normal"

               if(newBid.qty > oldBid.qty) {
                  className = oldBid.className == "flashUp1" ? "flashUp2" : "flashUp1"
               } else if(newBid.qty < oldBid.qty) {
                  className = oldBid.className == "flashDown1" ? "flashDown2" : "flashDown1"
               } else {
                  className = "normal"
               }
               orderBook.bids[newBid.px] =  {
                 ...newBid,
                 className: className
               }
            }
        }
        for(i = 0; i < update.offers.length; i++) {
            var newOffer = update.offers[i]
            var oldOffer = orderBook.offers[newOffer.px] || {...newOffer, qty: 0}

            if(newOffer.qty == 0) {
               delete orderBook.offers[newOffer.px]
            } else {
               var className = "normal"

               if(newOffer.qty > oldOffer.qty) {
                  className = oldOffer.className == "flashUp1" ? "flashUp2" : "flashUp1"
               } else if(newOffer.qty < oldOffer.qty) {
                  className = oldOffer.className == "flashDown1" ? "flashDown2" : "flashDown1"
               } else {
                  className = "normal"
               }
               orderBook.offers[newOffer.px] = {
                 ...newOffer,
                 className: className
               }
            }
        }
        state[update.id] = orderBook
        console.log(JSON.stringify(update, null, 0))
        console.log(JSON.stringify(state[update.id], null, 2))
        updateOrders(update.id, orderBook)
    }
}

function updateOrders(id, orderBook) {
    var bids = Object.keys(orderBook.bids).reverse().map(px => ({
        px: px,
        qty: orderBook.bids[px].qty,
        className: orderBook.bids[px].className
     }))
    var offers = Object.keys(orderBook.offers).map(px => ({
        px: px,
        qty: orderBook.offers[px].qty,
        className: orderBook.offers[px].className
     }))

    for(i = 0; i < rows; i++) {
        var bid = bids[i] || {px: "", qty: ""}
        var bidPx = document.getElementById("bid-" + id + "-" + i + "-px")
        var bidQty = document.getElementById("bid-" + id + "-" + i + "-qty")

        bidPx.innerHTML = ""+ (bid.px / 100.0)
        bidQty.innerHTML = ""+ bid.qty
        bidQty.className = bid.className
    }
    for(i = 0; i < rows; i++) {
        var offer = offers[i] || {px: "", qty: ""}
        var offerPx = document.getElementById("offer-" + id + "-" + i + "-px")
        var offerQty = document.getElementById("offer-" + id + "-" + i + "-qty")

        offerPx.innerHTML = ""+ (offer.px / 100.0)
        offerQty.innerHTML = ""+ offer.qty
        offerQty.className = offer.className
    }
}

