
# A Small Swing Demo

[![build](https://github.com/david-hamann/Tiny-Swing-Demo/actions/workflows/maven.yml/badge.svg)](https://github.com/david-hamann/Tiny-Swing-Demo/actions/workflows/maven.yml)

[//]: # (TODO add coverage and other tags)



This is a small demo using Java Swing.

Minimal unit tests have been written at this point, but there is one unit test given for flavor.  Anything beyond demo code would warrant fine tuning the UI and more thorough testing.

## Prerequisites

* Java 17

## Build and test

```bash
mvn clean test package
```

## Run the application

```bash
java -jar target/swing-demo-0.0.1-snapshot-shaded.jar
```

Executing `./run` from the root of the project will build, test, create the shaded jar file, and then run the application.



## Usage

Start by searching for an element ID.  "Search" is misnamed, since it is an ID lookup.  To start out, enter `123455` or `123456` into the search field, and perform the search.  These two objects are loaded into a demo data-store at startup.

Once one of the objects are displayed, you can update everything, except the ID, and save the change back to the data store.  If you press the "clear" button, you reset the form to a blank, and you can enter values, and save which will save to the datastore and display the new object ID.

The data element consists of the following fields

```java
public class Widget {
	public final String name;
	public final String description;
	public final List<String> attributes;
	public final long created;
	public final long updated;
}
```
The attributes are represented in the form by a comma-delimitted string.


The buttons along the lower edge of the form perform the following actions.

   - `clear` - Clears the form and resets all fields.
   - `reset` - Resets the current object and removes any unsaved changes.
   - `save`  - Saves a new or updated object.

A save will not be performed on an empty or unchanged object.

