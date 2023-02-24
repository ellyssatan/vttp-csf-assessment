package vttp2022.csf.assessment.server.repositories;

import java.util.HashMap;
import java.util.Map;
import java.io.ByteArrayOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.UUID;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class MapCache {


	@Value("${space.bucket}")
	private String bucket;
	@Value("${space.endpoint.url}")
	private String endpointUrl;

	@Autowired
	private AmazonS3 s3;

	static String FILEPATH = "";
    static File file = new File(FILEPATH);

	// TODO Task 4
	// Use this method to retrieve the map
	// You can add any parameters (if any) and the return type 
	// DO NOT CHNAGE THE METHOD'S NAME
	public String getMap(byte[] bytes) {
		// Implmementation in here

		Map<String, String> userData = new HashMap<>();
	  
		// InputStream targetStream = new ByteArrayInputStream(bytes);
	
		try {
            OutputStream os = new FileOutputStream(file);
            os.write(bytes);
            System.out.println("Successful");
 
            // Close the file connections
            os.close();
        }
 
        // Catch block to handle the exceptions
        catch (Exception e) {
 
            // Display exception on console
            System.out.println("Exception: " + e);
        }

        String key = UUID.randomUUID().toString().substring(0, 8);

        StringTokenizer tk = new StringTokenizer(key, ".");
        int count = 0;
        String filenameExt = "";

        while (tk.hasMoreTokens()) {
            if (count == 1) {
                filenameExt = tk.nextToken();
                break;
            } else {
                filenameExt = tk.nextToken();
            }
            count++;
        }

        System.out.println("myobjects/%s.%s".formatted(key, filenameExt));

        if (filenameExt.equals("blob")) {
            filenameExt = filenameExt + ".png";
        }

        PutObjectRequest putReq = new PutObjectRequest(
            "databucket",
            "myobject/%s.%s".formatted(key, filenameExt),
            file);

        putReq.withCannedAcl(CannedAccessControlList.PublicRead);
        s3.putObject(putReq);

		// image url in digital ocean
		return "https://%s.%s/%s".formatted(bucket, endpointUrl, "myobjects/%s.%s".formatted(key, filenameExt));
	}

	// You may add other methods to this class

}
