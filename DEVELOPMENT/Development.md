# Development Phase and Details
DEVELOPMENT folder should be used for sharing of any temporary resource until development purposes.

## Postman Collection

> HammerDown.postman_collection.json

Above file can be directly imported inside Postman to get latest end points configured for testing.

NOTE: Any change in Postman Collection must be updated by replacing the current json file with lastest one.

## Server

### H2 Console Database
On the browser append /h2-console to open up the h2 database.

Example URL: locahost:8080/h2-console

Username and password can be kept as it, as they are configured via application_properties. Just hit connect and see the data.

Data Initializr Function can be used to add some initial data every time server restarts.