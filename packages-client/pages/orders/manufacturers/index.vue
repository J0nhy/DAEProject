<template>
  <div v-if="error!='0'" class="alert alert-danger">Error: {{ error.message }}</div>
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
            <span v-if="order.logisticsOperatorsUsername">
              {{ order.logisticsOperatorsUsername }}

            </span>
            <span v-else class="text-muted">None
            </span>

          </td>
          <td class="d-flex gap-2">
            <nuxt-link v-if="!order.logisticsOperatorsUsername" :to="`manufacturers/${order.id}`">
              <button class="btn btn-success"><i class="bi bi-check"></i>Completar</button>
            </nuxt-link>

            <nuxt-link v-if="order.status != 'ENTREGUE' && order.status!='CANCELADA'" :to="`manufacturers/${order.id}`">
              <button  @click.prevent="cancelOrder(order.id, order.customerUsername)" class="btn btn-danger"><i class="bi bi-x"></i>Cancelar</button>
            </nuxt-link>
            <nuxt-link :to="`manufacturers/sensors/${order.id}`">
              <button v-if="order.status !== 'ENTREGUE' && order.status !== 'CANCELADA' && order.status !== 'PENDENTE'" class="btn btn-Dark"><i class="bi bi-search"></i></button>
            </nuxt-link>

          </td>
        </tr>
      </tbody>
    </table>
  </div>
  <button @click.prevent="goBack" class="btn btn-secondary" style="margin-right: 5px;">Voltar</button>

  <button @click.prevent="loadOrders" class="btn btn-primary">Refresh Data</button>
</template>
    
<script setup>
import { useAuthStore } from "~/store/auth-store.js";
import { useRouter } from 'vue-router';

const router = useRouter();
const authStore = useAuthStore()
let orders = ref([]);
let error = ref([]);
error.value='0'

const { token, user } = storeToRefs(authStore)

const isLoading = ref(true);

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

  if (user.value) {
    isLoading.value = false;
    
  }
  loadOrders()
})



watch(user, () => {
  if (user.value) {
    isLoading.value = false;
    
    loadOrders()
    //refresh()

  }else{
    router.push('/auth/login');
  }


}
)

const loadOrders = async () => {
  token.value = localStorage.getItem('token')
  const { data: orderss, errors, refresh } = await useFetch(`${api}/orders`, {
  headers: {
    'Authorization': `Bearer ${token.value}`
  },
  
});
orders.value=orderss.value
}

const cancelOrder = async (id, cusUsername) => {
  const response = await useFetch(`${api}/orders/${id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json', 
      'Authorization': `Bearer ${token.value}`

    },
    body: JSON.stringify({
      status: "CANCELADA",
    }),
    
    
  });
  send("A sua encomenda foi cancelada!", cusUsername)

  loadOrders()
}

async function send(text, cuUsername) {
  
  const { error: sendError } = await useFetch(
    `${api}/users/${cuUsername}/email/send`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json', // Indica que você está enviando dados no formato JSON
      'Authorization': `Bearer ${token.value}`
    }, 
    body: JSON.stringify({
      subject: text,
      message: "Order Status",
    }),
    })
    

}



const goBack = () => {
  router.go(-1); 
};
</script>
    