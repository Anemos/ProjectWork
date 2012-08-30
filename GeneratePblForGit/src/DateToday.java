import java.text.SimpleDateFormat;
import java.util.Date;

public class DateToday {
	Date today = new Date();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
	
	public void setFormat(String fmt) {
		this.formatter = new SimpleDateFormat(fmt);
	}
	
	// 00시 00분에 대한 시간상수 가져오기
	public long getNightNoon() {
		try {
			String dateString = formatter.format(today);
			Date date = formatter.parse(dateString);
			return date.getTime();
		} catch ( Exception err) {
			System.err.println("에러! 시간계산에 실패했습니다.\n" + err.getMessage());
		} 
		return 0;
	}
}
