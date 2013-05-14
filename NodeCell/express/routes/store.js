var formidable = require('formidable')
,fs = require('fs')
,util = require('util')
,path = require('path');

// our 'database'
var items = {
    SKN:{name:'Shuriken', price:100},
    ASK:{name:'Ashiko', price:690},
    CGI:{name:'Chigiriki', price:250},
    NGT:{name:'Naginata', price:900},
    KTN:{name:'Katana', price:1000}
};

exports.home = function(req, res){
	if( typeof req.session.username  == 'undefined' ) {
		res.render('home', {title: 'Ninja Store'});
	} else {
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
	console.log("Item param id : " + req.params.id)
    // don't let nameless people view the items, redirect them back to the homepage
    if (typeof req.session.username == 'undefined') res.redirect('/');
    else {
        var name = items[req.params.id].name;
        var price = items[req.params.id].price;
        res.render('item', { title: 'Ninja Store - ' + name, username: req.session.username, name:name, price:price });
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