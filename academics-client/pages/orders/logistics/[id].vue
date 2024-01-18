<template>
    {{ order }}
    <div v-if="error" class="alert alert-danger">Error: {{ error.message }}</div>
    <div v-else>
        <h2 class="mb-4">Order {{ order.id }}</h2>


        <!--make text boxes-->
        <div>Order Customer: {{ order.customerUsername }}</div>
        <div>
      Order Status:
      <select name="Status" id="status" v-model="selectedStatus">
        <option value="PENDENTE">PENDENTE</option>
        <option value="ENVIADA">ENVIADA</option>
        <option value="EM_TRANSITO">EM TRANSITO</option>
        <option value="ENTREGUE">ENTREGUE</option>
        <option value="CANCELADA">CANCELADA</option>
      </select>
    </div>
        <div>Order Total Weight: {{ calculateTotalWeight() }} kg</div>
        <div>Total of Products:{{ order.products.length }} </div>
        <!-- Listagem de Produtos -->
        <div>
            <h3>Products of Order {{ order.id }}</h3>
            <ul>
                <li v-for="product in order.products" :key="product.id">
                    {{ product.productName }}
                </li>
            </ul>
        </div>





    </div>
    <button @click.prevent="refresh" class="btn btn-primary">Refresh Data</button>
    <button @click.prevent="updateOrderStatus" class="btn btn-success">Update Data</button>
</template>
<script setup>
const route = useRoute()
const id = route.params.id


const selectedStatus = ref('');

const config = useRuntimeConfig()
const api = config.public.API_URL
const { data: order, error: orderErr } = await
useFetch(`${api}/orders/${id}`)
/*
const { data: subjects, error: subjectsErr } = await
useFetch(`${api}/students/${username}/subjects`)
*/
const messages = ref([])
if (orderErr.value) messages.value.push(orderErr.value)
//if (subjectsErr.value) messages.value.push(subjectsErr.value)


const calculateTotalWeight = () => {
    if (order.value && order.value.products) {
        return order.value.products.reduce(
            (totalWeight, product) => totalWeight + parseFloat(product.productWeight),
            0
        );
    } else {
        return 0;
    }
};

const updateOrderStatus = async () => {
  const newStatus = selectedStatus.value;
  console.log(newStatus);

  const apiUrl = `${api}/orders/${id}`;

  // Atualize a ordem no servidor usando o método PUT
  const response = await useFetch(apiUrl, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json', // Indica que você está enviando dados no formato JSON
    },
    body: JSON.stringify({
      status: newStatus,
    }),
    // Adicione qualquer outro cabeçalho ou configuração necessário para a sua API
  });


};

const setOrderStatus = () => {
    // Verifica se a ordem foi carregada
    if (order.value) {
        // Define o status selecionado com base no status atual da ordem
        selectedStatus.value = order.value.status;
    }
};
onMounted(() => {
    setOrderStatus();
});

</script>