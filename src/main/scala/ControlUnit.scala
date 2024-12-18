import chisel3._
import chisel3.util._

class ControlUnit extends Module {
  val io = IO(new Bundle {
    val opcode = Input(UInt(5.W)) // type of instruction
    val aluOp = Output(UInt(4.W)) //alu operation
    val regWrite = Output(Bool()) // if we write to memory
    val memWrite = Output(Bool()) //if write to regiter SD
    val memRead = Output(Bool()) //if read from memory LD
    val stop = Output(Bool())
    //Define the module interface here (inputs/outputs)
    val aluSrc = Output(Bool())        // if ALU uses or immediate

  })
  io.aluOp := 0.U
  io.regWrite := false.B
  io.memWrite := false.B
  io.memRead := false.B
  io.stop := false.B
  io.aluSrc := false.B

  switch(io.opcode) {
    is(0.U) { //add
      io.regWrite := true.B // write result to reg
      io.aluOp := 0.U //add op
    }
    is(1.U) { // SUB
      io.regWrite := true.B
      io.aluOp := 1.U // SUB operation

    }
    is(2.U) { // MULT
      io.regWrite := true.B
      io.aluOp := 2.U // MULT operation

    }
    is(3.U) { // LI (Load Immediate)// LI
      io.regWrite := true.B
      io.aluOp := 3.U // ADD operation
      io.aluSrc := true.B  // ALU uses immediate value as operator

    }
    is(4.U) { //LD (Load from Data Memory)
      io.regWrite := true.B
      io.aluOp := 4.U
      io.memRead := true.B
    }
    is(5.U) { // SD
      io.aluOp := 4.U
      io.memWrite := true.B
    }
    is(7.U) { //JR
      io.aluOp  := 5.U

    }
    is(8.U) { // JEQ
      io.aluOp := 6.U //SUB

    }
    is(9.U) { // JLT
      io.aluOp := 7.U //sub

    }
    is(10.U) { // END
      io.stop := true.B

    }
  }
  //Implement this module here
}