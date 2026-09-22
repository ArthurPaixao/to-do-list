package carreiras.com.github.todolist.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import carreiras.com.github.todolist.data.Tarefa
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListaTarefasExclusaoConcluidasTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            var tarefas by remember {
                mutableStateOf(
                    listOf(
                        Tarefa(id = 1, titulo = "Estudar Room", descricao = ""),
                        Tarefa(id = 2, titulo = "Enviar atividade", descricao = "", concluida = true),
                        Tarefa(id = 3, titulo = "Comprar caderno", descricao = "", concluida = true)
                    )
                )
            }
            var confirmando by remember { mutableStateOf(false) }

            ListaTarefasContent(
                tarefas = tarefas,
                onNovaTarefa = {},
                onEditarTarefa = {},
                onCheckedChange = { _, _ -> },
                onDeletar = {},
                confirmandoExclusaoConcluidas = confirmando,
                onLimparConcluidas = { confirmando = true },
                onConfirmarExclusaoConcluidas = {
                    tarefas = tarefas.filterNot { it.concluida }
                    confirmando = false
                },
                onCancelarExclusaoConcluidas = { confirmando = false }
            )
        }
    }

    @Test
    fun cancelar_mantemTarefasConcluidas() {
        composeTestRule.onNodeWithText("Limpar concluídas").performClick()
        composeTestRule.onNodeWithText("Excluir tarefas concluídas?").assertIsDisplayed()
        composeTestRule.onNodeWithText("• Enviar atividade").assertIsDisplayed()
        composeTestRule.onNodeWithText("• Comprar caderno").assertIsDisplayed()

        composeTestRule.onNodeWithText("Cancelar").performClick()

        composeTestRule.onNodeWithText("Excluir tarefas concluídas?").assertDoesNotExist()
        composeTestRule.onNodeWithText("Enviar atividade").assertIsDisplayed()
        composeTestRule.onNodeWithText("Comprar caderno").assertIsDisplayed()
    }

    @Test
    fun excluir_removeSomenteConcluidas() {
        composeTestRule.onNodeWithText("Limpar concluídas").performClick()

        composeTestRule.onNodeWithText("Excluir").performClick()

        composeTestRule.onNodeWithText("Excluir tarefas concluídas?").assertDoesNotExist()
        composeTestRule.onNodeWithText("Enviar atividade").assertDoesNotExist()
        composeTestRule.onNodeWithText("Comprar caderno").assertDoesNotExist()
        composeTestRule.onNodeWithText("Estudar Room").assertIsDisplayed()
        composeTestRule.onNodeWithText("Limpar concluídas").assertDoesNotExist()
    }
}
