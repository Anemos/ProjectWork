import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.UserList;
import twitter4j.auth.AccessToken;

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
			
			Twitter twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer("yEBno90OUb9T0MwkzqlzpQ", 
			          "cdMq8s3h0bM7ovRMTx7FHk9LddelOD4ukA0RIU344M");
			AccessToken accessToken = new AccessToken("57646743-1y6YSN0C52J0sLfK91x1xwDi89LJY4nPA3mlnsX95", "tI3WSEnmIJ9Yt29yO2sWXVKXM57Dr1YVL3lAj3dyC8");
			twitter.setOAuthAccessToken(accessToken);
			
			ResponseList<UserList> lists = twitter.getUserLists("kissanemos");
            for (UserList list : lists) {
                System.out.println("id:" + list.getId() + ", name:" + list.getName() + ", description:"
                        + list.getDescription() + ", slug:" + list.getSlug() + "");
            }
            System.exit(0);
			
			/*
			List<Status> statuses = twitter.getRetweetsOfMe();
			for (Status status : statuses) { 
			  ResponseList<User> users = twitter.lookupUsers("kissanemos");
			  
			  for (User user : users) {
			    BasicDBObject doc = new BasicDBObject();
			    doc.put("user_name", user.getScreenName());
			    doc.put("tweet", status.getText());
			    doc.put("tweet_id", status.getId());
			    doc.put("date", status.getCreatedAt());
			    coll.insert(doc);
			 }
			}
			*/
		} catch( Exception te ) {
			te.printStackTrace();
            System.out.println("Failed to list the lists: " + te.getMessage());
            System.exit(-1);
		}
	}

}
