const SDC = require('statsd-client');
const sdc = new SDC({
  host: 'statsd-exporter',
  port: 9125,
});

const random = (max) => Math.floor(Math.random() * max) + 0;

const scheduleConstant = (func, timeout) => {
  setTimeout(() => {
    func();
    scheduleConstant(func, timeout);
  }, timeout);
};

const scheduleRandom = (func, max) => {
  setTimeout(() => {
    func();
    scheduleRandom(func, max);
  }, random(max));
};

scheduleRandom(() => sdc.increment('node.counter'), 100);

scheduleConstant(() => sdc.increment('node.counter500'), 500);

scheduleConstant(() => sdc.increment('node.counter1000'), 1000);

scheduleRandom(() => sdc.gauge('node.gauge', random(10)));

scheduleRandom(() => sdc.histogram('node.histogram', random(10), {foo: 'bar'}));

console.log('Statsd client initialized');

// setInterval(() => {
//    const timer = new Date();

//    // Increment counter by one.
//    sdc.increment('node.counter');
  
//    // Increment counter by 10
//    sdc.increment('node.counter_10', 10);

//    // Set gauge to 10
//;    sdc.gauge('node.gauge', 10);

//    // Calculates time diff of time between the variable and
//    // when the function was called
//    sdc.timing('node.timer', timer);

//    // Set will count just 2 elements since '50' is repeated
//    sdc.set('node.set', 50);
//    sdc.set('node.set', 100);
//    sdc.set('node.set', 50);

//    // Histogram with tags
//    sdc.histogram('node.histogram', 10, {foo: 'bar'});
// }, 1000);