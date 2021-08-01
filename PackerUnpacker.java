import java.io.*;
import java.util.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;

class PackerUnpacker
{
	public static void main(String arg[])
	{
		Scanner sobj = new Scanner(System.in);
		System.out.println("-------------------------------");
		System.out.println("File Packer - Unpacker");
	
		while(true)
		{
			System.out.println("-------------------------------");
			System.out.println("Enter your choice");
			System.out.println("1 : Packing");
			System.out.println("2 : Unpacking");
			System.out.println("3 : Exit");
			System.out.println("-------------------------------");
			
			String sDir,sFilename;
			int iChoice = sobj.nextInt();

			switch(iChoice)
			{
				case 1:
					System.out.println("Enter Directory name");
					sDir = sobj.next();

					System.out.println("Enter the file name for packing");
					sFilename = sobj.next();

					Packer pobj = new Packer(sDir,sFilename);

				break;

				case 2:
					System.out.println("Enter packed file name");
					sFilename = sobj.next();

					Unpacker uobj = new Unpacker(sFilename);

				break;

				case 3:
					System.out.println("-------------------------------");
					System.out.println("Thank you for using File Packer Unpacker");
					System.out.println("-------------------------------");
					System.exit(0);

				break;

				default:
					System.out.println("Wrong choice");
				break;
			}
		}
	}
}

class Packer
{
	// Object for file writing
	public FileOutputStream outstream = null;

	// parametrised constructor
	public Packer(String sFolderName, String sFileName)
	{
		try
		{
			// Create new file for packing
			File outfile = new File(sFileName);
			outstream = new FileOutputStream(sFileName);
			
			TravelDirectory(sFolderName);
		}
		catch(Exception obj)
		{
			System.out.println(obj);
		}
	}

	public void TravelDirectory(String path)
	{
		File directoryPath = new File(path);
		int iCounter = 0;
		
		// Get all file names from directory
		File arr[] = directoryPath.listFiles();

		System.out.println("-------------------------------");
		for(File filename : arr)
		{	
			if(filename.getName().endsWith(".txt"))
			{
				iCounter++;
				System.out.println("Packed file : "+filename.getName());
				PackFile(filename.getAbsolutePath());	
			}		
		}
		System.out.println("-------------------------------");
		System.out.println("Succesfully packed files : "+iCounter);
		System.out.println("-------------------------------");
	}

	public void PackFile(String sFilePath)
	{
		byte bHeader[] = new byte[100];
		byte bBuffer[] = new byte[1024];
		int iLength = 0;

		FileInputStream istream = null;

		File fobj = new File(sFilePath);

		String temp = sFilePath+" "+fobj.length();

		// Create header of 100 bytes
		for(int i = temp.length(); i< 100; i++)
		{
			temp = temp + " ";
		}	

		bHeader = temp.getBytes();
		
		try
		{
			// open the file for reading
			istream = new FileInputStream(sFilePath);

			outstream.write(bHeader,0,bHeader.length);
			
			while((iLength = istream.read(bBuffer)) > 0)
			{
				outstream.write(bBuffer,0,iLength);
			}

			istream.close();
		}
		catch(Exception obj)
		{}
	}
}

class Unpacker
{	
	public FileOutputStream outstream = null;

	public Unpacker(String src)
	{
		unpackFile(src);
	}

	public void unpackFile(String sFilePath)
	{
		try
		{
			FileInputStream instream = new FileInputStream(sFilePath);
			
			byte bHeader[] = new byte[100];
			int iLength = 0;
			int iCounter = 0;

			while((iLength = instream.read(bHeader,0,100)) > 0)
			{
				String str = new String(bHeader);
				
				String ext = str.substring(str.lastIndexOf("/"));

				ext = ext.substring(1);

				String words[] = ext.split("\\s");
				String name = words[0];
				int size = Integer.parseInt(words[1]);

				byte arr[] = new byte[size];
				instream.read(arr,0,size);
				
				System.out.println("New File gets created as :"+name);
				
				// New file gets created
				FileOutputStream fout = new FileOutputStream(name);
				
				// Write the data into newnly created file
				fout.write(arr,0,size);

				iCounter++;
			}

			System.out.println("Sucessfully unpacked files : "+iCounter);
		}
		catch(Exception obj)
		{}
	}
}




























