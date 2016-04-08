import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class GetClassListForTeacher
{
	private static String[] list;
	private static SelectClassScreen select;
	
	public GetClassListForTeacher(SelectClassScreen s)
	{
		select = s;
	}
		
	public static void getClassList (final String[] args)
    {
		Thread thread = new Thread()
		{
			public void run()
			{
				getClasses(args);
			}
		};
		
		thread.start();
	}
	
	private static void getClasses(String[] args)
	{
		try{
			String uname = args[0];
			String link = "https://php.radford.edu/~team05/getclasslistforteacher.php";
	        String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(uname, "UTF-8");
	
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
	            list = sb.toString().split("&");
	        }
	        else
	        {
	        	String[] result = {"No Classes Found"};
	        	list = result;
	        }
	        
	        select.setClasses(list);
	        
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	    }
//		return new String[] {""};
	}
	
}