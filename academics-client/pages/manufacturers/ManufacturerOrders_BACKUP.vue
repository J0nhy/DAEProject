<template>
  <div class="content">
    <div class="container-fluid">
      <br><br>
      <div class="row">
        <div class="col-12">

          <card class="strpied-tabled-with-hover"
                body-classes="table-full-width table-responsive"
          >
            <template slot="header">
              <h4 class="card-title">Manufacturer Orders</h4>
              <p class="card-category">List of Orders</p>
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


const tableColumns = ['Id', 'Name', 'Email', 'Phone', 'Address']

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
    this.fetchOrders();
  },
  methods: {
    async fetchOrders() {
      try {
        const token = 'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJNYW51ZmFjdHVyZXIxIiwiaWF0IjoxNzA1MjU1ODAyLCJleHAiOjE3MDUyNTk0MDJ9.XrpDtNfxdL5xAJY4otd_XiowfCaBwgpX_YOj4ruloYOvgwa0mv-JBHUqOmK_uNxH'; // Substitua com o seu token real
        const apiUrl = process.env.VUE_APP_API_URL
        const response = await fetch(`${apiUrl}/orders`, {
          method: 'GET',
          //mode: 'no-cors',

          headers: {
            'Authorization': `Bearer ${token}`
          }
        });
        const contentType = response.headers.get('Content-Type');


      const responseBody = await response.json();
      console.log('Corpo da resposta:', responseBody);

       this.table1.data = responseBody;
      } catch (error) {
        console.error('Error fetching orders:', error);
      }
    }
  }
};
</script>