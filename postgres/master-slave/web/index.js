const express = require('express');
const Sequelize = require('sequelize');

const app = express();
const port = process.env.PORT || 3000;
const DATABASE_WRITE_HOST = process.env.DATABASE_WRITE_HOST;
const DATABASE_READ_HOST1 = process.env.DATABASE_READ_HOST1;
const DATABASE_READ_HOST2 = process.env.DATABASE_READ_HOST2;

const sequelize = new Sequelize('testdb', null, null, {
  dialect: 'postgres',
  replication: {
    read: [
      { host: DATABASE_READ_HOST1, username: 'user', password: 'pass' },
      { host: DATABASE_READ_HOST2, username: 'user', password: 'pass' },
    ],
    write: { host: DATABASE_WRITE_HOST, username: 'user', password: 'pass' }
  },
  logging: false 
});

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