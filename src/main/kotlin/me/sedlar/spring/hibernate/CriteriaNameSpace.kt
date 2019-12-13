package me.sedlar.spring.hibernate

import org.hibernate.SessionFactory
import java.math.BigDecimal
import java.math.BigInteger
import java.sql.Date
import java.sql.Time
import java.sql.Timestamp
import javax.persistence.criteria.*
import javax.persistence.criteria.CriteriaBuilder.*
import javax.persistence.metamodel.EntityType

class CriteriaNameSpace<T> {

    lateinit var query: CriteriaQuery<T>
    lateinit var builder: CriteriaBuilder
    lateinit var model: Root<T>

    val queryOrderList: List<Order>
        get() = getOrderList()

    val queryParameters: Set<ParameterExpression<*>>
        get() = getParameters()

    val rootModel: EntityType<T>
        get() = getModel()

    // START CriteriaQuery functions

    fun select(selection: Selection<out T>): CriteriaQuery<T> = query.select(selection)
    fun select(): CriteriaQuery<T> = select(model)
    fun multiselect(vararg selections: Selection<*>): CriteriaQuery<T> = query.multiselect(*selections)
    fun multiselect(selectionList: List<Selection<*>>): CriteriaQuery<T> = query.multiselect(selectionList)
    fun where(restriction: Expression<Boolean>): CriteriaQuery<T> = query.where(restriction)
    fun where(vararg restrictions: Predicate): CriteriaQuery<T> = query.where(*restrictions)
    fun groupBy(vararg grouping: Expression<*>): CriteriaQuery<T> = query.groupBy(*grouping)
    fun groupBy(grouping: List<Expression<*>>): CriteriaQuery<T> = query.groupBy(grouping)
    fun having(restriction: Expression<Boolean>): CriteriaQuery<T> = query.having(restriction)
    fun having(vararg restrictions: Predicate): CriteriaQuery<T> = query.having(*restrictions)
    fun orderBy(vararg o: Order): CriteriaQuery<T> = query.orderBy(*o)
    fun orderBy(o: List<Order>): CriteriaQuery<T> = query.orderBy(o)
    fun distinct(distinct: Boolean): CriteriaQuery<T> = query.distinct(distinct)
    fun getOrderList(): List<Order> = query.orderList
    fun getParameters(): Set<ParameterExpression<*>> = query.parameters

    // END CriteriaQuery functions

    // START CriteriaBuilder functions

    // ordering:

    fun asc(x: Expression<*>): Order = builder.asc(x)
    fun desc(x: Expression<*>): Order = builder.desc(x)

    // aggregate functions:

    fun <N : Number> avg(x: Expression<N>): Expression<Double> = builder.avg(x)
    fun <N : Number> sum(x: Expression<N>): Expression<N> = builder.sum(x)
    fun sumAsLong(x: Expression<Int>): Expression<Long> = builder.sumAsLong(x)
    fun sumAsDouble(x: Expression<Float>): Expression<Double> = builder.sumAsDouble(x)
    fun <N : Number> max(x: Expression<N>): Expression<N> = builder.max(x)
    fun <N : Number> min(x: Expression<N>): Expression<N> = builder.min(x)
    fun <X : Comparable<X>> greatest(x: Expression<X>): Expression<X> = builder.greatest(x)
    fun <X : Comparable<X>> least(x: Expression<X>): Expression<X> = builder.least(x)
    fun count(x: Expression<*>): Expression<Long> = builder.count(x)
    fun countDistinct(x: Expression<*>): Expression<Long> = builder.countDistinct(x)

    // subqueries:

    fun exists(subquery: Subquery<*>): Predicate = builder.exists(subquery)
    fun <Y> all(subquery: Subquery<Y>): Expression<Y> = builder.all(subquery)
    fun <Y> some(subquery: Subquery<Y>): Expression<Y> = builder.some(subquery)
    fun <Y> any(subquery: Subquery<Y>): Expression<Y> = builder.any(subquery)

    // boolean functions:

    fun and(x: Expression<Boolean>, y: Expression<Boolean>): Predicate = builder.and(x, y)
    fun and(vararg restrictions: Predicate): Predicate = builder.and(*restrictions)
    fun or(x: Expression<Boolean>, y: Expression<Boolean>): Predicate = builder.or(x, y)
    fun or(vararg predicates: Predicate) = builder.or(*predicates)
    fun not(restriction: Expression<Boolean>): Predicate = builder.not(restriction)
    fun conjunction(): Predicate = builder.conjunction()
    fun disjunction(): Predicate = builder.disjunction()

