import java.util.List;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;


public class Hello {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// show a message
		System.out.println("Hello World!");
		try {
			/*
			Mongo m = new Mongo();
			DB db = m.getDB("test");
			DBCollection coll = db.getCollection("tickets");
			
			BasicDBObject doc = new BasicDBObject();

			doc.put("officer", "Andrew Smith");
			doc.put("location", "Target Shopping Center parking lot");
			doc.put("vehicle_plate", "Virginia 2345");
			doc.put("offense", "Double parked");
			doc.put("date", "2010/08/13");

			coll.insert(doc);
			
			DBCursor cur = coll.find();
			while (cur.hasNext()) {
			 System.out.println(cur.next());
			}
			*/
			Mongo m = new Mongo();
			DB db = m.getDB("twitter_stats");
			DBCollection coll = db.getCollection("retweets");

			Twitter twitter = new TwitterFactory().getInstance("<some user name>", "<some password>");
			List<Status> statuses = twitter.getRetweetsOfMe();
			for (Status status : statuses) { 
			  ResponseList<User> users = twitter.getRetweetedBy(status.getId());
			  
			  for (User user : users) {
			    BasicDBObject doc = new BasicDBObject();
			    doc.put("user_name", user.getScreenName());
			    doc.put("tweet", status.getText());
			    doc.put("tweet_id", status.getId());
			    doc.put("date", status.getCreatedAt());
			    coll.insert(doc);
			 }
			}
		} catch( Exception e) {
			System.out.println(e.toString());
		}
	}

}
