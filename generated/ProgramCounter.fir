;buildInfoPackage: chisel3, version: 3.3.2, scalaVersion: 2.12.11, sbtVersion: 1.3.10
circuit ProgramCounter : 
  module ProgramCounter : 
    input clock : Clock
    input reset : UInt<1>
    output io : {flip stop : UInt<1>, flip jump : UInt<1>, flip run : UInt<1>, flip programCounterJump : UInt<16>, programCounter : UInt<16>}
    
    reg programCounterReg : UInt<16>, clock with : (reset => (reset, UInt<16>("h00"))) @[ProgramCounter.scala 13:34]
    io.programCounter <= programCounterReg @[ProgramCounter.scala 15:21]
    node _T = eq(io.stop, UInt<1>("h00")) @[ProgramCounter.scala 17:17]
    node _T_1 = and(io.run, _T) @[ProgramCounter.scala 17:15]
    node _T_2 = eq(io.jump, UInt<1>("h00")) @[ProgramCounter.scala 17:28]
    node _T_3 = and(_T_1, _T_2) @[ProgramCounter.scala 17:26]
    when _T_3 : @[ProgramCounter.scala 17:37]
      node _T_4 = add(io.programCounter, UInt<1>("h01")) @[ProgramCounter.scala 18:46]
      node _T_5 = tail(_T_4, 1) @[ProgramCounter.scala 18:46]
      programCounterReg <= _T_5 @[ProgramCounter.scala 18:25]
      skip @[ProgramCounter.scala 17:37]
    else : @[ProgramCounter.scala 20:41]
      node _T_6 = eq(io.stop, UInt<1>("h00")) @[ProgramCounter.scala 20:22]
      node _T_7 = and(io.run, _T_6) @[ProgramCounter.scala 20:20]
      node _T_8 = and(_T_7, io.jump) @[ProgramCounter.scala 20:31]
      when _T_8 : @[ProgramCounter.scala 20:41]
        programCounterReg <= io.programCounterJump @[ProgramCounter.scala 21:25]
        skip @[ProgramCounter.scala 20:41]
    
