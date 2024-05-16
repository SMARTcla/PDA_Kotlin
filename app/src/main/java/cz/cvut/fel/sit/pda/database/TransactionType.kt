package cz.cvut.fel.sit.pda.database

enum class TransactionType(val displayName: String, val category: String) {
    // Expences
    RESTAURANT("Restaurant", "Expenses"),
    GROCERIES("Groceries", "Expenses"),
    LEISURE("Leisure", "Expenses"),
    FAMILY("Family", "Expenses"),
    TRANSPORT("Transport", "Expenses"),
    HEALTHCARE("Healthcare", "Expenses"),
    GIFTS("Gifts", "Expenses"),
    SHOPPING("Shopping", "Expenses"),
    OTHER_EXP("Other", "Expenses"),
    // Income
    SALARY("Salary", "Income"),
    BENEFITS("Benefits", "Income"),
    OTHER_INC("Other", "Income"),

}