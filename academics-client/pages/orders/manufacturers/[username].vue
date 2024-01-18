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
                                        <select v-model="selectedCompany" class="form-select">
                                            <option disabled value="1" selected>Please select one</option>
                                            <option v-for="operator in logisticsOperators" :key="logisticsOperators.id"
                                                :value="operator.company">
                                                {{ operator.company }}
                                            </option>
                                        </select>
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
let selectedCompany=ref(["1"]);
if (error.value) messages.value.push(error.value)
</script>