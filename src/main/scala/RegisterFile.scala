import chisel3._
import chisel3.util._

class RegisterFile extends Module {
  val io = IO(new Bundle {
    val readAdress1 = Input(UInt(5.W))
    val readAdress2 = Input(UInt(5.W))
    val writeSel = Input(UInt(5.W))

    val writeData = Input(UInt(32.W))

    val output1 = Output(UInt(32.W)) //read data
    val output2 = Output(UInt(32.W)) //read data
    val writeEnable = Input(Bool())


    //Define the module interface here (inputs/outputs)
  })
  val registers = Reg(Vec(11, UInt(16.W))) //ny array av registers

  when(io.writeEnable) { //skriv om register är aktiv
    registers(io.writeSel) := io.writeData
  }
  io.output1 := registers(io.readAdress1)
  io.output2 := registers(io.readAdress2)
  //Implement this module here

}