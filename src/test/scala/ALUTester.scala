import chisel3._
import chisel3.iotesters
import chisel3.iotesters.PeekPokeTester

import java.util

class ALUTester(dut: ALU) extends PeekPokeTester(dut) {
  // ALU Tester passthrough 5 clock cycles

  // 1+1
  poke(dut.io.input1, 1)
  poke(dut.io.input2, 1)
  poke(dut.io.aluOp, 0)
  System.out.print(peek(dut.io.resultint))
  System.out.print("\n")
  step(5)

  // 1-1
  poke(dut.io.input1, 1)
  poke(dut.io.input2, 1)
  poke(dut.io.aluOp, 1)
  System.out.print(peek(dut.io.resultint))
  System.out.print("\n")
  step(5)

  // 1*1
  poke(dut.io.input1, 1)
  poke(dut.io.input2, 1)
  poke(dut.io.aluOp, 2)
  System.out.print(peek(dut.io.resultint))
  System.out.print("\n")
  step(5)

  //li

  poke(dut.io.input1, 1)
  poke(dut.io.input2, 1)
  poke(dut.io.aluOp, 3)
  System.out.print(peek(dut.io.resultint))
  System.out.print("\n")
  step(5)

  //ld & sd

  poke(dut.io.input1, 1)
  poke(dut.io.input2, 1)
  poke(dut.io.aluOp, 4)
  System.out.print(peek(dut.io.resultint))
  System.out.print("\n")
  step(5)

  //jump

  poke(dut.io.input1, 1)
  poke(dut.io.input2, 1)
  poke(dut.io.aluOp, 5)
  System.out.print(peek(dut.io.resultint))
  System.out.print("\n")
  System.out.print(peek(dut.io.resultbool))
  System.out.print("\n")
  step(5)

  //jeq

  poke(dut.io.input1, 1)
  poke(dut.io.input2, 1)
  poke(dut.io.aluOp, 6)
  System.out.print(peek(dut.io.resultint))
  System.out.print("\n")
  System.out.print(peek(dut.io.resultbool))
  System.out.print("\n")
  step(5)

  poke(dut.io.input1, 1)
  poke(dut.io.input2, 7)
  poke(dut.io.aluOp, 6)
  System.out.print(peek(dut.io.resultint))
  System.out.print("\n")
  System.out.print(peek(dut.io.resultbool))
  System.out.print("\n")
  step(5)

  //jlt
  poke(dut.io.input1, 1)
  poke(dut.io.input2, 1)
  poke(dut.io.aluOp, 6)
  System.out.print(peek(dut.io.resultint))
  System.out.print("\n")
  System.out.print(peek(dut.io.resultbool))
  System.out.print("\n")
  step(5)


  poke(dut.io.input1, 1)
  poke(dut.io.input2, 2)
  poke(dut.io.aluOp, 7)
  System.out.print(peek(dut.io.resultint))
  System.out.print("\n")
  System.out.print(peek(dut.io.resultbool))
  System.out.print("\n")
  step(5)

}
object ALUTester {
  def main(args: Array[String]): Unit = {
    println("Testing ALU")
    iotesters.Driver.execute(
      Array("--generate-vcd-output", "on",
        "--target-dir", "generated",
        "--top-name", "ALU"),
      () => new ALU()) {
      c => new ALUTester(c)
    }
  }
}