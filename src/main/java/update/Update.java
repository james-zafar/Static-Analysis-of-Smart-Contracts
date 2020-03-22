package update;
/*
 * com.amazonaws libraries imported from
 *https://jar-download.com/artifacts/com.amazonaws/aws-java-sdk-s3
 */

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;

public class Update {

    public Update() {
        try{
            final AWSCredentials credentials = new BasicAWSCredentials(
                    "AKIASK7K4P3QKM2X4YKU",
                    "cqhmZKie8i1ZFWA1Ckjr4KqvalicneuZGyG10qkP"
            );
            final AmazonS3 s3 = AmazonS3ClientBuilder
                    .standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withRegion(Regions.EU_WEST_2)
                    .build();

           deleteConfigFiles(s3);
           checkDictionaryFiles(s3);
           System.out.println("Creating new dictionary...");
           new CreateDictionary();
           createDictionary(s3);
           System.out.println("Removing local files...");
           removeLocalFiles();
           System.out.println("Update completed successfully");
        } catch (AmazonServiceException e) {
            System.out.println("Error, the request could not be processed");
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (SdkClientException e) {
            System.out.println("Error - the process service is unavailable at this time");
            System.out.println(e.getMessage());
        } catch(IOException e) {
            System.out.println("IO Error: Can not delete local files");
            System.out.println("All other operations successfully executed...");
            System.out.print(e.getMessage());
        }
    }

    /**
     *
     * @param s3 An instance of Amazon S3
     * @throws SdkClientException If the Amazon S3 can not be reached or the response can not be processed
     */
    private void deleteConfigFiles(AmazonS3 s3) throws SdkClientException{
        System.out.println("Checking for config files...");
        if(s3.doesObjectExist("dissertation-bucket", "webDisplay.json")) {
            System.out.println("Removing old config files...");
            s3.deleteObject(new DeleteObjectRequest("dissertation-bucket", "webDisplay.json"));
            System.out.println("Successfully removed config files...");
        }else {
            System.out.println("No old config files found...");
        }
    }

    /**
     *
     * @param s3 An instance of Amazon S3
     * @throws SdkClientException If the Amazon S3 can not be reached or the response can not be processed
     */
    private void checkDictionaryFiles(AmazonS3 s3) throws SdkClientException {
        if(s3.doesObjectExist("dissertation-bucket", "definitions.json")) {
            System.out.println("Removing old dictionary files...");
            s3.deleteObject(new DeleteObjectRequest("dissertation-bucket", "definitions.json"));
            System.out.println("Successfully removed dictionary files...");
        } else {
            System.out.println("No dictionary to update - a new dictionary will be created...");
        }
    }

    /**
     *
     * @param s3 An instance of Amazon S3
     * @throws AmazonServiceException if Amazon S3 can not be reached
     */
    private void createDictionary(AmazonS3 s3) throws AmazonServiceException{
        String filePath = System.getProperty("user.dir") + "/src/main/java/update/Dictionary.json";
        s3.putObject(new PutObjectRequest(
                "dissertation-bucket", "Dictionary.json", new File(filePath))
                .withCannedAcl(CannedAccessControlList.PublicRead)
        );
    }

    /**
     *
     * @throws IOException if local files can not be removed
     */
    private void removeLocalFiles() throws IOException {
        String displayPath = System.getProperty("user.dir") + "/src/main/java/upload/webDisplay.json";
        String dictPath = System.getProperty("user.dir") + "/src/main/java/update/Dictionary.json";
        File webDisplay = new File(displayPath);
        File dictFile = new File(dictPath);
        if(webDisplay.exists()) {
            if(webDisplay.delete()) {
                System.out.println("Successfully removed local config files...");
            }else {
                throw new IOException();
            }
        }

        if(dictFile.exists()) {
            if(dictFile.delete()) {
                System.out.println("Successfully removed local dictionary files");
            }else {
                throw new IOException();
            }
        }
    }
}
