<template>
     
  <!--{{ sensors }}-->
  
  <div v-if="error" class="alert alert-danger">Error: {{ error.message }}</div>
  <div v-else>
      <h2 class="mb-4" >Sensors of Order {{ id }}</h2>

      <div class="container">
          <div class="row">
              <div class="col-8">

                  <div v-for="packageGroup in groupedSensors" :key="packageGroup.packageRef">
      <h4 class="mt-3">Package {{ packageGroup.packageRef }}</h4>
      <div class="table-responsive">
          <table class="table table-bordered table-hover">
              <thead>
      <tr>
          <th style="text-align: center;">Sensor Type</th>
          <th style="text-align: center;">Sensor Value</th>
          <th style="text-align: center;">Data Type</th>
          <th style="text-align: center;">Actions</th>
      </tr>
  </thead>
              <tbody>
                  <tr v-for="sensor in packageGroup.sensors" :key="sensor.id">
                      <!-- ... your existing table row code ... -->
                      <td style="text-align: center;">{{ sensor.sensorType }}</td>
          <td style="text-align: center;">
              <input type="text" v-model="sensor.value">
          </td>
          <td style="text-align: center;">{{ sensor.dataType }}</td>
          <td style="text-align: center;">
              <button @click.prevent="updateSensor(sensor.id, sensor.value)" class="btn btn-success">Atualizar</button>
          </td>
                  </tr>
              </tbody>
          </table>
      </div>
  </div>


              </div>
          </div>
          <!--{{ order.packages.sensors }}-->

      </div>


  </div>
  <button @click.prevent="goBack" class="btn btn-secondary" style="margin-right: 5px;">Voltar</button>

  <button @click.prevent="refresh" class="btn btn-primary">Refresh Data</button>
</template>
<script setup>
import { useAuthStore } from "~/store/auth-store.js";
import { useRouter } from 'vue-router';

const router = useRouter();
const route = useRoute()
const id = route.params.id

const sensors = ref([])

const authStore = useAuthStore()
const selectedValue = ref(null)

const { token, user } = storeToRefs(authStore)

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
loadSensors()


})
watch(user, () => {
if (!user.value) {
  router.push('/auth/login');
}else{
  loadSensors()
}
if (user.value.role !== 'Manufacturer') {
    router.push('/');
  }
}
)


const config = useRuntimeConfig()
const api = config.public.API_URL

const loadSensors = async () => {
token.value = localStorage.getItem('token')


const { data: sensorss, error: sensorsErr } = await
useFetch(`${api}/orders/${id}/sensors`, {
headers: {
  'Authorization': `Bearer ${token.value}`
}
})

sensors.value = sensorss.value

return sensors.value

}

const messages = ref([])

const updateSensor = async (sensorId, sensorValue) => {
const response = await fetch(`${api}/sensors/${sensorId}`, {
  method: 'PUT',
  headers: {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${token.value}`
  },
  body: JSON.stringify({
    value: sensorValue
  })
});

// Lógica adicional de tratamento de resposta, se necessário
};

const groupedSensors = computed(() => {
  const grouped = {};
  sensors.value.forEach((sensor) => {
      if (!grouped[sensor.packageRef]) {
          grouped[sensor.packageRef] = { packageRef: sensor.packageRef, sensors: [] };
      }
      grouped[sensor.packageRef].sensors.push(sensor);
  });

  // Convert the object into an array
  return Object.values(grouped);
});


const goBack = () => {
router.go(-1); // Isso voltará uma página na história do navegador
};


</script>