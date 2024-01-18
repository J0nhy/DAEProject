<template>
  <h1>Login Form</h1>
  <div>Username:
    <input @keyup.enter="login" v-model="loginFormData.username">
  </div>
  <div>Password:
    <input @keyup.enter="login" type="password" v-model="loginFormData.password">
  </div>
  <button @click="login">LOGIN</button>
  <div v-if="token">
    <div>Token: {{ token }}</div>
  </div>
  <div v-if="messages.length > 0">
    <h2>Messages</h2>
    <div v-for="message in messages">
      <pre>{{ message }}</pre>
    </div>
  </div>
</template>
<script setup>
const config = useRuntimeConfig()
const api = config.public.API_URL
const loginFormData = ref({
  username: "",
  password: ""
})
import {useAuthStore} from "~/store/auth-store.js"
//const token = ref(null)
//const user = ref(null)
const authStore = useAuthStore()
const {token, user} = storeToRefs(authStore)

const messages = ref([])



async function login() {
  const { data, error } = await useFetch(`${api}/auth/login`, {
    method: 'post',
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    },
    body: loginFormData.value
  })

  // Store the token in session storage
  localStorage.setItem('token', data.value)
  token.value = data.value
  await getUser()
  if (error.value) {
    messages.value.push({ tokenError: error.value.message })
    return
  }

  //redirect to home page
  navigateTo('/')

}

async function getUser() {
    const { data, error } = await useFetch(`${api}/auth/user`, {
        method: 'get',
        headers: {
            'Accept': 'application/json',
            'Authorization': 'Bearer ' + token.value
        }
    })
    if (error.value) {
        messages.value.push({ error: error.value.message })
    }
    if (data.value) {
        messages.value.push({ payload: data.value })
        user.value = data.value;
        localStorage.setItem('user', JSON.stringify(user.value))
    }
}
</script>