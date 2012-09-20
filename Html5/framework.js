var lineNumber = 0;

var show = function(title, value){
    var node,
        result;

    lineNumber += 1;
    node = document.createElement('li');
    document.getElementById('showArea').appendChild(node);
    result = document.createTextNode(lineNumber + '. ' + title + ': ' + value);
    node.appendChild(result);
}


var ua = navigator.userAgent.toLowerCase();

var firefox = /firefox/.test(ua);
var ie = /msie/.test(ua);
var opera = /opera/.test(ua);
var safari = /safari/.test(ua) && !/chrome/.test(ua);
var chrome = /safari/.test(ua) && /chrome/.test(ua);

var browser = firefox ? 'Firefox' : ie ? 'IE' : opera ? 'Opera' : safari ? 'Safari' : chrome ? 'Chrome' : '';
