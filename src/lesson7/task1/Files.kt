@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import java.io.File
import java.util.*
import kotlin.math.max


/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                } else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val text = File(inputName).readText().toLowerCase()
    val res = mutableMapOf<String, Int>()
    for (key in substrings) {
        val lowKey = key.toLowerCase()
        var index = text.indexOf(lowKey, 0)
        res[key] = 0
        while (index != -1) {
            index = text.indexOf(lowKey, index + 1)
            res[key] = res[key]!! + 1
        }
    }
    return res
}


/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    val map = mapOf('ы' to 'и', 'Ы' to 'И', 'я' to 'а', 'Я' to 'А', 'ю' to 'у', 'Ю' to 'У')
    var prevChar = ' '
    File(outputName).bufferedWriter().use {
        for (i in File(inputName).readText()) {
            if ((i in map.keys) && (prevChar in "ЖжЧчШшЩщ")) {
                it.write(map[i].toString())
            } else {
                it.write(i.toString())
            }
            prevChar = i
        }
    }
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    val lines = File(inputName).readLines().map { it.trim() }
    val max = lines.map { it.length }.max() ?: 0
    File(outputName).bufferedWriter().use {
        for (line in lines) {
            it.write(" ".repeat((max - line.length) / 2) + line)
            it.newLine()
        }
    }
}

/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> {
    val map = mutableMapOf<String, Int>()
    val lowText = File(inputName).readText().toLowerCase()
    val list = lowText.split(Regex("""[^a-zа-яё]+""")).filter { it.isNotEmpty() }.toList()
    for (i in list.toSet()) {
        map[i] = list.count { it == i }
    }
    return map.toList().sortedByDescending { it.second }.take(20).toMap()
}

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    val lowMap = mutableMapOf<Char, String>()
    dictionary.forEach { (k, v) -> lowMap[k.toLowerCase()] = v.toLowerCase() }
    File(outputName).bufferedWriter().use {
        for (char in File(inputName).readText()) {
            val curChar = char.toLowerCase()
            if (curChar in lowMap.keys) {
                if (lowMap[curChar]!!.isNotEmpty()) {
                    if (char.isUpperCase()) {
                        it.write(
                            lowMap[curChar]!!.first().toUpperCase()
                                    + lowMap[curChar]!!.slice(1 until lowMap[curChar]!!.length)
                        )
                    } else {
                        it.write(lowMap[curChar]!!)
                    }
                }
            } else {
                it.write(char.toString())
            }
        }
    }
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый
 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *как же болят глаза...
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    val text = File(inputName).readLines().filter { it.toLowerCase().toSet().size == it.length }
    val max = text.map { it.length }.max() ?: -1
    File(outputName).writeText(text.filter { it.length == max }.joinToString(", "))
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * При решении этой и двух следующих задач полезно прочитать статью Википедии "Стек".
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,
Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun tagFindHelper(line: String, num: Int): String {
    when (line[num]) {
        '~' -> if (num < line.length - 1 && line[num + 1] == '~') return "s"
        '*' -> return if (num < line.length - 1 && line[num + 1] == '*') "b" else "i"
    }
    return "null"
}

fun tagWriteListChange(list: ArrayDeque<String>, tag: String): String {
    return if (tag in list) {
        list.remove(tag)
        ("</$tag>")
    } else {
        list.add(tag)
        ("<$tag>")
    }
}
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    val text = File(inputName).readLines().toMutableList()
    val out = File(outputName).bufferedWriter()
    val list = ArrayDeque<String>()
    out.write("<html><body><p>")
    while (text.isNotEmpty() && text.first() == "") {
        text.removeAt(0)
    }
    var paragraph = false
    for (line in text) {
        if (!paragraph && line.isEmpty()) {
            out.write("</p>")
            paragraph = true
        }
        var ind = 0
        while (ind < line.length) {
            if (paragraph) {
                out.write("<p>")
                paragraph = false
            }
            when (tagFindHelper(line, ind)) {
                "s" -> {
                    out.write(tagWriteListChange(list, "s"))
                    ind++
                }
                "b" -> {
                    if ("i" in list && "b" in list && list.indexOf("b") < list.indexOf("i")) {
                        out.write(tagWriteListChange(list, "i") + tagWriteListChange(list, "b"))
                        ind += 2
                    } else {
                        ind++
                        out.write(tagWriteListChange(list, "b"))
                    }
                }
                "i" -> {
                    out.write(tagWriteListChange(list, "i"))
                }
                "null" -> {
                    out.write(line[ind].toString())
                }
            }
            ind++
        }
    }
    if (!paragraph){
        out.write("</p>")
    }
    out.write("</body></html>")
    out.close()
}
/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>
Или колбаса
</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>
Фрукты
<ol>
<li>Бананы</li>
<li>
Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ol>
</li>
</ul>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    TODO()
}

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}


