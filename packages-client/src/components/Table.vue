<template>
  <table class="table">
    <thead>
      <slot name="columns">
        <tr>
          <th v-for="column in columns" :key="column">{{ column }}</th>
        </tr>
      </slot>
    </thead>
    <tbody>
      <tr v-for="(item, index) in data" :key="index">
        <slot :row="item">
          <td v-for="column in columns" :key="column" v-if="hasValue(item, column)">{{ itemValue(item, column) }}</td>
          <td><button v-if="itemValue(item, 'logistics') === 'none' && buttonsManufacturerOrders" type="button" class="btn btn-primary">Add Logistics</button></td>
            <td><button v-if="buttonsManufacturerOrders" type="button" class="btn btn-primary">Details</button></td>
          
        </slot>
      </tr>
    </tbody>
  </table>
</template>
<script>
export default {
  name: 'l-table',
  props: {
    columns: Array,
    data: Array,
    buttonsManufacturerOrders: {
      type: Boolean,
      default: false  // Set the default value to false
    }
  },
  methods: {
    hasValue(item, column) {
      return item[column] !== 'undefined'
    },
    itemValue(item, column) {
      return item[column]
    }
  }
}
</script>
<style></style>
