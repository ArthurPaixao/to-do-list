package carreiras.com.github.todolist.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import carreiras.com.github.todolist.data.Tarefa
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListaTarefasExclusaoTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            var tarefas by remember {
                mutableStateOf(
                    listOf(
                        Tarefa(id = 1, titulo = "Estudar Room", descricao = ""),
                        Tarefa(id = 2, titulo = "Enviar atividade", descricao = "")
                    )
                )
            }
            var tarefaParaExcluir by remember { mutableStateOf<Tarefa?>(null) }

            ListaTarefasContent(
                tarefas = tarefas,
                onNovaTarefa = {},
                onEditarTarefa = {},
                onCheckedChange = { _, _ -> },
                onDeletar = { tarefaParaExcluir = it },
                tarefaParaExcluir = tarefaParaExcluir,
                onConfirmarExclusao = {
                    val selecionada = tarefaParaExcluir
                    tarefas = tarefas.filterNot { it.id == selecionada?.id }
                    tarefaParaExcluir = null
                },
                onCancelarExclusao = { tarefaParaExcluir = null }
            )
        }
    }

    @Test
    fun cancelar_fechaDialogoSemAlterarLista() {
        composeTestRule.onAllNodesWithContentDescription("Deletar tarefa")[1].performClick()
        composeTestRule.onNodeWithText("Excluir tarefa?").assertIsDisplayed()

        composeTestRule.onNodeWithText("Cancelar").performClick()

        composeTestRule.onNodeWithText("Excluir tarefa?").assertDoesNotExist()
        composeTestRule.onNodeWithText("Estudar Room").assertIsDisplayed()
        composeTestRule.onNodeWithText("Enviar atividade").assertIsDisplayed()
    }

    @Test
    fun excluir_removeSomenteTarefaSelecionada() {
        composeTestRule.onAllNodesWithContentDescription("Deletar tarefa")[1].performClick()

        composeTestRule.onNodeWithText("Excluir").performClick()

        composeTestRule.onNodeWithText("Excluir tarefa?").assertDoesNotExist()
        composeTestRule.onNodeWithText("Enviar atividade").assertDoesNotExist()
        composeTestRule.onNodeWithText("Estudar Room").assertIsDisplayed()
    }
}
