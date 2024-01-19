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
            <th>Actions</th>
  
          </tr>
        </thead>
        <tbody>
          <tr v-for="order in orders" :key="order.id">
            <td>{{ order.id }}</td>
            <td>{{ order.status }}</td>
            <td>{{ order.customerUsername }}</td>
 
            <td class="d-flex gap-2">
              <nuxt-link @click.prevent="transportOrder(order.id)">
                <button v-if="order.status = 'Pending'" class="btn btn-primary"><i
                    class="bi bi-airplane-fill"></i>Transportar</button>
              </nuxt-link>
              <nuxt-link  @click.prevent="deliverOrder(order.id)">
                <button v-if="order.status = 'Pending'" class="btn btn-success"><i
                    class="bi bi-check-lg"></i>Entregue</button>
              </nuxt-link>
         
              <nuxt-link @click.prevent="cancelOrder(order.id)">
                <button v-if="order.status = 'Pending'" class="btn btn-danger"><i
                    class="bi bi-x"></i>Cancelar</button>
              </nuxt-link>
              
              <nuxt-link :to="`logistics/${order.id}`">
                <button v-if="order.status = 'Pending'" class="btn btn-Dark"><i
                    class="bi bi-search"></i></button>
              </nuxt-link>
              </td>
          </tr>
        </tbody>
      </table>
    </div>
    <button @click.prevent="refresh" class="btn btn-primary">Refresh Data</button>
  </template>

  <script setup>
  import { useAuthStore } from "~/store/auth-store.js";
  const authStore = useAuthStore()

  const {token, user} = storeToRefs(authStore)


  const config = useRuntimeConfig()
  const api = config.public.API_URL
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
    //console.log(user.value)
    
  })
  const { data: orders, error, refresh } = await useFetch(`${api}/orders/logistics-operator/${user.value.username}`)



  const cancelOrder = async (id) => {
    const apiUrl = `${api}/orders/${id}`;
    // Atualize a ordem no servidor usando o método PUT
    const response = await useFetch(apiUrl, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json', // Indica que você está enviando dados no formato JSON
        },
        body: JSON.stringify({
            status: "CANCELADA",
        }),
        // Adicione qualquer outro cabeçalho ou configuração necessário para a sua API
    });
};
const transportOrder = async (id) => {
    const apiUrl = `${api}/orders/${id}`;
    // Atualize a ordem no servidor usando o método PUT
    const response = await useFetch(apiUrl, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json', // Indica que você está enviando dados no formato JSON
        },
        body: JSON.stringify({
            status: "EM_TRANSITO",
        }),
        // Adicione qualquer outro cabeçalho ou configuração necessário para a sua API
    });

    refresh()
};
const deliverOrder = async (id) => {
    const apiUrl = `${api}/orders/${id}`;
    // Atualize a ordem no servidor usando o método PUT
    const response = await useFetch(apiUrl, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json', // Indica que você está enviando dados no formato JSON
        },
        body: JSON.stringify({
            status: "ENTREGUE",
        }),
        // Adicione qualquer outro cabeçalho ou configuração necessário para a sua API
    });
};

  </script>
    