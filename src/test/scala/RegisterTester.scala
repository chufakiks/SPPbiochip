/*import chisel3._
import chisel3.iotesters.PeekPokeTester

class RegisterTester(dut: RegisterFile) extends PeekPokeTester(dut) {

  //Program Counter running for 5 clock cycles
  poke(dut.io.readAdress1, 1)
  poke(dut.io.readAdress2, 0)
  poke(dut.io.writeSel, 1)
  poke(dut.io.writeData, 0)
  poke(dut.io.writeEnable,1)
  step(5)
  System.out.print(peek(dut.io.))
  System.out.print("\n")
}

object ProgramCounterTester {
  def main(args: Array[String]): Unit = {
    println("Testing RegisterFile")
    iotesters.Driver.execute(
      Array("--generate-vcd-output", "on",
        "--target-dir", "generated",
        "--top-name", "RegisterFile"),
      () => new RegisterFile()) {
      c => new RegisterTester(c)
    }
  }
}
*/