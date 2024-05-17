package cz.cvut.fel.sit.pda.database

enum class TransactionType(val displayName: String, val category: String) {
    // Expences
    RESTAURANT(displayName = "Restaurant", category = "Expenses"),
    GROCERIES(displayName = "Groceries", category = "Expenses"),
    LEISURE(displayName = "Leisure", category =  "Expenses"),
    FAMILY(displayName = "Family", category =  "Expenses"),
    TRANSPORT(displayName = "Transport", category =  "Expenses"),
    HEALTHCARE(displayName = "Healthcare", category =  "Expenses"),
    GIFTS(displayName = "Gifts", category =  "Expenses"),
    SHOPPING(displayName = "Shopping", category =  "Expenses"),
    OTHER_EXP(displayName = "Other", category =  "Expenses"),
    // Income
    SALARY(displayName = "Salary", category =  "Income"),
    BENEFITS(displayName = "Benefits", category =  "Income"),
    PROFIT(displayName = "Profit", category = "Income"),

}