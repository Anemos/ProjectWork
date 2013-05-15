var fs = require('fs')
,util = require('util')
,dateutil = require('date-utils');

var items = {
	    SKN:{name:'Shuriken', price:100},
	    ASK:{name:'Ashiko', price:690},
	    CGI:{name:'Chigiriki', price:250},
	    NGT:{name:'Naginata', price:900},
	    KTN:{name:'Katana', price:1000}
	};

var filelist = {};

function findFile(path, searchFile, callback) {
  fs.readdir(path, function (err, files) {
    if(err) { return callback(err); }
    files.forEach(function(file) {
      fs.stat(path+'/'+file, function(err, stats) {
        if(err) { return callback(err); }
        //console.log('stats: ' + util.inspect(stats));
        console.log('file name : ' + file);
        console.log('file size : ' + stats.size);
        console.log('file 접근일자 : ' + stats.atime.toFormat('YYYY-MM-DD HH24:MI:SS'));
        
        var eachfile = {};
        var id = file.substr(0,file.lastIndexOf('.'));
        eachfile.name = file;
        eachfile.size = stats.size;
        eachfile.atime = stats.atime.toFormat('YYYY-MM-DD HH24:MI:SS');
        filelist[id] = eachfile;
        
        if(stats.isFile() && file == searchFile) {
          callback(undefined, path+'/'+file);
        } else if(stats.isDirectory()) {
          findFile(path+'/'+file, searchFile, callback);
        }
      });
    });
  });
}

findFile('./tmp', 'fhmc_logo.png', function(err, path) {
  if(err) { throw err; }
  console.log('Found file at: '+path);
  for( var id in filelist ) {
	  console.log('File list at: '+ filelist[id].size);
  }
  
  var items = [{name:"name1", data:"data1"}, 
               {name:"name2", data:"data2"}, 
               {name:"name3", data:"data3"}, 
               {name:"name4", data:"data4"}, 
               {name:"name5", data:"data5"}]

  var test = [];

  for(var i = 0; i < items.length; i++){
      var item = {};
      var id = items[i].name;
      item.label = items[i].name;
      item.value = items[i].data;
      test[id] =item;
  }
  
  for(var id in test) {
	  console.log('File list at: '+ id);
  }
});
