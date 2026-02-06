package kr.android.shoppinglistapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kr.android.shoppinglistapp.ui.theme.BodyText
import kr.android.shoppinglistapp.ui.theme.DeleteRed
import kr.android.shoppinglistapp.ui.theme.DialogBg
import kr.android.shoppinglistapp.ui.theme.EditBlue
import kr.android.shoppinglistapp.ui.theme.EditorBg
import kr.android.shoppinglistapp.ui.theme.EmptyText
import kr.android.shoppinglistapp.ui.theme.PrimaryButton
import kr.android.shoppinglistapp.ui.theme.RowBg
import kr.android.shoppinglistapp.ui.theme.RowBorder
import kr.android.shoppinglistapp.ui.theme.ScreenBg
import kr.android.shoppinglistapp.ui.theme.TitleText

//home screen and shopping list logic
@Composable
fun ShoppingList(modifier: Modifier)
{

    //shopping list
    val shoppingItems = remember { mutableStateListOf<ShoppingListClass>() }

    //item adding dialog box toggle
    var showDialog by remember { mutableStateOf(false) }

    //item details
    var itemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") }

    //id generation
    var nextId by remember { mutableStateOf(0) }

    //parent column
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = ScreenBg),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {

        //app title
        Text(
            "Shopping List 💸",
            fontSize = 40.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold,
            color = TitleText,
            modifier = Modifier.padding(top = 16.dp))

        Spacer(Modifier.height(20.dp))

        //add item button
        Button(
            onClick = {
                itemName = ""
                itemQuantity = ""
                showDialog = true
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryButton,
                contentColor = Color.White
            ),
            modifier = Modifier
                .padding(4.dp)
                .height(48.dp)
        ) {
            Text(
                "Add Item",
                fontSize = 16.sp
            )
        }

//        Spacer(Modifier.height(240.dp))

        if (shoppingItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No items yet 🛒\n\nTap \"Add Item\" to get started",
                    color = EmptyText,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }
        }
        else {

            Spacer(Modifier.height(8.dp))

            //scrollable shopping list
            LazyColumn(
                modifier = Modifier.padding(bottom = 15.dp),
            ) {

                //looping through the list
                itemsIndexed(
                    items = shoppingItems,
                    key = { _, item -> item.id }
                ) { index, item ->
                    if (item.isEditing) {
                        ShoppingItemEditor(
                            item = item,
                            onSave = { editedName, editedQuantity ->
                                val itemIndex = shoppingItems.indexOfFirst { it.id == item.id }

                                if (itemIndex != -1) {
                                    shoppingItems[itemIndex] = shoppingItems[itemIndex].copy(
                                        name = editedName,
                                        quantity = editedQuantity,
                                        isEditing = false
                                    )
                                }
                            },
                            onCancel = {
                                shoppingItems.replaceAll { it.copy(isEditing = false) }
                            }
                        )
                    } else {
                        ShoppingItemView(
                            serial = index + 1,   // +1 so it starts from 1 instead of 0
                            item = item,
                            onEditClick = {
                                shoppingItems.replaceAll {
                                    it.copy(isEditing = it.id == item.id)
                                }
                            },
                            onDeleteClick = {
                                shoppingItems -= item
                            }
                        )
                    }
                }
            }
        }

        //item adding window (modified alert box)
        if (showDialog){

            AlertDialog(
                modifier = Modifier.fillMaxWidth(),
                onDismissRequest = {
                    showDialog = false
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = Color.DarkGray
                    )
                },
                title = {
                    Text(
                        "Add Shopping Item",
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {

                    Column {
                        OutlinedTextField(
                            value = itemName,
                            onValueChange = {
                                itemName = it.take(50)
                            },
                            label = {
                                Text("Item Name")
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = RowBorder,
                                unfocusedBorderColor = RowBorder.copy(alpha = .5f),
                                focusedLabelColor = TitleText,
                                unfocusedLabelColor = BodyText
                            )
                        )
                        Text(
                            text = "${itemName.length}/50",
                            fontSize = 12.sp,
                            color = BodyText.copy(alpha = .8f),
                            modifier = Modifier.align(Alignment.End)
                        )

                        Spacer(Modifier.height(8.dp))

                        OutlinedTextField(
                            value = itemQuantity,
                            onValueChange = {
                                itemQuantity = it
                                    .filter { it.isDigit() }
                                    .take(3)
                            },
                            label = {
                                Text("Item Quantity")
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = RowBorder,
                                unfocusedBorderColor = RowBorder.copy(alpha = .5f),
                                focusedLabelColor = TitleText,
                                unfocusedLabelColor = BodyText
                            )
                        )
                        Text(
                            text = "${itemQuantity.length}/3",
                            fontSize = 12.sp,
                            color = BodyText.copy(alpha = .8f),
                            modifier = Modifier.align(Alignment.End)
                        )
                    }
                },
                confirmButton = {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        //add button
                        Button(
                            onClick = {
                                if (itemName.isNotBlank()){

                                    val newItem = ShoppingListClass(
                                        id = nextId++,
                                        name = itemName,
                                        quantity = itemQuantity.toIntOrNull() ?: return@Button
                                    )

                                    shoppingItems += newItem
                                    itemName = ""
                                    itemQuantity = ""
                                    showDialog = false
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PrimaryButton,
                                contentColor = Color.White,
                                disabledContainerColor = PrimaryButton.copy(alpha = 0.6f),
                                disabledContentColor = Color.White.copy(alpha = 0.8f)
                            ),
                            enabled = itemName.isNotBlank() &&
                                        itemQuantity.toIntOrNull() != null &&
                                        itemQuantity.toInt() > 0
                        ) {
                            Text("Add")
                        }

                        //cancel button
                        Button(
                            onClick = {
                                itemName = ""
                                itemQuantity = ""
                                showDialog = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = DeleteRed,
                                contentColor = Color.White
                            )
                        ) {
                            Text("Cancel")
                        }
                    }
                },
                containerColor = DialogBg
            )
        }
    }
}
