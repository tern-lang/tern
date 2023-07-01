// Generated at Sat Jul 01 13:00:12 BST 2023 (StructValidator)
package trumid.poc.example.events

import trumid.poc.example.events._
import trumid.poc.cluster.ResultCode

object OrderBookUpdateEventValidator {

   def validate(orderBookUpdateEvent: OrderBookUpdateEvent): ResultCode = {
      if(!orderBookUpdateEvent.bids().validate().success()) {
         return orderBookUpdateEvent.bids().validate()
      }
      if(!orderBookUpdateEvent.offers().validate().success()) {
         return orderBookUpdateEvent.offers().validate()
      }
      ResultCode.OK
   }
}
