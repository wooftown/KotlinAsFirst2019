@file:Suppress("UNUSED_PARAMETER", "ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")

package lesson9.task2

import kotlinx.html.ThScope
import lesson9.task1.*
import java.lang.IllegalStateException
import java.util.*
import kotlin.math.abs
import kotlin.math.max


/**
 * Пример
 *
 * Транспонировать заданную матрицу matrix.
 * При транспонировании строки матрицы становятся столбцами и наоборот:
 *
 * 1 2 3      1 4 6 3
 * 4 5 6  ==> 2 5 5 2
 * 6 5 4      3 6 4 1
 * 3 2 1
 */
fun <E> transpose(matrix: Matrix<E>): Matrix<E> {
    if (matrix.width < 1 || matrix.height < 1) return matrix
    val result = createMatrix(height = matrix.width, width = matrix.height, e = matrix[0, 0])
    for (i in 0 until matrix.width) {
        for (j in 0 until matrix.height) {
            result[i, j] = matrix[j, i]
        }
    }
    return result
}

/**
 * Пример
 *
 * Сложить две заданные матрицы друг с другом.
 * Складывать можно только матрицы совпадающего размера -- в противном случае бросить IllegalArgumentException.
 * При сложении попарно складываются соответствующие элементы матриц
 */
operator fun Matrix<Int>.plus(other: Matrix<Int>): Matrix<Int> {
    require(!(width != other.width || height != other.height)) { "matrices must be of the same size" }
    if (width < 1 || height < 1) return this
    val result = this.copy()
    for (i in 0 until height) {
        for (j in 0 until width) {
            result[i, j] += other[i, j]
        }
    }
    return result
}

/**
 * Сложная
 *
 * Заполнить матрицу заданной высоты height и ширины width
 * натуральными числами от 1 до m*n по спирали,
 * начинающейся в левом верхнем углу и закрученной по часовой стрелке.
 *
 * Пример для height = 3, width = 4:
 *  1  2  3  4
 * 10 11 12  5
 *  9  8  7  6
 */
fun generateSpiral(height: Int, width: Int): Matrix<Int> {
    TODO()
}

/**
 * Сложная
 *
 * Заполнить матрицу заданной высоты height и ширины width следующим образом.
 * Элементам, находящимся на периферии (по периметру матрицы), присвоить значение 1;
 * периметру оставшейся подматрицы – значение 2 и так далее до заполнения всей матрицы.
 *
 * Пример для height = 5, width = 6:
 *  1  1  1  1  1  1
 *  1  2  2  2  2  1
 *  1  2  3  3  2  1
 *  1  2  2  2  2  1
 *  1  1  1  1  1  1
 */
fun generateRectangles(height: Int, width: Int): Matrix<Int> {
    val result = createMatrix(height, width, 0)
    var res = 0
    while (res != max(height, width)) {
        res++
        for (row in (res - 1)..(height - res)) {
            for (column in (res - 1)..(width - res)) {
                result[row, column] = res
            }
        }
    }
    return result
}

/**
 * Сложная
 *
 * Заполнить матрицу заданной высоты height и ширины width диагональной змейкой:
 * в левый верхний угол 1, во вторую от угла диагональ 2 и 3 сверху вниз, в третью 4-6 сверху вниз и так далее.
 *
 * Пример для height = 5, width = 4:
 *  1  2  4  7
 *  3  5  8 11
 *  6  9 12 15
 * 10 13 16 18
 * 14 17 19 20
 */
fun generateSnake(height: Int, width: Int): Matrix<Int> {
    val result = createMatrix(height, width, 0)
    var row = 0
    var column: Int
    var counter = 1
    val list = mutableListOf<Pair<Int, Int>>()
    for (i in 0 until width) {
        list.add(row to i)
    }
    for (i in 1 until height) {
        list.add(i to width - 1)
    }
    list.reverse()
    while (counter != height * width + 1) {
        row = list.last().first
        column = list.last().second
        list.remove(list.last())
        while ((row < height) && (column >= 0)) {
            result[row, column] = counter
            counter++
            row++
            column--
        }
    }
    return result
}


