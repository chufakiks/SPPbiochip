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

  //initializing output
  io.done := false.B



  //Creating components
  val programCounter = Module(new ProgramCounter())
  val dataMemory = Module(new DataMemory())
  val programMemory = Module(new ProgramMemory())
  val registerFile = Module(new RegisterFile())
  val controlUnit = Module(new ControlUnit())
  val alu = Module(new ALU())


  // spliting up the instruction
  val instruction = programMemory.io.instructionRead

  programCounter.io.stop := false.B // Default assignment for stop

  //muxes

  //mux inbetween registerfile and alu
  val immediate = instruction(17, 8)
  val muxregalu = Mux(controlUnit.io.aluSrc, immediate, registerFile.io.readAdress2)



  //Connecting the modules
  // Program counter update logic with conditional jump
  /*
  val jumpCondition = (controlUnit.io.jeq && alu.io.resultbool) || (controlUnit.io.jlt && alu.io.resultbool)
  val finalJump = controlUnit.io.jump && jumpCondition
  val nextPC = Mux(finalJump, alu.io.resultint, programCounter.io.programCounter + 1.U)
  programCounter.io.programCounterJump := nextPC

   */

  //programcounter
  programCounter.io.run := io.run
  programCounter.io.stop := controlUnit.io.stop
  programCounter.io.jump := alu.io.resultbool
  programCounter.io.programCounterJump := instruction(17-11)

  //programmemory
  programMemory.io.address := programCounter.io.programCounter

  //registerfile
  registerFile.io.writeData := Mux(controlUnit.io.memRead, dataMemory.io.dataRead, alu.io.resultint)
  registerFile.io.readAdress1 := instruction(22,18)
  registerFile.io.readAdress2 := Mux(controlUnit.io.regWrite, instruction(17,12), instruction(27,23))
  registerFile.io.writeSel := instruction(27,23)
  registerFile.io.writeEnable := controlUnit.io.regWrite

  //alu
  alu.io.input1 := registerFile.io.readAdress1
  alu.io.input2 := muxregalu
  alu.io.aluOp := controlUnit.io.aluOp

  //control unit
  controlUnit.io.opcode := instruction(31,28)


  //data memory
  dataMemory.io.address := alu.io.resultint
  dataMemory.io.dataWrite := registerFile.io.readAdress2
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