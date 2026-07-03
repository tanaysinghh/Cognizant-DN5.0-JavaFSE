# JPA vs Hibernate vs Spring Data JPA

A short write-up on how these three relate, in my own words.

## JPA (Java Persistence API)
JPA is only a **specification** (JSR 338). It defines a standard set of interfaces
and annotations (`@Entity`, `@Id`, `@Table`, `EntityManager`, etc.) for mapping Java
objects to database tables. JPA itself has **no implementation** — it just describes
the rules that an implementation must follow.

## Hibernate
Hibernate is one of the **implementations** of the JPA specification. It is an ORM
(Object-Relational Mapping) tool that actually does the work: generating SQL, managing
sessions and transactions, caching, lazy loading, and so on. You can use Hibernate on
its own, or through the JPA interfaces.

## Spring Data JPA
Spring Data JPA is a **layer of abstraction on top of JPA/Hibernate**. It removes the
boilerplate you normally write when persisting data. Instead of writing an
`EntityManager` and manual queries, you just declare a repository interface that
extends `JpaRepository`, and Spring generates the implementation at runtime — giving
you `save()`, `findById()`, `findAll()`, `deleteById()`, plus derived query methods
built from method names.

## Summary

| Layer            | What it is                              | Provides implementation? |
|------------------|-----------------------------------------|--------------------------|
| JPA              | Specification (interfaces + annotations)| No                       |
| Hibernate        | ORM tool, a JPA implementation          | Yes                      |
| Spring Data JPA  | Abstraction over JPA to cut boilerplate | Uses Hibernate underneath|

**In one line:** JPA is the *standard*, Hibernate is the *engine that implements it*,
and Spring Data JPA is the *convenience layer* that lets you use that engine with far
less code.