/**
 * Средняя
 *
 * Содержимое квадратной матрицы matrix (с произвольным содержимым) повернуть на 90 градусов по часовой стрелке.
 * Если height != width, бросить IllegalArgumentException.
 *
 * Пример:    Станет:
 * 1 2 3      7 4 1
 * 4 5 6      8 5 2
 * 7 8 9      9 6 3
 */
fun <E> rotate(matrix: Matrix<E>): Matrix<E> {
    require(matrix.height == matrix.width)
    val result = matrix.copy()
    for (x in 0 until matrix.height) {
        for (y in 0 until matrix.width) {
            result[x, matrix.height - y - 1] = matrix[y, x]
        }
    }
    return result
}

/**
 * Сложная
 *
 * Проверить, является ли квадратная целочисленная матрица matrix латинским квадратом.
 * Латинским квадратом называется матрица размером n x n,
 * каждая строка и каждый столбец которой содержат все числа от 1 до n.
 * Если height != width, вернуть false.
 *
 * Пример латинского квадрата 3х3:
 * 2 3 1
 * 1 2 3
 * 3 1 2
 */
fun isLatinSquare(matrix: Matrix<Int>): Boolean {
    if (matrix.height != matrix.width) return false
    val trueSet = mutableSetOf<Int>()
    for (i in 1 until matrix.width + 1) {
        trueSet.add(i)
    }
    for (i in 0 until matrix.width) {
        if (matrix.listRow(i).toSet() != trueSet) return false
    }
    for (i in 0 until matrix.width) {
        if (matrix.listColumn(i).toSet() != trueSet) return false
    }
    return true
}

/**
 * Средняя
 *
 * В матрице matrix каждый элемент заменить суммой непосредственно примыкающих к нему
 * элементов по вертикали, горизонтали и диагоналям.
 *
 * Пример для матрицы 4 x 3: (11=2+4+5, 19=1+3+4+5+6, ...)
 * 1 2 3       11 19 13
 * 4 5 6  ===> 19 31 19
 * 6 5 4       19 31 19
 * 3 2 1       13 19 11
 *
 * Поскольку в матрице 1 х 1 примыкающие элементы отсутствуют,
 * для неё следует вернуть как результат нулевую матрицу:
 *
 * 42 ===> 0
 */
fun addZeros(matrix: Matrix<Int>): Matrix<Int> {
    val result = createMatrix(matrix.height + 2, matrix.width + 2, 0)
    for (row in 0 until matrix.height) {
        for (column in 0 until matrix.width) {
            result[row + 1, column + 1] = matrix[row, column]
        }
    }
    return result
}

fun sumNeighbours(matrix: Matrix<Int>): Matrix<Int> {
    val dirList = listOf(-1 to -1, 1 to 1, 1 to -1, -1 to 1, 0 to 1, 0 to -1, 1 to 0, -1 to 0)
    val newMatrix = addZeros(matrix)
    val result = matrix.copy()
    for (row in 0 until matrix.height) {
        for (column in 0 until matrix.width) {
            var sum = 0
            for ((x, y) in dirList) {
                sum += newMatrix[row + x + 1, column + y + 1]
            }
            result[row, column] = sum
        }
    }
    return result
}

/**
 * Средняя
 *
 * Целочисленная матрица matrix состоит из "дырок" (на их месте стоит 0) и "кирпичей" (на их месте стоит 1).
 * Найти в этой матрице все ряды и колонки, целиком состоящие из "дырок".
 * Результат вернуть в виде Holes(rows = список дырчатых рядов, columns = список дырчатых колонок).
 * Ряды и колонки нумеруются с нуля. Любой из спискоов rows / columns может оказаться пустым.
 *
 * Пример для матрицы 5 х 4:
 * 1 0 1 0
 * 0 0 1 0
 * 1 0 0 0 ==> результат: Holes(rows = listOf(4), columns = listOf(1, 3)): 4-й ряд, 1-я и 3-я колонки
 * 0 0 1 0
 * 0 0 0 0
 */
