import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileFinder {
	private PblFileValidator imageValidator = new PblFileValidator();
	static String FILE_SEPARATOR   = System.getProperty("file.separator");
	private String userDir = System.getProperty("user.dir");
	private String workingDir;
	
    public void listAllFiles(String path) {
        File root = new File(path);
        File[] list = root.listFiles(new ImageFileFilter());
        if (list != null) {  // In case of access error, list is null
            for (File f : list) {
                if (f.isDirectory()) {
                    System.out.println(f.getAbsoluteFile());
                    listAllFiles(f.getAbsolutePath());
                } else {
                    System.out.println(f.getAbsoluteFile());
                }
            }
        }
    }
    
    public void listFilesByExport(String path) {
        File root = new File(path);
        File[] list = root.listFiles(new ImageFileFilter());
        
        if (list != null) {  // In case of access error, list is null
            for (File f : list) {
                if (f.isDirectory()) {
                    //System.out.println(f.getAbsoluteFile());
                	listFilesByExport(f.getAbsolutePath());
                } else {
                	// ����� ���ϳ�¥ üũ
                	long curTime = new DateToday().getNightNoon();
                	long lastTime = f.lastModified();
                    if ( imageValidator.validate(f.getName()) && f.lastModified() > curTime ) {
                    	System.out.println(f.getName());
                    	/*
                    	try {
                    		String line;
                    		String[] commands =
                    		       {"cmd.exe",
                    		        "/c",
                    		        "e:"
                    		        + FILE_SEPARATOR
                    		        + "pbldump-1.3.1stable"
                    		        + FILE_SEPARATOR + "pbldump.exe -es " + f.getAbsolutePath() + " *.*"};

                    		Process oProcess = new ProcessBuilder(commands).start();
                    		
                    		Process oProcess = new ProcessBuilder("cmd", "/c", "dir").start();
                    		BufferedReader bri = new BufferedReader
                    		        (new InputStreamReader(oProcess.getInputStream()));
                    		BufferedReader bre = new BufferedReader
                    		        (new InputStreamReader(oProcess.getErrorStream()));
                    		while ((line = bri.readLine()) != null) {
                    		  System.out.println(line);
                    		}
                    		bri.close();
                    		while ((line = bre.readLine()) != null) {
                    		  System.out.println(line);
                    		}
                    		      bre.close();
                    		      oProcess.waitFor();
                    		      System.out.println("Done.");
                    	    
                    	} catch (Exception err) { // ���� ó��
                    		System.setProperty("user.dir", userDir);
                    	    System.err.println("����! �ܺ� ��� ���࿡ �����߽��ϴ�.\n" + err.getMessage());
                    	    System.exit(-1);
                    	}
                    	*/
                    }
                }
            }
        }
        
    }
    
    public static void main(String[] args) {
        FileFinder ff = new FileFinder();
        String rootFolder = "c:\\kdac\\PBL\\PB90\\wip_execute\\";
        System.out.println("List of all files under " + rootFolder);
        System.out.println("------------------------------------");
        ff.listFilesByExport(rootFolder); // this will take a while to run!
    }
    
}