    // turn Expression<Boolean> into a Predicate
    // useful for use with varargs methods

    fun isTrue(x: Expression<Boolean>): Predicate = builder.isTrue(x)
    fun isFalse(x: Expression<Boolean>): Predicate = builder.isFalse(x)

    // null tests:

    fun isNull(x: Expression<*>): Predicate = builder.isNull(x)
    fun isNotNull(x: Expression<*>): Predicate = builder.isNotNull(x)

    // equality:

    fun equal(x: Expression<*>, y: Expression<*>): Predicate = builder.equal(x, y)
    fun equal(x: Expression<*>, y: Any): Predicate = builder.equal(x, y)
    fun notEqual(x: Expression<*>, y: Expression<*>): Predicate = builder.notEqual(x, y)
    fun notEqual(x: Expression<*>, y: Any): Predicate = builder.notEqual(x, y)

    // comparisons for generic (non-numeric) operands:

    fun <Y : Comparable<Y>> greaterThan(x: Expression<out Y>, y: Expression<out Y>): Predicate = builder.greaterThan(x, y)
    fun <Y : Comparable<Y>> greaterThan(x: Expression<out Y>, y: Y): Predicate = builder.greaterThan(x, y)
    fun <Y : Comparable<Y>> greaterThanOrEqualTo(x: Expression<out Y>, y: Expression<out Y>): Predicate = builder.greaterThanOrEqualTo(x, y)
    fun <Y : Comparable<Y>> greaterThanOrEqualTo(x: Expression<out Y>, y: Y): Predicate = builder.greaterThanOrEqualTo(x, y)
    fun <Y : Comparable<Y>> lessThan(x: Expression<out Y>, y: Expression<out Y>): Predicate = builder.lessThan(x, y)
    fun <Y : Comparable<Y>> lessThan(x: Expression<out Y>, y: Y): Predicate = builder.lessThan(x, y)
    fun <Y : Comparable<Y>> lessThanOrEqualTo(x: Expression<out Y>, y: Expression<out Y>): Predicate = builder.lessThanOrEqualTo(x, y)
    fun <Y : Comparable<Y>> lessThanOrEqualTo(x: Expression<out Y>, y: Y): Predicate = builder.lessThanOrEqualTo(x, y)
    fun <Y : Comparable<Y>> between(v: Expression<out Y>, x: Expression<out Y>, y: Expression<out Y>): Predicate = builder.between(v, x, y)
    fun <Y : Comparable<Y>> between(v: Expression<out Y>, x: Y, y: Y): Predicate = builder.between(v, x, y)

    // comparisons for numeric operands:

    fun gt(x: Expression<out Number>, y: Expression<out Number>): Predicate = builder.gt(x, y)
    fun gt(x: Expression<out Number>, y: Number): Predicate = builder.gt(x, y)
    fun ge(x: Expression<out Number>, y: Expression<out Number>): Predicate = builder.ge(x, y)
    fun ge(x: Expression<out Number>, y: Number): Predicate = builder.ge(x, y)
    fun lt(x: Expression<out Number>, y: Expression<out Number>): Predicate = builder.lt(x, y)
    fun lt(x: Expression<out Number>, y: Number): Predicate = builder.lt(x, y)
    fun le(x: Expression<out Number>, y: Expression<out Number>): Predicate = builder.le(x, y)
    fun le(x: Expression<out Number>, y: Number): Predicate = builder.le(x, y)

    // numerical operations:

    fun <N : Number> neg(x: Expression<N>): Expression<N> = builder.neg(x)
    fun <N : Number> abs(x: Expression<N>): Expression<N> = builder.abs(x)
    fun <N : Number> sum(x: Expression<out N>, y: Expression<out N>): Expression<N> = builder.sum(x, y)
    fun <N : Number> sum(x: Expression<out N>, y: N): Expression<N> = builder.sum(x, y)
    fun <N : Number> sum(x: N, y: Expression<out N>): Expression<N> = builder.sum(x, y)
    fun <N : Number> prod(x: Expression<out N>, y: Expression<out N>): Expression<N> = builder.prod(x, y)
    fun <N : Number> prod(x: Expression<out N>, y: N): Expression<N> = builder.prod(x, y)
    fun <N : Number> prod(x: N, y: Expression<out N>): Expression<N> = builder.prod(x, y)
    fun <N : Number> diff(x: Expression<out N>, y: Expression<out N>): Expression<N> = builder.diff(x, y)
    fun <N : Number> diff(x: Expression<out N>, y: N): Expression<N> = builder.diff(x, y)
    fun <N : Number> diff(x: N, y: Expression<out N>): Expression<N> = builder.diff(x, y)
    fun quot(x: Expression<out Number>, y: Expression<out Number>): Expression<Number> = builder.quot(x, y)
    fun quot(x: Expression<out Number>, y: Number): Expression<Number> = builder.quot(x, y)
    fun quot(x: Number, y: Expression<out Number>): Expression<Number> = builder.quot(x, y)
    fun mod(x: Expression<Int>, y: Expression<Int>): Expression<Int> = builder.mod(x, y)
    fun mod(x: Expression<Int>, y: Int): Expression<Int> = builder.mod(x, y)
    fun mod(x: Int, y: Expression<Int>): Expression<Int> = builder.mod(x, y)
    fun sqrt(x: Expression<out Number>): Expression<Double> = builder.sqrt(x)

