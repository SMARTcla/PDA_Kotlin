package cz.cvut.fel.sit.pda.models

enum class TransactionType(val displayName: String, val category: String) {
    // Expences
    RESTAURANT("Restaurant", "Expenses"),
    GROCERIES("Groceries", "Expenses"),
    SUBSCRIPTIONS("Subscriptions", "Expenses"),
    ENTERTAINMENT("Entertainment", "Expenses"),
    UTILITIES("Utilities", "Expenses"),
    RENT("Rent", "Expenses"),
    TRANSPORT("Transport", "Expenses"),
    HEALTHCARE("Healthcare", "Expenses"),
    INSURANCE("Insurance", "Expenses"),
    GIFTS("Gifts", "Expenses"),
    CLOTHING("Clothing", "Expenses"),
    EDUCATION("Education", "Expenses"),
    PERSONAL_CARE("Personal Care", "Expenses"),
    TRAVEL("Travel", "Expenses"),
    OTHER("Other", "Expenses"),
    // Income
    SALARY("Salary", "Income"),
    BENEFITS("Benefits", "Income");
}