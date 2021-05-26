# /usr/bin/env python

from __future__ import print_function
import json
import os
import base64
import logging

# Global logging configuration
logger = logging.getLogger()
logger.setLevel(logging.INFO)


def lambda_handler(event, context):
    logger.info('## EVENT => '+str(event))
    result = "Dropped"
    output = []
    for record in event["records"]:
        # Kinesis data is base64 encoded so decode here
        payload = base64.b64decode(record["data"])
        data = json.loads(payload)
        print("data is => ", data)
        user_country = data["results"][0]["location"]["country"]
        if user_country == "India":
            result = "Ok"

        output_record = {
            'recordId': record['recordId'],
            'result': result,
            'data': record["data"]
        }
        output.append(output_record)

    return output
