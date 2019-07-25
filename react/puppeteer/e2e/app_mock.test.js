const mocker = require('puppeteer-request-mocker');
const { PendingXHR } = require('pending-xhr-puppeteer');

describe('app', () => {
  it('should display My Blog logo', async () => {
    page = await browser.newPage();
    const pendingXHR = new PendingXHR(page);

    await page.goto('http://localhost:8080');
    await mocker.start({
      page,
      mockList: ['https://jsonplaceholder.typicode.com/posts'],
    });
    
    await pendingXHR.waitForAllXhrFinished();
    await page.waitForSelector('section');

    const sectionCount = await page.evaluate(() => document.querySelectorAll('section').length);
    console.log('sectionCount: ', sectionCount);
    await expect(sectionCount).toBe(100);
    await expect(page).toMatch('My Blog');

    await mocker.stop();
  })
});