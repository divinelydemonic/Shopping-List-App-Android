package kr.android.shoppinglistapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kr.android.shoppinglistapp.ui.theme.DeleteRed
import kr.android.shoppinglistapp.ui.theme.EditorBg
import kr.android.shoppinglistapp.ui.theme.PrimaryButton

//shopping item editor view
@Composable
fun ShoppingItemEditor(
    item: ShoppingListClass,
    onSave: (String, Int) -> Unit,
    onCancel: () -> Unit
){

    var editedName by remember { mutableStateOf(item.name) }
    var editedQuantity by remember { mutableStateOf(item.quantity.toString()) }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(20),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = EditorBg
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(horizontal = 24.dp, vertical = 40.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Text(
                        "Name:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                    BasicTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = editedName,
                        onValueChange = {
                            editedName = it
                                .replace("\n", "")
                                .take(50)
                        },
//                    singleLine = true,
                        textStyle = TextStyle(
                            fontSize = 20.sp
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                    )
                }

                Spacer(Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Text(
                        "Qty:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                    BasicTextField(
                        value = editedQuantity,
                        onValueChange = {
                            editedQuantity = it
                                .filter { it.isDigit() }
                                .take(3)
                        },
                        singleLine = true,
                        textStyle = TextStyle(
                            fontSize = 20.sp
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                    )
                }
            }
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {

                //save button
                Button(
                    onClick = {
                        val qty = editedQuantity.toIntOrNull() ?: return@Button
                        if (qty <= 0) return@Button

                        onSave(editedName, qty)
                    },
                    enabled = editedQuantity.toIntOrNull()?.let { it > 0 } == true,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryButton,
                        contentColor = Color.White,
                        disabledContainerColor = PrimaryButton.copy(alpha = 0.6f),
                        disabledContentColor = Color.White.copy(alpha = 0.8f)
                    )
                ) {
                    Text("Save")
                }

                Spacer(Modifier.height(4.dp))

                //cancel button
                Button(
                    onClick = { onCancel() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DeleteRed,
                        contentColor = Color.White
                    )
                ) {
                    Text(("Cancel"))
                }
            }

        }
    }
}