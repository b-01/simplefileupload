# simplefileupload
File sharing platform written in Java/Vaadin.
The project is archived, outdated and not maintained.

## Setup

 1. Create database with the provided database script
 2. Change to filesusers password (in DB)
 3. Create an entry `admin:<password>` in the users table!
   + password = `sha256(<pw>_<salt>)` (*mind the underline*)
 3. Set values in `at/b01/simplefileuploader/config/configuration.xml`
 4. Create directories on the server and set the correct file permissions
 5. Upload updated WAR-File
	
	
## Info

 * The deploy path is set in `context.xml` (currently set to /files) 
 * This results in an url like: `http://xxx.xxx/files`
