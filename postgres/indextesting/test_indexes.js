const Sequelize = require('sequelize');
const sequelize = new Sequelize('postgres://testing:password@localhost:5432/indextesting', { logging: false });

/*  PLAYING WITH INDEXES
SELECT * FROM auths  WHERE "userId" = 3 and status = 2 and "createdAt" > current_date - interval '10' day;

SELECT * FROM auths 
WHERE "userId" = 3 and status = 2 and "createdAt" > current_date - interval '200100' day and "createdAt" < current_date - interval '200000' day;

select count(*) from auths;

create index auth_compound on auths("userId", "createdAt", "status");
create index auth_user on auths("userId");

DROP index auth_compound;
drop index auth_user;
*/

const BULK_SIZE = 1000;

class Auth extends Sequelize.Model {}
Auth.init({
  id: { type: Sequelize.INTEGER, autoIncrement: true, primaryKey: true },
  userId: Sequelize.INTEGER,
  order: Sequelize.STRING,
  status: Sequelize.INTEGER,
  createdAt: Sequelize.DATE,
}, { 
  sequelize, 
  modelName: 'auth',
  // indexes: [{
  //   name: 'public_by_userId_status_createAt',
  //   fields: ['userId', 'status', 'createdAt'],
  // }],
});

Date.prototype.addDays = function(days) {
  var date = new Date(this.valueOf());
  date.setDate(date.getDate() + days);
  return date;
}

const createBatch = (index=1) => {
  if (index === 0) {
    return;
  }
  const usersCount = 10;
  const statusCount = 3;

  if (index % 10 == 0) {
    console.log('index: ', index);
  }

  const users = [];
  for (let i = 0; i < BULK_SIZE; i++) {
    users.push({
      order: 'janedoe' + i,
      userId: i % usersCount,
      status: i % statusCount,
      createdAt: new Date(2019, 7, i % 30 + 1).addDays(-index * 30),
    })
  }

  return Auth.bulkCreate(users)
    .then(() => createBatch(index-1));
}

const elapsedSeconds = (startTime) => {
  return Math.round((new Date() - startTime) / 1000);
}

const ROWS_TO_CREATE = 1000000;
let startTime;

sequelize.drop()
  .then(() => sequelize.sync())
  .then(() => {
    console.log('Creaating rows');
    startTime = new Date();
  })
  .then(() => createBatch(Math.floor(ROWS_TO_CREATE / BULK_SIZE)))
  .then(() => {
    console.log(`Time taken: ${elapsedSeconds(startTime)}s`)
  });
