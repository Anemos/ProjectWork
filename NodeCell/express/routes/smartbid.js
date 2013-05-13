// just a dummy response for now, persistence we'll add later
var dummyResponse =  {
    "name":"Monty Python and the search for the holy grail",
    "id":123,
    "startPrice":0.69,
    "currency":"GBP",
    "description":"Must have item",
    "links":[
        {
            "linkType":"application/vnd.smartbid.item",
            "rel":"Add item to watchlist",
            "href":"/users/123/watchlist"
        },
        {
            "linkType":"application/vnd.smartbid.bid",
            "rel":"Place bid on item",
            "href":"/items/123/bid"
        },
        {
            "linkType":"application/vnd.smartbid.user",
            "rel":"Get owner's details",
            "href":"/users/123"
        }
    ]
};

//pass in the app so we can register routes
module.exports = function(app) {
	app.get('/items/:itemid',get)
}

// called from app.js
get = function(req,res){
	res.type('application/vnd.smartbid.item+json');
	res.send(200,dummyResponse);
}