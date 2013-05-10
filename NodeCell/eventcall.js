var ready = require("./eventemitter");

ready.on('ready', function() {
  console.log('module ready is ready');
});