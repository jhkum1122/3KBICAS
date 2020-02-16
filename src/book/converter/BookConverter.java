package book.converter;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import book.model.Book;

public class BookConverter {

	// convert Book Object to MongoDB DBObject
	// take special note of converting id String to ObjectId
	public static DBObject toDBObject(Book p) {

		System.out.println("Here is BookConverter.toDBObject()");

		BasicDBObjectBuilder builder = BasicDBObjectBuilder.start()
				.append("name", p.getName()).append("country", p.getCountry());
		if (p.getId() != null)
			builder = builder.append("_id", new ObjectId(p.getId()));
		return builder.get();
	}

	// convert DBObject Object to Book
	// take special note of converting ObjectId to String
	public static Book toBook(DBObject doc) {

		System.out.println("Here is BookConverter.toBook()");

		Book p = new Book();
		p.setName((String) doc.get("name"));
		p.setCountry((String) doc.get("country"));
		ObjectId id = (ObjectId) doc.get("_id");
		p.setId(id.toString());
		return p;
	}

}
