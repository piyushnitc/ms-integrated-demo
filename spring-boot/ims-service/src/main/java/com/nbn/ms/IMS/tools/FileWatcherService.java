package com.nbn.ms.IMS.tools;

import java.io.IOException;
import java.nio.file.*;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class FileWatcherService {

	
	public static void runService() throws IOException, InterruptedException {

		NRLogService nrLogService = new NRLogService();
		
		Thread thread = new Thread() {
			public void run() {
				System.out.println("Thread Running");
				WatchService watchService = null;
				try {
					watchService = FileSystems.getDefault().newWatchService();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				// Path path = Paths.get(System.getProperty("user.dir"));
				Path path = Paths.get("./logs/");
				System.out.println("****" + path.toString());
				try {
					path.register(watchService, 
							StandardWatchEventKinds.ENTRY_CREATE,
							//StandardWatchEventKinds.ENTRY_DELETE, 
							StandardWatchEventKinds.ENTRY_MODIFY);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				int i = 0;
				WatchKey key;
				try {
					while ((key = watchService.take()) != null) {
						for (WatchEvent<?> event : key.pollEvents()) {
							System.out.println(
									"Event kind:" + event.kind() + ". File affected: " + event.context() + ".");

							if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
								System.out.println("File Created ...");
							} else if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
								i++;
								System.out.println("File Modified ..." + i);
								if (Objects.nonNull(nrLogService)) {
									System.out.println("Logging ...");
									//nrLogService.sendLogToNewRelic();
								}

							}

						}
						key.reset();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};
		thread.start();

	}
}
