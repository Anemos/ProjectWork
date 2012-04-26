import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA-256�� �̿��� ���� ���� ���� Ȯ���� ���� Ŭ����
 * 
 * @author koltwgi
 * [��ó] SHA(Secure Hash Algorithm)�� �̿��� ���� ���� ���� Ȯ��|�ۼ��� ������ �ٴ�
 */
public class ModifyChecker {
 /**
  * ���� �̸��� ���� ������ �ؽð����� �����ϴ� �޼���
  * 
  * @param filepath �̸��� ������ ����
  * @throws NoSuchAlgorithmException
  * @throws FileNotFoundException
  * @throws IOException
  */
 public void renameToHash(String filepath) throws NoSuchAlgorithmException, FileNotFoundException, IOException {
  File file = new File(filepath);
  
  if(file.exists()){
   String hash = getHashcode(getFileContents(filepath));
   
   String path = file.getParentFile().getAbsolutePath() + "/";
   
   // ������ Ȯ���� - ������ Ȯ���ڰ� ���� ��� ����ó�� ����..
   String filename = file.getName();
   String ex = filename.substring(filename.indexOf("."));
   
   file.renameTo(new File(path + hash + ex));   
  }
  else {
   throw new FileNotFoundException();
  }
 }
 
 /**
  * ������ �о� ������ byte �迭�� ��ȯ�ϴ� �޼���
  * @param filepath
  * @return byte �迭 Ÿ���� ���� ����
  * @throws IOException
  */
 public byte[] getFileContents(String filepath) throws IOException{
  BufferedInputStream bistream = new BufferedInputStream(new FileInputStream(filepath));
  
  byte[] contents = new byte[bistream.available()];
  
  bistream.close();
  
  return contents;
 }
 
 /**
  * byte �迭�κ��� SHA-256�� �̿��� �ؽõ� ���� ��� �޼���
  * 
  * @param message �ؽ��ڵ带 ������ byte �迭
  * @return message�� �ؽõ� ������ 16���� ǥ���� String���� ��ȯ�ȴ�.
  * @throws NoSuchAlgorithmException getInstance�޼����� ���� ������ ��ȣȭ �ϰ����� �������� ���� �� �߻�
  */
 public String getHashcode(byte[] message) throws NoSuchAlgorithmException{
  // SHA�� ����ϱ� ���� MessageDigest Ŭ�����κ��� �ν��Ͻ��� ��´�.
  MessageDigest md = MessageDigest.getInstance("SHA-256");
  
  // �ؽ��� byte�迭�� �Ѱ��ش�.
  // SHA-256�� ��� �޽����� ������ �� �ִ� �ִ� bit ���� 2^64-1�� �̴�.
  md.update(message);
  
  // �ؽ̵� byte �迭�� digest�޼����� ��ȯ���� ���� ��´�.
  byte[] hashbytes = md.digest();
  
  // ���� ���� 16������ ����� �۾�
  StringBuilder sbuilder = new StringBuilder();
  for(int i=0 ; i<hashbytes.length ; i++){
   // %02x �κ��� 0 ~ f �� ������ ���ڸ� ���̹Ƿ� ���ڸ� ���� �����ϴ� ������ �Ѵ�. 
   sbuilder.append(String.format("%02x", hashbytes[i] & 0xff));
  }
  
  return sbuilder.toString();
 }
 
 /**
  * ������ ���� ���θ� �Ǵ��ϴ� �޼���
  * @param file ������ ���濩�θ� Ȯ���� ����
  * @return ������ ��� true ��ȯ <br> ������ ��� false ��ȯ
  * @throws IOException 
  * @throws NoSuchAlgorithmException 
  */
 public boolean isModified(String filepath) throws NoSuchAlgorithmException, IOException{
  File file = new File(filepath);  
  
  String filename = file.getName();
  // Ȯ���� ����
  if(filename.contains("."))
   filename = filename.substring(0, filename.indexOf("."));
  
  String hashcode = getHashcode(getFileContents(filepath));
  
  return !filename.equals(hashcode);
 }
 
 // �׽�Ʈ�� ���� ���� �޼���
 public static void main(String[] args) {
  ModifyChecker checker = new ModifyChecker();
  
  /*try {
   String filepath = "���ϰ��/���ϸ�";
   
   // ���� �̸� ����
   checker.renameToHash(filepath);
   
   // ���� ���� üũ
   if(checker.isModified("���ϰ��/���ϸ�")){
    System.out.println("������ ���� �Ǿ����ϴ�.");
   }
   
  } catch (NoSuchAlgorithmException e) {
   e.printStackTrace();
  } catch (FileNotFoundException e) {
   e.printStackTrace();
  } catch (IOException e) {
   e.printStackTrace();
  }*/
  
  try{
   // �׳� �ؽ��ڵ常 ��������
   String hash = checker.getHashcode("�� ������ �������� �ȵ˴ϴ�.".getBytes());
   System.out.println(hash);
  }catch (NoSuchAlgorithmException e) {
   e.printStackTrace();
  }
  
 }
}