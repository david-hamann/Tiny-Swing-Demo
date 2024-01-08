
# A Small Swing Demo


## Build and test

```bash
mvn clean test package
```

## Run the application

```bash
java -jar target/swing-demo-0.0.1-snapshot-shaded.jar
```




## Usage

Start by searching for an element ID.  "Search" is misnamed, since it is an ID lookup.  To start out, enter `123455` or `123456` into the search field, and perform the search.  These two objects are loaded into a demo data-store at startup.

Once one of the objects are displayed, you can update everything, except the ID, and save the change back to the data store.  If you press the "clear" button, you reset the form to a blank, and you can enter values, and save which will save to the datastore and display the new object ID.



