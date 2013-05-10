function route(handle,pathname,response,request) {
  console.log("About to route a request for " + pathname);
  if( typeof handle[pathname] === 'function' ) {
    return handle[pathname](response,request);
  } else {
    console.log("No request Handler found for " + pathname);
    response.writeHead(404,{"content-type": "text/plain"});
    response.write("404 Not Found");
    response.end()
  }
}

exports.route=route; 