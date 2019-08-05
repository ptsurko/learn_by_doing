const express = require('express');
const Sequelize = require('sequelize');

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
}, {
  // options
});

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
app.post('/api/users', (req, res) => {
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