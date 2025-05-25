<script>
import { todoApi } from "@/api";
import TodoItem from "@/components/TodoItem";

export default {
  components: {
    TodoItem,
  },
  data() {
    return {
      todos: [],
      filter: "all",
    };
  },
  computed: {
    filteredTodos() {
      if (this.filter === "all") {
        return this.todos;
      }
      return this.todos.filter((todo) => todo.completed === this.filter);
    },
  },
  methods: {
    async fetchTodos() {
      try {
        const response = await todoApi.getAllTodos();
        this.todos = response.data;
      } catch (error) {
        console.error("할 일 목록 조회 실패:", error);
      }
    },
    filterTodos(status) {
      this.filter = status;
    },
    async toggleTodoStatus(todo) {
      try {
        await todoApi.updateTodo(todo.id, {
          ...todo,
          completed: !todo.completed,
        });
        await this.fetchTodos();
      } catch (error) {
        console.error("상태 변경 실패:", error);
      }
    },
    async deleteTodo(id) {
      try {
        await todoApi.deleteTodo(id);
        await this.fetchTodos();
      } catch (error) {
        console.error("삭제 실패:", error);
      }
    },
  },
  created() {
    this.fetchTodos();
  },
};
</script>

<template>
  <div class="todo-list">
    <h2>할 일 목록</h2>
    <div class="filters">
      <button @click="filterTodos('all')">전체</button>
      <button @click="filterTodos(false)">미완료</button>
      <button @click="filterTodos(true)">완료</button>
    </div>
    <ul v-if="filteredTodos.length > 0">
      <li
        v-for="todo in filteredTodos"
        :key="todo.id"
        :class="{ completed: todo.completed }"
      >
        <todo-item
          :todo="todo"
          @toggle-completed="toggleTodoStatus"
          @delete-todo="deleteTodo"
        >
        </todo-item>
      </li>
    </ul>
    <p v-else>할 일이 없습니다.</p>
  </div>
</template>

<style scoped></style>
