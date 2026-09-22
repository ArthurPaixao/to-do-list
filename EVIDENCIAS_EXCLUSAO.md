# Evidências — Confirmação de exclusão de tarefas

Capturas feitas no emulador Pixel 8 (Android 16). A tarefa usada no teste foi **Revisar arquitetura MVVM**, que fica no meio da lista, para mostrar que apenas ela é removida e que as demais continuam com prazo, destaque de atraso e conclusão preservados.

## 1. Lista antes da exclusão

Quatro tarefas cadastradas: uma com prazo vencido (em vermelho), uma concluída e duas pendentes.

<img src="docs/images/exclusao/01-lista-antes-da-exclusao.png" alt="Lista antes da exclusão" width="300">

## 2. Diálogo aberto com a tarefa selecionada

Ao tocar na lixeira de **Revisar arquitetura MVVM**, o `AlertDialog` do Material 3 aparece sobre a lista, informando que a tarefa será excluída e exibindo o título dela.

<img src="docs/images/exclusao/02-dialogo-aberto.png" alt="Diálogo de confirmação aberto" width="300">

## 3. Resultado ao cancelar

Tocando em **Cancelar**, o diálogo fecha e a lista permanece igual.

<img src="docs/images/exclusao/03-resultado-ao-cancelar.png" alt="Lista após cancelar" width="300">

## 4. Nova abertura do diálogo

Tocando novamente na lixeira da mesma tarefa, o diálogo é reaberto.

<img src="docs/images/exclusao/04-dialogo-reaberto.png" alt="Diálogo reaberto" width="300">

## 5. Resultado após confirmar a exclusão

Tocando em **Excluir**, o diálogo fecha e somente **Revisar arquitetura MVVM** é removida.

<img src="docs/images/exclusao/05-resultado-apos-excluir.png" alt="Lista após confirmar a exclusão" width="300">

## Resumo da implementação

- `TarefaViewModel` guarda a tarefa selecionada em `tarefaParaExcluir` (`StateFlow<Tarefa?>`) e expõe `solicitarExclusao`, `cancelarExclusao` e `confirmarExclusao`.
- `ListaTarefasScreen` observa esse estado e exibe o `ConfirmarExclusaoDialog` sobre a própria lista, sem nova rota de navegação.
- Previews adicionadas: **Confirmação de exclusão** (lista com o diálogo aberto) e **Diálogo de exclusão**.
- Testes de UI em `ListaTarefasExclusaoTest` cobrem o cancelamento e a exclusão apenas da tarefa selecionada.
