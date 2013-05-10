var http = require('http')
,formidable = require('formidable')
,fs = require('fs')
,sys = require('sys')
,path = require('path');

http.createServer(function (req, res) {
  // set up some routes
  switch(req.url) {
    case '/':
         // show the user a simple form
          console.log("[200] " + req.method + " to " + req.url);
          res.writeHead(200, "OK", {'Content-Type': 'text/html'});
          res.write('<html><head><title>Hello Noder!</title></head><body>');
          res.write('<h1>Welcome Noder, who are you?</h1>');
          res.write('<form enctype="multipart/form-data" action="/formhandler" method="post">');
          res.write('Name: <input type="text" name="username" value="John Doe" /><br />');
          res.write('Age: <input type="text" name="userage" value="99" /><br />');
          res.write('File :<input type="file" name="upload" multiple="multiple"><br>');
          res.write('<input type="submit" />');
          res.write('</form></body></html');
          res.end();
      break;
    case '/formhandler':
        if (req.method == 'POST') {
            console.log("[200] " + req.method + " to " + req.url);

            req.on('data', function(chunk) {
              console.log("Received body data:");
              //console.log(chunk.toString()); 주석을 풀면 비프음이 계속적으로 발생함
            });
            var form = new formidable.IncomingForm();
            var uploaded_file;
            form.uploadDir = "tmp";
            form.on('file', function(field,file) {
              uploaded_file = file.name;
              fs.rename(file.path, path.dirname(file.path) + '//' + file.name, function(err) {
                if(err) {
                  console.log('rename failed ' + err);
                }
              });
            });
            
            form.on('progress', function(byteReceived, byteExpected) {
              console.log('Updated: ' + Math.round(byteReceived/byteExpected * 100) + '%');
            });
            
            form.on('end', function() {
              res.writeHead(200, {"content-type":"text/plain"});
              res.write('Uploaded file: ' + uploaded_file);
              res.write('\n');
              res.end('Done');
            });
            
            form.parse(req);

          } else {
            console.log("[405] " + req.method + " to " + req.url);
            res.writeHead(405, "Method not supported", {'Content-Type': 'text/html'});
            res.end('<html><head><title>405 - Method not supported</title></head><body><h1>Method not supported.</h1></body></html>');
          }
      break;
    default:
      res.writeHead(404, "Not found", {'Content-Type': 'text/html'});
      res.end('<html><head><title>404 - Not found</title></head><body><h1>Not found.</h1></body></html>');
      console.log("[404] " + req.method + " to " + req.url);
  };
}).listen(8000)