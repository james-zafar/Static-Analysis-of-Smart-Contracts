package upload;

/*
 * com.amazonaws libraries imported from
 * hx   x   ttps://jar-download.com/artifacts/com.amazonaws/aws-java-sdk-s3
 */

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import java.io.File;

public class AWSUpload {

    public AWSUpload() {
        AWSCredentials ac = new BasicAWSCredentials("", "");
        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(ac))
                .withRegion(Regions.DEFAULT_REGION)
                .build();
        s3client.putObject("dissertation-bucket", "bucket", new File(".\\json\\"));
    }
}


