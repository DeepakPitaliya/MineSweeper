package minesweeper

import kotlin.random.Random

val minesSet = mutableSetOf<Pair<Int, Int>>()
val minesByPlayerSet = mutableSetOf<Pair<Int, Int>>()
val displayField = Array(9) { CharArray(9) { '.' } } // field displayed to player
val mineField = Array(9) { CharArray(9) { '.' } }    // actual field with mines marked, not displayed to player


fun main() {
    println("How many mines do you want on the field?")
    val numMines = readln().toInt()
    printDisplayField(displayField)

    // first input and create field after it so that first input cell is safe and use it to mark cells in display
    var t = takeInput()
    val mineField = getField(Pair(t.x, t.y), numMines)
    if (t.mode == "free") {
        markSlash(mineField, displayField, t.x, t.y)
    } else {
        displayField[t.x][t.y] = '*'
    }
    if (isWin()) {
        printDisplayField(displayField)
        println("Congratulations! You found all the mines!")
    } else {
        printDisplayField(displayField)
    }

    // main game loop
    while (!isWin()) {
        t = takeInput()
        when (t.mode) {
            "free" -> {
                if (mineField[t.x][t.y] == 'X') {
                    minesSet.forEach {
                        displayField[it.first][it.second] = 'X'
                    }
                    printDisplayField(displayField)
                    println("You stepped on a mine and failed!")
                    break
                } else {
                    markSlash(mineField, displayField, t.x, t.y)
                }
            }

            "mine" -> {
                if (displayField[t.x][t.y] == '.') {
                    displayField[t.x][t.y] = '*'
                    minesByPlayerSet.add(Pair(t.x, t.y))
                } else if (displayField[t.x][t.y] == '*') {
                    displayField[t.x][t.y] = '.'
                    minesByPlayerSet.remove(Pair(t.x, t.y))
                }
            }
        }
        printDisplayField(displayField)
        if (isWin()) {
            println("Congratulations! You found all the mines!")
        }
    }
}

data class Input(val x: Int, val y: Int, val mode: String)

fun takeInput(): Input {
    println("Set/unset mine marks or claim a cell as free:")
    val input = readln().split(" ")
    val x = input[1].toInt() - 1
    val y = input[0].toInt() - 1
    val mode = input[2]
    return Input(x, y, mode)
}

fun getField(pair: Pair<Int, Int>, numMines: Int): Array<CharArray> {
    while (minesSet.size < numMines) {
        val mine = Pair(Random.nextInt(9), Random.nextInt(9))
        if (mine != pair) minesSet.add(mine)
    }
    minesSet.forEach {
        mineField[it.first][it.second] = 'X'
        for (r in maxOf(it.first - 1, 0)..minOf(it.first + 1, 8)) {
            for (c in maxOf(it.second - 1, 0)..minOf(it.second + 1, 8)) {
                when (mineField[r][c]) {
                    '.' -> mineField[r][c] = '1'
                    in '1'..'7' -> mineField[r][c]++
                }
            }
        }
    }
    return mineField
}

fun markSlash(mineField: Array<CharArray>, displayField: Array<CharArray>, x: Int, y: Int) {

    if (mineField[x][y] == '.') {
        mineField[x][y] = '/'
        displayField[x][y] = '/'
        for (r in maxOf(x - 1, 0)..minOf(x + 1, 8)) {
            for (c in maxOf(y - 1, 0)..minOf(y + 1, 8)) {
                markSlash(mineField, displayField, r, c)
            }
        }
    } else {
        displayField[x][y] = mineField[x][y]
    }
}

fun printDisplayField(displayField: Array<CharArray>) {
    println(" │123456789│\n—│—————————│")
    for (i in displayField.indices) {
        print("${i + 1}|")
        for (j in displayField[i].indices) {
            print(displayField[i][j])
        }
        println("|")
    }
    println("—│—————————│")
}

fun isWin(): Boolean {
    var dotCount = 0
    return if (minesSet.containsAll(minesByPlayerSet) && minesByPlayerSet.containsAll(minesSet)) {
        true
    } else {
        for (r in displayField) {
            for (c in r) {
                if (c == '.') dotCount++
            }
        }
        dotCount == minesSet.size
    }
}
