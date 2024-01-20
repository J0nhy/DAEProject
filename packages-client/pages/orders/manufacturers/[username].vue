<template>
    <div v-if="errorr" class="alert alert-danger">There was an error {{ errorr.value }}</div>
    <div v-else>
        <div class="mb-4">
            <h2>Order Details</h2>
        </div>
        <div class="container">
            <div class="row">
                <div class="col-8">
                    <div class="table-responsive">
                        <div v-if="order">
                        <table class="table table-bordered table-hover">
                            <tbody>

                                <tr>
                                    <th scope="row">Order ID</th>
                                    <td style="text-align: center;">{{ order.id }}</td>
                                </tr>
                                <tr>
                                    <th scope="row">Customer Username</th>
                                    <td style="text-align: center;">{{ order.customerUsername }}</td>
                                </tr>

                                <tr>
                                    <th scope="row">Logistics Operator Company</th>
                                    <td style="text-align: center;" v-if="order.logisticsOperators">{{
                                        order.logisticsOperators.company }}</td>
                                    <td style="text-align: center;" v-if="!order.logisticsOperators">
                                        <select style="text-align: center;" v-model="selectedCompany" class="form-select">
                                            <option disabled value="0" selected>None</option>
                                            <option v-for="operator in logisticsOperators" :key="logisticsOperators.id"
                                                :value="operator.username">
                                                {{ operator.company }}
                                            </option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">Package Primary</th>
                                    <td style="text-align: center;"><strong>Material:</strong><select
                                            style="text-align: center;" v-model="selectedPrimaryType" class="form-select">
                                            <option value="1" selected>None</option>
                                            <option value="MADEIRA" selected>MADEIRA</option>
                                            <option value="CARTAO" selected>CARTAO</option>
                                            <option value="PLASTICO" selected>PLASTICO</option>
                                            <option value="VIDRO" selected>VIDRO</option>
                                            <option value="METAL" selected>METAL</option>
                                            <option value="PAPEL" selected>PAPEL</option>
                                            <option value="OBSIDIAN" selected>OBSIDIAN</option>
                                        </select>
                                        <strong>Quant:</strong>
                                        <select style="text-align: center;" v-model="selectedPrimaryQuant"
                                            class="form-select">
                                            <option value="0">0</option>
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">Package Secondary</th>
                                    <td style="text-align: center;"><strong>Material:</strong><select
                                            style="text-align: center;" v-model="selectedSecundaryType" class="form-select">
                                            <option value="1" selected>None</option>
                                            <option value="MADEIRA" selected>MADEIRA</option>
                                            <option value="CARTAO" selected>CARTAO</option>
                                            <option value="PLASTICO" selected>PLASTICO</option>
                                            <option value="VIDRO" selected>VIDRO</option>
                                            <option value="METAL" selected>METAL</option>
                                            <option value="PAPEL" selected>PAPEL</option>
                                            <option value="OBSIDIAN" selected>OBSIDIAN</option>
                                        </select>
                                        <strong>Quant:</strong>
                                        <select style="text-align: center;" v-model="selectedSecundaryQuant"
                                            class="form-select">
                                            <option value="0">0</option>
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">Package Terciary</th>
                                    <td style="text-align: center;"><strong>Material:</strong><select
                                            style="text-align: center;" v-model="selectedTerciaryType" class="form-select">
                                            <option value="1" selected>None</option>
                                            <option value="MADEIRA" selected>MADEIRA</option>
                                            <option value="CARTAO" selected>CARTAO</option>
                                            <option value="PLASTICO" selected>PLASTICO</option>
                                            <option value="VIDRO" selected>VIDRO</option>
                                            <option value="METAL" selected>METAL</option>
                                            <option value="PAPEL" selected>PAPEL</option>
                                            <option value="OBSIDIAN" selected>OBSIDIAN</option>
                                        </select>
                                        <strong>Quant:</strong>
                                        <select style="text-align: center;" v-model="selectedTerciaryQuant"
                                            class="form-select">
                                            <option value="0" selected>0</option>
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                        </select>
                                    </td>
                                </tr>

                            </tbody>
                        </table>
                        </div>
                        <button @click.prevent="goBack" class="btn btn-secondary" style="margin-right: 5px;">Voltar</button>
                        <button @click.prevent="addPackageAndLogisticsOperators(order.customerUsername)" class="btn btn-primary">Submeter</button>

                    </div>
                </div>
            </div>
        </div>

    </div>
