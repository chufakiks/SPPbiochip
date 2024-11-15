import chisel3._
import chisel3.util._

class ProgramCounter extends Module {
  val io = IO(new Bundle {
    val stop = Input(Bool())
    val jump = Input(Bool())
    val run = Input(Bool())
    val programCounterJump = Input(UInt(16.W))
    val programCounter = Output(UInt(16.W))
  })
  val programCounterReg = RegInit(0.U(16.W))

  when(io.run & !io.stop & !io.jump){
    programCounterReg := io.programCounter + 1.U
  }.elsewhen(io.run & !io.stop & io.jump){
      programCounterReg := io.programCounterJump
  }
    .otherwise {
      programCounterReg := io.programCounter + 1.U(1.W)
    }
  io.programCounter := programCounterReg
}