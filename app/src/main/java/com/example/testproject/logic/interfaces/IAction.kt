package com.example.testproject.logic.interfaces

import com.example.testproject.model.Result

interface IAction {
    fun execute(result: Result?)
}