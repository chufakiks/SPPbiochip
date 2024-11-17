import chisel3._
import chisel3.util._
import chisel3.iotesters
import chisel3.iotesters.PeekPokeTester
import java.util

object Programs{
  val program1 = Array(
    "b00111010000000001111111100000000".U(32.W), // 0
    "b00110110000000000000000100000000".U(32.W), // 1
    "b00110111000000000000000000000000".U(32.W), // 2
    "b00111000000000000001010000000000".U(32.W), // 3
    "b00111001000000011001000000000000".U(32.W), // 4
    "b00110000000000000000000000000000".U(32.W), // 5
    "b00110001000000000001001100000000".U(32.W), // 6
    "b0011_0010_0001_00000000000000000000".U(32.W), // 7
    "b10000000011100011010000000000000".U(32.W), // 8
    "b10000010011100011010000000000000".U(32.W), // 9
    "b10000000000100011010000000000000".U(32.W), // 10
    "b1000_0010_0001_0001101_0000000000000".U(32.W), // 11 c
    "b0111_0000_0000_0010010_0000000000000".U(32.W), // 12 c
    "b0010_0011_0010_1000_0000000000000000".U(32.W), // 13 c
    "b0000_0100_0011_0000_0000000000000000".U(32.W), // 14 c
    "b0000_0101_0100_1001_0000000000000000".U(32.W), // 15 c
    "b0101_0111_0101_0000_0000000000000000".U(32.W), // 16 c
    "b0111_0000_0000_0100111_0000000000000".U(32.W), // 17 c
    "b0010_0011_0010_1000_0000000000000000".U(32.W), // 18 c
    "b0000_0011_0000_0011_0000000000000000".U(32.W), // 19 c
    "b0100_0101_0011_0000_0000000000000000".U(32.W), // 20 c
    "b1000_0101_0111_0100101_0000000000000".U(32.W), // 21 c
    "b0001_0100_0011_1000_0000000000000000".U(32.W), // 22 c
    "b0100_0101_0100_0000_0000000000000000".U(32.W), // 23 c
    "b1000_0101_0111_0100101_0000000000000".U(32.W), // 24 c
    "b0000_0100_0011_1000_0000000000000000".U(32.W), // 25 c
    "b0100_0101_0100_0000_0000000000000000".U(32.W), // 26 c
    "b1000_0101_0111_0100101_0000000000000".U(32.W), // 27 c
    "b0001_0100_0011_0110_0000000000000000".U(32.W), // 28 c
    "b0100_0101_0100_0000_0000000000000000".U(32.W), // 29 c
    "b1000_0100_0111_0100101_0000000000000".U(32.W), // 30 c
    "b0000_0100_0011_0110_0000000000000000".U(32.W), // 31 c
    "b0100_0101_0100_0000_0000000000000000".U(32.W), // 32 c
    "b1000_0101_0111_0100101_0000000000000".U(32.W), // 33 c
    "b0000_0100_0011_1001_0000000000000000".U(32.W), // 34 c
    "b0101_1010_0100_00000000000000000000".U(32.W), // 35 c
    "b0111_0000_0000_0100111_0000000000000".U(32.W), // 36 c
    "b0000_0100_0011_1001_0000000000000000".U(32.W), // 37 c
    "b0101_0111_0100_0000_0000000000000000".U(32.W), // 38 c
    "b0000_0010_0010_0110_0000000000000000".U(32.W), // 39 c
    "b1001_0010_1000_0001000_0000000000000".U(32.W), // 40 c
    "b0000_0000_0000_0110_0000000000000000".U(32.W), // 41 c
    "b1001_0000_0001_0000111_0000000000000".U(32.W), // 42 c
    "b1010_0000_0000_0000_0000000000000000".U(32.W) // 43 c
  )
}