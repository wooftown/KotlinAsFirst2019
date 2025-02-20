@file:Suppress("UNUSED_PARAMETER")

package lesson8.task1

import lesson1.task1.sqr
import kotlin.math.*

data class Point(val x: Double, val y: Double) {

    fun distance(other: Point): Double = sqrt(sqr(x - other.x) + sqr(y - other.y))
}

/**
 * Треугольник, заданный тремя точками (a, b, c, см. constructor ниже).
 * Эти три точки хранятся в множестве points, их порядок не имеет значения.
 */
@Suppress("MemberVisibilityCanBePrivate")
class Triangle private constructor(private val points: Set<Point>) {

    private val pointList = points.toList()

    val a: Point get() = pointList[0]

    val b: Point get() = pointList[1]

    val c: Point get() = pointList[2]

    constructor(a: Point, b: Point, c: Point) : this(linkedSetOf(a, b, c) as Set<Point>)

    fun halfPerimeter() = (a.distance(b) + b.distance(c) + c.distance(a)) / 2.0

    fun area(): Double {
        val p = halfPerimeter()
        return sqrt(p * (p - a.distance(b)) * (p - b.distance(c)) * (p - c.distance(a)))
    }

    fun contains(p: Point): Boolean {
        val abp = Triangle(a, b, p)
        val bcp = Triangle(b, c, p)
        val cap = Triangle(c, a, p)
        return abp.area() + bcp.area() + cap.area() <= area()
    }

    override fun equals(other: Any?) = other is Triangle && points == other.points

    override fun hashCode() = points.hashCode()

    override fun toString() = "Triangle(a = $a, b = $b, c = $c)"
}


data class Circle(val center: Point, val radius: Double) {
    /**
     * Рассчитать расстояние между двумя окружностями.
     * Расстояние между непересекающимися окружностями рассчитывается как
     * расстояние между их центрами минус сумма их радиусов.
     * Расстояние между пересекающимися окружностями считать равным 0.0.
     */
    fun distance(other: Circle): Double = maxOf(center.distance(other.center) - radius - other.radius, 0.0)

    /**
     * Вернуть true, если и только если окружность содержит данную точку НА себе или ВНУТРИ себя
     */
    fun contains(p: Point): Boolean = center.distance(p) - radius <= 0.0
}

/**
 * Отрезок между двумя точками
 */
data class Segment(val begin: Point, val end: Point) {
    override fun equals(other: Any?) =
        other is Segment && (begin == other.begin && end == other.end || end == other.begin && begin == other.end)

    override fun hashCode() =
        begin.hashCode() + end.hashCode()

    fun midPoint(): Point = Point(((begin.x + end.x) / 2), ((begin.y + end.y) / 2))
}

/**
 * Дано множество точек. Вернуть отрезок, соединяющий две наиболее удалённые из них.
 * Если в множестве менее двух точек, бросить IllegalArgumentException
 */
fun diameter(vararg points: Point): Segment {
    val pointsList = points.distinct()
    require(pointsList.size > 1)
    var maxPair = pointsList[0] to pointsList[0]
    for (i in pointsList.indices) {
        for (j in i + 1 until pointsList.size) {
            if (pointsList[i].distance(pointsList[j]) > maxPair.first.distance(maxPair.second)) {
                maxPair = pointsList[i] to pointsList[j]
            }
        }
    }
    return Segment(maxPair.first, maxPair.second)
}

/**
 * Построить окружность по её диаметру, заданному двумя точками
 * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними
 */
fun circleByDiameter(diameter: Segment): Circle =
    Circle(diameter.midPoint(), diameter.midPoint().distance(diameter.begin))

/**
 * Прямая, заданная точкой point и углом наклона angle (в радианах) по отношению к оси X.
 * Уравнение прямой: (y - point.y) * cos(angle) = (x - point.x) * sin(angle)
 * или: y * cos(angle) = x * sin(angle) + b, где b = point.y * cos(angle) - point.x * sin(angle).
 * Угол наклона обязан находиться в диапазоне от 0 (включительно) до PI (исключительно).
 */
class Line private constructor(val b: Double, val angle: Double) {
    init {
        require(angle >= 0 && angle < PI) { "Incorrect line angle: $angle" }
    }

    constructor(point: Point, angle: Double) : this(point.y * cos(angle) - point.x * sin(angle), angle)

