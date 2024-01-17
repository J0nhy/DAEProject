<template>
    <card>
      <h4 slot="header" class="card-title">Create Customer</h4>
      <form @submit.prevent="create">
        <div class="row">
          <div class="col-md-4">
            <base-input type="text" label="Username" placeholder="Username">
            </base-input>
            <!--:disabled="true"-->
          </div>
  
        </div>
        <div class="row">
          <div class="col-md-4">
            <base-input type="text" label="Name" placeholder="Name">
            </base-input>
          </div>
        </div>
        <div class="row">
          <div class="col-md-4">
            <base-input type="text" label="Password" placeholder="Password">
            </base-input>
          </div>
        </div>
        <div class="row">
          <div class="col-md-4">
            <base-input type="text" label="Email" placeholder="Email">
            </base-input>
          </div>
        </div>
        <div class="row">
          <div class="col-md-4">
            <base-input type="text" label="Nif" placeholder="Nif">
            </base-input>
          </div>
        </div>
        <div class="row">
          <div class="col-md-4">
            <base-input type="text" label="PhoneNumber" placeholder="PhoneNumber">
            </base-input>
          </div>
        </div>
        <div class="row">
          <div class="col-md-4">
            <base-input type="email" label="Email" placeholder="Email">
            </base-input>
            <!--v-model="user.email"-->
          </div>
        </div>
        <div class="row">
          <div class="col-md-12">
            <base-input type="text" label="Address" placeholder="Home Address">
            </base-input>
          </div>
        </div>
        <div class="text-center">
          <button type="submit" class="btn btn-success btn-fill float-right">
            Create User
          </button>
        </div>
        <div class="clearfix"></div>
      </form>
    </card>
  </template>
  <script>
  import Card from 'src/components/Cards/Card.vue'
  
  export default {
    components: {
      Card
    },
    data() {
      return {
        user: {
          username: 'Customerx',
          name: 'michael23',
          password: '',
          email: 'Mike',
          nif: 'Andrew',
          phonenumber: 'Melbourne, Australia',
          email: 'melbourne',
          address: 'Australia'
        }
  
      }
  
    },
  
    methods: {
  
      async create() {
        const requestOptions = {
          method: 'POST',
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(studentForm)
        }
        const { error } = await useFetch(`${api}/customers`, requestOptions)
        if (!error.value) navigateTo('/')
        message.value = error.value
      }
    }
  }
  
  </script>
  <style></style>
  