package com.toteat.toteatds.components.list

/**
 * Natural order for diner names: digit runs compare by value ("Comensal 2" before
 * "Comensal 10"), accents and case are ignored, and Ñ sorts as its own letter between
 * N and O. Ties fall back to the original text so the order is stable across recompositions.
 *
 * `Collator` and `java.text.Normalizer` are not available in commonMain, so accents are
 * stripped by hand for accented Latin letters only.
 */
internal object DinerNameOrder : Comparator<String> {

    override fun compare(a: String, b: String): Int {
        var i = 0
        var j = 0
        while (i < a.length && j < b.length) {
            if (a[i].isAsciiDigit() && b[j].isAsciiDigit()) {
                val endA = a.digitRunEnd(i)
                val endB = b.digitRunEnd(j)
                val byValue = compareDigitRuns(a.substring(i, endA), b.substring(j, endB))
                if (byValue != 0) return byValue
                i = endA
                j = endB
            } else {
                val byLetter = a[i].sortRank().compareTo(b[j].sortRank())
                if (byLetter != 0) return byLetter
                i++
                j++
            }
        }
        val byRemaining = (a.length - i).compareTo(b.length - j)
        return if (byRemaining != 0) byRemaining else a.compareTo(b)
    }

    private fun Char.isAsciiDigit(): Boolean = this in '0'..'9'

    private fun String.digitRunEnd(start: Int): Int {
        var end = start
        while (end < length && this[end].isAsciiDigit()) end++
        return end
    }

    // Compares by length after dropping leading zeros, then by text, so a very long
    // number never overflows an Int.
    private fun compareDigitRuns(a: String, b: String): Int {
        val trimmedA = a.trimStart('0')
        val trimmedB = b.trimStart('0')
        val byLength = trimmedA.length.compareTo(trimmedB.length)
        return if (byLength != 0) byLength else trimmedA.compareTo(trimmedB)
    }

    // Doubling every code leaves a slot right after 'n' for 'ñ'.
    private fun Char.sortRank(): Int {
        val base = lowercaseChar().withoutAccent()
        return if (base == 'ñ') 'n'.code * 2 + 1 else base.code * 2
    }
}

internal fun String.groupLetter(): Char = first().lowercaseChar().withoutAccent().uppercaseChar()

private fun Char.withoutAccent(): Char = when (this) {
    'á', 'à', 'â', 'ä', 'ã' -> 'a'
    'é', 'è', 'ê', 'ë' -> 'e'
    'í', 'ì', 'î', 'ï' -> 'i'
    'ó', 'ò', 'ô', 'ö', 'õ' -> 'o'
    'ú', 'ù', 'û', 'ü' -> 'u'
    'ç' -> 'c'
    else -> this
}
