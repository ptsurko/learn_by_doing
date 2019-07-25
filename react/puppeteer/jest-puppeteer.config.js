module.exports = {
  launch: {
    dumpio: true,
    headless: true,
    args: [
      '--disable-web-security',
    ],
  },
  server: {
    command: 'PORT=8080 BROWSER=none yarn start',
    port: 8080,
    launchTimeout: 10000,
  },
}