fun findHoles(matrix: Matrix<Int>): Holes {
    val rows = mutableListOf<Int>()
    val columns = mutableListOf<Int>()
    for (row in 0 until matrix.height) {
        if (matrix.listRow(row).sum() == 0) {
            rows.add(row)
        }
    }
    for (column in 0 until matrix.width) {
        if (matrix.listColumn(column).sum() == 0) {
            columns.add(column)
        }
    }
    return Holes(rows, columns)
}

/**
 * Класс для описания местонахождения "дырок" в матрице
 */
data class Holes(val rows: List<Int>, val columns: List<Int>)

/**
 * Средняя
 *
 * В целочисленной матрице matrix каждый элемент заменить суммой элементов подматрицы,
 * расположенной в левом верхнем углу матрицы matrix и ограниченной справа-снизу данным элементом.
 *
 * Пример для матрицы 3 х 3:
 *
 * 1  2  3      1  3  6
 * 4  5  6  =>  5 12 21
 * 7  8  9     12 27 45
 *
 * К примеру, центральный элемент 12 = 1 + 2 + 4 + 5, элемент в левом нижнем углу 12 = 1 + 4 + 7 и так далее.
 */

fun sumSubMatrix(matrix: Matrix<Int>): Matrix<Int> {
    val result = createMatrix(matrix.height, matrix.width, 0)
    for (row in 0 until matrix.height) {
        for (column in 0 until matrix.width) {
            var k = 0
            for (x in 0..row) {
                for (y in 0..column) {
                    k += matrix[x, y]
                }
            }
            result[row, column] = k
        }
    }
    return result
}

/**
 * Сложная
 *
 * Даны мозаичные изображения замочной скважины и ключа. Пройдет ли ключ в скважину?
 * То есть даны две матрицы key и lock, key.height <= lock.height, key.width <= lock.width, состоящие из нулей и единиц.
 *
 * Проверить, можно ли наложить матрицу key на матрицу lock (без поворота, разрешается только сдвиг) так,
 * чтобы каждой единице в матрице lock (штырь) соответствовал ноль в матрице key (прорезь),
 * а каждому нулю в матрице lock (дырка) соответствовала, наоборот, единица в матрице key (штырь).
 * Ключ при сдвиге не может выходить за пределы замка.
 *
 * Пример: ключ подойдёт, если его сдвинуть на 1 по ширине
 * lock    key
 * 1 0 1   1 0
 * 0 1 0   0 1
 * 1 1 1
 *
 * Вернуть тройку (Triple) -- (да/нет, требуемый сдвиг по высоте, требуемый сдвиг по ширине).
 * Если наложение невозможно, то первый элемент тройки "нет" и сдвиги могут быть любыми.
 */
fun findPart(key: Matrix<Int>, lock: Matrix<Int>, x: Int, y: Int): Matrix<Int> {
    val result = createMatrix(key.height, key.width, -1)
    for (row in x until x + key.height) {
        for (column in y until y + key.width) {
            result[row - x, column - y] = abs(lock[row, column] - 1)
        }
    }
    return result
}


fun canOpenLock(key: Matrix<Int>, lock: Matrix<Int>): Triple<Boolean, Int, Int> {
    for (row in 0 until lock.height - key.height + 1) {
        for (column in 0 until lock.width - key.width + 1) {
            if (key == findPart(key, lock, row, column)) return Triple(true, row, column)
        }
    }
    return Triple(false, 0, 0)
}

/**
 * Простая
 *
 * Инвертировать заданную матрицу.
 * При инвертировании знак каждого элемента матрицы следует заменить на обратный
 */
operator fun Matrix<Int>.unaryMinus(): Matrix<Int> {
    val result = MatrixImpl(height, width, 0)
    for (row in 0 until height) {
        for (column in 0 until width) {
            result[row, column] = -this[row, column]
        }
    }
    return result
}

private fun <T> Matrix<T>.copy(): Matrix<T> {
    val result = createMatrix(height, width, this[0, 0])
    for (row in 0 until height) {
        for (column in 0 until width) {
            result[row, column] = this[row, column]
        }
    }
    return result
}


