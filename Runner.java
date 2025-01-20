package ie.gmit.dip;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class Runner {
	public static void main(String[] args) throws MalformedURLException, FileNotFoundException, IOException, URISyntaxException{
		new Menu().go();//starts menu and scanner for user input
	}
}
