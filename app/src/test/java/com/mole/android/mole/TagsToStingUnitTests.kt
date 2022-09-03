package com.mole.android.mole

import org.junit.Test

import org.junit.Assert.*


class TagsToStingUnitTests {
    @Test
    fun testTagsToString_withTwoTag() {
        val tags = listOf("first", "second")
        val expected = "#first, #second"
        val actual = tagsToString(tags)
        assertEquals(expected, actual)
    }

    @Test
    fun testTagsToString_withEmptyTags() {
        val tags = listOf<String>()
        val expected = ""
        val actual = tagsToString(tags)
        assertEquals(expected, actual)
    }

    @Test
    fun testTagsToString_withOneTag() {
        val tags = listOf("first")
        val expected = "#first"
        val actual = tagsToString(tags)
        assertEquals(expected, actual)
    }
}