/*
 * Copyright 2019 Amazon.com, Inc. or its affiliates.
 * Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.amazonaws.services.kinesis.producer.sample;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.ByteBuffer;
import java.util.Random;

public class Utils {
	private static final Random RANDOM = new Random();

	/**
	 * @return A random unsigned 128-bit int converted to a decimal string.
	 */
	public static String randomExplicitHashKey() {
		return new BigInteger(128, RANDOM).toString(10);
	}

	/**
	 * Generates a blob containing a UTF-8 string. The string begins with the
	 * sequence number in decimal notation, followed by a space, followed by
	 * padding.
	 * 
	 * @param sequenceNumber The sequence number to place at the beginning of the
	 *                       record data.
	 * @param totalLen       Total length of the data. After the sequence number,
	 *                       padding is added until this length is reached.
	 * @return ByteBuffer containing the blob
	 */
	public static ByteBuffer generateData(long sequenceNumber, int totalLen) {
		StringBuilder sb = new StringBuilder();
		sb.append(Long.toString(sequenceNumber));
		sb.append(" #####AWSMachineLearning####");
		while (sb.length() < totalLen) {
			sb.append("a");
		}
		try {
			System.out.println("Sending data to => " + sb);
			return ByteBuffer.wrap(sb.toString().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static ByteBuffer generateUserData(){

		ByteBuffer userData = null;
		try {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://randomuser.me/api/")).build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			String responseBody = response.body();

			userData = ByteBuffer.wrap(responseBody.toString().getBytes("UTF-8"));
			System.out.println(responseBody);
		} catch (Throwable ex) {
			throw new RuntimeException(ex);
		}

		return userData;
	}
}
