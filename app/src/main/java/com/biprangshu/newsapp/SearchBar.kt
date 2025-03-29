package com.biprangshu.newsapp

import android.content.res.Configuration
import androidx.compose.foundation.clickable // Import clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn // Use heightIn for min height
import androidx.compose.foundation.layout.padding // Import padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface // Import Surface for readOnly state
import androidx.compose.material3.Text
import androidx.compose.material3.TextField // Keep TextField for interactive state
import androidx.compose.material3.TextFieldDefaults // Keep TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue // Import getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed // Keep composed for modifier logic consistency (though simplified)
import androidx.compose.ui.draw.clip // Import clip
import androidx.compose.ui.graphics.Color // Keep Color for Transparent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.biprangshu.newsapp.ui.theme.NewsAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    text: String,
    readOnly: Boolean,
    onClick: (() -> Unit)? = null, // Corrected: onClick is nullable Unit function
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    // Handle click only when readOnly is true
    val isPressed by interactionSource.collectIsPressedAsState()
    LaunchedEffect(key1 = isPressed) {
        if (isPressed && readOnly) { // Trigger onClick only if readOnly and pressed
            onClick?.invoke()
        }
    }

    val searchBarMinHeight = 56.dp // Standard M3 height
    val searchBarShape = MaterialTheme.shapes.extraLarge // Common M3 SearchBar shape

    Box(modifier = modifier.heightIn(min = searchBarMinHeight)) { // Ensure minimum height
        if (readOnly) {
            // Use a Surface to mimic TextField appearance but make it clickable
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = searchBarMinHeight) // Ensure height
                    .clip(searchBarShape)
                    .clickable( // Make the surface clickable
                        enabled = onClick != null, // Enable click only if onClick is provided
                        interactionSource = interactionSource,
                        indication = null // Basic visual feedback handled by interactionSource if needed elsewhere
                    ) {
                        // Actual click logic is in LaunchedEffect listening to isPressed
                    },
                shape = searchBarShape,
                color = MaterialTheme.colorScheme.surfaceVariant, // M3 color for input backgrounds
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant // M3 color for placeholder/icons
            ) {
                // Layout content inside the Surface similar to TextField's placeholder/icon
                Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), contentAlignment = Alignment.CenterStart) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "Search Icon",
                            modifier = Modifier.size(24.dp), // Standard M3 icon size
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "Search News...", // Placeholder text for readOnly state
                            style = MaterialTheme.typography.bodyLarge, // Match TextField's text style
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        } else {
            // Use actual TextField when interactive
            TextField(
                value = text,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = searchBarMinHeight), // Ensure consistent height
                readOnly = false, // Explicitly false here
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Search Icon", // Add description
                        modifier = Modifier.size(24.dp), // Standard M3 icon size
                        tint = MaterialTheme.colorScheme.onSurfaceVariant // M3 color for less emphasis
                    )
                },
                placeholder = {
                    Text(
                        text = "Search News...", // Use more descriptive placeholder
                        style = MaterialTheme.typography.bodyLarge, // Use bodyLarge for placeholder like M3
                        color = MaterialTheme.colorScheme.onSurfaceVariant // M3 placeholder color
                    )
                },
                shape = searchBarShape, // Use defined M3 shape
                // **** CORRECTED: Use textFieldColors and appropriate parameters ****
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant, // Use containerColor
                    // textColor = MaterialTheme.colorScheme.onSurface, // Implicitly uses content color
                    cursorColor = MaterialTheme.colorScheme.primary,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    // Icon and placeholder colors are often derived, but can be specified if needed
                    // focusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    // unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    // placeholderColor = MaterialTheme.colorScheme.onSurfaceVariant, // Handled by placeholder composable color
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = { onSearch() } // Keep original action
                ),
                textStyle = MaterialTheme.typography.bodyLarge, // Consistent text style
                interactionSource = interactionSource // Keep interaction source
            )
        }
    }
}

// Custom modifier can be removed or simplified if not strictly needed.
// Keeping the structure but removing the border logic as M3 handles visual states.
fun Modifier.searchBarBorder() = composed {
    // No border applied by default in M3 filled TextField style.
    // Use OutlinedTextField if border is desired.
    this
}
