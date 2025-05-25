<script>
import { todoApi } from "@/api";

export default {
  name: "TodoItem",
  props: {
    todo: {
      type: Object,
      required: true,
    },
  },
  methods: {
    async toggleStatus() {
      try {
        // 현재 상태의 반대 값으로 업데이트
        const updatedTodo = {
          ...this.todo,
          completed: !this.todo.completed,
        };

        // API 호출하여 완료 상태 변경
        await todoApi.updateTodo(this.todo.id, updatedTodo);

        // 부모 컴포넌트에 상태 변경 알림
        this.$emit("status-changed", updatedTodo);
      } catch (error) {
        console.error("상태 변경 실패:", error);
        // 오류 발생 시 사용자에게 알림
        alert("상태를 변경하는 중 오류가 발생했습니다");
      }
    },
  },
};
</script>

<template>
  <div class="todo-item" :class="{ completed: todo.completed }">
    <div class="todo-content">
      <input
        type="checkbox"
        :checked="todo.completed"
        @change="toggleStatus"
        class="todo-checkbox"
      />
      <span class="todo-title">{{ todo.title }}</span>
    </div>
    <button @click="$emit('delete-todo', todo.id)" class="delete-btn">
      삭제
    </button>
  </div>
</template>

<style scoped></style>
