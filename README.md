# BCBSM Full Stack Developer Candidate Coding Exercise

## Requirements:
1.	Create a single page web application with a login screen (username, password)
2.	Demonstrate user login and authentication - Spring Security
3.	Upon login present member with ability to compress the multiple files and download it as a ZIP/RAR file, User must be login to access this page
4.	Display success or Failure message on web page, once a file downloaded successfuly or failed.
5.  Fork this repository and we will review your code from the fork.
6.  Mandatory to provide a code and workable application walk through 

## Tech Stack:  
    Springboot  
    Angular  
    MongoDB

## About project setup
Setup have parent project with 2 modules, one for springboot api and another for angular ui project.
###	Run UI (VSCode)
Run below commands to build and run UI on node. Application will star on http://localhost:4200
	npm install
	ng serve
Below are the steps to run api project

###	Run API (in eclipse)
1. Change the mongodb connection url in applicaiton.properites
Currently I am using free tier AWS based mongodb database

2. Once application is up and running, use postman to run following end-point. Application is running on "localhost:8080" url

	a. POST: <url>/register: This will register new user. Verify the data in database table "users"
		{
    		"username":"krishna",
    		"password":"sunday123"
		}
		
	b. POST: <url>/authenticate: This will return a JWT token in response. All subsequent requests must contain the JWT token in request.
		{
    		"username":"krishna",
    		"password":"sunday123"
		}
	
	c. GET: <url>/greeting: Test authentication. Use above generated JWT token in "Authorization" Type {Bearer Token}
	
	d. POST: <url>/uploadfiles: After JWT token setup, set 2 form-data param in Body. Verify in db in "ImageFile" table
		This service does not support "GridFS".  
		key=title, value={string name of file}
		key=image, value={actual image file}			//remember to change the key type to file
	
	e. GET: <url>/imageFiles/{id}: Test if files are fetched from mongodb and sent as download-able file.
		Once you receive the response, save it as file to check the file content.
		example url: localhost:8080/imageFileAsBytes/64ee202f8b1a110afdcf9b02
	f. POST: <url>/zipImageFiles: Send fileId list as comma separated string in body request param that you want to download.
		key=fileIdList, value={64ee202f8b1a110afdcf9b02,64ee20a18b1a110afdcf9b05}
	