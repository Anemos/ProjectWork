var formidable = require('formidable')
,fs = require('fs')
,util = require('util')
,path = require('path')
,dateutil = require('date-utils')
,exec = require('child_process').exec
,spawn = require('child_process').spawn;

// our 'database'
/*var items = {
    SKN:{name:'Shuriken', price:100},
    ASK:{name:'Ashiko', price:690},
    CGI:{name:'Chigiriki', price:250},
    NGT:{name:'Naginata', price:900},
    KTN:{name:'Katana', price:1000}
};*/

/*var items = {
europeanflag:{ name:'europeanflag.png',size: 3089, atime:'2013-05-14 14:34:46'},
aaa200905:{ name:'전공장월별재료비_공제전(200905).xls',size: 9169431, atime:'2013-05-14 14:34:46'},
fhmc_logo:{ name:'fhmc_logo.png',size: 12854, atime:'2013-05-14 14:34:50'}
};*/

var items = {};

function findFile(path, searchFile, callback) {
	  fs.readdir(path, function (err, files) {
	    if(err) { return callback(err); }
	    var i = 0;
	    files.forEach( function(file) {
	      fs.stat(path+'/'+file, function(err, stats) {
	        if(err) { return callback(err); }
	        //console.log('stats: ' + util.inspect(stats));
	        var eachfile = {};
	        var id = file.substr(0,file.lastIndexOf('.'));
	        var extname = file.substr(file.lastIndexOf('.') + 1);
	        eachfile.name = file;
	        eachfile.size = stats.size;
	        eachfile.atime = stats.atime.toFormat('YYYY-MM-DD HH24:MI:SS');
	        items[id] = eachfile;
	        
	        if(stats.isFile() && file == searchFile) {
	          callback(undefined, path+'/'+file);
	        } else if(stats.isDirectory()) {
	          findFile(path+'/'+file, searchFile, callback);
	        }
	        
	      });
	    });
	  });
	}

exports.home = function(req, res){
	if( typeof req.session.username  == 'undefined' ) {
		res.render('home', {title: 'Ninja Store'});
	} else {
		// get the upload file
		findFile('./public/uploads', 'xxx.png', function(err, path) {	
		});
		res.redirect('/items');
	}
};

exports.upload = function(req, res){
	if (req.method == 'POST') {
        console.log("[200] " + req.method + " to " + req.url);
        console.log(req.files);
     // get the temporary location of the file
        var tmp_path = req.files.myfile.path;
        var target_path = './public/uploads/' + req.files.myfile.name;
        fs.rename(tmp_path, target_path, function(err) {
            if (err) throw err;
            // delete the temporary file, so that the explicitly set temporary upload dir does not get filled with unwanted files
            fs.unlink(tmp_path, function() {
                if (err) throw err;
                
                // get the upload file
        		findFile('./public/uploads', 'xxx.png', function(err, path) {	
        		});
                
                res.send('File uploaded to: ' + target_path + ' - ' + req.files.myfile.size + ' bytes');
            });
        });
	} else {
		res.render('upload', {title: 'File Upload'});
    }
};

exports.home_post_handler = function(req, res){
	username = req.body.username || 'Anonymous';
	req.session.username = username;
	res.redirect('/');
}

//handler for displaying the items
exports.items = function(req, res) {
    // don't let nameless people view the items, redirect them back to the homepage
    if (typeof req.session.username == 'undefined') res.redirect('/');
    else res.render('items', { title: 'Ninja Store - Items', username: req.session.username, items:items });
};

// handler for displaying individual items
exports.item = function(req, res) {
	console.log("파일명 id : " + req.params.id)
    // don't let nameless people view the items, redirect them back to the homepage
    if (typeof req.session.username == 'undefined') res.redirect('/');
    else {
        var name = items[req.params.id].name;
        var size = items[req.params.id].size;
        res.render('item', { title: 'Ninja Store - ' + name, username: req.session.username, id:req.params.id, name:name, size:size });
    }
};

//handler for showing simple pages
exports.page = function(req, res) {
    var name = req.query.name;
    var contents = {
        about: 'Ninja Store sells the coolest ninja stuff in the world. Anyone shopping here is cool.',
        contact: 'You can contact us at <address><strong>Ninja Store</strong>,<br>1, World Ninja Headquarters,<br>Ninja Avenue,<br>NIN80B7-JP,<br>Nihongo.</address>'
    };
    res.render('page', { title: 'Ninja Store - ' + name, username: req.session.username, content:contents[name] });
};

exports.download = function(req, res){
	var file = './public/uploads/' + items[req.params.id].name;
	console.log(items[req.params.id].name + ' downloaded to ' + file);
	res.download(file); // Set disposition and send it.
};

exports.filedownload = function(req, res) {
	// App variables
	var file_url = 'http://upload.wikimedia.org/wikipedia/commons/4/4f/Big%26Small_edit_1.jpg';
	var DOWNLOAD_DIR = './downloads/';

	// We will be downloading the files to a directory, so make sure it's there
	// This step is not required if you have manually created the directory
	var mkdir = 'mkdir -p ' + DOWNLOAD_DIR;
	var child = exec(mkdir, function(err, stdout, stderr) {
	    if (err) throw err;
	    else download_file_wget(file_url);
	});
	
	//Function to download file using wget
	function download_file_wget(file_url) {
	    // extract the file name
	    var file_name = url.parse(file_url).pathname.split('/').pop();
	    // compose the wget command
	    var wget = 'wget -P ' + DOWNLOAD_DIR + ' ' + file_url;
	    // excute wget using child_process' exec function

	    var child = exec(wget, function(err, stdout, stderr) {
	        if (err) throw err;
	        else console.log(file_name + ' downloaded to ' + DOWNLOAD_DIR);
	    });
	};
};