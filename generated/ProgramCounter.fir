;buildInfoPackage: chisel3, version: 3.3.2, scalaVersion: 2.12.11, sbtVersion: 1.3.10
circuit ProgramCounter : 
  module ProgramCounter : 
    input clock : Clock
    input reset : UInt<1>
    output io : {flip stop : UInt<1>, flip jump : UInt<1>, flip run : UInt<1>, flip programCounterJump : UInt<16>, programCounter : UInt<16>}
    
    reg pCounter : UInt<16>, clock with : (reset => (reset, UInt<16>("h00"))) @[ProgramCounter.scala 12:25]
    when io.run : @[ProgramCounter.scala 14:3]
      when io.stop : @[ProgramCounter.scala 15:20]
        pCounter <= pCounter @[ProgramCounter.scala 16:16]
        skip @[ProgramCounter.scala 15:20]
      else : @[ProgramCounter.scala 19:24]
        when io.jump : @[ProgramCounter.scala 19:24]
          pCounter <= io.programCounterJump @[ProgramCounter.scala 20:16]
          skip @[ProgramCounter.scala 19:24]
        else : @[ProgramCounter.scala 22:16]
          node _T = add(pCounter, UInt<1>("h01")) @[ProgramCounter.scala 23:28]
          node _T_1 = tail(_T, 1) @[ProgramCounter.scala 23:28]
          pCounter <= _T_1 @[ProgramCounter.scala 23:16]
          skip @[ProgramCounter.scala 22:16]
      skip @[ProgramCounter.scala 14:3]
    io.programCounter <= pCounter @[ProgramCounter.scala 26:21]
    
