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
          <th>Logistics Operator</th>
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
              {{ order.logisticsOperators.username }}

            </span>
            <span v-else class="text-muted">None
            </span>
            
          </td>
          <td class="d-flex gap-2">  
            <button v-if="order.status='Pending'" class="btn btn-success" >
              Completar
                        <i class="bi bi-search"></i>
                    </button>   
                    <button v-if="order.status='Pending'" class="btn btn-danger" >
              Cancelar
                        <i class="bi bi-search"></i>
                    </button>            
          </td>
        </tr>
      </tbody>
    </table>
  </div>
  <button @click.prevent="refresh" class="btn btn-primary">Refresh Data</button>
</template>
  
<script setup>
const config = useRuntimeConfig()
const api = config.public.API_URL
const { data: orders, error, refresh } = await useFetch(`${api}/orders`)
</script>
  