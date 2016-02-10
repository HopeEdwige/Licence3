import java.io.*;

class Mnemo { 

    // La procédure Mnemo.creerFichier(int ipo,int[] po,String nomComplet) 
    // permet d'obtenir, dans le fichier de nom nomComplet (ie AVEC suffixe 
    // .gen ou .ima), le mnémonique du code contenu dans le tableau po[1..ipo] 
    // Les tableaux inst et nbp sont "public" car ils servent dans Mapile
 
    public static final String [] inst =
    {"",
     "réserver  ","empiler   ","contenug  ","affecterg ","ou        ",
     "et        ","non       ","inf       ","infeg     ","sup       ",
     "supeg     ","eg        ","diff      ","add       ","sous      ",
     "mul       ","div       ","bsifaux   ","bincond   ","lirent    ",
     "lirebool  ","ecrent    ","ecrbool   ","arrêt     ","empileradg",
     "empileradl","contenul  ","affecterl ","appel     ","retour    "};
	
    public static final int [] nbp =
    {0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,1,2,2,2,2,1};
	
    public static void creerFichier(int ipo,int[] po,String nomComplet) {
	int code,i=1;
	OutputStream f=Ecriture.ouvrir(nomComplet);
	if (f==null) {
	    System.out.println("ouverture de "+nomComplet+" impossible");
	    System.exit(1);
	}
	Ecriture.ecrireStringln(f,"FICHIER "+nomComplet+" : ");
	Ecriture.ecrireStringln(f,"");
	while (i<=ipo) {
	    code=po[i];
	    Ecriture.ecrireInt(f,i,4);
	    if (code<1 || code>=inst.length) {
		Ecriture.ecrireStringln(f," code instruction incorrect : "+code);
		Ecriture.fermer(f);
		System.out.println("code mal généré, consultez le fichier "
				   +nomComplet);
		System.exit(1);
	    }
	    Ecriture.ecrireString(f,"  "+inst[code]);	
	    switch (nbp[code]) {
	    case 1 : i++;Ecriture.ecrireInt(f,po[i],7);break;
	    case 2 : i++;Ecriture.ecrireInt(f,po[i],7);
		     i++;Ecriture.ecrireInt(f,po[i],4);break;
	    }	    
	    i++;Ecriture.ecrireStringln(f,"");    
	}
	Ecriture.fermer(f);
    }
}




// Les classes suivantes permettent de déclarer un descripteur : 
// EltDef : type de chaque élément de la table des points d'entrée tabDef
// EltRef : type de chaque élément de la table des références externes tabRef
// Descripteur : type d'un descripteur, les procédures ecrireDesc/lireDesc
//               permettent la sauvegarde/initialisation d'une variable
//               Descripteur vers/à partir d'un fichier dont le nom est fourni 
//               SANS suffixe (celui-ci est ajouté par les deux procédures et 
//               est ".desc")


class EltDef {
    public String nomProc;
    public int adPo,nbParam;
    public EltDef () { }
    public EltDef (String nomProc,int adPo,int nbParam) {
	this.nomProc=nomProc;this.adPo=adPo;this.nbParam=nbParam;
	}
}
    
class EltRef {
    public String nomProc;
    public int nbParam;
    public EltRef () { }
    public EltRef (String nomProc,int nbParam) {
	this.nomProc=nomProc;this.nbParam=nbParam;
    }
}


class Descripteur {
    //valeurs du vecteur de translation 
    public static final int TRANSDON=1,TRANSCODE=2,REFEXT=3;

    public String unite;
    public int tailleCode,tailleGlobaux,nbDef=0,nbRef=0,nbTransExt=0;

    public static final int MAXREF=10,MAXDEF=10;
    public EltDef [] tabDef = new EltDef [MAXDEF+1];
    public EltRef [] tabRef = new EltRef [MAXREF+1];

    public Descripteur() { 
	for (int i=0;i<=MAXDEF;i++) tabDef[i]=new EltDef("inconnu",-2,-2);
	for (int i=0;i<=MAXREF;i++) tabRef[i]=new EltRef("inconnu",-2);
    }

    public String toString() {
	String s=
	    "unité          "+unite+"\n"+
	    "tailleCode     "+tailleCode+"\n"+
	    "tailleGlobaux  "+tailleGlobaux+"\n"+
	    "nbDef          "+nbDef+"\n"+
	    "nbRef          "+nbRef+"\n"+
	    "nbTransExt     "+nbTransExt+"\n"+
	    "tabDef         "+" \n";
	for (int i=1;i<=nbDef;i++) 
	    s=s+"    "+tabDef[i].nomProc+"  "+tabDef[i].adPo+"  "+
		tabDef[i].nbParam+"\n";
	s=s+"tabRef         "+" \n";
	for (int i=1;i<=nbRef;i++) 
	    s=s+"    "+tabRef[i].nomProc+"  "+tabRef[i].nbParam+"\n";
	return s;
    }

	// Utilitaire permettant d'ecrire dans un fichier le contenu d'un descripteur
    public void ecrireDesc(String nomFichier) {
	OutputStream f=Ecriture.ouvrir(nomFichier+".desc");
	if (f==null) {
	    System.out.println("création de "+nomFichier+".desc impossible");
	    System.exit(1);
	}
	Ecriture.ecrireStringln(f,"FICHIER "+nomFichier+".desc :");
	Ecriture.ecrireStringln(f,"");
	Ecriture.ecrireString(f,""+this);
	Ecriture.fermer(f);
    }

	// Utilitaire permettant d'initialiser un descripteur à partir d'un fichier .desc
    public void lireDesc(String nomFichier) {
	InputStream f=Lecture.ouvrir(nomFichier+".desc");
	String nomProc;
	int adPo,nbParam;
	if (f==null) {
	    System.out.println("fichier "+nomFichier+".desc inexistant");
	    System.exit(1);
	}
	Lecture.lireString(f);Lecture.lireString(f);
	Lecture.lireUnite(f,false);unite=Lecture.lireUnite(f,true);
	Lecture.lireUnite(f,false);tailleCode=Lecture.lireIntln(f);
	Lecture.lireUnite(f,false);tailleGlobaux=Lecture.lireIntln(f);
	Lecture.lireUnite(f,false);nbDef=Lecture.lireIntln(f);
	Lecture.lireUnite(f,false);nbRef=Lecture.lireIntln(f);
	Lecture.lireUnite(f,false);nbTransExt=Lecture.lireIntln(f);
	Lecture.lireString(f);
	for (int i=1;i<=nbDef;i++) {
	    nomProc=Lecture.lireUnite(f,false);
	    adPo=Lecture.lireInt(f);
	    nbParam=Lecture.lireIntln(f);
	    tabDef[i]=new EltDef(nomProc,adPo,nbParam);
	}
	Lecture.lireString(f);
	for (int i=1;i<=nbRef;i++) {
	    nomProc=Lecture.lireUnite(f,false);
	    nbParam=Lecture.lireIntln(f);
	    tabRef[i]=new EltRef(nomProc,nbParam);
	}
	Lecture.fermer(f);
    }
}
