package kr.android.shoppinglistapp

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kr.android.shoppinglistapp.ui.theme.*

//home screen and shopping list logic
@Composable
fun ShoppingList(
    viewModel: LocationViewModel,
    navController: NavController,
    context: Context,
    locationUtils: LocationUtils
)
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
        modifier = Modifier
            .fillMaxSize()
            .background(color = ScreenBg)
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            //app title
            Text(
                "The Shopping List",
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                color = TitleText
            )

            //change location icon button
            IconButton(
                onClick = {
                    locationUtils.requestLocationUpdates(viewModel)
                    navController.navigate("locationDialog") {
                        this.launchSingleTop
                    }
                },
                modifier = Modifier.padding(end = 4.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = Color.Black
                )
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "change location",
                    tint = Purple40
                )
            }
        }

        Spacer(Modifier.height(10.dp))

        if (viewModel.address.value.isNotEmpty()) {
            Box(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Address: ",
                        fontWeight = FontWeight.Bold,
                        color = EmptyText
                    )
                    Text(
                        text = viewModel.address.value.firstOrNull()?.formatted_address ?: "No address found!",
                        color = EmptyText
                    )
                }
            }
        }

        Spacer(Modifier.height(32.dp))

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
                .padding(2.dp)
                .height(40.dp)
        ) {
            Text(
                "Add Item",
                fontSize = 15.sp
            )
        }

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
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
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
                                unfocusedLabelColor = BodyText,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black
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
                            onValueChange = { qty ->
                                itemQuantity = qty
                                    .filter { it.isDigit() }
                                    .take(3)
                            },
                            textStyle = TextStyle(
                                color = Color.Black
                            ),
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
                                unfocusedLabelColor = BodyText,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black
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
