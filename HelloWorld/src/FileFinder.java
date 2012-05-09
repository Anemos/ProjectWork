import java.io.*;

public class FileFinder {
	private PblFileValidator imageValidator = new PblFileValidator();
	
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
        /*
        try {
        	Process oProcess = new ProcessBuilder("C://Program Files//Microsoft Office//Office12//EXCEL.exe","d:/test.xls").start();
        } catch (IOException e) { // 에러 처리
    	    System.err.println("에러! 외부 명령 실행에 실패했습니다.\n" + e.getMessage());
    	    System.exit(-1);
    	}
    	*/
        
        if (list != null) {  // In case of access error, list is null
            for (File f : list) {
                if (f.isDirectory()) {
                    //System.out.println(f.getAbsoluteFile());
                    listAllFiles(f.getAbsolutePath());
                } else {
                    if ( imageValidator.validate(f.getName()) ) {
                    	//System.out.println(f.getAbsoluteFile());
                    	System.out.println(f.getAbsolutePath());
                    	try {
                    		Process oProcess = new ProcessBuilder("E:\\pbldump-1.3.1stable\\pbldump.exe", "-es", "test_app.pbl","*.*").start();            	
                    	} catch (IOException e) { // 에러 처리
                    	    System.err.println("에러! 외부 명령 실행에 실패했습니다.\n" + e.getMessage());
                    	    System.exit(-1);
                    	}
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
