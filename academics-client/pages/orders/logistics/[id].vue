<template>
    <!-- {{ order }} -->
    <!--{{ order.packages }}-->
    <div v-if="error" class="alert alert-danger">Error: {{ error.message }}</div>
    <div v-else>
        <h2 class="mb-4">Sensors of Order {{ order.id }}</h2>

        <div class="container">
            <div class="row">
                <div class="col-8">
                    <div class="table-responsive">
                        <!--{{ sensors.sensors }}-->
                        <table class="table table-bordered table-hover">
                            <tbody>
                                <tr v-for="sensor in sensors.sensors" :key="sensor.id">
                                    <th scope="row">Sensor Type</th>
                                    <td style="text-align: center;">{{ sensor.sensorType }}</td>
                                </tr>
                                <tr v-for="sensor in sensors.sensors" :key="sensor.id">
                                    <th scope="row">Sensor Value</th>
                                    <td style="text-align: center;">
        <input type="text" v-model="sensor.value" >
    </td>                                  </tr>
                                <tr v-for="sensor in sensors.sensors" :key="sensor.id">
                                    <th scope="row">Data Type</th>
                                    <td style="text-align: center;">{{ sensor.dataType }}</td>
                                </tr>
                            </tbody>
                        </table>
                       <!-- {{ sensors }}-->
                    </div>
                </div>
            </div>
            <!--{{ order.packages.sensors }}-->

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

const { data: sensors, error: sensorsErr } = await
useFetch(`${api}/packages/${id}/`)


const messages = ref([])
if (orderErr.value) messages.value.push(orderErr.value)



</script>