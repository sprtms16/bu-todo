import axios from "axios";

// axios 인스턴스 생성
const apiClient = axios.create({
  baseURL: "http://localhost:8080/api",
  headers: {
    "Content-Type": "application/json",
  },
  timeout: 10000,
});

// 요청 인터셉터
apiClient.interceptors.request.use(
  (config) => {
    // 요청 전에 처리할 작업
    console.log("API 요청:", config);
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// 응답 인터셉터
apiClient.interceptors.response.use(
  (response) => {
    // 응답 데이터 가공
    return response;
  },
  (error) => {
    console.error("API 오류:", error);
    return Promise.reject(error);
  }
);

// Todo API 함수들
export const todoApi = {
  // 모든 할 일 목록 조회
  getAllTodos() {
    return apiClient.get("/todos");
  },

  // 완료 상태별 할 일 목록 조회
  getTodosByCompleted(completed) {
    return apiClient.get(`/todos?completed=${completed}`);
  },

  // 할 일 추가
  createTodo(todo) {
    return apiClient.post("/todos", todo);
  },

  // 할 일 수정
  updateTodo(id, todo) {
    return apiClient.put(`/todos/${id}`, todo);
  },

  // 할 일 삭제
  deleteTodo(id) {
    return apiClient.delete(`/todos/${id}`);
  },
};
