public class Nucleoside {
	//nucleoSIDE = sugar + base
	//nucleoTIDE = nucleoSIDE + phosphate grps
	public char base;
	public boolean isDeoxy; //1 = DNA, 0 = RNA

	public Nucleoside(char b, boolean d) {
		this.base = b; this.isDeoxy = d;
	}
	public Nucleoside(Nucleoside n) {
		this.base = n.base; this.isDeoxy = n.isDeoxy;
	}
}