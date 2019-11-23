@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import kotlinx.html.InputType
import lesson2.task2.daysInMonth
import java.lang.Character.isDigit

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main() {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */

val months = listOf(
    "",
    "января",
    "февраля",
    "марта",
    "апреля",
    "мая",
    "июня",
    "июля",
    "августа",
    "сентября",
    "октября",
    "ноября",
    "декабря"
)

fun dateStrToDigit(str: String): String {
    val parts = str.split(" ")
    if (parts.size != 3) {
        return ""
    }
    val day = parts[0].toIntOrNull()
    val year = parts[2].toIntOrNull()
    val month = months.indexOf(parts[1])
    if ((day == null) || (year == null) || (day > daysInMonth(month, year)) || (month !in 1..12)) {
        return ""
    }
    return String.format("%02d.%02d.%d", day, month, year)
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    val parts = digital.split(".")
    if (parts.size != 3) {
        return ""
    }
    val day = parts[0].toIntOrNull()
    val year = parts[2].toIntOrNull()
    if ((day == null) || (year == null) || (parts[1].toInt() !in 1..12) ||
        (day > daysInMonth(parts[1].toInt(), year))) {
        return ""
    }
    val month = months[parts[1].toInt()]
    return String.format("%d %s %d", day, month, year)
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 * если использовать .filter { it != ' ' } то работает дольше
 * Submission #17783
 */
fun flattenPhoneNumber(phone: String): String {
    val validSet = setOf('-', '(', ')', '+', ' ')
    if ((phone.toSet() - validSet).isEmpty()) {
        return ""
    }
    var isOpened = false
    var isFilled = false
    val res = mutableListOf<Char>()
    for (i in phone) {
        if ((i !in validSet) && (!i.isDigit())) return ""
        if (i.isDigit()) {
            res.add(i)
            if (isOpened) {
                isFilled = true
            }
        }
        if (i == '+') {
            if (res.size == 0) res.add(i) else return ""
        }
        if (i == '(') {
            if (!isOpened) isOpened = true else return ""
        }
        if (i == ')') {
            if (isOpened && isFilled) {
                isOpened = false
                isFilled = false
            } else {
                return ""
            }
        }
    }
    return res.joinToString("")
}


/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    if (!jumps.matches(Regex("""((\d+|[-%]) )*(\d+|[-%])"""))) {
        return -1
    }
    return Regex("""\d+""").findAll(jumps).map { it.value.toInt() }.max() ?: -1
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 * (\d+ [%+-]+ )*(\d+ [%+-]+)
 */
fun bestHighJump(jumps: String): Int {
    if (!jumps.matches(Regex("""(\d+ [%+-]+ )*(\d+ [%+-]+)"""))) {
        return -1
    }
    val list = jumps.split(' ')
    for (i in (list.size - 1) downTo 1 step 2) {
        if ("+" in list[i]) {
            return list[i - 1].toInt()
        }
    }
    return -1
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 * (\d+ [+-] )*\d+
 */
fun plusMinus(expression: String): Int {
    require(expression.matches(Regex("""(\d+ [+-] )*\d+"""))) { "Incorrect expression" }
    val parts = expression.split(' ')
    var res = parts[0].toInt()
    for (x in 1 until parts.size - 1 step 2) {
        if (parts[x] == "+") {
            res += parts[x + 1].toInt()
        } else {
            res -= parts[x + 1].toInt()
        }
    }
    return res
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    var index = -1
    var previous = ""
    for (word in str.split(' ')) {
        if (word.toLowerCase() == previous.toLowerCase()) {
            return index
        }
        index += previous.length
        previous = word
        index++
    }
    return -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 * ([^\s;]+ \d*.?\d+; )+
 * ([^\s;]+ (\d*.?\d+; |\d*.?\d+))+
 */
fun mostExpensive(description: String): String {
    if (!description.matches(Regex("""([^\s;]+ \d*.?\d+; )*([^\s;]+ \d*.?\d+)"""))) {
        return ""
    }
    val list = description.split("; ")
    var res = ""
    var max = -0.1
    for (i in list) {
        val nextList = i.split(" ")
        if (nextList[1].toDouble() > max) {
            max = nextList[1].toDouble()
            res = nextList[0]
        }
    }
    return res
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    if ((!roman.matches(Regex("""^M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$"""))) || (roman == "")) {
        return -1
    }
    val romanToInt = mapOf(
        "I" to 1,
        "IV" to 4,
        "V" to 5,
        "IX" to 9,
        "X" to 10,
        "XL" to 40,
        "L" to 50,
        "XC" to 90,
        "C" to 100,
        "CD" to 400,
        "D" to 500,
        "CM" to 900,
        "M" to 1000
    )
    val subtractionList = listOf(
        "IV", "IX", "XL", "XC", "CD", "CM"
    )
    var result = 0
    var index = 0
    while (index != roman.length) {
        val oneSymbol = roman.substring(index, index + 1)
        val twoSymbols = if (index + 2 <= roman.length) {
            roman.substring(index, index + 2)
        } else {
            ""
        }
        if (twoSymbols in subtractionList) {
            result += romanToInt.getValue(twoSymbols)
            index += 2
        } else {
            result += romanToInt.getValue(oneSymbol)
            index++
        }
    }
    return result
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *[ +-><\[\]]+
 * [ +-<>\[\]]+
 */

fun closedBracket(beginning: Int, commands: String): Int {
    var bracket = 0
    for (i in beginning until commands.length) {
        if (commands[i] == '[')
            bracket++
        if (commands[i] == ']')
            bracket--

        if (bracket == 0) return i
    }
    return -1
}

fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    require(commands.matches(Regex("""[ +-<>\[\]]+|^$"""))) { "Incorrect commands" }
    var bracket = 0
    for (i in commands.filter { it in "[]" }) {
        if (i == '[') {
            bracket++
        } else {
            bracket--
        }
        require(bracket >= 0) { "Incorrect brackets sequence in commands list" }
    }
    require(bracket == 0) { "Incorrect brackets sequence in commands list" }
    val bracketsMap = mutableMapOf<Int, Int>()
    for (i in commands.indices) {
        if (commands[i] == '[') {
            val now = closedBracket(i, commands)
            bracketsMap[i] = now
            bracketsMap[now] = i
        }
    }
    val cellsList = MutableList(cells) { 0 }
    var position = cells / 2
    var commandsPassed = 0
    var commandNow = 0
    while ((commandsPassed < limit) && (commandNow < commands.length)) {
        when (commands[commandNow]) {
            '+' -> cellsList[position]++
            '-' -> cellsList[position]--
            '>' -> position++
            '<' -> position--
            '[' -> if (cellsList[position] == 0) commandNow = bracketsMap[commandNow]!!
            ']' -> if (cellsList[position] != 0) commandNow = bracketsMap[commandNow]!!
        }
        check(position in 0 until cells) { "Tracker went out from cells" }
        commandNow++
        commandsPassed++
    }
    return cellsList
}
