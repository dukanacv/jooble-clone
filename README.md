# Job portal application

This is a simple, yet effective, job portal application built using Spring framework, where job seekers can search for 
jobs and apply for them. On the other side, companies can post job oppenings and manage the applications they receive.

### Features
- Users can register as either a job seekers(app users) or a company, gaining respective roles
- App users can search for job posts and apply for them
- Companies can post job offers and manage(view, edit or delete) received users applications for certain job
- Authentication and authorization is implemented to restrict access to certain endpoints and features

### Technology stack used in app
- Spring boot
- Spring Data JPA
- Hibernate
- MySQL relational database
- Hibernate
- Swagger

### Getting started
1. Clone the repository: git clone https://github.com/dukanacv/jooble-clone.git
2. Import the project into your preferred IDE
3. Build and run the application
4. On your web browser, navigate to http://localhost:8080/users/register or http://localhost:8080/companies/register
5. Register as app user or company to start using application

### API endpoints to lookout for
- /users/register (POST) - register a new user
- /companies/regsiter (POST) - register new company
- /users/login (POST) - login an existing user(company)
- /job-posts/all (GET) - get all job posts
- /job-posts/create (POST) - create a new job post
- /job-posts/search (GET) - search a specific job(s) by their title(name)
- /job-posts/{id} (PUT) - update a specific job post by id
- /job-posts/{id} (DELETE) - delete a specific job post by id
- /applications/apply (POST) - apply for a job post
- /applications/all/{id} (GET) - get all applications for certain job
- /applications/{id} (DELETE) - delete an application by id
