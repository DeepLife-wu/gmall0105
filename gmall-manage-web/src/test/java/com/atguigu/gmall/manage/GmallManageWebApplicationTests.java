package com.atguigu.gmall.manage;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallManageWebApplicationTests {

	@Test
	public void contextLoads() throws Exception {
		String tracker = GmallManageWebApplicationTests.class.getResource("/tracker.conf").getPath();
		ClientGlobal.init(tracker);

		TrackerClient trackerClient = new TrackerClient();
		TrackerServer trackerServer = trackerClient.getConnection();

		StorageClient storageClient = new StorageClient(trackerServer,null);
		String[] jpgs = storageClient.upload_file("e:/2019100613121616496573.jpg", "jpg", null);
		System.out.println("result===》" + Arrays.toString(jpgs));


	}



}
