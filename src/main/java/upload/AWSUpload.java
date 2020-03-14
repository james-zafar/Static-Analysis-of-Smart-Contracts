package upload;

/*
 * com.amazonaws libraries imported from
 *https://jar-download.com/artifacts/com.amazonaws/aws-java-sdk-s3
 */

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;

public class AWSUpload {

    public AWSUpload() {
        AWSCredentials credentials = new BasicAWSCredentials(
                "AKIASK7K4P3QKM2X4YKU",
                "cqhmZKie8i1ZFWA1Ckjr4KqvalicneuZGyG10qkP"
        );
        final AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.EU_WEST_2)
                .build();
        String filePath = System.getProperty("user.dir") + "/src/main/java/upload/webDisplay.JSON";
        try {
            s3.putObject(new PutObjectRequest(
                    "dissertation-bucket", "json1.json", new File(filePath))
                    .withCannedAcl(CannedAccessControlList.PublicRead)
            );
        } catch (AmazonServiceException e) {
            System.out.println(e.getMessage());
        }
    }
}


