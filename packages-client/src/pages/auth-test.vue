<template>
  <div>
    <h1>Login Form</h1>
    <div>
      Username: <input v-model="loginFormData.username" />
    </div>
    <div>
      Password: <input v-model="loginFormData.password" />
    </div>
    <button @click="login">LOGIN</button>
    <button @click="reset">RESET</button>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const api = process.env.VUE_APP_API_URL;
const loginFormData = {
  username: null,
  password: null
};

const token = ref(localStorage.getItem('token') || null);
const messages = ref([]);

async function login() {
  try {
    const response = await fetch(`${api}/auth/login`, {
      method: 'post',
      headers: {
        'Content-Type': 'application/json',
        Accept: 'application/json'
      },
      body: JSON.stringify(loginFormData)
    });

    const data = await response.text();

    if (response.ok) {
      const jwtToken = data; // Assume que o token JWT é retornado diretamente
      console.log('Token recebido:', jwtToken);
      token.value = jwtToken;
      localStorage.setItem('token', jwtToken); // Salva o token no localStorage
      messages.value.push({ token: jwtToken });
    } else {
      messages.value.push({ error: data.message });
    }
  } catch (error) {
    console.error('Erro no login:', error);
    messages.value.push({ error: error.message });
  }
}

function reset() {
  token.value = null;
  localStorage.removeItem('token'); // Remove o token do localStorage
  messages.value = [];
}
</script>
