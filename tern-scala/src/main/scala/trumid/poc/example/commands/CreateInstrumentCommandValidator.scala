// Generated at Sat Jul 01 15:12:09 BST 2023 (StructValidator)
package trumid.poc.example.commands

import trumid.poc.cluster.ResultCode

object CreateInstrumentCommandValidator {

   def validate(createInstrumentCommand: CreateInstrumentCommand): ResultCode = {
      if(createInstrumentCommand.instrumentId() <= 0) {
         return ResultCode.fail("Invalid value for 'instrumentId'") // ValidatePositive
      }
      if(createInstrumentCommand.scale() < 0) {
         return ResultCode.fail("Invalid value for 'scale'") // ValidatePositiveOrZero
      }
      ResultCode.OK
   }
}
