
# A Small Swing Demo

This is a very quickly built demo using Java Swing.  It is rough around the edges, but it should be relatively bug free.

Minimal unit tests have been written at this point, but there is one unit test given for flavor.  Anything beyond demo code would warrant more thoroughness.

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

The buttons along the lower edge of the form perform the following actions.
<dl>
	<dt>clear</dt>
		<dd>clears the form, and resets all fields</dd>
	<dt>reset</dt>
		<dd>resets the current object, removing any changes that have not been saved</dd>
	<dt>save</dt>
		<dd>Saves a new or updated object.</dd>
</dl>

A save will not be performed on an empty or unchanged object.



<style type="text/css">
 dl {
    padding: 0.5em;
  }
  dt {
    float: left;
    clear: left;
    width: 100px;
    text-align: right;
    font-weight: bold;
    color: blue;
  }
  dt::after {
    content: ":";
  }
  dd {
    margin: 0 0 0 110px;
    padding: 0 0 0.5em 0;
  }
</style>
