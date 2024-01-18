<template>
  <div class="content">


    <div class="container-fluid">
      <button class="btn btn-success btn-fill float-top" @click.prevent="$router.push({ name: 'logisticsoperator Add' })">
        Create Logistics Operator
      </button>
      <br><br>
      <div class="row">
        <div class="col-12">

          <card class="strpied-tabled-with-hover" body-classes="table-full-width table-responsive">
            <template slot="header">
              <h4 class="card-title">Logistics Operator</h4>
              <p class="card-category">List of Logistics Operators</p>
            </template>
            <l-table class="table-hover table-striped" :columns="table1.columns" :data="table1.data" :buttonsManufacturerOrders=true>
              
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
import axios from 'axios';


const tableColumns = ['id', 'status', 'customerUsername', 'logistics']

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
    this.fetchLogistics();
  },
  methods: {
    async fetchLogistics() {
      try {
        //const token = 'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJNYW51ZmFjdHVyZXIxIiwiaWF0IjoxNzA1MjU1ODAyLCJleHAiOjE3MDUyNTk0MDJ9.XrpDtNfxdL5xAJY4otd_XiowfCaBwgpX_YOj4ruloYOvgwa0mv-JBHUqOmK_uNxH'; // Substitua com o seu token real
        const apiUrl = process.env.VUE_APP_API_URL
        const response = await axios.get(`${apiUrl}/orders`);
        const responseBody = await response.data;



        for (let i = 0; i < response.data.length; i++) {
          let user = []
          user.id = responseBody[i].id;
          user.status = responseBody[i].status;
          user.customerUsername = responseBody[i].customerUsername;
          // user.logistics=responseBody[i].logisticsOperators['username'];
          user.logistics = responseBody[i].logisticsOperators
            ? responseBody[i].logisticsOperators.username
            : "none";
          this.table1.data.push(user);
        }
        //this.table1.data = responseBody;
      } catch (error) {
        console.error('Error fetching manufacturers:', error);
      }
    }
  }
};
</script>

<style></style>
