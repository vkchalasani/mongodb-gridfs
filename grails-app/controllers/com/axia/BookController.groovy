package com.axia

import com.mongodb.*
import com.mongodb.gridfs.GridFSDBFile
import com.mongodb.BasicDBObject
import com.mongodb.gridfs.GridFS
import com.mongodb.gridfs.GridFSInputFile
import com.mongodb.DBObject
import com.mongodb.WriteConcern
import com.mongodb.DB
import com.mongodb.BasicDBObject
import com.mongodb.gridfs.GridFSFile
import com.mongodb.client.gridfs.GridFSBucket
import com.mongodb.gridfs.GridFS
import com.mongodb.client.gridfs.GridFSBuckets
import java.io.InputStream
import com.mongodb.client.gridfs.model.GridFSUploadOptions
import org.bson.Document
import com.mongodb.client.FindIterable
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Sorts
import org.bson.conversions.Bson
import static java.util.Arrays.asList

class BookController {

	def mongoclient = new MongoClient()
	def db = mongoclient.getDatabase("test")
	GridFSBucket gridFSBucket = GridFSBuckets.create(db) 

    def index() { 
    	render "Hello Mongo"
    	println "Hello Mongo Print"
    	println "printing db name"
    	println db.getName()
    	def filename = "nmaapdf4"
    	//def count = db.getCollection("fs.files").find(Filters.eq("filename", "nmaa3")).count()
    	def count = db.getCollection("fs.files").count(Filters.eq("filename", filename))
    	println count
    	if(count==1){
    		//Bson doc1 = new Document("filename", "nmaapdf");
			//Bson doc2 = new Document("metadata.status", "old");
			//Bson doc3 = new Document("\$set", doc2);
			db.getCollection("fs.files").update([filename:"nmaapdf4"],[$set:["metadata.status" : "old"]])
			//db.getCollection("fs.files").updateOne(new Document("filename", "nmaapdf2"),
		        //new Document("\$set", new Document("metadata.status", "old")));
			//db.getCollection("fs.files").updateMany(doc1,doc3);
    		//db.getCollection("fs.files").updateMany(new Document("filename", "nmaapdf"),new Document("\$set", new Document("metadata.status", "old")))
    	}
    	if(count>1){
    		println "count more than one"
    		db.getCollection("fs.files").update([$and:[["filename":"nmaapdf4"],["metadata.status":"current"]]],[$set:["metadata.status" : "old"]])
    		//db.getCollection("fs.files").findOne("metadata.status" : "current")
    		//db.getCollection("fs.files").update([filename:"nmaapdf3"],[$set:["metadata.status" : "old"]])
    	}
    	def version = count+1
    	def input = new File('C:/Users/vchalasani/Desktop/from scratch/new_Flex Term Conversion Application.pdf').newInputStream()
    	def options = new GridFSUploadOptions().metadata(new Document("version", String.valueOf(version)).append("status","current"))
    	gridFSBucket.uploadFromStream("nmaapdf4", input, options)
    	InputStream streamToUploadFrom = new FileInputStream(new File("C:/Users/vchalasani/Desktop/from scratch/new_Family Plan Conversion Application (1).pdf"))
    }
    def find(){
    	render "finding documents"
    	//db.getCollection("fs.files").update([$and:[["filename":"nmaapdf3"],["metadata.status":"just"]]],[$set:["metadata.status" : "another"]])
    	//println(db.getCollection("fs.files").findOne([$and:[["filename":"nmaapdf3"],["metadata.status":"another"]]]))
    	//println(db.getCollection("fs.files").update({$and:[{filename:"nmaapdf3"},{"metadata.status":"just"}]},{$set:{"metadata.status" : "another"}}))
    	//println(db.getCollection("fs.files").update([filename:"nmaapdf3"],[$set:["metadata.status" : "old"]])
    	//def doc = db.getCollection("fs.files").findOne("metadata.status" : "old");
    	FindIterable<Document> iterable = db.getCollection("fs.files").find(Filters.eq("filename", "nmaapdf4"))
    	for (Document d : iterable){
    		println(d)
    	}
    	//db.fs.files.find({$and:[{filename : "nmaapdf3"},{"metadata.status" : "testingchange"}]})
    	//println(db.fs.files.find({$and:[{filename : "nmaapdf3"},{"metadata.status" : "testingchange"}]}))
    }
}