    // typecasts:

    fun toLong(number: Expression<out Number>): Expression<Long> = builder.toLong(number)
    fun toFloat(number: Expression<out Number>): Expression<Float> = builder.toFloat(number)
    fun toDouble(number: Expression<out Number>): Expression<Double> = builder.toDouble(number)
    fun toBigDecimal(number: Expression<out Number>): Expression<BigDecimal> = builder.toBigDecimal(number)
    fun toBigInteger(number: Expression<out Number>): Expression<BigInteger> = builder.toBigInteger(number)
    fun toString(character: Expression<Char>): Expression<String> = builder.toString(character)

    // literals:

    fun <T> literal(value: T): Expression<T> = builder.literal(value)
    fun <T> nullLiteral(resultClass: Class<T>): Expression<T> = builder.nullLiteral(resultClass)

    // parameters:

    fun <T> parameter(paramClass: Class<T>): ParameterExpression<T> = builder.parameter(paramClass)
    fun <T> parameter(paramClass: Class<T>, name: String): ParameterExpression<T> = builder.parameter(paramClass, name)

    // collection operations:

    fun <C : Collection<*>> isEmpty(collection: Expression<C>): Predicate = builder.isEmpty(collection)
    fun <C : Collection<*>> isNotEmpty(collection: Expression<C>): Predicate = builder.isNotEmpty(collection)
    fun <C : Collection<*>> size(collection: Expression<C>): Expression<Int> = builder.size(collection)
    fun <C : Collection<*>> size(collection: C): Expression<Int> = builder.size(collection)
    fun <E, C : Collection<E>> isMember(elem: Expression<E>, collection: Expression<C>): Predicate = builder.isMember(elem, collection)
    fun <E, C : Collection<E>> isMember(elem: E, collection: Expression<C>): Predicate = builder.isMember(elem, collection)
    fun <E, C : Collection<E>> isNotMember(elem: Expression<E>, collection: Expression<C>): Predicate = builder.isNotMember(elem, collection)
    fun <E, C : Collection<E>> isNotMember(elem: E, collection: Expression<C>): Predicate = builder.isNotMember(elem, collection)

    // get the values and keys collections of the Map, which may then
    // be passed to size(), isMember(), isEmpty(), etc
    fun <V, M : Map<*, V>> values(map: M): Expression<Collection<V>> = builder.values(map)

    fun <K, M : Map<K, *>> keys(map: M): Expression<Set<K>> = builder.keys(map)

    // string functions:

    fun containsIgnoreCase(expression: Expression<String>, text: String) = builder.containsIgnoreCase(expression, text)
    fun containsIgnoreCase(column: String, text: String) = builder.containsIgnoreCase(getString(column), text)

