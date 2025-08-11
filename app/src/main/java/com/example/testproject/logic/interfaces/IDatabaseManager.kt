package com.example.testproject.logic.interfaces

import com.example.testproject.data.entity.RootEntity

interface IDatabaseManager {
    fun load(): MutableList<RootEntity>
    fun save(rootEntity: RootEntity)
    fun clear()
}