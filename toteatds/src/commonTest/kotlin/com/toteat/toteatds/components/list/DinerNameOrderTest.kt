package com.toteat.toteatds.components.list

import kotlin.test.Test
import kotlin.test.assertEquals

class DinerNameOrderTest {

    @Test
    fun `numbers are compared by value`() {
        assertEquals(
            listOf("Comensal 1", "Comensal 2", "Comensal 10"),
            listOf("Comensal 10", "Comensal 2", "Comensal 1").sortedWith(DinerNameOrder)
        )
    }

    @Test
    fun `accents do not move a name out of its letter`() {
        assertEquals(
            listOf("Álvaro", "Ana", "Zoe"),
            listOf("Zoe", "Álvaro", "Ana").sortedWith(DinerNameOrder)
        )
    }

    @Test
    fun `ñ sorts between n and o`() {
        assertEquals(
            listOf("Nora", "Ñandú", "Oscar"),
            listOf("Oscar", "Ñandú", "Nora").sortedWith(DinerNameOrder)
        )
    }

    @Test
    fun `case does not affect the order`() {
        assertEquals(listOf("Ana", "beto"), listOf("beto", "Ana").sortedWith(DinerNameOrder))
    }

    @Test
    fun `a shorter name goes before a longer one it starts`() {
        assertEquals(listOf("Juan", "Juan 2"), listOf("Juan 2", "Juan").sortedWith(DinerNameOrder))
    }

    @Test
    fun `the group letter ignores accents but keeps ñ`() {
        assertEquals('A', "Álvaro".groupLetter())
        assertEquals('Ñ', "ñandú".groupLetter())
    }
}
