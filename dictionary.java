import java.io.*;
import java.net.URL;
import java.util.Scanner;

public class dictionary {

	public static void main(String[] args) throws IOException
	{
		dictionary dict = new dictionary();
		
	}
	
	//constructor
	public dictionary() throws IOException
	{
		
		copyDictionary();
	}

	//makes Dictionary
	private void copyDictionary() throws IOException
	{
		// Open the file
		FileInputStream fstream = new FileInputStream("tempDictionary.txt"); ///usr/share/dict/american-english
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		File dictionary = new File("tempDictionary2.txt");
		int size = 0;
		
		//create the file
		dictionary.createNewFile();
		
		//write to file
		FileWriter writer = new FileWriter(dictionary);
		
		String strLine;

		//Read File Line By Line
		while ((strLine = br.readLine()) != null)
		{
		  //write to file along with part of speech.
			if(containsComma(strLine) == false)
			{
				writer.write(strLine + ": " + practiceScrape(strLine) + "\n");
				writer.flush();
				size++;
			}
		}
		writer.close();
		//Close the input stream
		br.close();
		System.out.println("size of Dictionary.txt = " + size);

	}//end copyDictionary
	

	private boolean containsComma(String word)
	{
		if(word.length() > 2 && word.charAt(word.length()-2) == '\'')
		{
			return true;
			
		}
		return false;
	}//end containsComma
	
	private String practiceScrape(String word) throws IOException
	{
		
		URL pageLocation = new URL("http://alpha.totodefinition.com/search.php?word="+word);
		Scanner in = new Scanner(pageLocation.openStream());
		String PoS = "";
		
		while(in.hasNext())
		{
			String line = in.next();
			//System.out.println(line);
			if(line.contains("valign=\"top\"><b>") && !line.contains("1"))
			{
				int from = line.indexOf("<b>");
				int to = line.lastIndexOf("</b>");
				PoS += line.substring(from +3, to);
				System.out.println("Part of Speech: " + PoS);
				
			}
			
		}
		in.close();
		
		return PoS;
	}//practice scrape

}