/**
 * Средняя
 *
 * Перемножить две заданные матрицы друг с другом.
 * Матрицы можно умножать, только если ширина первой матрицы совпадает с высотой второй матрицы.
 * В противном случае бросить IllegalArgumentException.
 * Подробно про порядок умножения см. статью Википедии "Умножение матриц".
 */
operator fun Matrix<Int>.times(other: Matrix<Int>): Matrix<Int> {
    require(this.width == other.height)
    val result = createMatrix(this.height, other.width, 0)
    for (row in 0 until this.height) {
        for (column in 0 until other.width) {
            for (k in 0 until this.width) {
                result[row, column] += (this[row, k] * other[k, column])
            }
        }
    }
    return result
}


/**
 * Сложная
 *
 * В матрице matrix размером 4х4 дана исходная позиция для игры в 15, например
 *  5  7  9  1
 *  2 12 14 15
 *  3  4  6  8
 * 10 11 13  0
 *
 * Здесь 0 обозначает пустую клетку, а 1-15 – фишки с соответствующими номерами.
 * Напомним, что "игра в 15" имеет квадратное поле 4х4, по которому двигается 15 фишек,
 * одна клетка всегда остаётся пустой. Цель игры -- упорядочить фишки на игровом поле.
 *
 * В списке moves задана последовательность ходов, например [8, 6, 13, 11, 10, 3].
 * Ход задаётся номером фишки, которая передвигается на пустое место (то есть, меняется местами с нулём).
 * Фишка должна примыкать к пустому месту по горизонтали или вертикали, иначе ход не будет возможным.
 * Все номера должны быть в пределах от 1 до 15.
 * Определить финальную позицию после выполнения всех ходов и вернуть её.
 * Если какой-либо ход является невозможным или список содержит неверные номера,
 * бросить IllegalStateException.
 *
 * В данном случае должно получиться
 * 5  7  9  1
 * 2 12 14 15
 * 0  4 13  6
 * 3 10 11  8
 */
private val fifteenDir = listOf(1 to 0, -1 to 0, 0 to 1, 0 to -1)

fun findNearZero(matrix: Matrix<Int>, x: Int, zero: Cell): Cell {
    for ((i, j) in fifteenDir) {
        val row = zero.row + j
        val column = zero.column + i
        if ((column in 0..3) && (row in 0..3) && (matrix[row, column] == x)) {
            return Cell(row, column)
        }
    }
    return zero
}


fun fifteenGameMoves(matrix: Matrix<Int>, moves: List<Int>): Matrix<Int> {
    val result = matrix.copy()
    var zero = Cell(0, 0)
    for (i in 0..3) {
        for (j in 0..3) {
            if (result[i, j] == 0) zero = Cell(i, j)
        }
    }
    for (i in moves) {
        val next = findNearZero(result, i, zero)
        check(next != zero)
        result[zero] = i
        result[next] = 0
        zero = next
    }
    return result
}

