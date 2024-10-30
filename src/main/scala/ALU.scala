import chisel3._
import chisel3.util._

class ALU extends Module {
  val io = IO(new Bundle {
    val A = Input(UInt(32.W))
    val B = Input(UInt(32.W))
    val opcode = Input(UInt(32.W))
    val resultint = Output(UInt(32.W))
    val resultbool = Output(Bool())
  })

  resultint := 0.U
  resultbool := false.B

  switch(io.opcode){
    is(0.U) { io.resultint := io.A + io.B }
    is(1.U) { io.resultint := io.A - io.B } 
    is(2.U) { io.resultint := io.A * io.B }
    is(3.U) { io.resultbool := io.A == io.B }
  }

}