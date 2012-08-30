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
        return true;
      }
    }
    return false;
  }
}