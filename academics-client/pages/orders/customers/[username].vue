<template>
    <div v-if="error" class="alert alert-danger">There was an error</div>
    <div v-else>
        <div class="mb-4">
            <h2>Order Details</h2>
        </div>
        <div class="container">
            <div class="row">
                <div class="col-8">
                    <div class="table-responsive">
                        <table class="table table-bordered">
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
                                        <select disabled style="text-align: center;" v-model="selectedCompany" class="form-select">
                                            <option disabled value="0" selected>Please select one</option>
                                            <option  v-for="operator in logisticsOperators" :key="logisticsOperators.id"
                                                :value="operator.username">
                                                {{ operator.company }}
                                            </option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">Package Primary</th>
                                    <td style="text-align: center;"><strong>Material:</strong><select disabled
                                            style="text-align: center;" v-model="selectedPrimaryType" class="form-select">
                                            <option disabled value="1" selected>None</option>
                                        </select>
                                        <strong>Quant:</strong>
                                        <select disabled style="text-align: center;" v-model="selectedPrimaryQuant" class="form-select">
                                            <option value="0">0</option>
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">Package Secundary</th>
                                    <td style="text-align: center;"><strong>Material:</strong><select disabled
                                            style="text-align: center;" v-model="selectedSecundaryType" class="form-select">
                                            <option disabled value="1" selected>None</option>
                                        </select>
                                        <strong>Quant:</strong>
                                        <select disabled style="text-align: center;" v-model="selectedSecundaryQuant" class="form-select">
                                            <option value="0">0</option>
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">Package Terciary</th>
                                    <td style="text-align: center;"><strong>Material:</strong><select disabled
                                            style="text-align: center;" v-model="selectedTerciaryType" class="form-select">
                                            <option value="1" selected>None</option>
                                        </select>
                                        <strong>Quant:</strong>
                                        <select disabled style="text-align: center;" v-model="selectedTerciaryQuant" class="form-select">
                                            <option value="0" selected>0</option>
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>                                        </select>
                                    </td>
                                </tr>

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

    </div>
</template>
  
  
  
<script setup>
const route = useRoute()
const username = route.params.username

const config = useRuntimeConfig()
const api = config.public.API_URL
const { data: order, error } = await
useFetch(`${api}/orders/${username}`)

const { data: logisticsOperators } = await
useFetch(`${api}/logisticsoperators`)

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

if (error.value) messages.value.push(error.value)
</script>