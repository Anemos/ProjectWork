import java.io.*;

/**
 * A class that implements the Java FileFilter interface.
 */
public class ImageFileFilter implements FileFilter
{
  private final String[] okFileExtensions = 
    new String[] {"pbl"};

  public boolean accept(File file)
  {
    for (String extension : okFileExtensions)
    {
      if ( (file.getName().toLowerCase().endsWith(extension)) || file.isDirectory() )
      {
    	  // kdac 파일은 중요정보가 있어서 제외함
        if (file.getName().startsWith("kdac"))
        	return false;
        else
        	return true;
      }
    }
    return false;
  }
}