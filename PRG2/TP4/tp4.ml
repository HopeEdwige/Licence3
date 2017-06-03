
(* Reccup TP3 *)

type etatPinceau = Pose | Leve;;

type position = int * int;;

type direction = Nord | Sud | Est | Ouest;;

type programme =
| Stop
| TourneGauche
| TourneDroite
| Avance of int
| Pinceau of etatPinceau
| Bloc of programme list
| Repete of int * programme;;

type robot = {pos: position; dir: direction; etat: etatPinceau};;

let gauche = function
  | Nord -> Ouest
  | Est -> Nord
  | Sud -> Est
  | Ouest -> Sud;;

let droite = function
  | Ouest -> Nord
  | Nord -> Est
  | Est -> Sud
  | Sud -> Ouest;;

let avance (x,y) i dir =
  match dir with
  | Nord -> (x,y+i)
  | Est -> (x+i,y)
  | Sud -> (x,y-i)
  | Ouest -> (x-i,y);;

let rec execute p r =
  match p with
  | Stop -> r
  | TourneGauche -> {r with dir= (gauche r.dir)}
  | TourneDroite -> {r with dir= (droite r.dir)}
  | Avance i -> {r with pos= (avance r.pos i r.dir)}
  | Pinceau b -> {r with etat= b}
  | Bloc [] -> r
  | Bloc (p1::l) ->
    let r1 = execute p1 r in
    execute (Bloc l) r1
  | Repete(i,p1) ->
    if i<=0 then r
    else
      let r1= execute p1 r in
      execute (Repete(i-1,p1)) r1;;

let mon_robot = {
  pos = (0, 0);
  dir = Nord;
  etat = Pose
};;

let p1 = Bloc([Pinceau Pose;Repete(4,Bloc([Avance(10);TourneDroite]))]);;

(* Fin Reccup TP3 *)


(* -------- *)

type 'a gen = unit -> 'a;;

let lance (g : 'a gen) : 'a = g ();;

let genInt i j =
  fun () ->
    (Random.int (j-i+1))+i;;

let rec genList g i =
  fun () ->
    if i=0
    then []
    else lance g :: lance (genList g (i-1));;

type 'a resultatTest = Ok | Echec of 'a;;

let rec_max = 20;;

let rec genererProg i = function () ->
  if i = 0
  then match Random.int 5 with
      0 -> Repete (Random.int rec_max, (lance (genererProg (i-1))))
    | 1 -> TourneGauche
    | 2 -> TourneDroite
    | 3 -> Avance (Random.int rec_max)
    | 4 -> match Random.int 2 with
	  0 -> Pinceau Pose
	| 1 -> Pinceau Leve
  else if i > 0
  then Bloc (lance (genList (genererProg (i-1)) (Random.int rec_max)))
  else Stop;;

let mon_prog = lance (genererProg 2);;

let result = execute mon_prog mon_robot;;

let rec pasRepete = function p ->
  match p with
      Stop -> true
    | Repete (_, _) -> false
    | TourneGauche -> true
    | TourneDroite -> true
    | Avance _ -> true
    | Pinceau _ -> true
    | Bloc l -> List.for_all pasRepete l;;

let result_pas_repete = pasRepete mon_prog;;
