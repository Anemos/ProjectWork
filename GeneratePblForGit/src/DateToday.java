import java.text.SimpleDateFormat;
import java.util.Date;

public class DateToday {
	Date today = new Date();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
	
	public void setFormat(String fmt) {
		this.formatter = new SimpleDateFormat(fmt);
	}
	
	// 00�� 00�п� ���� �ð���� ��������
	public long getNightNoon() {
		try {
			String dateString = formatter.format(today);
			Date date = formatter.parse(dateString);
			return date.getTime();
		} catch ( Exception err) {
			System.err.println("����! �ð���꿡 �����߽��ϴ�.\n" + err.getMessage());
		} 
		return 0;
	}
}
