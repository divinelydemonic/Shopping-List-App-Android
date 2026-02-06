package kr.android.shoppinglistapp

data class ShoppingListClass (
    var id: Int,
    var name: String,
    var quantity: Int,
    var isEditing: Boolean = false
)