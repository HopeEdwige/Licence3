let l = [1; 2; 3; 4; 5; 6];;



let rec longueur l = match l with
    [] -> 0
  | x::r -> 1 + longueur r;;

assert(longueur(l) = 6);;
assert(longueur([]) = 0);;



let rec oter ( l, e ) = match l with
    [] -> []
  | x::r -> if x = e then oter(r, e) else x::oter(r, e);;

assert(oter(l, 4) = [1; 2; 3; 5; 6]);;
assert(oter([1; 2; 3], 4) = [1; 2; 3]);;
assert(oter([], 4) = []);;



let rec nboccur (l, e) = match l with
    [] -> 0
  | x::r -> if x = e then 1 + nboccur(r, e) else nboccur(r, e);;

assert(nboccur(l, 4) = 1);;
assert(nboccur([1; 2; 3; 5; 6], 4) = 0);;
assert(nboccur([], 4) = 0);;



let rec remplacer (l, x, y) = match l with
    []-> []
  | t::r -> if t = x then y::remplacer(r, x, y) else t::remplacer(r, x, y);;

assert(remplacer(l, 4, 6) = [1; 2; 3; 6; 5; 6]);;



let rec nieme (l, i) = match l with
    [] -> None
  | x::r -> if i = 1 then Some x else nieme(r, i-1);;

assert(nieme(l, 4) = Some 4);;
assert(nieme([], 42) = None);;
