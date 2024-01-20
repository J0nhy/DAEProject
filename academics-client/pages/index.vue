<template>
    <div>
      <h1 class="welcome-heading">Welcome to Order Management</h1>
      <p class="manage-resources">Manage your resources:</p>
      <div class="order-links">
        <template v-if="userRole === 'Manufacturer'">
          <nuxt-link to="orders/manufacturers" class="order-link">Manufacturers Orders</nuxt-link><br>
        </template>
        <hr class="divider">
        <template v-if="userRole === 'Customer'">
          <nuxt-link to="/orders/customers" class="order-link">Customer Orders</nuxt-link><br>
        </template>
        <template v-if="userRole === 'LogisticsOperator'">
          <nuxt-link to="orders/logistics" class="order-link">Logistic Orders</nuxt-link><br>
        </template>
      </div>
    </div>
  </template>
  
<script setup>
import { useAuthStore } from "~/store/auth-store.js";
const router = useRouter()


const authStore = useAuthStore()
const {token, user} = storeToRefs(authStore)

const userRole = ref(null)

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
        userRole.value = user.value.role
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

<style>
/* OrderManagement.css */
.welcome-heading {
  font-size: 24px;
  margin-bottom: 20px;
}

.manage-resources {
  font-size: 16px;
  margin-bottom: 10px;
}

.order-links {
  padding-left: 14px;
  margin-right: 800px;
}

.order-link {
  text-decoration: none;
  color: #007bff;
  font-weight: bold;
}

.divider {
  border: 1px solid #ccc;
  margin-top: 10px;
  margin-bottom: 10px;
}
</style>