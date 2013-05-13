
/*
 * GET home page.
 */

exports.index = function(req, res){
  res.render('index', { title: 'The Node.js and the Express.js tutorial' });
};