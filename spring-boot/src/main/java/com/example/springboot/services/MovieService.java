package com.example.springboot.services;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.amazonaws.services.dynamodbv2.document.Item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    @Autowired
    private DynamoDB dynamoDB;
    private String tableName = "Movies";

    public void createTable() throws Exception {

        if (!isTableExist()) {
            return;
        }

        System.out.println("Attempting to create table; please wait...");
        Table table = dynamoDB.createTable(tableName,
                Arrays.asList(new KeySchemaElement("year", KeyType.HASH), // Partition
                                                                          // key
                        new KeySchemaElement("title", KeyType.RANGE)), // Sort key
                Arrays.asList(new AttributeDefinition("year", ScalarAttributeType.N),
                        new AttributeDefinition("title", ScalarAttributeType.S)),
                new ProvisionedThroughput(10L, 10L));
        table.waitForActive();
        System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());
    }

    public Boolean deleteTable() {

        if (!isTableExist()) {
            return true;
        }

        Table table = dynamoDB.getTable(tableName);
        try {
            System.out.println("Attempting to delete table; please wait...");
            table.delete();
            table.waitForDelete();
            System.out.print("Success.");
            return true;
        } catch (Exception e) {
            System.err.println("Unable to delete table: ");
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean isTableExist() {
        try {
            dynamoDB.getTable(tableName).describe();
            return true;
        } catch (com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException rnfe) {
            System.out.println("Table does not exist");
        }
        return false;

    }

    public void loadTable() {

        if (isTableExist()) {
            deleteTable();
        }

        try {
            createTable();
            dynamoDB.getTable(tableName);

            Table table = dynamoDB.getTable("Movies");
            JsonParser parser = new JsonFactory().createParser(new File("moviedata.json"));

            JsonNode rootNode = new ObjectMapper().readTree(parser);
            Iterator<JsonNode> iter = rootNode.iterator();

            ObjectNode currentNode;

            while (iter.hasNext()) {
                currentNode = (ObjectNode) iter.next();

                int year = currentNode.path("year").asInt();
                String title = currentNode.path("title").asText();

                try {
                    table.putItem(new Item().withPrimaryKey("year", year, "title", title).withJSON("info",
                            currentNode.path("info").toString()));
                    System.out.println("PutItem succeeded: " + year + " " + title);

                } catch (Exception e) {
                    System.err.println("Unable to add movie: " + year + " " + title);
                    System.err.println(e.getMessage());
                    break;
                }
            }
            parser.close();
        } catch (Exception e) {
            System.err.println("Unable to create table: ");
            System.err.println(e.getMessage());
        }
    }
}
