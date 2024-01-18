<template>
    <div>
        <h1>Welcome to Order Managment</h1>
        Manage your resources:
        <div style="padding-left: 14px; margin-right: 800px;">
            <nuxt-link to="/customers">Customers</nuxt-link><br>
            <nuxt-link to="/logistics">Logistic Operators</nuxt-link><br>
            <nuxt-link to="/manufactures">Manufactures</nuxt-link><br>
            <hr>
            <nuxt-link to="/orderCustomer">Customer Orders</nuxt-link><br>
            <nuxt-link to="/orderLogistic">Logistic Orders</nuxt-link><br>
            <nuxt-link to="/orderManufacturer">Manufacturer Orders</nuxt-link><br>
        </div>
    </div>
</template>