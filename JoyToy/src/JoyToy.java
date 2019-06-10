import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;

public class JoyToy {
	
	static void usage() {
		System.out.println("usage: Java JoyToy [source file name]");
	}
	
	public static void main(String[] args) {
		boolean interactice = false;
		
		if(args.length >= 2) {
			usage();
			return;
		}
		
		try {
			BufferedReader in;
			
			if(args.length == 0) {
				in = new BufferedReader(new InputStreamReader(System.in));
				interactice = true;
			} else {
				in = new BufferedReader(new FileReader(args[0]));
			}
			
			Lexer lex = new Lexer(in);
			Parser parser = new Parser();
			while(true) {
				if(interactice) {
					System.out.print("JoyToy: ");
				}
				JTCode code = (JTCode) parser.parse(lex);
				if(code == null) break;
				System.out.println("result = " + code.toString());
			}
			in.close();
		} catch (FileNotFoundException e) {
			if(args.length > 0) {
				System.out.println("can't open file '" + args[0] + "'");
			} else {
				System.out.println("can't open file");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
