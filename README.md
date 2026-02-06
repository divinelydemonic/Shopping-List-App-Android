# 🛒 Shopping List App (Jetpack Compose)

A modern Android Shopping List application built using Jetpack Compose and Material 3.  
The app allows users to add, edit, and delete shopping items with a clean UI and smooth state-driven updates.

---

## ✨ Features

- Add shopping items with name and quantity  
- Edit existing items inline  
- Delete items from the list  
- Auto-generated serial numbers  
- Friendly empty-state UI  
- Custom color palette with Material 3  
- Fully built using Jetpack Compose  
- State management using Compose APIs  

---

## 🧩 Tech Stack

- Language: Kotlin  
- UI Framework: Jetpack Compose  
- Design System: Material 3  
- Architecture: Single Activity + Composables  
- State Management: remember, mutableStateOf, mutableStateListOf  

---

## 🗂️ Project Structure

```
kush.android.shoppinglistapp
│
├── MainActivity.kt
├── ShoppingList.kt
├── ShoppingItemView.kt
├── ShoppingItemEditor.kt
├── ShoppingListClass.kt
│
└── ui.theme
    ├── Color.kt
    ├── Theme.kt
    └── Type.kt
```

---

## 🧠 App Logic Overview

### Adding Items
- Uses a custom AlertDialog
- Input validation for name and quantity
- Automatically generates unique item IDs

### Editing Items
- Inline editor replaces the item card
- Only one item can be edited at a time
- Save and cancel actions supported

### State Handling
- Items stored in a mutableStateListOf
- UI updates automatically on state changes
- isEditing flag controls edit/view mode

---

## 🎨 UI & Design Highlights

- Soft pastel background for readability  
- Card-based list items with elevation  
- Color-coded actions:
  - Edit
  - Delete
- Responsive layout using LazyColumn  
- Clean empty-state messaging  

---

## 📌 Requirements

- Android Studio Hedgehog or newer  
- Kotlin 1.9+  
- Minimum SDK: 24  

---

## 📈 Future Improvements

- Persistent storage (Room / DataStore)
- Swipe-to-delete gestures
- Item categories
- Dark mode support
- Animations with Compose APIs

---

⭐ If you like this project, feel free to star the repository!
