<script setup>
import { useAuthStore } from "~/store/auth-store.js";
const router = useRouter();
const authStore = useAuthStore();

function logout() {
  authStore.logout();
  router.push('/');
}
</script>

<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container">
      <nuxt-link class="navbar-brand" to="/">Gestor de Coisas</nuxt-link>

      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ms-auto">
          <li class="nav-item">
            <nuxt-link class="nav-link" to="/auth-test">Test</nuxt-link>
          </li>
          <li class="nav-item">
            <nuxt-link class="nav-link" to="/auth/login">Login</nuxt-link>
          </li>
          <li class="nav-item">
            <nuxt-link class="nav-link" to="/customers">Customers</nuxt-link>
          </li>
        </ul>
        
        <ul class="navbar-nav">
          <li class="nav-item">
            <a href="#" class="nav-link" @click.prevent="logout">Logout</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>

  <div class="container mt-4">
    <slot /> 
  </div>
</template>
