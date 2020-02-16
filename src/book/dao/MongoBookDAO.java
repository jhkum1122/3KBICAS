package book.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import book.converter.BookConverter;
import book.model.Book;

//DAO class for different MongoDB CRUD operations
//take special note of "id" String to ObjectId conversion and vice versa
//also take note of "_id" key for primary key
public class MongoBookDAO {

	private DBCollection col;

	public MongoBookDAO(MongoClient mongo) {
		
		System.out.println("Here is MongoDBBookDAO.MongoDBBookDAO()");

		this.col = mongo.getDB("testDB").getCollection("Books");
	}

	public Book createBook(Book b) {
		
		System.out.println("Here is MongoDBBookDAO.createBook()");

		DBObject doc = BookConverter.toDBObject(b);
		this.col.insert(doc);
		ObjectId id = (ObjectId) doc.get("_id");
		b.setId(id.toString());
		return b;
	}

	public void updateBook(Book b) {
		
		System.out.println("Here is MongoDBBookDAO.updateBook()");

		DBObject query = BasicDBObjectBuilder.start()
				.append("_id", new ObjectId(b.getId())).get();
		this.col.update(query, BookConverter.toDBObject(b));
	}

	public List<Book> readAllBook() {
		
		System.out.println("Here is MongoDBBookDAO.readAllBook()");

		List<Book> data = new ArrayList<Book>();
		DBCursor cursor = col.find();
		while (cursor.hasNext()) {
			DBObject doc = cursor.next();
			Book p = BookConverter.toBook(doc);
			data.add(p);
		}
		return data;
	}

	public void deleteBook(Book p) {
		
		System.out.println("Here is MongoDBBookDAO.deleteBook()");

		DBObject query = BasicDBObjectBuilder.start()
				.append("_id", new ObjectId(p.getId())).get();
		this.col.remove(query);
	}

	public Book readBook(Book p) {
		
		System.out.println("Here is MongoDBBookDAO.readBook()");

		DBObject query = BasicDBObjectBuilder.start()
				.append("_id", new ObjectId(p.getId())).get();
		DBObject data = this.col.findOne(query);
		return BookConverter.toBook(data);
	}

}
