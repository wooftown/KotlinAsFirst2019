@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson9.task1

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E

    operator fun get(cell: Cell): E

    fun listColumn(column: Int): List<E> {
        val list = mutableListOf<E>()
        for (row in 0 until height) {
            list.add(get(row, column))
        }
        return list
    }

    fun listRow(row: Int): List<E> {
        val list = mutableListOf<E>()
        for (column in 0 until width) {
            list.add(get(row, column))
        }
        return list
    }

    fun findCell(value: E): Cell? {
        for (row in 0..3) {
            for (column in 0..3) {
                if (get(row, column) == 0) {
                    return Cell(row, column)
                }
            }
        }
        return null
    }

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)

    operator fun set(cell: Cell, value: E)
}


fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> = MatrixImpl(height, width, e)


class MatrixImpl<E>(override val height: Int, override val width: Int, default: E) : Matrix<E> {
    init {
        require(height > 0) { "Incorrect height = $height" }
        require(width > 0) { "Incorrect width = $width" }
    }

    private val info = List(height) { MutableList(width) { default } }

    override fun get(row: Int, column: Int): E = info[row][column]

    override fun get(cell: Cell): E = info[cell.row][cell.column]

    fun rowToList(row: Int): List<E> = info[row]

    override fun set(row: Int, column: Int, value: E) {
        info[row][column] = value
    }

    override fun set(cell: Cell, value: E) {
        set(cell.row, cell.column, value)
    }

    override fun equals(other: Any?) =
        other is MatrixImpl<*> &&
                height == other.height &&
                width == other.width &&
                info == other.info

    override fun hashCode(): Int = info.hashCode()

    override fun toString(): String = info.joinToString { it.joinToString() }
}

