module.exports = {
  apps: [
    {
      name: "order-service",
      script: "java",
      args: "-jar order-service.jar",
      cwd: "/opt/cloudmart/order-service",
      env: {
        SERVER_PORT: "8082",
        CONFIG_SERVER_URL: "http://localhost:8888",
        EUREKA_SERVER_URL: "http://localhost:8761/eureka",
        // --- Point this at either a self-hosted MongoDB VM or a MongoDB Atlas cluster ---
        MONGODB_URI: "mongodb://<MONGO_HOST>:27017/orderdb",
        GOOGLE_CLOUD_PROJECT: "<YOUR_GCP_PROJECT_ID>" // used by the Firestore client
      },
      autorestart: true,
      max_restarts: 10,
      min_uptime: "10s",
      restart_delay: 3000,
      out_file: "/var/log/pm2/order-service-out.log",
      error_file: "/var/log/pm2/order-service-error.log",
      log_date_format: "YYYY-MM-DD HH:mm:ss"
    }
  ]
};
