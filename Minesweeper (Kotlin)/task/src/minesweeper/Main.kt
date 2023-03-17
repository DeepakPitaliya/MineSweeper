package minesweeper

import kotlin.random.Random

val minesInFieldCoordinates = mutableListOf<Pair<Int, Int>>()
val minesByPlayerCoordinates = mutableListOf<Pair<Int, Int>>()
fun main() {
    val mineField = createMineField(9, 9)
    printMineField(mineField)
    while (!(minesInFieldCoordinates.containsAll(minesByPlayerCoordinates) && minesByPlayerCoordinates.containsAll(
            minesInFieldCoordinates
        ))
    ) {
        println("Set/delete mines marks (x and y coordinates):")
        val input = readln().split(" ").map { it -> it.toInt() }
        val y = input[0] - 1
        val x = input[1] - 1
        setOrRemoveMark(mineField, x, y)
    }
    println("Congratulations! You found all the mines!")
}

fun setOrRemoveMark(mineField: Array<CharArray>, x: Int, y: Int): Array<CharArray> {
    if (mineField[x][y] in '1'..'8') {
        println("There is a number here!")
    } else if (mineField[x][y] == '.') {
        mineField[x][y] = '*'
        minesByPlayerCoordinates.add(Pair(x, y))
        printMineField(mineField)
    } else {
        mineField[x][y] = '.'
        minesByPlayerCoordinates.remove(Pair(x, y))
        printMineField(mineField)
    }
    return mineField
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
            minesPlaced++
            minesInFieldCoordinates.add(Pair(mineRow, mineCol))
        }
    }
    for (i in mineField.indices) {
        for (j in mineField[i].indices) {
            if (mineField[i][j] == 'X') mineField[i][j] = '.'
        }
    }
    return mineField
}

fun printMineField(mineField: Array<CharArray>) {
    println(" │123456789│\n—│—————————│")
    for (i in mineField.indices) {
        print("${i + 1}|")
        for (j in mineField[i].indices) {
            print(mineField[i][j])
        }
        println("|")
    }
    println("—│—————————│")
}
