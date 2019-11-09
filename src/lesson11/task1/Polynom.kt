@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

import kotlin.math.max

/**
 * Класс "полином с вещественными коэффициентами".
 *
 * Общая сложность задания -- сложная.
 * Объект класса -- полином от одной переменной (x) вида 7x^4+3x^3-6x^2+x-8.
 * Количество слагаемых неограничено.
 *
 * Полиномы можно складывать -- (x^2+3x+2) + (x^3-2x^2-x+4) = x^3-x^2+2x+6,
 * вычитать -- (x^3-2x^2-x+4) - (x^2+3x+2) = x^3-3x^2-4x+2,
 * умножать -- (x^2+3x+2) * (x^3-2x^2-x+4) = x^5+x^4-5x^3-3x^2+10x+8,
 * делить с остатком -- (x^3-2x^2-x+4) / (x^2+3x+2) = x-5, остаток 12x+16
 * вычислять значение при заданном x: при x=5 (x^2+3x+2) = 42.
 *
 * В конструктор полинома передаются его коэффициенты, начиная со старшего.
 * Нули в середине и в конце пропускаться не должны, например: x^3+2x+1 --> Polynom(1.0, 2.0, 0.0, 1.0)
 * Старшие коэффициенты, равные нулю, игнорировать, например Polynom(0.0, 0.0, 5.0, 3.0) соответствует 5x+3
 */


class Polynom(vararg coeffs: Double) {
    private val data = coeffs.toList().reversed().dropLastWhile { it == 0.0 }

    constructor(list: List<Double>) : this(*list.toDoubleArray())

    /**
     * Геттер: вернуть значение коэффициента при x^i
     */
    fun coeff(i: Int): Double = data[i]

    /**
     * Расчёт значения при заданном x
     */
    fun getValue(x: Double): Double {
        var k = 1.0
        var result = 0.0
        for (i in data) {
            result += i * k
            k *= x
        }
        return result
    }

    /**
     * Степень (максимальная степень x при ненулевом слагаемом, например 2 для x^2+x+1).
     *
     * Степень полинома с нулевыми коэффициентами считать равной 0.
     * Слагаемые с нулевыми коэффициентами игнорировать, т.е.
     * степень 0x^2+0x+2 также равна 0.
     */
    fun degree(): Int {
        for (i in data.indices.reversed()) {
            if (data[i] != 0.0) return i
        }
        return 0
    }

    fun copy() = Polynom(data.reversed())

    /**
     * Сложение
     */
    operator fun plus(other: Polynom): Polynom {
        val result = MutableList(max(data.size, other.data.size)) { 0.0 }
        for (i in result.indices) {
            if (i in this.data.indices) {
                result[i] += this.data[i]
            }
            if (i in other.data.indices) {
                result[i] += other.data[i]
            }
        }
        return Polynom(result.reversed())
    }

    /**
     * Смена знака (при всех слагаемых)
     */
    operator fun unaryMinus(): Polynom = Polynom(data.map { -it }.reversed())

    /**
     * Вычитание
     */
    operator fun minus(other: Polynom): Polynom {
        val result = MutableList(max(data.size, other.data.size)) { 0.0 }
        for (i in result.indices) {
            if (i in this.data.indices) {
                result[i] += this.data[i]
            }
            if (i in other.data.indices) {
                result[i] -= other.data[i]
            }
        }
        return Polynom(result.reversed())
    }

    /**
     * Умножение
     */
    operator fun times(other: Polynom): Polynom {
        val result = MutableList(data.size + other.data.size) { 0.0 }
        for (i in data.indices) {
            for (j in other.data.indices) {
                result[i + j] += data[i] * other.data[j]
            }
        }
        return Polynom(result.reversed())
    }

    /**
     * Деление
     *
     * Про операции деления и взятия остатка см. статью Википедии
     * "Деление многочленов столбиком". Основные свойства:
     *
     * Если A / B = C и A % B = D, то A = B * C + D и степень D меньше степени B
     */
    operator fun div(other: Polynom): Polynom {
        val result = mutableListOf<Double>()
        var divisible = this.copy()
        val divider = Polynom(other.data.dropLastWhile { it == 0.0 }.reversed())
        var i = divisible.data.size - divider.data.size
        while (i >= 0) {
            val x = divisible.data[i + divider.data.size - 1] / divider.data.last()
            result.add(x)
            val newData = List(i + 1) { 0.0 }.toMutableList()
            newData[newData.size - 1] = x
            val poly = Polynom(newData.reversed()) * divider
            divisible -= poly
            i--
        }
        return Polynom(result)
    }

    /**
     * Взятие остатка
     */
    operator fun rem(other: Polynom): Polynom = this - (this / other) * other

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean = other is Polynom && data == other.data

    /**
     * Получение хеш-кода
     */
    override fun hashCode(): Int = data.hashCode()
}