/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */

fun numberLength(x: Int): Int = x.toString().length

fun multiplicationList(x: Int, lhv: Int): List<Int> {
    var i = x
    val list = mutableListOf<Int>()
    while (i != 0) {
        list.add(i % 10 * lhv)
        i /= 10
    }
    return list
}

fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    val result = lhv * rhv
    val freePlace = numberLength(result) + 1
    val list = multiplicationList(rhv, lhv)
    File(outputName).bufferedWriter().use {
        it.write(" ".repeat(freePlace - numberLength(lhv)) + lhv)
        it.newLine()
        it.write("*" + " ".repeat(freePlace - numberLength(rhv) - 1) + rhv)
        it.newLine()
        it.write("-".repeat(freePlace))
        it.newLine()

        for (i in 0 until numberLength(rhv)) {
            val count = numberLength(list[i])
            if (i == 0) {
                it.write(" ".repeat(freePlace - count) + list[i])

            } else {
                it.write('+' + " ".repeat(freePlace - i - count - 1) + list[i])
            }
            it.newLine()
        }
        it.write("-".repeat(freePlace))
        it.newLine()
        it.write(" ".repeat(freePlace - numberLength(result)) + result)

        it.close()
    }
}


/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun numberToList(x: Int): List<Int> {
    val list = x.toString().toList()
    val res = mutableListOf<Int>()
    for (i in list) {
        res.add(i.toString().toInt())
    }
    return res
}

fun firstDivisor(x: Int, y: Int): Int {
    var a = 0
    val list = numberToList(x)
    for (element in list) {
        a = a * 10 + element
        if (a / y > 0) {
            return a
        }
    }
    return a
}

fun divisionList(x: Int, y: Int): List<Pair<Int, Int>> {
    val first = firstDivisor(x, y)
    val list = mutableListOf<Pair<Int, Int>>()
    val numberList = numberToList(x)
    var i = numberLength(first)
    list.add(first to (first - first % y))
    var a = list.last().first - list.last().second
    while (i != numberLength(x)) {
        a = a * 10 + numberList[i]
        list.add(a to (a - a % y))
        a %= y
        i++
    }
    return list
}

fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    val list = divisionList(lhv, rhv)
    var divisible = list[0].first
    var deducted = list[0].second
    var spaces = if (numberLength(divisible) == numberLength(deducted)) 1 else 0
    var maxLength = max(numberLength(divisible), numberLength(deducted) + 1)
    var res = divisible - deducted
    File(outputName).bufferedWriter().use {
        it.write(" ".repeat(spaces) + "$lhv | $rhv\n")
        it.write(
            " ".repeat(maxLength - numberLength(deducted) - 1) + "-" + deducted +
                    " ".repeat(numberLength(lhv) + 3 + spaces - maxLength) + lhv / rhv
        )
        it.newLine()
        it.write("-".repeat(maxLength))
        it.newLine()
        spaces = maxLength
        for (i in 1 until list.size) {
            spaces++
            divisible = list[i].first
            deducted = list[i].second
            maxLength = max(numberLength(divisible), numberLength(deducted) + 1)
            if (res == 0) {
                it.write(" ".repeat(spaces - numberLength(divisible) - 1) + "0" + divisible)
            } else {
                it.write(" ".repeat(spaces - numberLength(divisible)) + divisible)
            }
            it.newLine()
            it.write(" ".repeat(spaces - numberLength(deducted) - 1) + "-" + deducted)
            it.newLine()
            it.write(" ".repeat(spaces - maxLength) + "-".repeat(maxLength))
            it.newLine()
            res = divisible - deducted
        }
        it.write(" ".repeat(spaces - numberLength(lhv % rhv)) + lhv % rhv)
        it.close()
    }
}