    /**
     * Найти точку пересечения с другой линией.
     * Для этого необходимо составить и решить систему из двух уравнений (каждое для своей прямой)
     */
    fun crossPoint(other: Line): Point {
        if (angle == other.angle) {
            throw ArithmeticException("parallel lines have no intersection point")
        }
        val x = (other.b * cos(angle) - b * cos(other.angle)) / sin(angle - other.angle)
        val y = (b * sin(other.angle) - other.b * sin(angle)) / sin(other.angle - angle)
        return Point(x, y)
    }

    override fun equals(other: Any?) = other is Line && angle == other.angle && b == other.b

    override fun hashCode(): Int {
        var result = b.hashCode()
        result = 31 * result + angle.hashCode()
        return result
    }

    override fun toString() = "Line(${cos(angle)} * y = ${sin(angle)} * x + $b)"
}

/**
 * Построить прямую по отрезку
 */
fun lineBySegment(s: Segment): Line = lineByPoints(s.begin, s.end)

/**
 * Построить прямую по двум точкам
 */
fun lineByPoints(a: Point, b: Point): Line {
    val tg =
        (a.y - b.y) / (a.x - b.x)
    return if (tg >= 0) {
        Line(a, atan(tg))
    } else {
        Line(a, (PI - atan(-tg)) % PI)
    }

}

/**
 * Построить серединный перпендикуляр по отрезку или по двум точкам
 */
fun bisectorByPoints(a: Point, b: Point): Line {
    val line = lineByPoints(a, b)
    return Line(Segment(a, b).midPoint(), (line.angle + PI / 2) % PI)
}

/**
 * Задан список из n окружностей на плоскости. Найти пару наименее удалённых из них.
 * Если в списке менее двух окружностей, бросить IllegalArgumentException
 */
fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle> {
    val list = circles.distinct()
    require(list.size > 1) { "not enough points" }
    var minDistance = list[0].distance(list[1])
    var circlePair = list[0] to list[1]
    for (i in list.indices) {
        for (j in i + 1 until list.size) {
            val distance = list[i].distance(list[j])
            if (distance < minDistance) {
                minDistance = distance
                circlePair = list[i] to list[j]
            }
            if (minDistance == 0.0) return circlePair
        }
    }
    return circlePair
}

/**
 * Дано три различные точки. Построить окружность, проходящую через них
 * (все три точки должны лежать НА, а не ВНУТРИ, окружности).
 * Описание алгоритмов см. в Интернете
 * (построить окружность по трём точкам, или
 * построить окружность, описанную вокруг треугольника - эквивалентная задача).
 *
 * Центр описанной окружности лежит на пересечении серединных перпендикуляров к сторонам треугольника
 * r = abc/4s
 */
fun circleByThreePoints(a: Point, b: Point, c: Point): Circle {
    val radius = a.distance(b) * a.distance(c) * b.distance(c) / (4 * Triangle(a, b, c).area())
    val center = bisectorByPoints(a, b).crossPoint(bisectorByPoints(b, c))
    return Circle(center, radius)
}

/**
 * Дано множество точек на плоскости. Найти круг минимального радиуса,
 * содержащий все эти точки. Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит одну точку, вернуть круг нулевого радиуса с центром в данной точке.
 *
 * Примечание: в зависимости от ситуации, такая окружность может либо проходить через какие-либо
 * три точки данного множества, либо иметь своим диаметром отрезок,
 * соединяющий две самые удалённые точки в данном множестве.
 * меньший угол
 */

fun minContainingCircle(vararg points: Point): Circle {
    val pointsList = points.distinct()
    require(pointsList.isNotEmpty()) { "at least one point is required" }
    if (pointsList.size == 1) return Circle(pointsList.first(), 0.0)
    val diameter = diameter(*points)
    val a = diameter.begin
    val b = diameter.end
    val resultCircle = circleByDiameter(diameter)
    val c = pointsList.filter { it != a && it != b }.maxBy { it.distance(resultCircle.center) }
    return if (!resultCircle.contains(c ?: a)) {
        circleWithThreePoints(a, b, c!!)
    } else {
        resultCircle
    }
}

fun circleWithThreePoints(a: Point, b: Point, c: Point): Circle =
    if (Triangle(a, b, c).area() == 0.0) {
        circleByDiameter(diameter(a, b, c))
    } else {
        val radius = a.distance(b) * a.distance(c) * b.distance(c) / (4 * Triangle(a, b, c).area())
        Circle(bisectorByPoints(a, b).crossPoint(bisectorByPoints(b, c)), radius)
    }

