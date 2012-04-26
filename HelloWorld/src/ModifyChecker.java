import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA-256을 이용해 파일 내용 수정 확인을 위한 클래스
 * 
 * @author koltwgi
 * [출처] SHA(Secure Hash Algorithm)을 이용한 파일 변경 여부 확인|작성자 마음이 뛰다
 */
public class ModifyChecker {
 /**
  * 파일 이름을 파일 내용의 해시값으로 변경하는 메서드
  * 
  * @param filepath 이름을 변경할 파일
  * @throws NoSuchAlgorithmException
  * @throws FileNotFoundException
  * @throws IOException
  */
 public void renameToHash(String filepath) throws NoSuchAlgorithmException, FileNotFoundException, IOException {
  File file = new File(filepath);
  
  if(file.exists()){
   String hash = getHashcode(getFileContents(filepath));
   
   String path = file.getParentFile().getAbsolutePath() + "/";
   
   // 원래의 확장자 - 파일의 확장자가 없는 경우 예외처리 안함..
   String filename = file.getName();
   String ex = filename.substring(filename.indexOf("."));
   
   file.renameTo(new File(path + hash + ex));   
  }
  else {
   throw new FileNotFoundException();
  }
 }
 
 /**
  * 파일을 읽어 내용을 byte 배열로 반환하는 메서드
  * @param filepath
  * @return byte 배열 타입의 파일 내용
  * @throws IOException
  */
 public byte[] getFileContents(String filepath) throws IOException{
  BufferedInputStream bistream = new BufferedInputStream(new FileInputStream(filepath));
  
  byte[] contents = new byte[bistream.available()];
  
  bistream.close();
  
  return contents;
 }
 
 /**
  * byte 배열로부터 SHA-256를 이용해 해시된 값을 얻는 메서드
  * 
  * @param message 해시코드를 생성할 byte 배열
  * @return message의 해시된 값으로 16진수 표현의 String으로 반환된다.
  * @throws NoSuchAlgorithmException getInstance메서드의 인자 전달한 암호화 암고리즘이 존재하지 않을 때 발생
  */
 public String getHashcode(byte[] message) throws NoSuchAlgorithmException{
  // SHA를 사용하기 위해 MessageDigest 클래스로부터 인스턴스를 얻는다.
  MessageDigest md = MessageDigest.getInstance("SHA-256");
  
  // 해싱할 byte배열을 넘겨준다.
  // SHA-256의 경우 메시지로 전달할 수 있는 최대 bit 수는 2^64-1개 이다.
  md.update(message);
  
  // 해싱된 byte 배열을 digest메서드의 반환값을 통해 얻는다.
  byte[] hashbytes = md.digest();
  
  // 보기 좋게 16진수로 만드는 작업
  StringBuilder sbuilder = new StringBuilder();
  for(int i=0 ; i<hashbytes.length ; i++){
   // %02x 부분은 0 ~ f 값 까지는 한자리 수이므로 두자리 수로 보정하는 역할을 한다. 
   sbuilder.append(String.format("%02x", hashbytes[i] & 0xff));
  }
  
  return sbuilder.toString();
 }
 
 /**
  * 파일의 변경 여부를 판단하는 메서드
  * @param file 내용의 변경여부를 확인할 파일
  * @return 수정된 경우 true 반환 <br> 보존된 경우 false 반환
  * @throws IOException 
  * @throws NoSuchAlgorithmException 
  */
 public boolean isModified(String filepath) throws NoSuchAlgorithmException, IOException{
  File file = new File(filepath);  
  
  String filename = file.getName();
  // 확장자 제거
  if(filename.contains("."))
   filename = filename.substring(0, filename.indexOf("."));
  
  String hashcode = getHashcode(getFileContents(filepath));
  
  return !filename.equals(hashcode);
 }
 
 // 테스트를 위한 메인 메서드
 public static void main(String[] args) {
  ModifyChecker checker = new ModifyChecker();
  
  /*try {
   String filepath = "파일경로/파일명";
   
   // 파일 이름 변경
   checker.renameToHash(filepath);
   
   // 파일 변경 체크
   if(checker.isModified("파일경로/파일명")){
    System.out.println("파일이 변경 되었습니다.");
   }
   
  } catch (NoSuchAlgorithmException e) {
   e.printStackTrace();
  } catch (FileNotFoundException e) {
   e.printStackTrace();
  } catch (IOException e) {
   e.printStackTrace();
  }*/
  
  try{
   // 그냥 해시코드만 얻으려면
   String hash = checker.getHashcode("이 파일은 고쳐져선 안됩니다.".getBytes());
   System.out.println(hash);
  }catch (NoSuchAlgorithmException e) {
   e.printStackTrace();
  }
  
 }
}