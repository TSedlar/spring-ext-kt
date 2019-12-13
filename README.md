# Spring Kotlin Extension

[![](https://img.shields.io/github/license/mashape/apistatus.svg)](LICENSE)
[![](https://jitpack.io/v/TSedlar/spring-ext-kt.svg)](https://jitpack.io/#TSedlar/spring-ext-kt)

## Getting Started

### CriteriaNameSpace

This utility brings forth a much clearer syntax for using CriteraQuery with hibernate.

Our example model will have the below structure:

```kotlin
data class Package(
    val name: String,
    val version: String,
    val bucket: String,
    val description: String
)
```

Now say we want to query the database for a package that has a name or description containing a query... 

We can do this in a much more clear way, through extension methods offered by `CriteriaNameSpace`:

```kotlin
// returns a List<Package> in ascending order of name
sessionFactory.query(Package::class.java) { namespace ->
    with(namespace) {
        select()
            .where(
                or(
                    containsIgnoreCase(getString("name"), queryString),
                    containsIgnoreCase(getString("description"), queryString)
                )
            )
            .orderAsc(getString("name").toLowerCase())
    }
}
```

Using kotlin's [with](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/with.html) function, we can inherit all of the methods within [CriteriaNameSpace](src/main/kotlin/me/sedlar/spring/hibernate/CriteriaNameSpace.kt) for the current scope.

One can also directly use the raw objects via:
 - [CriteriaQuery](https://docs.oracle.com/javaee/7/api/javax/persistence/criteria/CriteriaQuery.html) - `namespace#query`
 - [CriteriaBuilder](https://docs.oracle.com/javaee/7/api/javax/persistence/criteria/CriteriaBuilder.html) - `namespace#builder`
 - [Root](https://docs.oracle.com/javaee/7/api/javax/persistence/criteria/Root.html) - `namespace#rootModel`

Otherwise, one would have to access the [CriteriaBuilder](https://docs.oracle.com/javaee/7/api/javax/persistence/criteria/CriteriaBuilder.html) with each function call.

The equivalent code would look as such:

```kotlin
val session = sessionFactory.openSession()

val builder = session.criteriaBuilder
val query = builder.createQuery(Package::class.java)

val root = query.from(Package::class.java)

query.select(root)
    .where(
        builder.or(
            builder.like(builder.lower(root.get<String>("name")), "%${queryString.toLowerCase()}%"),
            builder.like(builder.lower(root.get<String>("description")), "%${queryString.toLowerCase()}%")
        )
    )
    .orderBy(builder.asc(
        builder.lower(root.get<String>("name"))
    ))

// returns a List<Package> in ascending order of name
val result = session.createQuery(query).resultList

session.close()
```



