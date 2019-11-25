const express = require('express');
const cookieParser = require('cookie-parser');
const next = require('next');
const axios = require('axios');
const passport = require('passport');
const { OAuth2Strategy } = require('passport-google-oauth');
const CookieStrategy = require('passport-cookie');

require('dotenv').config();

const SESSION_COOKIE_NAME = 'nodejs.google-auth.session.id';

passport.use(new OAuth2Strategy({
  clientID: process.env.GOOGLE_CONSUMER_KEY,
  clientSecret: process.env.GOOGLE_CONSUMER_SECRET,
  callbackURL: "http://localhost:3000/auth/google/callback"
}, function (accessToken, refreshToken, profile, done) {
  console.log('accessToken: ', accessToken);
  console.log('refreshToken: ', refreshToken);
  console.log('profile: ', profile);

  const email = profile.emails
    .map(item => item.value)
    .find(email => email);

  return done(null, { 
    fullName: profile.displayName, 
    email, 
    accessToken 
  });
}));

passport.use(new CookieStrategy({
  cookieName: SESSION_COOKIE_NAME,
}, function (token, done) {
  console.log('token: ', token);
  done(null, token);
}));

const dev = process.env.NODE_ENV !== 'production';
const app = next({ dev });
const handle = app.getRequestHandler();
app.prepare()
  .then(() => {
    const server = express();
    server.use(cookieParser());
    server.use(passport.initialize());

    server.get('/auth/login',
      passport.authenticate('google', { session: false, scope: ['email', 'profile'] }),
    );

    server.get('/auth/google/callback',
      passport.authenticate('google', { session: false, failureRedirect: '/login' }),
      function (req, res) {
        console.log('callback------')
        console.log('req: ', req.params);

        res.cookie(SESSION_COOKIE_NAME, req.user);
        res.redirect('/');
      });

    server.get('/auth/user',
      passport.authenticate('cookie', { session: false, failureRedirect: '/login' }),
      function (req, res) {
        console.log('returning user: ', req.user);
        res.json(req.user);
      },
    );

    server.get('/auth/user/profile',
      passport.authenticate('cookie', { session: false, failureRedirect: '/login' }),
      function (req, res, next) {
        console.log('returning user profile for: ', req.user);

        axios.get('https://people.googleapis.com/v1/people/me?personFields=names,emailAddresses', {
          headers: {
            Authorization: `Bearer ${req.user.accessToken}`,
          }
        }).then(response => {
            console.log('got profile response');
            res.json(response.data);
          })
          .catch(next);
      },
    );

    server.get('/auth/logout',
      function (req, res) {
        req.logout();
        res.clearCookie(SESSION_COOKIE_NAME, { path: '/' });
        res.redirect('/login');
      },
    );

    server.get('/login', 
      (req, res) => {
        return handle(req, res)
      });

    server.get('*', 
      passport.authenticate('cookie', { session: false, failureRedirect: '/login' }),
      (req, res) => {
        return handle(req, res)
      });
    
    server.listen(3000, (err) => {
      if (err) throw err
      console.log('> Ready on http://localhost:3000')
    })
  })
  .catch((ex) => {
    console.error(ex.stack)
    process.exit(1)
  })