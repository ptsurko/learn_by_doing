const express = require('express');
const fs = require('fs');
const dotenv = require('dotenv');
const app = express();
const { Client } = require('pg')

dotenv.config();

const context = 'K8S APP1';
const port = process.env.PORT || 8080;

const client = new Client({
  user: process.env.POSTGRES_USER,
  host: process.env.POSTGRES_HOST,
  database: process.env.POSTGRES_DB,
  password: process.env.POSTGRES_PASSWORD,
  port: Number.parseInt(process.env.POSTGRES_PORT, 10) || 5432,
});
client.connect().then(() => console.log(`${context}: Connected to DB!`));

app.get('/health', (req, res) => {
  res.status(200).send();
});

app.get('/', (req, res) => {
  res.json({ message: `Hello World from ${context}!` });
});

app.get('/api/env', (req, res) => {
  res.send(JSON.stringify(process.env, null, 2));
});

app.get('/api/secrets', (req, res) => {
  fs.readFile('/etc/app-config/config.yaml', 'utf8', function(err, contents) {
    if (err) {
      res.status(500).send('Error: ' + err);
    } else {
      res.send(contents);
    }
  });
});

app.get('/api/now', (req, res) => {
  client.query('SELECT NOW()', (err, data) => {
    if (err) {
      res.status(500).send('Error: ' + err);
    } else {
      res.send(data.rows[0]);
    }
  })
})

app.listen(port, () => {
  console.log(`${context}: Listening on port ${port}!`);
});

process.on('SIGTERM', function () {
  server.close(function () {
    console.log(`${context}: Terminating!`);
    process.exit(0);
  });
});
