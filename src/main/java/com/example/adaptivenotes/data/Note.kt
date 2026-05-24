package com.example.adaptivenotes.data // Перевір, щоб тут була твоя назва пакета

// Опис того, які поля має нотатка
data class Note(
    val id: Int,
    val title: String,
    val text: String
)

// Тестові дані, які ми покажемо в додатку
val sampleNotes = listOf(
    Note(1, "Перша нотатка", "Це приклад нотатки."),
    Note(2, "Завдання", "Купити молоко, зарядити ноутбук.")
)