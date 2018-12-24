import java.util.ArrayList;

public class NucleicAcid {
	public String type; //DNA or RNA
	public ArrayList<Nucleoside> seq;

	public NucleicAcid(Nucleoside[] nucleosides) {
		Nucleoside sample = nucleosides[0];
		if (sample.isDeoxy) {
			this.type = "DNA";
		} else {
			this.type = "RNA";
		}
		this.seq = new ArrayList<>;
		for(int i = 0; i < nucleosides.length; i++) {
			seq.add(nucleosides[i]);
		}
	}
	public NucleicAcid(NucleicAcid na) {
		this.type = na.type; 
		this.seq = new ArrayList<>; this.seq = na.seq;
	}
}