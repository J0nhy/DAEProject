<template>
  <div v-if="error" class="alert alert-danger">Error: {{ error.message }}</div>
  <div v-else>
    <h2 class="mb-4">Orders</h2>
    <table class="table table-bordered table-hover">
      <thead class="thead-dark">
        <tr>
          <th>ID</th>
          <th>Status</th>
          <th>Customer Username</th>
          <th>Logistics Operator Company</th>
          <th>Actions</th>

        </tr>
      </thead>
      <tbody>
        <tr v-for="order in orders" :key="order.id">
          <td>{{ order.id }}</td>
          <td>{{ order.status }}</td>
          <td>{{ order.customerUsername }}</td>
          <td>
            <span v-if="order.logisticsOperators">
              {{ order.logisticsOperators.company }}

            </span>
            <span v-else class="text-muted">None
            </span>

          </td>
          <td class="d-flex gap-2">
            <nuxt-link v-if="(order.status = 'Pending') && (!order.logisticsOperators)"
              :to="`customers/${order.id}`">
              <button class="btn btn-success"><i class="bi bi-check"></i>Completar</button>


              <button class="btn btn-danger"><i class="bi bi-x"></i>Cancelar</button>

            </nuxt-link>

          </td>
        </tr>
      </tbody>
    </table>
  </div>
  <button @click.prevent="refresh" class="btn btn-primary">Refresh Data</button>
</template>
    
<script setup>
// stuff for logged user
import { useAuthStore } from "~/store/auth-store.js";
const authStore = useAuthStore()

const { token, user } = storeToRefs(authStore)


const config = useRuntimeConfig()
const api = config.public.API_URL
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


const { data: orders, error, refresh } = await useFetch(`${api}/orders/customer/${user.value.username}`)
</script>
    