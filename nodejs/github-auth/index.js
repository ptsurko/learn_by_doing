const express = require('express');
const axios = require('axios');
const app = express();
const port = 3000;

const CLIENT_ID = 'cbe5870c8f5e4c319219';
const CLIENT_SECRET = '13cf2bc26a093cf6355db6ccfa06db9cc321af37';

app.get('/', (req, res) => res.send(`
<html>
  <body>
    <a href="https://github.com/login/oauth/authorize?client_id=cbe5870c8f5e4c319219&redirect_url=http://localhost:3000/auth/github/callback&scope=repo%20user">Authenticate with Github</a>
  </body>
</body>
`))

app.get('/setup', function (req, res) {
  axios.get('https://github.com/login/oauth/authorize', {
    params: {

    }
  })
  res.send('hello world')
})

app.get('/auth/github/callback', (req, res) => {
  
  if (req.query.code) {
    const code = req.query.code;
    console.log('code: ', code);

    axios.get('https://github.com/login/oauth/access_token', {
      params: {
        client_id: CLIENT_ID,
        client_secret: CLIENT_SECRET,
        code,
        redirect_url: 'http://localhost:3000/auth/github/callback',
        state: '123',
      },
      headers: {
        Accept: 'application/json'
      }
    }).then((response) => {
      const accessToken = response.data.access_token;

      return axios.get('https://api.github.com/user', {
        headers: {
          Authorization: `token ${accessToken}`,
        },
      }).then(response => {
        console.log('https://api.github.com/user: ', response.data);
        
        res.end('end');
      })
    })
  } else {
    res.end('missing token');
  }
})

app.listen(port, () => console.log(`Example app listening on port ${port}!`))
