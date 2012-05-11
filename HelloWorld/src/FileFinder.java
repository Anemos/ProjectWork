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
                	// 변경된 파일날짜 체크
                	long curTime = new DateToday().getNightNoon();
                	long lastTime = f.lastModified();
                    if ( imageValidator.validate(f.getName()) && f.lastModified() > curTime ) {            	
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
                    		/*
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
                    	    */
                    	} catch (Exception err) { // 에러 처리
                    		System.setProperty("user.dir", userDir);
                    	    System.err.println("에러! 외부 명령 실행에 실패했습니다.\n" + err.getMessage());
                    	    System.exit(-1);
                    	}
                    }
                }
            }
        }
        
    }
    /*
    public static void main(String[] args) {
        FileFinder ff = new FileFinder();
        String rootFolder = "c:\\kdac\\PBL\\PB90\\wip_execute\\";
        System.out.println("List of all files under " + rootFolder);
        System.out.println("------------------------------------");
        ff.listFilesByExport(rootFolder); // this will take a while to run!
    }
    */
}
