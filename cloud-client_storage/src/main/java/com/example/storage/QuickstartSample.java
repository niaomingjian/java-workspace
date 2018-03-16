
package com.example.storage;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class QuickstartSample {
  public static void main(String... args) throws Exception {
    createBucket();
    
    //upload();
    
    //download();
  }

  private static void createBucket() throws Exception {
//    String SERVICE_ACCOUNT_JSON_PATH = "C:\\Users\\chenyy3.SJNS\\gcpconfig\\gcp20180311-4edbf4ff2bfb.json";
//    Storage storage = StorageOptions.newBuilder()
//        .setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream(SERVICE_ACCOUNT_JSON_PATH))).build()
//        .getService();
    
    Storage storage = StorageOptions.getDefaultInstance().getService();
    String bucketName = "my-new-bucket-abc-20180314001";
    // Creates the new bucket
    Bucket bucket = storage.create(BucketInfo.of(bucketName));

    System.out.printf("Bucket %s created.%n", bucket.getName());
  }
  
  private static void download() {
     // Instantiates a client
    
    // Set GOOGLE_APPLICATION_CREDENTIALS environment variable in advance
    Storage storage = StorageOptions.getDefaultInstance().getService();

    BlobId blobId = BlobId.of("nmjcloud_jar_test", "addons/simple-bean-1.0.jar");
    Blob blob = storage.get(blobId);

    Path path = Paths.get("D:\\lib\\simple-bean-1.0.jar");
    blob.downloadTo(path);

    System.out.printf("Download successfully%n");
  }
  
  private static void upload() throws UnsupportedEncodingException {
    // Instantiates a client
   
   // Set GOOGLE_APPLICATION_CREDENTIALS environment variable in advance
   Storage storage = StorageOptions.getDefaultInstance().getService();

   String bucketName = "nmj-my-bucket-name";
   String blobName = "my_blob_name.txt";
   BlobId blobId = BlobId.of(bucketName, blobName);
   BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();
   Blob blob = storage.create(blobInfo, "Hello, World!".getBytes("UTF-8"));
   
   System.out.printf("Upload successfully%n");
 }
}
