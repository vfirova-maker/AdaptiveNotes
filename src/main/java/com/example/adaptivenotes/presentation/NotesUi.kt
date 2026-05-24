package com.example.adaptivenotes.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.adaptivenotes.R
import com.example.adaptivenotes.data.Note
import com.example.adaptivenotes.data.sampleNotes

// 1. Компонент: Список усіх нотаток
@Composable
fun NotesList(
    notes: List<Note>,
    onClick: (Note) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxSize().padding(8.dp)) {
        items(notes, key = { it.id }) { note ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { onClick(note) }
                    .semantics {
                        // Доступність: опис для екранних дикторів та сканера
                        contentDescription = "Note ${note.title}"
                    }
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        text = note.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = note.text,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// 2. Компонент: Детальний перегляд однієї нотатки
@Composable
fun NoteDetails(
    note: Note,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    showBackButton: Boolean = true
) {
    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        if (showBackButton) {
            Button(
                onClick = onBack,
                modifier = Modifier.minimumInteractiveComponentSize() // Мішень кліку не менше 48dp
            ) {
                Text(stringResource(id = R.string.back))
            }
            Spacer(Modifier.height(16.dp))
        }
        Text(
            text = note.title,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = note.text,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

// 3. Макет для ТЕЛЕФОНУ (Compact)
@Composable
fun NotesPhoneLayout() {
    var selectedNote by remember { mutableStateOf<Note?>(null) }

    if (selectedNote == null) {
        NotesList(
            notes = sampleNotes,
            onClick = { selectedNote = it }
        )
    } else {
        NoteDetails(
            note = selectedNote!!,
            onBack = { selectedNote = null }
        )
    }
}

// 4. Макет для ПЛАНШЕТА (Medium / Expanded)
@Composable
fun NotesTabletLayout() {
    var selectedNote by remember { mutableStateOf(sampleNotes.first()) }

    Row(Modifier.fillMaxSize()) {
        NotesList(
            notes = sampleNotes,
            onClick = { selectedNote = it },
            modifier = Modifier.weight(1f)
        )
        NoteDetails(
            note = selectedNote,
            onBack = {},
            modifier = Modifier.weight(2f),
            showBackButton = false // На планшеті бічна панель завжди відкрита, кнопка "Назад" не потрібна
        )
    }
}

// 5. Головний адаптивний розподілювач
@Composable
fun AdaptiveNotesApp(widthSizeClass: WindowWidthSizeClass) {
    when (widthSizeClass) {
        WindowWidthSizeClass.Compact -> NotesPhoneLayout()
        WindowWidthSizeClass.Medium,
        WindowWidthSizeClass.Expanded -> NotesTabletLayout()
    }
}

// 6. Адаптивні Previews для інтерактивного перегляду в Android Studio
@Preview(name = "Phone Layout", widthDp = 360, heightDp = 640, showBackground = true)
@Composable
fun PhonePreview() {
    AdaptiveNotesApp(WindowWidthSizeClass.Compact)
}

@Preview(name = "Tablet Layout", widthDp = 1280, heightDp = 800, showBackground = true)
@Composable
fun TabletPreview() {
    AdaptiveNotesApp(WindowWidthSizeClass.Expanded)
}