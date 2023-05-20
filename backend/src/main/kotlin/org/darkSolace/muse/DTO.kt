package org.darkSolace.muse

import jakarta.transaction.Transactional

interface DTO<T, E> {
    @Transactional
    fun fromCollection(collection: Collection<T>): Collection<E> = collection.map { from(it) }

    @Transactional
    fun from(item: T): E
}
