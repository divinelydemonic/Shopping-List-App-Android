package kr.android.shoppinglistapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kr.android.shoppinglistapp.ui.theme.BodyText
import kr.android.shoppinglistapp.ui.theme.DeleteRed
import kr.android.shoppinglistapp.ui.theme.EditBlue
import kr.android.shoppinglistapp.ui.theme.RowBg

//shopping list view
@Composable
fun ShoppingItemView(
    serial: Int,
    item: ShoppingListClass,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
)
{
    //parent column
    Column {

        //parent row for each item
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            colors = CardDefaults.cardColors(containerColor = RowBg)
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                //item serial number
                Box(
                    modifier = Modifier
                        .size(25.dp)
                        .padding(end = 10.dp)
                        .background(EditBlue.copy(alpha = .1f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = serial.toString(),
                        fontWeight = FontWeight.Bold,
                        color = EditBlue
                    )
                }

                //item name row
                Row(
                    modifier = Modifier.weight(2f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {

                    Text(
                        "Name: ",
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = BodyText
                    )

                    Text(
                        item.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = BodyText
                    )
                }

                Spacer(Modifier.width(8.dp))

                //item quantity row
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {

                    Text(
                        "Qty: ",
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = BodyText
                    )

                    Text(
                        item.quantity.toString(),
                        color = BodyText
                    )
                }

                Spacer(Modifier.width(4.dp))

                //buttons row
                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    //edit button
                    IconButton(
                        onClick = { onEditClick() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            tint = EditBlue
                        )
                    }

                    //delete button
                    IconButton(
                        onClick = { onDeleteClick() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = DeleteRed
                        )
                    }
                }
            }
        }
    }
}