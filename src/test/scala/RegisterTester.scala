import chisel3._
import chisel3.iotesters.PeekPokeTester

class RegisterTester(dut: RegisterFile) extends PeekPokeTester(dut) {

  //LD
  poke(dut.io.readAdress1, 4)
  poke(dut.io.readAdress2, 5)
  poke(dut.io.writeSel, 5)
  poke(dut.io.writeData, 200)
  poke(dut.io.writeEnable, true)
  step(1)

  poke(dut.io.readAdress1, 5)
  System.out.println(peek(dut.io.output1))
}

object RegisterTester {
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