    fun like(x: Expression<String>, pattern: Expression<String>): Predicate = builder.like(x, pattern)
    fun like(x: Expression<String>, pattern: String): Predicate = builder.like(x, pattern)
    fun like(x: Expression<String>, pattern: Expression<String>, escapeChar: Expression<Char>): Predicate = builder.like(x, pattern, escapeChar)
    fun like(x: Expression<String>, pattern: Expression<String>, escapeChar: Char): Predicate = builder.like(x, pattern, escapeChar)
    fun like(x: Expression<String>, pattern: String, escapeChar: Expression<Char>): Predicate = builder.like(x, pattern, escapeChar)
    fun like(x: Expression<String>, pattern: String, escapeChar: Char): Predicate = builder.like(x, pattern, escapeChar)
    fun notLike(x: Expression<String>, pattern: Expression<String>): Predicate = builder.notLike(x, pattern)
    fun notLike(x: Expression<String>, pattern: String): Predicate = builder.notLike(x, pattern)
    fun notLike(x: Expression<String>, pattern: Expression<String>, escapeChar: Expression<Char>): Predicate = builder.notLike(x, pattern, escapeChar)
    fun notLike(x: Expression<String>, pattern: Expression<String>, escapeChar: Char): Predicate = builder.notLike(x, pattern, escapeChar)
    fun notLike(x: Expression<String>, pattern: String, escapeChar: Expression<Char>): Predicate = builder.notLike(x, pattern, escapeChar)
    fun notLike(x: Expression<String>, pattern: String, escapeChar: Char): Predicate = builder.notLike(x, pattern, escapeChar)
    fun concat(x: Expression<String>, y: Expression<String>): Expression<String> = builder.concat(x, y)
    fun concat(x: Expression<String>, y: String): Expression<String> = builder.concat(x, y)
    fun concat(x: String, y: Expression<String>): Expression<String> = builder.concat(x, y)
    fun substring(x: Expression<String>, from: Expression<Int>): Expression<String> = builder.substring(x, from)
    fun substring(x: Expression<String>, from: Int): Expression<String> = builder.substring(x, from)
    fun substring(x: Expression<String>, from: Expression<Int>, len: Expression<Int>): Expression<String> = builder.substring(x, from, len)
    fun substring(x: Expression<String>, from: Int, len: Int): Expression<String> = builder.substring(x, from, len)
    fun trim(x: Expression<String>): Expression<String> = builder.trim(x)
    fun trim(ts: Trimspec, x: Expression<String>): Expression<String> = builder.trim(ts, x)
    fun trim(t: Expression<Char>, x: Expression<String>): Expression<String> = builder.trim(t, x)
    fun trim(ts: Trimspec, t: Expression<Char>, x: Expression<String>): Expression<String> = builder.trim(ts, t, x)
    fun trim(t: Char, x: Expression<String>): Expression<String> = builder.trim(t, x)
    fun trim(ts: Trimspec, t: Char, x: Expression<String>): Expression<String> = builder.trim(ts, t, x)
    fun lower(x: Expression<String>): Expression<String> = builder.lower(x)
    fun upper(x: Expression<String>): Expression<String> = builder.upper(x)
    fun length(x: Expression<String>): Expression<Int> = builder.length(x)
    fun locate(x: Expression<String>, pattern: Expression<String>): Expression<Int> = builder.locate(x, pattern)
    fun locate(x: Expression<String>, pattern: String): Expression<Int> = builder.locate(x, pattern)
    fun locate(x: Expression<String>, pattern: Expression<String>, from: Expression<Int>): Expression<Int> = builder.locate(x, pattern, from)
    fun locate(x: Expression<String>, pattern: String, from: Int): Expression<Int> = builder.locate(x, pattern, from)

    // Date/time/timestamp functions:

    fun currentDate(): Expression<Date> = builder.currentDate()
    fun currentTimestamp(): Expression<Timestamp> = builder.currentTimestamp()
    fun currentTime(): Expression<Time> = builder.currentTime()

    // in builders:

    fun <T> `in`(expression: Expression<out T>): In<T> = builder.`in`(expression)
    fun <T> inside(expression: Expression<out T>): In<T> = builder.`in`(expression)

    // coalesce, nullif:

    fun <Y> coalesce(x: Expression<out Y>, y: Expression<out Y>): Expression<Y> = builder.coalesce(x, y)
    fun <Y> coalesce(x: Expression<out Y>, y: Y): Expression<Y> = builder.coalesce(x, y)
    fun <Y> nullif(x: Expression<Y>, y: Expression<*>): Expression<Y> = builder.nullif(x, y)
    fun <Y> nullif(x: Expression<Y>, y: Y): Expression<Y> = builder.nullif(x, y)

    // coalesce builder:

    fun <T> coalesce(): Coalesce<T> = builder.coalesce()

    // case builders:

    fun <C, R> selectCase(expression: Expression<out C>): SimpleCase<C, R> = builder.selectCase(expression)
    fun <R> selectCase(): Case<R> = builder.selectCase()
    fun <T> function(name: String, type: Class<T>, vararg args: Expression<*>): Expression<T> = builder.function(name, type, *args)

    // methods for downcasting:

