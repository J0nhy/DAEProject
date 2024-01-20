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
            <nuxt-link @click.prevent="transportOrder(order.id, order.customerUsername)">
              <button v-if="order.status === 'ENVIADA'" class="btn btn-primary"><i
                  class="bi bi-airplane-fill"></i>Transportar</button>
            </nuxt-link>
            <nuxt-link @click.prevent="deliverOrder(order.id, order.customerUsername)">
              <button v-if="order.status === 'EM_TRANSITO'" class="btn btn-success"><i
                  class="bi bi-check-lg"></i>Entregue</button>
            </nuxt-link>
            <nuxt-link @click.prevent="cancelOrder(order.id, order.customerUsername)">
              <button v-if="order.status !== 'ENTREGUE' && order.status !== 'CANCELADA'" class="btn btn-danger">
                <i class="bi bi-x"></i>Cancelar
              </button>
            </nuxt-link>
            <nuxt-link :to="`logistics/${order.id}`">
              <button v-if="order.status !== 'ENTREGUE' && order.status !== 'CANCELADA'" class="btn btn-Dark"><i
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
  const { data: order, error, refresh } = await useFetch(`${api}/orders/logistics-operator/${user.value.username}`, {
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



const cancelOrder = async (id, user) => {
  const apiUrl = `${api}/orders/${id}`;
  // Atualize a ordem no servidor usando o método PUT
const response = await useFetch(apiUrl, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json', // Indica que você está enviando dados no formato JSON
      'Authorization': `Bearer ${token.value}`
    },
    body: JSON.stringify({
      status: "CANCELADA",
    }),
    // Adicione qualquer outro cabeçalho ou configuração necessário para a sua API
  });
  refresh()
  send("A sua encomenda foi cancelada!", user)
  alert("Foi enviado para o Customer um email com a informação que a sua encomenda foi cancelada!")

};
const transportOrder = async (id, user) => {
  const apiUrl = `${api}/orders/${id}`;
  // Atualize a ordem no servidor usando o método PUT
    const response = await useFetch(apiUrl, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json', // Indica que você está enviando dados no formato JSON
      'Authorization': `Bearer ${token.value}`

    },
    body: JSON.stringify({
      status: "EM_TRANSITO",
    }),
    // Adicione qualquer outro cabeçalho ou configuração necessário para a sua API
  });

  refresh()
  send("A sua encomenda está a ser transportada com sucesso!", user)
  alert("Foi enviado para o Customer um email com a informação que a sua encomenda está a ser transportada com sucesso!")
};
const deliverOrder = async (id, user) => {
  const apiUrl = `${api}/orders/${id}`;
// Atualize a ordem no servidor usando o método PUT
  const response = await useFetch(apiUrl, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json', // Indica que você está enviando dados no formato JSON
      'Authorization': `Bearer ${token.value}`

    },
    body: JSON.stringify({
      status: "ENTREGUE",
    }),
    // Adicione qualquer outro cabeçalho ou configuração necessário para a sua API
  });
  refresh()
  send("A sua encomenda foi entregue com sucesso!", user)
  alert("Foi enviado para o Customer um email com a informação que a sua encomenda foi entregue com sucesso!")
};

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
  router.go(-1); // Isso voltará uma página na história do navegador
};

</script>
    