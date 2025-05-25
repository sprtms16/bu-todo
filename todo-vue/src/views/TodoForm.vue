<script>
import { todoApi } from "@/api";

export default {
  name: "TodoForm",
  data() {
    return {
      newTodo: {
        title: "",
        completed: false,
      },
      loading: false,
      error: null,
    };
  },
  methods: {
    async addTodo() {
      // 입력값 유효성 검사
      if (!this.newTodo.title.trim()) {
        this.error = "할 일을 입력해주세요";
        return;
      }

      this.loading = true;
      this.error = null;

      try {
        // API 호출로 새 할 일 추가
        await todoApi.createTodo(this.newTodo);

        // 입력 필드 초기화
        this.newTodo.title = "";

        // 부모 컴포넌트에 이벤트 발생시켜 목록 갱신
        this.$emit("todo-added");
      } catch (err) {
        console.error("할 일 추가 실패:", err);
        this.error = "할 일을 추가하는 중 오류가 발생했습니다";
      } finally {
        this.loading = false;
      }
    },
  },
};
</script>

<template>
  <div class="todo-form">
    <form @submit.prevent="addTodo">
      <div class="input-group">
        <input
          type="text"
          v-model="newTodo.title"
          placeholder="새로운 할 일을 입력하세요"
          class="form-control"
          :disabled="loading"
          required
        />
        <button
          type="submit"
          class="btn btn-primary"
          :disabled="loading || !newTodo.title.trim()"
        >
          {{ loading ? "추가 중..." : "추가" }}
        </button>
      </div>
      <div v-if="error" class="error-message">
        {{ error }}
      </div>
    </form>
  </div>
</template>

<style scoped></style>
