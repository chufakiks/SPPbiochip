import chisel3._
import chisel3.util._

class ALU extends Module {
  val io = IO(new Bundle {
    val input1 = Input(UInt(32.W))
    val input2 = Input(UInt(32.W))
    val aluOp = Input(UInt(32.W))
    val resultint = Output(UInt(32.W))
    val resultbool = Output(Bool())
  })

  io.resultint := 0.U
  io.resultbool := false.B

  switch(io.aluOp){
    is(0.U) { io.resultint := io.input1 + io.input2 } // add
    is(1.U) { io.resultint := io.input1 - io.input2 } // sub
    is(2.U) { io.resultint := io.input1 * io.input2 } // mul
    is(3.U) { io.resultint := io.input2 } // Li
    is(4.U) { io.resultint := io.input1 } // LD & SD
    is(5.U) { // Jump
      io.resultint := io.input1
      io.resultbool := true.B
    }
    is(6.U) { // Jump if equal
      when (io.input1 === io.input2) {
        io.resultint := io.input1
        io.resultbool := true.B
      }
    }
    is(7.U) { // jump if less than
      when (io.input1 > io.input2) {
        io.resultint := io.input1
        io.resultbool := true.B
      }
    }
  }
}