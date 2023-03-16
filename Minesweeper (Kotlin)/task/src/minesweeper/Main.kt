package minesweeper

import kotlin.random.Random

fun main() {
    val mineField = createMineField(9, 9)
    printMineField(mineField)
}

fun createMineField(rows: Int, columns: Int): Array<CharArray> {
    println( "How many mines do you want on the field?")
    val numMines = readln().toInt()

    val mineField = Array(rows) { CharArray(columns) }
    for (i in 0 until rows) {
        for (j in 0 until columns) {
            mineField[i][j] = '.'
        }
    }

    var minesPlaced = 0
    while (minesPlaced < numMines) {
        val mineRow = Random.nextInt(rows)
        val mineCol = Random.nextInt(columns)
        if (mineField[mineRow][mineCol] == '.') {
            mineField[mineRow][mineCol] = 'X'
            minesPlaced ++
        }
    }
    return mineField
}

fun printMineField(mineField: Array<CharArray>) {
    for (i in mineField.indices) {
        for (j in mineField[i].indices) {
            print(mineField[i][j])
        }
        println()
    }
}