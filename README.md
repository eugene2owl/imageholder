# imageholder
Spring Boot Restful API integrated with several AWS services via AWS SDK.

## Integration with AWS S3
Application stores metadata of the images in the database, and their content in S3 buckets.

### Upload the image via Postman
<img src="https://raw.githubusercontent.com/eugene2owl/imageholder/main/src/assets/postman1.png" alt="drawing" width="800"/>

### Read all images metadata via Postman
<img src="https://raw.githubusercontent.com/eugene2owl/imageholder/main/src/assets/postman2.png" alt="drawing" width="800"/>

### Download the image via Postman
<img src="https://raw.githubusercontent.com/eugene2owl/imageholder/main/src/assets/postman3.png" alt="drawing" width="800"/>

### Images metadata in the database table
<img src="https://raw.githubusercontent.com/eugene2owl/imageholder/main/src/assets/db.png" alt="drawing" width="800"/>

### Images content in the AWS S3 bucket
<img src="https://raw.githubusercontent.com/eugene2owl/imageholder/main/src/assets/s3.png" alt="drawing" width="800"/>

## Integration with AWS SQS
Application does poll messages from SQS queue, parse and process them.
It uses long polling and batching technologies while polling messages from the queue.

## Integration with AWS SNS
Application does build notifications with appropriate text and sends them to SNS topic.

### Email received by email subscribed on the SNS topic
<img src="https://raw.githubusercontent.com/eugene2owl/imageholder/main/src/assets/sns-email.png" alt="drawing" width="800"/>

## Integration with AWS Lambda
Application does call Lambda function using AWS SDK client for the Lambda service (as it does for all other AWS services too).
