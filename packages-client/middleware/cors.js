export default function ({ $config, $axios }) {
    // Set up CORS headers
    $axios.onRequest((config) => {
      config.headers.common['Access-Control-Allow-Origin'] = $config.API_URL; // Replace with your API URL
      config.headers.common['Access-Control-Allow-Methods'] = 'GET, POST, PUT, DELETE';
      config.headers.common['Access-Control-Allow-Headers'] = 'Content-Type, Authorization';
  
      return config;
    });
  }