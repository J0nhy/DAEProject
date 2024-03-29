// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  devtools: { enabled: true },
  runtimeConfig: {
    public: {
      API_URL: process.env.API_URL || 'http://localhost:8080/packages/api',
    }
  },
  serverMiddleware: ['~/middleware/cors.js'],

  modules: [
    '@pinia/nuxt'
  ],
  css: [
    'bootstrap/dist/css/bootstrap.css',
    'bootstrap-icons/font/bootstrap-icons.css',

  ]
})
