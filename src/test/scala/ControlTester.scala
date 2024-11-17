import chisel3.iotesters.PeekPokeTester
import chisel3.iotesters

class ControlTester(dut: ControlUnit) extends PeekPokeTester(dut) {
  // 0000
  poke(dut.io.opcode, 0)
  System.out.print(peek(dut.io.regWrite))
  System.out.print("\n")
  System.out.print(peek(dut.io.aluOp))
  step(1)

  // 0001
  poke(dut.io.opcode, 1)
  System.out.print(peek(dut.io.regWrite))
  System.out.print("\n")
  System.out.print(peek(dut.io.aluOp))
  step(1)

  // 0010
  poke(dut.io.opcode, 2)
  System.out.print(peek(dut.io.regWrite))
  System.out.print("\n")
  System.out.print(peek(dut.io.aluOp))
  step(1)

  // 0011
  poke(dut.io.opcode, 3)
  System.out.print(peek(dut.io.regWrite))
  System.out.print("\n")
  System.out.print(peek(dut.io.aluOp))
  System.out.print("\n")
  System.out.print(peek(dut.io.aluSrc))
  step(1)

  // 0100
  poke(dut.io.opcode, 4)
  System.out.print(peek(dut.io.regWrite))
  System.out.print("\n")
  System.out.print(peek(dut.io.aluOp))
  System.out.print("\n")
  System.out.print(peek(dut.io.memRead))
  step(1)

  // 0101
  poke(dut.io.opcode, 5)
  System.out.print(peek(dut.io.aluOp))
  System.out.print("\n")
  System.out.print(peek(dut.io.memWrite))
  step(1)

  // 0111
  poke(dut.io.opcode, 7)
  System.out.print(peek(dut.io.aluOp))
  step(1)

  // 1000
  poke(dut.io.opcode, 8)
  System.out.print(peek(dut.io.aluOp))
  step(1)

  // 1001
  poke(dut.io.opcode, 9)
  System.out.print(peek(dut.io.aluOp))
  step(1)

  // 1010
  poke(dut.io.opcode, 10)
  System.out.print(peek(dut.io.aluOp))
  System.out.print("\n")
  System.out.print(peek(dut.io.stop))
  step(1)

  object ControlTester {
    def main(args: Array[String]): Unit = {
      println("Testing ControlUnit")
      iotesters.Driver.execute(
        Array("--generate-vcd-output", "on",
          "--target-dir", "generated",
          "--top-name", "CPUTop"),
        () => new ControlUnit()) {
        c => new ControlTester(c)
      }
    }
  }
}
