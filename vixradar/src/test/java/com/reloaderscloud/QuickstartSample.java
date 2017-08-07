package com.reloaderscloud;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class QuickstartSample {
	 public static void main(String... args) throws Exception {
		    // Instantiates a client
		    Storage storage = StorageOptions.getDefaultInstance().getService();

		    // The name for the new bucket
		    String bucketName = "vix-radar.appspot.com";  // "my-new-bucket";

		    // Creates the new bucket
		    Bucket bucket = storage.get(bucketName);

		    System.out.printf("Bucket %s created.%n", bucket.getName());
		  }
}
