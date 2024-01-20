<template>
  <div class="center-container">
    <div class="login-container">
      <h1 class="form-group">Login Form</h1>
      <div class="form-group">
        <input @keyup.enter="login" v-model="loginFormData.username" placeholder="Username" class="centered-input">
      </div>
      <div class="form-group">
        <input @keyup.enter="login" type="password" v-model="loginFormData.password" placeholder="Password" class="centered-input">
      </div>
      <button @click="login" class="centered-button">LOGIN</button>
      <div v-if="token">
        <div class="token-info">Token: {{ token }}</div>
      </div>
      <div v-if="messages.length > 0" class="messages-container">
        <h2>Messages</h2>
        <div v-for="message in messages" :key="message">
          <pre>{{ message }}</pre>
        </div>
      </div>
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

<style>
/* LoginForm.css */
.center-container {
  display: flex;
  justify-content: center;
  align-items: center;
}

.login-container {
  max-width: 400px;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.form-group {
  margin-bottom: 15px;
}

.centered-input {
  width: 100%;
  box-sizing: border-box;
  padding: 10px;
}

.centered-button {
  width: 100%; /* Make the button full-width */
  padding: 12px; /* Adjust the padding */
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  box-sizing: border-box;
  margin-top: 10px; /* Add margin for separation */
}

.token-info {
  margin-top: 10px;
  font-weight: bold;
}

.messages-container {
  margin-top: 20px;
}
</style>