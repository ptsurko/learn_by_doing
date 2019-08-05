const express = require('express');
const Sequelize = require('sequelize');
const prometheusMiddleware = require('express-prometheus-middleware');

const app = express();
const port = process.env.PORT || 3000;
const DATABASE_URL = process.env.DATABASE_URL;

const sequelize = new Sequelize(DATABASE_URL, { logging: false });

sequelize
  .authenticate()
  .then(() => {
    console.log('Connection has been established successfully.');
  })
  .then(() => sequelize.sync())
  .then(() => {
    console.log('All tables were successfully synchronized.');
  })
  .catch(err => {
    console.error('Unable to connect to the database:', err);
  });

const User = sequelize.define('user', {
  firstName: {
    type: Sequelize.STRING,
    allowNull: false
  },
  lastName: {
    type: Sequelize.STRING
  }
});

app.use(prometheusMiddleware({
  metricsPath: '/metrics',
  collectDefaultMetrics: false,
  requestDurationBuckets: [0.01, 0.1, 0.3, 0.5, 0.75, 1, 1.5],
}));

app.get('/api/users', (req, res) => {
  User.findAll()
    .then((users) => {
      res.json(users).send();
    });
});
app.get('/api/users/:userId', (req, res) => {
  User.findByPk(req.params.userId)
    .then((user) => {
      res.json(user).send();
    });
});

const random = (max) => Math.floor(Math.random() * max) + 0;

app.post('/api/users', (req, res) => {
  if (Math.random() > 0.8) {
    res.status(500).send();
    return;
  }

  if (Math.random() > 0.97) {
    setTimeout(() => {
      res.status(200).json({});
    }, random(3000));
    return;

    // const slowness = random(500000000);
    // let sum = 1;
    // for (let i = 1; i < slowness; i++) {
    //   sum *= i;
    // }
    // console.log('sum: ', sum);
  }

  User.create({
    firstName: 'John',
    lastName: 'Hancock'
  }).then((user) => {
    res.json(user).send();
  });
});

app.listen(port, () => {
  console.log(`Example app listening on port ${port}!`);
});
