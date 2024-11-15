import chisel3._
import chisel3.util._

class CPUTop extends Module {
  val io = IO(new Bundle {
    val done = Output(Bool ())
    val run = Input(Bool ())

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

    val instruction = Output(UInt (32.W))
    val counter = Output(UInt (16.W))
  })

  //initializing output
  io.done := false.B



  //Creating components
  val programCounter = Module(new ProgramCounter())
  val dataMemory = Module(new DataMemory())
  val programMemory = Module(new ProgramMemory())
  val registerFile = Module(new RegisterFile())
  val controlUnit = Module(new ControlUnit())
  val alu = Module(new ALU())

  var instruction =  programMemory.io.instructionRead

  // spliting up the instruction
  io.instruction := programMemory.io.instructionRead
  //control unit
  controlUnit.io.opcode := instruction(31,28)
  io.done := controlUnit.io.stop

  // test
  //io.instruction := instruction
  io.counter := programCounter.io.programCounter


  //programcounter
  programCounter.io.run := io.run
  programCounter.io.stop := controlUnit.io.stop
  programCounter.io.programCounterJump := instruction(19,13)
  programCounter.io.jump := alu.io.resultbool

  //programmemory
  programMemory.io.address := programCounter.io.programCounter

  //registerfile
  registerFile.io.writeData := Mux(controlUnit.io.memRead, dataMemory.io.dataRead, alu.io.resultint)
  registerFile.io.readAdress1 := instruction(23,20)
  registerFile.io.readAdress2 := Mux(controlUnit.io.regWrite, instruction(19,13), instruction(27,24))
  registerFile.io.writeSel := instruction(27,24)
  registerFile.io.writeEnable := controlUnit.io.regWrite

  //alu
  alu.io.input1 := registerFile.io.output1
  alu.io.input2 := Mux(controlUnit.io.aluSrc, instruction(19, 8), registerFile.io.readAdress2)
  alu.io.aluOp := controlUnit.io.aluOp

  //data memory
  dataMemory.io.address := alu.io.resultint
  dataMemory.io.dataWrite := registerFile.io.output2
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