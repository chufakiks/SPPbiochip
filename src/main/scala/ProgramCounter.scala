import chisel3._
import chisel3.util._

class ProgramCounter extends Module {
  val io = IO(new Bundle {
    val stop = Input(Bool()) //behåll count världet
    val jump = Input(Bool()) //count världet ska en upp
    val run = Input(Bool())
    val programCounterJump = Input(UInt(16.W))
    val programCounter = Output(UInt(16.W))
  })
  val pCounter = RegInit(0.U(16.W))
  when (io.run)
  {
    when (io.stop) {
      pCounter := pCounter
      //io.run := io.run
    }.
    elsewhen (io.jump) {
      pCounter := io.programCounterJump
    }
    .otherwise {
      pCounter := pCounter + 1.U
    }
  }
  io.programCounter := pCounter
}
//pCounter := Mux(pCounter === 2.U, 0.U , pCounter + 1.U)