<template>
  <div class="wrapper">
    <side-bar>
      <mobile-menu slot="content"></mobile-menu>
      <sidebar-link to="/admin/order-list">
        <i class="nc-icon nc-notes"></i>
        <p>Orders</p>
      </sidebar-link>
      <sidebar-link to="/manufacturer/order-list">
        <p>Manufacturer Orders</p>
      </sidebar-link>
      <sidebar-link to="/logistic/order-list">
        <p>Logistics Operator Orders</p>
      </sidebar-link>
      <sidebar-link to="/customer/order-list">
        <p>Customer Orders</p>
      </sidebar-link>
      <sidebar-link to="/logistic/logisticsoperator-list">
        <i class="nc-icon nc-notes"></i>
        <p>Logistics Operator</p>
      </sidebar-link>


      <sidebar-link to="/customer/customer-list">
        <i class="nc-icon nc-notes"></i>
        <p>Customer</p>
      </sidebar-link>
      <template slot="bottom-links">
        <sidebar-link class="active"
                      to="/admin/upgrade">
          <i class="nc-icon nc-alien-33"></i>
          <p>Upgrade to PRO</p>
        </sidebar-link>
      </template>
    </side-bar>
    <div class="main-panel">
      <top-navbar></top-navbar>

      <dashboard-content @click="toggleSidebar">

      </dashboard-content>

      <content-footer></content-footer>
    </div>
  </div>
</template>
<style lang="scss">

</style>
<script>
  import TopNavbar from './TopNavbar.vue'
  import ContentFooter from './ContentFooter.vue'
  import DashboardContent from './Content.vue'
  import MobileMenu from './MobileMenu.vue'
  export default {
    components: {
      TopNavbar,
      ContentFooter,
      DashboardContent,
      MobileMenu
    },
    methods: {
      toggleSidebar () {
        if (this.$sidebar.showSidebar) {
          this.$sidebar.displaySidebar(false)
        }
      }
    }
  }

</script>
