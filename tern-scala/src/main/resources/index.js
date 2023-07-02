const state = {}
const rows = 20

subscribe()
refresh()


function refresh() {
//    setInterval(() => {
//        const ids = Object.keys(state)
//
//        for(let i = 0; i < ids.length; i++) {
//            const id = ids[i]
//            updateOrders(id, state[id])
//        }
//    }, 200)
}

function subscribe() {
   const ws = new WebSocket("ws://localhost:4444/");

   ws.onopen = function() {
      console.log("Connected...")
   };

   ws.onmessage = function (evt) {
      let update = JSON.parse(evt.data)
      let element = document.getElementById(update.id)

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
  const body = document.body
  const parent = document.getElementById("panel-" + id)
  const newTable = document.createElement('table');

  newTable.setAttribute("id", "" + id)
  newTable.style.width = '100px';
  newTable.style.border = '1px solid #555555';
  newTable.cellSpacing  = 0
  newTable.cellPadding  = 2

  createHeading(newTable)

  for (let i = 0; i < rows; i++) {
    createRow(newTable, id, i)
  }
  parent.appendChild(newTable);
}

function createRow(newTable, instrument, index) {
    const newRow = newTable.insertRow();
    const ids = [
        "bid-" + instrument + "-" + index + "-qty",
        "bid-" + instrument + "-" + index + "-px",
        "offer-" + instrument + "-" + index + "-px",
        "offer-" + instrument + "-" + index + "-qty",
    ];
    const align = [
        "left",
        "left",
        "right",
        "right"
    ];

    for (let j = 0; j < 4; j++) {
       const newCell = newRow.insertCell();

       newCell.setAttribute("id", ids[j])
       newCell.innerHTML = "";
       newCell.style.border = '1px solid #555555';
       newCell.style.height = '14px';
       newCell.style.textAlign = align[j]
    }
}

function createHeading(newTable) {
    const newRow = newTable.insertRow();
    const headings = ["Quantity", "Buy", "Sell", "Quantity"]
    const align = [
        "left",
        "left",
        "right",
        "right"
    ];

    for (let j = 0; j < 4; j++) {
       const newCell = newRow.insertCell();

       newCell.innerHTML = headings[j];
       newCell.style.border = '1px solid #555555';
       newCell.style.fontWeight = 'bold';
       newCell.style.textAlign = align[j]
    }
}

function updateTable(update) {
    const element = document.getElementById(update.id)

    if(element) {
        const orderBook = state[update.id] || {id: update.id, bids: {}, offers: {}}

        for(let i = 0; i < update.bids.length; i++) {
            const newBid = update.bids[i]
            const oldBid = orderBook.bids[newBid.px] || {...newBid, qty: 0}

            if(newBid.qty == 0) {
               delete orderBook.bids[newBid.px]
            } else {
               let className = oldBid.className

               if(newBid.qty > oldBid.qty) {
                  className = oldBid.className == "flashUp1" ? "flashUp2" : "flashUp1"
               } else if(newBid.qty < oldBid.qty) {
                  className = oldBid.className == "flashDown1" ? "flashDown2" : "flashDown1"
               }
               orderBook.bids[newBid.px] =  {
                 ...newBid,
                 className: className
               }
            }
        }
        for(let i = 0; i < update.offers.length; i++) {
            const newOffer = update.offers[i]
            const oldOffer = orderBook.offers[newOffer.px] || {...newOffer, qty: 0}

            if(newOffer.qty == 0) {
               delete orderBook.offers[newOffer.px]
            } else {
               let className = oldOffer.className

               if(newOffer.qty > oldOffer.qty) {
                  className = oldOffer.className == "flashUp1" ? "flashUp2" : "flashUp1"
               } else if(newOffer.qty < oldOffer.qty) {
                  className = oldOffer.className == "flashDown1" ? "flashDown2" : "flashDown1"
               }
               orderBook.offers[newOffer.px] = {
                 ...newOffer,
                 className: className
               }
            }
        }
        state[update.id] = orderBook
        updateOrders(update.id, update.version, orderBook)
    }
}

function updateOrders(id, version, orderBook) {
    const bids = Object.keys(orderBook.bids).reverse().map(px => ({
        px: px,
        qty: orderBook.bids[px].qty,
        className: orderBook.bids[px].className
     }))
    const offers = Object.keys(orderBook.offers).map(px => ({
        px: px,
        qty: orderBook.offers[px].qty,
        className: orderBook.offers[px].className
     }))

    for(let i = 0; i < rows; i++) {
        const bid = bids[i] || {px: "", qty: ""}
        const bidPx = document.getElementById("bid-" + id + "-" + i + "-px")
        const bidQty = document.getElementById("bid-" + id + "-" + i + "-qty")

        bidPx.innerHTML = ""+ (bid.px / 100.0)
        bidQty.innerHTML = ""+ bid.qty
        bidQty.className = bid.className
    }
    for(let i = 0; i < rows; i++) {
        const offer = offers[i] || {px: "", qty: ""}
        const offerPx = document.getElementById("offer-" + id + "-" + i + "-px")
        const offerQty = document.getElementById("offer-" + id + "-" + i + "-qty")

        offerPx.innerHTML = ""+ (offer.px / 100.0)
        offerQty.innerHTML = ""+ offer.qty
        offerQty.className = offer.className
    }
    document.getElementById("version").innerHTML = "" + version
}