</template>
  
  
  
<script setup>
//

import { useAuthStore } from "~/store/auth-store.js";
import { useRouter } from 'vue-router';

const router = useRouter();
const route = useRoute();
const authStore = useAuthStore()

const id = route.params.username

const errorr = ref(null)

const { token, user } = storeToRefs(authStore)

const isLoading = ref(true);
const order = ref(null);
const logisticsOperators = ref(null);


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
        loadOrders()
        loadLogistics()
    }

})

watch(user, () => {
  if (user.value) {
    isLoading.value = false;
    
    loadOrders()
    loadLogistics()
    //refresh()

  }else{
    router.push('/auth/login');
  }


}
)

//cenas
const username = route.params.username

const loadOrders = async () => {

    token.value = localStorage.getItem('token')

const { data: orderr, error } = await
    useFetch(`${api}/orders/${username}`, {
        headers: {
            'Authorization': `Bearer ${token.value}`
        },
    });
    console.log(orderr)
    order.value = orderr.value
    return orderr
}

const loadLogistics = async () => {

token.value = localStorage.getItem('token')
const { data: logisticsOperatorss } = await
useFetch(`${api}/logisticsoperators`, {
    headers: {
        'Authorization': `Bearer ${token.value}`
    },
});
logisticsOperators.value = logisticsOperatorss.value

return logisticsOperators
}


const messages = ref([])
let selectedCompany = ref([]);
let selectedPrimaryType = ref([]);
let selectedPrimaryQuant = ref([]);
let selectedSecundaryType = ref([]);
let selectedSecundaryQuant = ref([]);
let selectedTerciaryType = ref([]);
let selectedTerciaryQuant = ref([]);

selectedCompany.value = 0;

selectedPrimaryType.value = 1;
selectedPrimaryQuant.value = 0;

selectedSecundaryType.value = 1;
selectedSecundaryQuant.value = 0;

selectedTerciaryType.value = 1;
selectedTerciaryQuant.value = 0;


if (errorr.value) messages.value.push(errorr.value)

const addPackageAndLogisticsOperators = async (cusUsername) => {
    if (selectedPrimaryType.value == 1) {
        selectedPrimaryQuant.value = 0;
    }
    if (selectedSecundaryType.value == 1) {
        selectedSecundaryQuant.value = 0;
    }
    if (selectedTerciaryType.value == 1) {
        selectedTerciaryQuant.value = 0;
    }
    if (selectedPrimaryType.value == 1 && selectedSecundaryType.value == 1 && selectedTerciaryType.value == 1) {
        return alert("Select at least one package")
    }
    else if (selectedPrimaryQuant.value == 0 && selectedSecundaryQuant.value == 0 && selectedTerciaryQuant.value == 0) {
        return alert("Select a quantity for at least one package")
    }
    if (selectedCompany.value == 0) {
        return alert("Select a logistics operator")
    }
    const data = {
        "logisticsOperatorsUsername": selectedCompany.value,
        "packages": [
            {
                "quantity": selectedPrimaryQuant.value,
                "packageMaterial": selectedPrimaryType.value,
                "packageType": "Primary"
            },
            {
                "quantity": selectedSecundaryQuant.value,
                "packageMaterial": selectedSecundaryType.value,
                "packageType": "Secondary"
            },
            {
                "quantity": selectedTerciaryQuant.value,
                "packageMaterial": selectedTerciaryType.value,
                "packageType": "Tertiary"
            }
        ]
    }

    const response = await fetch(`${api}/orders/${username}/completeOrder`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token.value}`
        },
        body: JSON.stringify(data)
    })


    const responseData = await response.json()
    console.log(responseData)
    if (response.ok) {
        alert("Order updated successfully")
        send("A sua encomenda foi enviada com sucesso!", cusUsername)
        navigateTo('/orders/manufacturers')

    } else {
        alert("Error updating order")
    }
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
  router.go(-1); // Isso voltará uma página na história do navegador
};
</script>