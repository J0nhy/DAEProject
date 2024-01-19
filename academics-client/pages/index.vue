<template>
    <div>
        <h1>Welcome to Order Management</h1>
        Manage your resources:
        <div style="padding-left: 14px; margin-right: 800px;">
            <template v-if="userRole === 'Customer'">
                <nuxt-link to="orders/customers">Customers</nuxt-link><br>
            </template>
            <template v-if="p === 'LogisticsOperator'">
                <nuxt-link to="/logistics">Logistic Operators</nuxt-link><br>
            </template>
            <template v-if="userRole === 'Manufacturer'">
                <nuxt-link to="orders/manufacturers">Manufacturers</nuxt-link><br>
            </template>
            <hr>
            <template v-if="userRole === 'Customer'">
                <nuxt-link to="/orderCustomer">Customer Orders</nuxt-link><br>
            </template>
            <template v-if="userRole === 'LogisticsOperator'">
                <nuxt-link to="/orderLogistic">Logistic Orders</nuxt-link><br>
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