    fun <X, T, V : T> treat(join: Join<X, T>, type: Class<V>): Join<X, V> = builder.treat(join, type)
    fun <X, T, E : T> treat(join: CollectionJoin<X, T>, type: Class<E>): CollectionJoin<X, E> = builder.treat(join, type)
    fun <X, T, E : T> treat(join: SetJoin<X, T>, type: Class<E>): SetJoin<X, E> = builder.treat(join, type)
    fun <X, T, E : T> treat(join: ListJoin<X, T>, type: Class<E>): ListJoin<X, E> = builder.treat(join, type)
    fun <X, K, T, V : T> treat(join: MapJoin<X, K, T>, type: Class<V>): MapJoin<X, K, V> = builder.treat(join, type)
    fun <X, T : X> treat(path: Path<X>, type: Class<T>): Path<T> = builder.treat(path, type)
    fun <X, T : X> treat(root: Root<X>, type: Class<T>): Root<T> = builder.treat(root, type)

    // END CriteriaBuilder functions

    // START Root functions

    fun getModel(): EntityType<T> = model.model
    operator fun <Y> get(attributeName: String): Path<Y> = model.get<Y>(attributeName)
    fun getString(column: String) = get<String>(column)
    fun getInt(column: String) = get<Int>(column)
    fun getFloat(column: String) = get<Float>(column)
    fun getDouble(column: String) = get<Double>(column)
    fun getLong(column: String) = get<Long>(column)
    fun getBigDecimal(column: String) = get<BigDecimal>(column)
    fun getBigInteger(column: String) = get<BigInteger>(column)

    // END Root functions

    // START infix/operator functions

    infix fun plus(query: CriteriaQuery<T>): CriteriaNameSpace<T> {
        this.query = query
        return this
    }

    infix fun plus(builder: CriteriaBuilder): CriteriaNameSpace<T> {
        this.builder = builder
        return this
    }

    infix fun plus(model: Root<T>): CriteriaNameSpace<T> {
        this.model = model
        return this
    }

    fun CriteriaQuery<T>.orderAsc(x: Expression<*>) = orderBy(asc(x))
    fun CriteriaQuery<T>.orderDesc(x: Expression<*>) = orderBy(desc(x))

    fun Path<String>.toLowerCase(): Expression<String> {
        return lower(this)
    }

    infix fun Predicate.or(predicate: Predicate): Predicate {
        return or(this, predicate)
    }

    // END infix/operator functions
}

infix fun <T> CriteriaQuery<T>.plus(builder: CriteriaBuilder): CriteriaNameSpace<T> {
    val namespace = CriteriaNameSpace<T>()
    namespace.query = this
    namespace.builder = builder
    return namespace
}

infix fun <T> CriteriaQuery<T>.plus(model: Root<T>): CriteriaNameSpace<T> {
    val namespace = CriteriaNameSpace<T>()
    namespace.query = this
    namespace.model = model
    return namespace
}

infix fun <T> CriteriaBuilder.plus(query: CriteriaQuery<T>): CriteriaNameSpace<T> {
    val namespace = CriteriaNameSpace<T>()
    namespace.builder = this
    namespace.query = query
    return namespace
}

infix fun <T> CriteriaBuilder.plus(model: Root<T>): CriteriaNameSpace<T> {
    val namespace = CriteriaNameSpace<T>()
    namespace.builder = this
    namespace.model = model
    return namespace
}

infix fun <T> Root<T>.plus(query: CriteriaQuery<T>): CriteriaNameSpace<T> {
    val namespace = CriteriaNameSpace<T>()
    namespace.model = this
    namespace.query = query
    return namespace
}

infix fun <T> Root<T>.plus(builder: CriteriaBuilder): CriteriaNameSpace<T> {
    val namespace = CriteriaNameSpace<T>()
    namespace.model = this
    namespace.builder = builder
    return namespace
}

typealias QueryModifier<T> = (CriteriaNameSpace<T>) -> Unit

fun <T> SessionFactory.query(type: Class<T>, queryModifier: QueryModifier<T>? = null): List<T> {
    val session = this.openSession()

    val builder = session.criteriaBuilder
    val query = builder.createQuery(type)

    val root = query.from(type)

    queryModifier?.let {
        it(CriteriaNameSpace<T>().apply {
            this.query = query
            this.builder = builder
            this.model = root
        })
    }

    val result = session.createQuery(query).resultList

    session.close()

    return result
}

fun <T> Root<T>.getString(column: String): Path<String> {
    return this.get<String>(column)
}

fun CriteriaBuilder.contains(expression: Expression<String>, text: String): Predicate {
    return this.like(expression, "%${text}%")
}

fun CriteriaBuilder.containsIgnoreCase(expression: Expression<String>, text: String): Predicate {
    return contains(lower(expression), text.toLowerCase())
}
