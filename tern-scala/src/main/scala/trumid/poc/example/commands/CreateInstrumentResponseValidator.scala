// Generated at Sat Jul 01 15:12:09 BST 2023 (StructValidator)
package trumid.poc.example.commands

import trumid.poc.cluster.ResultCode

object CreateInstrumentResponseValidator {

   def validate(createInstrumentResponse: CreateInstrumentResponse): ResultCode = {
      if(createInstrumentResponse.reason().isDefined && createInstrumentResponse.reason().get.length() == 0) {
         return ResultCode.fail("Invalid value for 'reason'") // ValidateNotBlank
      }
      ResultCode.OK
   }
}
