<template>
  <div v-if="error" class="alert alert-danger">Error: {{ error.message }}</div>
  <div v-else>
    <h2 class="mb-4">Orders</h2>
    <table class="table table-bordered table-hover">
      <thead class="thead-dark">
        <tr>
          <th>Status</th>
          <th>Logistics Operator Company</th>
          <th>Actions</th>

        </tr>
      </thead>
      <tbody>
        <tr v-for="order in orders" :key="order.id">
          <td>{{ order.status }}</td>
          <td>
            <span v-if="order.logisticsOperatorsUsername">
              {{ order.logisticsOperatorsUsername }}

            </span>
            <span v-else class="text-muted">None
            </span>

          </td>
          <td class="d-flex gap-2">
            <nuxt-link :to="`customers/${order.id}`">
              <button v-if="order.status !== 'ENTREGUE' && order.status !== 'CANCELADA' && order.status !== 'PENDENTE'" class="btn btn-Dark"><i
                  class="bi bi-search"></i></button>
            </nuxt-link>

          </td>
        </tr>
      </tbody>
    </table>
  </div>
  <button @click.prevent="goBack" class="btn btn-secondary" style="margin-right: 5px;">Voltar</button>

  <button @click.prevent="refresh" class="btn btn-primary">Refresh Data</button>
</template>
    
<script setup>
import { useAuthStore } from "~/store/auth-store.js";
import { useRouter } from 'vue-router';

const router = useRouter();
const authStore = useAuthStore()

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

})

watch(user, () => {
  if (user.value) {
    isLoading.value = false;

    loadOrders()
    //refresh()

  } else {
    router.push('/auth/login');
  }


}
)
const orders = ref([]);


//F5 e nao dar erro
const loadOrders = async () => {
  token.value = localStorage.getItem('token')
  const { data: order, error, refresh } = await useFetch(`${api}/orders/customer/${user.value.username}`, {
    headers: {
      'Authorization': `Bearer ${token.value}`
    }
  });
  orders.value = order._value;
  return order._value;
}

const refresh = async () => {
  const { data: order, error, refresh } = await useFetch(`${api}/orders/logistics-operator/${user.value.username}`, {
    headers: {
      'Authorization': `Bearer ${token.value}`
    }
  });
  orders.value = order._value;
  return order._value;
}


const goBack = () => {
  router.go(-1); // Isso voltará uma página na história do navegador
};

</script>