/**
 * Очень сложная
 *
 * В матрице matrix размером 4х4 дана исходная позиция для игры в 15, например
 *  5  7  9  2
 *  1 12 14 15
 *  3  4  6  8
 * 10 11 13  0
 *
 * Здесь 0 обозначает пустую клетку, а 1-15 – фишки с соответствующими номерами.
 * Напомним, что "игра в 15" имеет квадратное поле 4х4, по которому двигается 15 фишек,
 * одна клетка всегда остаётся пустой.
 *
 * Цель игры -- упорядочить фишки на игровом поле, приведя позицию к одному из следующих двух состояний:
 *
 *  1  2  3  4          1  2  3  4
 *  5  6  7  8   ИЛИ    5  6  7  8
 *  9 10 11 12          9 10 11 12
 * 13 14 15  0         13 15 14  0
 *
 * Можно математически доказать, что РОВНО ОДНО из этих двух состояний достижимо из любой исходной позиции.
 *
 * Вернуть решение -- список ходов, приводящих исходную позицию к одной из двух упорядоченных.
 * Каждый ход -- это перемена мест фишки с заданным номером с пустой клеткой (0),
 * при этом заданная фишка должна по горизонтали или по вертикали примыкать к пустой клетке (но НЕ по диагонали).
 * К примеру, ход 13 в исходной позиции меняет местами 13 и 0, а ход 11 в той же позиции невозможен.
 *
 * Одно из решений исходной позиции:
 *
 * [8, 6, 14, 12, 4, 11, 13, 14, 12, 4,
 * 7, 5, 1, 3, 11, 7, 3, 11, 7, 12, 6,
 * 15, 4, 9, 2, 4, 9, 3, 5, 2, 3, 9,
 * 15, 8, 14, 13, 12, 7, 11, 5, 7, 6,
 * 9, 15, 8, 14, 13, 9, 15, 7, 6, 12,
 * 9, 13, 14, 15, 12, 11, 10, 9, 13, 14,
 * 15, 12, 11, 10, 9, 13, 14, 15]
 *
 * Перед решением этой задачи НЕОБХОДИМО решить предыдущую
 * 1 2 3 4 5 6 7 8 9 10 11 12 0 13 14 15   6==6
 *
 * getvalue\[x] - 10.5 9.5
 * \[x] - 8.6 9.2
 * \[matrix] - 8.0
 *
 *
 */
class Field(val field: Matrix<Int>, val hops: List<Int>, val zero: Cell, val f: Int) {
    fun nearZero(): List<Cell> {
        val list = mutableListOf<Cell>()
        for ((i, j) in fifteenDir) {
            if (zero.row + i in 0..3 && zero.column + j in 0..3) {
                list.add(Cell(zero.row + i, zero.column + j))
            }
        }
        return list
    }
}

val fieldF = mapOf(
    1 to Pair(0, 0), 2 to Pair(0, 1), 3 to Pair(0, 2),
    4 to Pair(0, 3), 5 to Pair(1, 0), 6 to Pair(1, 1),
    7 to Pair(1, 2), 8 to Pair(1, 3), 9 to Pair(2, 0),
    10 to Pair(2, 1), 11 to Pair(2, 2), 12 to Pair(2, 3),
    13 to Pair(3, 1), 14 to Pair(3, 2), 15 to Pair(3, 3),
    0 to Pair(3, 3)
)

fun findF(matrix: Matrix<Int>): Int {
    var f = 0
    for (i in 0..3) {
        for (j in 0..3) {
            f += abs(fieldF[matrix[i, j]]!!.first - i) + abs(fieldF[matrix[i, j]]!!.second - j)
        }
    }
    return f
}

fun findZero(matrix: Matrix<Int>): Cell? {
    for (row in 0..3) {
        for (column in 0..3) {
            if (matrix[row, column] == 0) {
                return Cell(row, column)
            }
        }
    }
    return null
}

fun fifteenGameSolution(matrix: Matrix<Int>): List<Int> {
    val winField = createMatrix(4, 4, 0)
    for (i in 1..15) {
        winField[(i - 1) / 4, (i - 1) % 4] = i
    }
    val winField2 = winField.copy()
    winField2[3, 2] = 14
    winField2[3, 1] = 15
    if (matrix == winField || matrix == winField2) {
        return listOf()
    }

    val passedFields = mutableSetOf(matrix)
    val fieldsQueue = PriorityQueue<Field>(compareBy { it.f })
    fieldsQueue.add(
        Field(
            matrix, listOf(),
            findZero(matrix) ?: throw IllegalStateException("cant find zero"), findF(matrix)
        )
    )

    while (true) {
        val next = fieldsQueue.poll()
        for (move in next.nearZero()) {
            val new = next.field.copy()
            new[next.zero] = new[move]
            new[move] = 0
            val hops = next.hops.toMutableList()
            hops.add(new[next.zero])
            if (new == winField || new == winField2) {
                return hops
            }
            if (new in passedFields) {
                continue
            }
            passedFields.add(new)
            fieldsQueue.add(Field(new, hops, move, findF(new)))
        }
    }
}



