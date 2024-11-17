import chisel3._
import chisel3.iotesters.PeekPokeTester

class RegisterTester(dut: RegisterFile) extends PeekPokeTester(dut) {

  // Initialize registers with some values for testing
  for (i <- 0 until 11) {
    poke(dut.io.writeSel, i)
    poke(dut.io.writeData, i * 10) // Assign value as 10 * index
    poke(dut.io.writeEnable, true)
    step(1)
  }

  // Verify write by reading back the values
  for (i <- 0 until 11) {
    poke(dut.io.readAdress1, i)
    expect(dut.io.output1, i * 10) // Expect the value written earlier
  }

  // Test simultaneous reads
  poke(dut.io.readAdress1, 2)
  poke(dut.io.readAdress2, 4)
  step(1)
  expect(dut.io.output1, 20) // Value at index 2
  expect(dut.io.output2, 40) // Value at index 4

  // Test write disable (writeEnable = false)
  poke(dut.io.writeSel, 3)
  poke(dut.io.writeData, 999) // Attempt to write this value
  poke(dut.io.writeEnable, false)
  step(1)
  poke(dut.io.readAdress1, 3)
  expect(dut.io.output1, 30) // Original value remains (no overwrite)

  // Overwrite test
  poke(dut.io.writeSel, 3)
  poke(dut.io.writeData, 1234)
  poke(dut.io.writeEnable, true)
  step(1)
  poke(dut.io.readAdress1, 3)
  expect(dut.io.output1, 1234) // Overwritten value should match
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
