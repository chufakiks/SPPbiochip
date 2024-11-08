import chisel3._
import chisel3.util._

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
  /*
  val muxImmediate = Mux(controlUnit.io.memRead, registerFile.io.output2, registerFile.io.output1)
  val muxWriteReg = Mux(controlUnit.io.regWrite, alu.io.resultint,dataMemory.io.dataRead)
  val muxJumpSelector = Mux(controlUnit.io.jump, alu.io.resultbool, controlUnit.io.memRead)
  val muxJump = Mux(muxJumpSelector, programMemory.io.address, programCounter.io.programCounter)
  */
  

  // spliting up the instruction
  val instruction = programMemory.io.instructionRead

  //muxes

  //mux inbetween datamemory and register file
  val muxdatareg = Mux(controlUnit.io.memRead, dataMemory.io.dataRead, alu.io.resultint)

  //mux inbetween registerfile and alu
  //val muxregalu = Mux(sel er ikke implerenteret, instruction(17,8),registerFile.io.readAdress2)

  //mux for jump
  //val muxcontrolunit = Mux(alu.io.resultbool,controlUnit.io.jump,what to put here)

  //mux inbetween pc and adder
  val muxpcadd = Mux(muxcontrolunit, programCounter.io.programCounter + 1, instruction(17,11))

  //Connecting the modules

  //programcounter
  programCounter.io.run := io.run

  //programmemory
  programMemory.io.address := programCounter.io.programCounter

  //registerfile
  registerFile.io.writeData := muxdatareg
  registerFile.io.readAdress1 := instruction(22,18)
  registerFile.io.readAdress2 := instruction(17,12)
  registerFile.io.writeSel := instruction(27,23)
  registerFile.io.writeEnable := controlUnit.io.regWrite

  //alu
  alu.io.A := registerFile.io.readAdress1
  //alu.io.B := muxregalu
  alu.io.aluOp := controlUnit.io.aluOp

  //control unit
  controlUnit.io.opcode := instruction(31,28)

  //data memory
  dataMemory.io.address := controlUnit.io.aluOp
  dataMemory.io.dataWrite := registerFile.io.writeData
  dataMemory.io.writeEnable := controlUnit.io.memWrite

//////////////////////////////////////////////////////////////////

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