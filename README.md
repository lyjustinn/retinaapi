# retinaapi
API created to facilitate image uploads to AWS S3. It manages authentication/authorization using JWT and Spring Security and updates databases with Spring JPA.

### Prerequisites

Before you get started:
1. Please make sure you have an AWS account with an S3 bucket to upload images to
2. Also make sure you have an aws credentials file or some other way for the AWS SDK to use your account
3. You will also need a mySQL database either running locally, or somewhere in the cloud
4. Make sure you add an application.properties file with the right connection string, drivers, cors origins, etc
