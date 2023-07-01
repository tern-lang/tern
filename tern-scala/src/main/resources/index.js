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
    createRow(newTable, i)
  }
  body.appendChild(newTable);
}

function createRow(newTable, index) {
    var newRow = newTable.insertRow();
    var ids = [
        "bid-" + index + "-qty",
        "bid-" + index + "-px",
        "offer-" + index + "-px",
        "offer-" + index + "-qty",
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
    var headings = ["Quantity", "Bid", "Offer", "Quantity"]
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
    var orderBook = state[update.id] || {id: update.id, bids: {}, offers: {}}

    for(i = 0; i < update.bids.length; i++) {
        var newBid = update.bids[i]

        if(newBid.quantity == 0) {
           delete orderBook.bids[newBid.px]
        } else {
           orderBook.bids[newBid.px] =  newBid
        }
    }
    for(i = 0; i < update.offers.length; i++) {
        var newOffer = update.offers[i]

        if(newOffer.qty == 0) {
           delete orderBook.offers[newOffer.px]
        } else {
           orderBook.offers[newOffer.px] = newOffer
        }
    }

    var bids = Object.keys(orderBook.bids).reverse().map(px => ({px: px, qty: orderBook.bids[px].qty}))
    var offers = Object.keys(orderBook.offers).map(px => ({px: px, qty: orderBook.offers[px].qty}))

    state[update.id] = {id: update.id, bids: bids, offers: offers}
    console.log(JSON.stringify(state[update.id], null, 2))
    updateOrders(update.id, state[update.id])
}

function updateOrders(id, orderBook) {
    for(i = 0; i < rows; i++) {
        var bid = orderBook.bids[i] || {px: "", qty: ""}
        var bidPx = document.getElementById("bid-" + i + "-px")
        var bidQty = document.getElementById("bid-" + i + "-qty")

        bidPx.innerHTML = ""+ (bid.px / 100.0)
        bidQty.innerHTML = ""+ bid.qty
    }
    for(i = 0; i < rows; i++) {
        var offer = orderBook.offers[i] || {px: "", qty: ""}
        var offerPx = document.getElementById("offer-" + i + "-px")
        var offerQty = document.getElementById("offer-" + i + "-qty")

        offerPx.innerHTML = ""+ (offer.px / 100.0)
        offerQty.innerHTML = ""+ offer.qty
    }
}

