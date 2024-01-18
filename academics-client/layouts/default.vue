<script setup>
import { useAuthStore } from "~/store/auth-store.js";
const router = useRouter()


const authStore = useAuthStore()
const {token, user} = storeToRefs(authStore)

function logout() {
  authStore.logout()
  router.push('/')

}

onMounted(() => {
    // check if token exists in local storage
    const tokenLocal = localStorage.getItem('token')
    const userLocal = localStorage.getItem('user')
    if (userLocal) {
        user.value = JSON.parse(userLocal)
        
    }
    if (tokenLocal) {
        token.value = tokenLocal
    }
    if (!token.value) {
        navigateTo('/auth/login')
    }
    console.log(user.value)
  })
</script>

<template>
  <nav class="navbar">
    <div class="nav-links">
      <nuxt-link v-if="!token" to="/auth/login" class="nav-link">Login</nuxt-link>
    </div>
    <div class="nav-actions">
      <a v-if="token" href="#" @click.prevent="logout" class="nav-link">Logout</a>
    </div>
  </nav>

  <div class="nav-slot">
      <slot />
    </div>
</template>



<style scoped>
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  background-color: #333;
  color: #fff;
}

.nav-links, .nav-actions {
  display: flex;
  align-items: center;
}

.nav-link {
  color: #fff;
  text-decoration: none;
  margin-right: 10px;
}

.nav-divider {
  margin: 0 10px;
}

.nav-slot {
  margin-left: auto;
}
</style>
