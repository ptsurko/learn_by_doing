const minimatch = require('minimatch');
const { RequestInterceptor, ResponseFaker } = require('puppeteer-request-spy');
const { PendingXHR } = require('pending-xhr-puppeteer');

describe('app', () => {
  let requestInterceptor;

  beforeEach(() => {
    requestInterceptor = new RequestInterceptor(minimatch);

    const jsonResponseFaker = new ResponseFaker('https://jsonplaceholder.typicode.com/posts', {
      status: 200,
      contentType: 'application/json',
      body: JSON.stringify([
        {
          "userId": 1,
          "id": 1,
          "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
          "body": "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
        },
        {
          "userId": 1,
          "id": 2,
          "title": "qui est esse",
          "body": "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla"
        }
      ]),
    });

    requestInterceptor.addFaker(jsonResponseFaker);
  });

  it('should display My Blog logo', async () => {
    page = await browser.newPage();
    page.setRequestInterception(true);
    page.on('request', requestInterceptor.intercept.bind(requestInterceptor));

    const pendingXHR = new PendingXHR(page);

    await page.goto('http://localhost:8080');
    await pendingXHR.waitForAllXhrFinished();
    await page.waitForSelector('section');

    const sectionCount = await page.evaluate(() => document.querySelectorAll('section').length);
    await expect(sectionCount).toBe(2);
    await expect(page).toMatch('My Blog');
  });
});