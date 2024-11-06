import chisel3._
import chisel3.util._
import firrtl.FirrtlProtos.Firrtl.Expression.Mux

class CPUTop extends Module {
  val io = IO(new Bundle {
    val done = Output(Bool ())
    val run = Input(Bool ())
    val jump = Input(Bool())

    //This signals are used by the tester for loading and dumping the memory content, do not touch
    val testerDataMemEnable = Input(Bool ())
    val testerDataMemAddress = Input(UInt (16.W))
    val testerDataMemDataRead = Output(UInt (32.W))
    val testerDataMemWriteEnable = Input(Bool ())
    val testerDataMemDataWrite = Input(UInt (32.W))
    //This signals are used by the tester for loading and dumping the memory content, do not touch
    val testerProgMemEnable = Input(Bool ())
    val testerProgMemAddress = Input(UInt (16.W))
    val testerProgMemDataRead = Output(UInt (32.W))
    val testerProgMemWriteEnable = Input(Bool ())
    val testerProgMemDataWrite = Input(UInt (32.W))

  })

  //Creating components
  val programCounter = Module(new ProgramCounter())
  val dataMemory = Module(new DataMemory())
  val programMemory = Module(new ProgramMemory())
  val registerFile = Module(new RegisterFile())
  val controlUnit = Module(new ControlUnit())
  val alu = Module(new ALU())
  val muxImmediate = Mux(controlUnit.io.memRead, registerFile.io.output2, registerFile.io.output1)
  val muxWriteReg = Mux(controlUnit.io.regWrite, alu.io.resultint,dataMemory.io.dataRead)
  val muxJumpSelector = Mux(controlUnit.io.jump, alu.io.resultbool, controlUnit.io.memRead)
  val muxJump = Mux(muxJumpSelector, programMemory.io.address, programCounter.io.programCounter)




  //Connecting the modules

  programCounter.io.run := io.run
  programMemory.io.address := programCounter.io.programCounter
  alu.io.aluOp := controlUnit.io.aluOp
  programCounter.io.stop := controlUnit.io.stop
  registerFile.io.readAdress1 := programMemory.io.instructionRead
  registerFile.io.readAdress2 := programMemory.io.instructionRead
  registerFile.io.writeSel := programMemory.io.instructionRead

  muxImmediate := programMemory.io.address
  muxWriteReg := 


  ////////////////////////////////////////////
  //Continue here with your connections
  ////////////////////////////////////////////

  //This signals are used by the tester for loading the program to the program memory, do not touch
  programMemory.io.testerAddress := io.testerProgMemAddress
  io.testerProgMemDataRead := programMemory.io.testerDataRead
  programMemory.io.testerDataWrite := io.testerProgMemDataWrite
  programMemory.io.testerEnable := io.testerProgMemEnable
  programMemory.io.testerWriteEnable := io.testerProgMemWriteEnable
  //This signals are used by the tester for loading and dumping the data memory content, do not touch
  dataMemory.io.testerAddress := io.testerDataMemAddress
  io.testerDataMemDataRead := dataMemory.io.testerDataRead
  dataMemory.io.testerDataWrite := io.testerDataMemDataWrite
  dataMemory.io.testerEnable := io.testerDataMemEnable
  dataMemory.io.testerWriteEnable := io.testerDataMemWriteEnable
}