import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class GetStudentList extends Thread
{
	private SelectClassScreen screen;
	private String clas;
	
	public GetStudentList(SelectClassScreen s, String cl)
	{
		screen = s;
		clas = cl;
		this.start();
	}
	
	public void run()
	{
		try
		{
			String[] students;
			String link = "https://php.radford.edu/~team05/getclassstudents.php";
	        String data = URLEncoder.encode("class", "UTF-8") + "=" + URLEncoder.encode(clas, "UTF-8");
	        
	        URL url = new URL(link);
	        URLConnection conn = url.openConnection();
	        
	        conn.setDoOutput(true);
	        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	
	        wr.write(data);
	        wr.flush();
	
	        BufferedReader reader = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	
	        StringBuilder sb = new StringBuilder();
	        String line;
	
	        //read server response
	        if((line = reader.readLine()) != null)
	        {
	            sb.append(line);
	            students = sb.toString().split("&");
	        }
	        else
	        {
	        	students = new String[] {"FUCKINGs Found"};
	        }
	        
	        setStudents(students);
	        this.interrupt();
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	        this.interrupt();
	    }
	}
	
	private void setStudents(String[] l)
	{
		screen.setStudents(l);
	}
}