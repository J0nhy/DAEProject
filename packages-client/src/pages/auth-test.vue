<template>

  <div>
    <h1>Login Form</h1>
    <div>Username:
      <input v-model="loginFormData.username">
    </div>

    <div>Password:
      <input v-model="loginFormData.password">
    </div>

    <button @click="login">LOGIN</button>
    <button @click="reset">RESET</button>
  </div>


</template>

<script setup>
const api = process.env.VUE_APP_API_URL
const loginFormData = ({
  username: null,
  password: null
})

const token = null
const messages = []
async function login() {
  const { data, error } = await fetch(`${api}/auth/login`, {
    method: "post",
    headers: {
      "Content-Type": "application/json",
      "Accept": "application/json"
    },
    body: JSON.stringify(loginFormData)
  })
  if (error.value) {
    messages.value.push({ error: error.value.message })
  }
  if (data.value) {
    token.value = data.value
    messages.value.push({ token: token.value })
  }
}
function reset() {
  token.value = null
  messages.value = []
}

</script>
