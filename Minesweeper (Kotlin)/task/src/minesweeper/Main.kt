package minesweeper

import kotlin.random.Random

fun main() {
    val mineField = showAdjacentMines(createMineField(9, 9))

    printMineField(mineField)
}

fun createMineField(rows: Int, columns: Int): Array<CharArray> {
    println("How many mines do you want on the field?")
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

fun showAdjacentMines(mineField: Array<CharArray>): Array<CharArray> {
    for (i in mineField.indices) {
        for (j in mineField[i].indices) {
            if (mineField[i][j] == '.') {
                var count = 0
                if (i == 0) {
                    if (j == 0) {
                        if (mineField[0][1] == 'X') count++
                        if (mineField[1][0] == 'X') count++
                        if (mineField[1][1] == 'X') count++
                        if (count > 0) mineField[i][j] = count.digitToChar()
                    } else if (j == 8) {
                        if (mineField[0][7] == 'X') count++
                        if (mineField[1][7] == 'X') count++
                        if (mineField[1][8] == 'X') count++
                        if (count > 0) mineField[i][j] = count.digitToChar()
                    } else {
                        if (mineField[i][j - 1] == 'X') count++
                        if (mineField[i][j + 1] == 'X') count++
                        if (mineField[i + 1][j - 1] == 'X') count++
                        if (mineField[i + 1][j] == 'X') count++
                        if (mineField[i + 1][j + 1] == 'X') count++
                        if (count > 0) mineField[i][j] = count.digitToChar()
                    }
                } else if (i == 8) {
                    if (j == 0) {
                        if (mineField[8][1] == 'X') count++
                        if (mineField[7][0] == 'X') count++
                        if (mineField[7][1] == 'X') count++
                        if (count > 0) mineField[i][j] = count.digitToChar()
                    } else if (j == 8) {
                        if (mineField[8][7] == 'X') count++
                        if (mineField[7][7] == 'X') count++
                        if (mineField[7][8] == 'X') count++
                        if (count > 0) mineField[i][j] = count.digitToChar()
                    } else {
                        if (mineField[i][j - 1] == 'X') count++
                        if (mineField[i][j + 1] == 'X') count++
                        if (mineField[i - 1][j - 1] == 'X') count++
                        if (mineField[i - 1][j] == 'X') count++
                        if (mineField[i - 1][j + 1] == 'X') count++
                        if (count > 0) mineField[i][j] = count.digitToChar()
                    }
                } else if (j == 0) {
                    if (mineField[i - 1][0] == 'X') count++
                    if (mineField[i - 1][1] == 'X') count++
                    if (mineField[i][1] == 'X') count++
                    if (mineField[i + 1][0] == 'X') count++
                    if (mineField[i + 1][1] == 'X') count++
                    if (count > 0) mineField[i][j] = count.digitToChar()
                } else if (j == 8) {
                    if (mineField[i - 1][8] == 'X') count++
                    if (mineField[i - 1][7] == 'X') count++
                    if (mineField[i][7] == 'X') count++
                    if (mineField[i + 1][8] == 'X') count++
                    if (mineField[i + 1][7] == 'X') count++
                    if (count > 0) mineField[i][j] = count.digitToChar()
                } else {
                    if (mineField[i - 1][j - 1] == 'X') count++
                    if (mineField[i - 1][j] == 'X') count++
                    if (mineField[i - 1][j + 1] == 'X') count++
                    if (mineField[i][j - 1] == 'X') count++
                    if (mineField[i][j + 1] == 'X') count++
                    if (mineField[i + 1][j - 1] == 'X') count++
                    if (mineField[i + 1][j] == 'X') count++
                    if (mineField[i + 1][j + 1] == 'X') count++
                    if (count > 0) mineField[i][j] = count.digitToChar()
                }
            }
        }
    }
    return mineField
}