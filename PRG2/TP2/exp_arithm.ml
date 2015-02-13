type ('a, 'b) arbre_bin = Feuille of 'b
			  | Noeud of ('a, 'b) noeud
and ('a, 'b) noeud = { gauche : ('a, 'b) arbre_bin;
		       op : 'a;
		       droite : ('a, 'b) arbre_bin };;

type operateur = Plus | Moins | Mult | Div;;

type exp_arithm = (operateur, int) arbre_bin;;



let exp_arithm e0 = Noeud { gauche = Feuille 1;
		 op = Plus;
		 droite = Feuille 4 };;
let exp_arithm e1 = Noeud { gauche = Feuille 3;
		 op = Mult;
		 droite = e0 };;


let a0 = Feuille "string ici";;
let a1 = Noeud { gauche = a0;
		 op = "+";
		 droite = Feuille "retest" };;
let a2 = Noeud { gauche = Feuille 0;
		 op = "duehb";
		 droite = Feuille 5 };;



let rec (eval : exp_arithm -> int) =
  Feuille f -> f
  | Noeud n -> match n.op with
      Plus -> eval n.gauche + eval n.droite
      | Moins -> eval n.gauche - eval n.droite
      | Mult -> eval n.gauche * eval n.droite
      | Div -> eval n.gauche / eval n.droite
      | _ -> failwith "Operateur non reconnu";;

eval(e1);;
