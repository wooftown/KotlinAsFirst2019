@file:Suppress("UNUSED_PARAMETER")

package lesson3.task1

import lesson1.task1.sqr
import java.lang.Math.pow
import kotlin.math.*


/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result *= i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
    when {
        n == m -> 1
        n < 10 -> 0
        else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
    }

/**
 * Простая
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int {
    var res = 1
    var number = abs(n)
    while (number >= 10) {
        res++
        number /= 10
    }
    return res
}

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    var x = 0
    var y = 1
    var z: Int
    for (i in 1 until n) {
        z = x
        x += y
        y = z
    }
    return x + y
}


/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    val a = min(n, m)
    val b = max(n, m)
    var res = b
    while (res % a != 0) {
        res += b
    }
    return res
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    if (n % 2 == 0) {
        return 2
    }

    for (i in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % i == 0) {
            return i
        }
    }
    return n
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int = n / minDivisor(n)


/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
/* Функция для нахождения НОД */
fun largestCoDivisor(a: Int, b: Int): Int {
    var x = a
    var y = b
    while (x != y) {
        if (x > y) {
            x -= y
        } else {
            y -= x
        }
    }
    return x
}


fun isCoPrime(m: Int, n: Int): Boolean = largestCoDivisor(m, n) == 1

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
// в разработке
/*
fun squareBetweenExists(m: Int, n: Int): Boolean = (
        (sqr(sqrt(m.toDouble()).toInt()) == m) || (sqr(sqrt(n.toDouble()).toInt()) == n) ||
                (sqr(sqrt(n.toDouble()).toInt()) != sqr(sqrt(m.toDouble()).toInt()))
        )
*/
fun squareBetweenExists(m: Int, n: Int): Boolean = (
        (floor(sqrt(m.toDouble())) != floor(sqrt(n.toDouble()))) ||
                (ceil(sqrt(m.toDouble())) != ceil(sqrt(n.toDouble())))
                || (m == n && sqrt(m.toDouble()) == floor(sqrt(m.toDouble())))
        )

/**
 * Средняя
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int {
    var k = 0
    var a = x
    while (a != 1) {
        if (a % 2 == 0) {
            a /= 2
        } else {
            a = 3 * a + 1
        }
        k++
    }
    return k
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю.
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.sin и другие стандартные реализации функции синуса в этой задаче запрещается.
 */
fun sin(x: Double, eps: Double): Double {
    val x = x % (2 * PI)
    var sin = x;
    var k = 1
    var next = -x * (x * x) / ((k + 1) * (k + 2))
    while (abs(next) > abs(eps)) {
        sin += next
        k += 2
        next = -next * (x * x) / ((k + 1) * (k + 2))

    }
    return sin
}


/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.cos и другие стандартные реализации функции косинуса в этой задаче запрещается.
 */
fun cos(x: Double, eps: Double): Double {
    val x = x % (2 * PI)
    var cos = 1.0;
    var k = 0
    var next = -(x * x) / ((k + 1) * (k + 2))
    while (abs(next) > abs(eps)) {
        cos += next
        k += 2
        next = -next * (x * x) / ((k + 1) * (k + 2))

    }
    return cos
}
/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revert(n: Int): Int {
    var a = n
    var k = digitNumber(n)
    var x = 0
    for (i in 1..k) {
        k--
        x += ((a % 10) * pow(10.0, k.toDouble())).toInt()
        a /= 10
    }
    return x
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean = n == revert(n)

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean {
    var m = n
    val k = n % 10
    for (i in 1..digitNumber(n)) {
        if (m % 10 != k) {
            return true
        }
        m /= 10
    }
    return false
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun numberOfDigit(k: Int, n: Int): Int = k / pow(10.0, (n).toDouble()).toInt() % 10 // функция для нахождения цифры

fun squareSequenceDigit(n: Int): Int {
    var k = 0
    var i = 0
    var x = 1
    while (i < n) {
        k = x * x
        i += digitNumber(k)
        x++
    }
    return numberOfDigit(k, i - n)
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int {
    var k = 0
    var i = 0
    var x1 = 0
    var x2 = 1
    while (i < n) {
        k = x1 + x2
        i += digitNumber(k)
        x2 = x1
        x1 = k
    }
    return numberOfDigit(k, i - n)
}
