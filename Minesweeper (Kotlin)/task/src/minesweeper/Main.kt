package minesweeper

import kotlin.random.Random

fun main() {
    val mineField = createMineField(9, 9)
    printMineField(mineField)
}

fun createMineField(rows: Int, columns: Int): Array<CharArray> {
    println("How many mines do you want on the field?")
    val numMines = readln().toInt()

    val mineField = Array(rows) { CharArray(columns) { '.' } }

    var minesPlaced = 0
    while (minesPlaced < numMines) {
        val mineRow = Random.nextInt(rows)
        val mineCol = Random.nextInt(columns)
        if (mineField[mineRow][mineCol] != 'X') {
            mineField[mineRow][mineCol] = 'X'
            for (r in maxOf(mineRow - 1, 0)..minOf(mineRow + 1, 8)) {
                for (c in maxOf(mineCol - 1, 0)..minOf(mineCol + 1, 8)) {
                    when (mineField[r][c]) {
                        '.' -> mineField[r][c] = '1'
                        in '1'..'7' -> mineField[r][c]++
                    }
                }
            }
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
