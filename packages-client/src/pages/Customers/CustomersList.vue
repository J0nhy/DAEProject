<template>

    <div class="content">
  
  
      <div class="container-fluid">
        <button class="btn btn-success btn-fill float-top" @click.prevent="$router.push({ name: 'Customer Create' })">
        Create Customer
        </button>
        <br><br>
        <div class="row">
          <div class="col-12">
  
            <card class="strpied-tabled-with-hover"
                  body-classes="table-full-width table-responsive"
            >
              <template slot="header">
                <h4 class="card-title">Customers</h4>
                <p class="card-category">List of Customers</p>
              </template>
  
              <l-table class="table-hover table-striped"
                       :columns="table1.columns"
                       :data="table1.data">
              </l-table>
            </card>
  
          </div>
  
        </div>
      </div>
    </div>
  </template>
  <script>
  import LTable from 'src/components/Table.vue'
  import Card from 'src/components/Cards/Card.vue'
  
  
  const tableColumns = ['username', 'Name', 'Email', 'Phone', 'Address']
  
  export default {
    components: {
      LTable,
      Card
    },
    data() {
      return {
        table1: {
          columns: [...tableColumns],
          data: []
        }
      }
    },
    created() {
      this.fetchCustomers();
    },
    methods: {
      async fetchCustomers() {
        try {
          const token = 'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJDdXN0b21lcjEiLCJpYXQiOjE3MDU1MDc5MDIsImV4cCI6MTcwNTUxMTUwMn0.EkhgTJt-qPSuph9lYukuVfV-GUsZeC3oGpo7j8cyA-hxyFsNwRyTo7ONj17EL19q'; // Substitua com o seu token real
          const apiUrl = process.env.VUE_APP_API_URL
          const response = await fetch(`${apiUrl}/customers`, {
            method: 'GET',
            //mode: 'no-cors',
  
            headers: {
              'Authorization': `Bearer ${token}`
            }
          });
          const contentType = response.headers.get('Content-Type');
  
  
        const responseBody = await response.json();
        console.log('Corpo da resposta:', responseBody);
  
         this.table1.data = responseBody; // Assuming the API returns an array of manufacturers
        } catch (error) {
          console.error('Error fetching manufacturers:', error);
        }
      }
    }
  };
  </script>
  
  <style>
  </style>
  