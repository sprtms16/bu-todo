import { createRouter, createWebHistory } from "vue-router";
import TodoList from "../views/TodoList.vue";
import TodoForm from "../views/TodoForm.vue";

const routes = [
  {
    path: "/",
    name: "TodoList",
    component: TodoList,
  },
  {
    path: "/form",
    name: "TodoForm",
    component: TodoForm,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
