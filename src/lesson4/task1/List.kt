@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import lesson1.task1.sqr
import java.lang.Math.*
import kotlin.math.sqrt

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
    when {
        y < 0 -> listOf()
        y == 0.0 -> listOf(0.0)
        else -> {
            val root = sqrt(y)
            // Результат!
            listOf(-root, root)
        }
    }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double = sqrt(v.fold(0.0) { previousResult, element ->
    previousResult + sqr(element)
})


/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double =
    if (list.isNotEmpty()) list.sum() / list.size
    else 0.0


/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    val x = mean(list)
    for (i in 0 until list.size) {
        list[i] -= x
    }
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.
 */
fun times(a: List<Int>, b: List<Int>): Int {
    var res = 0
    for (i in 0 until a.size) {
        res += a[i] * b[i]
    }
    return res
}

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0 при любом x.
 */
fun polynom(p: List<Int>, x: Int): Int {
    var res = 0
    for (i in 0 until p.size) {
        res += p[i] * pow(x.toDouble(), i.toDouble()).toInt()
    }
    return res
}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Int>): MutableList<Int> {
    var sum = 0
    for (i in 0 until list.size) {
        list[i] += sum
        sum = list[i]
    }

    return list
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    var x = n
    var last = 0
    val list = mutableListOf<Int>()
    while (x > 1) {
        for (i in 2..x) {
            if (x % i == 0) {
                last = i
                break
            }
        }
        x /= last
        list.add(last)
    }
    return list
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String = factorize(n).joinToString(separator = "*")

/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    val list = mutableListOf<Int>()
    var x = n
    if (x == 0) {
        list.add(0)
    }
    while (x > 0) {
        list.add(x % base)
        x -= x % base
        x /= base

    }
    return list.reversed()
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, n.toString(base) и подобные), запрещается.
 */
fun convertToString(n: Int, base: Int): String {
    var str = ""
    val list = convert(n, base)
    for (i in 0 until list.size) {
        if (list[i] < 10) {
            str += list[i]
        } else {
            str += (list[i] - 10 + 'a'.toInt()).toChar()
        }
    }
    return str
}

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var res = 0
    for (i in 0 until digits.size) {
        res *= base
        res += digits[i]
    }
    return res
}


/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, str.toInt(base)), запрещается.
 */


fun decimalFromString(str: String, base: Int): Int {
    var res = 0
    var x: Int
    for (i in 0 until str.length) {
        x = if (str[i] in 'a'..'z') {
            10 + (str[i] - 'a')
        } else {
            (str[i] - '0')
        }
        res *= base
        res += x
    }
    return res
}

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String {
    var res = ""
    when (n / 1000) {
        1 -> res += "M"
        2 -> res += "MM"
        3 -> res += "MMM"
    }
    when (n / 100 % 10) {
        1 -> res += "C"
        2 -> res += "CC"
        3 -> res += "CCC"
        4 -> res += "CD"
        5 -> res += "D"
        6 -> res += "DC"
        7 -> res += "DCC"
        8 -> res += "DCCC"
        9 -> res += "CM"
    }
    when (n / 10 % 10) {
        1 -> res += "X"
        2 -> res += "XX"
        3 -> res += "XXX"
        4 -> res += "XL"
        5 -> res += "L"
        6 -> res += "LX"
        7 -> res += "LXX"
        8 -> res += "LXXX"
        9 -> res += "XC"
    }
    when (n % 10) {
        1 -> res += "I"
        2 -> res += "II"
        3 -> res += "III"
        4 -> res += "IV"
        5 -> res += "V"
        6 -> res += "VI"
        7 -> res += "VII"
        8 -> res += "VIII"
        9 -> res += "IX"
    }
    return res
}

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */

fun hundreds(n: Int): String = when (n / 100) {
    1 -> "сто "
    2 -> "двести "
    3 -> "триста "
    4 -> "четыреста "
    5 -> "пятьсот "
    6 -> "шестьсот "
    7 -> "семьсот "
    8 -> "восемьсот "
    9 -> "девятьсот "
    else -> ""
}

fun teens(n: Int): String = when (n % 10) {
    1 -> "одиннадцать "
    2 -> "двенадцать "
    3 -> "тринадцать "
    4 -> "четырнадцать "
    5 -> "пятнадцать "
    6 -> "шестнадцать "
    7 -> "семнадцать "
    8 -> "восемнадцать "
    else -> "девятнадцать "
}

fun tensAndUnits(n: Int, flag: Int): String {
    var res = ""
    when (n / 10 % 10) {
        1 -> res += "десять "
        2 -> res += "двадцать "
        3 -> res += "тридцать "
        4 -> res += "сорок "
        5 -> res += "пятьдесят "
        6 -> res += "шестьдесят "
        7 -> res += "семьдесят "
        8 -> res += "восемьдесят "
        9 -> res += "девяносто "
    }
    when (n % 10) {
        1 -> res += if (flag == 0) {
            "одна "
        } else {
            "один "
        }
        2 -> res += if (flag == 0) {
            "две "
        } else {
            "два "
        }
        3 -> res += "три "
        4 -> res += "четыре "
        5 -> res += "пять "
        6 -> res += "шесть "
        7 -> res += "семь "
        8 -> res += "восемь "
        9 -> res += "девять "
    }
    return res
}

fun russian(n: Int): String {
    var res = ""
    val n1 = n / 1000
    val n2 = n % 1000

    res += hundreds(n1)
    res += if (n1 % 100 in 11..19) {
        teens(n1)
    } else {
        tensAndUnits(n1, 0)
    }

    if ((n1 % 10 == 1) && (n1 % 100 !in 11..19)) {
        res += "тысяча "
    } else {
        if ((n1 % 10 in 2..4) && (n1 % 100 !in 11..19)) {
            res += "тысячи "
        } else {
            if (n1 > 0) {
                res += "тысяч "
            }
        }
    }

    res += hundreds(n2)
    res += if (n2 % 100 in 11..19) {
        teens(n2)
    } else {
        tensAndUnits(n2, 1)
    }
    return res.substring(0, res.length - 1)
}