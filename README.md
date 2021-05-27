# Kinesis

This repo contains sample applications using Kinesis Data Stream, Kinesis Data Firehose, Kinesis Data Analytics.

## Run Kinesis Data Stream Application

### Build and Package

```bash
cd  data-stream; mvn clean package
```

### Run KPL Producer

Producer requires three inputs :

1. Name of the Kinesis Data Stream.
2. AWS Region
3. Time to run

To run the producer to put some data into a stream called ``mykinesis`` in ``ap-southeast-1`` for ``100 seconds``:

```bash
mvn exec:java -Dexec.mainClass="com.amazonaws.services.kinesis.producer.sample.SampleProducer" -Dexec.args="mykinesis ap-southeast-1 100"
```

### Run KCL Consumer

In Progress..

## Run Kinesis Data Firehose Application

Once you create a kinesis data stream and produce the data for the stream using KPL library, you can set up a kinesis firhose application to transform this data and send it data to S3.

### Setup Firehose Data Transformer

Kinesis Data Firehose can invoke a Lambda function to transform incoming source data and deliver the transformed data to destinations.

The input datato lambda function is in this format :

```json
{
    "invocationId":"<Invocation Id>",
    "sourceKinesisStreamArn":"<Kinesis Stream ARN>",
    "deliveryStreamArn":"<Kinesis Firehose ARN>",
    "region":"<AWS REGION>",
    "records":[
       {
          "recordId":"<Record Id>",
          "approximateArrivalTimestamp":"Timestamp",
          "data":"<Base64 Encoded Data>",
          "kinesisRecordMetadata":{
             "sequenceNumber":"<Sequence Number>",
             "subsequenceNumber":"<Sub Sequence Number>",
             "partitionKey":"<Partition Key>",
             "shardId":"<Shard Id>",
             "approximateArrivalTimestamp":"Timestamp"
          }
       }
    ]
}
```

Example:

```json

{
    "invocationId":"f0c1a2e2-c434-47f9-ba79-e615431e1e8d",
    "sourceKinesisStreamArn":"arn:aws:kinesis:ap-southeast-1:078261783316:stream/mykinesis2",
    "deliveryStreamArn":"arn:aws:firehose:ap-southeast-1:078261783316:deliverystream/mykinesis2-firehose",
    "region":"ap-southeast-1",
    "records":[
       {
          "recordId":"49618613381198302189976705307357840920689311765172846594000000",
          "approximateArrivalTimestamp":1622030317789,
          "data":"eyJyZXN1bHRzIjpbeyJnZW5kZXIiOiJtYWxlIiwibmFtZSI6eyJ0aXRsZSI6Ik1yIiwiZmlyc3QiOiJNYcOrbCIsImxhc3QiOiJHdWlsbG90In0sImxvY2F0aW9uIjp7InN0cmVldCI6eyJudW1iZXIiOjI5ODYsIm5hbWUiOiJSdWUgQmF0YWlsbGUifSwiY2l0eSI6IkNoYW1waWdueS1zdXItTWFybmUiLCJzdGF0ZSI6IkhhdXRlcy1BbHBlcyIsImNvdW50cnkiOiJGcmFuY2UiLCJwb3N0Y29kZSI6MjY4ODAsImNvb3JkaW5hdGVzIjp7ImxhdGl0dWRlIjoiLTQuODgyMiIsImxvbmdpdHVkZSI6Ii0xMDcuMjMxMCJ9LCJ0aW1lem9uZSI6eyJvZmZzZXQiOiItMzowMCIsImRlc2NyaXB0aW9uIjoiQnJhemlsLCBCdWVub3MgQWlyZXMsIEdlb3JnZXRvd24ifX0sImVtYWlsIjoibWFlbC5ndWlsbG90QGV4YW1wbGUuY29tIiwibG9naW4iOnsidXVpZCI6ImE2NTQ2MzczLTJkNGEtNGY5OS04ZjA3LThjNDA5YTdiOWIzNCIsInVzZXJuYW1lIjoiYW5ncnlsYWR5YnVnNTU2IiwicGFzc3dvcmQiOiJ3ZWRkaW5nIiwic2FsdCI6ImpadE85TElhIiwibWQ1IjoiNWUzYzczZDIyMjFkNGM1NDFlM2VkMDVlY2Q4NTExYzIiLCJzaGExIjoiMjRiMzdjNGYxYzUxMDIzMzZkMGQ4NTIxNTc0NTAxZDM2MTAzYjY5YyIsInNoYTI1NiI6IjRhMGE5MGZhN2Y3Zjc5Mzc2NTU4NzdmM2I0NmZkMzY1ZTFmZmFjZWY3YTllN2U2MzM1NDlhNWE3YTNmNDdiNmEifSwiZG9iIjp7ImRhdGUiOiIxOTg4LTAyLTA5VDExOjA0OjI0LjM0NFoiLCJhZ2UiOjMzfSwicmVnaXN0ZXJlZCI6eyJkYXRlIjoiMjAwNi0xMi0wNFQxNzoyMToyNC4xMjJaIiwiYWdlIjoxNX0sInBob25lIjoiMDQtNjQtNzYtNzItMjgiLCJjZWxsIjoiMDYtMzItNzUtNjMtNTEiLCJpZCI6eyJuYW1lIjoiSU5TRUUiLCJ2YWx1ZSI6IjFOTmFONDEzODE5NjEgMjMifSwicGljdHVyZSI6eyJsYXJnZSI6Imh0dHBzOi8vcmFuZG9tdXNlci5tZS9hcGkvcG9ydHJhaXRzL21lbi8xLmpwZyIsIm1lZGl1bSI6Imh0dHBzOi8vcmFuZG9tdXNlci5tZS9hcGkvcG9ydHJhaXRzL21lZC9tZW4vMS5qcGciLCJ0aHVtYm5haWwiOiJodHRwczovL3JhbmRvbXVzZXIubWUvYXBpL3BvcnRyYWl0cy90aHVtYi9tZW4vMS5qcGcifSwibmF0IjoiRlIifV0sImluZm8iOnsic2VlZCI6IjgxMTg1ZTVjM2ViYWE5ZTMiLCJyZXN1bHRzIjoxLCJwYWdlIjoxLCJ2ZXJzaW9uIjoiMS4zIn19",
          "kinesisRecordMetadata":{
             "sequenceNumber":"49618613381198302189976705307357840920689311765172846594",
             "subsequenceNumber":0,
             "partitionKey":"1622030248966",
             "shardId":"shardId-000000000000",
             "approximateArrivalTimestamp":1622030317789
          }
       }
    ]
}

```

## Kinesis Data Analytics



## Appendix

### Kinesis Docs Reference

1. Kinesis Client Library -  https://docs.aws.amazon.com/streams/latest/dev/shared-throughput-kcl-consumers.html

2. Kinesis Firehose Transformation -  https://docs.aws.amazon.com/firehose/latest/dev/data-transformation.html

### ReInvent Videos

1. Kinesis Best Practices : https://www.youtube.com/watch?v=jKPlGznbfZ0