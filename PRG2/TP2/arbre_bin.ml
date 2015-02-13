type ('a, 'b) arbre_bin = Feuille of 'b
			  | Noeud of ('a, 'b) noeud
and ('a, 'b) noeud = { gauche : ('a, 'b) arbre_bin;
		       op : 'a;
		       droite : ('a, 'b) arbre_bin };;


let a0 = Feuille 3;;
let a1 = Noeud { gauche = a0;
		 op = "+";
		 droite = Feuille 0 };;
let a2 = Noeud { gauche = Feuille 0;
		 op = "-";
		 droite = a1 };;



let rec nb_noeuds = function
      Feuille f -> 0
    | Noeud n -> 1 + nb_noeuds n.gauche + nb_noeuds n.droite;;

assert(nb_noeuds(a0) = 0);;
assert(nb_noeuds(a1) = 1);;
assert(nb_noeuds(a2) = 2);;



let rec profondeur = function 
Feuille f -> 0 
  | Noeud n -> let prof_g = profondeur n.gauche and prof_d = profondeur n.droite in
	       if (prof_g > prof_d) then 1 + prof_g else 1 + prof_d;;

assert(profondeur(a0) = 0);;
assert(profondeur(a1) = 1);;
assert(profondeur(a2) = 2);;



let rec mirroir = function
Feuille f -> Feuille f
  | Noeud n -> Noeud { gauche = mirroir n.droite;
		       op = n.op;
		       droite = mirroir n.gauche; };;

mirroir(a2);;



let rec ops_et_feuilles = function 
Feuille f -> ([], [f])
  | Noeud n -> let (op_g, f_g) = ops_et_feuilles n.gauche and (op_d, f_d) = ops_et_feuilles n.droite in (n.op :: op_g @ op_d, f_g @ f_d);;

ops_et_feuilles(a2);;
