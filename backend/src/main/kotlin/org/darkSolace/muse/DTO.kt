package org.darkSolace.muse

interface DTO<T, E> {
    fun fromCollection(collection: Collection<T>): Collection<E> = collection.map { from(it) }
    fun from(item: T): E
}