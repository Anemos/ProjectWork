import org.apache.tools.ant.Task;

public class GeneratePbldump extends Task {
	public void execute() {
		FileFinder ff = new FileFinder();
        String rootFolder = "c:\\kdac\\";
        ff.listFilesByExport(rootFolder);
    }
}
