package vocab;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.Scanner;

import util.IOUtils;
import util.SerializeUtils;

public class WordEntry {

	public String word;	
	public long count = 0;
	public int[] code;
	public int[] point;
	public int id;

	public WordEntry(String word) {
		this.word = word;
	}

	@Override
	public String toString() {
		return word + "(count:" + count + ", code:" + getCodeStr() + ", point:" + getPointStr() + ")";
	}

	private String getPointStr() {
		String s = "";
		for(int c : point){
			s+=c + " ";
		}
		return s;
	}

	private String getCodeStr() {
		String s = "";
		for(int c : code){
			s+=c;
		}
		return s;
	}

	public int getId() {
		return id;
	}

	public int[] getCode() {
		return code;
	}

	public int[] getPoint() {
		return point;
	}

	public String getWord() {
		return word;
	}

	public long getCount() {
		return count;
	}

	public void save(PrintStream out) {
		out.println(id);
		out.println(word);
		out.println(count);
		if(code != null){
			out.println(true);
			SerializeUtils.saveIntArray(code, out);
			SerializeUtils.saveIntArray(point, out);
		}
		else{
			out.println(false);			
		}
	}

	public void load(Scanner in){
		id = Integer.parseInt(in.nextLine());
		word = in.nextLine();
		count = Long.parseLong(in.nextLine());
		boolean containsHuffman = Boolean.parseBoolean(in.nextLine());
		if(containsHuffman){
			code = SerializeUtils.loadIntArray(in);
			point = SerializeUtils.loadIntArray(in);
		}
		in.nextLine();
	}

	public static WordEntry load(BufferedReader in){
		try{
			int id = Integer.parseInt(in.readLine());
			String word = in.readLine();
			WordEntry entry = new WordEntry(word);
			entry.id = id;
			entry.count = Integer.parseInt(in.readLine());
			boolean containsHuffman = Boolean.parseBoolean(in.readLine());
			if(containsHuffman){

				entry.code = SerializeUtils.loadIntArray(in);
				entry.point = SerializeUtils.loadIntArray(in);
			}
			return entry;
		} catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	public void addCount(int i) {
		count+=i;
	}

	public static void main(String[] args){
		Vocab vocab = new Vocab();
		vocab.addWordToVocab("hello");
		vocab.addWordToVocab("world");
		vocab.addWordToVocab("!");
		vocab.sortVocabByCount();
		vocab.saveVocab(System.err);
		vocab.saveVocab(IOUtils.getPrintStream("/tmp/file"));
		Vocab loadedVocab = Vocab.loadVocab(IOUtils.getReader("/tmp/file"));

	}
}
