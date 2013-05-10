var http = require("http"), fs = require("fs");

var options = {
  host: 'localhost',
  port: 8000,
  path: '/',
  method: 'POST'
};

var req = http.request(options, function(res) {
  console.log(res.statusCode);
});

var readStream = fs.ReadStream(__dirname + '/in.txt');
readStream.pipe(req);

var writeStream = fs.createWriteStream(__dirname + '/out.txt');

var server = http.createServer( function(req,res) {
  req.on('data', function(data) {
    writeStream.write(data);
  });
  
  req.on('end', function() {
    writeStream.end();
    res.statusCode = 200;
    res.end('OK');
  });
});

server.listen(8000);